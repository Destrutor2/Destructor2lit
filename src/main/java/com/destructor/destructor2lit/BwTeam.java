package com.destructor.destructor2lit;

import com.destructor.destructor2lit.gens.TeamGen;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class BwTeam {
	Boolean sharp = false;
	byte protection = 0;
	byte haste = 0;
	byte forge = 0;
	long lastTrapTrigger = 0;
	Boolean healpool = false;
	Boolean dragonbuff = false;
	Queue<Byte> traps = new LinkedList<>();
	TeamGen gen;
	Location spawn;
	ChatColor chatColor;
	String color;
	List<OfflinePlayer> players = new ArrayList<>();
	Location baseCenter;
	Block bedblock1;
	Block bedblock2;


	public BwTeam(ChatColor chatColor, String color, Location spawn, Location bedblock1loc, Location bedblock2loc) {
		this.chatColor = chatColor;
		this.color = color;
		this.spawn = spawn;
		this.bedblock1 = spawn.getWorld().getBlockAt(bedblock1loc);
		this.bedblock2 = spawn.getWorld().getBlockAt(bedblock2loc);
		this.baseCenter = bedblock1loc.toVector().getMidpoint(bedblock2loc.toVector()).toLocation(spawn.getWorld());
	}


	public Boolean getSharp() {
		return sharp;
	}

	public void setSharp(Boolean sharp) {
		this.sharp = sharp;
	}

	public byte getProtection() {
		return protection;
	}

	public void setProtection(byte protection) {
		this.protection = protection;
	}

	public byte getHaste() {
		return haste;
	}

	public void setHaste(byte haste) {
		this.haste = haste;
	}

	public byte getForge() {
		return forge;
	}

	public void setForge(byte forge) {
		this.forge = forge;
	}

	public Boolean getHealpool() {
		return healpool;
	}

	public void setHealpool(Boolean healpool) {
		this.healpool = healpool;
	}

	public Boolean getDragonbuff() {
		return dragonbuff;
	}

	public void setDragonbuff(Boolean dragonbuff) {
		this.dragonbuff = dragonbuff;
	}

	public Queue<Byte> getTraps() {
		return traps;
	}

	public void addTrap(byte trap) {
		traps.add(trap);
	}

	public TeamGen getGen() {
		return gen;
	}

	public void setGen(TeamGen gen) {
		this.gen = gen;
	}

	public Location getSpawn() {
		return spawn;
	}

	public ChatColor getChatColor() {
		return chatColor;
	}

	public String getColor() {
		return color;
	}

	public List<OfflinePlayer> getPlayers() {
		return players;
	}

	public void addPlayer(OfflinePlayer player) {
		players.add(player);
	}

	public Location getBaseCenter() {
		return baseCenter;
	}

	public void logTriggerTrap() {
		this.lastTrapTrigger = System.currentTimeMillis();
	}

	public boolean canTrapTrigger() {
		return (this.lastTrapTrigger + 15000 < System.currentTimeMillis() && !traps.isEmpty());
	}

	public void updateInventories() {
		for (OfflinePlayer p : players) {
			if (p.isOnline()) {
				for (ItemStack it : p.getPlayer().getInventory().getContents()) {
					if (sharp && it != null)
						if (sharpenable.contains(it.getType()))
							it.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
				}

				for (ItemStack it : p.getPlayer().getInventory().getArmorContents()) {
					if (protection != 0)
						it.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, protection);
				}
			}
		}
	}

	public void updateEffects() {
		for (OfflinePlayer p : players) {
			if (p.isOnline()) {
				if (haste != 0)
					p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, haste, false, false));
			}
		}
	}

	private static List<Material> sharpenable = new ArrayList<Material>() {{
		add(Material.WOOD_SWORD);
		add((Material.WOOD_AXE));
		add((Material.WOOD_PICKAXE));
		add(Material.STONE_SWORD);
		add(Material.STONE_AXE);
		add(Material.STONE_PICKAXE);
		add(Material.IRON_SWORD);
		add(Material.IRON_PICKAXE);
		add(Material.IRON_AXE);
		add(Material.GOLD_AXE);
		add(Material.GOLD_PICKAXE);
		add(Material.GOLD_SWORD);
		add(Material.DIAMOND_AXE);
		add(Material.DIAMOND_PICKAXE);
		add(Material.DIAMOND_SWORD);
	}};
}
