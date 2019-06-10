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
import org.bukkit.Effect;

/**
 *
 * @author Noah
 */
public class kpopStar implements Role{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgRoleControl + "\nRolle: K-Pop Star");
    }

    @Override
    public void extra(RPGPlayer player, String[] ext) {
        try {
            switch(ext[0]){
                case "act":
                    rpgUtils.playEffektToAllRpgPlayer(player.getPlayer().getLocation(), 100, Effect.DRAGON_BREATH);
                    pluginUtils.spawnRandomFireworkAroundPlayer(player.getPlayer());
                    rpgUtils.sendMessageToAllAliveRpgPlayer("§b[K-Pop star] " + player.getPlayer().getDisplayName() + " Tritt auf!");
                    break;
                    
                    default:
                    case "help":
                        player.getPlayer().sendMessage("/rpg extra act - Mach einen Auftritt");
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
