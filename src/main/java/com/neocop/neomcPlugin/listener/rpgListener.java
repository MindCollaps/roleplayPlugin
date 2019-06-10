/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.listener;

import com.neocop.neomcPlugin.roleplay.RPGPlayer;
import com.neocop.neomcPlugin.roleplay.RpgEngine;
import com.neocop.neomcPlugin.roleplay.rpgUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Noah
 */
public class rpgListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!RpgEngine.rpgRunning) {
            return;
        }
        Player current = Bukkit.getPlayer(e.getView().getPlayer().getUniqueId());
        switch (e.getInventory().getTitle()) {
            case "§cKiller":
                e.setCancelled(true);
                if (RpgEngine.phase == 0) {
                    ItemStack i = e.getCurrentItem();
                    RPGPlayer preyRpg = null;
                    try {
                        preyRpg = rpgUtils.getRpgPlayerByName(i.getItemMeta().getDisplayName());
                    } catch (Exception ex) {
                        current.sendMessage("§cError");
                        break;
                    }
                    Player prey = preyRpg.getPlayer();
                    e.getInventory().clear();
                    current.closeInventory();
                    //Harter typ beschützt
                    if(preyRpg.isProtect()){
                        current.sendMessage("§cDein Opfer konnte nicht Sterben! Vielleicht wird es beschützt");
                        prey.sendMessage("§aDu wurdest vor dem Sterben bewart! Bedank dich bei §l" + preyRpg.getProtecter().getPlayer().getDisplayName() + " §r§afür deine Rettung!");
                        preyRpg.getProtecter().getPlayer().sendMessage("§aDu hast §l" + prey.getDisplayName() + "§r§a beschützt!");
                        preyRpg.getProtecter().getRole().getRole().resetNightsExtra();
                        RpgEngine.rpgRolePlayer.get(prey.getDisplayName()).setProtect(false);
                        RpgEngine.rpgRolePlayer.get(prey.getDisplayName()).setProtecter(null);
                        break;
                    }
                    try {
                        RpgEngine.killPlayer(rpgUtils.getRpgPlayerByName(prey.getDisplayName()), 1);
                        RpgEngine.killerKilled = true;
                    } catch (Exception ex) {
                        System.out.println(ex);
                        current.sendMessage("§cError");
                        break;
                    }

                } else {
                    e.getInventory().clear();
                    current.closeInventory();
                    current.sendMessage("§cDies ist nur in der Nacht möglich!");
                }
                break;
            case "§9Detectiv":
                try {
                    e.setCancelled(true);
                    ItemStack in = e.getCurrentItem();
                    Player test = Bukkit.getPlayer(in.getItemMeta().getDisplayName());
                    e.getInventory().clear();
                    current.closeInventory();
                    boolean bad = RpgEngine.analyzePlayer(current, test);

                    if (bad) {
                        current.sendMessage("§aTestergebnis Positiv! Er ist definitiv der Killer! Bitte allen anderen UNBEDINGT mitteilen!");
                    } else {
                        current.sendMessage("§cTestergebnis Negativ! Was nicht bedeutet, dass es kein Killer ist, vielleicht kann er einfach nur gut Lügen.");
                    }
                } catch (Exception x) {
                    System.out.println(x);
                    break;
                }
                break;
        }
    }

    //Unvorhergesehner Tod
    @EventHandler
    public void playerDied(PlayerDeathEvent event) {
        if (RpgEngine.rpgRunning) {
            Player died = event.getEntity();
            if (RpgEngine.rpgPlayer.containsKey(died.getDisplayName())) {
                if (!RpgEngine.killedPlayer.contains(died.getDisplayName())) {
                    try {
                        RPGPlayer rpP = rpgUtils.getRpgPlayerByName(died.getDisplayName());
                        RpgEngine.killPlayerWhichLeftWithoutExpection(rpP);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }
}
