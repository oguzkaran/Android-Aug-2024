package org.csystem.app.random.text.client.viewmodel

import org.csystem.app.random.text.client.constant.DEFAULT_HOST
import org.csystem.app.random.text.client.constant.DEFAULT_PORT
import java.io.Serializable

data class ServerInfo(var host: String = DEFAULT_HOST, var port: String = DEFAULT_PORT) : Serializable {
    override fun toString() = "$host:$port"
}