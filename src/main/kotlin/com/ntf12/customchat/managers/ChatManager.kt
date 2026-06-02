package com.ntf12.customchat.managers

import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID
import net.kyori.adventure.text.format.NamedTextColor

object ChatManager {
    private lateinit var plugin: JavaPlugin
    private val prefixes = mutableMapOf<UUID, String>()
    private val suffixes = mutableMapOf<UUID, String>()
    private val colors = mutableMapOf<UUID, String>()

    fun initialize(pl: JavaPlugin) {
        plugin = pl
        loadAll()
    }

    private fun loadAll() {
        val cfg = plugin.config
        val section = cfg.getConfigurationSection("players") ?: return
        for (id in section.getKeys(false)) {
            try {
                val uuid = UUID.fromString(id)
                prefixes[uuid] = cfg.getString("players.$id.prefix", "") ?: ""
                suffixes[uuid] = cfg.getString("players.$id.suffix", "") ?: ""
                colors[uuid] = cfg.getString("players.$id.color", "") ?: ""
            } catch (t: Throwable) {
                plugin.logger.warning("Invalid player UUID in config: $id")
            }
        }
    }

    fun saveAll() {
        val cfg = plugin.config
        val section = cfg.createSection("players")
        for (entry in prefixes.keys) {
            val id = entry.toString()
            section.createSection(id)
            cfg.set("players.$id.prefix", prefixes[entry])
            cfg.set("players.$id.suffix", suffixes[entry])
            cfg.set("players.$id.color", colors[entry])
        }
        plugin.saveConfig()
    }

    fun setPrefix(uuid: UUID, prefix: String) {
        prefixes[uuid] = prefix
        plugin.config.set("players.$uuid.prefix", prefix)
        plugin.saveConfig()
    }

    fun setSuffix(uuid: UUID, suffix: String) {
        suffixes[uuid] = suffix
        plugin.config.set("players.$uuid.suffix", suffix)
        plugin.saveConfig()
    }

    fun setColor(uuid: UUID, colorName: String) {
        colors[uuid] = colorName
        plugin.config.set("players.$uuid.color", colorName)
        plugin.saveConfig()
    }

    fun getPrefix(uuid: UUID): String = prefixes[uuid] ?: ""
    fun getSuffix(uuid: UUID): String = suffixes[uuid] ?: ""
    fun getColor(uuid: UUID): String = colors[uuid] ?: ""

    fun parseColor(name: String): NamedTextColor? {
        return try {
            NamedTextColor.NAMES.value(name.lowercase())
        } catch (e: Exception) {
            null
        }
    }
}
