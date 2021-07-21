package com.destructor.destructor2lit.events;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Title;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class BedBreak {
	public static void Break(BlockBreakEvent e, Main main) {
//		String[] colors = {"red", "blue", "green", "yellow", "aqua", "white", "pink", "gray"};
//		C'est mieux comme ca au cas ou on ajoute ou enleve des couleurs dans la config, ca sera change ici aussi
		List<String> colors = main.colors;
		String color = "null";
		Player breaker = e.getPlayer();
		List<Player> victims = new ArrayList<>();
		Utils utils = new Utils();
		for (String c : colors) {
			if ((e.getBlock().getX() == main.getConfig().getInt("Beds." + c + ".block1.x") && e.getBlock().getY() == main.getConfig().getInt("Beds." + c + ".block1.y") && e.getBlock().getZ() == main.getConfig().getInt("Beds." + c + ".block1.z")) || (e.getBlock().getX() == main.getConfig().getInt("Beds." + c + ".block2.x") && e.getBlock().getY() == main.getConfig().getInt("Beds." + c + ".block2.y") && e.getBlock().getZ() == main.getConfig().getInt("Beds." + c + ".block2.z"))) {
				color = c;
			}
		}
		if (color.equalsIgnoreCase("null")) {
			e.setCancelled(true);
			return;
		}
		for (Player player : main.getPlayers()) {
			if (utils.getMetadata(player, "color").asString().equalsIgnoreCase(color)) {
				victims.add(player);
			}
		}
//		à ce niveau là, on a la victime, le joueur qui a cassé le lit et le joueur a qui le lit a été cassé
		if (victims.contains(breaker)) {
			breaker.sendMessage(ChatColor.RED + "You can't destroy your own bed!");
			e.setCancelled(true);
			return;
		}

		main.removeBed(color);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage("");
			if (victims.contains(player)) {
				player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "BED DESTRUCTION > " + ChatColor.RESET + ChatColor.GRAY + "Your was destroyed by " + utils.getColor(breaker) + breaker.getDisplayName() + ChatColor.GRAY + "!");
				Title title = new Title("BED DESTROYED!");
				title.setTitleColor(ChatColor.RED);
				title.setSubtitle("You will no longer respawn!");
				title.setSubtitleColor(ChatColor.WHITE);
				title.setTimingsToTicks();
				title.setFadeInTime(5);
				title.setStayTime(60);
				title.setFadeOutTime(5);
				title.send(player);
				utils.setMetadata(player, "lastdamager", breaker.getName());
			} else {
				player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "BED DESTRUCTION > " +ChatColor.RESET+ utils.getTeamColor(color) + color.substring(0, 1).toUpperCase() + color.substring(1) + " Bed" + ChatColor.GRAY + " was destroyed by " + utils.getColor(breaker) + breaker.getDisplayName() + ChatColor.GRAY + "!");
			}
			player.sendMessage("");
			player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
		}

	}
}
