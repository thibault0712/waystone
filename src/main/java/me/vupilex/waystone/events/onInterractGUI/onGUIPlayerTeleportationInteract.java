package me.vupilex.waystone.events.onInterractGUI;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.animations.particlesCircle;
import me.vupilex.waystone.files.CustomConfig;
import me.vupilex.waystone.guis.playerTeleportationRequestGUI;
import me.vupilex.waystone.guis.waystoneGUI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class onGUIPlayerTeleportationInteract implements Listener {
    @EventHandler
    public void onGUIPlayerTeleportationInteract(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();
        if (inventoryName.equalsIgnoreCase(Main.getInstance().getConfig().getString("Gui-player-teleportation-title"))){
            ItemStack itemStack = event.getCurrentItem();
            event.setCancelled(true);
            if(itemStack.getType().equals(Material.PLAYER_HEAD)){
                int expToLevel = player.getLevel();
                int xpCost = Main.getInstance().getConfig().getInt("XP-level-teleportation-between-players-cost");
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                Player playerReceiver = skullMeta.getOwningPlayer().getPlayer();
                //Vérifie si requête déjà envoyé et si déjà envoyé on return
                for (Map.Entry<Player, Player> map : Main.getInstance().playerTeleportationRequest.entrySet()) {
                    Player mapSender = map.getKey();
                    Player mapReceiver = map.getValue();
                    if (playerReceiver == mapReceiver && mapSender == player) {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.8f, 1.0f);
                        player.sendMessage(Main.getInstance().getConfig().getString("Request-already-sended"));
                        return;
                    }
                }
                if (expToLevel >= xpCost) {
                    player.setLevel(expToLevel - xpCost);
                } else{
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.8f, 1.0f);
                    player.sendMessage(Main.getInstance().getConfig().getString("Not-enough-xp-level"));
                    return;
                }
                Main.getInstance().playerTeleportationRequest.put(player, playerReceiver);
                player.sendMessage(Main.getInstance().getConfig().getString("Success-sended-request-teleportation"));
                playerReceiver.sendMessage(Main.getInstance().getConfig().getString("request-teleportation-receive").replace("%player%", player.getName()));
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8f, 1.0f);
                playerReceiver.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8f, 1.0f);
            }else {
                if (itemStack.getItemMeta().getDisplayName().equals(Main.getInstance().getConfig().getString("Gui-waystone-button"))) {
                    String key = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                        player.openInventory(waystoneGUI.waystoneGUI(player, key, 0));
                } else if (itemStack.getItemMeta().getDisplayName().equals(Main.getInstance().getConfig().getString("Gui-waystone-player-teleportation-request"))) {
                    String key = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                    player.openInventory(playerTeleportationRequestGUI.playerTeleportationRequestGUI(player, key, 0));
                }
            }
        }
    }
}
