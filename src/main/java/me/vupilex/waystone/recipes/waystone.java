package me.vupilex.waystone.recipes;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.files.CustomConfig;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class waystone {
    public static ItemStack waystoneItem(JavaPlugin javaPlugin){
        ItemStack waystone = new ItemStack(Material.LODESTONE, 1);
        ItemMeta waystoneMeta = waystone.getItemMeta();
        waystoneMeta.addEnchant(Enchantment.ARROW_DAMAGE, 50, true);
        waystoneMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        waystoneMeta.setLore(javaPlugin.getConfig().getStringList("Waystone-lore"));
        waystoneMeta.setDisplayName(javaPlugin.getConfig().getString("Waystone-name"));
        waystoneMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "waystone"), PersistentDataType.BYTE, (byte) 1);
        waystone.setItemMeta(waystoneMeta);
        return waystone;
    }

    public static ShapedRecipe waystone(JavaPlugin javaPlugin){
        NamespacedKey key = new NamespacedKey(javaPlugin, "waystone");
        ShapedRecipe waystoneRecipe = new ShapedRecipe(key, waystoneItem(javaPlugin));
        waystoneRecipe.shape("***","%B%","***");
        waystoneRecipe.setIngredient('B', amulet.amuletItem(javaPlugin).getType());
        waystoneRecipe.setIngredient('%', Material.CHISELED_STONE_BRICKS);
        waystoneRecipe.setIngredient('*', Material.STONE_BRICKS);
        return(waystoneRecipe);
    }
}
