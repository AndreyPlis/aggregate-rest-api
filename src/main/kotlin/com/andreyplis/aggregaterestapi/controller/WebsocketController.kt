package com.andreyplis.aggregaterestapi.controller

import com.andreyplis.aggregaterestapi.model.StompEvent
import com.andreyplis.aggregaterestapi.model.StompEventData
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

    val listeners: MutableMap<String, DefaultContextEventListener<CallerController<CallerData>>> = LinkedHashMap()

    @MessageMapping("/subscribe")
    fun subscribe(message: StompEvent) {

        val context = serverService.getContextManager().get(message.context) ?: return
        context.getEventDefinition(message.event) ?: return

        val listener: DefaultContextEventListener<CallerController<CallerData>> = object : DefaultContextEventListener<CallerController<CallerData>>() {
            override fun handle(p0: Event?) {
                var payload = ""
                if (p0!!.data.recordCount > 0)
                    payload = p0.data.rec().toString()

                val ed = StompEventData(p0.context, p0.name, payload)
                template.convertAndSend("/topic/event", ed)
            }
        }

        context.addEventListener(message.event, listener)
        listeners["$message.context@$message.event"] = listener
    }

    @MessageMapping("/unsubscribe")
    fun unsubscribe(message: StompEvent) {

        if (!listeners.containsKey("$message.context@$message.event"))
            return

        val listener = listeners["$message.context@$message.event"]
        serverService.getContextManager().get(message.context).removeEventListener(message.event, listener)
        listeners.remove("$message.context@$message.event")
    }
}