/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.utils.Preferences;

/**
 *
 * @author Noah
 */
public class detektiv implements Role{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendTitle("Deine Rolle ist", "§9Detektiv!");
        player.getPlayer().sendMessage(Preferences.detectiv);
        RpgEngine.villagerTeam.add(player);
        RpgEngine.extraVillager.add(player);
    }

    @Override
    public void actionNight(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgDetectivNightAction);
    }

    @Override
    public void actionDay(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgDetectivDayAction);
    }
    
}
