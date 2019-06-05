/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.rpgUtils;
import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.pluginUtils;
import org.bukkit.Sound;

/**
 *
 * @author Noah
 */
public class backfisch implements Role{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgRoleControl + "\nRolle: Backfisch");
    }

    @Override
    public void extra(RPGPlayer player, String[] ext) {
        try {
            switch(ext[0]){
                case "blubbl":
                    pluginUtils.playSoundToAllPlayers(Sound.AMBIENT_UNDERWATER_ENTER);
                    rpgUtils.sendMessageToAllAliveRpgPlayer("§b" + player.getPlayer().getDisplayName() + " Blubbelt!");
                    break; 
                    
                    default:
                    case "help":
                        player.getPlayer().sendMessage("/rpg extra blubbl - BLUBBL");
                        break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void resetNightsExtra() {
    }
    
}
