package com.webcrawler.repository;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import java.util.concurrent.CompletableFuture;

public interface UrlRepository {
    CompletableFuture<PutItemResult> insert(String urlContainer);
    CompletableFuture<Boolean> contains(String url);
}
