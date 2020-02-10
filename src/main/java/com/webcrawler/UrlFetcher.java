package com.webcrawler;

import java.util.concurrent.CompletableFuture;

public interface UrlFetcher {
    CompletableFuture<String> fetch(String url);
}
