package com.cjh3139;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(player.getName() + " <gray>joined the server!"));
        FirstPlugin.getInstance().getGameManager().createGame(player);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (event.getAction() == Action.PHYSICAL){
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            if (block != null && block.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)){
                Game game = FirstPlugin.getInstance().getGameManager().getGame(player.getUniqueId());
                if (game != null && game.getState().equals("Paused")){
                    game.start();
                }
            }
        }
    }

}