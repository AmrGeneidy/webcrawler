package com.webcrawler.service.events;

import com.webcrawler.model.Page;
import com.webcrawler.service.PageProcessor;
import com.webcrawler.service.UrlProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Pulls HTML pages from input data steam and process then by calling {@link PageProcessor#processPage(Page)}.
 */
public class PageReceiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PageReceiver.class);

    @Autowired
    PageProcessor pageProcessor;

    @KafkaListener(topics = "${kafka.pages.topic.json}",containerFactory = "pageKafkaListenerContainerFactory")
    public void receive(Page payload) {
        LOGGER.info("Page pulled ='{}'", payload.getUrl());
        pageProcessor.processPage(payload).join();
        LOGGER.info("Page Processed ='{}'", payload.getUrl());
    }
}
