package com.cjh3139;

import org.bukkit.plugin.java.JavaPlugin;

public final class FirstPlugin extends JavaPlugin{

    private static FirstPlugin instance;
    private GameManager gameManager;
    private PlatformHandler platformHandler;

    @Override
    public void onEnable(){
        instance = this;
        this.gameManager = new GameManager();
        this.platformHandler = new PlatformHandler();
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.gameManager.startTickTask(this);
    }

    @Override
    public void onDisable(){
        instance = null;
    }

    public static FirstPlugin getInstance(){
        return instance;
    }

    public GameManager getGameManager(){
        return gameManager;
    }

    public PlatformHandler getPlatformHandler(){
        return platformHandler;
    }
}