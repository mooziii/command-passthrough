package me.obsilabor.commandpassthrough.waterfall

import me.obsilabor.commandpassthrough.Common
import me.obsilabor.commandpassthrough.ConfigManager
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.ChatEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.event.EventHandler
import java.util.concurrent.TimeUnit

class Waterfall : Plugin(), Listener {
    override fun onEnable() {
        ConfigManager
        proxy.registerChannel(Common.CHANNEL)
        proxy.pluginManager.registerListener(this, this)
    }

    @EventHandler
    fun onCommand(event: ChatEvent) {
        if (!event.isCommand || event.sender !is ProxiedPlayer) {
            return
        }
        val player = event.sender as ProxiedPlayer
        val command = event.message.removePrefix("/")
        val keys = ConfigManager.config.commandMap.keys
        keys.forEach {
            if (command.startsWith(it, true)) {
                event.isCancelled = true
                player.connect(proxy.getServerInfo(ConfigManager.config.commandMap[it]))
                proxy.scheduler.schedule(this, {
                    player.server.sendData(Common.CHANNEL, command.encodeToByteArray())
                }, 500, TimeUnit.MILLISECONDS)
            }
        }
    }
}