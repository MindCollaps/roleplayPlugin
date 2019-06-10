/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplay.roles;

import com.neocop.roleplayplugin.roleplay.RPGPlayer;

/**
 *
 * @author Noah
 */
public interface Role {
    public void start(RPGPlayer player);
    public void extra(RPGPlayer player, String[] ext);
    public void resetNightsExtra();
}
