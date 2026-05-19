package com.cjh3139;

import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.HappyGhast;

public class Platform {
    private final HappyGhast ghast;
    private final BlockDisplay block;

    public Platform(HappyGhast ghast, BlockDisplay block){
        this.ghast = ghast;
        this.block = block;
    }

    public HappyGhast getGhast(){
        return this.ghast;
    }

    public BlockDisplay getBlock(){
        return this.block;
    }
}