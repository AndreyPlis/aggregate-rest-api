package com.andreyplis.aggregaterestapi.controller

import com.andreyplis.aggregaterestapi.service.ServerService
import com.tibbo.aggregate.common.context.CallerController
import com.tibbo.aggregate.common.context.CallerData
import com.tibbo.aggregate.common.context.DefaultContextEventListener
import com.tibbo.aggregate.common.data.Event
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller


@Controller
class WebsocketController(val serverService: ServerService, val template: SimpMessagingTemplate) {

    val listeners: MutableMap<String, DefaultContextEventListener<CallerController<CallerData>>> = LinkedHashMap()

    @MessageMapping("/subscribe")
    fun subscribe(message: String) {
        val listener: DefaultContextEventListener<CallerController<CallerData>> = object : DefaultContextEventListener<CallerController<CallerData>>() {
            override fun handle(p0: Event?) {

                if (p0!!.data.recordCount > 0)
                    template.convertAndSend("/topic/event", p0.data.rec().toString())
                else
                    template.convertAndSend("/topic/event", "")
            }
        }
        val (context, event) = parameters(message)

        serverService.getContextManager().get(context).addEventListener(event, listener)
        listeners["$context@$event"] = listener
    }

    @MessageMapping("/unsubscribe")
    fun unsubscribe(message: String) {

        val (context, event) = parameters(message)

        val listener = listeners["$context@$event"]
        serverService.getContextManager().get(context).removeEventListener(event, listener)
        listeners.remove("$context@$event")
    }

    fun parameters(message: String): Pair<String, String> {
        val parser = JSONParser()

        val json = parser.parse(message) as JSONObject

        val context = json["context"] as String

        val event = json["event"] as String
        return Pair(context, event)
    }

}