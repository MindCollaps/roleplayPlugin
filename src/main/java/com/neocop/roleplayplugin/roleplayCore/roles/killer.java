/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.roles;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import com.neocop.roleplayplugin.utils.Preferences;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author Noah
 */
public class killer implements rpgRole {

    @Override
    public void start(Player player) {
        player.sendTitle("Deine Rolle ist", "§cMurder!");
        player.sendMessage(Preferences.killer);
        RpgEngine.killer.add(new RPGPlayer(player, this));
    }

    @Override
    public void actionNight(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 3, "§cKiller");

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        Object[] players = RpgEngine.rpgPlayer.values().toArray();
        Player p = null;
        for (int i = 0; players.length > i; i++) {
            p = (Player) players[i];
            skullMeta.setOwner(p.getDisplayName());
            skullMeta.setDisplayName(p.getDisplayName());
            skull.setItemMeta(skullMeta);
            inv.setItem(10 +i, skull);
        }
        player.openInventory(inv);
        player.sendMessage(Preferences.rpgKillerNightAction);
    }

    @Override
    public void actionDay(Player player) {
        player.sendMessage(Preferences.rpgKillerDayAction);
    }
}
