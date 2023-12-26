package me.vupilex.waystone.events.onInterractGUI;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.animations.particlesCircle;
import me.vupilex.waystone.files.CustomConfig;
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

public class onGUIObstruatedLocationInterract implements Listener {
    private static int killRunnable;

    private static float yUp = 0F;
    private static float yDown = 2F;
    @EventHandler
    public void onGUIwaystoneClick(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        String inventoryName = event.getView().getTitle();
        String acceptDisplayName = Main.getInstance().getConfig().getString("Gui-obstruated-location-accept");
        String denyDisplayName = Main.getInstance().getConfig().getString("Gui-obstruated-location-deny");
        if (inventoryName.equalsIgnoreCase(Main.getInstance().getConfig().getString("Gui-obstruated-location-title"))){
            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
            event.setCancelled(true);
            if (itemMeta.getDisplayName().equals(acceptDisplayName)) {
                String key = itemMeta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "waystone-key"), PersistentDataType.STRING);
                if (key == null) return;
                Location waystoneLocation = CustomConfig.get().getLocation(key + ".location");
                Location teleportationLocation = new Location(waystoneLocation.getWorld(), waystoneLocation.getX() + 0.5, waystoneLocation.getY() + 1, waystoneLocation.getZ() + 0.5);
                yUp = 0F;
                yDown = 2F;
                killRunnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        if (yUp > 2.0){
                            player.teleport(waystoneLocation);
                            Bukkit.getScheduler().cancelTask(killRunnable);
                            player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 0.5f, 1.0f);
                        }
                        particlesCircle.particlesCircleUp(player.getLocation(), 1F, yUp);
                        player.playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 0.8f, 1.0f);
                        particlesCircle.particlesCircleDown(teleportationLocation, 1F, yDown);

                        yDown -= 0.25;
                        yUp += 0.25;
                    }
                }, 0, 10);
            } else if (itemMeta.getDisplayName().equals(denyDisplayName)) {
                event.getView().close();
            }
        }
    }
}
