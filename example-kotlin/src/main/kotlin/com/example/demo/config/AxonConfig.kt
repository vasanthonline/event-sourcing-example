package com.example.demo.config

import com.example.demo.aggregate.AccountAggregate
import org.axonframework.eventsourcing.EventSourcingRepository

import org.axonframework.eventsourcing.eventstore.EventStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AxonConfig {
    @Bean
    fun accountAggregateEventSourcingRepository(eventStore: EventStore?): EventSourcingRepository<AccountAggregate> {
        return EventSourcingRepository.builder(
            AccountAggregate::class.java
        ).eventStore(eventStore).build()
    }
}