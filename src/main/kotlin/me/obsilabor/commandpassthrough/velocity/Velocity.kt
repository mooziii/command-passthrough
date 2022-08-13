package me.obsilabor.commandpassthrough.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.command.CommandExecuteEvent
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import me.obsilabor.commandpassthrough.Common
import me.obsilabor.commandpassthrough.ConfigManager
import java.util.concurrent.TimeUnit

@Plugin(
    id = "command_passthrough",
    name = "Command Passthrough",
    version = "1.0.0",
    authors = ["mooziii"]
)
class Velocity @Inject constructor(
    private val proxy: ProxyServer
) {
    private val channelId = MinecraftChannelIdentifier.create(Common.NAMESPACE, Common.PATH)

    init {
        ConfigManager
    }

    @Subscribe
    fun onProxyInit(event: ProxyInitializeEvent) {
        proxy.channelRegistrar.register(channelId)
    }

    @Subscribe
    fun onChat(event: CommandExecuteEvent) {
        if (event.commandSource is Player) {
            val player = event.commandSource as Player
            val command = event.command
            val keys = ConfigManager.config.commandMap.keys
            keys.forEach {
                if (command.startsWith(it, true)) {
                    player.createConnectionRequest(proxy.getServer(ConfigManager.config.commandMap[it]).orElseGet(null) ?: return).connect()
                    proxy.scheduler.buildTask(this) {
                        player.currentServer.ifPresent {server ->
                            server.sendPluginMessage(channelId, command.encodeToByteArray())
                        }
                    }.delay(500, TimeUnit.MILLISECONDS).schedule()
                }
            }
        }
    }
}