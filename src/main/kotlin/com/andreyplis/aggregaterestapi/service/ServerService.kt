package com.andreyplis.aggregaterestapi.service

import com.andreyplis.aggregaterestapi.model.Context
import java.util.*

interface ServerService {

    fun getAllContexts(): List<Context>

    fun getContext(path: String): Optional<Context>

    fun getContextVariable(path: String, id: String): Optional<String>
}