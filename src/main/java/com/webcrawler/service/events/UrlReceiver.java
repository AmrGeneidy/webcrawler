package com.webcrawler.service.events;

import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.UrlProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Pulls urls from urlFrontier and visit them by calling {@link UrlProcessor#process(String)}.
 */
public class UrlReceiver {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(UrlReceiver.class);

    @Autowired
    UrlProcessor urlProcessor;

    @KafkaListener(topics = "${kafka.urls.topic.json}",containerFactory = "urlFrontierKafkaListenerContainerFactory")
    public void receive(UrlContainer payload) {
        LOGGER.info("received url='{}'", payload.toString());
        urlProcessor.process(payload.getUrl()).join();
        LOGGER.info("processed url='{}'", payload.toString());
    }
}
