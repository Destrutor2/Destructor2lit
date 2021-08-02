package com.destructor.destructor2lit.gens;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.enums.GameState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class TeamGen {
	private final String color;
	private final Location location;
	private final Main main;
	Random random = new Random();
	//	Il faut savoir que ces delays sont en ticks, il est mieux qu'ils soient divisibles par ticksToTry sinon ils ne seront pas précis
	int maxGoldDelay;
	int minGoldDelay;
	int maxIronDelay;
	int ticksToTry;
	private byte lvl;
	private int ironIndex;
	private int nextIron;
	private int goldIndex;
	private int nextGold;
	private int emeraldIndex;
	private int nextEmerald;
	private int nearbyIron;
	private int nearbyGold;
	private int nearbyEmerald;

	public TeamGen(String color, Location location, Main main) {
		this.color = color;
		this.location = location;
		this.main = main;
		maxGoldDelay = main.maxGoldDelay;
		minGoldDelay = main.minGoldDelay;
		maxIronDelay = main.maxIronDelay;
		ticksToTry = main.ticksToTry;
		start();
	}

	private void start() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (main.isState(GameState.PLAYING)) {
					if (ironIndex >= nextIron) {
						if (nearbyIron < 48) {
							Item it = location.getWorld().dropItem(location, new ItemStack(Material.IRON_INGOT));
							it.setMetadata("nonstackable",new FixedMetadataValue(main,true));
							it.setVelocity(new Vector(0, 0, 0));
						}
						nextIron = random.nextInt(maxIronDelay / ticksToTry);
						ironIndex = 0;
						nearbyIron = 0;
						for (Entity e : location.getWorld().getNearbyEntities(location, 2, 2, 2)) {
							if (e.getType().equals(EntityType.DROPPED_ITEM)) {
								if (((Item) e).getItemStack().getType().equals(Material.IRON_INGOT)) {
									nearbyIron += ((Item) e).getItemStack().getAmount();
								}
							}
						}
					} else {
						ironIndex++;
					}

					if (goldIndex >= nextGold) {
						if (nearbyGold < 12) {
							Item it = location.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
							it.setMetadata("nonstackable",new FixedMetadataValue(main,true));
							it.setVelocity(new Vector(0, 0, 0));
						}
						nextGold = minGoldDelay / ticksToTry + random.nextInt(maxGoldDelay / ticksToTry - minGoldDelay / ticksToTry);
						goldIndex = 0;
						nearbyGold = 0;
						for (Entity e : location.getWorld().getNearbyEntities(location, 2, 2, 2)) {
							if (e.getType().equals(EntityType.DROPPED_ITEM)) {
								if (((Item) e).getItemStack().getType().equals(Material.GOLD_INGOT)) {
									nearbyGold += ((Item) e).getItemStack().getAmount();
								}
							}
						}
					} else {
						goldIndex++;
					}
				}
			}
		}.runTaskTimer(main, 0, ticksToTry);
	}

	public byte getLvl() {
		return lvl;
	}

	public void setLvl(byte lvl) {
		this.lvl = lvl;
		switch (lvl) {
			case 1:
				maxIronDelay = 8;
				maxGoldDelay = 30;
				minGoldDelay = 6;
//				ici on mets les caractéristiques des niveaux de gens
			default:
				return;
		}
	}

}
