package com.example.demo.events


class MoneyDebitedEvent(id: String, val debitAmount: Double, val currency: String) :
    BaseEvent<String>(id)
