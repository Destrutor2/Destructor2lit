package com.destructor.destructor2lit.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public class ArmorStandUtils {
	public static List<ArmorStand> createTextArmorstand(Location position, String[] text) {
		List<ArmorStand> armorStands = new ArrayList<>();

		for (String s : text) {
			ArmorStand armorStand = position.getWorld().spawn(position.clone().add(0, -2 - (armorStands.size() * 0.37), 0), ArmorStand.class);
			armorStand.setVisible(false);
			armorStand.setGravity(false);
			armorStand.setMarker(false);
			armorStand.setCustomName(s);
			armorStand.setCustomNameVisible(true);
			armorStands.add(armorStand);
		}

		return armorStands;
	}

}
