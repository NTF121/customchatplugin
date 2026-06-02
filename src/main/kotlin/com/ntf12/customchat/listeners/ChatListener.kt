package com.ntf12.customchat.listeners

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.plugin.java.JavaPlugin
import io.papermc.paper.event.player.AsyncChatEvent
import com.ntf12.customchat.managers.ChatManager
import com.ntf12.customchat.utils.Utils
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

class ChatListener(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncChatEvent) {
        event.isCancelled = true

        val player = event.player
        val uuid = player.uniqueId
        val prefix = ChatManager.getPrefix(uuid)
        val suffix = ChatManager.getSuffix(uuid)
        val colorName = ChatManager.getColor(uuid)

        val original = event.originalMessage()

        val coloredMessage = if (colorName.isNotEmpty()) {
            try {
                val color = NamedTextColor.NAMES.value(colorName.lowercase())
                if (color != null) original.color(color) else original
            } catch (e: Exception) {
                original
            }
        } else original

        val prefixComp = if (prefix.isNotEmpty()) Utils.colorize(prefix).append(Component.text("") as Component) else Component.empty()
        val suffixComp = if (suffix.isNotEmpty()) Component.text("").append(Utils.colorize(suffix) as Component) else Component.empty()

        val nameComp = Component.text(player.name)

        val final = Component.empty()
            .append(prefixComp as Component)
            .append(nameComp as Component)
            .append(suffixComp as Component)
            .append(Component.text(":") as Component)
            .append(Component.text(" ") as Component)
            .append(coloredMessage as Component)

        plugin.server.scheduler.runTask(plugin, Runnable {
            plugin.server.onlinePlayers.forEach { it.sendMessage(final) }
        })
    }
}
