package me.obsilabor.commandpassthrough

import kotlinx.serialization.Serializable

@Serializable
data class PassthroughConfig(
    val commandMap: HashMap<String, String> // command to server
)