package com.cjh3139;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HappyGhast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.time.Duration;

public class PlayerListener implements Listener{

    private String formatDuration(Duration d){
        long millis = d.toMillis();
        long seconds = millis / 1000;
        long centis = (millis % 1000) / 10;
        return seconds + "." + String.format("%02d", centis) + "s";
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(player.getName() + " <gray>joined the server!"));
        FirstPlugin.getInstance().getGameManager().createGame(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        GameManager gameManager = FirstPlugin.getInstance().getGameManager();
        Game game = gameManager.getGame(player.getUniqueId());
        if (game == null)
            return;
        if (game.getState().equals("Running")){
            game.finish();
        }
        gameManager.removeGame(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (event.getAction() == Action.PHYSICAL){
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            if (block != null && block.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)){
                Game game = FirstPlugin.getInstance().getGameManager().getGame(player.getUniqueId());
                if (game == null) return;
                if (game.getState().equals("Paused")){
                    FirstPlugin.getInstance().getPlatformHandler().buildCourse(player);
                    game.start();
                }
            }
            else if (block != null && block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)){
                Game game = FirstPlugin.getInstance().getGameManager().getGame(player.getUniqueId());
                if (game == null) return;
                if (game.getState().equals("Running")){
                    Duration elapsed = game.finish();
                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<green><bold>Finished!</bold> <white>Time: <yellow>" + formatDuration(elapsed)));
                    Duration best = Game.getHighscore();
                    if (best == null || elapsed.compareTo(best) < 0){
                        Game.setHighscore(elapsed);
                        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(
                            "<gold><bold>★ NEW HIGH SCORE!</bold> <yellow>" + player.getName()
                                + "<gray> finished in <green>" + formatDuration(elapsed)));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerLeftClick(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player && event.getEntity() instanceof HappyGhast){
            for (Entity passenger : event.getEntity().getPassengers()){
                if (passenger instanceof BlockDisplay){
                    passenger.setGlowing(false);
                }
            }
            Player player = (Player) event.getDamager();
            Game game = FirstPlugin.getInstance().getGameManager().getGame(player.getUniqueId());
            if (game == null)
                return;
            if (game.getSelected() == null)
                return;
            game.setSelected(null);
            player.sendActionBar(MiniMessage.miniMessage().deserialize("<red>Deselected block!"));
            player.playSound(player, Sound.BLOCK_COPPER_GRATE_PLACE, 1f, 1.5f);
        }
    }

    @EventHandler
    public void onPlayerRightClickEntity(PlayerInteractEntityEvent event){
        if (event.getRightClicked() instanceof HappyGhast){
            Player player = event.getPlayer();
            HappyGhast ghast = (HappyGhast) event.getRightClicked();
            Game game = FirstPlugin.getInstance().getGameManager().getGame(player.getUniqueId());
            if (game == null)
                return;
            Platform platform = game.getPlatformByGhast(ghast);
            if (platform == null || !platform.isMovable())
                return;
            Platform previousPlatform = game.getSelected();
            if (previousPlatform != null){
                previousPlatform.getBlock().setGlowing(false);
            }
            game.setSelected(platform);
            platform.getBlock().setGlowing(true);
            player.sendActionBar(MiniMessage.miniMessage().deserialize("<green>Selected block!"));
            player.playSound(player, Sound.BLOCK_CHAIN_STEP, 1f, 1.5f);
        }
    }

}