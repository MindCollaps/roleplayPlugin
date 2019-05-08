/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import static org.bukkit.NamespacedKey.minecraft;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Noah
 */
public class CmdTrole implements IntCommand {

    @Override
    public boolean calledUser(String[] args, CommandSender sender, Command command) {
        if (sender.isOp()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        Player current = (Player) sender;
        switch (args[0]) {
            case "view":
                if (args.length > 1) {
                    Player tar = null;
                    try {
                        tar = Bukkit.getPlayer(args[1]);
                    } catch (Exception e) {
                        sender.sendMessage("Kein Spieler gefunden");
                        return;
                    }
                    int times = Integer.valueOf(args[2]);
                    for (int i = 0; i < times; i++) {
                        Block target = tar.getTargetBlock(null, 0);
                        if (target.getType().isBlock()) {
                            target.breakNaturally();
                        }
                    }
                } else {
                    current.sendMessage("Bitte spieler angeben!");
                }
                break;

            case "sickofdiamonds":
                if (args.length > 1) {
                    Player tar = null;
                    try {
                        tar = Bukkit.getPlayer(args[1]);
                    } catch (Exception e) {
                        sender.sendMessage("Nope, wohl eher nicht...");
                        return;
                    }
                    int times = Integer.valueOf(args[2]);
                    for (int i = 0; i < times; i++) {
                        Block target = tar.getTargetBlock(null, 0);
                        if (target.getType().getId() == 57) {
                            tar.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1, 10));
                        } else {
                            current.sendMessage("Falsch!");
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void actionServer(String[] args, CommandSender sender, Command command) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
