/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplay.roles;

import com.neocop.roleplayplugin.roleplay.RPGPlayer;
import com.neocop.roleplayplugin.utils.Preferences;

/**
 *
 * @author Noah
 */
public class person implements Role{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgRoleControl + "\nRolle: normale Person");
    }

    @Override
    public void extra(RPGPlayer player, String[] ext) {
        player.getPlayer().sendMessage("§cDiese Rolle hat keine extras!");
    }

    @Override
    public void resetNightsExtra() {
    }
    
}
