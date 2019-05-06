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
public class villager implements Role{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendTitle("Deine Rolle ist", "§aVillager!");
        player.getPlayer().sendMessage(Preferences.villager);
        RpgEngine.villagerTeam.add(player);
    }

    @Override
    public void actionNight(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgVillagerNightAction);
    }

    @Override
    public void actionDay(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgVillagerDayAction);
    }
    
}
