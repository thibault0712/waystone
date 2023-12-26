package me.vupilex.waystone.guis.GUIItems;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.files.CustomConfig;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class bottomWaystoneItems {
    public static ItemStack renameItem(String key){
        ItemStack rename = new ItemStack(Material.SPRUCE_SIGN);
        ItemMeta metaRename = rename.getItemMeta();
        metaRename.setLore(null);
        metaRename.setDisplayName(Main.getInstance().getConfig().getString("Gui-waystone-rename"));
        metaRename.addEnchant(Enchantment.FIRE_ASPECT, 6, true);
        metaRename.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaRename.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING, key);
        rename.setItemMeta(metaRename);

        return rename;
    }

    public static ItemStack removePlayerAccessItem(String key){
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta metaBarrier = barrier.getItemMeta();
        metaBarrier.setDisplayName(Main.getInstance().getConfig().getString("Gui-waystone-remove-access-players"));
        metaBarrier.setLore(null);
        metaBarrier.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
        metaBarrier.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaBarrier.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING, key);

        barrier.setItemMeta(metaBarrier);
        return barrier;
    }

    public static ItemStack waystoneInformationItem(String WaystoneClickedKey){
        List<String> lore = new ArrayList<>();
        lore.add(Main.getInstance().getConfig().getString("Gui-waystone-owner-prefix") + CustomConfig.get().getString(WaystoneClickedKey + ".ownerName"));
        lore.add(Main.getInstance().getConfig().getString("Gui-waystone-players-list-prefix"));
        List<String> playerList = CustomConfig.get().getStringList(WaystoneClickedKey + ".playersName");
        for (String pseudo : playerList) {
            lore.add(Main.getInstance().getConfig().getString("Gui-waystone-player-prefix") + pseudo);
        }

        ItemStack waystoneInfo =  CustomConfig.get().getItemStack(WaystoneClickedKey + ".item");
        ItemMeta metaWaystoneInfo = waystoneInfo.getItemMeta();
        metaWaystoneInfo.setDisplayName("§2§l" + CustomConfig.get().getString(WaystoneClickedKey + ".title"));
        metaWaystoneInfo.setLore(lore);
        metaWaystoneInfo.addEnchant(Enchantment.ARROW_DAMAGE, 50, true);
        metaWaystoneInfo.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaWaystoneInfo.getPersistentDataContainer().remove(new NamespacedKey(Main.getInstance(), "waystone-key"));
        waystoneInfo.setItemMeta(metaWaystoneInfo);
        waystoneInfo.setAmount(1);
        return waystoneInfo;
    }
}
