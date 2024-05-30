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

    @PostMapping("/publish")
    public ResponseEntity<String> sendMessageToKafkaTopic(@RequestBody TransactionInfoDto transactionInfoDto) {
        kafkaProducer.sendMessage(transactionInfoDto);
        return ResponseEntity.ok("Message sent to Kafka topic");
    }
}
