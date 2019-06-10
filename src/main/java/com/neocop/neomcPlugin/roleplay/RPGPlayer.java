/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.roleplay;

import com.neocop.neomcPlugin.roleplay.roles.RPGRole;
import com.neocop.neomcPlugin.roleplay.abilitys.RPGAbility;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class RPGPlayer {

    public Player player;
    public RPGAbility ability;
    public RPGRole role;

    public boolean protect;
    public RPGPlayer protecter;
    public boolean alive;

    public RPGPlayer(Player player, RPGAbility ability, RPGRole role) {
        this.player = player;
        this.ability = ability;
        this.alive = true;
        this.protect = false;
        this.role = role;
    }

    public RPGRole getRole() {
        return role;
    }

    public void setRole(RPGRole role) {
        this.role = role;
    }
    
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public RPGAbility getAbility() {
        return ability;
    }

    public void setAbility(RPGAbility role) {
        this.ability = role;
    }

    public boolean isProtect() {
        return protect;
    }

    public void setProtect(boolean protect) {
        this.protect = protect;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public RPGPlayer getProtecter() {
        return protecter;
    }

    public void setProtecter(RPGPlayer protecter) {
        this.protecter = protecter;
    }
    
    public void setProtection(RPGPlayer protecter, boolean protect){
        this.protecter = protecter;
        this.protect = protect;
    }
}
