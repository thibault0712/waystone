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

import java.util.Arrays;

public class amulet{
    public static ItemStack amuletItem(JavaPlugin javaPlugin){
        ItemStack amulet = new ItemStack(Material.RECOVERY_COMPASS, 1);
        ItemMeta amuletMeta = amulet.getItemMeta();
        amuletMeta.setLore(Main.getInstance().getConfigLang.getStringList("Amulet-lore"));
        amuletMeta.setDisplayName(Main.getInstance().getConfigLang.getString("Amulet-name"));
        amuletMeta.addEnchant(Enchantment.ARROW_DAMAGE, 50, true);
        amuletMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        amuletMeta.getPersistentDataContainer().set(new NamespacedKey(javaPlugin, "amulet_of_teleportation"), PersistentDataType.BYTE, (byte) 1);
        amulet.setItemMeta(amuletMeta);
        return amulet;
    }

    public static ShapedRecipe amulet(JavaPlugin javaPlugin){
        NamespacedKey key = new NamespacedKey(javaPlugin, "amulet_of_teleportation");

        ShapedRecipe amuletRecipe = new ShapedRecipe(key, amuletItem(javaPlugin));
        amuletRecipe.shape("*%*","%B%","*%*");
        amuletRecipe.setIngredient('%', Material.IRON_INGOT);
        amuletRecipe.setIngredient('B', Material.ENDER_PEARL);
        return(amuletRecipe);
    }
}
