package com.webcrawler.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlDedupServiceImpl implements UrlDedupService {
    Set<String> set = ConcurrentHashMap.newKeySet();

    @Override
    public boolean isDuplicate(String url) {
        return !set.add(url);
    }
}
