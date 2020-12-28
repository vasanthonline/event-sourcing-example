package com.example.demo.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class AccountQueryEntity {

    @Id
    private var id: String = ""

    private var accountBalance = 0.0

    private var currency: String = ""

    private var status: String = ""

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getAccountBalance(): Double {
        return accountBalance
    }

    fun setAccountBalance(accountBalance: Double) {
        this.accountBalance = accountBalance
    }

    fun getCurrency(): String {
        return currency
    }

    fun setCurrency(currency: String) {
        this.currency = currency
    }

    fun getStatus(): String {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    override fun toString(): String {
        return "AccountQueryEntity{" +
                "id='" + id + '\'' +
                ", accountBalance=" + accountBalance +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                '}'
    }



}