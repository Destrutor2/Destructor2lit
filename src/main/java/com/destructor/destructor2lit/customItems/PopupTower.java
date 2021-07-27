package com.destructor.destructor2lit.customItems;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class PopupTower {
	int[][] coordsList = {
			{2, -1, -1}, {2, -1, 0}, {2, -1, 1}, {1, -1, 2}, {0, -1, 2}, {-1, -1, 1}, {-1, -1, -1}, {0, -1, -2}, {1, -1, -2},
			{2, 0, -1}, {2, 0, 0}, {2, 0, 1}, {1, 0, 2}, {0, 0, 2}, {-1, 0, 1}, {-1, 0, -1}, {0, 0, -2}, {1, 0, -2},
			{2, 1, -1}, {2, 1, 0}, {2, 1, 1}, {1, 1, 2}, {0, 1, 2}, {-1, 1, 1}, {-1, 1, -1}, {0, 1, -2}, {1, 1, -2},
			{2, 2, -1}, {2, 2, 0}, {2, 2, 1}, {1, 2, 2}, {0, 2, 2}, {-1, 2, 1}, {-1, 2, 0}, {-1, 2, -1}, {0, 2, -2}, {1, 2, -2},
			{2, 3, -1}, {2, 3, 0}, {2, 3, 1}, {1, 3, 2}, {0, 3, 2}, {-1, 3, 1}, {-1, 3, 0}, {-1, 3, -1}, {0, 3, -2}, {1, 3, -2},
			{2, 4, -2}, {2, 4, -1}, {2, 4, 0}, {2, 4, 1}, {2, 4, 2}, {1, 4, 2}, {0, 4, 2}, {-1, 4, 2}, {-1, 4, 1}, {-1, 4, 0}, {-1, 4, -1}, {-1, 4, -2}, {0, 4, -2}, {1, 4, -2}, {1, 4, -1}, {1, 4, 1}, {0, 4, 1}, {0, 4, 0}, {0, 4, -1}, {3, 4, -2}, {3, 4, 0}, {3, 4, 2}, {2, 4, 3}, {-1, 4, 3}, {-2, 4, 2}, {-2, 4, 0}, {-2, 4, -2}, {-1, 4, -3}, {2, 4, -3},
			{3, 5, -2}, {3, 5, -1}, {3, 5, 0}, {3, 5, 1}, {3, 5, 2}, {2, 5, 3}, {1, 5, 3}, {0, 5, 3}, {-1, 5, 3}, {-2, 5, 2}, {-2, 5, 1}, {-2, 5, 0}, {-2, 5, -1}, {-2, 5, -2}, {-1, 5, -3}, {0, 5, -3}, {1, 5, -3}, {2, 5, -3},
			{3, 6, -2}, {3, 6, 0}, {3, 6, 2}, {2, 6, 3}, {-1, 6, 3}, {-2, 6, 2}, {-2, 6, 0}, {-2, 6, -2}, {-1, 6, -3}, {2, 6, -3}};
	int[][] laddersList = {{1, -1, 0}, {1, 0, 0}, {1, 1, 0}, {1, 2, 0}, {1, 3, 0}, {1, 4, 0}};

	public PopupTower(Main main, Block block, Player player, int speedMultiplier) {
		byte facingData = block.getData();
		byte colorData = Utils.colorData(Utils.getMetadata(player, "color").asString());
		block.setType(Material.AIR);

		new BukkitRunnable() {
			int i = 0;
			int j = 0;
			int x;
			int z;
			int checkx;
			int checkz;

			@Override
			public void run() {
				block.getWorld().playSound(block.getLocation(), Sound.CHICKEN_EGG_POP,1,1);
				for (int k = 0; k < speedMultiplier; k++) {
					switch (facingData) {
						case 5:
							x = -coordsList[i][0];
							z = -coordsList[i][2];
							break;
						case 2:
							x = coordsList[i][2];
							z = coordsList[i][0];
							break;
						case 3:
							x = -coordsList[i][2];
							z = -coordsList[i][0];
							break;
						case 4:
						default:
							x = coordsList[i][0];
							z = coordsList[i][2];
							break;
					}
					if (block.getRelative(x, coordsList[i][1], z).getType().equals(Material.AIR)) {
						block.getRelative(x, coordsList[i][1], z).setType(Material.WOOL);
						block.getRelative(x, coordsList[i][1], z).setData(colorData);
						block.getRelative(x, coordsList[i][1], z).setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
					}
					if (j <= laddersList.length - 1) {
						switch (facingData) {
							case 2:
								x = laddersList[j][2];
								z = laddersList[j][0];
								checkx = x;
								checkz = z + 1;
								break;
							case 3:
								x = -laddersList[j][2];
								z = -laddersList[j][0];
								checkx = x;
								checkz = z - 1;
								break;
							case 4:
							default:
								x = laddersList[j][0];
								z = laddersList[j][2];
								checkx = x + 1;
								checkz = z;
								break;
							case 5:
								x = -laddersList[j][0];
								z = -laddersList[j][2];
								checkx = x - 1;
								checkz = z;
								break;
						}
						if (block.getRelative(checkx, laddersList[j][1], checkz).getType().isSolid() && block.getRelative(x, laddersList[j][1], z).getType().equals(Material.AIR)) {
							block.getRelative(x, laddersList[j][1], z).setType(Material.LADDER);
							block.getRelative(x, laddersList[j][1], z).setData(facingData);
							block.getRelative(x, laddersList[j][1], z).setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
							j++;
						}
					}

					if (i > 12 && j == 0)
						j = 1;

					i++;
					if (i > coordsList.length - 1) {
						this.cancel();
						return;
					}
				}
			}
		}.runTaskTimer(main, 0, 1);

	}
}
