package de.founntain.infinitystorage.events;

import de.founntain.infinitystorage.InfinityStorage;
import de.founntain.infinitystorage.storage.PlayerStorage;
import de.founntain.infinitystorage.util.StorageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class OnInventoryCloseEvent implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Player player = (Player) e.getPlayer();
        Inventory inventory = e.getInventory();
        InventoryView inventoryView = e.getView();

        if(inventoryView.getTitle().equals(StorageManager.getInventoryName(player.getUniqueId()))){
            PlayerStorage playerStorage = InfinityStorage.inventoryManager.getStorageOfPlayer(player.getUniqueId());

            if(playerStorage == null)
                return;

            playerStorage.saveInventory(inventory);
        }
    }
}
