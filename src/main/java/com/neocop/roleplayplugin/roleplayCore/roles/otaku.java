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
public class otaku implements Role{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgRoleControl + "\nRolle: Otaku");
    }

    @Override
    public void extra(RPGPlayer player, String[] ext) {
        try {
            switch(ext[0]){
                case "name":
                    rpgUtils.sendMessageToAllAliveRpgPlayer("§b[Otaku]§r " + player.getPlayer().getDisplayName() + "´s Zweitname ist übrigens:\nJugemu Jugemu Goko no Surikire Kaijarisu igyo no suigyomatsu Unraimatsu Furaimatsu Ku Neru Tokoro ni Sumu Tokoro Yabura koji no Bura koji Paipo-paipo Paipo no Sharingan Shuringan no Gurindai Gurindai no Ponpokopi no Ponpokona no Chokyumei no Chosuke");
                    break;
                    
                    default:
                        
                    case "neko":
                        rpgUtils.sendMessageToAllAliveRpgPlayer("§b[Otaku]§r This is Nemu Neko chan (´・ω・｀)");
                        pluginUtils.playSoundToAllPlayers(Sound.ENTITY_CAT_AMBIENT);
                        break;
                        
                    case "help":
                        player.getPlayer().sendMessage("/rpg extra name - Sag deinen fancy und gar nicht unnötig langen Zweitnamen :D (FMA)");
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
