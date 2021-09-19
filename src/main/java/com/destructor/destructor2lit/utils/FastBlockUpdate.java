package com.destructor.destructor2lit.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FastBlockUpdate {

	private JavaPlugin javaPlugin;
	private int blocksPerTick;

	private Map<Location, Map<Material, Byte>> blocks = new LinkedHashMap<Location, Map<Material, Byte>>();
	private FastBlockUpdateTask fastBlockUpdateTask = null;

	public FastBlockUpdate(JavaPlugin javaPlugin, int blocksPerTick) {
		this.javaPlugin = javaPlugin;
		this.blocksPerTick = blocksPerTick;
	}

	public int getBlocksPerTick() {
		return blocksPerTick;
	}

	public void setBlocksPerTick(int blocksPerTick) {
		this.blocksPerTick = blocksPerTick;
	}

	public Map<Location, Map<Material, Byte>> getBlocks() {
		return blocks;
	}

	public void setBlocks(Map<Location, Map<Material, Byte>> blocks) {
		this.blocks = blocks;
	}

	public void addBlock(Location location, Material material, Byte b) {
		Map<Material, Byte> inner = new HashMap<Material, Byte>();
		inner.put(material, b);
		getBlocks().put(location, inner);
	}

	public void run() {
		fastBlockUpdateTask = new FastBlockUpdateTask(getBlocksPerTick(), getBlocks());
		fastBlockUpdateTask.start();
		new BukkitRunnable() {
			@Override
			public void run() {
				if (isComplete()) {
					fastBlockUpdateTask = null;
				}
			}
		}.runTaskTimer(javaPlugin, 10, 10);
	}

	public boolean isRunning(){
		return fastBlockUpdateTask != null;
	}

	public boolean isComplete() {
		if (fastBlockUpdateTask == null) {
			return false;
		} else {
			return fastBlockUpdateTask.isComplete();
		}
	}

	public class FastBlockUpdateTask {

		Map<Location, Map<Material, Byte>> blocksRemaining = new LinkedHashMap<Location, Map<Material, Byte>>();
		private List<BukkitRunnable> tasks = new ArrayList<BukkitRunnable>();

		private int blocksPerTicks;

		private boolean complete;

		FastBlockUpdateTask(int blocksPerSecond, Map<Location, Map<Material, Byte>> blocks) {
			this.blocksPerTicks = blocksPerSecond;
			this.blocksRemaining.putAll(blocks);
		}

		public void start() {
			for (int i = 0; i < (blocksRemaining.size() / blocksPerTicks) + 1; i++) {
				tasks.add(new BukkitRunnable() {
					@Override
					public void run() {
						for (int blocks = 0; blocks < blocksPerTicks; blocks++) {
							if (!blocksRemaining.entrySet().iterator().hasNext()) break;
							Map.Entry<Location, Map<Material, Byte>> entry = blocksRemaining.entrySet().iterator().next();
							Location key = entry.getKey();
							Map<Material, Byte> value = entry.getValue();
							Material material = null;
							for (Material mat : value.keySet()) material = mat;
							Byte data = value.get(material);
							key.getBlock().setTypeIdAndData(material.getId(), data, false);
							blocksRemaining.remove(key);
						}
						if (blocksRemaining.size() < 1) {
							setComplete(true);
							for (BukkitRunnable runnable : tasks) runnable.cancel();
						}
					}
				});
			}
			for (int i = 0; i < tasks.size(); i++) {
				BukkitRunnable runnable = tasks.get(i);
				runnable.runTaskLater(javaPlugin, (i + 1));
			}
		}

		public boolean isComplete() {
			return complete;
		}

		public void setComplete(boolean complete) {
			this.complete = complete;
		}
	}

}
