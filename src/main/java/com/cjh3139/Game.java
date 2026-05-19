package com.cjh3139;

import org.bukkit.Location;
import org.bukkit.entity.HappyGhast;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
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
    private Platform selected;

    public Game(Player owner){
        this.owner = owner;
        this.id = (int) (Math.random() * 1000000);
        this.state = "Paused";
    }

    public void start(){
        this.state = "Running";
        this.startTime = Instant.now();
    }

    public Duration finish(){
        Duration elapsed = Duration.between(this.startTime, Instant.now());
        for (Platform p : this.platforms){
            p.getGhast().remove();
            p.getBlock().remove();
        }
        this.platforms.clear();
        this.state = "Paused";
        return elapsed;
    }
    public static Duration getHighscore(){
        return highscore;
    }
    public static void setHighscore(Duration newHighscore){
        highscore = newHighscore;
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

    public Platform getPlatformByGhast(HappyGhast ghast){
        for (Platform p : this.platforms){
            if (p.getGhast().equals(ghast)){
                return p;
            }
        }
        return null;
    }

    public Platform getSelected(){
        return this.selected;
    }
    public void setSelected(Platform selected){
        this.selected = selected;
    }

    public void tick(){
        if (this.selected == null) return;
        Location loc = this.owner.getLocation();
        Vector forward = loc.getDirection().multiply(5);
        loc.add(forward);
        loc.setYaw(0f);
        loc.setPitch(0f);
        this.selected.getGhast().teleport(loc);
    }
}