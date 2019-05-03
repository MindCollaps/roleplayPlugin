/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.utils.Preferences;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class detektiv implements rpgRole{

    @Override
    public void start(Player player) {
        player.sendTitle("Deine Rolle ist", "§9Detektiv!");
        player.sendMessage(Preferences.detectiv);
        RpgEngine.villagerTeam.add(new RPGPlayer(player, this));
        RpgEngine.detective.add(new RPGPlayer(player, this));
    }

    @Override
    public void actionNight(Player player) {
        player.sendMessage(Preferences.rpgDetectivNightAction);
    }

    @Override
    public void actionDay(Player player) {
        player.sendMessage(Preferences.rpgDetectivDayAction);
    }
    
}
