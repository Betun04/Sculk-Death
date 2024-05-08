package me.betun.sculkevent.listeners

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Cow
import org.bukkit.entity.EntityType.WARDEN
import org.bukkit.entity.Goat
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.EquipmentSlot.*
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.Potion
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType
import java.util.*


class AllPlayerInteracts : Listener {

    private fun changeModelData(num: Int, item: ItemStack, title: String, armor: Boolean, damage: Boolean): ItemStack {
        val itemMeta = item.itemMeta
        itemMeta.setCustomModelData(num)
        itemMeta.displayName(Component.text(title))

        //Warden helmet
        if(armor && item == ItemStack(Material.NETHERITE_HELMET)){
            val modifierArmor = AttributeModifier(UUID.randomUUID(),"generic.armor",5.0,AttributeModifier.Operation.ADD_NUMBER,HEAD)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR,modifierArmor)
            val modifierThoug = AttributeModifier(UUID.randomUUID(),"generic.armor_toughness",5.0,AttributeModifier.Operation.ADD_NUMBER,HEAD)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,modifierThoug)
            val modifierKnock = AttributeModifier(UUID.randomUUID(),"generic.knockback_resistance",0.2,AttributeModifier.Operation.ADD_NUMBER,HEAD)
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,modifierKnock)
        }
        //Warden chestplate
        else if(armor && item == ItemStack(Material.NETHERITE_CHESTPLATE)){
            val modifierArmor = AttributeModifier(UUID.randomUUID(),"generic.armor",10.0,AttributeModifier.Operation.ADD_NUMBER,CHEST)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR,modifierArmor)
            val modifierThoug = AttributeModifier(UUID.randomUUID(),"generic.armor_toughness",5.0,AttributeModifier.Operation.ADD_NUMBER,CHEST)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,modifierThoug)
            val modifierKnock = AttributeModifier(UUID.randomUUID(),"generic.knockback_resistance",0.2,AttributeModifier.Operation.ADD_NUMBER,CHEST)
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,modifierKnock)
        }
        //Warden leggins
        else if(armor && item == ItemStack(Material.NETHERITE_LEGGINGS)){
            val modifierArmor = AttributeModifier(UUID.randomUUID(),"generic.armor",8.0,AttributeModifier.Operation.ADD_NUMBER,LEGS)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR,modifierArmor)
            val modifierThoug = AttributeModifier(UUID.randomUUID(),"generic.armor_toughness",5.0,AttributeModifier.Operation.ADD_NUMBER,LEGS)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,modifierThoug)
            val modifierKnock = AttributeModifier(UUID.randomUUID(),"generic.knockback_resistance",0.2,AttributeModifier.Operation.ADD_NUMBER,LEGS)
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,modifierKnock)
        }
        //Warden boots
        else if(armor && item == ItemStack(Material.NETHERITE_BOOTS)){
            val modifierArmor = AttributeModifier(UUID.randomUUID(),"generic.armor",5.0,AttributeModifier.Operation.ADD_NUMBER,FEET)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR,modifierArmor)
            val modifierThoug = AttributeModifier(UUID.randomUUID(),"generic.armor_toughness",5.0,AttributeModifier.Operation.ADD_NUMBER,FEET)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,modifierThoug)
            val modifierKnock = AttributeModifier(UUID.randomUUID(),"generic.knockback_resistance",0.2,AttributeModifier.Operation.ADD_NUMBER,FEET)
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,modifierKnock)
        }
        //Warden Axe
        else if(damage && item == ItemStack(Material.NETHERITE_AXE)){
            val modifierDamage = AttributeModifier(UUID.randomUUID(),"generic.attack_damage",12.0,AttributeModifier.Operation.ADD_NUMBER,HAND)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifierDamage)
            val modifierAtSpeed = AttributeModifier(UUID.randomUUID(),"generic.attack_speed",1.0,AttributeModifier.Operation.ADD_NUMBER,HAND)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,modifierAtSpeed)
        //Warden Sword
        }else if(damage && item == ItemStack(Material.NETHERITE_SWORD)){
            val modifierDamage = AttributeModifier(UUID.randomUUID(),"generic.attack_damage",10.0,AttributeModifier.Operation.ADD_NUMBER,HAND)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifierDamage)
            val modifierAtSpeed = AttributeModifier(UUID.randomUUID(),"generic.attack_speed",1.6,AttributeModifier.Operation.ADD_NUMBER,HAND)
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,modifierAtSpeed)
        }
        item.setItemMeta(itemMeta)
        val itemAct = item

        return itemAct
    }

    @EventHandler
    fun onClick(e: InventoryClickEvent) {

    //Toda la funciones de los totems
        //Totem de maldicion
        if (e.getView().getTitle() == "Maldecir") {
            val player = e.whoClicked
            val skullMeta = e.currentItem!!.itemMeta as SkullMeta
            val nombreJugador = skullMeta.owningPlayer!!.name
            val jugadorObjetivo = Bukkit.getPlayer(nombreJugador.toString())
            val effect = jugadorObjetivo!!.getPotionEffect(PotionEffectType.WEAKNESS)

            if (effect != null && effect.amplifier == 0) {
                jugadorObjetivo.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.DARKNESS,
                        PotionEffect.INFINITE_DURATION,
                        0,
                        false,
                        false
                    )
                )
            } else {
                jugadorObjetivo.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.WEAKNESS,
                        PotionEffect.INFINITE_DURATION,
                        0,
                        false,
                        false
                    )
                )
            }

            e.isCancelled = true

            val world = player.world

            world.playSound(player.location, Sound.ENTITY_WARDEN_SONIC_BOOM, 3.0F, 0.533F)
            world.playSound(jugadorObjetivo.location, Sound.ENTITY_WARDEN_SONIC_BOOM, 3.0F, 0.533F)

            e.getView().close()
            val onlinePlayers = Bukkit.getOnlinePlayers()

            for (actualPlayer in onlinePlayers) {
                actualPlayer.sendMessage("§b" + player.name + " ha dejado caer una §5maldición §bsobre " + jugadorObjetivo.name)
            }

            player.itemInHand.subtract(1)

        }
        //Totem de bendicion
        else if (e.getView().getTitle() == "Bendecir") {
            val player = e.whoClicked
            val skullMeta = e.currentItem!!.itemMeta as SkullMeta
            val nombreJugador = skullMeta.owningPlayer!!.name
            val jugadorObjetivo = Bukkit.getPlayer(nombreJugador.toString())
            val effect = jugadorObjetivo!!.getPotionEffect(PotionEffectType.HEALTH_BOOST)
            if (effect?.amplifier == 0 && jugadorObjetivo.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                jugadorObjetivo.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.HEALTH_BOOST,
                        PotionEffect.INFINITE_DURATION,
                        2,
                        false,
                        true
                    )
                )
                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.REGENERATION,
                        300,
                        0,
                        false,
                        true
                    )
                )
            } else {
                jugadorObjetivo.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.INCREASE_DAMAGE,
                        PotionEffect.INFINITE_DURATION,
                        0,
                        false,
                        true
                    )
                )
                player.addPotionEffect(
                    PotionEffect(
                        PotionEffectType.REGENERATION,
                        300,
                        60,
                        false,
                        true
                    )
                )
            }

            e.isCancelled = true

            val world = player.world

            world.playSound(player.location, Sound.BLOCK_BEACON_POWER_SELECT, 3.0F, 0.533F)
            world.playSound(jugadorObjetivo.location, Sound.BLOCK_BEACON_POWER_SELECT, 3.0F, 0.533F)

            e.getView().close()
            val onlinePlayers = Bukkit.getOnlinePlayers()

            for (actualPlayer in onlinePlayers) {
                actualPlayer.sendMessage("§b" + player.name + " ha usado una §abendición §ben " + jugadorObjetivo.name)
            }

            player.itemInHand.subtract(1)


        }
    //Mesa de crafteo especial
        if(e.view.title == "§f\uF001"){

            var maldCorrect = false
            var bendCorrect = false
            var tool = -1
            var armor = -1

            //Pocion de weakness
            val baseWeakPot = Potion(PotionType.WEAKNESS, 1)
            val oneWeakPot = baseWeakPot.toItemStack(1)
            //Pocion de fuerza
            val baseStrPot = Potion(PotionType.STRENGTH, 1)
            val oneStrPot = baseStrPot.toItemStack(1)

            //Corazon de warden (EchoShard CustomModelData:2)
            val wardenCora = ItemStack(Material.ECHO_SHARD)
            val wardenCoraMeta = wardenCora.itemMeta
            wardenCoraMeta.setCustomModelData(2)
            wardenCoraMeta.displayName(Component.text("§bCorazón de Warden"))
            wardenCora.setItemMeta(wardenCoraMeta)

        //Que solo sea correcto cuando hayan ciertos materiales en ciertos slots
            //Crafteo totem de maldicion
            if((e.inventory.getItem(0) == oneWeakPot && e.inventory.getItem(2) == oneWeakPot && e.inventory.getItem(6) == oneWeakPot && e.inventory.getItem(8) == oneWeakPot)
                && (e.inventory.getItem(1) == ItemStack(Material.NETHERITE_BLOCK) && e.inventory.getItem(7) == ItemStack(Material.NETHERITE_BLOCK))
                && (e.inventory.getItem(3) == wardenCora && e.inventory.getItem(5) == wardenCora)
                ){
                maldCorrect = true
            }
            //Crafteo totem de bendicion
            else if((e.inventory.getItem(0) == oneStrPot && e.inventory.getItem(2) == oneStrPot && e.inventory.getItem(6) == oneStrPot && e.inventory.getItem(8) == oneStrPot)
                && (e.inventory.getItem(1) == ItemStack(Material.NETHERITE_BLOCK) && e.inventory.getItem(7) == ItemStack(Material.NETHERITE_BLOCK))
                && (e.inventory.getItem(3) == wardenCora && e.inventory.getItem(5) == wardenCora)){
                bendCorrect = true
            }
            //Crafteo pico warden
            else if(e.inventory.contains(Material.NETHERITE_PICKAXE) && e.inventory.contains(wardenCora)){
                tool = 0
            }
            //Crafteo hacha warden
            else if(e.inventory.contains(Material.NETHERITE_AXE) && e.inventory.contains(wardenCora)){
                tool = 1
            }
            //Crafteo pala warden
            else if(e.inventory.contains(Material.NETHERITE_SHOVEL) && e.inventory.contains(wardenCora)){
                tool = 2
            }
            //Crafteo espada warden
            else if(e.inventory.contains(Material.NETHERITE_SWORD) && e.inventory.contains(wardenCora)){
                tool = 3
            }
            else if(e.inventory.contains(Material.NETHERITE_BOOTS) && e.inventory.contains(wardenCora)){
                armor = 0
            }
            else if(e.inventory.contains(Material.NETHERITE_LEGGINGS) && e.inventory.contains(wardenCora)){
                armor = 1
            }
            else if(e.inventory.contains(Material.NETHERITE_CHESTPLATE) && e.inventory.contains(wardenCora)){
                armor = 2
            }
            else if(e.inventory.contains(Material.NETHERITE_HELMET) && e.inventory.contains(wardenCora)){
                armor = 3
            }

            //Totem de maldicion
            val maldTotem = ItemStack(Material.TOTEM_OF_UNDYING)
            val maldTotemMeta = maldTotem.itemMeta
            maldTotemMeta.setCustomModelData(1)
            maldTotemMeta.displayName(Component.text("§4Totem de maldición"))
            maldTotem.setItemMeta(maldTotemMeta)

            //Totem de bendicion
            val bendTotem = ItemStack(Material.TOTEM_OF_UNDYING)
            val bendTotemMeta = bendTotem.itemMeta
            bendTotemMeta.setCustomModelData(2)
            bendTotemMeta.displayName(Component.text("§6Totem de bendición"))
            bendTotem.setItemMeta(bendTotemMeta)

            val world = e.whoClicked.world

            if(maldCorrect && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(maldTotem)
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(bendCorrect && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(bendTotem)
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(tool == 0 && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(changeModelData(2, ItemStack(Material.NETHERITE_PICKAXE),"§bPico de warden",false,false))
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(tool == 1 && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(changeModelData(2, ItemStack(Material.NETHERITE_AXE),"§bHacha de warden",false,true))
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(tool == 2 && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(changeModelData(2, ItemStack(Material.NETHERITE_SHOVEL),"§bPala de warden",false,false))
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(tool == 3 && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(changeModelData(2, ItemStack(Material.NETHERITE_SWORD),"§bEspada de warden",false,true))
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(armor == 0 && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(changeModelData(2, ItemStack(Material.NETHERITE_BOOTS),"§bBotas de warden",true,false))
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(armor == 1 && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(changeModelData(2, ItemStack(Material.NETHERITE_LEGGINGS),"§bGrebas de warden",true,false))
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(armor == 2 && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(changeModelData(2, ItemStack(Material.NETHERITE_CHESTPLATE),"§bPeto de warden",true,false))
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
            else if(armor == 3 && e.rawSlot == 4){
                e.whoClicked.inventory.addItem(changeModelData(2, ItemStack(Material.NETHERITE_HELMET),"§bCasco de warden",true,false))
                world.playSound(e.whoClicked.location, Sound.BLOCK_BEACON_ACTIVATE, 3.0F, 1.0F)
                e.inventory.clear()
                e.whoClicked.closeInventory()
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun stopMilking(e: PlayerInteractEntityEvent) {
        //Que los jugadores no puedan ordeñar vacas y/o cabras
        val p = e.player
        if ((e.rightClicked is Cow || e.rightClicked is Goat) && p.itemInHand.type == Material.BUCKET) {
            e.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerUse(e: PlayerInteractEvent) {
        val p = e.player

    //Que solo funcionen los totems cuando se les da click derecho al aire o a un bloque no funcional, ej: cofre, yunque, etc
        //Menu del Totem de maldición
        if (p.itemInHand.type == Material.TOTEM_OF_UNDYING && p.itemInHand.itemMeta.customModelData == 1 && e.action.isRightClick) {
            val inventory = Bukkit.createInventory(p, 54, "Maldecir")

            val skull = ItemStack(Material.PLAYER_HEAD) // Create a new ItemStack of the Player Head type.

            val skullMeta =
                skull.itemMeta as SkullMeta // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties

            val onlinePlayers = Bukkit.getOnlinePlayers()
            var invSlot = 0

            for (player in onlinePlayers) {
                val effect = player.getPotionEffect(PotionEffectType.WEAKNESS)
                val effect2 = player.getPotionEffect(PotionEffectType.SLOW_DIGGING)

                if ((effect == null || effect.amplifier == 0) && effect2 == null && !player.isOp) {
                    skullMeta.setOwningPlayer(Bukkit.getPlayer(player.name.toString())) // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).

                    skullMeta.setDisplayName(player.name.toString())

                    skull.setItemMeta(skullMeta) // Apply the modified meta to the initial created item

                    inventory.setItem(invSlot, skull)
                    invSlot++
                }
            }

            p.openInventory(inventory)
        }
        //Menu del Totem de bendición
        else if (p.itemInHand.type == Material.TOTEM_OF_UNDYING && p.itemInHand.itemMeta.customModelData == 2 && e.action.isRightClick) {
            val inventory = Bukkit.createInventory(p, 54, "Bendecir")

            val skull = ItemStack(Material.PLAYER_HEAD) // Create a new ItemStack of the Player Head type.

            val skullMeta =
                skull.itemMeta as SkullMeta // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties

            val onlinePlayers = Bukkit.getOnlinePlayers()
            var invSlot = 0

            for (player in onlinePlayers) {
                val effect = player.getPotionEffect(PotionEffectType.INCREASE_DAMAGE)
                val effect2 = player.getPotionEffect(PotionEffectType.HEALTH_BOOST)

                if ((effect == null || effect.amplifier == 0) && (effect2?.amplifier == 0 || effect2?.amplifier == 1 || effect2?.amplifier == 2 || effect2?.amplifier == null) && !player.isOp) {
                    skullMeta.setOwningPlayer(Bukkit.getPlayer(player.name.toString())) // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).

                    skullMeta.setDisplayName(player.name.toString())

                    skull.setItemMeta(skullMeta) // Apply the modified meta to the initial created item

                    inventory.setItem(invSlot, skull)
                    invSlot++
                }
            }

            p.openInventory(inventory)
        }

        //Abrir crafteo custom
        if((p.location.x > -12 && p.location.x < 12) && (p.location.y in 73.0..81.0) && (p.location.z > -12 && p.location.z < 12)) {
            val inventory = Bukkit.createInventory(p, InventoryType.DISPENSER,"§f\uF001")
            val panel = ItemStack(Material.GLASS_PANE)
            val panelMeta = panel.itemMeta
            panelMeta.displayName(Component.text("Click para craftear!"))
            inventory.setItem(4,panel)

            if (e.action.isRightClick && e.clickedBlock?.type == Material.NOTE_BLOCK) {
                p.openInventory(inventory)
                e.isCancelled = true
            }
        }

    }

    @EventHandler
    fun invClose(e: InventoryCloseEvent){
        //Cuando cierra la mesa de crafteo de los totems devuelve los items
        if(e.view.title == "§f\uF001"){
            for(slot in 0..8){
                val item = e.inventory.getItem(slot)
                if(item != null) {
                    item.amount = e.inventory.getItem(slot)!!.amount
                    e.player.inventory.addItem(item)
                }
            }
            e.inventory.clear()
        }

        val helm = e.player.inventory.getItem(HEAD)
        if (helm.getEnchantmentLevel(Enchantment.OXYGEN) == 3) {
            e.player.removePotionEffect(PotionEffectType.DARKNESS)
        }
        else if(helm.getEnchantmentLevel(Enchantment.OXYGEN) ==0 || helm.isEmpty){
            e.player.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS,PotionEffect.INFINITE_DURATION,0))
        }
    }

    @EventHandler
    fun onKill(e: EntityDeathEvent) {
        val world = e.entity.killer?.world
        val killed = e.entity
        val killer = e.entity.killer
        val wardenCora = ItemStack(Material.ECHO_SHARD)
        val wardenCoraMeta = wardenCora.itemMeta
        wardenCoraMeta.setCustomModelData(2)
        wardenCoraMeta.displayName(Component.text("§bCorazón de Warden"))
        wardenCora.setItemMeta(wardenCoraMeta)

        if(killed.type == WARDEN && killer is Player){
            world?.dropItemNaturally(killer.location,wardenCora)
        }
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent){
        val helm = e.player.inventory.getItem(HEAD)
        if (helm.getEnchantmentLevel(Enchantment.OXYGEN) == 3) {
            e.player.removePotionEffect(PotionEffectType.DARKNESS)
        }
        else if(helm.getEnchantmentLevel(Enchantment.OXYGEN) ==0 || helm.isEmpty){
            e.player.addPotionEffect(PotionEffect(PotionEffectType.DARKNESS,PotionEffect.INFINITE_DURATION,0))
        }
    }
}