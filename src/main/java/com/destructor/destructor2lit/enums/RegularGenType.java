package com.destructor.destructor2lit.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum RegularGenType {
	DIAMOND(ChatColor.AQUA, Material.DIAMOND, "Diamond", Material.DIAMOND_BLOCK, 4), EMERALD(ChatColor.GREEN, Material.EMERALD, "Emerald", Material.EMERALD_BLOCK, 2);

	ChatColor color;
	Material droppedItem;
	String displayName;
	Material displayItem;
	int maxSpawn;

	RegularGenType(ChatColor color, Material droppedItem, String displayName, Material displayItem, int maxSpawn) {
		this.color = color;
		this.droppedItem = droppedItem;
		this.displayName = displayName;
		this.displayItem = displayItem;
		this.maxSpawn = maxSpawn;
	}

	public ChatColor getColor() {
		return color;
	}

	public Material getDroppedItem() {
		return droppedItem;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Material getDisplayItem() {
		return displayItem;
	}

	public int getMaxSpawn() {
		return maxSpawn;
	}
}
