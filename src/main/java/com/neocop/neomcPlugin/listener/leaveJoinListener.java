/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.listener;

import com.neocop.neomcPlugin.roleplay.RPGPlayer;
import com.neocop.neomcPlugin.roleplay.RpgEngine;
import com.neocop.neomcPlugin.roleplay.rpgUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                    try {
                        RPGPlayer rpP = rpgUtils.getRpgPlayerByName(died.getDisplayName());
                        RpgEngine.killPlayerWhichLeftWithoutExpection(rpP);
                    } catch (Exception ex) {
                        Logger.getLogger(leaveJoinListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
