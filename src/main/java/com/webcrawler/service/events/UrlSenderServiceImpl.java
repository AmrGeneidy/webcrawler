package com.webcrawler.service.events;

import com.webcrawler.model.UrlContainer;
import com.webcrawler.util.CompletablePromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CompletableFuture;

public class UrlSenderServiceImpl implements UrlSenderService {
    @Value("${kafka.urls.topic.json}")
    private String topic;
    @Autowired
    private KafkaTemplate<String, UrlContainer> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlSenderServiceImpl.class);

    @Override
    public CompletableFuture<SendResult<String, UrlContainer>> sendUrl(UrlContainer url) {
        ListenableFuture<SendResult<String, UrlContainer>> sendPageEvent = kafkaTemplate.send(topic, url);
        sendPageEvent.addCallback(new ListenableFutureCallback<SendResult<String, UrlContainer>>() {

            @Override
            public void onSuccess(final SendResult<String, UrlContainer> message) {
                LOGGER.info("sent message = " + message + " with offset= " + message.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                LOGGER.error("unable to send message= " + url.toString(), throwable);
            }
        });

        return new CompletablePromise<>(sendPageEvent);
    }
}
