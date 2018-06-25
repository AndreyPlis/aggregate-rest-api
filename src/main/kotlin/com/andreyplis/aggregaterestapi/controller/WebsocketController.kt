package com.andreyplis.aggregaterestapi.controller

import com.andreyplis.aggregaterestapi.service.ServerService
import com.tibbo.aggregate.common.context.CallerController
import com.tibbo.aggregate.common.context.CallerData
import com.tibbo.aggregate.common.context.DefaultContextEventListener
import com.tibbo.aggregate.common.data.Event
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller


@Controller
class WebsocketController(val serverService: ServerService, val template: SimpMessagingTemplate) {

    @MessageMapping("/subscribe")
    fun greeting(message: String) {
        val ab: DefaultContextEventListener<CallerController<CallerData>> = object : DefaultContextEventListener<CallerController<CallerData>>() {
            override fun handle(p0: Event?) {
                template.convertAndSend("/topic/event", "Hello")
            }
        }
        serverService.getContextManager().get("users.admin.models.test").addEventListener("test", ab)
    }

}