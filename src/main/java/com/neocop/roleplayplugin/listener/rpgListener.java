/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.listener;

import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.roleplayCore.rpgUtils;
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
                    Player prey = Bukkit.getPlayer(i.getItemMeta().getDisplayName());
                    e.getInventory().clear();
                    current.closeInventory();
                    {
                        try {
                            RpgEngine.killPlayer(rpgUtils.getRpgPlayerByName(prey.getDisplayName()), 1);
                            RpgEngine.killerKilled = true;
                        } catch (Exception ex) {
                            System.out.println(ex);
                            current.sendMessage("§cError");
                        }
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
                    System.out.println("Value in: " + in.getItemMeta().getDisplayName());
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
                    rpgUtils.playerDiedOrLeaveWithoutExpection(died);
                }
            }
        }
    }
}
