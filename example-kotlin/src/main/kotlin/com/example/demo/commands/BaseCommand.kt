package com.example.demo.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier


open class BaseCommand<T>(@field:TargetAggregateIdentifier val id: T)
