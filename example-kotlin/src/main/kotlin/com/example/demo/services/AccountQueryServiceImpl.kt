package com.example.demo.services

import com.example.demo.entities.AccountQueryEntity
import com.example.demo.entities.AccountRepository
import org.axonframework.eventhandling.DomainEventMessage
import java.util.stream.Collectors

import org.axonframework.eventsourcing.eventstore.EventStore
import org.springframework.stereotype.Service


@Service
class AccountQueryServiceImpl(private val eventStore: EventStore, private val accountRepository: AccountRepository) :
    AccountQueryService {
    override fun listEventsForAccount(accountNumber: String): List<Any> {
        return eventStore.readEvents(accountNumber).asStream().map { s: DomainEventMessage<*>? -> s!!.payload }
            .collect(Collectors.toList())
    }

    override fun getAccount(accountNumber: String): AccountQueryEntity? {
        return accountRepository.findById(accountNumber).orElseThrow()
    }
}