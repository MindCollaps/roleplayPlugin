/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.rpgUtils;
import com.neocop.roleplayplugin.utils.Preferences;

/**
 *
 * @author Noah
 */
public class kpopFan implements Role{
    RPGPlayer kpopStar;
    boolean kpopStarAv = true;

    @Override
    public void start(RPGPlayer player) {
        String kStar;
        try {
            this.kpopStar = rpgUtils.getRPGPlayerByRoleName("kpopstar");
        } catch (Exception ex) {
            kpopStarAv = false;
        }
        if(kpopStarAv){
            kStar = "§aOh mein Gott, ein K-Pop Star ist mit im Spiel OwO. Sein Name ist §l" + kpopStar.getPlayer().getDisplayName() + "§r§a!";
        } else {
            kStar = "§cDiese Runde gibt es keinen K-Pop Star!";
        }
        player.getPlayer().sendMessage(Preferences.rpgRoleControl + "\nRolle: K-Pop Fan\n"+kStar);
    }

    @Override
    public void extra(RPGPlayer player, String[] ext) {
        player.getPlayer().sendMessage("§cDiese Rolle hat keine extras!");
    }

    @Override
    public void resetNightsExtra() {
    }
    
}
