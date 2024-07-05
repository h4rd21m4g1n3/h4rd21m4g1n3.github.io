package cz.cvut.fel.nss.transactions.finance_module.kafka;

import cz.cvut.fel.nss.transactions.finance_module.kafka.dto.TransactionInfoDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up Kafka consumer.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;
    /**
     * Creates a Kafka consumer factory for deserializing TransactionInfoDto messages.
     *
     * @return the Kafka consumer factory
     */
    @Bean
    public ConsumerFactory<String, TransactionInfoDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        var deserializer = new JsonDeserializer<>(TransactionInfoDto.class);
        deserializer.setTypeMapper(new DefaultJackson2JavaTypeMapper());
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }
    /**
     * Creates a Kafka listener container factory for handling TransactionInfoDto messages.
     *
     * @return the Kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionInfoDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransactionInfoDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}