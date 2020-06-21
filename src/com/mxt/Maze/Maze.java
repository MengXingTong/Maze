package com.mxt.Maze;

import com.mxt.Maze.Commands.ProjectCommands;
import com.mxt.Maze.Listeners.PlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class Maze extends JavaPlugin implements Listener {
    private static Plugin plugin = null;
    public static Plugin getInst(){
        return plugin;
    }
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§7[§dMaze§7] §bMaze插件已加载！");
        plugin = this;
        Loading.loadConfig();
        getServer().getPluginManager().registerEvents(this, getInst());
        Bukkit.getPluginCommand("maze").setExecutor(new ProjectCommands());
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(), this);
    }
    public static HashMap<String, List<Location>> PlayerSelMap = new HashMap<>();
}
