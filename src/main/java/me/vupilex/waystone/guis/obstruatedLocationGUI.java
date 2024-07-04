package me.vupilex.waystone.guis;

import me.vupilex.waystone.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class obstruatedLocationGUI {
    public static Inventory obstruatedLocationGUI(String WaystoneClickedKey){
        Inventory inventory = Bukkit.createInventory(null, 27, Main.getInstance().getConfigLang.getString("Gui-obstruated-location-title"));

        ItemStack accept = new ItemStack(Material.GREEN_WOOL);
        ItemMeta acceptMeta = accept.getItemMeta();
        acceptMeta.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-obstruated-location-accept"));
        acceptMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "waystone-key"), PersistentDataType.STRING, WaystoneClickedKey);
        acceptMeta.setLore(null);
        accept.setItemMeta(acceptMeta);

        ItemStack deny = new ItemStack(Material.RED_WOOL);
        ItemMeta denyMeta = deny.getItemMeta();
        denyMeta.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-obstruated-location-deny"));
        denyMeta.setLore(null);
        deny.setItemMeta(denyMeta);

        ItemStack explanation = new ItemStack(Material.NAME_TAG);
        ItemMeta explanationMeta = explanation.getItemMeta();
        explanationMeta.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-obstruated-location-explanation-title"));
        explanationMeta.setLore(Main.getInstance().getConfigLang.getStringList("Gui-obstruated-location-explanation-lore"));
        explanation.setItemMeta(explanationMeta);

        inventory.setItem(12, deny);
        inventory.setItem(4, explanation);
        inventory.setItem(14, accept);

        return inventory;
    }
}
