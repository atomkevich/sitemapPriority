package com.epam;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import org.apache.commons.collections.IteratorUtils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.data.mongodb.core.MongoOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.Dictionary.DICTIONARY_NEW;
import static com.epam.Dictionary.DICTIONARY_USED;
import static org.jsoup.Jsoup.connect;

/**
 * author alina on 16.6.15.
 */
public class WorkerThread implements Runnable {
    private final static String SITEMAP = "sitemap.xml";
    private final static String INVENTORY_SITEMAP = "inventorysitemap.xml";

    private List<UrlStats> validUrls;
    MongoOperations mongoOperation;

    public WorkerThread(List<UrlStats> validUrls, MongoOperations mongoOperation) {
        this.validUrls = validUrls;
        this.mongoOperation = mongoOperation;
    }

    private final Predicate<Element> elementsFilter = new Predicate<Element>() {
        @Override
        public boolean apply(Element element) {
            return "1.0".equals(((TextNode) element.childNode(0)).text());
        }
    };


    @Override
    public void run() {
        HttpClientParams httpClientParams = new HttpClientParams();
        httpClientParams.setSoTimeout(5000);
        httpClientParams.setConnectionManagerTimeout(5000l);
        HttpClient client = new HttpClient(httpClientParams);


        for (UrlStats urlStats : validUrls) {

            System.out.println("start processing url: " + urlStats.getUrl());
            AnalizeMap analizeMap = new AnalizeMap();
            analizeMap.setSite(urlStats.getUrl());

            GetMethod method = new GetMethod(urlStats.getUrl());
            GetMethod siteMapPAge = new GetMethod(normalizeUrl(urlStats.getUrl(), SITEMAP));
            GetMethod inventorySiteMap = new GetMethod(normalizeUrl(urlStats.getUrl(), INVENTORY_SITEMAP));

            try {
                if (client.executeMethod(method) == 200) {
                    if ((client.executeMethod(inventorySiteMap) != 200)) {
                        if ((client.executeMethod(siteMapPAge) != 200)) {
                            analizeMap.setHasPriority(false);
                            analizeMap.setHasSitemap(false);
                            analizeMap.setErrors("client response code: " + client.executeMethod(siteMapPAge));
                        } else {
                            parseSiteMap(normalizeUrl(urlStats.getUrl(), SITEMAP), analizeMap);
                        }
                    } else {
                        parseSiteMap(normalizeUrl(urlStats.getUrl(), INVENTORY_SITEMAP), analizeMap);
                    }
                    getNew(urlStats.getUrl(), analizeMap);
                    mongoOperation.save(analizeMap);
                }
            } catch (IOException e) {
                System.out.println("Client exeption:  " + e.toString());
            }


        }
    }

    private void getNew(String url, AnalizeMap analizeMap) throws IOException {
        Document document = connect(url).ignoreContentType(true).ignoreHttpErrors(true).timeout(20000).get();
        List<Links> newMaps = new ArrayList<>();
        List<Links> usedMaps = new ArrayList<>();
        for (String key : DICTIONARY_NEW) {
            newMaps.add(new Links(key, document.select("a:contains(" + key + ")").toString()));
        }
        for (String key : DICTIONARY_USED) {
            usedMaps.add(new Links(key, document.select("a:contains(" + key + ")").toString()));
        }
        analizeMap.setExtractedNewLinks(newMaps);
        analizeMap.setExtractedUsedLinks(usedMaps);
    }

    private AnalizeMap parseSiteMap(String url, AnalizeMap analizeMap) {
        Document doc = null;
        try {
            doc = getPage(url);
        } catch (Exception exception) {
            doc = null;
            analizeMap.setErrors(exception.toString());
            System.out.println("Error connect to sitemap.xml:  " + exception.toString());
        }
        if (doc == null) {
            analizeMap.setHasPriority(false);
            analizeMap.setHasSitemap(false);
        } else {
            analizeMap.setTotalCount(doc.select("loc").size());
            Elements priority = doc.select("priority");
            if (priority != null) {

                System.out.println("Priority nodes count: " + priority.size());
                List<Element> list = IteratorUtils.toList((Iterators.filter(priority.iterator(), elementsFilter)));
                List<String> inventoryUrls = new ArrayList<String>(Collections2.transform(list, new Function<Element, String>() {
                    @Override
                    public String apply(Element element) {
                        Element urlNode = element.parent().child(0);
                        return ((TextNode) urlNode.childNode(0)).text();
                    }
                }));
                analizeMap.setInventories(inventoryUrls);
                analizeMap.setHasPriority(true);
                analizeMap.setHasSitemap(true);

            }
        }
        return analizeMap;

    }

    private Document getPage(String url) throws IOException {
        Document document = connect(url).timeout(5000).ignoreContentType(true).ignoreHttpErrors(true).get();
        int retryCount = 2;
        while (document == null && retryCount > 0) {
            document = connect(url).ignoreContentType(true).ignoreHttpErrors(true).get();
            retryCount--;
        }
        return document;
    }

    private String normalizeUrl(String url, String xml) {
        if (url.endsWith("/")) {
            return url + xml;
        } else {
            return url + "/" + xml;
        }
    }

    public List<UrlStats> getValidUrls() {
        return validUrls;
    }

    public void setValidUrls(List<UrlStats> validUrls) {
        this.validUrls = validUrls;
    }
}