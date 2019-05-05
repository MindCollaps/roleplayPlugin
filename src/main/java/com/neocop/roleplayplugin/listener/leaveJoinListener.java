/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.listener;

import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.roleplayCore.rpgUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Noah
 */
public final class leaveJoinListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        RpgEngine.onlinePlayer.put(event.getPlayer().getDisplayName(), event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        RpgEngine.onlinePlayer.remove(event.getPlayer().getDisplayName());
        if (RpgEngine.rpgRunning) {
            Player died = event.getPlayer();
            if (RpgEngine.rpgPlayer.containsKey(died.getDisplayName())) {
                if (RpgEngine.killedPlayer.contains(died.getDisplayName())) {
                    rpgUtils.playerDiedOrLeaveWithoutExpection(died);
                }
            }
        }
    }
}
