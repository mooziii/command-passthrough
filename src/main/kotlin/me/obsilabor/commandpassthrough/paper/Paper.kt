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

class Paper : JavaPlugin(), PluginMessageListener {
    override fun onEnable() {
        Bukkit.getMessenger().registerIncomingPluginChannel(this, Common.CHANNEL, this)
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray?) {
        if (channel == Common.CHANNEL) {
            player.performCommand(message?.decodeToString() ?: return)
        }
    }
}