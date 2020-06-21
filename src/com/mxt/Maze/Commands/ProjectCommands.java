package com.mxt.Maze.Commands;

import com.mxt.Maze.Loading;
import com.mxt.Maze.Maze;
import com.mxt.Maze.Utils.CreateMaze;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectCommands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("Maze.admin")) {
                sender.sendMessage("§7[§dMaze§7] §b/maze sel §e开启/关闭选择模式");
                sender.sendMessage("§7[§dMaze§7] §7左键选择第一个点,右键选择第二个点.");
                sender.sendMessage("§7[§dMaze§7] §b/maze create §e生成迷宫");
                sender.sendMessage("§7[§dMaze§7] §b/maze reload §e重载配置文件");
                return true;
            }
            sender.sendMessage("§7[§dMaze§7] §c你没有权限这样做.");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("sel")) {
                if (sender.hasPermission("Maze.admin")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        HashMap<String, List<Location>> PlayerSelMap = Maze.PlayerSelMap;
                        if (PlayerSelMap.containsKey(player.getName())) {
                            PlayerSelMap.remove(player.getName());
                            sender.sendMessage("§7[§dMaze§7] §a成功关闭选择模式.");
                            return true;
                        }
                        PlayerSelMap.put(player.getName(), new ArrayList<>());
                        sender.sendMessage("§7[§dMaze§7] §a成功开启选择模式.");
                        return true;
                    }
                    sender.sendMessage("§7[§dMaze§7] §c后台不能执行此指令！");
                    return true;
                }
                sender.sendMessage("§7[§dMaze§7] §c你没有权限这样做.");
                return true;
            }
            if (args[0].equalsIgnoreCase("create")) {
                if (sender.hasPermission("Maze.admin")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        HashMap<String, List<Location>> PlayerSelMap = Maze.PlayerSelMap;
                        if(!PlayerSelMap.containsKey(player.getName())){
                            sender.sendMessage("§7[§dMaze§7] §a请在选择模式下执行该指令.");
                            return true;
                        }
                        List<Location> Locations = PlayerSelMap.get(player.getName());
                        if(Locations.size() <2){
                            sender.sendMessage("§7[§dMaze§7] §a请选择2个坐标后再生成迷宫.");
                            sender.sendMessage("§7[§dMaze§7] §a左键,右键方块以设置迷宫范围.");
                            return true;
                        }
                        if(Locations.get(0).getBlockX() == 0 && Locations.get(0).getBlockY() == 300 && Locations.get(0).getBlockZ() == 0){
                            sender.sendMessage("§7[§dMaze§7] §a请选择2个坐标后再生成迷宫.");
                            sender.sendMessage("§7[§dMaze§7] §a左键,右键方块以设置迷宫范围.");
                            return true;
                        }
                        if(Locations.get(0).getBlockY() != Locations.get(1).getBlockY()){
                            sender.sendMessage("§7[§dMaze§7] §a请确保选择的两点在同一平面内.");
                            return true;
                        }
                        Bukkit.getScheduler().runTaskAsynchronously(Maze.getInst(),new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    CreateMaze.CreateMazeModel(Locations.get(0),Locations.get(1));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        sender.sendMessage("§7[§dMaze§7] §a成功创建迷宫.");
                        return true;
                    }
                    sender.sendMessage("§7[§dMaze§7] §c后台不能执行此指令！");
                    return true;
                }
                sender.sendMessage("§7[§dMaze§7] §c你没有权限这样做.");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("Maze.admin")) {
                    Loading.loadConfig();
                    sender.sendMessage("§7[§dMaze§7] §c重载成功！");
                    return true;
                }
                sender.sendMessage("§7[§dMaze§7] §c你没有权限这样做.");
                return true;
            }
        }
        return true;
    }
}
