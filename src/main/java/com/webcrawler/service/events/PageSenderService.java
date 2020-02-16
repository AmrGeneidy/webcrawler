package com.webcrawler.service.events;

import com.webcrawler.model.Page;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

/**
 * Sends {@link Page}s to a data steam.
 */
public interface PageSenderService {
    CompletableFuture<SendResult<String, Page>> sendPage(Page page);
}
