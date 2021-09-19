package com.destructor.destructor2lit.timers;

import com.destructor.destructor2lit.enums.GameState;
import com.destructor.destructor2lit.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class WaitingScoreboardTimer extends BukkitRunnable {

	int timer = 0;
	ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
	public Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	Objective objective;
	Main main;
	String[] AnimatedName = new String[90];
	Score[] lines;
	Team date;
	Team playersCount;
	Team countdown;

	public WaitingScoreboardTimer(Main main) {
		this.main = main;
		AnimatedName[0] = ChatColor.GOLD + "" + ChatColor.BOLD + "B" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ED WARS";
		AnimatedName[1] = ChatColor.WHITE + "" + ChatColor.BOLD + "B" + ChatColor.GOLD + "" + ChatColor.BOLD + "E" + ChatColor.YELLOW + "" + ChatColor.BOLD + "D WARS";
		AnimatedName[2] = ChatColor.WHITE + "" + ChatColor.BOLD + "BE" + ChatColor.GOLD + "" + ChatColor.BOLD + "D" + ChatColor.YELLOW + "" + ChatColor.BOLD + " WARS";
		AnimatedName[3] = ChatColor.WHITE + "" + ChatColor.BOLD + "BE" + ChatColor.GOLD + "" + ChatColor.BOLD + "D" + ChatColor.YELLOW + "" + ChatColor.BOLD + " WARS";
		AnimatedName[4] = ChatColor.WHITE + "" + ChatColor.BOLD + "BED " + ChatColor.GOLD + "" + ChatColor.BOLD + "W" + ChatColor.YELLOW + "" + ChatColor.BOLD + "ARS";
		AnimatedName[5] = ChatColor.WHITE + "" + ChatColor.BOLD + "BED W" + ChatColor.GOLD + "" + ChatColor.BOLD + "A" + ChatColor.YELLOW + "" + ChatColor.BOLD + "RS";
		AnimatedName[6] = ChatColor.WHITE + "" + ChatColor.BOLD + "BED WA" + ChatColor.GOLD + "" + ChatColor.BOLD + "R" + ChatColor.YELLOW + "" + ChatColor.BOLD + "S";
		AnimatedName[7] = ChatColor.WHITE + "" + ChatColor.BOLD + "BED WAR" + ChatColor.GOLD + "" + ChatColor.BOLD + "S";
		AnimatedName[8] = ChatColor.WHITE + "" + ChatColor.BOLD + "BED WARS";
		Arrays.fill(AnimatedName, 9, 29, ChatColor.WHITE + "" + ChatColor.BOLD + "BED WARS");
		Arrays.fill(AnimatedName, 29, 38, ChatColor.YELLOW + "" + ChatColor.BOLD + "BED WARS");
		Arrays.fill(AnimatedName, 38, 40, ChatColor.WHITE + "" + ChatColor.BOLD + "BED WARS");
		Arrays.fill(AnimatedName, 40, 90, ChatColor.YELLOW + "" + ChatColor.BOLD + "BED WARS");
		objective = scoreboard.registerNewObjective("startobjective", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		date = scoreboard.registerNewTeam("date");
		date.addEntry(ChatColor.GRAY + "/");
		playersCount = scoreboard.registerNewTeam("players");
		playersCount.addEntry(ChatColor.WHITE + "Players: ");
		countdown = scoreboard.registerNewTeam("countdown");
		countdown.addEntry(ChatColor.WHITE + "");
		objective.getScore(ChatColor.WHITE + "Players: ").setScore(8);
		objective.getScore(ChatColor.WHITE + "").setScore(6);
		objective.getScore(ChatColor.GRAY + "/").setScore(11);
		lines = new Score[]{
				objective.getScore(" "),
				objective.getScore("    "),
				objective.getScore(ChatColor.WHITE + "Map: " + ChatColor.GREEN + Bukkit.getWorlds().get(0).getName()),
				objective.getScore(" "),
				objective.getScore("   "),
				objective.getScore(" "),
				objective.getScore("  "),
				objective.getScore(ChatColor.WHITE + "Mode: " + ChatColor.GREEN + "Solo"),
				objective.getScore(ChatColor.WHITE + "Version: " + ChatColor.GRAY + main.getDescription().getVersion()),
				objective.getScore(" "),
				objective.getScore(ChatColor.YELLOW + "Eldolfin#8451")

		};
		for (int i = 0; i < lines.length; i++) {
			lines[i].setScore(lines.length - i);
		}
	}


	@Override
	public void run() {

		if (!main.isState(GameState.WAITING)) {
			this.cancel();
		}
		if (timer == 89) {
			timer = 0;
		}

		objective.setDisplayName(AnimatedName[timer]);

		LocalDateTime now = LocalDateTime.now();
//		lines[0] = objective.getScore(ChatColor.GRAY + dtf.format(now));
//		lines[3] = objective.getScore(ChatColor.WHITE + "Players: " + ChatColor.GREEN + main.getPlayers().size() + "/8");
//		lines[5] = objective.getScore(ChatColor.WHITE + "Starting in " + ChatColor.GREEN + main.startTimer.timer + "s");
		date.setPrefix((ChatColor.GRAY + dtf.format(now)).substring(0, 7));
		date.setSuffix(dtf.format(now).substring(6,11)+ChatColor.DARK_GRAY+dtf.format(now).substring(10));
		if (main.getPlayers().size() > 1) {
			countdown.setPrefix(ChatColor.WHITE + "Starting in ");
			countdown.setSuffix(ChatColor.GREEN + Integer.toString(main.startTimer.timer + 1) + "s");
		} else {
			countdown.setPrefix(ChatColor.WHITE + "");
			countdown.setSuffix(ChatColor.WHITE + "Waiting...");
		}
		playersCount.setSuffix(ChatColor.GREEN + Integer.toString(main.getPlayers().size()) + "/8");

		timer++;
	}

}
