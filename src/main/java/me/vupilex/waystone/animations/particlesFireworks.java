package me.vupilex.waystone.animations;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class particlesFireworks {
    public static Firework particlesFireworks(Location location, Player player, JavaPlugin javaPlugin){
        Firework firework = player.getWorld().spawn(location.add(0.5, 1, 0.5), Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        firework.setMetadata("nodamage", new FixedMetadataValue(javaPlugin, true));
        fireworkMeta.addEffect(FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL)
                .withColor(Color.RED)
                .withColor(Color.GREEN)
                .withColor(Color.BLUE)
                .withFade(Color.ORANGE)
                .flicker(true)
                .trail(true)
                .build());
        fireworkMeta.setPower(2);
        firework.setFireworkMeta(fireworkMeta);
        return firework;
    }
}
