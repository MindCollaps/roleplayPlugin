/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore;

import com.neocop.roleplayplugin.roleplayCore.roles.Role;

/**
 *
 * @author Noah
 */
public class RPGRole {

    Role role;
    String roleName;
    int neededPlayers;
    
    //0 = killer, 1 = villager, 2 = solo, 3 = team
    int roleTyp;
    
    public int killer = 0;
    public int villager = 1;
    public int extraVillager = 2;
    
    public RPGRole(Role role, String roleName, int roleTyp, int neededPlayers){
        this.role = role;
        this.roleName = roleName;
        this.roleTyp = roleTyp;
        this.neededPlayers = neededPlayers;
    } 
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public int getNeededPlayers() {
        return neededPlayers;
    }

    public void setNeededPlayers(int neededPlayers) {
        this.neededPlayers = neededPlayers;
    }
    
    public int getRoleTyp() {
        return roleTyp;
    }

    public void setRoleTyp(int roleTyp) {
        this.roleTyp = roleTyp;
    }
}
