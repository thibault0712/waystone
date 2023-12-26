package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.guis.waystoneGUI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.print.attribute.Attribute;

public class onAmuletRightClick implements Listener {
    @EventHandler
    public static void onAmuletRightClick(PlayerInteractEvent event){
        if (event.getItem() == null) return;

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();
        if (itemStack.getType() == Material.RECOVERY_COMPASS){
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
            if (dataContainer.has(new NamespacedKey(Main.getInstance(), "amulet_of_teleportation"), PersistentDataType.BYTE)){
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.8f, 1.0f);
                player.openInventory(waystoneGUI.waystoneGUI(player, null, 0));
            }
        }
    }
}
