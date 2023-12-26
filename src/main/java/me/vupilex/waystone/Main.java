package me.vupilex.waystone;

import me.vupilex.waystone.events.*;
import me.vupilex.waystone.events.onInterractGUI.*;
import me.vupilex.waystone.files.CustomConfig;
import me.vupilex.waystone.recipes.amulet;
import me.vupilex.waystone.recipes.waystone;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    private static Main instance;

    public HashMap<Player, String> playerForRenameWaystone = new HashMap<>();
    public HashMap<Player, Player> playerTeleportationRequest = new HashMap<>();
    // Premier player -> Personne qui fait la demande
    // Deuxième player -> Personne qui reçoit la demande

    @Override
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage("§f╔═════════════════════════════════════════════════════════════════════════════════╗");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3██╗    ██╗ █████╗ ██╗   ██╗███████╗████████╗ ██████╗ ███╗   ██╗███████╗     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3██║    ██║██╔══██╗╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔═══██╗████╗  ██║██╔════╝     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3██║ █╗ ██║███████║ ╚████╔╝ ███████╗   ██║   ██║   ██║██╔██╗ ██║█████╗       §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3██║███╗██║██╔══██║  ╚██╔╝  ╚════██║   ██║   ██║   ██║██║╚██╗██║██╔══╝       §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3╚███╔███╔╝██║  ██║   ██║   ███████║   ██║   ╚██████╔╝██║ ╚████║███████╗     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3 ╚══╝╚══╝ ╚═╝  ╚═╝   ╚═╝   ╚══════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═══╝╚══════╝     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║                             §6§lSTATUS : §r§bloading                                    §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f╚═════════════════════════════════════════════════════════════════════════════════╝");

        Bukkit.getServer().getConsoleSender().sendMessage("§l§8[§3WAYSTONE§8] :§r§b loading config file");
        CustomConfig.setupFiles();
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();
        saveDefaultConfig();
        saveConfig();
        Bukkit.getServer().getConsoleSender().sendMessage("§l§8[§3WAYSTONE§8] :§r§b config file loaded");

        Bukkit.getServer().getConsoleSender().sendMessage("§l§8[§3WAYSTONE§8] :§r§b loading events");
        getServer().getPluginManager().registerEvents(new onWaystonePlace(), this);
        getServer().getPluginManager().registerEvents(new onWaystoneBreak(), this);
        getServer().getPluginManager().registerEvents(new onWaystoneExplode(), this);
        getServer().getPluginManager().registerEvents(new onAmuletRightClick(), this);
        getServer().getPluginManager().registerEvents(new onWaystonRightClick(), this);
        getServer().getPluginManager().registerEvents(new onWaystonLeftClick(), this);
        getServer().getPluginManager().registerEvents(new onGUIWaystoneInterract(), this);
        getServer().getPluginManager().registerEvents(new fireworkDamage(), this);
        getServer().getPluginManager().registerEvents(new onBlockPlaceAtFrontOfWaystone(), this);
        getServer().getPluginManager().registerEvents(new onGUIObstruatedLocationInterract(), this);
        getServer().getPluginManager().registerEvents(new onGUIRemovePlayerAccess(), this);
        getServer().getPluginManager().registerEvents(new onPlayerRenameWaystone(), this);
        getServer().getPluginManager().registerEvents(new onPlayerCancelRenameWaystoneAction(), this);
        getServer().getPluginManager().registerEvents(new onGUIPlayerTeleportationInteract(), this);
        getServer().getPluginManager().registerEvents(new onGUIPlayerTeleportationRequestInteract(), this);
        getServer().getPluginManager().registerEvents(new onPlayerDisconnect(), this);
        getServer().getPluginManager().registerEvents(new onPlayerInteractArmorStand(), this);
        Bukkit.getServer().getConsoleSender().sendMessage("§l§8[§3WAYSTONE§8] :§r§b events loaded");

        Bukkit.getServer().getConsoleSender().sendMessage("§l§8[§3WAYSTONE§8] :§r§b loading recipes");
        getServer().addRecipe(amulet.amulet(this));
        getServer().addRecipe(waystone.waystone(this));
        Bukkit.getServer().getConsoleSender().sendMessage("§l§8[§3WAYSTONE§8] :§r§b recipes loaded");

        instance = this;
        Bukkit.getServer().getConsoleSender().sendMessage("§f╔═════════════════════════════════════════════════════════════════════════════════╗");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3██╗    ██╗ █████╗ ██╗   ██╗███████╗████████╗ ██████╗ ███╗   ██╗███████╗     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3██║    ██║██╔══██╗╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔═══██╗████╗  ██║██╔════╝     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3██║ █╗ ██║███████║ ╚████╔╝ ███████╗   ██║   ██║   ██║██╔██╗ ██║█████╗       §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3██║███╗██║██╔══██║  ╚██╔╝  ╚════██║   ██║   ██║   ██║██║╚██╗██║██╔══╝       §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3╚███╔███╔╝██║  ██║   ██║   ███████║   ██║   ╚██████╔╝██║ ╚████║███████╗     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║     §3 ╚══╝╚══╝ ╚═╝  ╚═╝   ╚═╝   ╚══════╝   ╚═╝    ╚═════╝ ╚═╝  ╚═══╝╚══════╝     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f║                             §6§lSTATUS : §r§bloaded                                     §f║");
        Bukkit.getServer().getConsoleSender().sendMessage("§f╚═════════════════════════════════════════════════════════════════════════════════╝");
    }

    public static Main getInstance(){
        return instance;
    }

}
