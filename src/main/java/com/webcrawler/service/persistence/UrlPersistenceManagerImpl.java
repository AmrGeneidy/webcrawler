package com.webcrawler.service.persistence;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.webcrawler.model.Page;
import com.webcrawler.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UrlPersistenceManagerImpl implements UrlPersistenceManager {
    @Autowired
    private PageRepository pageRepository;

    @Override
    public CompletableFuture<PutItemResult> save(Page page) {
        return pageRepository.savePage(page);
    }

    @Override
    public CompletableFuture<String> getBody(String url) {
        return pageRepository.getBody(url);
    }
}
