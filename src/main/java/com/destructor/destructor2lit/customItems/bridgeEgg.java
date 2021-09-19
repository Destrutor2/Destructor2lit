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
		itemData = Utils.colorData(color);
		this.egg = egg;
		egg.setBounce(true);
		new BukkitRunnable() {
			ArrayList<Location> eggLocations = new ArrayList<>();
			int i = 0;

			@Override
			public void run() {
				eggLocations.add(egg.getLocation().clone());

				if (i >= 4) {
					if (eggLocations.get(i - 2).getY() < main.bwConfig.buildlimit || eggLocations.get(i - 2).getY() > main.bwConfig.builddownlimit) {
						egg.remove();
						this.cancel();
						return;
					}
					egg.getWorld().playSound(((Player) egg.getShooter()).getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
					eggLocations.get(i - 2).getBlock().setType(Material.WOOL);
					eggLocations.get(i - 2).getBlock().setData(itemData);
					eggLocations.get(i - 2).getBlock().setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
					for(int x=0;x<=bridgeWidth-1;x++){
						for(int z=0;z<=bridgeWidth-1;z++){
							eggLocations.get(i - 2).getBlock().getRelative(x, 0, z).setType(Material.WOOL);
							eggLocations.get(i - 2).getBlock().getRelative(x, 0, z).setData(itemData);
							eggLocations.get(i - 2).getBlock().getRelative(x, 0, z).setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
						}
					}
//					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 0).setType(Material.WOOL);
//					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 0).setData(itemData);
//					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 0).setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
//					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 1).setType(Material.WOOL);
//					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 1).setData(itemData);
//					eggLocations.get(i - 2).getBlock().getRelative(1, 0, 1).setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
				}

				if (egg.isDead()) {
					this.cancel();
				}


				i++;
			}


		}.runTaskTimer(main, 0, 1);
	}

}
