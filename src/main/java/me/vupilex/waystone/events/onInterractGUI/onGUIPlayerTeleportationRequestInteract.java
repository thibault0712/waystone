package me.vupilex.waystone.events.onInterractGUI;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.animations.particlesCircle;
import me.vupilex.waystone.guis.playerTeleportationGUI;
import me.vupilex.waystone.guis.playerTeleportationRequestGUI;
import me.vupilex.waystone.guis.waystoneGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class onGUIPlayerTeleportationRequestInteract implements Listener {
    private static int killRunnable;
    private static float yUp = 0F;
    private static float yDown = 2F;
    @EventHandler
    public void onGUIPlayerTeleportationRequestInteract(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();
        if (inventoryName.equalsIgnoreCase(Main.getInstance().getConfig().getString("Gui-player-teleportation-request-title"))){
            ItemStack itemStack = event.getCurrentItem();
            event.setCancelled(true);
            if(itemStack.getType().equals(Material.PLAYER_HEAD) && !itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(Main.getInstance().getConfig().getString("Gui-waystone-teleport-players"))){
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                Player playerToTeleport = skullMeta.getOwningPlayer().getPlayer();
                if (playerToTeleport.isOnline()){
                    player.closeInventory();
                    if (Main.getInstance().getConfig().getBoolean("Enable-teleportation-animation") == true) {
                        yUp = 0F;
                        yDown = 2F;
                        killRunnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                if (yUp > 2.0) {
                                    playerToTeleport.teleport(player.getLocation());
                                    Bukkit.getScheduler().cancelTask(killRunnable);
                                    playerToTeleport.playSound(playerToTeleport.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 0.5f, 1.0f);
                                }
                                particlesCircle.particlesCircleUp(playerToTeleport.getLocation(), 1F, yUp);
                                playerToTeleport.playSound(playerToTeleport.getLocation(), Sound.ITEM_FIRECHARGE_USE, 0.8f, 1.0f);

                                yDown -= 0.25;
                                yUp += 0.25;
                            }
                        }, 0, 10);
                    }else{
                        playerToTeleport.sendMessage(Main.getInstance().getConfig().getString("Teleportation-delay").replaceAll("%delay%", String.valueOf(Main.getInstance().getConfig().getInt("Player-teleportation-delay"))));
                        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                playerToTeleport.teleport(player.getLocation());
                                playerToTeleport.playSound(playerToTeleport.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 0.5f, 1.0f);
                            }
                        }, Main.getInstance().getConfig().getInt("Player-teleportation-delay") * 20);
                    }
                    Main.getInstance().playerTeleportationRequest.remove(playerToTeleport, player);
                }
            }else {
                if (itemStack.getItemMeta().getDisplayName().equals(Main.getInstance().getConfig().getString("Gui-waystone-button"))) {
                    String key = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                    player.openInventory(waystoneGUI.waystoneGUI(player, key, 0));
                } else if (itemStack.getItemMeta().getDisplayName().equals(Main.getInstance().getConfig().getString("Gui-waystone-teleport-players"))) {
                    String key = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                    player.openInventory(playerTeleportationGUI.playerTeleportationGUI(player, key, 0));
                }
            }
        }
    }
}
