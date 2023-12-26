package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.files.CustomConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.UUID;

public class onWaystonePlace implements Listener {
    @EventHandler
    public void onWaystonePlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        PersistentDataContainer dataContainer = event.getItemInHand().getItemMeta().getPersistentDataContainer();
        if (dataContainer.has(new NamespacedKey(Main.getInstance(), "waystone"), PersistentDataType.BYTE)){
            Location location = event.getBlock().getLocation();
            String waystoneID;
            do {
                waystoneID = String.valueOf(UUID.randomUUID());
            }while(CustomConfig.get().contains(waystoneID));
            ItemStack defaultItem = new ItemStack(Material.LODESTONE);
            if (Main.getInstance().getConfig().getBoolean("Enable-hologram-title")) {
                Location asLocation = location.clone().add(0.5, 0, 0.5);
                ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(asLocation, EntityType.ARMOR_STAND);
                as.setGravity(false);
                as.setCanPickupItems(false);
                as.setCustomName(Main.getInstance().getConfig().getString("Default-waystone-name"));
                as.setCustomNameVisible(true);
                as.setVisible(false);
                CustomConfig.get().set(waystoneID + ".armorStand", as.getUniqueId().toString());
            }

            CustomConfig.get().set(waystoneID + ".location", location);
            CustomConfig.get().set(waystoneID + ".owner", player.getUniqueId().toString());
            CustomConfig.get().set(waystoneID + ".ownerName", player.getName());
            CustomConfig.get().set(waystoneID + ".players", Arrays.asList(player.getUniqueId().toString()));
            CustomConfig.get().set(waystoneID + ".playersName", Arrays.asList(player.getDisplayName()));
            CustomConfig.get().set(waystoneID + ".item", defaultItem);
            CustomConfig.get().set(waystoneID + ".title", Main.getInstance().getConfig().getString("Default-waystone-name"));
            CustomConfig.save();
            player.sendMessage(Main.getInstance().getConfig().getString("Place-Waystone"));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8f, 1.0f);
        }
    }

}
