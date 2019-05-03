/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.listener;

import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
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
public class rpgListener implements Listener{
    
    
    @EventHandler
    public void killListener(PlayerDeathEvent event){
        
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!RpgEngine.rpgRunning){
            return;
        }
        switch(e.getInventory().getTitle()){
            case "§cKiller":
                e.setCancelled(true);
                ItemStack i = e.getCurrentItem();
                Player prey = Bukkit.getPlayer(i.getItemMeta().getDisplayName());
                e.getInventory().clear();
                Bukkit.getPlayer(e.getView().getPlayer().getUniqueId()).closeInventory();
                RpgEngine.killPlayer(prey, Bukkit.getPlayer(e.getView().getPlayer().getUniqueId()));
                break;
            case "§9Detectiv":
                e.setCancelled(true);
                ItemStack in = e.getCurrentItem();
                Player test = Bukkit.getPlayer(in.getItemMeta().getDisplayName());
                e.getInventory().clear();
                Player current = Bukkit.getPlayer(e.getView().getPlayer().getUniqueId());
                current.closeInventory();
                boolean bad = RpgEngine.analyzePlayer(current, test);
                if(bad){
                    current.sendMessage("§aTestergebnis Positiv! Er ist definitiv der Killer! Bitte allen anderen UNBEDINGT mitteilen!");
                } else {
                    current.sendMessage("§cTestergebnis Negativ! Was nicht bedeutet, dass es kein Killer ist, vielleicht kann er einfach nur gut Lügen.");
                }
                break;
        }
    }
}
