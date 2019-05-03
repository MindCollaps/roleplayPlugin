/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.Core;

import java.util.HashMap;
import com.neocop.roleplayplugin.commands.IntCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class commandHandler {

    public static HashMap<String, IntCommand> commands = new HashMap<>();

    public static void handleCommand(commandContainer cmd) {
        if (commands.containsKey(cmd.invoke.toLowerCase())) {
            if (cmd.sender instanceof Player) {
                boolean safe = commands.get(cmd.invoke.toLowerCase()).calledUser(cmd.args, cmd.sender, cmd.command);
                if (safe) {
                    commands.get(cmd.invoke).actionUser(cmd.args, cmd.sender, cmd.command);
                } else {
                    System.out.println("[Error]Command cant executet!");
                }
            } else {
                commands.get(cmd.invoke).actionServer(cmd.args, cmd.sender, cmd.command);
            }
        } else {
            System.out.println("Command cant found");
        }
    }

    public static commandContainer commandParser(String[] args, CommandSender sender, String invoke, Command command) {
        return new commandContainer(args, command, invoke, sender);
    }

    public static class commandContainer {

        public final String[] args;
        public final Command command;
        public final CommandSender sender;
        public final String invoke;

        public commandContainer(String[] arg, Command com, String inv, CommandSender send) {
            this.args = arg;
            this.command = com;
            this.invoke = inv;
            this.sender = send;
        }
    }
}
