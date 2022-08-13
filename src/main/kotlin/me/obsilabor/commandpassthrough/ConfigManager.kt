package me.obsilabor.commandpassthrough

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File

object ConfigManager {
    private val file = File("config/command-passthrough.json")

    init {
        if(!file.parentFile.exists()) {
            file.parentFile.mkdir()
        }
        if(!file.exists()) {
            file.createNewFile()
            file.writeText(Common.json.encodeToString(PassthroughConfig(hashMapOf("tictactoe" to "lobby"))))
        }
    }

    val config: PassthroughConfig
        get() = Common.json.decodeFromString(file.readText())
}