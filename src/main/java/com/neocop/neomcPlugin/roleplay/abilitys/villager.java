/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.roleplay.abilitys;

import com.neocop.neomcPlugin.roleplay.RPGPlayer;
import com.neocop.neomcPlugin.roleplay.RpgEngine;
import com.neocop.neomcPlugin.utils.Preferences;

/**
 *
 * @author Noah
 */
public class villager implements Ability{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendTitle("Deine Rolle ist", "§aVillager!");
        player.getPlayer().sendMessage(Preferences.villager);
        RpgEngine.villagerTeam.put(player.getPlayer().getDisplayName(), player);
    }

    @Override
    public void actionNight(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgVillagerNightAction);
    }

    @Override
    public void actionDay(RPGPlayer player) {
        if(RpgEngine.killerKilled){
             player.getPlayer().sendMessage(Preferences.rpgVillagerDayActionDied);
        } else {
             player.getPlayer().sendMessage(Preferences.rpgVillagerDayActionNoDied);
        }
    }
    
}
