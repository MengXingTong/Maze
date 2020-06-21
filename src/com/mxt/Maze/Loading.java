package com.mxt.Maze;

public class Loading {
    public static void loadConfig(){
        Maze.getInst().saveDefaultConfig();
        Maze.getInst().reloadConfig();
    }
}
