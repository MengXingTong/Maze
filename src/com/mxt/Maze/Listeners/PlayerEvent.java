package com.mxt.Maze.Listeners;

import com.mxt.Maze.Maze;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerEvent implements Listener {
    @EventHandler
    public void interact(PlayerInteractEvent e) {
        HashMap<String, List<Location>> PlayerSelMap = Maze.PlayerSelMap;
        Player player = e.getPlayer();
        if(!PlayerSelMap.containsKey(player.getName())){
            return;
        }
        if(e.getAction() == Action.LEFT_CLICK_BLOCK){
            Block block = e.getClickedBlock();
            if(block == null){
                return;
            }
            List<Location> locs = new ArrayList<>();
            if(PlayerSelMap.get(player.getName()).equals(locs)){
                locs.add(block.getLocation());
            }else{
                locs = PlayerSelMap.get(player.getName());
                locs.set(0,block.getLocation());
            }
            PlayerSelMap.put(player.getName(),locs);
            player.sendMessage("§7[§dMaze§7] §a成功设置迷宫创建点1. §f" + block.getX() + "," + block.getY() + "," + block.getZ());
            e.setCancelled(true);
            return;
        }
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = e.getClickedBlock();
            if(block == null){
                return;
            }
            List<Location> locs = new ArrayList<>();
            if(PlayerSelMap.get(player.getName()).equals(locs)){
                locs.add(new Location(block.getWorld(),0,300,0));
                locs.add(block.getLocation());
                PlayerSelMap.put(player.getName(),locs);
            }else{
                locs = PlayerSelMap.get(player.getName());
                if(locs.size() > 1) {
                    locs.set(1, block.getLocation());
                }else{
                    locs.add(block.getLocation());
                }
                PlayerSelMap.put(player.getName(),locs);
            }
            player.sendMessage("§7[§dMaze§7] §a成功设置迷宫创建点2. §f" + block.getX() + "," + block.getY() + "," + block.getZ());
            e.setCancelled(true);
            return;
        }
    }
}
