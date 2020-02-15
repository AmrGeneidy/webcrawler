package com.webcrawler.configuration;

import java.util.HashMap;
import java.util.Map;

import com.webcrawler.model.Page;
import com.webcrawler.model.UrlContainer;
import com.webcrawler.service.events.PageReceiver;
import com.webcrawler.service.events.UrlReceiver;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kafka cluster
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        // allows a pool of processes to divide the work of consuming and processing records
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "webcrawler");
        // automatically reset the offset to the earliest offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

    @Bean
    public ConsumerFactory<String, Page> pageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(Page.class));
    }

    @Bean(name="pageKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Page>> pageKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Page> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pageConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, UrlContainer> urlConsumerFactory() {
        return new DefaultKafkaConsumerFactory<String, UrlContainer>(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(UrlContainer.class));
    }

    @Bean(name="urlKafkaListenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UrlContainer>> urlKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UrlContainer> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(urlConsumerFactory());

        return factory;
    }

    @Bean
    public UrlReceiver urlReceiver() {
        return new UrlReceiver();
    }

    @Bean
    public PageReceiver pageReceiver() {
        return new PageReceiver();
    }
}
