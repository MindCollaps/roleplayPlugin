/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.neomcPlugin.netconnect;

import com.neocop.neomcPlugin.roleplay.RpgEngine;
import com.neocop.neomcPlugin.utils.pluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class interactModul {

    public static String validate(String[] msg) {
        switch (msg[0].toLowerCase()) {
            case "get":
                switch (msg[1].toLowerCase()) {
                    case "onlineplayers":
                        synchronized (RpgEngine.onlinePlayer) {
                            Object[] players = RpgEngine.onlinePlayer.values().toArray();
                            Player p = null;
                            String playernames = "";
                            for (int i = 0; i < players.length; i++) {
                                p = (Player) players[i];
                                playernames = playernames + "," + p.getDisplayName();
                            }
                            return "onlineplayers (" + String.valueOf(RpgEngine.onlinePlayer.size()) + ") {" + playernames + "}";
                        }
                }
                return "0X10";

            case "set":
                return "not supported jet!";

            case "cmd":
                try {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), pluginUtils.getStringFromArray(1, msg.length, " ", msg));
                } catch (Exception e) {
                    e.printStackTrace();
                    return "0X01";
                }
                return "Dispatched command!";
        }
        return "0X10";
    }

}
