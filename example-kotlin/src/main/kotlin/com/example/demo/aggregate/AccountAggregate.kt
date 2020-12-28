package com.example.demo.aggregate

import com.example.demo.commands.CreateAccountCommand
import com.example.demo.commands.CreditMoneyCommand
import com.example.demo.commands.DebitMoneyCommand
import com.example.demo.events.*
import org.axonframework.eventsourcing.EventSourcingHandler

import org.axonframework.modelling.command.AggregateLifecycle

import org.axonframework.commandhandling.CommandHandler

import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate


@Aggregate
class AccountAggregate {
    @AggregateIdentifier
    lateinit var id: String
    var accountBalance:Double = 0.0
    lateinit var currency: String
    lateinit var status: String

    constructor() {}

    @CommandHandler
    constructor(createAccountCommand: CreateAccountCommand) {
        AggregateLifecycle.apply(
            AccountCreatedEvent(
                createAccountCommand.id,
                createAccountCommand.accountBalance,
                createAccountCommand.currency
            )
        )
    }

    @EventSourcingHandler
    protected fun on(accountCreatedEvent: AccountCreatedEvent) {
        id = accountCreatedEvent.id
        accountBalance = accountCreatedEvent.accountBalance
        currency = accountCreatedEvent.currency
        status = java.lang.String.valueOf(Status.CREATED)
        AggregateLifecycle.apply(AccountActivatedEvent(id, Status.ACTIVATED))
    }

    @EventSourcingHandler
    protected fun on(accountActivatedEvent: AccountActivatedEvent) {
        status = java.lang.String.valueOf(accountActivatedEvent.status)
    }

    @CommandHandler
    protected fun on(creditMoneyCommand: CreditMoneyCommand) {
        AggregateLifecycle.apply(
            MoneyCreditedEvent(
                creditMoneyCommand.id,
                creditMoneyCommand.creditAmount,
                creditMoneyCommand.currency
            )
        )
    }

    @EventSourcingHandler
    protected fun on(moneyCreditedEvent: MoneyCreditedEvent) {
        if (accountBalance < 0 and (accountBalance + moneyCreditedEvent.creditAmount).compareTo(0.0)) {
            AggregateLifecycle.apply(AccountActivatedEvent(id, Status.ACTIVATED))
        }
        accountBalance += moneyCreditedEvent.creditAmount
    }

    @CommandHandler
    protected fun on(debitMoneyCommand: DebitMoneyCommand) {
        AggregateLifecycle.apply(
            MoneyDebitedEvent(
                debitMoneyCommand.id,
                debitMoneyCommand.debitAmount,
                debitMoneyCommand.currency
            )
        )
    }

    @EventSourcingHandler
    protected fun on(moneyDebitedEvent: MoneyDebitedEvent) {
        if (accountBalance >= 0 and (accountBalance - moneyDebitedEvent.debitAmount).compareTo(0)) {
            AggregateLifecycle.apply(AccountHeldEvent(id, Status.HOLD))
        }
        accountBalance -= moneyDebitedEvent.debitAmount
    }

    @EventSourcingHandler
    protected fun on(accountHeldEvent: AccountHeldEvent) {
        status = java.lang.String.valueOf(accountHeldEvent.status)
    }
}
