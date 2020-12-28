package com.example.demo.commands

class DebitMoneyCommand(id: String, val debitAmount: Double, val currency: String) :
    BaseCommand<String>(id)
