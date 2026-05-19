package com.cjh3139;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HappyGhast;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;
import org.bukkit.block.data.BlockData;
import org.joml.Vector3f;

import java.util.ArrayList;

public class PlatformHandler {

    private static final ArrayList<Platform> passengers = new ArrayList<>();

    public HappyGhast spawnBlock(Player player, BlockData block, Location location, float scale, boolean isMoveable){

        location.setYaw(0f);
        location.setPitch(0f);
        BlockDisplay blockDisplay = location.getWorld().spawn(location, BlockDisplay.class, display -> {
           display.setBlock(block);
           Transformation translation = display.getTransformation();
           translation.getScale().set(new Vector3f(scale, 0.5f, scale));
           translation.getTranslation().set(scale * -0.5, -1, scale * -0.925);
           display.setTransformation(translation);
        });
        HappyGhast ghastDisplay = location.getWorld().spawn(location, HappyGhast.class, ghast->{
           ghast.setInvisible(true);
           ghast.getAttribute(Attribute.SCALE).setBaseValue(0.25);
        });
        ghastDisplay.addPassenger(blockDisplay);

        Platform platform = new Platform(ghastDisplay, blockDisplay, isMoveable);
        if (isMoveable){
            passengers.add(platform);
        }
        Game game = FirstPlugin.getInstance().getGameManager().getGame(player.getUniqueId());
        if (game != null){
            game.addPlatform(platform);
        }
        return ghastDisplay;
    }

    public void buildCourse(Player player){
        World world = player.getWorld();
        BlockData gold = Material.GOLD_BLOCK.createBlockData();

        spawnBlock(player, gold, new Location(world, -187, 100, -124), 1f, true);
        spawnBlock(player, gold, new Location(world, -186, 100, -127), 2f, true);
        spawnBlock(player, gold, new Location(world, -184, 100, -124), 2f, true);
        spawnBlock(player, gold, new Location(world, -186, 100, -121), 2f, true);

        spawnBlock(player, gold, new Location(world, -167, 100, -124), 3f, false);
        spawnBlock(player, gold, new Location(world, -148.5, 100, -112.5), 3f, false);
    }
}
