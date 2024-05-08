package me.betun.SculkDeath

import me.betun.sculkevent.commands.openGUICommand
import me.betun.sculkevent.listeners.AllPlayerInteracts
import org.bukkit.plugin.java.JavaPlugin

class SculkDeath : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        logger.info("Plugin cargado correctamente. Sculk Death 1.0")

        registerListeners()
    }

    private fun registerListeners(){
        server.pluginManager.registerEvents(AllPlayerInteracts(),this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Plugin desactiado correctamente. Sculk Death 1.0")
    }
}
