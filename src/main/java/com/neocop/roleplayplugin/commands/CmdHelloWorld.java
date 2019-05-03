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
public class CmdHelloWorld implements IntCommand{

    @Override
    public boolean calledUser(String[] args, CommandSender sender, Command command) {
        return true;
    }

    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        sender.sendMessage("§cHallo Welt...lOOOl §k12");
    }

    @Override
    public void actionServer(String[] args, CommandSender sender, Command command) {
        System.out.println("Hello Welt...BZW typ..also...Konsolen typ ;D");
    }
    
}
