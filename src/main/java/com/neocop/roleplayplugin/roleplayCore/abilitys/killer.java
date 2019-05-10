/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.abilitys;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.utils.Preferences;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        Object[] players = RpgEngine.rpgRolePlayer.values().toArray();
        RPGPlayer p = null;
        int count = 9;
        for (int i = 0; players.length > i; i++) {
            p =  (RPGPlayer) players[i];
            count++;
            if (p.getAbility().getRoleTyp() == 0) {
                count--;
            } else {
                skullMeta.setOwner(p.getPlayer().getDisplayName());
                skullMeta.setDisplayName(p.getPlayer().getDisplayName());
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
