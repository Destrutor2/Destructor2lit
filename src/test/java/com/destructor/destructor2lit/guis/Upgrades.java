package com.destructor.destructor2lit.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Upgrades {
    public static void openUI(Player player) {
        //        debug
        Bukkit.broadcastMessage(ChatColor.RED+player.getDisplayName()+" a ouvert le l'upgrade");
    }
}
