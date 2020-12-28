package com.example.demo.events


class AccountCreatedEvent(id: String, val accountBalance: Double, val currency: String) :
    BaseEvent<String>(id)