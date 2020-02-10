package com.webcrawler;

public interface UrlFrontier {
    void enqueue(String url);
    String dequeue();
    boolean isEmpty();
}
