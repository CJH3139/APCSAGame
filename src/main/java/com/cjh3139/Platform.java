package com.cjh3139;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.HappyGhast;

public class Platform {
    private final HappyGhast ghast;
    private final BlockDisplay block;
    private final boolean movable;

    public Platform(HappyGhast ghast, BlockDisplay block, boolean movable){
        this.ghast = ghast;
        this.block = block;
        this.movable = movable;
    }

    public HappyGhast getGhast(){
        return this.ghast;
    }

    public BlockDisplay getBlock(){
        return this.block;
    }

    public boolean isMovable(){
        return this.movable;
    }
}