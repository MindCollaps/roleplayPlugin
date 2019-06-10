/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.abilitys;

import com.neocop.roleplayplugin.roleplayCore.abilitys.Ability;
import com.neocop.roleplayplugin.roleplayCore.roles.Role;

/**
 *
 * @author Noah
 */
public class RPGAbility {

    Ability ability;
    String abilityName;
    int neededPlayers;
    int data;
    
    //0 = killer, 1 = villager, 2 = solo, 3 = team
    int roleTyp;
    
    public int killer = 0;
    public int villager = 1;
    public int extraVillager = 2;
    
    public RPGAbility(Ability ability, String abilityName, int abilityType, int neededPlayers){
        this.ability = ability;
        this.abilityName = abilityName;
        this.roleTyp = abilityType;
        this.neededPlayers = neededPlayers;
    }
    
    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public int getNeededPlayers() {
        return neededPlayers;
    }

    public void setNeededPlayers(int neededPlayers) {
        this.neededPlayers = neededPlayers;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getRoleTyp() {
        return roleTyp;
    }

    public void setRoleTyp(int roleTyp) {
        this.roleTyp = roleTyp;
    }

    public int getKiller() {
        return killer;
    }

    public void setKiller(int killer) {
        this.killer = killer;
    }

    public int getVillager() {
        return villager;
    }

    public void setVillager(int villager) {
        this.villager = villager;
    }

    public int getExtraVillager() {
        return extraVillager;
    }

    public void setExtraVillager(int extraVillager) {
        this.extraVillager = extraVillager;
    }
}
