package com.webcrawler.service;

public interface UrlFrontierService {
    void enqueue(String url);
    String dequeue();
    boolean isEmpty();
}
