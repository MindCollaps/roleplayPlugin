/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore;

import org.bukkit.entity.Player;
import com.neocop.roleplayplugin.roleplayCore.roles.RPGRole;

/**
 *
 * @author Noah
 */
public class RPGPlayer {
    
    Player player;
    RPGRole role;
    String roleName;
    boolean protect;
    boolean alive;
    
    public RPGPlayer(Player player, RPGRole role, String roleName){
        this.player = player;
        this.role = role;
        this.roleName = roleName;
        this.alive = true;
        this.protect = false;
    }
    
        public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public RPGRole getRole() {
        return role;
    }

    public void setRole(RPGRole role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
}
