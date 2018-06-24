package com.andreyplis.aggregaterestapi.service.remote

import com.tibbo.aggregate.common.Log
import com.tibbo.aggregate.common.protocol.RemoteContextManager
import com.tibbo.aggregate.common.protocol.RemoteServer
import com.tibbo.aggregate.common.protocol.RemoteServerController
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class RemoteAggreGate {
    lateinit var contextManager: RemoteContextManager
    lateinit var rlc: RemoteServerController

    @PostConstruct
    fun init() {
        Log.start()

        // Provide correct server address/port and name/password of server user to log in as
        val rls = RemoteServer("localhost", RemoteServer.DEFAULT_PORT, "admin", "admin")

        // Creating server controller
        rlc = RemoteServerController(rls, true)

        // Connecting to the server
        rlc.connect()

        // Authentication/authorization
        rlc.login()

        // Getting context manager
        contextManager = rlc.contextManager

    }

    @PreDestroy
    fun destroy() {
        rlc.disconnect()
    }
}