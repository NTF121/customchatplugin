package com.ntf12.customchat

import org.bukkit.plugin.java.JavaPlugin
import com.ntf12.customchat.managers.PluginManager
import com.ntf12.customchat.listeners.PlayerListener
import com.ntf12.customchat.listeners.ChatListener
import com.ntf12.customchat.managers.ChatManager

class customchatplugin : JavaPlugin() {
    
    override fun onEnable() {
        saveDefaultConfig()
        // Initialize managers
        PluginManager.initialize(this)
        ChatManager.initialize(this)
        
        // Register listeners
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(ChatListener(this), this)
        
        // Register commands
        getCommand("setprefix")?.setExecutor(com.ntf12.customchat.commands.SetPrefixCommand())
        getCommand("setsuffix")?.setExecutor(com.ntf12.customchat.commands.SetSuffixCommand())
        getCommand("setcolor")?.setExecutor(com.ntf12.customchat.commands.SetColorCommand())
        
        logger.info("customchatplugin has been enabled!")
    }

    override fun onDisable() {
        ChatManager.saveAll()
        logger.info("customchatplugin has been disabled!")
    }
}