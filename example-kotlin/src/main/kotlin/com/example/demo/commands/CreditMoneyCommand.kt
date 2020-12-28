package com.example.demo.commands

class CreditMoneyCommand(id: String, val creditAmount: Double, val currency: String) :
    BaseCommand<String>(id)