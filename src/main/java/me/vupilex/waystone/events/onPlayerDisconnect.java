package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class onPlayerDisconnect implements Listener {
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event){
        Player player = event.getPlayer();
        List<Player> keysToRemove = new ArrayList<>();
        for (Map.Entry<Player, Player> entry : Main.getInstance().playerTeleportationRequest.entrySet()) {
            if (entry.getValue().equals(player) || entry.getKey().equals(player)) {
                keysToRemove.add(entry.getKey());
            }
        }

        // Supprimez les entrées correspondantes dans la HashMap
        for (Player key : keysToRemove) {
            Main.getInstance().playerTeleportationRequest.remove(key);
        }
    }
}
