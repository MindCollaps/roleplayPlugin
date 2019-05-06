/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;

/**
 *
 * @author Noah
 */
public interface Role {
    public void start(RPGPlayer player);
    public void actionNight(RPGPlayer player);
    public void actionDay(RPGPlayer player);
}
