package me.vupilex.waystone.events;

import me.vupilex.waystone.files.CustomConfig;
import net.minecraft.server.commands.ReturnCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class onBlockPlaceAtFrontOfWaystone implements Listener {
    @EventHandler
    public void onBlockPlaceAtFrontOfWaystone(BlockPlaceEvent event){
        if (event.getPlayer().isSneaking()) return;
        if (event.getBlockAgainst().getType() != Material.LODESTONE) return;
        if (event.getBlockPlaced().getType() == Material.LODESTONE) return;

        Location blockLocation = event.getBlockAgainst().getLocation();
        for (String key : CustomConfig.get().getKeys(false)) {
            Location configLocation = CustomConfig.get().getLocation(key + ".location");
            //Pour une raison inconnu le equals ne foncitonne pas
            if (configLocation.getX() == blockLocation.getX() && configLocation.getY() == blockLocation.getY() && configLocation.getZ() == blockLocation.getZ()){
                event.setCancelled(true);
            }
        }
    }

}
