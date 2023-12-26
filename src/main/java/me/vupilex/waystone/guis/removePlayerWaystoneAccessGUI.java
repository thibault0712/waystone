package me.vupilex.waystone.guis;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;
import me.vupilex.waystone.Main;
import me.vupilex.waystone.files.CustomConfig;
import me.vupilex.waystone.guis.GUIItems.bottomWaystoneItems;
import me.vupilex.waystone.guis.GUIItems.globalItems;
import org.bukkit.Bukkit;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;


public class removePlayerWaystoneAccessGUI {
    public static Inventory removePlayerWaystoneAccessGUI(String key, int page){
        Inventory inventory = Bukkit.createInventory(null, 54, Main.getInstance().getConfig().getString("Gui-waystone-remove-access-players-title"));
        int slotPosition = 0;
        if (page > 0){
            inventory.setItem(36, globalItems.previousPage(key, page - 1));
        }
        for (int i = 0; i < CustomConfig.get().getStringList(key + ".playersName").size(); i++){
            String playerName = CustomConfig.get().getStringList(key + ".playersName").get(i);
            String uuid = CustomConfig.get().getStringList(key + ".players").get(i);
            if (!uuid.equalsIgnoreCase(CustomConfig.get().getString(key + ".owner"))){
                if (page > 0 && i <= page*44){
                    continue;
                }
                if (slotPosition == 44){
                    inventory.setItem(slotPosition, globalItems.nextPage(key, page + 1));
                    break;
                }
                if (slotPosition != 36 || page == 0){
                    ItemStack head = day.dean.skullcreator.SkullCreator.itemFromUuid(UUID.fromString(uuid));
                    SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                    headMeta.setDisplayName("§l§6" + playerName);
                    headMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "waystone-key"), PersistentDataType.STRING, key);
                    headMeta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "player-uuid"), PersistentDataType.STRING, uuid);
                    headMeta.setLore(Main.getInstance().getConfig().getStringList("Gui-waystone-remove-player-access-left-click-to-expulse"));
                    head.setItemMeta(headMeta);
                    inventory.setItem(slotPosition, head); //-1 car on retire le propriétaire
                }
                slotPosition++;
            }
        }

        for (int i = 45; i <= 53; i++) {
            if(i == 45){
                inventory.setItem(i, globalItems.backButton(key));
            }else {
                inventory.setItem(i, globalItems.darkGlassItem());
            }
        }

        return inventory;
    }
}
