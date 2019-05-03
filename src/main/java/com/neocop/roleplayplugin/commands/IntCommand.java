/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Noah
 */
public interface IntCommand {
    boolean calledUser(String[] args, CommandSender sender, Command command);
    void actionUser(String[] args, CommandSender sender, Command command);
    void actionServer(String[] args, CommandSender sender, Command command);
}
