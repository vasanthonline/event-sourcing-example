package com.example.demo.commands

class CreateAccountCommand(id: String, val accountBalance: Double, val currency: String) :
    BaseCommand<String>(id)