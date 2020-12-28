package com.example.demo.controller

import com.example.demo.entities.AccountQueryEntity
import com.example.demo.services.AccountQueryService
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = ["/bank-accounts"])
@Api(value = "Account Queries", description = "Account Query Events Endpoint", tags = arrayOf("Account Queries"))
class AccountQueryController(private val accountQueryService: AccountQueryService) {
    @GetMapping("/{accountNumber}")
    fun getAccount(@PathVariable(value = "accountNumber") accountNumber: String): AccountQueryEntity? {
        return accountQueryService.getAccount(accountNumber)
    }

    @GetMapping("/{accountNumber}/events")
    fun listEventsForAccount(@PathVariable(value = "accountNumber") accountNumber: String): List<Any?>? {
        return accountQueryService.listEventsForAccount(accountNumber)
    }
}