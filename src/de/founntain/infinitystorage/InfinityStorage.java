package de.founntain.infinitystorage;

import de.founntain.infinitystorage.commands.OpenStorageCommand;
import de.founntain.infinitystorage.events.OnInventoryClickEvent;
import de.founntain.infinitystorage.events.OnInventoryCloseEvent;
import de.founntain.infinitystorage.util.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class InfinityStorage extends JavaPlugin {
    private final Server server;
    public static final String CONSOLE_PREFIX = ChatColor.WHITE + "[" + ChatColor.GOLD +"Infinity Storage" + ChatColor.WHITE + "] ";

    public static InventoryManager inventoryManager;

    public InfinityStorage(){
        this.server = Bukkit.getServer();
        inventoryManager = new InventoryManager();
    }

    @Override
    public void onLoad() {
        this.sendConsoleMessage("Plugin loaded");
        this.sendConsoleMessage("Plugin made by " + ChatColor.GOLD + "Founntain");
        this.sendConsoleMessage("GitHub: " + ChatColor.YELLOW + "https://github.com/Founntain/Minecraft-LifeStealPlugin");
    }

    @Override
    public void onEnable() {
        this.registerCommands();

        this.registerEvents();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static void sendConsoleMessage(String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(InfinityStorage.CONSOLE_PREFIX + msg);
    }

    private void registerCommand(String command, CommandExecutor commandExecutor){

        this.sendConsoleMessage(ChatColor.RED + commandExecutor.getClass().getSimpleName() + ChatColor.WHITE + " registered ");

        getCommand(command).setExecutor(commandExecutor);
    }

    private void registerEvent(Listener listener){
        this.sendConsoleMessage(ChatColor.RED + listener.getClass().getSimpleName() + ChatColor.WHITE + " registered ");

        this.server.getPluginManager().registerEvents(listener, this);
    }

    private void registerCommands(){
        registerCommand("openstorage", new OpenStorageCommand());
    }

    private void registerEvents(){
        registerEvent(new OnInventoryClickEvent());
        registerEvent(new OnInventoryCloseEvent());
    }
}
