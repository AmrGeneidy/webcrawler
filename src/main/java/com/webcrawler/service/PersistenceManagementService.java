package com.webcrawler.service;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.webcrawler.model.Page;

import java.util.concurrent.CompletableFuture;

public interface PersistenceManagementService {
    CompletableFuture<PutItemResult> save(Page page);
    CompletableFuture<String> getBody(String url);
}
