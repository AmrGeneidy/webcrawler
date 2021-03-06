package com.webcrawler.model;

public class UrlContainer {
    private String url;

    public UrlContainer(String url) {
        this.url = url;
    }

    // needed for kafka serialization.
    public UrlContainer() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "[url=" + url + "]";
    }
}
