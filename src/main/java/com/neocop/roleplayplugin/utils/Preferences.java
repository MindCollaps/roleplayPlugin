/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neocop.roleplayplugin.utils;

/**
 *
 * @author Noah
 */
public class Preferences {
    public static final String noPermissionOpNeeded = "§cDu hast keine berechtigung für diesen Command! Nur Op´s haben berechtigung!";
    public static final String noPermissionCommandOnlyForUser = "Dieser Command ist nur für den User anwendbar!";
    public static final String notEnoughPlayerForRpg = "§eEs sind nicht genug Spieler in der mitchmach Liste. Füge sie mit dem add command hinzu!";
    public static final String noRunningRpg = "§cEs ist kein Roleplay im gange!";
    
    public static final String playerAddToRpg = "§aDu wurdest zum Roleplay hinzugefügt!";
    public static final String playerNotFound = "§cSpieler konnte nicht gefunden werden oder ist nicht Online!";
    public static final String playerRpgListEmpty = "§eDie Spieler Liste ist leer, füge neue Spieler mit dem add befehl hinzu!";
    public static final String playerRemoveFromRpg = "§eDu wurdest von Roleplay entfernt!";
    public static final String playerAlreadyInRpg = "§eDer Spieler ist bereits im Roleplay!";
    public static final String playerIsNotInRpg = "§eDer Spieler ist nicht im Roleplay!";
    public static final String playerDiedByOtherPlayer = "§eDu wurdest so eben Getötet, verlasse gegebenfalls bitte den Sprachchat, falls eure Roleplay Gruppe einen verwendet!";
    
    public static final String rpgKillerNightAction = "§cWähle, wen du töten willst!";
    public static final String rpgKillerDayAction = "Versuche deine Taten zu verschleiern ;3";
    
    public static final String rpgVillagerDayAction = "Versuche durchs Gespräch heraus zu finden wer jemanden getötet haben könnte!";
    public static final String rpgVillagerNightAction = "Für dich gibt es Nachts nichts zu tun! Gute nacht :3";
    
    public static final String rpgDetectivNightAction = "Für dich gibt es Nachts nichts zu tun! Gute nacht :3";
    public static final String rpgDetectivDayAction = "Versuche durchs Gespräch heraus zu finden wer jemanden getötet haben könnte! Falls du willst, kannst du deinen Lügendetektor benutzen, dies machst du mit dem /detectiv befehl!";
    
    public static final String succPlayerAddToRpg = "§aSpieler wurde dem Roleplay hinzugefügt!";
    public static final String succPlayerRemoveFromRpg = "§aSpieler wurde vom Roleplay entfernt";
    
    public static final String globalPlayerDiedByOtherPlayer = "§eEin Spieler wurde soeben von einer unbekannten Person getötet. Findet den Mörder!";
    public static final String globalRpgGetCanceled = "§cRoleplay wurde unterbrochen!";
    
    public static final String helpRpgCommon = "/rpg start - Startet das Rolepay\n"
            + "/rpg stop - Stoppt das Spiel\n"
            + "/rpg add player <player> - Fügt einen Spieler hinzu\n"
            + "/rpg remove player <player> - Entfernt einen Spieler\n"
            + "/rpg list - Zeigt alle Spieler die am Roleplay beteiligt sind an\n"
            + "/rpg join - Fügt dich selbst zum Roleplay hinzu\n"
            + "/rpg help - Zeigt diese Seite...wer hätte es gedacht..loool";
    
    private static final String striche = "§r§e--------------------------------------------------§r";
    
    public static final String killer = striche + "\n§c§lKiller\n\n§r§dDu hast jede Nacht die Möglichkeit eine Person zu Töten! Dies tust du über das nächtlich erscheinende Inventar!\n" + striche;
    public static final String villager = striche + "\n§a§lVillager\n\n§r§dDu bist ein ganz normaler Dorfbewohner, doch nicht unnötig, sondern ein wichtiger Bestandteil des gesammten Dorfteams. Jeden Tag kannst du abstimmen, wer getötet werden soll! Nutze deine Stimme sinnvoll ;3\n" + striche;
    public static final String detectiv = striche + "\n§9§lDetektiv\n\n§r§dDu bist eine sehr wichtige Rolle, nämlich der Detektiv. Jeden Tag hast du das Sagen und kannst pro Spiel zwie Person mit einer Erfolgschance von 40% einen Lügentest machen lassen, schlägt er aus, weißt du, dass etwas nicht in ordung ist, gibt er allerdings keinen muchs von sich, war es wohl ein Fehlschlag, oder er ist ein Dorfbewohner. Pass auf die Bürger auf ;3\n" + striche;
}
