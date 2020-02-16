package com.webcrawler.service;

import java.util.concurrent.CompletableFuture;

/**
 * Asynchronously fetches URLs from World Wide Web.
 */
public interface UrlFetcherService {
    CompletableFuture<String> fetch(String url);
}
