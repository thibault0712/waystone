package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.animations.particlesFireworks;
import me.vupilex.waystone.files.CustomConfig;
import me.vupilex.waystone.guis.waystoneGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class onPlayerInteractArmorStand implements Listener {
@EventHandler
    public void onPlayerInteractArmorStand(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        if (event.getRightClicked().getType().equals(EntityType.ARMOR_STAND)){
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();
            String armorStandID = armorStand.getUniqueId().toString();
            Location armorStandLocation = event.getRightClicked().getLocation();

            for (String key : CustomConfig.get().getKeys(false)) {
                String armorStandConfigID = CustomConfig.get().getString(key + ".armorStand");
                //Pour une raison inconnu le configLocation.equals() ne foncitonne pas
                if (Objects.equals(armorStandID, armorStandConfigID)){
                    List<String> playersWaystone = CustomConfig.get().getStringList(key + ".players");
                    List<String> playersNameWaystone = CustomConfig.get().getStringList(key + ".playersName");

                    if (playersWaystone.contains(player.getUniqueId().toString())){
                        player.openInventory(waystoneGUI.waystoneGUI(player, key, 0));
                        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1.8f, 1.0f);
                        //Modification des pseudos qui ont changés, pas api mojang car on veut un serveur crack
                        for (int i = 0; i < playersWaystone.size(); i++) {
                            String playerUUID = playersWaystone.get(i);
                            Player playerData = Bukkit.getPlayer(UUID.fromString(playerUUID));
                            if (playerData != null) {
                                String playerName = player.getDisplayName();
                                if (!playerName.equalsIgnoreCase(playersNameWaystone.get(i))) {
                                    playersNameWaystone.set(i, playerName);
                                    CustomConfig.get().set(key + ".playersName", playersNameWaystone);
                                    CustomConfig.save();
                                }
                            }
                        }
                    }else{
                        playersWaystone.add(player.getUniqueId().toString());
                        playersNameWaystone.add(player.getName());
                        CustomConfig.get().set(key + ".players", playersWaystone);
                        CustomConfig.get().set(key + ".playersName", playersNameWaystone);
                        CustomConfig.save();
                        player.sendMessage(Objects.requireNonNull(Main.getInstance().getConfigLang.getString("Waystone-found")));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8f, 1.0f);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> particlesFireworks.particlesFireworks(armorStandLocation, player, Main.getInstance()).detonate(), 5);
                    }
                    break;
                }
            }
        }
    }
}
