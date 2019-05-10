/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.rpgUtils;
import com.neocop.roleplayplugin.utils.Preferences;
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
                    rpgUtils.playEffektToAllRpgPlayer(player.getPlayer().getLocation(), 100, Effect.ENDEREYE_LAUNCH);
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
    
}
