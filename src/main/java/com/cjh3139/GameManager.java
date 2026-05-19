package com.cjh3139;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class GameManager {
    private final ArrayList<Game> games = new ArrayList<Game>();

    public Game createGame(Player player){
        Game game = new Game(player);
        games.add(game);
        return game;
    }

    public Game getGame(UUID uuid){
        for (Game game : games){
            if (game.getPlayer().getUniqueId().equals(uuid)){
                return game;
            }
        }
        return null;
    }

    public void removeGame(UUID uuid){
        for (int i = 0; i < games.size(); i++){
            if (games.get(i).getPlayer().getUniqueId().equals(uuid)){
                games.remove(i);
                return;
            }
        }
    }
}