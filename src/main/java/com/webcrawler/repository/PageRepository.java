package com.webcrawler.repository;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.webcrawler.model.Page;
import com.webcrawler.util.CompletablePromise;

public interface PageRepository {
    CompletablePromise<PutItemResult> savePage(Page page);
}
