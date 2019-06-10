/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.utils;

import com.neocop.roleplayplugin.roleplay.RpgEngine;
import static com.neocop.roleplayplugin.roleplay.RpgEngine.rpgRoles;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    
    public static void generatePortAdminKey(){
        System.out.println("--------------\nGenerating Port Admin Key!");
            int length = 5;
           String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                        + "abcdefghijklmnopqrstuvwxyz"
                        + "0123456789"
                        + "#-_<>";
                String str = new Random().ints(length, 0, chars.length())
                        .mapToObj(i -> "" + chars.charAt(i))
                        .collect(Collectors.joining());
            Preferences.portAdminKey = str;
            System.out.println("Port Admin Key:\n" + str + "\n--------------");
    }
    
    public static void playSoundToAllPlayers(Sound sound){
        Object[] players = RpgEngine.onlinePlayer.values().toArray();
        Player p = null;
        for (int i = 0; i < players.length; i++) {
            p = (Player) players[i];
            p.playSound(p.getLocation(), sound, 1.0F, 0);
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

    public static String getMinecraftColorCode(String s) {
        String c = "";
        if (s.equalsIgnoreCase("black")) {
            c = "§0";
        }
        if (s.equalsIgnoreCase("dark_blue")) {
            c = "§1";
        }
        if (s.equalsIgnoreCase("dark_green")) {
            c = "§2";
        }
        if (s.equalsIgnoreCase("dark_aqua")) {
            c = "§3";
        }
        if (s.equalsIgnoreCase("dark_red")) {
            c = "§4";
        }
        if (s.equalsIgnoreCase("dark_purple")) {
            c = "§5";
        }
        if (s.equalsIgnoreCase("gold")) {
            c = "§6";
        }
        if (s.equalsIgnoreCase("gray")) {
            c = "§7";
        }
        if (s.equalsIgnoreCase("dark_gray")) {
            c = "§8";
        }
        if (s.equalsIgnoreCase("blue")) {
            c = "§9";
        }
        if (s.equalsIgnoreCase("green")) {
            c = "§a";
        }
        if (s.equalsIgnoreCase("aqua")) {
            c = "§b";
        }
        if (s.equalsIgnoreCase("red")) {
            c = "§c";
        }
        if (s.equalsIgnoreCase("light_purple")) {
            c = "§d";
        }
        if (s.equalsIgnoreCase("yellow")) {
            c = "§e";
        }
        if (s.equalsIgnoreCase("white")) {
            c = "§f";
        }

        return c;
    }
    
    public static ItemStack getCloth(String clothing){
        ItemStack is = null;
        
        if(clothing.equalsIgnoreCase("leather_helmet")){
            is = new ItemStack(Material.LEATHER_HELMET);
        }
        if(clothing.equalsIgnoreCase("leather_chestplate")){
            is = new ItemStack(Material.LEATHER_CHESTPLATE);
        }
        if(clothing.equalsIgnoreCase("leather_leggins")){
            is = new ItemStack(Material.LEATHER_LEGGINGS);
        }
        if(clothing.equalsIgnoreCase("leather_boots")){
            is = new ItemStack(Material.LEATHER_BOOTS);
        }
        if(clothing.equalsIgnoreCase("chainmail_helmet")){
            is = new ItemStack(Material.CHAINMAIL_HELMET);
        }
        if(clothing.equalsIgnoreCase("chainmail_chestplate")){
            is = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        }
        if(clothing.equalsIgnoreCase("chainmail_leggins")){
            is = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        }
        if(clothing.equalsIgnoreCase("chainmail_boots")){
            is = new ItemStack(Material.CHAINMAIL_BOOTS);
        }
        if(clothing.equalsIgnoreCase("iron_helmet")){
            is = new ItemStack(Material.IRON_HELMET);
        }
        if(clothing.equalsIgnoreCase("iron_chesplate")){
            is = new ItemStack(Material.IRON_CHESTPLATE);
        }
        if(clothing.equalsIgnoreCase("iron_leggins")){
            is = new ItemStack(Material.IRON_LEGGINGS);
        }
        if(clothing.equalsIgnoreCase("iron_boots")){
            is = new ItemStack(Material.IRON_BOOTS);
        }
        if(clothing.equalsIgnoreCase("diamond_helmet")){
            is = new ItemStack(Material.DIAMOND_HELMET);
        }
        if(clothing.equalsIgnoreCase("diamond_chestplate")){
            is = new ItemStack(Material.DIAMOND_CHESTPLATE);
        }
        if(clothing.equalsIgnoreCase("diamond_leggins")){
            is = new ItemStack(Material.DIAMOND_LEGGINGS);
        }
        if(clothing.equalsIgnoreCase("diamond_boots")){
            is = new ItemStack(Material.DIAMOND_BOOTS);
        }
        if(clothing.equalsIgnoreCase("golden_helmet")){
            is = new ItemStack(Material.GOLDEN_HELMET);
        }
        if(clothing.equalsIgnoreCase("golden_chestplate")){
            is = new ItemStack(Material.GOLDEN_CHESTPLATE);
        }
        if(clothing.equalsIgnoreCase("golden_leggins")){
            is = new ItemStack(Material.GOLDEN_LEGGINGS);
        }
        if(clothing.equalsIgnoreCase("golden_boots")){
            is = new ItemStack(Material.GOLDEN_BOOTS);
        }
        return is;
    }
}
