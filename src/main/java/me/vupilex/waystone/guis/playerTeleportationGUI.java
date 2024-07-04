package me.vupilex.waystone.guis;

import me.vupilex.waystone.Main;
import me.vupilex.waystone.guis.GUIItems.globalItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class playerTeleportationGUI {
    public static Inventory playerTeleportationGUI(Player player, String WaystoneClickedKey ,int page){
        //On utilise waystoneClickerKey pour le back button sinon on aura pas de retour
        Inventory inventory = Bukkit.createInventory(null, 54, Main.getInstance().getConfigLang.getString("Gui-player-teleportation-title"));
        int slotPosition = 0;
        int keyPosition = 0;
        if (page > 0){
            inventory.setItem(36, globalItems.previousPage(WaystoneClickedKey, page - 1));
        }
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
            if (page > 0 && keyPosition <= page*44){
                keyPosition++;
                continue;
            }
            if (onlinePlayer != player){
                if (slotPosition == 44){
                    inventory.setItem(slotPosition, globalItems.nextPage(WaystoneClickedKey, page + 1));
                    break;
                }else {
                    ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta metaPlayerHead = (SkullMeta) playerHead.getItemMeta();
                    metaPlayerHead.setDisplayName("§6" + onlinePlayer.getName());
                    metaPlayerHead.setOwningPlayer(onlinePlayer);
                    metaPlayerHead.setDisplayName(null);
                    playerHead.setItemMeta(metaPlayerHead);
                    inventory.addItem(playerHead);
                }
                slotPosition++;
            }
        }

        for (int i = 45; i <= 53; i++) {
            if(i == 45){
                inventory.setItem(i, globalItems.waystoneButton(WaystoneClickedKey));
            }else if(i == 46){
                inventory.setItem(i, globalItems.playerTeleportationRequest(WaystoneClickedKey));
            }else{
                inventory.setItem(i, globalItems.darkGlassItem());
            }
        }
        return inventory;
    }
}
