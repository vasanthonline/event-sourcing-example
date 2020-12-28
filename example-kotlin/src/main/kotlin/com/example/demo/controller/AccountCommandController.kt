package com.example.demo.controller

import com.example.demo.dto.AccountCreateDTO
import com.example.demo.dto.MoneyCreditDTO
import com.example.demo.dto.MoneyDebitDTO
import com.example.demo.services.AccountCommandService
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*

import java.util.concurrent.CompletableFuture


@RestController
@RequestMapping(value = ["/bank-accounts"])
@Api(value = "Account Commands", description = "Account Commands Related Endpoints", tags = arrayOf("Account Commands"))
class AccountCommandController(private val accountCommandService: AccountCommandService) {

    @PostMapping
    fun createAccount(@RequestBody accountCreateDTO: AccountCreateDTO): CompletableFuture<String> {
        return accountCommandService.createAccount(accountCreateDTO)
    }

    @PutMapping(value = ["/credits/{accountNumber}"])
    fun creditMoneyToAccount(
        @PathVariable(value = "accountNumber") accountNumber: String,
        @RequestBody moneyCreditDTO: MoneyCreditDTO
    ): CompletableFuture<String> {
        return accountCommandService.creditMoneyToAccount(accountNumber, moneyCreditDTO)
    }

    @PutMapping(value = ["/debits/{accountNumber}"])
    fun debitMoneyFromAccount(
        @PathVariable(value = "accountNumber") accountNumber: String,
        @RequestBody moneyDebitDTO: MoneyDebitDTO
    ): CompletableFuture<String> {
        return accountCommandService.debitMoneyFromAccount(accountNumber, moneyDebitDTO)
    }
}