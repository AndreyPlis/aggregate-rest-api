package com.andreyplis.aggregaterestapi.model

data class StompEvent(
        val context: String,
        val event: String
)