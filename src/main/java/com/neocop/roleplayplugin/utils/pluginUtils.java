/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.utils;

import java.util.Random;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 *
 * @author Noah
 */
public class pluginUtils {

    public static boolean isUserCommand(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        } else {
            return false;
        }
    }

    public static void spawnRandomFireworkAroundPlayer(Player p) {
        //Spawn the Firework, get the FireworkMeta.
        Location loc1 = p.getLocation();
        Location loc2 = p.getLocation();
        Location loc3 = p.getLocation();
        Location loc4 = p.getLocation();

        loc1.setX(loc1.getX() + 2);
        loc1.setY(loc1.getY() + 2);
        
        loc2.setX(loc2.getX() - 2);
        loc2.setY(loc2.getY() + 2);
        
        loc3.setZ(loc3.getZ() + 2);
        loc3.setY(loc3.getY() + 2);
        
        loc4.setY(loc4.getY() + 2);
        loc4.setZ(loc4.getZ() - 2);

        Firework fwwt = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fwwt.getFireworkMeta();
        //Our random generator
        Random r = new Random();

        //Get the type
        int rt = r.nextInt(3) + 1;
        Type type = Type.BALL;
        if (rt == 1) {
            type = Type.BALL;
        }
        if (rt == 2) {
            type = Type.BALL_LARGE;
        }
        if (rt == 3) {
            type = Type.BURST;
        }
        if (rt == 4) {
            type = Type.STAR;
        }

        //Get our random colours 
        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getMinecraftColor(r1i);
        Color c2 = getMinecraftColor(r2i);

        //Create our effect with this
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

        //Then apply the effect to the meta
        fwm.addEffect(effect);

        //Generate some random power and set it
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);

        //Then apply this to our rocket
        Firework fw = (Firework) p.getWorld().spawnEntity(loc1, EntityType.FIREWORK);
        fw.setFireworkMeta(fwm);
        fw = (Firework) p.getWorld().spawnEntity(loc2, EntityType.FIREWORK);
        fw.setFireworkMeta(fwm);
        fw = (Firework) p.getWorld().spawnEntity(loc3, EntityType.FIREWORK);
        fw.setFireworkMeta(fwm);
        fw = (Firework) p.getWorld().spawnEntity(loc4, EntityType.FIREWORK);
        fw.setFireworkMeta(fwm);
        try {
            fwwt.remove();
        } catch (Exception e) {
        }
    }

    public static Color getMinecraftColor(int i) {
        Color c = null;
        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLACK;
        }
        if (i == 3) {
            c = Color.BLUE;
        }
        if (i == 4) {
            c = Color.FUCHSIA;
        }
        if (i == 5) {
            c = Color.GRAY;
        }
        if (i == 6) {
            c = Color.GREEN;
        }
        if (i == 7) {
            c = Color.LIME;
        }
        if (i == 8) {
            c = Color.MAROON;
        }
        if (i == 9) {
            c = Color.NAVY;
        }
        if (i == 10) {
            c = Color.OLIVE;
        }
        if (i == 11) {
            c = Color.ORANGE;
        }
        if (i == 12) {
            c = Color.PURPLE;
        }
        if (i == 13) {
            c = Color.RED;
        }
        if (i == 14) {
            c = Color.SILVER;
        }
        if (i == 15) {
            c = Color.TEAL;
        }
        if (i == 16) {
            c = Color.WHITE;
        }
        if (i == 17) {
            c = Color.YELLOW;
        }

        return c;
    }
}
