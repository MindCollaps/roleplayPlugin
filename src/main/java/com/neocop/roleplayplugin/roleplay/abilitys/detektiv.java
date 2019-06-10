/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplay.abilitys;

import com.neocop.roleplayplugin.roleplay.RPGPlayer;
import com.neocop.roleplayplugin.roleplay.RpgEngine;
import com.neocop.roleplayplugin.utils.Preferences;

/**
 *
 * @author Noah
 */
public class detektiv implements Ability{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendTitle("Deine Rolle ist", "§9Detektiv!");
        player.getPlayer().sendMessage(Preferences.detectiv);
        RpgEngine.villagerTeam.put(player.getPlayer().getDisplayName(), player);
        RpgEngine.extraVillager.put(player.getPlayer().getDisplayName(), player);
        RpgEngine.extraSc.addPlayer(player.getPlayer());
        RpgEngine.extraSc.addPlayer(player.getPlayer());
    }

    @Override
    public void actionNight(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgDetectivNightAction);
    }

    @Override
    public void actionDay(RPGPlayer player) {
        if(RpgEngine.killerKilled){
         player.getPlayer().sendMessage(Preferences.rpgDetectivDayActionDied);   
        } else {
            player.getPlayer().sendMessage(Preferences.rpgDetectivDayActionNoDied);   
        }
    }
    
}
