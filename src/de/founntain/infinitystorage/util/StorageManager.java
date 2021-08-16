package de.founntain.infinitystorage.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class StorageManager {

    public static String getInventoryName(UUID userID){
        String name = Bukkit.getPlayer(userID).getDisplayName();

        return ChatColor.BLUE + name + "'s storage";
    }

    public static ItemStack GetStoragePageBtn(boolean isNextBtn){
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        if(isNextBtn)
            meta.setDisplayName(ChatColor.YELLOW + "Next page");
        else
            meta.setDisplayName(ChatColor.YELLOW + "Previous page");

        item.setItemMeta(meta);

        return item;
    }
}
