package me.vupilex.waystone.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private static File file;
    private static FileConfiguration fileConfiguration;

    public static void setupFiles(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Waystone").getDataFolder(), "data/data.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return fileConfiguration;
    }

    public static void save(){
        try{
            fileConfiguration.save(file);
        }catch (IOException e){
        }
    }

    public static void reLoad(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}
