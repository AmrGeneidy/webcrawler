package com.webcrawler.repository;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.webcrawler.model.Page;

import java.util.concurrent.CompletableFuture;

public interface PageRepository {
    CompletableFuture<PutItemResult> savePage(Page page);
    CompletableFuture<String> getBody(String url);
}
