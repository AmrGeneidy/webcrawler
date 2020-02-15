package com.webcrawler.service.events;

import com.webcrawler.model.UrlContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class UrlReceiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UrlReceiver.class);

    @KafkaListener(topics = "${kafka.urls.topic.json}",containerFactory = "urlKafkaListenerContainerFactory")
    public void receive(UrlContainer payload) {
        LOGGER.info("received payload='{}'", payload.toString());
    }
}
