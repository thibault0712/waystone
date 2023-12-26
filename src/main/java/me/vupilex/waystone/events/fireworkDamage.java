package me.vupilex.waystone.events;

import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class fireworkDamage implements Listener {
    @EventHandler
    public void fireworkDamage(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Firework) {
            Firework fw = (Firework) event.getDamager();
            if (fw.hasMetadata("nodamage")) {
                event.setCancelled(true);
            }
        }
    }
}
