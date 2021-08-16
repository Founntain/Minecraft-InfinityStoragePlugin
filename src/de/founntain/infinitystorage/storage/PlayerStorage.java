package de.founntain.infinitystorage.storage;

import de.founntain.infinitystorage.InfinityStorage;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerStorage {
    private UUID playerId;
    private ArrayList<Inventory> inventories;

    public PlayerStorage(UUID playerId, ArrayList<Inventory> inventories){
        this.playerId = playerId;
        this.inventories = inventories;
    }

    public PlayerStorage(UUID playerId, Inventory inventory){
        this.playerId = playerId;
        this.inventories = new ArrayList<>();

        this.inventories.add(inventory);
    }

    public PlayerStorage(UUID playerId){
        this.playerId = playerId;
        this.inventories = new ArrayList<>();
    }

    public UUID getPlayerId(){
        return this.playerId;
    }

    public Inventory getInventory(int index){
        return this.inventories.get(index);
    }

    public ArrayList<Inventory> getInventories(){
        return this.inventories;
    }

    public Inventory addInventory(Inventory inventory){
        this.inventories.add(inventory);

        return inventory;
    }

    public void addInventories(Inventory[] inventories){
        for(Inventory inventory : inventories){
            this.inventories.add(inventory);
        }
    }

    public void addInventories(ArrayList<Inventory> inventories){
        this.inventories.addAll(inventories);
    }

    public void removeInventory(Inventory inventory){
        this.inventories.remove(inventory);
    }

    public void removeInventories(ArrayList<Inventory> inventories){
        this.inventories.removeAll(inventories);
    }

    public void removeEmptyInventories(){
        ArrayList<Inventory> inventoriesToDelete = new ArrayList<>();

        for(Inventory inventory : this.inventories){
            inventory.remove(Material.AIR);

            int airCounter = 0;
            for(ItemStack item : inventory.getContents()){
                if(item == null) {
                    airCounter++;
                    continue;
                }
                if(item.getType() == Material.AIR)
                    airCounter++;
            }

            if(inventory.getContents().length <= 2 || airCounter == 52)
                inventoriesToDelete.add(inventory);
        }

        this.removeInventories(inventoriesToDelete);
    }

    public void saveInventory(Inventory inventory){
        int invIndex = this.inventories.indexOf(inventory);

        if(invIndex == -1){
            System.out.println("Inventory not found adding it");

            this.addInventory(inventory);

            InfinityStorage.inventoryManager.savePlayerStorage(this.playerId);

            return;
        }

        this.inventories.set(invIndex, inventory);

        InfinityStorage.inventoryManager.savePlayerStorage(this.playerId);
    }

    public int getIndexOfInventory(Inventory inventory){
        return this.inventories.indexOf(inventory);
    }

    public int getInventoriesCount(){
        return this.inventories.size();
    }
}
