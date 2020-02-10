package com.webcrawler;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UrlDedupImpl implements UrlDedup {
    Set<String> set = ConcurrentHashMap.newKeySet();

    @Override
    public boolean isDuplicate(String url) {
        return !set.add(url);
    }
}
