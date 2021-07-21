package com.destructor.destructor2lit.customItems;

import com.destructor.destructor2lit.Main;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.effect.CubeEffect;
import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class bwSponge {
	public bwSponge(Main main, Block block) {
		int rayon = 5;
		for (int x = -rayon; x <= rayon; x++) {
			for (int y = -rayon; y <= rayon; y++) {
				for (int z = -rayon; z <= rayon; z++) {
					if (block.getRelative(x, y, z).getType().equals(Material.STATIONARY_WATER)||block.getRelative(x, y, z).getType().equals(Material.WATER)) {
						block.getRelative(x, y, z).setType(Material.AIR);
					}
				}
			}
		}

		Stack<Player> targetPlayers= new Stack<>();
		targetPlayers.addAll(Bukkit.getOnlinePlayers());
		for (Player player : main.noAnimationPlayers) {
			targetPlayers.remove(player);
		}
		for (int i = 1; i <= 8; i++) {
			CubeEffect effect = new CubeEffect(main.effectManager);
			effect.visibleRange = 25;
			effect.duration = 3000;
			effect.delay = (i - 1) * 10;
			effect.particles = 1 + Math.floorDiv(i,2);
			effect.speed = 0.04f;
			effect.particle = ParticleEffect.CLOUD;
			effect.outlineOnly = false;
			effect.edgeLength = i;
			effect.targetPlayers= targetPlayers;
			effect.enableRotation = false;
			effect.particleOffsetX = 0.18f;
			effect.particleOffsetY = 0.18f;
			effect.particleOffsetZ = 0.18f;
			effect.setLocation(block.getLocation().add(0.5, 0.5, 0.5));
			effect.start();
		}

		new BukkitRunnable() {
			int i=0;
			@Override
			public void run() {
				if(i==6){
					block.getWorld().playSound(block.getLocation(),Sound.SPLASH2,2,1.55f);
					block.setType(Material.AIR);
					this.cancel();
				} else {
					block.getWorld().playSound(block.getLocation(), Sound.CLICK,0.5f,0.5f);
				}

				i++;
			}
		}.runTaskTimer(main,0,10);

	}
}
