package com.andreyplis.aggregaterestapi.model

data class Context(
        val path: String,
        val name: String,
        val description: String,
        val children: List<Context> = ArrayList(),
        val variables: List<Variable> = ArrayList(),
        val functions: List<Function> = ArrayList(),
        val events: List<Event> = ArrayList()
)