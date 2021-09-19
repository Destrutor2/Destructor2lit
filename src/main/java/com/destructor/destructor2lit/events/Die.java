package com.destructor.destructor2lit.events;

import com.destructor.destructor2lit.API.BwKillEvent;
import com.destructor.destructor2lit.enums.BwDeaths;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.timers.DeathTimer;
import com.destructor.destructor2lit.utils.Utils;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Die {
/*	public static void Void(Player player, Main main) {
		die(player, main, "%bwplayer% was knocked into the void by %bwkiller%", "%bwplayer% fell in the void.");

		Utils utils = new Utils();
		String lastdamager = utils.getMetadata(player, "lastdamager").asString();
		utils.setMetadata(player, "alive", false);
		utils.setMetadata(player, "invincible", true);
		utils.setMetadata(player, "lastdamager", "null");
//        si il y a un lastdamager, on fait la même chose qu'un Kill(player) mais on modifie le message de mort
		if (!lastdamager.equals("null") && utils.getMetadata(player, "lastattack").asLong() > (new SystemClock().currentTimeMillis() - 30000)) {
			Player killer = Bukkit.getPlayer(lastdamager);
			player.setHealth(20);
			for (Player p : players) {
				p.hidePlayer(player);
			}
			main.getHiddenPlayers().add(player);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (main.hasBed(utils.getMetadata(player, "color").asString())) {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " was knocked into the void by " + utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY + ".");
				} else {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " was knocked into the void by " + utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY + "." + ChatColor.AQUA + " FINAL KILL!");
				}
			}
			if (utils.countItem(player, Material.IRON_INGOT) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT, utils.countItem(player, Material.IRON_INGOT)));
				killer.sendMessage(ChatColor.WHITE + "+" + utils.countItem(player, Material.IRON_INGOT) + " Iron");
			}
			if (utils.countItem(player, Material.GOLD_INGOT) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, utils.countItem(player, Material.GOLD_INGOT)));
				killer.sendMessage(ChatColor.GOLD + "+" + utils.countItem(player, Material.GOLD_INGOT) + " Gold");
			}
			if (utils.countItem(player, Material.DIAMOND) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.DIAMOND, utils.countItem(player, Material.DIAMOND)));
				killer.sendMessage(ChatColor.AQUA + "+" + utils.countItem(player, Material.DIAMOND) + " Diamonds");
			}
			if (utils.countItem(player, Material.EMERALD) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.EMERALD, utils.countItem(player, Material.EMERALD)));
				killer.sendMessage(ChatColor.DARK_GREEN + "+" + utils.countItem(player, Material.EMERALD) + " Emeralds");
			}
			player.getInventory().clear();
			player.getInventory().setHelmet(null);
			player.getInventory().setChestplate(null);
			player.getInventory().setLeggings(null);
			player.getInventory().setBoots(null);
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600 * 20, 1, false, false));
			player.teleport(new Location(player.getWorld(), main.getConfig().getDouble("mainspawn.x"), main.getConfig().getDouble("mainspawn.y"), main.getConfig().getDouble("mainspawn.z"), 0, 0));
			player.setAllowFlight(true);
			player.setFlying(true);
			killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);
			if (main.hasBed(utils.getMetadata(player, "color").asString())) {
				DeathTimer timer = new DeathTimer(player, players, main);
				timer.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
			}

		} else {
			player.setHealth(20);
			for (Player p : players) {
				p.hidePlayer(player);
			}
			main.getHiddenPlayers().add(player);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (main.hasBed(utils.getMetadata(player, "color").asString())) {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " fell in the void.");
				} else {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " fell in the void." + ChatColor.BOLD + ChatColor.AQUA + " FINAL KILL!");
				}
			}
			player.getInventory().clear();
			player.getInventory().setHelmet(null);
			player.getInventory().setChestplate(null);
			player.getInventory().setLeggings(null);
			player.getInventory().setBoots(null);
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600 * 20, 1, false, false));
			player.teleport(new Location(player.getWorld(), main.getConfig().getDouble("mainspawn.x"), main.getConfig().getDouble("mainspawn.y"), main.getConfig().getDouble("mainspawn.z"), 0, 0));
			player.setAllowFlight(true);
			player.setFlying(true);
			if (main.hasBed(utils.getMetadata(player, "color").asString())) {
				DeathTimer timer = new DeathTimer(player, players, main);
				timer.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
			}
		}

	}*/

	/*public static void Kill(Player player, Main main) {
		die(player, main, "%bwplayer% was killed by %bwkiller%");
		Utils utils = new Utils();
		boolean killerIsOwned;
		Player killer;
		if (entitykiller instanceof Player) {
			killer = (Player) entitykiller;
			killerIsOwned = false;
		} else {
			killer = Bukkit.getPlayer(utils.getMetadata(entitykiller, "owner").asString());
			killerIsOwned = true;
		}

		player.setHealth(20);
		utils.setMetadata(player, "alive", false);
		utils.setMetadata(player, "invincible", true);
		utils.setMetadata(player, "lastdamager", "null");
		for (Player p : players) {
			p.hidePlayer(player);
		}
		main.getHiddenPlayers().add(player);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (killerIsOwned) {
				if (main.hasBed(utils.getMetadata(player, "color").asString())) {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " was killed by " + utils.getColor(killer) + killer.getDisplayName() + "'s" + ChatColor.GRAY + " Dream Defender.");
				} else {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " was killed by " + utils.getColor(killer) + killer.getDisplayName() + "'s" + ChatColor.GRAY + " Dream Defender." + ChatColor.AQUA + " FINAL KILL!");
				}
			} else {
				if (main.hasBed(utils.getMetadata(player, "color").asString())) {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " was killed by " + utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY + ".");
				} else {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " was killed by " + utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY + "." + ChatColor.AQUA + " FINAL KILL!");
				}
			}
		}
		if (utils.countItem(player, Material.IRON_INGOT) != 0) {
			killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT, utils.countItem(player, Material.IRON_INGOT)));
			killer.sendMessage(ChatColor.WHITE + "+" + utils.countItem(player, Material.IRON_INGOT) + " Iron");
		}
		if (utils.countItem(player, Material.GOLD_INGOT) != 0) {
			killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, utils.countItem(player, Material.GOLD_INGOT)));
			killer.sendMessage(ChatColor.GOLD + "+" + utils.countItem(player, Material.GOLD_INGOT) + " Gold");
		}
		if (utils.countItem(player, Material.DIAMOND) != 0) {
			killer.getInventory().addItem(new ItemStack(Material.DIAMOND, utils.countItem(player, Material.DIAMOND)));
			killer.sendMessage(ChatColor.AQUA + "+" + utils.countItem(player, Material.DIAMOND) + " Diamonds");
		}
		if (utils.countItem(player, Material.EMERALD) != 0) {
			killer.getInventory().addItem(new ItemStack(Material.EMERALD, utils.countItem(player, Material.EMERALD)));
			killer.sendMessage(ChatColor.DARK_GREEN + "+" + utils.countItem(player, Material.EMERALD) + " Emeralds");
		}
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600 * 20, 1, false, false));
		player.teleport(new Location(player.getWorld(), main.getConfig().getDouble("mainspawn.x"), main.getConfig().getDouble("mainspawn.y"), main.getConfig().getDouble("mainspawn.z"), 0, 0));
		player.setAllowFlight(true);
		player.setFlying(true);
		killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);
		if (main.hasBed(utils.getMetadata(player, "color").asString())) {
			DeathTimer timer = new DeathTimer(player, players, main);
			timer.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
		}
	}*/

/*	public static void Fall(Player player, Main main) {
		die(player, main, "%bwplayer% was knocked off a cliff by %bwkiller%", "%bwplayer% died.");

		Utils utils = new Utils();
		String lastdamager = utils.getMetadata(player, "lastdamager").asString();
		utils.setMetadata(player, "alive", false);
		utils.setMetadata(player, "invincible", true);
		utils.setMetadata(player, "lastdamager", "null");
//        Ici, comme pour le Void(), on va voir si le joueur mort a un lastdamager, si oui, on fait comme pour un Kill() en changeant le kill message, sinon, on fait comme un Void() mais en droppant les items et en changeant le message
		if (!lastdamager.equals("null") && utils.getMetadata(player, "lastattack").asLong() > (new SystemClock().currentTimeMillis() - 30000)) {
			Player killer = Bukkit.getPlayer(lastdamager);
			List<Player> players = main.getPlayers();
			player.setHealth(20);
			for (Player p : players) {
				p.hidePlayer(player);
			}
			main.getHiddenPlayers().add(player);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (main.hasBed(utils.getMetadata(player, "color").asString())) {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " was knocked off a cliff by " + utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY + ".");
				} else {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " was knocked off a cliff by " + utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY + "." + ChatColor.AQUA + " FINAL KILL!");
				}
			}
			if (utils.countItem(player, Material.IRON_INGOT) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT, utils.countItem(player, Material.IRON_INGOT)));
				killer.sendMessage(ChatColor.WHITE + "+" + utils.countItem(player, Material.IRON_INGOT) + " Iron");
			}
			if (utils.countItem(player, Material.GOLD_INGOT) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, utils.countItem(player, Material.GOLD_INGOT)));
				killer.sendMessage(ChatColor.GOLD + "+" + utils.countItem(player, Material.GOLD_INGOT) + " Gold");
			}
			if (utils.countItem(player, Material.DIAMOND) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.DIAMOND, utils.countItem(player, Material.DIAMOND)));
				killer.sendMessage(ChatColor.AQUA + "+" + utils.countItem(player, Material.DIAMOND) + " Diamonds");
			}
			if (utils.countItem(player, Material.EMERALD) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.EMERALD, utils.countItem(player, Material.EMERALD)));
				killer.sendMessage(ChatColor.DARK_GREEN + "+" + utils.countItem(player, Material.EMERALD) + " Emeralds");
			}
			player.getInventory().clear();
			player.getInventory().setHelmet(null);
			player.getInventory().setChestplate(null);
			player.getInventory().setLeggings(null);
			player.getInventory().setBoots(null);
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600 * 20, 1, false, false));
			player.teleport(new Location(player.getWorld(), main.getConfig().getDouble("mainspawn.x"), main.getConfig().getDouble("mainspawn.y"), main.getConfig().getDouble("mainspawn.z"), 0, 0));
			player.setAllowFlight(true);
			player.setFlying(true);
			killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);
			if (main.hasBed(utils.getMetadata(player, "color").asString())) {

				DeathTimer timer = new DeathTimer(player, players, main);
				timer.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
			}

		} else {
			player.setHealth(20);
			List<Player> players = main.getPlayers();
			for (Player p : players) {
				p.hidePlayer(player);
			}
			main.getHiddenPlayers().add(player);

			for (Player p : Bukkit.getOnlinePlayers()) {
				if (main.hasBed(utils.getMetadata(player, "color").asString())) {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " died.");
				} else {
					p.sendMessage(utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY + " died." + ChatColor.BOLD + ChatColor.AQUA + " FINAL KILL!");
				}
			}
			for (ItemStack itemStack : player.getInventory()) {
				if (itemStack != null) {
					if (itemStack.getType().equals(Material.IRON_INGOT) || itemStack.getType().equals(Material.GOLD_INGOT) || itemStack.getType().equals(Material.DIAMOND) || itemStack.getType().equals(Material.EMERALD)) {
						player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
					}
				}
			}
			player.getInventory().clear();
			player.getInventory().setHelmet(null);
			player.getInventory().setChestplate(null);
			player.getInventory().setLeggings(null);
			player.getInventory().setBoots(null);
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600 * 20, 1, false, false));
			player.teleport(new Location(player.getWorld(), main.getConfig().getDouble("mainspawn.x"), main.getConfig().getDouble("mainspawn.y"), main.getConfig().getDouble("mainspawn.z"), 0, 0));
			player.setAllowFlight(true);
			player.setFlying(true);
			if (main.hasBed(utils.getMetadata(player, "color").asString())) {
				DeathTimer timer = new DeathTimer(player, players, main);
				timer.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
			}

	}*/

/*	public static void Reconnect(Player player, Main main) {
		die(player, main, "null", "null");

		player.setHealth(20);
		List<Player> players = main.getPlayers();
		Utils utils = new Utils();
		for (Player p : players) {
			p.hidePlayer(player);
		}
		main.getHiddenPlayers().add(player);
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600 * 20, 1, false, false));
		player.teleport(new Location(player.getWorld(), main.getConfig().getDouble("mainspawn.x"), main.getConfig().getDouble("mainspawn.y"), main.getConfig().getDouble("mainspawn.z"), 0, 0));
		player.setAllowFlight(true);
		player.setFlying(true);
		DeathTimer timer = new DeathTimer(player, players, main);
		timer.runTaskTimer(Main.getPlugin(Main.class), 0, 20);

	}*/

	public static void Die(Player player, Main main, BwDeaths deathType) {
		die(player, main, deathType.getDefaultKillMessage(), deathType.getDefaultDeathMessage(), deathType);
	}

	//	dans les message, utiliser %bwkiller% et %bwplayer%
	private static void die(Player player, Main main, String killMessage, String dieMessage, BwDeaths deathType) {
		Utils utils = new Utils();
		String lastdamager = utils.getMetadata(player, "lastdamager").asString();
		utils.setMetadata(player, "alive", false);
		utils.setMetadata(player, "invincible", true);
		utils.setMetadata(player, "lastdamager", "null");
		player.setHealth(20);
		for (Player p : main.getPlayers()) {
			p.hidePlayer(player);
		}
		String displayedMessage;

		if (!lastdamager.equals("null") && (utils.getMetadata(player, "lastattack").asLong() > (new SystemClock().currentTimeMillis() - main.bwConfig.attacktagstaytimemillis))) {
			displayedMessage = killMessage;
			Player killer = Bukkit.getPlayer(UUID.fromString(lastdamager));
			Bukkit.getPluginManager().callEvent(new BwKillEvent(killer, player, deathType, !main.hasBed(utils.getMetadata(player, "color").asString())));
			if (utils.countItem(player, Material.IRON_INGOT) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT, utils.countItem(player, Material.IRON_INGOT)));
				killer.sendMessage(ChatColor.WHITE + "+" + utils.countItem(player, Material.IRON_INGOT) + " Iron");
			}
			if (utils.countItem(player, Material.GOLD_INGOT) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, utils.countItem(player, Material.GOLD_INGOT)));
				killer.sendMessage(ChatColor.GOLD + "+" + utils.countItem(player, Material.GOLD_INGOT) + " Gold");
			}
			if (utils.countItem(player, Material.DIAMOND) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.DIAMOND, utils.countItem(player, Material.DIAMOND)));
				killer.sendMessage(ChatColor.AQUA + "+" + utils.countItem(player, Material.DIAMOND) + " Diamonds");
			}
			if (utils.countItem(player, Material.EMERALD) != 0) {
				killer.getInventory().addItem(new ItemStack(Material.EMERALD, utils.countItem(player, Material.EMERALD)));
				killer.sendMessage(ChatColor.DARK_GREEN + "+" + utils.countItem(player, Material.EMERALD) + " Emeralds");
			}
			killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1, 1);
			displayedMessage = displayedMessage.replace("%bwkiller%", utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY);
		} else {
			displayedMessage = dieMessage;
			for (ItemStack itemStack : player.getInventory()) {
				if (itemStack != null) {
					if (itemStack.getType().equals(Material.IRON_INGOT) || itemStack.getType().equals(Material.GOLD_INGOT) || itemStack.getType().equals(Material.DIAMOND) || itemStack.getType().equals(Material.EMERALD)) {
						player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
					}
				}
			}
		}

		for (Entity e : player.getWorld().getEntities()) {
			if (e instanceof IronGolem)
				if (((IronGolem) e).getTarget() != null)
					if (((IronGolem) e).getTarget().equals(player))
						((IronGolem) e).setTarget(null);
		}
		main.getHiddenPlayers().add(player);
		player.setItemOnCursor(null);
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.setFireTicks(0);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600 * 20, 1, false, false));
		player.teleport(main.spawn);
		player.setAllowFlight(true);
		player.setFlying(true);
		if (main.hasBed(utils.getMetadata(player, "color").asString())) {
			DeathTimer timer = new DeathTimer(player, main.getPlayers(), main);
			timer.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
		}

		displayedMessage = displayedMessage.replaceAll("%bwplayer%", utils.getColor(player) + player.getDisplayName() + ChatColor.GRAY);

		player.getInventory().clear();


		if (displayedMessage == "null")
			return;

		if (!main.hasBed(utils.getMetadata(player, "color").asString())) {
			displayedMessage = displayedMessage + ChatColor.AQUA + "" + ChatColor.BOLD + " FINAL KILL!";
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(displayedMessage);
		}
		if (!main.hasBed(utils.getMetadata(player, "color").asString())) {
			player.sendMessage(ChatColor.RED + "You've been eliminated!" + ChatColor.WHITE + " (verifier ce message)");
		}
	}

}
