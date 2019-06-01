/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.undo;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class undoArmorstand {

    ArmorStand aromrStand;
    Player player;

    public undoArmorstand(ArmorStand aS, Player player) {
        this.aromrStand = aS;
        this.player = player;
    }

    public ArmorStand getArmorStand() {
        return aromrStand;
    }

    public void setArmorStand(ArmorStand aS) {
        this.aromrStand = aS;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
