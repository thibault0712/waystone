package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.files.CustomConfig;
import net.minecraft.network.chat.PlayerChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class onPlayerRenameWaystone implements Listener {
    @EventHandler
    public void onPlayerRenameWaystone(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if (Main.getInstance().playerForRenameWaystone.containsKey(player)){
            String waystoneKey = Main.getInstance().playerForRenameWaystone.get(player);
            String waystoneNewName = event.getMessage();

            if (Main.getInstance().getConfig().getBoolean("Enable-hologram-title")){
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    Bukkit.getEntity(UUID.fromString(CustomConfig.get().getString(waystoneKey + ".armorStand"))).remove();
                    Location asLocation = CustomConfig.get().getLocation(waystoneKey + ".location").clone().add(0.5, 0, 0.5);
                    ArmorStand as = (ArmorStand) asLocation.getWorld().spawnEntity(asLocation, EntityType.ARMOR_STAND);
                    as.setVisible(false);
                    as.setGravity(false);
                    as.setCanPickupItems(false);
                    as.setCustomName(waystoneNewName.replace("&", "§"));
                    as.setCustomNameVisible(true);
                    CustomConfig.get().set(waystoneKey + ".armorStand", as.getUniqueId().toString());
                    CustomConfig.save();
                });
            }

            CustomConfig.get().set(waystoneKey + ".title", waystoneNewName.replaceAll("&.", ""));
            CustomConfig.save();
            Main.getInstance().playerForRenameWaystone.remove(player);
            player.sendMessage(Main.getInstance().getConfigLang.getString("Success-change-title"));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8f, 1.0f);
            event.setCancelled(true);
        }
    }
}
