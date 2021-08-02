package com.destructor.destructor2lit.events;

import com.destructor.destructor2lit.BwTeam;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Title;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class TriggerTrap {
	public TriggerTrap(Player player, BwTeam bwTeam, Main main) {
		if (!bwTeam.getPlayers().contains(player) && bwTeam.canTrapTrigger() && Utils.getMetadata(player, "lastMilk").asLong() + 30000 < System.currentTimeMillis()) {
			byte type = bwTeam.getTraps().remove();
			bwTeam.logTriggerTrap();

			switch (type) {
				case 1:
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 1, true, true));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0, true, true));
					showTitle("It's a trap", bwTeam);
					break;
				case 2:
					showTitleandGiveEffect("Counter-Offensive Trap", bwTeam, new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, 200, 0, true, true), new PotionEffect(PotionEffectType.JUMP, 200, 1)});
					break;
				case 3:
					Title title = new Title(RED + "" + BOLD + "ALARM!!!", "Alarm trap set off by " + bwTeam.getChatColor() + bwTeam.getColor());
					if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
						player.removePotionEffect(PotionEffectType.INVISIBILITY);
						Utils.showArmor(player);
					}
					for (OfflinePlayer p : bwTeam.getPlayers()) {
						if (p.isOnline()) {
							p.getPlayer().sendMessage(RED + "" + BOLD + "Alarm trap set off by " + WHITE + BOLD + player.getDisplayName() + RED + BOLD + " from " + bwTeam.getChatColor() + bwTeam.getColor() + RED + BOLD + " team!");
							p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 2f, 0.5f);
							title.send(p.getPlayer());
						}
					}

					List<OfflinePlayer> soundPlayers = new ArrayList<>(bwTeam.getPlayers());
					soundPlayers.add(player);
					playAlarmSound(soundPlayers, main, 1.71f, 1.8f);
					break;
				case 4:
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0, true, true));
					showTitle("Miner Fatigue Trap", bwTeam);
					break;
			}
		}
	}

	private void showTitleandGiveEffect(String trapName, BwTeam bwTeam, PotionEffect[] effects) {
		Title title = new Title(RED + "TRAP TRIGGERED!", "Your " + trapName + " has been set off!", 0, 1, 1);
		for (OfflinePlayer p : bwTeam.getPlayers()) {
			if (p.isOnline()) {
				p.getPlayer().sendMessage(RED + "" + BOLD + trapName + " was set off!");
				p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 2f, 0.5f);
				title.send(p.getPlayer());
				p.getPlayer().addPotionEffects(Arrays.asList(effects));
			}
		}
	}

	private void showTitle(String trapName, BwTeam bwTeam) {
		Title title = new Title(RED + "TRAP TRIGGERED!", "Your " + trapName + " has been set off!", 0, 1, 1);
		for (OfflinePlayer p : bwTeam.getPlayers()) {
			if (p.isOnline()) {
				p.getPlayer().sendMessage(RED + "" + BOLD + trapName + " was set off!");
				p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 2f, 0.5f);
				title.send(p.getPlayer());
			}
		}
	}

	public static void playAlarmSound(List<OfflinePlayer> players, Main main, float pitchtest1, float pitchtest2) {
		List<Player> concernedPlayers = new ArrayList<>();
		for (OfflinePlayer p : players) {
			if (p.isOnline())
				concernedPlayers.add(p.getPlayer());
		}

		new BukkitRunnable() {
			int counter = 0;

			@Override
			public void run() {
				if (counter == 20) {
					this.cancel();
				}
				for (Player player : concernedPlayers) {
					player.playSound(player.getLocation(), Sound.NOTE_PIANO, 2, (counter % 2 == 0 ? pitchtest1 : pitchtest2));
				}

				counter++;
			}
		}.runTaskTimerAsynchronously(main, 0, 2);
	}
}
