package me.vupilex.waystone.guis;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.guis.GUIItems.bottomWaystoneItems;
import me.vupilex.waystone.guis.GUIItems.globalItems;
import me.vupilex.waystone.files.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class waystoneGUI {
    public static Inventory waystoneGUI(Player player, String WaystoneClickedKey, int page){
        Inventory inventory = Bukkit.createInventory(null, 54, Main.getInstance().getConfig().getString("Gui-waystone-title"));
        int slotPosition = 0;
        int keyPosition = 0;
        if (page > 0){
            inventory.setItem(36, globalItems.previousPage(WaystoneClickedKey, page - 1));
        }
        for (String key :  CustomConfig.get().getKeys(false)){
            if (page > 0 && keyPosition <= page*44){
                keyPosition++;
                continue;
            }
            if (CustomConfig.get().getStringList(key + ".players").contains(player.getUniqueId().toString()) && !key.equals(WaystoneClickedKey)){
                if (slotPosition == 44){
                    inventory.setItem(slotPosition, globalItems.nextPage(WaystoneClickedKey, page + 1));
                    break;
                }else {
                    UUID owner = UUID.fromString(CustomConfig.get().getString(key + ".owner"));
                    Location waystoneLocation = CustomConfig.get(). getLocation(key + ".location");
                    Player ownerPlayer = Bukkit.getPlayer(owner);
                    String ownerName;
                    if (ownerPlayer == null){
                        ownerName = CustomConfig.get().getString(key + ".ownerName");
                    }else{
                        ownerName = ownerPlayer.getName();
                    }
                    ItemStack waystoneItem = CustomConfig.get().getItemStack(key + ".item");
                    ItemMeta waystoneMeta = waystoneItem.getItemMeta();
                    waystoneMeta.setDisplayName("§6§l" + CustomConfig.get().getString(key + ".title"));
                    waystoneMeta.setLore(Arrays.asList(
                            Main.getInstance().getConfig().getString("Gui-waystone-owner-prefix") + ownerName,
                            Main.getInstance().getConfig().getString("Gui-waystone-location-prefix") + "x : " + waystoneLocation.getX() + ", y : " + waystoneLocation.getY() + ", z : " + waystoneLocation.getZ()));
                    waystoneMeta.addEnchant(Enchantment.ARROW_DAMAGE, 50, true);
                    waystoneMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    waystoneMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "waystone-key"), PersistentDataType.STRING, key);
                    waystoneItem.setItemMeta(waystoneMeta);
                    waystoneItem.setAmount(1);
                    inventory.addItem(waystoneItem);
                }
                slotPosition++;
            }
        }

        for (int i = 45; i <= 53; i++) {
            if (i == 49 && WaystoneClickedKey != null){
                inventory.setItem(i, bottomWaystoneItems.waystoneInformationItem(WaystoneClickedKey));
            }else if(i == 45 && Main.getInstance().getConfig().getBoolean("Enable-the-teleportation-between-players") == true){
                inventory.setItem(i, globalItems.teleportAtPlayers(player, WaystoneClickedKey));
            }else if(i == 46 && Main.getInstance().getConfig().getBoolean("Enable-the-teleportation-between-players") == true){
                inventory.setItem(i, globalItems.playerTeleportationRequest(WaystoneClickedKey));
            }else if(i == 48 && WaystoneClickedKey != null && player.getUniqueId().toString().equals(CustomConfig.get().getString(WaystoneClickedKey + ".owner")) && WaystoneClickedKey != null){
                inventory.setItem(i, bottomWaystoneItems.renameItem(WaystoneClickedKey));
            }else if(i == 50 && WaystoneClickedKey != null && player.getUniqueId().toString().equals(CustomConfig.get().getString(WaystoneClickedKey + ".owner")) && WaystoneClickedKey != null){
                inventory.setItem(i, bottomWaystoneItems.removePlayerAccessItem(WaystoneClickedKey));
            }else {
                inventory.setItem(i, globalItems.darkGlassItem());
            }
        }
        return inventory;
    }
}
