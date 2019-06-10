/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.commands;

import com.neocop.roleplayplugin.undo.undoArmorstand;
import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.pluginUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author Noah
 */
public class CmdUtils implements IntCommand {
    
    ArrayList<undoArmorstand> titles = new ArrayList<>();
    ArrayList<undoArmorstand> players = new ArrayList<>();
    
    @Override
    public boolean calledUser(String[] args, CommandSender sender, Command command) {
        Player p = (Player) sender;
        if (p.isOp()) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void actionUser(String[] args, CommandSender sender, Command command) {
        Player p = (Player) sender;
        try {
            if (args.length <= 0) {
                p.sendMessage(Preferences.helpUtilCommon);
                return;
            }
        } catch (Exception e) {
            p.sendMessage(Preferences.helpUtilCommon);
        }
        if (args.length >= 0) {
            switch (args[0]) {
                case "adTest":
                    ItemStack stack = p.getItemInHand();
                    if(stack.getType().equals(Material.COD)){
                        stack.setAmount(stack.getAmount()-1);
                        ItemStack fisch = new ItemStack(Material.COOKED_COD);
                        ItemMeta fischMeta = fisch.getItemMeta();
                        fischMeta.setDisplayName("Backfisch");
                        
                        List<String> lore = Arrays.asList(new String[]{"Das ist ein leckerer","gebackener Fisch","lol"});
                        fischMeta.setLore(lore);
                        fisch.setItemMeta(fischMeta);
                        p.getInventory().addItem(fisch);
                        p.sendMessage("§cYou´ve got a fish :D!");
                    } else {
                        p.sendMessage("NÖ DU HAST KEIN FISCH!!!!! HOL DIR ABA SOFORTT EINEN SONST GIBTS STRESS ... lol");
                    }
                    break;
                    
                case "spawn":
                    if (args.length >= 1) {
                        ArmorStand aS;
                        Player tar;
                        switch (args[1]) {
                            
                            case "title":
                                aS = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
                                aS.setGravity(false);
                                aS.setCanPickupItems(false);
                                aS.setVisible(false);
                                aS.setBasePlate(false);
                                aS.setCustomName(args[2]);
                                aS.setCustomNameVisible(true);
                                titles.add(new undoArmorstand(aS, p));
                                break;
                            
                            case "player":
                                try {
                                    tar = Bukkit.getPlayer(args[2]);
                                } catch (Exception e) {
                                    p.sendMessage("§cPlayer cant found");
                                    break;
                                }
                                aS = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
                                aS.setGravity(false);
                                aS.setVisible(true);
                                aS.setArms(true);
                                aS.setBasePlate(false);
                                aS.setCustomName(tar.getDisplayName());
                                aS.setCustomNameVisible(true);
                                
                                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                                SkullMeta skullM = (SkullMeta) skull.getItemMeta();
                                skullM.setDisplayName(tar.getDisplayName() + "´s head");
                                skullM.setOwner(tar.getDisplayName());
                                skull.setItemMeta(skullM);
                                
                                aS.setHelmet(skull);
                                aS.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                                aS.setBoots(new ItemStack(Material.LEATHER_BOOTS));
                                aS.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                                
                                players.add(new undoArmorstand(aS, p));
                                break;
                            
                            default:
                                p.sendMessage(Preferences.helpUtilCommon);
                                break;
                        }
                    }
                    break;
                
                case "prop":
                    if (args.length >= 1) {
                        switch (args[1]) {
                            
                            case "title":
                                ArrayList<ArmorStand> titleList = getTitleFromPlayer(p);
                                if (args.length >= 2) {
                                    switch (args[2]) {
                                        
                                        case "set":
                                            ArmorStand aS = titleList.get(titleList.size() - 1);
                                            String c;
                                            String before = aS.getCustomName();
                                            if (args.length >= 3) {
                                                switch (args[3]) {
                                                    
                                                    case "name":
                                                        aS.setCustomName(args[4]);
                                                        break;
                                                        
                                                    case "color":
                                                        c = pluginUtils.getMinecraftColorCode(args[4]);
                                                        aS.setCustomName("loading");
                                                        aS.setCustomName(c + before);
                                                        break;
                                                }
                                            }
                                            break;
                                        
                                        case "remove":
                                            titleList.get(titleList.size() - 1).remove();
                                            titles.remove(getIntOfTitles(p));
                                            break;
                                        default:
                                            p.sendMessage(Preferences.helpUtilCommon);
                                            break;
                                    }
                                }
                                
                                break;
                            
                            case "player":
                                ArrayList<ArmorStand> playerList = getPlayerFromPlayer(p);
                                if (args.length >= 2) {
                                    switch (args[2]) {
                                        
                                        case "set":
                                            if (args.length >= 3) {
                                                ArmorStand aS = playerList.get(playerList.size() - 1);
                                                String before = aS.getCustomName();
                                                switch (args[3]) {
                                                    
                                                    case "name":
                                                        playerList.get(playerList.size() - 1).setCustomName(args[4]);
                                                        break;
                                                        
                                                    case "color":
                                                        String c = pluginUtils.getMinecraftColorCode(args[4]);
                                                        aS.setCustomName("loading");
                                                        aS.setCustomName(c + before);
                                                        break;
                                                        
                                                    case "cloth":
                                                            switch(args[4]){
                                                                case "helmet":
                                                                    aS.setHelmet(pluginUtils.getCloth(args[5]));
                                                                    break;
                                                                case "chestplate":
                                                                    aS.setChestplate(pluginUtils.getCloth(args[5]));
                                                                    break;
                                                                case "leggins":
                                                                    aS.setLeggings(pluginUtils.getCloth(args[5]));
                                                                    break;
                                                                case "boots":
                                                                    aS.setBoots(pluginUtils.getCloth(args[5]));
                                                                    break;
                                                        }
                                                        break;
                                                }
                                            }
                                            break;
                                        
                                        case "remove":
                                            playerList.get(playerList.size() - 1).remove();
                                            players.remove(getIntOfPlayers(p));
                                            break;
                                        
                                        default:
                                            p.sendMessage(Preferences.helpUtilCommon);
                                            break;
                                    }
                                }
                                break;
                            
                            case "help":
                            default:
                                p.sendMessage(Preferences.helpUtilCommon);
                                break;
                        }
                    }
                    break;
                case "help":
                default:
                    p.sendMessage(Preferences.helpUtilCommon);
                    break;
            }
        } else {
            p.sendMessage(Preferences.helpUtilCommon);
        }
    }
    
    @Override
    public void actionServer(String[] args, CommandSender sender, Command command) {
        System.out.println(Preferences.noPermissionCommandOnlyForUser);
    }
    
    public ArrayList<ArmorStand> getTitleFromPlayer(Player player) {
        undoArmorstand s = null;
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            s = titles.get(i);
            if (s.getPlayer().getDisplayName().equalsIgnoreCase(player.getDisplayName())) {
                list.add(s.getArmorStand());
            }
        }
        return list;
    }
    
    public ArrayList<ArmorStand> getPlayerFromPlayer(Player player) {
        undoArmorstand s = null;
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            s = players.get(i);
            if (s.getPlayer().getDisplayName().equalsIgnoreCase(player.getDisplayName())) {
                list.add(s.getArmorStand());
            }
        }
        return list;
    }
    
    private int getIntOfTitles(Player player) {
        undoArmorstand s = null;
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            s = titles.get(i);
            if (s.getPlayer().getDisplayName().equalsIgnoreCase(player.getDisplayName())) {
                list.add(s.getArmorStand());
            }
        }
        return list.size()-1;
    }
    
    private int getIntOfPlayers(Player player) {
        undoArmorstand s = null;
        ArrayList list = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            s = players.get(i);
            if (s.getPlayer().getDisplayName().equalsIgnoreCase(player.getDisplayName())) {
                list.add(s.getArmorStand());
            }
        }
        return list.size()-1;
    }
}
