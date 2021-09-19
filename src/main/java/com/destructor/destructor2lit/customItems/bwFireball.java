package com.destructor.destructor2lit.customItems;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Utils;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFireball;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class bwFireball {
	PlayerInteractEvent e;
	Main main;
	Player player;


	public bwFireball(PlayerInteractEvent e, Main main) {
		this.main = main;
		this.e = e;
		this.player = e.getPlayer();
		Utils utils = new Utils();
		SystemClock clock = new SystemClock();


		if (utils.getMetadata(player, "lastfb").asLong() < clock.currentTimeMillis() - 500) {
			utils.setMetadata(player, "lastfb", clock.currentTimeMillis());

			utils.useItem(player,1);

			if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {

//				Location spawnloc = player.getEyeLocation();
//				Vector eyedirection = player.getLocation().getDirection();
//				eyedirection.normalize();
//				eyedirection.multiply(1);
//				spawnloc.add(eyedirection);
//				Fireball fb = (Fireball) player.getWorld().spawnEntity(spawnloc, EntityType.FIREBALL);
				Fireball fb = player.launchProjectile(Fireball.class);

				Vector eyedirection = player.getLocation().getDirection().clone();
//				fb.setDirection(eyedirection);
				eyedirection.normalize();
				eyedirection.multiply(0.1*main.getConfig().getDouble("Fireballs.speed"));
				((CraftFireball) fb).getHandle().dirX= eyedirection.getX();
				((CraftFireball) fb).getHandle().dirY= eyedirection.getY();
				((CraftFireball) fb).getHandle().dirZ= eyedirection.getZ();
				fb.setIsIncendiary(false);
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,10,0,false,false),false);

			} else if (e.getAction().equals((Action.RIGHT_CLICK_BLOCK))) {

				Location center = player.getEyeLocation();
				Vector v = center.getDirection().normalize().multiply(0.1);

//			On cherche le block clicke
				for (int i = 1; i <= 100; i++) {
					center.add(v);
					if (center.getBlock().getType() != Material.AIR)
						break;
				}

				utils.doKnockback(center,
						main.getConfig().getDouble("Fireballjump.knockbackradius"),
						main.getConfig().getDouble("Fireballjump.knockbackmodifier"),
						0,
						main.getConfig().getDouble("Fireballjump.explosionheightmodifier"),
						0, player, main.getConfig().getDouble("Fireballjump.throwermultiplier"),
						main.getConfig().getDouble("Fireballjump.velocitymodifier"),
						main);

				center.getWorld().createExplosion(center, (float) main.getConfig().getDouble("Fireballs.explosionpower"), true);
				utils.doFire(center, (float) main.getConfig().getDouble("Fireballs.fireprob"), main.getConfig().getInt("Fireballs.fireradius"));
			}


		} else {
			player.sendMessage(ChatColor.RED + "Please wait 0.5s to use that again");
		}
	}
}

