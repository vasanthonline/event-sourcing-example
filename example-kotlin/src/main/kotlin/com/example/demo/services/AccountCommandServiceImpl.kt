package com.example.demo.services

import com.example.demo.commands.DebitMoneyCommand
import com.example.demo.dto.MoneyDebitDTO
import java.util.concurrent.CompletableFuture
import com.example.demo.commands.CreditMoneyCommand
import com.example.demo.dto.MoneyCreditDTO
import java.util.UUID
import com.example.demo.commands.CreateAccountCommand
import com.example.demo.dto.AccountCreateDTO
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class AccountCommandServiceImpl(private val commandGateway: CommandGateway) : AccountCommandService {
    override fun createAccount(accountCreateDTO: AccountCreateDTO): CompletableFuture<String> {
        return commandGateway.send(
            CreateAccountCommand(
                UUID.randomUUID().toString(),
                accountCreateDTO.startingBalance,
                accountCreateDTO.currency
            )
        )
    }

    override fun creditMoneyToAccount(
        accountNumber: String,
        moneyCreditDTO: MoneyCreditDTO
    ): CompletableFuture<String> {
        return commandGateway.send(
            CreditMoneyCommand(
                accountNumber,
                moneyCreditDTO.creditAmount,
                moneyCreditDTO.currency
            )
        )
    }

    override fun debitMoneyFromAccount(
        accountNumber: String,
        moneyDebitDTO: MoneyDebitDTO
    ): CompletableFuture<String> {
        return commandGateway.send(
            DebitMoneyCommand(
                accountNumber,
                moneyDebitDTO.debitAmount,
                moneyDebitDTO.currency
            )
        )
    }
}