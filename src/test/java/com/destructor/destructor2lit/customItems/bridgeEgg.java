package com.destructor.destructor2lit.customItems;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Utils;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.bukkit.*;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class bridgeEgg {
	Utils utils = new Utils();
	Egg egg;
	byte itemData;

	public bridgeEgg(String color, Egg egg, Main main, int bridgeWidth) {
		switch (color) {
			case "red":
				itemData = 14;
				break;
			case "blue":
				itemData = 11;
				break;
			case "green":
				itemData = 5;
				break;
			case "yellow":
				itemData = 4;
				break;
			case "aqua":
				itemData = 9;
				break;
			case "white":
				itemData = 0;
				break;
			case "pink":
				itemData = 6;
				break;
			case "gray":
				itemData = 7;
				break;
			default:
				itemData = 0;
				break;
		}
		this.egg = egg;
		egg.setBounce(true);
		new BukkitRunnable() {
			ArrayList<Location> eggLocations = new ArrayList<>();
			int i = 0;

			@Override
			public void run() {
				utils.setMetadata((Player) egg.getShooter(), "lastfb", new SystemClock().currentTimeMillis());
				eggLocations.add(egg.getLocation().clone());

				if (i >= 4) {
					if (eggLocations.get(i - 2).getY() < main.builddownlimit || eggLocations.get(i - 2).getY() > main.buildlimit) {
						egg.remove();
						this.cancel();
					}
					egg.getWorld().playSound(((Player) egg.getShooter()).getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
					eggLocations.get(i - 2).getBlock().setType(Material.WOOL);
					eggLocations.get(i - 2).getBlock().setData(itemData);
					eggLocations.get(i - 2).getBlock().setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
					eggLocations.get(i - 2).getBlock().getRelative(0, 0, 1).setType(Material.WOOL);
					eggLocations.get(i - 2).getBlock().getRelative(0, 0, 1).setData(itemData);
					eggLocations.get(i - 2).getBlock().getRelative(0, 0, 1).setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 0).setType(Material.WOOL);
					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 0).setData(itemData);
					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 0).setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 1).setType(Material.WOOL);
					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 1).setData(itemData);
					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 1).setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
				}

				if (egg.isDead()) {
					this.cancel();
				}


				i++;
			}


		}.runTaskTimer(main, 0, 1);
	}

}
