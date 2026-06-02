package com.ntf12.customchat.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import com.ntf12.customchat.managers.ChatManager

class SetPrefixCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can set prefixes")
            return true
        }
        val player = sender as Player
        if (args.isEmpty()) {
            player.sendMessage("Usage: /setprefix <text|clear>")
            return true
        }
        if (args.size == 1 && args[0].equals("clear", true)) {
            ChatManager.setPrefix(player.uniqueId, "")
            player.sendMessage("Prefix cleared")
            return true
        }
        val prefix = args.joinToString(" ")
        ChatManager.setPrefix(player.uniqueId, prefix)
        player.sendMessage("Prefix set to: $prefix")
        return true
    }
}
