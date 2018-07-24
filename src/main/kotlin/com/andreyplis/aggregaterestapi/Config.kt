package com.andreyplis.aggregaterestapi

import com.tibbo.aggregate.common.protocol.RemoteServer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.net.InetAddress

@Component
@ConfigurationProperties(prefix = "aggregate-server")
class Config {
    var address: InetAddress = InetAddress.getLocalHost()
    var port: kotlin.Int = RemoteServer.DEFAULT_PORT
    var username: String = RemoteServer.DEFAULT_USERNAME
    var password: String = RemoteServer.DEFAULT_PASSWORD
}