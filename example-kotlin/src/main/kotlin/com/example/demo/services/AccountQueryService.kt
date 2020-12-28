package com.example.demo.services

import com.example.demo.entities.AccountQueryEntity

interface AccountQueryService {
    fun listEventsForAccount(accountNumber: String): List<Any>
    fun getAccount(accountNumber: String): AccountQueryEntity?
}