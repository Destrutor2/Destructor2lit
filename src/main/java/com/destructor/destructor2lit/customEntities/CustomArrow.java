package com.destructor.destructor2lit.customEntities;

import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;

public class CustomArrow extends EntityArrow {
	public CustomArrow(World world, EntityLiving entityliving, EntityLiving entityliving1, float f, float f1) {
		super(world, entityliving, entityliving1, f, f1);
	}

	@Override
	public void t_() {
		super.t_();
		Bukkit.broadcastMessage("arrow hit critical?: "+isCritical());
	}
}
