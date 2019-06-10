/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

/**
 *
 * @author Noah
 */
public class RPGRole {
    Role role;
    String roleName;
    int maxAmount;
    
    public RPGRole(String roleName, Role role, int maxAmount){
        this.role = role;
        this.roleName = roleName;
        this.maxAmount = maxAmount;
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

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }
}
