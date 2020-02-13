package com.webcrawler.service;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.webcrawler.model.Page;
import com.webcrawler.util.CompletablePromise;

public interface PersistenceManagementService {
    CompletablePromise<PutItemResult> save(Page page);
}
