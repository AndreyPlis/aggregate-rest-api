package com.andreyplis.aggregaterestapi.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller




@Controller
class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    fun greeting(message: String): String {
        Thread.sleep(1000) // simulated delay
        return "Hello, $message"
    }

}