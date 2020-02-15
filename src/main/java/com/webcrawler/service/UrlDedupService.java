package com.webcrawler.service;

public interface UrlDedupService {
    boolean visit(String url);
    boolean isDuplicate(String url);
}
