package com.webcrawler.service.persistence;

import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.webcrawler.model.Page;
import com.webcrawler.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UrlPersistenceManagerImpl implements UrlPersistenceManager {
    @Autowired
    private UrlRepository urlRepository;

    @Override
    public CompletableFuture<PutItemResult> insert(String url) {
        return urlRepository.insert(url);
    }

    @Override
    public CompletableFuture<Boolean> contains(String url) {
        return urlRepository.contains(url);
    }
}
