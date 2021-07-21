package com.destructor.destructor2lit.timers;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GamePhaseTimer extends BukkitRunnable {

	private final Main main;
	ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
	Objective objective;
	Team date;
	Team phase;
	Utils utils = new Utils();
	Team[] teams;
	Player player;
	private int timer;
	private int currentPhasetimer;

	public GamePhaseTimer(Main main, Player player) {
		if (!player.isOnline()) {
			this.cancel();
		}
		teams = new Team[main.colors.size()];
		this.main = main;
		this.player = player;
		timer = main.getGamePhase().getTime() * 60;
		objective = scoreboard.registerNewObjective("objective", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		date = scoreboard.registerNewTeam("date");
		date.addEntry(ChatColor.GRAY + "/");
		objective.getScore(ChatColor.GRAY + "/").setScore(14);
		phase = scoreboard.registerNewTeam("phase");
		phase.addEntry(ChatColor.WHITE + " in ");
		objective.getScore(ChatColor.WHITE + " in ").setScore(12);
		for (int i = 0; i < main.colors.size(); i++) {
			teams[i] = scoreboard.registerNewTeam("team" + i);
			String entry = utils.getTeamColor(main.colors.get(i)) + main.colors.get(i).substring(0, 1).toUpperCase() + " " + ChatColor.WHITE + main.colors.get(i).substring(0, 1).toUpperCase() + main.colors.get(i).substring(1) + ": ";
			teams[i].addEntry(entry);
			objective.getScore(entry).setScore((main.colors.size() + 2) - i);
		}
		objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "BED WARS");
		Score line2 = objective.getScore("   ");
		Score line4 = objective.getScore("  ");
		Score line9 = objective.getScore(" ");
		Score line10 = objective.getScore(ChatColor.YELLOW + "Eldolfin#0001");
		line2.setScore(13);
		line4.setScore(11);
		line9.setScore(2);
		line10.setScore(1);

		Objective objective = scoreboard.registerNewObjective("health", "health");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objective.setDisplayName(ChatColor.RED + "❤");

		player.setScoreboard(scoreboard);
	}


	@Override
	public void run() {
		currentPhasetimer = main.getGamePhase().getTime()*60;
		timer = currentPhasetimer - main.globalTimer;
		LocalDateTime now = LocalDateTime.now();
		date.setPrefix((ChatColor.GRAY + dtf.format(now)).substring(0, 7));
		date.setSuffix(dtf.format(now).substring(6,11)+ChatColor.DARK_GRAY+dtf.format(now).substring(10));
		String seconds = (timer % 60) < 10 ? "0" + (timer % 60) : Integer.toString(timer % 60);
		phase.setPrefix(ChatColor.WHITE + main.getGamePhase().getDisplay());
		phase.setSuffix(ChatColor.GREEN + "" + Math.floorDiv(timer, 60) + ":" + seconds);

		String state;
		for (int i = 0; i < main.colors.size(); i++) {
			if (main.hasBed(main.colors.get(i))) {
				state = ChatColor.GREEN + "" + ChatColor.BOLD + "✓";
			} else {
				state = ChatColor.RED + "✘";
			}
			if (utils.getMetadata(player, "color").asString().equalsIgnoreCase(main.colors.get(i))) {
				state = state + ChatColor.GRAY + " YOU";
			}
			teams[i].setSuffix(state);
		}

	}
}
