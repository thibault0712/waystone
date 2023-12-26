package me.vupilex.waystone.events;

import me.vupilex.waystone.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class onPlayerCancelRenameWaystoneAction implements Listener {
    @EventHandler
    public void onPlayerCancelRenameWaystoneAction(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking() && Main.getInstance().playerForRenameWaystone.containsKey(player)) {
            Main.getInstance().playerForRenameWaystone.remove(player);
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
            player.sendMessage(Main.getInstance().getConfig().getString("Waystone-cancel-rename"));
        }
    }
}
