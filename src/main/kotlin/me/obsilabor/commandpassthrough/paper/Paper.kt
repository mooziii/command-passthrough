package me.obsilabor.commandpassthrough.paper

import me.obsilabor.commandpassthrough.Common
import me.obsilabor.commandpassthrough.ConfigManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.messaging.PluginMessageListener

class Paper : JavaPlugin(), PluginMessageListener, Listener {
    override fun onEnable() {
        ConfigManager.config
        Bukkit.getMessenger().registerIncomingPluginChannel(this, Common.CHANNEL, this)
        Bukkit.getPluginManager().registerEvents(this, this)
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray?) {
        if (channel == Common.CHANNEL) {
            player.performCommand(message?.decodeToString() ?: return)
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val commands = ConfigManager.config.commandMap
        commands.keys.forEach {
            if (Bukkit.getCommandMap().getCommand(it) == null) {
                event.player.addAdditionalChatCompletions(listOf("/$it"))
            }
        }
    }
}