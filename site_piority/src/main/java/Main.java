import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.jsoup.Jsoup.connect;

/**
 * Created by alina on 15.6.15.
 */
public class Main {
    private final static  Predicate<Element> elementsFilter = new Predicate<Element>() {
        @Override
        public boolean apply(Element element) {
            return "1.0".equals(((TextNode) element.childNode(0)).text());
        }
    };

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
        Query searchUserQuery = new Query(Criteria.where("status").is(200));

        // find the saved user again.
        List<UrlStats> validUrl = mongoOperation.find(searchUserQuery, UrlStats.class);
        HttpClient client = new HttpClient();
        for (UrlStats urlStats: validUrl) {
            System.out.println("start processing url: " + urlStats.getUrl());
            SiteMap siteMap = new SiteMap();
            siteMap.setSite(urlStats.getUrl());

            GetMethod method = new GetMethod(urlStats.getUrl());
            GetMethod siteMapPAge = new GetMethod(normalizeUrl(urlStats.getUrl()));
            if (client.executeMethod(method)== 200) {
                Document doc = null;
                try {
                    doc = connect(normalizeUrl(urlStats.getUrl())).ignoreContentType(true).ignoreHttpErrors(true).get();
                }catch (Exception exception){
                    doc = null;
                    System.out.println("Error connect to sitemap.xml:  " + exception.toString());
                }

                if (doc == null || (client.executeMethod(siteMapPAge) != 200)) {
                    siteMap.setHasPriority(false);
                    siteMap.setHasSitemap(false);
                } else {
                    Elements priority = doc.select("priority");
                    if (priority != null) {
                        System.out.println("Priority nodes count: " + priority.size());
                        List<Element> list = IteratorUtils.toList((Iterators.filter(priority.iterator(), elementsFilter)));
                        List<String> inventoryUrls = new ArrayList<String>(Collections2.transform(list, new Function<Element, String>() {
                            @Override
                            public String apply(Element element) {
                                Element urlNode = element.parent().child(0);
                                return ((TextNode)urlNode.childNode(0)).text();
                            }
                        }));
                        siteMap.setInventories(inventoryUrls);
                        siteMap.setHasPriority(true);
                        siteMap.setHasSitemap(true);

                    }
                }
                mongoOperation.save(siteMap);
            }


        }

    }


    private static String normalizeUrl(String url) {
        if (url.endsWith("/")) {
            return url + "sitemap.xml";
        } else {
            return url + "/sitemap.xml";
        }
    }
}
