package de.founntain.infinitystorage.util;

import de.founntain.infinitystorage.InfinityStorage;
import de.founntain.infinitystorage.storage.PlayerStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class InventoryManager {
    private final String FOLDER_PATH = "plugins/InfinityStorage/PlayerStorages/";

    private ArrayList<PlayerStorage> playerStorages;

    public InventoryManager(){
        this.playerStorages = new ArrayList<>();

        File file = new File("plugins/InfinityStorage/PlayerStorages/");

        if(!file.exists())
            file.mkdirs();
    }

    public void savePlayerStorage(final UUID playerId){
        PlayerStorage playerStorageToSave = null;

        for(PlayerStorage playerStorage : this.playerStorages){
            if(playerStorage.getPlayerId() != playerId) continue;

            playerStorageToSave = playerStorage;
        }

        if(playerStorageToSave == null) {
            InfinityStorage.sendConsoleMessage(ChatColor.RED + "There is not PlayerStorage to save!");
            return;
        }

        playerStorageToSave.removeEmptyInventories();

        try {
            YamlConfiguration c = new YamlConfiguration();

            ArrayList<ItemStack[]> inventoryItems = new ArrayList<>();

            for(Inventory inventory : playerStorageToSave.getInventories()){
                inventoryItems.add(inventory.getContents());
            }

            c.set("storage.inventories", inventoryItems);
            c.save(new File(this.FOLDER_PATH, playerId + ".yml"));

            InfinityStorage.sendConsoleMessage(ChatColor.YELLOW + Bukkit.getPlayer(playerId).getDisplayName()
                    + ChatColor.GREEN + " storage saved");
        }catch(IOException ex) {
            return;
        }
    }

    public PlayerStorage loadPlayerStorage(final UUID playerId){

        String path = this.FOLDER_PATH + playerId + ".yml";
        File file = new File(path);

        if(!file.exists()) {
            PlayerStorage playerStorage = createPlayerStorage(playerId);

            //this.savePlayerStorage(playerId);

            return playerStorage;
        }

        YamlConfiguration c = YamlConfiguration.loadConfiguration(file);

        List<ArrayList<ItemStack>> inventoryItems = ((List<ArrayList<ItemStack>>) c.get("storage.inventories"));

        ArrayList<Inventory> inventories = new ArrayList<>();

        for(ArrayList<ItemStack> items : inventoryItems){
            Inventory inventory = createNewEmptyStorageInventory(playerId);

            inventory.setContents(items.toArray(new ItemStack[0]));

            inventories.add(inventory);
        }

        PlayerStorage playerStorage = createPlayerStorage(playerId, inventories);

        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Loaded "
                + ChatColor.YELLOW + Bukkit.getPlayer(playerId).getDisplayName() + "'s"
                + ChatColor.GREEN + " storage successfull!");

        return playerStorage;
    }

    public ArrayList<PlayerStorage> getPlayerStorages(){
        return this.playerStorages;
    }

    public PlayerStorage createPlayerStorage(final UUID userId){
        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Creating storage for " + ChatColor.YELLOW + Bukkit.getPlayer(userId).getDisplayName());

        PlayerStorage playerStorage = new PlayerStorage(userId, createNewStorageInventory(Bukkit.getPlayer(userId)));

        this.playerStorages.add(playerStorage);

        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Storage created successfull!");

        return playerStorage;
    }

    public PlayerStorage createPlayerStorage(final UUID userId, final Inventory inventory){
        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Creating storage with inventory for " + ChatColor.YELLOW + Bukkit.getPlayer(userId).getDisplayName());

        PlayerStorage playerStorage = new PlayerStorage(userId, inventory);

        this.playerStorages.add(playerStorage);

        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Storage created successfull!");

        return playerStorage;
    }

    public PlayerStorage createPlayerStorage(final UUID userId, final Inventory[] inventories){
        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Creating storage with inventory for " + ChatColor.YELLOW + Bukkit.getPlayer(userId).getDisplayName());

        PlayerStorage playerStorage = new PlayerStorage(userId);

        playerStorage.addInventories(inventories);

        this.playerStorages.add(playerStorage);

        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Storage created successfull!");

        return playerStorage;
    }

    public PlayerStorage createPlayerStorage(final UUID userId, final ArrayList<Inventory> inventories){
        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Creating storage with inventory for " + ChatColor.YELLOW + Bukkit.getPlayer(userId).getDisplayName());

        PlayerStorage playerStorage = new PlayerStorage(userId);

        playerStorage.addInventories(inventories);

        this.playerStorages.add(playerStorage);

        InfinityStorage.sendConsoleMessage(ChatColor.GREEN + "Storage created successfull!");

        return playerStorage;
    }

    public PlayerStorage getStorageOfPlayer(final UUID userId){
        for(PlayerStorage playerStorage : this.playerStorages){
            if(playerStorage.getPlayerId() != userId) continue;

            return playerStorage;
        }

        return null;
    }

    public Inventory createNewStorageInventory(final Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, StorageManager.getInventoryName(player.getUniqueId()));

        ItemStack backBtn = StorageManager.GetStoragePageBtn(false);
        ItemStack nextBtn = StorageManager.GetStoragePageBtn(true);

        inventory.setItem(53, nextBtn);
        inventory.setItem(45, backBtn);

        return inventory;
    }

    public Inventory createNewStorageInventory(final UUID playerId){
        Player player = Bukkit.getPlayer(playerId);

        Inventory inventory = Bukkit.createInventory(null, 54, StorageManager.getInventoryName(player.getUniqueId()));

        ItemStack backBtn = StorageManager.GetStoragePageBtn(false);
        ItemStack nextBtn = StorageManager.GetStoragePageBtn(true);

        inventory.setItem(53, nextBtn);
        inventory.setItem(45, backBtn);

        return inventory;
    }

    public Inventory createNewEmptyStorageInventory(final Player player){
        return Bukkit.createInventory(null, 54, StorageManager.getInventoryName(player.getUniqueId()));
    }

    public Inventory createNewEmptyStorageInventory(final UUID playerId){
        Player player = Bukkit.getPlayer(playerId);

        return Bukkit.createInventory(null, 54, StorageManager.getInventoryName(player.getUniqueId()));
    }
}
