/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.roleplay.roles;

import com.neocop.neomcPlugin.roleplay.RPGPlayer;

/**
 *
 * @author Noah
 */
public interface Role {
    public void start(RPGPlayer player);
    public void extra(RPGPlayer player, String[] ext);
    public void resetNightsExtra();
}
