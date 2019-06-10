/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.roleplay.roles;

import com.neocop.neomcPlugin.roleplay.RPGPlayer;
import com.neocop.neomcPlugin.roleplay.rpgUtils;
import com.neocop.neomcPlugin.utils.Preferences;
import com.neocop.neomcPlugin.utils.pluginUtils;
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
