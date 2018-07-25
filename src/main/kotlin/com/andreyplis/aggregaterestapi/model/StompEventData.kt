package com.andreyplis.aggregaterestapi.model

data class StompEventData(
        val context: String,
        val event: String,
        val payload: String
)