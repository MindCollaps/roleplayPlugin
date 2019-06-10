/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.roleplay.roles;

import com.neocop.neomcPlugin.roleplay.RPGPlayer;
import com.neocop.neomcPlugin.roleplay.rpgUtils;
import com.neocop.neomcPlugin.utils.Preferences;
import org.bukkit.Effect;

/**
 *
 * @author Noah
 */
public class hacker implements Role{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgRoleControl + "\nRolle: hacker");
    }

    @Override
    public void extra(RPGPlayer player, String[] ext) {
        try {
            switch(ext[0]){
                case "hack":
                    rpgUtils.playEffektToAllRpgPlayer(player.getPlayer().getLocation(), 100, Effect.DRAGON_BREATH);
                    rpgUtils.sendMessageToAllAliveRpgPlayer("§b§k12§r§9[H§6§ka§rc§4§kk§rer]" + player.getPlayer().getDisplayName() + " hat dich gehackt!§4§k1§d52");
                    break;
                    
                    default:
                    case "help":
                        player.getPlayer().sendMessage("/rpg extra hack - Mach den anschein, dass du alle gehackt hast!");
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
