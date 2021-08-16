package de.founntain.infinitystorage.commands;

import de.founntain.infinitystorage.InfinityStorage;
import de.founntain.infinitystorage.storage.PlayerStorage;
import de.founntain.infinitystorage.util.InventoryManager;
import de.founntain.infinitystorage.util.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OpenStorageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;

        int page = 0;

        if(strings.length == 1){
            try{
                page = Integer.parseInt(strings[0]);

            }catch(Exception e){ }
        }

        Player player = (Player) commandSender;

        PlayerStorage playerStorage = InfinityStorage.inventoryManager.getStorageOfPlayer(player.getUniqueId());

        if(playerStorage == null)
            playerStorage = InfinityStorage.inventoryManager.loadPlayerStorage(player.getUniqueId());

        if(strings.length == 1){
            if(page > playerStorage.getInventoriesCount() || page == 0) {
                player.sendMessage(ChatColor.RED + "The page you entered was not valid. Your first page was opened");
                player.openInventory(playerStorage.getInventory(0));
            }
            else
                player.openInventory(playerStorage.getInventory(page - 1));


            return true;
        }

        player.openInventory(playerStorage.getInventory(0));

        return true;
    }
}
