package com.epam;

import java.util.List;
import java.util.Map;
import org.jsoup.select.Elements;

/**
 * Created by alina on 15.6.15.
 */
public class AnalizeMap {
    private String site;
    private Boolean hasSitemap;
    private Boolean hasPriority;
    private Boolean valid;
    private List<String> inventories;
    private Integer totalCount;
    private String errors;
    private List<Links> extractedNewLinks;
    private List<Links> extractedUsedLinks;

    public AnalizeMap() {
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Boolean getHasSitemap() {
        return hasSitemap;
    }

    public void setHasSitemap(Boolean hasSitemap) {
        this.hasSitemap = hasSitemap;
    }

    public Boolean getHasPriority() {
        return hasPriority;
    }

    public void setHasPriority(Boolean hasPriority) {
        this.hasPriority = hasPriority;
    }


    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<String> getInventories() {
        return inventories;
    }

    public void setInventories(List<String> inventories) {
        this.inventories = inventories;
    }



    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Links> getExtractedNewLinks() {
        return extractedNewLinks;
    }

    public void setExtractedNewLinks(List<Links> extractedNewLinks) {
        this.extractedNewLinks = extractedNewLinks;
    }

    public List<Links> getExtractedUsedLinks() {
        return extractedUsedLinks;
    }

    public void setExtractedUsedLinks(List<Links> extractedUsedLinks) {
        this.extractedUsedLinks = extractedUsedLinks;
    }
}
