package cz.cvut.fel.nss.transactions.transaction_module.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


/**
 * Configuration class for setting up Elasticsearch in the Spring context.
 */
@Configuration
@ComponentScan(basePackages = { "cz.cvut.fel.nss.transactions.transactionmodule.service" })
@EnableElasticsearchRepositories(basePackages = "cz.cvut.fel.nss.transactions.transactionmodule.repository.elastic")

public class ElasticsearchConfig {

//    @Bean
//    public RestHighLevelClient client() {
//        ClientConfiguration clientConfiguration
//                = ClientConfiguration.builder()
//                .connectedTo("localhost:9200")
//                .build();
//
//        return RestClients.create(clientConfiguration).rest();
//    }
//
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        return new ElasticsearchRestTemplate(client());
//    }


}