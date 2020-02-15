package com.webcrawler.service.events;

import com.webcrawler.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class PageReceiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PageReceiver.class);

    @KafkaListener(topics = "${kafka.pages.topic.json}",containerFactory = "pageKafkaListenerContainerFactory")
    public void receive(Page payload) {
        LOGGER.info("received payload='{}'", payload.toString());
    }
}
