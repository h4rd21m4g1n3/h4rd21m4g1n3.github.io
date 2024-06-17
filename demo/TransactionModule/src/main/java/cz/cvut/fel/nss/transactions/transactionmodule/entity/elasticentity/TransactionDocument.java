package cz.cvut.fel.nss.transactions.transactionmodule.entity.elasticentity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

/**
 * Represents a transaction document stored in Elasticsearch.
 */
@Document(indexName = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDocument {

    @Id
    private String id;

    @Field(type = FieldType.Integer)
    private int userId;

    @Field(type = FieldType.Float)
    private float amount;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Date)
    private LocalDate transactionDate;
}
