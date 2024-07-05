package cz.cvut.fel.nss.transactions.financemodule.kafka;

import cz.cvut.fel.nss.transactions.financemodule.kafka.dto.TransactionInfoDto;
import cz.cvut.fel.nss.transactions.financemodule.service.GoalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer component for processing transaction information messages.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {
    private final GoalService goalService;
    /**
     * Listens to Kafka messages on the specified topic and processes the transaction information.
     *
     * @param transactionInfo the transaction information received from Kafka
     */
    @KafkaListener(topics = "${message.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenGroupFoo(TransactionInfoDto transactionInfo) {
        log.info("Received Kafka message - TransactionInfoDto : {}", transactionInfo);
        goalService.updateGoalWithTransaction(transactionInfo);
    }
}