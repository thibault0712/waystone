package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.files.CustomConfig;
import me.vupilex.waystone.recipes.waystone;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.UUID;

public class onWaystoneBreak implements Listener {
    @EventHandler
    public void onWaystoneBreak(BlockBreakEvent event){

        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        if (event.getBlock().getType() == Material.LODESTONE && CustomConfig.get() != null){
            for (String key : CustomConfig.get().getKeys(false)) {
                Location configLocation = CustomConfig.get().getLocation(key + ".location");

                //Pour une raison inconnu le equals ne foncitonne pas
                if (configLocation.getX() == blockLocation.getX() && configLocation.getY() == blockLocation.getY() && configLocation.getZ() == blockLocation.getZ()){

                    if (CustomConfig.get().getString(key + ".owner").equals(player.getUniqueId().toString())) {
                        event.getBlock().getWorld().dropItemNaturally(blockLocation, waystone.waystoneItem(Main.getInstance()));
                        event.setDropItems(false);
                        if (CustomConfig.get().getString(key + ".armorStand") != null){
                            Bukkit.getEntity(UUID.fromString(CustomConfig.get().getString(key + ".armorStand"))).remove();
                        }
                        if (Main.getInstance().playerForRenameWaystone.containsKey(player)) {
                            if (Main.getInstance().playerForRenameWaystone.get(player).equalsIgnoreCase(key)){
                                Main.getInstance().playerForRenameWaystone.remove(player);
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
                                player.sendMessage(Main.getInstance().getConfig().getString("Waystone-cancel-rename"));
                            }
                        }
                        CustomConfig.get().set(key, null);
                        CustomConfig.save();
                    }else{
                        player.sendMessage(Main.getInstance().getConfig().getString("Not-Owner"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.8f, 1.0f);
                        event.setCancelled(true);
                        blockLocation.getWorld().spawnParticle(Particle.REDSTONE, blockLocation.add(0.5, 1.2, 0.5), 20, 0.0, 0.5, 0.0, new Particle.DustOptions(Color.GRAY, 1));
                    }
                    break;
                }
            }
        }
    }
}
