package com.cjh3139;

import org.bukkit.plugin.java.JavaPlugin;

public final class FirstPlugin extends JavaPlugin{

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable(){

    }
}