package com.webcrawler.service.events;

import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.UrlProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class UrlReceiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UrlReceiver.class);


    @Autowired
    UrlProcessor urlProcessor;

    @KafkaListener(topics = "${kafka.urls.topic.json}",containerFactory = "urlKafkaListenerContainerFactory")
    public void receive(UrlContainer payload) {
        LOGGER.info("received payload='{}'", payload.toString());
        urlProcessor.process(payload.getUrl());
        LOGGER.info("processed payload='{}'", payload.toString());
    }
}
