package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.animations.particlesFireworks;
import me.vupilex.waystone.files.CustomConfig;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class onWaystonLeftClick implements Listener {
    @EventHandler
    public void onWaystoneLeftClick(PlayerInteractEvent event){
        if (event.getClickedBlock() == null) return;
        if (event.getItem() == null) return;
        if (event.getItem().getType() == Material.DIAMOND_PICKAXE) return;
        if (event.getItem().getType() == Material.IRON_PICKAXE) return;
        if (event.getItem().getType() == Material.NETHERITE_PICKAXE) return;
        if (event.getItem().getType() == Material.GOLDEN_PICKAXE) return;
        if (event.getItem().getType() == Material.WOODEN_PICKAXE) return;
        if (event.getItem().getType() == Material.STONE_PICKAXE) return;
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK){
            if (event.getClickedBlock().getType() == Material.LODESTONE){
                Block block = event.getClickedBlock();
                Location blockLocation = block.getLocation();
                Player player = event.getPlayer();
                for (String key : CustomConfig.get().getKeys(false)) {
                    Location configLocation = CustomConfig.get().getLocation(key + ".location");
                    //Pour une raison inconnu le configLocation.equals() ne foncitonne pas
                    if (configLocation.getX() == blockLocation.getX() && configLocation.getY() == blockLocation.getY() && configLocation.getZ() == blockLocation.getZ()) {

                        if (CustomConfig.get().getString(key + ".owner").equals(player.getUniqueId().toString())){
                            ItemStack playerItemInHand = event.getItem();
                            CustomConfig.get().set(key + ".item", playerItemInHand);
                            CustomConfig.save();
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.8f, 1.0f);
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> particlesFireworks.particlesFireworks(blockLocation, player, Main.getInstance()).detonate(), 5);
                            player.sendMessage(Main.getInstance().getConfig().getString("Change-waystone-icon"));
                        }else{
                            player.sendMessage(Main.getInstance().getConfig().getString("Not-Owner"));
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.8f, 1.0f);
                        }
                        break;
                    }
                }
            }
        }
    }
}
