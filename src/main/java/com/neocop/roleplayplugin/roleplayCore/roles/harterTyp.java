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
public class harterTyp implements Role {

    boolean extraUse;

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgRoleControl + "\nRolle: harter Typ\nDu bist hier definitiv der coolste und solltest das alle unbedingt wissen lassen!\n Du kannst außerdem jeden Tag einen Spieler bestimmen, den du beschützen möchtest, benutze dafür den §l/rpg extra §rCommand!");
    }

    @Override
    public void extra(RPGPlayer player, String[] ext) {
        try {
            switch (ext[0]) {
                case "protect":
                    if (RpgEngine.villagerTeam.size() >= 1) {
                        if (!player.getPlayer().getDisplayName().equalsIgnoreCase(ext[1])) {
                            if (!extraUse) {
                                try {
                                    RpgEngine.rpgRolePlayer.get(ext[1]).setProtection(player, true);
                                    extraUse = true;
                                } catch (Exception e) {
                                    player.getPlayer().sendMessage("§cSpieler konnte nicht gefunden werden!");
                                    break;
                                }
                                player.getPlayer().sendMessage("§aSpieler §l" + ext[1] + " §r§awird die nächste Nacht beschützt!");
                            } else {
                                player.getPlayer().sendMessage("§cDu hast deine Fähigkeit bereits Eingesetzt!");
                            }
                        } else {
                            player.getPlayer().sendMessage("§cDu kannst dich nicht selbst beschützen!");
                        }
                    } else {
                        player.getPlayer().sendMessage("§cEs müssen mindestens noch zwei Spieler im Dorfteam am leben bleiben!");
                    }
                    break;

                default:
                case "help":
                    player.getPlayer().sendMessage("/rpg extra protect [spieler] - Beschütze einen Spieler");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void resetNightsExtra() {
        this.extraUse = false;
    }
}
