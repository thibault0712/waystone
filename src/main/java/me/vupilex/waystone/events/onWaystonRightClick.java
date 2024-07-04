package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.animations.particlesFireworks;
import me.vupilex.waystone.files.CustomConfig;
import me.vupilex.waystone.guis.waystoneGUI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.UUID;


public class onWaystonRightClick implements Listener {
    @EventHandler
    public void onWaystoneRightClick(PlayerInteractEvent event){
        if (event.getClickedBlock() == null) return;
        if (CustomConfig.get() == null) return;
        if (event.getPlayer().isSneaking()) return;

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if(block.getType() == Material.LODESTONE && event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Location blockLocation = block.getLocation();
            for (String key : CustomConfig.get().getKeys(false)) {
                Location configLocation = CustomConfig.get().getLocation(key + ".location");
                //Pour une raison inconnu le configLocation.equals() ne foncitonne pas
                if (configLocation.getX() == blockLocation.getX() && configLocation.getY() == blockLocation.getY() && configLocation.getZ() == blockLocation.getZ()){
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
                        player.sendMessage(Main.getInstance().getConfigLang.getString("Waystone-found"));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8f, 1.0f);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> particlesFireworks.particlesFireworks(blockLocation, player, Main.getInstance()).detonate(), 5);
                    }
                    break;
                }
            }
        }
    }
}
