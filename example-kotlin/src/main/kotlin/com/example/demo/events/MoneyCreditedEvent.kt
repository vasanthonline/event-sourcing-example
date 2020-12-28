package com.example.demo.events


class MoneyCreditedEvent(id: String, val creditAmount: Double, val currency: String) :
    BaseEvent<String>(id)