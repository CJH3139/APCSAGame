package com.cjh3139;

import org.bukkit.entity.Player;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Game {
    private Player owner;
    private int id;
    private static Duration highscore;
    private String state;
    private Instant startTime;
    private final ArrayList<Platform> platforms = new ArrayList<>();

    public Game(Player owner){
        this.owner = owner;
        this.id = (int) (Math.random() * 1000000);
        this.state = "Paused";
    }

    public void start(){
        this.state = "Running";
        this.startTime = Instant.now();
    }

    public Instant getStartTime(){
        return this.startTime;
    }
    public Player getPlayer(){
        return this.owner;
    }
    public String getState(){
        return this.state;
    }

    public void addPlatform(Platform platform){
        this.platforms.add(platform);
    }
    public ArrayList<Platform> getPlatforms(){
        return this.platforms;
    }
}