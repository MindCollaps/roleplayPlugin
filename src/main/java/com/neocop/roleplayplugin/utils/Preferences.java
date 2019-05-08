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

    public static int daysDuration = 60;
    public static int nightsDuration = 30;
    public static int voteDuration = 30;
    
    public static final String version = "1.0-SNAPSHOT";

    public static final String striche = "\n§r§e--------------------------------------------------§r\n";
    
    public static final String consoleDes = "[Roleplay Plugin]";

    public static final String commandNameRpg = "rpg";
    public static final String commandNameDetective = "detective";

    public static final String commandModDetectiveAnalyze = "analyze";

    public static final String noPermissionOpNeeded = "§cDu hast keine berechtigung für diesen Command! Nur Op´s haben berechtigung!";
    public static final String noPermissionCommandOnlyForUser = "Dieser Command ist nur für den User anwendbar!";
    public static final String noPermissionAlreadyVoted = "§cDu hast bereits deine Stimme abgegeben!";
    public static final String noPermissionDetectiveRoleNeeded = "§cDu musst detective sein, um diesen Befehl auszuführen!";
    public static final String noPermissionAlreadyDead = "§cDu bist bereits gestorben!";
    public static final String noPermissionVoteNotActive = "§cVoten ist nur während der Vote Phase möglich!";
    public static final String notEnoughPlayerForRpg = "§eEs sind nicht genug Spieler in der Spieler Liste. Füge sie mit dem add command hinzu!";
    public static final String noRunningRpg = "§cEs ist kein Roleplay im gange!";

    public static final String playerAddToRpg = "§aDu wurdest zum Roleplay hinzugefügt!";
    public static final String playerNotFound = "§cSpieler konnte nicht gefunden werden oder ist nicht Online!";
    public static final String playerRpgListEmpty = "§eDie Spieler Liste ist leer, füge neue Spieler mit dem add befehl hinzu!";
    public static final String playerRemoveFromRpg = "§eDu wurdest von Roleplay entfernt!";
    public static final String playerAlreadyInRpg = "§eDer Spieler ist bereits im Roleplay!";
    public static final String playerIsNotInRpg = "§eDer Spieler ist nicht im Roleplay!";
    public static final String playerAddToVoteList = "§aSpieler wurde auf die Votelist gesetzt!";
    public static final String playerDiedByVoteKill = "§eDu wurdest so eben von der Abstimmung getötet, verlasse gegebenfalls bitte den Sprachchat, falls eure Roleplay Gruppe einen verwendet!";
    public static final String playerDiedByOtherPlayer = "§eDu wurdest so eben von einem Spieler Getötet, verlasse gegebenfalls bitte den Sprachchat, falls eure Roleplay Gruppe einen verwendet!";

    public static final String rpgKillerNightAction = "Guten Abend\n§cWähle, wen du töten willst!";
    public static final String rpgKillerDayAction = "Guten Morgen!\nVersuche deine Taten zu verschleiern ;3";

    public static final String rpgVillagerDayAction = "Guten Morgen!\nVersuche durchs Gespräch heraus zu finden wer jemanden getötet haben könnte!";
    public static final String rpgVillagerNightAction = "Nachti :3\nFür dich gibt es Nachts nichts zu tun! Gute nacht :3";

    public static final String rpgDetectivNightAction = "Guten Abend\nFür dich gibt es Nachts nichts zu tun! Gute nacht :3";
    public static final String rpgDetectivDayAction = "Guten Abend\nVersuche durchs Gespräch heraus zu finden wer jemanden getötet haben könnte! Falls du willst, kannst du deinen Lügendetektor benutzen, dies machst du mit dem /detectiv befehl!";

    public static final String succPlayerAddToRpg = "§aSpieler wurde dem Roleplay hinzugefügt!";
    public static final String succPlayerRemoveFromRpg = "§aSpieler wurde vom Roleplay entfernt";

    public static final String globalPlayerDiedByOtherPlayer = "§eEin Spieler wurde soeben von einer unbekannten Person getötet. Findet den Mörder!";
    public static final String globalRpgGetCanceled = "§cRoleplay wurde unterbrochen!";
    public static final String globalRules = striche + "§e§lRules§r\n\nEs geht sofort los, nur noch ein paar grundlegende Sachen. \nEigentlich ganz einfach:\n-Vorzeitiges Sterben (durch äußere einflüsse) oder verlassen des Servers, führt zum Spielauschluss\n-Reportet Bugs\n-Seid nett\n-Habt spaß" + striche;
    public static final String globalNobodyDied = "§eDas dorf hat sich nicht entscheiden können, wer stirbt!";
    public static final String globalVoteBegin = striche + "§2§lVoting§r\n\nBitte stimmt jetzt mit dem vote command ab, wer diese Runde sterben soll. Es müssen mindestens 3/4 dafür sein, sonst findet keine Hinrichtung statt!" + striche;

    public static final String helpRpgCommon = "/" + commandNameRpg + " start - Startet das Rolepay\n"
            + "/" + commandNameRpg + " stop - Stoppt das Spiel\n"
            + "/" + commandNameRpg + " add player <player> - Fügt einen Spieler hinzu\n"
            + "/" + commandNameRpg + " remove player <player> - Entfernt einen Spieler\n"
            + "/" + commandNameRpg + " list - Zeigt alle Spieler die am Roleplay beteiligt sind an\n"
            + "/" + commandNameRpg + " join - Fügt dich selbst zum Roleplay hinzu\n"
            + "/" + commandNameRpg + " info - Zeigt ein paar infos über das Plugin usw.\n"
            + "/" + commandNameRpg + " preferences - Editiert die Einstellungen des Roleplays\n"
            + "/" + commandNameRpg + " help - Zeigt diese Seite...wer hätte es gedacht..loool";
    
    public static final String helpRpgPreferences = "/" + commandNameRpg + " preferences days <value> - Ändert die Zeiteinstellungen, wie lange ein Tag dauert"
            + "/" + commandNameRpg + " preferences nights <value> - Ändert die Zeiteinstellungen, wie lange eine Nacht dauert"
            + "/" + commandNameRpg + " preferences votes <value> -  Ändert die Zeiteinstellungen, wie lange die Votephase dauert";
    
    public static final String infoAboutPlugin = striche + "§4§k12§r§eRoleplay Plugin§4§k12§r\n\n§6Idee, Entwickler: §bNoah Till (Neo_MC) De(Germany)\n§6Arbeitszeit: §bbisher ca. 35 Stunden\n§6Entwickelt: §bseit 02.05.2019\n§6Version: §b" + version + striche;

    public static final String helpDetectivCommon = "/" + commandNameDetective + " " + commandModDetectiveAnalyze + " - Zeigt alle Spieler an, die einem Lügendetektortest unterzogen werden können.";

    public static final String killer = striche + "\n§c§lKiller\n\n§r§dDu hast jede Nacht die Möglichkeit eine Person zu Töten! Dies tust du über das nächtlich erscheinende Inventar!\n" + striche;
    public static final String villager = striche + "\n§a§lVillager\n\n§r§dDu bist ein ganz normaler Dorfbewohner, doch nicht unnötig, sondern ein wichtiger Bestandteil des gesammten Dorfteams. Jeden Tag kannst du abstimmen, wer getötet werden soll! Nutze deine Stimme sinnvoll ;3\n" + striche;
    public static final String detectiv = striche + "\n§9§lDetektiv\n\n§r§dDu bist eine sehr wichtige Rolle, nämlich der Detektiv. Jeden Tag hast du das Sagen und kannst pro Spiel zwie Person mit einer Erfolgschance von 40% einen Lügentest machen lassen, schlägt er aus, weißt du, dass etwas nicht in ordung ist, gibt er allerdings keinen muchs von sich, war es wohl ein Fehlschlag, oder er ist ein Dorfbewohner. Pass auf die Bürger auf ;3\n" + striche;
}
