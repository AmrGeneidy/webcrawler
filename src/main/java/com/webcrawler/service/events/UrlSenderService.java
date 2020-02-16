package com.webcrawler.service.events;

import com.webcrawler.model.UrlContainer;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

/**
 * Sends {@link UrlContainer} to kafka stream that acts as urlFrontier.
 */
public interface UrlSenderService {
    CompletableFuture<SendResult<String, UrlContainer>> sendUrl(UrlContainer url);
}
