package com.webcrawler.service;

import com.webcrawler.model.Page;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public interface PageSenderService {
    CompletableFuture<SendResult<String, Page>> sendPage(Page page);
}
