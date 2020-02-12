package com.webcrawler.service;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UrlDedupServiceImpl implements UrlDedupService {
    Set<String> set = ConcurrentHashMap.newKeySet();

    @Override
    public boolean isDuplicate(String url) {
        return !set.add(url);
    }
}
