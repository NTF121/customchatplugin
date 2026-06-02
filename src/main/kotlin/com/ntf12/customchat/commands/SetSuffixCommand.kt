package com.ntf12.customchat.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import com.ntf12.customchat.managers.ChatManager

class SetSuffixCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Only players can set suffixes")
            return true
        }
        val player = sender as Player
        if (args.isEmpty()) {
            player.sendMessage("Usage: /setsuffix <text|clear>")
            return true
        }
        if (args.size == 1 && args[0].equals("clear", true)) {
            ChatManager.setSuffix(player.uniqueId, "")
            player.sendMessage("Suffix cleared")
            return true
        }
        val suffix = args.joinToString(" ")
        ChatManager.setSuffix(player.uniqueId, suffix)
        player.sendMessage("Suffix set to: $suffix")
        return true
    }
}
