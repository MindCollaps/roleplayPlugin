/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.roleplay.abilitys;

import com.neocop.neomcPlugin.roleplay.RPGPlayer;
import com.neocop.neomcPlugin.roleplay.RpgEngine;
import com.neocop.neomcPlugin.utils.Preferences;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

/**
 *
 * @author Noah
 */
public class killer implements Ability {

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendTitle("Deine Rolle ist", "§cMurderer!");
        player.getPlayer().sendMessage(Preferences.killer);
        RpgEngine.killerTeam.put(player.getPlayer().getDisplayName(), player);
        RpgEngine.killerSc.addPlayer(player.player);
    }

    @Override
    public void actionNight(RPGPlayer player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 3, "§cKiller");

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        RPGPlayer p = null;
        int count = 9;
        for (int i = 0; players.length > i; i++) {
            p = (RPGPlayer) players[i];
            count++;
            if (p.getAbility().getRoleTyp() == 0) {
                count--;
            } else {
                skullMeta.setDisplayName(p.getPlayer().getDisplayName());
                skullMeta.setOwner(p.getPlayer().getDisplayName());
                skull.setItemMeta(skullMeta);
                inv.setItem(count, skull);
            }
        }
        player.getPlayer().openInventory(inv);
        player.getPlayer().sendMessage(Preferences.rpgKillerNightAction);
    }

    @Override
    public void actionDay(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgKillerDayAction);
    }
}
