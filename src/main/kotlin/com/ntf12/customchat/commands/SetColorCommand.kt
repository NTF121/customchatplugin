package com.ntf12.customchat.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import com.ntf12.customchat.managers.ChatManager
import net.kyori.adventure.text.format.NamedTextColor

class SetColorCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can set message color")
            return true
        }
        val player = sender as Player
        if (args.isEmpty()) {
            player.sendMessage("Usage: /setcolor <color|clear> — e.g. red, gold, aqua")
            return true
        }
        if (args.size == 1 && args[0].equals("clear", true)) {
            ChatManager.setColor(player.uniqueId, "")
            player.sendMessage("Message color cleared")
            return true
        }
        val colorName = args[0]
        try {
            val color = NamedTextColor.NAMES.value(colorName.lowercase())
            if (color != null) {
                ChatManager.setColor(player.uniqueId, colorName.uppercase())
                player.sendMessage("Message color set to: ${colorName.uppercase()}")
            } else {
                player.sendMessage("Unknown color: $colorName")
            }
        } catch (e: Exception) {
            player.sendMessage("Unknown color: $colorName")
        }
        return true
    }
}
