package com.webcrawler.service;

import java.util.concurrent.CompletableFuture;

public interface UrlFetcherService {
    CompletableFuture<String> fetch(String url);
}
