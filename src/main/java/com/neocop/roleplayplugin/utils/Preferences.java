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
    public static int detectivSucc = 30;
    
    public static final String version = "1.0-SNAPSHOT";

    public static final String striche = "\n§r§e--------------------------------------------------§r\n";
    
    public static final String consoleDes = "[Roleplay Plugin]";

    public static final String commandNameRpg = "rpg";
    public static final String commandNameDetective = "detective";

    public static final String commandModDetectiveAnalyze = "analyze";

    public static final String noPermissionOpNeeded = "§cDu hast nicht die benötigte Berechtigung um diesen Befehl auszuführen!";
    public static final String noPermissionCommandOnlyForUser = "Dieser Command ist nur für den User anwendbar!";
    public static final String noPermissionAlreadyVoted = "§cDu hast bereits eine Wahl getroffen!";
    public static final String noPermissionDetectiveRoleNeeded = "§cNur der Detectiv kann diesen Befehl ausführen!";
    public static final String noPermissionAlreadyDead = "§cDu bist bereits gestorben!";
    public static final String noPermissionVoteNotActive = "§cDu kannst jetzt noch nicht Voten. Warte bis die Votephase anfängt.";
    public static final String notEnoughPlayerForRpg = "§eEs sind nicht genug Spieler in der Spielerliste. Du kannst neue Spieler mit dem Befehl: /rpg add [name] hinzufügen.";
    public static final String noRunningRpg = "§cMomentan findet leider kein Roleplay statt";

    public static final String playerAddToRpg = "§aDu bist dem Roleplay beigetreten.";
    public static final String playerNotFound = "§cDieser Spieler wurde nicht gefunden.";
    public static final String playerRpgListEmpty = "§eMomentan nimmt niemand am Roleplay teil. Du kannst neue Spieler mit dem Befehl: /rpg add [name] hinzufügen";
    public static final String playerRemoveFromRpg = "§eDu whast das Roleplay verlassen.";
    public static final String playerAlreadyInRpg = "§eDieser Spieler nimmt schon am Roleplay teil.";
    public static final String playerIsNotInRpg = "§eDieser Spieler nimmt nicht am Roleplay teil.";
    public static final String playerAddToVoteList = "§aSpieler wurde auf die Votelist gesetzt!";
    public static final String playerDiedByVoteKill = "§eDu wurdest so eben von der Abstimmung getötet, verlasse gegebenfalls bitte den Sprachchat, falls eure Roleplay Gruppe einen verwendet!";
    public static final String playerDiedByOtherPlayer = "§eDu wurdest so eben von einem Spieler Getötet, verlasse gegebenfalls bitte den Sprachchat, falls eure Roleplay Gruppe einen verwendet!";

    public static final String rpgSomeoneDied = "Ein Spieler wurde umgebracht!";
    public static final String rpgNobodyDied = "Der Killer hat heute wohl niemanden getötet!";
    
    public static final String rpgKillerNightAction = "Der Mond leuchtet heute Abend sehr hell.\nWenn möchtest du umbringen?§c";
    public static final String rpgKillerDayAction = "Du solltest besser nicht endeckt werden. Es könnte für dich übel ausgehen.";

    public static final String rpgVillagerDayActionDied = "Wach auf! " + rpgSomeoneDied +" Du musst den Mörder überführen!";
    public static final String rpgVillagerDayActionNoDied = "Wach auf! " + rpgNobodyDied +" Du musst den Mörder überführen!";
    public static final String rpgVillagerNightAction = "Ein einfacher Bürger sollte abends nicht alleine wach sein.\nDu solltest besser Schlafen gehen.";

    public static final String rpgDetectivNightAction = "Die Bürger verlassen sich auf dich!\nDu solltest daher deine Kräfte schonen.";
    public static final String rpgDetectivDayActionDied = "Wach auf, " + rpgSomeoneDied + " \nDu kannst deinen Lügendetektor benutzen, um den Übeltäter zu identifizieren. Um diesen zu benutzen, führe einfach den Befehl : /detectiv analyze aus.";
    public static final String rpgDetectivDayActionNoDied = "Wach auf, " + rpgNobodyDied + " \nDu kannst deinen Lügendetektor benutzen, um den Übeltäter zu identifizieren. Um diesen zu benutzen, führe einfach den Befehl : /detectiv analyze [name] aus.";
    
    public static final String succPlayerAddToRpg = "§aSpieler zum Roleplay hinzugefügt!";
    public static final String succPlayerRemoveFromRpg = "§aDieser Spieler wurde aus dem Roleplay entfernt";

    public static final String globalPlayerDiedByOtherPlayer = "§eEin Spieler ist gestorben! Findet den Mörder!";
    public static final String globalRpgGetCanceled = "§cDas Roleplay wurde unterbrochen!";
    public static final String globalRules = striche + "§e§lRules§r\n\nEs geht sofort los, nur noch ein paar grundlegende Sachen. \nEigentlich ganz einfach:\n-Vorzeitiges Sterben (durch äußere einflüsse) oder verlassen des Servers, führt zum Spielauschluss\n-Verhaltet euch so, wie eure Rolle es verlangt\n-Reportet Bugs\n-Seid nett\n-Habt spaß" + striche;
    public static final String globalNobodyDied = "§eDas dorf hat sich nicht entscheiden können, wer stirbt!";
    public static final String globalVoteBegin = striche + "§2§lVoting§r\n\nBitte stimmt jetzt mit dem §l/rpg vote [player]§r command ab, wer diese Runde sterben soll. Es müssen mindestens 3/4 dafür sein, sonst findet keine Hinrichtung statt!" + striche;
    
    public static final String rpgRoleControl = striche + "§6§lDie Rolle§r\nDie sogenannte Rolle hat theoretisch keine besonderen eigenen Fähighkeiten, sie soll deinen Charakter, den du Spielen sollst bestimmen und anweisen. Verhalte dich also so, wie es deine Rolle vorgibt. Manche Rollen haben ein extra, dies kannst du mit §l/rpg extra help§r herraus finden!" + striche;
    
    public static final String helpRpgCommon = "/" + commandNameRpg + " start - Startet das Rolepay\n"
            + "/" + commandNameRpg + " stop - Stoppt das Spiel\n"
            + "/" + commandNameRpg + " add player <player> - Fügt einen Spieler hinzu\n"
            + "/" + commandNameRpg + " remove player <player> - Entfernt einen Spieler\n"
            + "/" + commandNameRpg + " list - Zeigt alle Spieler die am Roleplay beteiligt sind an\n"
            + "/" + commandNameRpg + " join - Fügt dich selbst zum Roleplay hinzu\n"
            + "/" + commandNameRpg + " info - Zeigt ein paar infos über das Plugin usw.\n"
            + "/" + commandNameRpg + " preferences - Editiert die Einstellungen des Roleplays\n"
            + "/" + commandNameRpg + " extra help - Zeigt die Rollen spezifischen fähigkeiten an\n"
            + "/" + commandNameRpg + " help - Zeigt diese Seite...wer hätte es gedacht..loool";
    
    public static final String helpRpgPreferences = "/" + commandNameRpg + " preferences days <value> - Ändert die Zeiteinstellungen, wie lange ein Tag dauert\n"
            + "/" + commandNameRpg + " preferences nights <value> - Ändert die Zeiteinstellungen, wie lange eine Nacht dauert\n"
            + "/" + commandNameRpg + " preferences votes <value> -  Ändert die Zeiteinstellungen, wie lange die Votephase dauert\n"
            + "/" + commandNameRpg + " preferences detectivsucc <value> - ändert die erfolgschance des Detectivs";
    
    public static final String helpUtilCommon = "§lObjects:§r\n-title: spawnt einen schwebenden Titel\n-player: spawnt einen Armorstand" + striche + "§lSpawn§r\n/util spawn [obj] [txt/playerName] - spawnt einen Titel"
            + striche + "§lProp§r\n/util prop [obj] set name [txt] - ändert den Text des erstellten Textes\n"
            + "/util prop [obj] set color [color] - was wohl?"
            + "/util prop [obj] set cloth [cloth] - zieht dat Fich an\n"
            + "/util prop [obj] remove - entfernt den erstellen Titel";
    
    public static final String infoAboutPlugin = striche + "§4§k12§r§eRoleplay Plugin§4§k12§r\n\n§6Idee, Entwickler: §bNoah Till (Neo_MC) De(Germany)\n§6Arbeitszeit: §bbisher ca. 35 Stunden\n§6Entwickelt: §bseit 02.05.2019\n§6Version: §b" + version + striche;

    public static final String helpDetectivCommon = "/" + commandNameDetective + " " + commandModDetectiveAnalyze + " - Zeigt alle Spieler an, die einem Lügendetektortest unterzogen werden können.";

    public static final String killer = striche + "\n§c§lMörder\n\n§r§dDu solltest jede Nacht einen Spieler umbringen! Ich, an deiner Stelle würde jeden umbringen. Es wird sicher viel Süaß machen, vertrau mir.\n" + striche;
    public static final String villager = striche + "\n§a§lEinwohner\n\n§r§dDu bist ein ganz normaler Einwohner dieser Stadt. Aber dennoch nicht unwichtig! Du hast eine STimme die du dafür benutzen kannst den Übeltäter zu überführen. Handel Weise!\n" + striche;
    public static final String detectiv = striche + "\n§9§lDetektiv\n\n§r§dDu bist sozusagen der Sheriff dieser Stadt. Niemand entkommt deiner Gerechtigkeit! Zumindest ist das so geplant. Also benutze deine Fähigkeiten Weise um deine Mitspieler zu beschützen! Du kannst mit deinem Lügendetektor herausfinden, ob ein Mitspieler korrumpiert ist. Doch manchmal kann eine Lüge selbst den lügendetektor austricksen. Also, sei achtsam!\n" + striche;
}
