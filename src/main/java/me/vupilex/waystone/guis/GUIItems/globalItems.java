package me.vupilex.waystone.guis.GUIItems;

import me.vupilex.waystone.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

public class globalItems {
    public static ItemStack darkGlassItem(){
        ItemStack darkGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta metaDarkGlass = darkGlass.getItemMeta();
        metaDarkGlass.setLore(null);
        metaDarkGlass.setDisplayName(null);
        darkGlass.setItemMeta(metaDarkGlass);

        return darkGlass;
    }

    public static ItemStack backButton(String key){
        ItemStack backHead = day.dean.skullcreator.SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/66d8eff4c673e0636907ea5c0b5ff4f64dc35c6aad9b797f1df663351b4c0814");
        SkullMeta metaBackHead = (SkullMeta) backHead.getItemMeta();
        metaBackHead.setLore(null);
        metaBackHead.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-back-button"));
        metaBackHead.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING, key);
        backHead.setItemMeta(metaBackHead);
        return backHead;
    }

    public static ItemStack nextPage(String key, int nextPageNumber){
        ItemStack nextPage = new ItemStack(Material.BOOK);
        ItemMeta metaNextPage = nextPage.getItemMeta();
        metaNextPage.setLore(null);
        metaNextPage.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-next-page"));
        metaNextPage.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
        metaNextPage.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaNextPage.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING, key);
        metaNextPage.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "next-page"), PersistentDataType.INTEGER, nextPageNumber);
        nextPage.setItemMeta(metaNextPage);

        return nextPage;
    }

    public static ItemStack previousPage(String key, int previousPageNumber){
        ItemStack nextPage = new ItemStack(Material.BOOK);
        ItemMeta metaNextPage = nextPage.getItemMeta();
        metaNextPage.setLore(null);
        metaNextPage.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-previous-page"));
        metaNextPage.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
        metaNextPage.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        metaNextPage.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING, key);
        metaNextPage.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "previous-page"), PersistentDataType.INTEGER, previousPageNumber);
        nextPage.setItemMeta(metaNextPage);

        return nextPage;
    }

    public static ItemStack playerTeleportationRequest(String key){
        ItemStack waystoneButton = new ItemStack(Material.BEACON);
        ItemMeta metaWaystoneButton = waystoneButton.getItemMeta();
        metaWaystoneButton.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-waystone-player-teleportation-request"));
        metaWaystoneButton.setLore(null);
        metaWaystoneButton.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
        metaWaystoneButton.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (key != null){
            metaWaystoneButton.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING, key);
        }        waystoneButton.setItemMeta(metaWaystoneButton);
        return waystoneButton;
    }

    public static ItemStack waystoneButton(String key){
        ItemStack waystoneButton = new ItemStack(Material.LODESTONE);
        ItemMeta metaWaystoneButton = waystoneButton.getItemMeta();
        metaWaystoneButton.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-waystone-button"));
        metaWaystoneButton.setLore(null);
        metaWaystoneButton.addEnchant(Enchantment.FIRE_ASPECT, 5, true);
        metaWaystoneButton.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (key != null){
            metaWaystoneButton.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING, key);
        }        waystoneButton.setItemMeta(metaWaystoneButton);
        return waystoneButton;
    }

    public static ItemStack teleportAtPlayers(Player player, String key){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwningPlayer(player);
        if (key != null){
            headMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "key-in-waystone"), PersistentDataType.STRING, key);
        }
        headMeta.setDisplayName(Main.getInstance().getConfigLang.getString("Gui-waystone-teleport-players"));
        head.setItemMeta(headMeta);

        return head;
    }
}
