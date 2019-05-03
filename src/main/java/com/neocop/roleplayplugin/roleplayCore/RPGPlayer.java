/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore;

import com.neocop.roleplayplugin.roleplayCore.roles.rpgRole;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class RPGPlayer {
    
    Player player;
    rpgRole role;
    
    public RPGPlayer(Player player, rpgRole role){
        this.player = player;
        this.role = role;
    }
    
    public Player getPlayer(){
        return player;
    }
}
