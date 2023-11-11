package org.nutrition.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.nutrition.model.dtos.FoodView;
import org.nutrition.model.dtos.RecordCreation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaReceiverConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    public String servers;

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, RecordCreation> kafkaListenerCreation(ConsumerFactory<String, RecordCreation> consumerFactoryCreation) {
        ConcurrentKafkaListenerContainerFactory<String, RecordCreation> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryCreation);
        return factory;
    }
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Long> kafkaListenerDeletion(ConsumerFactory<String, Long> consumerFactoryDeletion) {
        ConcurrentKafkaListenerContainerFactory<String, Long> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryDeletion);
        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, FoodView> kafkaListenerStorageFilling(ConsumerFactory<String, FoodView> consumerFactoryDeletion) {
        ConcurrentKafkaListenerContainerFactory<String, FoodView> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryStorageFilling());
        return factory;
    }

    @Bean
    ConsumerFactory<String, RecordCreation> consumerFactoryCreation() {
        JsonDeserializer<RecordCreation> deserializer = new JsonDeserializer<>(RecordCreation.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_one");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);

    }

    @Bean
    ConsumerFactory<String, Long> consumerFactoryDeletion() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_two");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new LongDeserializer());

    }
    
    @Bean
    ConsumerFactory<String, FoodView> consumerFactoryStorageFilling() {
        JsonDeserializer<FoodView> deserializer = new JsonDeserializer<>(FoodView.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_storage_filling_1");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);

    }

}
