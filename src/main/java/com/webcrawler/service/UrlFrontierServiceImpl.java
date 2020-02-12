package com.webcrawler.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class UrlFrontierServiceImpl implements UrlFrontierService {
    ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void enqueue(String url) {
        queue.add(url);
    }

    @Override
    public String dequeue() {
        return queue.remove();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
