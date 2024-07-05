package cz.cvut.fel.nss.transactions.transactionmodule.kafka;

import cz.cvut.fel.nss.transactions.transactionmodule.kafka.dto.TransactionInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Endpoint to publish a message to a Kafka topic.
     *
     * @param transactionInfoDto the transaction information to be sent
     * @return a response indicating the status of the message sending
     */
    @PostMapping("/publish")
    public ResponseEntity<String> sendMessageToKafkaTopic(@RequestBody TransactionInfoDto transactionInfoDto) {
        kafkaProducer.sendMessage(transactionInfoDto);
        return ResponseEntity.ok("Message sent to Kafka topic");
    }
}
