package com.webcrawler.model;

public class Page {
    private String url, body;

    public Page(String url, String body) {
        this.url = url;
        this.body = body;
    }

    // needed for kafka serialization.
    public Page() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Page [body=" + body + ", url=" + url + "]";
    }
}
