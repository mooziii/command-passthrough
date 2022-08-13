package me.obsilabor.commandpassthrough

import kotlinx.serialization.json.Json

object Common {
    const val NAMESPACE = "command_passthrough"
    const val PATH = "main"
    const val CHANNEL = "$NAMESPACE:$PATH"

    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        isLenient = true
    }
}