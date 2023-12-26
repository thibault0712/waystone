package me.vupilex.waystone.animations;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class particlesCircle {
    public static void particlesCircleUp(Location location, float radius, float y) {
        for (int i = 0; i < 24; i++) { // Notez que nous commençons à partir de 0 pour obtenir une boucle complète
            double angle = i * Math.PI / 12;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);

            for (int a = 0; a != y/0.25; a++){
                Location particleLocation = location.clone().add(x, a*0.25, z);
                location.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 10, 0.0, 0.0, 0.0, new Particle.DustOptions(Color.PURPLE, 1));
            }
        }
    }

    public static void particlesCircleDown(Location location, float radius, float y ) {
        for (int i = 0; i < 24; i++) { // Notez que nous commençons à partir de 0 pour obtenir une boucle complète
            double angle = i * Math.PI / 12;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);

            for (int a = 8; a!= y/0.25; a--){
                Location particleLocation = location.clone().add(x, a*0.25, z);
                location.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 10, 0.0, 0.0, 0.0, new Particle.DustOptions(Color.PURPLE, 1));
            }
        }
    }
}
