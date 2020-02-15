package com.webcrawler.service.events;

import com.webcrawler.model.Page;
import com.webcrawler.service.PageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class PageReceiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PageReceiver.class);

    @Autowired
    PageProcessor pageProcessor;

    @KafkaListener(topics = "${kafka.pages.topic.json}",containerFactory = "pageKafkaListenerContainerFactory")
    public void receive(Page payload) {
        LOGGER.info("Page ='{}'", payload.getUrl());
        pageProcessor.processPage(payload);
        LOGGER.info("Page Done ='{}'", payload.getUrl());
    }
}
