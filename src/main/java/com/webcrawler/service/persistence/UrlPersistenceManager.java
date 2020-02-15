package com.webcrawler.service.persistence;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.webcrawler.model.Page;

import java.util.concurrent.CompletableFuture;

public interface UrlPersistenceManager {
    CompletableFuture<PutItemResult> save(Page page);
    CompletableFuture<String> getBody(String url);
}
