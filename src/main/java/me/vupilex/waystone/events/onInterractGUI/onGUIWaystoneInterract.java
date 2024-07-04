package me.vupilex.waystone.events.onInterractGUI;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.animations.particlesCircle;
import me.vupilex.waystone.animations.tpAnimation;
import me.vupilex.waystone.files.CustomConfig;
import me.vupilex.waystone.guis.*;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class onGUIWaystoneInterract implements Listener {
    private static int killRunnable;

    private static float yUp = 0F;
    private static float yDown = 2F;
    @EventHandler
    public void onGUIwaystoneClick(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;

        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();
        if (inventoryName.equalsIgnoreCase(Main.getInstance().getConfigLang.getString("Gui-waystone-title"))){
            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
            String key = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "waystone-key"), PersistentDataType.STRING);
            if (key != null){
                Location waystoneLocation = CustomConfig.get().getLocation(key + ".location");
                Location teleportationLocationFirstBlock = new Location(waystoneLocation.getWorld(), waystoneLocation.getX() + 0.5, waystoneLocation.getY() + 1, waystoneLocation.getZ() + 0.5);
                Location teleportationLocationSecondBlock = new Location(waystoneLocation.getWorld(), waystoneLocation.getX() + 0.5, waystoneLocation.getY() + 2, waystoneLocation.getZ() + 0.5);
                player.closeInventory();
                if (teleportationLocationFirstBlock.getBlock().getType() == Material.AIR && teleportationLocationSecondBlock.getBlock().getType() == Material.AIR){
                    int expToLevel = player.getLevel();
                    int xpCost = Main.getInstance().getConfig().getInt("XP-level-teleportation-waystone-cost");
                    player.closeInventory();
                    if (expToLevel >= xpCost) {
                        player.setLevel(expToLevel - xpCost);
                    } else{
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.8f, 1.0f);
                        player.sendMessage(Main.getInstance().getConfigLang.getString("Not-enough-xp-level"));
                        return;
                    }
                    //Animation tp
                    if (Main.getInstance().getConfig().getBoolean("Enable-teleportation-animation") == true){
                        new tpAnimation(player, teleportationLocationFirstBlock).start();
                    }else{
                        player.sendMessage(Main.getInstance().getConfigLang.getString("Teleportation-delay").replaceAll("%delay%", String.valueOf(Main.getInstance().getConfig().getInt("Player-teleportation-delay"))));
                        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                player.teleport(teleportationLocationFirstBlock);
                                player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 0.5f, 1.0f);
                            }
                        }, Main.getInstance().getConfig().getInt("Player-teleportation-delay") * 20);
                    }


                }else{
                    player.openInventory(obstruatedLocationGUI.obstruatedLocationGUI(key));
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1f, 1.0f);
                }

            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Main.getInstance().getConfigLang.getString("Gui-waystone-remove-access-players"))) {
                String keyInWaystone = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                player.openInventory(removePlayerWaystoneAccessGUI.removePlayerWaystoneAccessGUI(keyInWaystone, 0));
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Main.getInstance().getConfigLang.getString("Gui-waystone-rename"))) {
                player.closeInventory();
                String keyInWaystone = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                if (Main.getInstance().playerForRenameWaystone.containsKey(player)){
                    Main.getInstance().playerForRenameWaystone.remove(player);
                }
                Main.getInstance().playerForRenameWaystone.put(player, keyInWaystone);
                player.sendMessage(Main.getInstance().getConfigLang.getString("Waystone-give-name"));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8f, 1.0f);
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Main.getInstance().getConfigLang.getString("Gui-next-page"))) {
                String keyInWaystone = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                Integer page = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "next-page"), PersistentDataType.INTEGER);
                player.openInventory(waystoneGUI.waystoneGUI(player, keyInWaystone, page));
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Main.getInstance().getConfigLang.getString("Gui-previous-page"))) {
                String keyInWaystone = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                Integer page = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "previous-page"), PersistentDataType.INTEGER);
                player.openInventory(waystoneGUI.waystoneGUI(player, keyInWaystone, page));
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Main.getInstance().getConfigLang.getString("Gui-waystone-teleport-players"))) {
                String keyInWaystone = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                player.openInventory(playerTeleportationGUI.playerTeleportationGUI(player, keyInWaystone, 0));
            } else if (itemMeta.getDisplayName().equals(Main.getInstance().getConfigLang.getString("Gui-waystone-player-teleportation-request"))) {
                String keyInWaystone = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                // A optimisé un peu inutile comme les femmes qui ont du pouvoir mdr
                if (keyInWaystone == null){
                    player.openInventory(playerTeleportationRequestGUI.playerTeleportationRequestGUI(player, null, 0));
                }else{
                    player.openInventory(playerTeleportationRequestGUI.playerTeleportationRequestGUI(player, keyInWaystone, 0));
                }
            }
                event.setCancelled(true);
        }
    }
}
