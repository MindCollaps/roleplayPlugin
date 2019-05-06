/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.roleplayCore.Threads;

import com.neocop.roleplayplugin.roleplayCore.RPGPlayer;
import com.neocop.roleplayplugin.roleplayCore.RpgEngine;
import static com.neocop.roleplayplugin.roleplayCore.RpgEngine.rpgRolePlayer;
import com.neocop.roleplayplugin.utils.Preferences;
import java.util.Timer;
import java.util.TimerTask;
import jdk.nashorn.internal.codegen.CompilerConstants;
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
        Player p = null;
        Object[] playerz = RpgEngine.rpgRolePlayer.values().toArray();
        RPGPlayer rpP = null;
        for (int i = 0; playerz.length > i; i++) {
            rpP = (RPGPlayer) playerz[i];
            p = rpP.player;
            p.setGameMode(GameMode.SURVIVAL);
            rpgRolePlayer.get(p.getDisplayName()).getRole().getRole().start(rpP);
        }
        System.out.println("sos");
        ccNight();
        //pluginUtils.sendBrodcastMessage(Preferences.globalRules);
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
        
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ccVote();
            }
        }, 10 * Preferences.daysDuration);
    }

    private static void ccNight() {
        //undo change from ccVote
        RpgEngine.voteAllowed = false;
        RpgEngine.rounds++;
        RpgEngine.nightRpg();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ccDay();
            }
        }, 10 * Preferences.nightsDuration);
    }

    private static void ccVote() {
        RpgEngine.voteRpg();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ccNight();
            }
        }, 10 * Preferences.voteDuration);
        
    }
    
    public static void exit() {
        RpgTimerThread.exit();
    }
}
