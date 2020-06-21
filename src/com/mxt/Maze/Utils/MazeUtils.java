package com.mxt.Maze.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class MazeUtils {
    public static int f(int a,boolean af){
        if(af){
            return a;
        }
        return -a;
    }
    public static int WayNum(World world,int x,int y,int z){
        int num = 0;
        if(world.getBlockAt(new Location(world,x+1,y,z)).getType() == Material.AIR){
            num += 1;
        }
        if(world.getBlockAt(new Location(world,x,y,z+1)).getType() == Material.AIR){
            num += 1;
        }
        if(world.getBlockAt(new Location(world,x-1,y,z)).getType() == Material.AIR){
            num += 1;
        }
        if(world.getBlockAt(new Location(world,x,y,z-1)).getType() == Material.AIR){
            num += 1;
        }
        return num;
    }
}
