package com.webcrawler.service;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.webcrawler.model.Page;
import com.webcrawler.repository.PageRepository;
import com.webcrawler.util.CompletablePromise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceManagementServiceImpl implements PersistenceManagementService {
    @Autowired
    private PageRepository pageRepository;

    @Override
    public CompletablePromise<PutItemResult> save(Page page) {
        return pageRepository.savePage(page);
    }
}
