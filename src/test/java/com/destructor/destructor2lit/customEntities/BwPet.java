package com.destructor.destructor2lit.customEntities;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.entity.EntityType.*;

public class BwPet {
	private Utils utils = new Utils();
	private EntityType entityType;
	private int maxAliveTime;
	private String color;
	private LivingEntity entity;

	public BwPet(EntityType entityType, int maxAliveTime, Player owner, Location spawnLocation, Main main) {
		this.entityType = entityType;
		this.maxAliveTime = maxAliveTime;
		this.color = utils.getMetadata(owner, "color").asString();

		if (entityType.equals(IRON_GOLEM)) {
			AngryIronGolem angryIronGolem = new AngryIronGolem(spawnLocation.getWorld(), main);
			EntityTypes.spawnEntity(angryIronGolem, spawnLocation);
			entity = (LivingEntity) angryIronGolem.getBukkitEntity();
		} else if (entityType.equals(SILVERFISH)) {
			OwnedSilverfish ownedSilverfish = new OwnedSilverfish(((CraftWorld)spawnLocation.getWorld()).getHandle());
			EntityTypes.spawnEntity(ownedSilverfish, spawnLocation);
			entity = (LivingEntity) ownedSilverfish.getBukkitEntity();
		} else {
			entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, entityType);
		}


		this.entity = entity;
		utils.setMetadata(entity, "owner", owner.getUniqueId());
		utils.setMetadata(entity, "color", color);
		entity.setCustomNameVisible(true);
		entity.setRemoveWhenFarAway(false);

		ChatColor nameColor = utils.getTeamColor(color);

		LivingEntity finalEntity = entity;
		new BukkitRunnable() {
			int timer = maxAliveTime * 10;
			String bar = "■■■■■■■■■■";
			String finalbar;

			@Override
			public void run() {
				timer--;

				if (timer <= 0 || finalEntity.isDead()) {
					finalEntity.remove();
					this.cancel();
				}

				finalbar = nameColor + bar.substring(0, (int) Math.floor(finalEntity.getHealth() / finalEntity.getMaxHealth() * 10)) + ChatColor.GRAY + bar.substring((int) Math.floor(finalEntity.getHealth() / finalEntity.getMaxHealth() * 10));
				finalEntity.setCustomName(nameColor + "" + Math.floorDiv(timer, 10) + "." + (timer % 10) + "s  " + ChatColor.GRAY + "[" + finalbar + "]");

			}
		}.runTaskTimer(main, 0, 2);
	}


	public LivingEntity getEntity() {
		return entity;
	}

	public String getColor() {
		return color;
	}
}
