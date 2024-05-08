package me.betun.sculkevent.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.PotionEffectType
import java.util.*


class openGUICommand : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(sender !is Player) return false

        val inventory = Bukkit.createInventory(sender, 9,"\uF808\uEFF4")

        val skull = ItemStack(Material.PLAYER_HEAD) // Create a new ItemStack of the Player Head type.

        val skullMeta = skull.itemMeta as SkullMeta // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties

        val onlinePlayers = Bukkit.getOnlinePlayers()
        var invSlot = 0

        for (player in onlinePlayers){
            val effect = player!!.getPotionEffect( PotionEffectType.WEAKNESS)
            if(effect == null || effect.amplifier == 0){
                skullMeta.setOwningPlayer(Bukkit.getPlayer(player.name.toString())) // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).

                skullMeta.setDisplayName(player.name.toString())

                skull.setItemMeta(skullMeta) // Apply the modified meta to the initial created item

                inventory.setItem(invSlot, skull)
                invSlot++
            }
        }

        sender.openInventory(inventory)

        return false;
    }
}