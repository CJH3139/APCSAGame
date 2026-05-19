package com.cjh3139;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerListener implements Listener{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(player + " <gray>joined the server!"));

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (event.getAction() == Action.PHYSICAL){
            Block block = event.getClickedBlock();
            if (block != null && block.getType().name().contains("PRESSURE_PLATE")){
                Player player = event.getPlayer();
                if (player.getMetadata("game;started") == (true){

                }

            }
        }
    }

}
