package com.webcrawler.service.persistence;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.util.concurrent.CompletableFuture;

/**
 * Persists visited urls. Used for de-duplicate.
 */
public interface UrlPersistenceManager {
    CompletableFuture<PutItemResult> insert(String url);
    CompletableFuture<Boolean> contains(String url);
}
