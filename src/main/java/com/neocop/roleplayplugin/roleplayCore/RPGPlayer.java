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
    
    public RPGPlayer(Player player, RPGRole role){
        this.player = player;
        this.role = role;
    }
    
    public Player getPlayer(){
        return player;
    }
}
