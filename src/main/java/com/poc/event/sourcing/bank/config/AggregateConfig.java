package com.poc.event.sourcing.bank.config;

import com.mongodb.MongoClient;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AggregateConfig {
    @Bean
    public EventStorageEngine eventStore(MongoClient client) {
        return new MongoEventStorageEngine(new DefaultMongoTemplate(client));
    }
}
