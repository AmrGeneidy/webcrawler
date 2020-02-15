package com.webcrawler.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webcrawler.model.Page;
import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.events.PageSenderService;
import com.webcrawler.service.events.PageSenderServiceImpl;
import com.webcrawler.service.events.UrlSenderService;
import com.webcrawler.service.events.UrlSenderServiceImpl;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.Map;

/**
 * Configs for {@link UrlContainer} and {@link Page} kafka producers.
 */
@EnableAsync
@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }

    // TODO: Use beans as input instead of making a function call.
    @Bean
    public ProducerFactory<String, Page> pagesProducerFactory(ObjectMapper objectMapper) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new JsonSerializer(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, Page> pagesKafkaTemplate(ObjectMapper objectMapper) {
        return new KafkaTemplate<>(pagesProducerFactory(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, UrlContainer> urlsKafkaTemplate(ObjectMapper objectMapper) {
        return new KafkaTemplate<>(urlsProducerFactory(objectMapper));
    }

    @Bean
    public ProducerFactory<String, UrlContainer> urlsProducerFactory(ObjectMapper objectMapper) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new StringSerializer(), new JsonSerializer(objectMapper));
    }

    @Bean
    public PageSenderService pageSenderService() {
        return new PageSenderServiceImpl();
    }

    @Bean
    public UrlSenderService urlSenderService() {
        return new UrlSenderServiceImpl();
    }
}