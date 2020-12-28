package com.example.demo.events

import com.example.demo.aggregate.Status


class AccountActivatedEvent(id: String, status: Status) : BaseEvent<String>(id) {
    val status: Status

    init {
        this.status = status
    }
}