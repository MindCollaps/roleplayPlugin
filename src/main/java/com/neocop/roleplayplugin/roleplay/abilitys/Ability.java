/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplay.abilitys;

import com.neocop.roleplayplugin.roleplay.RPGPlayer;

/**
 *
 * @author Noah
 */
public interface Ability {
    public void start(RPGPlayer player);
    public void actionNight(RPGPlayer player);
    public void actionDay(RPGPlayer player);
}
