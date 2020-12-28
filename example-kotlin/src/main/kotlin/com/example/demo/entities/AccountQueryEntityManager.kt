package com.example.demo.entities

import com.example.demo.aggregate.AccountAggregate
import com.example.demo.events.BaseEvent
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.eventsourcing.EventSourcingRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class AccountQueryEntityManager {
    @Autowired
    private val accountRepository: AccountRepository? = null

    @Autowired
    @Qualifier("accountAggregateEventSourcingRepository")
    private val accountAggregateEventSourcingRepository: EventSourcingRepository<AccountAggregate>? = null
    @EventSourcingHandler
    fun on(event: BaseEvent<String>) {
        persistAccount(buildQueryAccount(getAccountFromEvent(event)))
    }

    private fun getAccountFromEvent(event: BaseEvent<String>): AccountAggregate {
        return accountAggregateEventSourcingRepository!!.load(event.id.toString()).getWrappedAggregate()
            .getAggregateRoot()
    }

    private fun findExistingOrCreateQueryAccount(id: String): AccountQueryEntity {
        return accountRepository?.findById(id)?.orElse(AccountQueryEntity()) ?: AccountQueryEntity()
    }

    private fun buildQueryAccount(accountAggregate: AccountAggregate): AccountQueryEntity {
        val accountQueryEntity = findExistingOrCreateQueryAccount(accountAggregate.id)
        accountQueryEntity.setId(accountAggregate.id)
        accountQueryEntity.setAccountBalance(accountAggregate.accountBalance)
        accountQueryEntity.setCurrency(accountAggregate.currency)
        accountQueryEntity.setStatus(accountAggregate.status)
        return accountQueryEntity
    }

    private fun persistAccount(accountQueryEntity: AccountQueryEntity) {
        accountRepository?.save(accountQueryEntity)
    }
}