package com.mxt.Maze.Utils;

import com.mxt.Maze.Maze;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateMaze {
    public static void CreateMazeModel(Location loc1,Location loc2) throws InterruptedException {
        //首先随机设一个入口向前1格，然后筛选出3个格子中可以走的，随机选择1个走
        //筛选时要判断步数，如果小于等于边界长度应抛弃该走法
        //方法汇总：筛选格子，判断步数
        World world = loc1.getWorld();
        int x = loc1.getBlockX();
        int y = loc1.getBlockY();
        int z = loc1.getBlockZ();
        int a = x-loc2.getBlockX();
        int b = z-loc2.getBlockZ();
        boolean fa = false;
        boolean fb = false;
        if(a < 0){
            fa = true;
            a = -a;
        }
        if(b < 0){
            fb = true;
            b = -b;
        }
        Material block = Material.getMaterial(Maze.getInst().getConfig().getString("Id"));
        for(int aa = -1;aa <= a+1;aa++){
            for(int bb = -1;bb <= b+1;bb++) {
                setBlock(world, new Location(world, x + MazeUtils.f(aa, fa), y, z + MazeUtils.f(bb,fb)), Material.AIR);
            }
        }
        if(Maze.getInst().getConfig().getInt("High") > 1) {
            for (int yy = 1; yy < Maze.getInst().getConfig().getInt("High"); yy++) {
                for(int aa = -1;aa <= a+1;aa++){
                    for(int bb = -1;bb <= b+1;bb++) {
                        setBlock(world, new Location(world, x + MazeUtils.f(aa, fa), y+yy, z + MazeUtils.f(bb,fb)), Material.AIR);
                    }
                }
            }
        }
        //铺石头
        for(int aa = 0;aa <= a;aa++){
            for(int bb = 0;bb <= b;bb++) {
                setBlock(world, new Location(world, x + MazeUtils.f(aa, fa), y, z + MazeUtils.f(bb,fb)), block);
            }
        }
        Thread.sleep(500);
        int ra = new Random().nextInt(a-3)+2;
        int rb = new Random().nextInt(b-3)+2;
        List<int[]> Walls = new ArrayList<>();
        int num = MazeUtils.WayNum(world,x+MazeUtils.f(ra,fa),y,z+MazeUtils.f(rb,fb));
        if(num == 0 || num == 1){
            setBlock(world,new Location(world,x+MazeUtils.f(ra,fa),y,z+MazeUtils.f(rb,fb)),Material.AIR);
            Walls.add(new int[]{ra+1,rb});
            Walls.add(new int[]{ra-1,rb});
            Walls.add(new int[]{ra,rb+1});
            Walls.add(new int[]{ra,rb-1});
        }
        Thread.sleep(20);
        while(!Walls.isEmpty()){
            int random = new Random().nextInt(Walls.size());
            int[] xz = Walls.get(random);
            ra = xz[0];
            rb = xz[1];
            num = MazeUtils.WayNum(world,x+MazeUtils.f(ra,fa),y,z+MazeUtils.f(rb,fb));
            if(num == 1) {
                setBlock(world,new Location(world,x+MazeUtils.f(ra,fa),y,z+MazeUtils.f(rb,fb)),Material.AIR);
                if(world.getBlockAt(new Location(world,x+MazeUtils.f(ra+1,fa),y,z+MazeUtils.f(rb,fb))).getType() == block) {
                    Walls.add(new int[]{ra + 1, rb});
                }
                if(world.getBlockAt(new Location(world,x+MazeUtils.f(ra-1,fa),y,z+MazeUtils.f(rb,fb))).getType() == block) {
                    Walls.add(new int[]{ra - 1, rb});
                }
                if(world.getBlockAt(new Location(world,x+MazeUtils.f(ra,fa),y,z+MazeUtils.f(rb+1,fb))).getType() == block) {
                    Walls.add(new int[]{ra, rb + 1});
                }
                if(world.getBlockAt(new Location(world,x+MazeUtils.f(ra,fa),y,z+MazeUtils.f(rb-1,fb))).getType() == block) {
                    Walls.add(new int[]{ra, rb - 1});
                }
            }
            Walls.remove(random);
            Thread.sleep(20);
        }
        //入口
        for(int xa = 0;xa < a-3;xa++){
            if (world.getBlockAt(new Location(world, x + MazeUtils.f(xa, fa), y, z + MazeUtils.f(1, fb))).getType() == block) {
                setBlock(world, new Location(world, x + MazeUtils.f(xa, fa), y, z + MazeUtils.f(1, fb)), Material.AIR);
                Thread.sleep(20);
            }else{
                break;
            }
        }
        Thread.sleep(20);
        //出口
        for(int xa = a;xa > 0;xa--){
            if (world.getBlockAt(new Location(world, x + MazeUtils.f(xa, fa), y, z + MazeUtils.f(b-1, fb))).getType() == block) {
                setBlock(world, new Location(world, x + MazeUtils.f(xa, fa), y, z + MazeUtils.f(b-1, fb)), Material.AIR);
                Thread.sleep(20);
            }else{
                break;
            }
        }
        Thread.sleep(20);
        if(Maze.getInst().getConfig().getInt("High") > 1) {
            for (int yy = 1; yy < Maze.getInst().getConfig().getInt("High"); yy++) {
                for (int aa = 0; aa <= a; aa++) {
                    for (int bb = 0; bb <= b; bb++) {
                        if (world.getBlockAt(new Location(world, x + MazeUtils.f(aa, fa), y, z + MazeUtils.f(bb, fb))).getType() == block) {
                            setBlock(world, new Location(world, x + MazeUtils.f(aa, fa), y+yy, z + MazeUtils.f(bb, fb)), block);
                        }
                    }
                }
            }
        }
    }
    public static void setBlock(World world,Location location,Material block){
        Bukkit.getScheduler().runTask(Maze.getInst(),new BukkitRunnable() {
            @Override
            public void run() {
                world.getBlockAt(location).setType(block);
            }
        });
    }
}
