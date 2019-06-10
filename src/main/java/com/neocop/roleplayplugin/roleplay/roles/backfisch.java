/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplay.roles;

import com.neocop.roleplayplugin.roleplay.RPGPlayer;
import com.neocop.roleplayplugin.roleplay.rpgUtils;
import com.neocop.roleplayplugin.utils.Preferences;
import com.neocop.roleplayplugin.utils.pluginUtils;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Noah
 */
public class backfisch implements Role{

    @Override
    public void start(RPGPlayer player) {
        player.getPlayer().sendMessage(Preferences.rpgRoleControl + "\nRolle: Backfisch");
    }

    @Override
    public void extra(RPGPlayer player, String[] ext) {
        try {
            switch(ext[0]){
                case "blubbl":
                    pluginUtils.playSoundToAllPlayers(Sound.AMBIENT_UNDERWATER_ENTER);
                    rpgUtils.sendMessageToAllAliveRpgPlayer("§b" + player.getPlayer().getDisplayName() + " Blubbelt!");
                    break; 
                    
                case "bake":
                    ItemStack stack = player.getPlayer().getItemInHand();
                    if(stack.getType().equals(Material.COD)){
                        stack.setAmount(stack.getAmount()-1);
                        ItemStack fisch = new ItemStack(Material.COOKED_COD);
                        ItemMeta fischMeta = fisch.getItemMeta();
                        fischMeta.setDisplayName("Backfisch");
                        
                        List<String> lore = Arrays.asList(new String[]{"Das ist ein leckerer","gebackener Fisch","lol"});
                        fischMeta.setLore(lore);
                        fisch.setItemMeta(fischMeta);
                        player.getPlayer().getInventory().addItem(fisch);
                        player.getPlayer().sendMessage("§aYou´ve got a fish :D!");
                    } else {
                        player.getPlayer().sendMessage("NÖ DU HAST KEIN FISCH!!!!! HOL DIR ABA SOFORTT EINEN SONST GIBTS STRESS ... lol");
                    }
                    break;
                    
                    default:
                    case "help":
                        player.getPlayer().sendMessage("/rpg extra blubbl - BLUBBL");
                        break;
            }                              
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void resetNightsExtra() {
    }
    
}
