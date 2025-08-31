package org.csystem.app.random.text.client.viewmodel

import java.io.Serializable

data class ServerInfo(var host: String, var port: String) : Serializable {
    override fun toString() = "$host:$port"
}