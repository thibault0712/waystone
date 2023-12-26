package me.vupilex.waystone.events;

import me.vupilex.waystone.files.CustomConfig;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class onWaystoneExplode implements Listener {
    @EventHandler
    public void onWaystoneExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            if (block.getType() == Material.LODESTONE) {
                Location blockLocation = block.getLocation();
                for (String key : CustomConfig.get().getKeys(false)) {
                    Location configLocation = CustomConfig.get().getLocation(key + ".location");

                    //Pour une raison inconnu le equals ne foncitonne pas
                    if (configLocation.getX() == blockLocation.getX() && configLocation.getY() == blockLocation.getY() && configLocation.getZ() == blockLocation.getZ()){
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }
}
