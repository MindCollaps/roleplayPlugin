/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public interface RPGRole {
    public void start(Player player);
    public void actionNight(Player player);
    public void actionDay(Player player);
}
