package cz.cvut.fel.nss.transactions.transaction_module.kafka;

import cz.cvut.fel.nss.transactions.transaction_module.kafka.dto.TransactionInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, TransactionInfoDto> kafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String topic;

    public KafkaProducer(KafkaTemplate<String, TransactionInfoDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends a message to the specified Kafka topic.
     *
     * @param transactionInfoDto the transaction information to be sent
     */
    public void sendMessage(TransactionInfoDto transactionInfoDto) {
        logger.info("Producing message: {}", transactionInfoDto);
        kafkaTemplate.send(topic, transactionInfoDto);
    }
}