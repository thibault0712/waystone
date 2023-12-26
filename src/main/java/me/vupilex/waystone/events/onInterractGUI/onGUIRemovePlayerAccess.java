package me.vupilex.waystone.events.onInterractGUI;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.animations.particlesCircle;
import me.vupilex.waystone.files.CustomConfig;
import me.vupilex.waystone.guis.removePlayerWaystoneAccessGUI;
import me.vupilex.waystone.guis.waystoneGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class onGUIRemovePlayerAccess implements Listener {
    private static int killRunnable;

    private static float yUp = 0F;
    private static float yDown = 2F;
    @EventHandler
    public void onGUIwaystoneClick(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();
        if (inventoryName.equalsIgnoreCase(Main.getInstance().getConfig().getString("Gui-waystone-remove-access-players-title"))){
            event.setCancelled(true);
            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
            String waystoneKey = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "waystone-key"), PersistentDataType.STRING);
            String playerUUID = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "player-uuid"), PersistentDataType.STRING);
            //Moyen d'améliorer la compréhension du code en vérifiant si c'est une tête
            if (waystoneKey != null && playerUUID != null && event.getClick().isLeftClick()){
                List<String> playersWaystone = CustomConfig.get().getStringList(waystoneKey + ".players");
                List<String> playersNameWaystone = CustomConfig.get().getStringList(waystoneKey + ".playersName");
                for (int i = 0; i < playersWaystone.size(); i++){
                    if (playerUUID.equalsIgnoreCase(playersWaystone.get(i))){
                        playersNameWaystone.remove(i);
                        playersWaystone.remove(i);
                        CustomConfig.get().set(waystoneKey + ".players", playersWaystone);
                        CustomConfig.get().set(waystoneKey + ".playersName", playersNameWaystone);
                        CustomConfig.save();
                        player.sendMessage(Main.getInstance().getConfig().getString("Success-expulse-player"));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1f, 1.0f);
                        player.openInventory(removePlayerWaystoneAccessGUI.removePlayerWaystoneAccessGUI(waystoneKey, 0));
                    }
                }
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Main.getInstance().getConfig().getString("Gui-back-button"))) {
                String key = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                player.openInventory(waystoneGUI.waystoneGUI(player, key, 0));
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Main.getInstance().getConfig().getString("Gui-next-page"))) {
                String keyInWaystone = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                Integer page = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "next-page"), PersistentDataType.INTEGER);
                player.openInventory(removePlayerWaystoneAccessGUI.removePlayerWaystoneAccessGUI(keyInWaystone, page));
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Main.getInstance().getConfig().getString("Gui-previous-page"))) {
                String keyInWaystone = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING);
                Integer page = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "previous-page"), PersistentDataType.INTEGER);
                player.openInventory(removePlayerWaystoneAccessGUI.removePlayerWaystoneAccessGUI(keyInWaystone, page));
            }
        }
    }
}
