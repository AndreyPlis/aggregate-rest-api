package com.andreyplis.aggregaterestapi.model

data class Event(
        val name: String,
        var description: String? = null
)