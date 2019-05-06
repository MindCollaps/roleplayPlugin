/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class pluginUtils {
    
    public static boolean isUserCommand(CommandSender sender){
        if(sender instanceof Player){
            return true;
        } else {
            return false;
        }
    }
    
    public static void sendBrodcastMessage(String msg){
        Bukkit.broadcast(msg, null);
    }
}
