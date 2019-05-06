/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.Threads;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.rounds;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.rpgRolePlayer;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.stopRpg;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.villagerTeam;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.voteAllowed;
import com.neocop.roleplayplugin.utils.Preferences;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 *
 * @author Noah
 */
public class RpgTimerThread extends Thread {

    @Override
    public void run() {
        System.out.println(Preferences.consoleDes + " External Thread startet!");
        sleep(2000);
        Player p = null;
        Object[] playerz = RpgEngine.rpgRolePlayer.values().toArray();
        RPGPlayer rpP = null;
        for (int i = 0; playerz.length > i; i++) {
            rpP = (RPGPlayer) playerz[i];
            p = rpP.player;
            p.setGameMode(GameMode.SURVIVAL);
            rpgRolePlayer.get(p.getDisplayName()).getRole().getRole().start(rpP);
        }
        Bukkit.broadcast(Preferences.globalRules, null);
        
    }

    private static void ccDay() {
        if (RpgEngine.rounds >= Preferences.roundsValue) {
            RpgEngine.stopRpg(false);
            return;
        } else if (RpgEngine.villagerTeam.size() <= 1) {
            RpgEngine.stopRpg(false);
            return;
        } else {
            RpgEngine.dayRpg();
        }
        sleep(10 * Preferences.daysDuration);
        ccVote();
    }

    private static void ccNight() {
        //undo change from ccVote
        RpgEngine.voteAllowed = false;
        RpgEngine.rounds++;
        RpgEngine.nightRpg();
        sleep(10 * Preferences.nightsDuration);
        ccDay();

    }

    private static void ccVote() {
        RpgEngine.voteRpg();
        sleep(10 * Preferences.voteDuration);
        ccNight();
    }
    
    private static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(RpgTimerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
