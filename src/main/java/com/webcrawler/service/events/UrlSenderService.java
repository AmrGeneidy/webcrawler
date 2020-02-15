package com.webcrawler.service.events;

import com.webcrawler.model.UrlContainer;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public interface UrlSenderService {
    CompletableFuture<SendResult<String, UrlContainer>> sendUrl(UrlContainer url);
}
