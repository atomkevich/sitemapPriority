package com.epam;

import org.jsoup.select.Elements;

/**
 * author alina on 16.6.15.
 */
public class Links {
    private String key;
    private String elements;

    public Links() {
    }

    public Links(String key, String elements) {
        this.key = key;
        this.elements = elements;
    }

    public String getElements() {
        return elements;
    }

    public void setElements(String elements) {
        this.elements = elements;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
