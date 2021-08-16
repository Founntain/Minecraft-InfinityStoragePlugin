package de.founntain.infinitystorage.events;

import de.founntain.infinitystorage.InfinityStorage;
import de.founntain.infinitystorage.storage.PlayerStorage;
import de.founntain.infinitystorage.util.InventoryManager;
import de.founntain.infinitystorage.util.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class OnInventoryClickEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Inventory inventory = e.getInventory();
        InventoryView inventoryView = e.getView();

        ItemStack clickedItem = e.getCurrentItem();

        Player player = (Player) e.getWhoClicked();

        String itemDisplayname = "";

        if(clickedItem == null || clickedItem.getType() == Material.AIR) return;

        itemDisplayname = e.getCurrentItem().getItemMeta().getDisplayName();

        if(itemDisplayname.equals(ChatColor.YELLOW + "Previous page") || itemDisplayname.equals(ChatColor.YELLOW + "Next page")) {
            if(itemDisplayname.equals(ChatColor.YELLOW + "Previous page"))
                onPreviousClick(inventory, player);
            if(itemDisplayname.equals(ChatColor.YELLOW + "Next page"))
                onNextClick(inventory, player);

            e.setCancelled(true);
        }
    }

    private void onPreviousClick(final Inventory inventory, final Player player){
        PlayerStorage playerStorage = InfinityStorage.inventoryManager.getStorageOfPlayer(player.getUniqueId());

        int inventoryIndex = playerStorage.getIndexOfInventory(inventory);

        if(inventoryIndex == 0) {
            InfinityStorage.sendConsoleMessage("First Inventory Page");

            return;
        }

        Inventory newInventory = playerStorage.getInventory(inventoryIndex - 1);

        InfinityStorage.sendConsoleMessage(ChatColor.GOLD + "Now on page " + playerStorage.getIndexOfInventory(newInventory));

        player.openInventory(newInventory);
    }

    private void onNextClick(Inventory inventory, final Player player){
        PlayerStorage playerStorage = InfinityStorage.inventoryManager.getStorageOfPlayer(player.getUniqueId());

        int inventoryIndex = playerStorage.getIndexOfInventory(inventory);

        if(inventoryIndex == playerStorage.getInventoriesCount() - 1){
            Inventory newInventory = playerStorage.addInventory(InfinityStorage.inventoryManager.createNewStorageInventory(player));

            InfinityStorage.sendConsoleMessage(ChatColor.GOLD + "Now on page " + playerStorage.getIndexOfInventory(newInventory));

            player.openInventory(newInventory);

            return;
        }

        if(inventoryIndex == -1){
            playerStorage.addInventory(inventory);

            Inventory newInventory = playerStorage.addInventory(InfinityStorage.inventoryManager.createNewStorageInventory(player));

            InfinityStorage.sendConsoleMessage(ChatColor.GOLD + "Now on page " + playerStorage.getIndexOfInventory(newInventory));

            player.openInventory(newInventory);

            return;
        }

        Inventory newInventory = playerStorage.getInventory(inventoryIndex + 1);

        InfinityStorage.sendConsoleMessage(ChatColor.GOLD + "Now on page " + playerStorage.getIndexOfInventory(newInventory));

        player.openInventory(newInventory);
    }
}
