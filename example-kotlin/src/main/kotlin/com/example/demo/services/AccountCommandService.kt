package com.example.demo.services

import com.example.demo.dto.AccountCreateDTO
import com.example.demo.dto.MoneyCreditDTO
import com.example.demo.dto.MoneyDebitDTO
import java.util.concurrent.CompletableFuture


interface AccountCommandService {
    fun createAccount(accountCreateDTO: AccountCreateDTO): CompletableFuture<String>
    fun creditMoneyToAccount(accountNumber: String, moneyCreditDTO: MoneyCreditDTO): CompletableFuture<String>
    fun debitMoneyFromAccount(accountNumber: String, moneyDebitDTO: MoneyDebitDTO): CompletableFuture<String>
}