package com.andreyplis.aggregaterestapi.controller

import com.andreyplis.aggregaterestapi.model.Context
import com.andreyplis.aggregaterestapi.service.ServerService
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest/v1")
class ServerController(val serverService: ServerService) {

    @GetMapping("/server")
    fun retrieveAllGoods(): List<Context> {
        return serverService.getAllContexts()
    }

    @GetMapping("/{path}")
    fun retrieveContext(@PathVariable path: String): ResponseEntity<Any> {
        val context = serverService.getContext(path)
        if (context.isPresent)
            return ResponseEntity.ok(context)
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/{path}/variables/{id}")
    fun retrieveContextVariable(@PathVariable path: String, @PathVariable id: String): ResponseEntity<Any> {
        val variable = serverService.getContextVariable(path, id)
        if (variable.isPresent)
            return ResponseEntity.ok(variable)
        return ResponseEntity.notFound().build()
    }
}