package com.webcrawler.service.events;

import com.webcrawler.model.Page;
import com.webcrawler.util.CompletablePromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CompletableFuture;

@Service
@Primary
public class PageSenderServiceImpl implements PageSenderService {
    @Value("${kafka.pages.topic.json}")
    private String topic;
    @Autowired
    private KafkaTemplate<String, Page> kafkaTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(PageSenderServiceImpl.class);

    @Async
    public CompletableFuture<SendResult<String, Page>> sendPage(Page page) {
        ListenableFuture<SendResult<String, Page>> sendPageEvent = kafkaTemplate.send(topic, page);
        sendPageEvent.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(final SendResult<String, Page> message) {
                LOGGER.info("sent message = " + message.getProducerRecord().value().getUrl() + " with offset= " + message.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                LOGGER.error("unable to send message= " + page.toString(), throwable);
            }
        });

        return new CompletablePromise<>(sendPageEvent);
    }
}
