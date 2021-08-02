//package com.destructor.destructor2lit.customEntities;
//
//import com.comphenix.protocol.events.PacketContainer;
//import com.destructor.destructor2lit.Main;
//import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayServerEntityTeleport;
//import net.minecraft.server.v1_8_R3.EntityArmorStand;
//import net.minecraft.server.v1_8_R3.World;
//import org.bukkit.util.Vector;
//
//
//public class Generator extends EntityArmorStand {
//	//	private int tick = 104;
////	private boolean turn = true;
//	private int tick = 156;
//	private double yChange;
//	private int counter = 0;
//
//	public Generator(World var1) {
//		super(var1);
//		this.noclip = true;
//	}
//
//	public void m() {
//		if (tick == 0) {
//			tick = 208;
//		}
//
//		this.yaw = (float) (200 * Math.sin(counter * 0.05));
//
//
//		if (tick > 104) {
//			this.yChange = 0.007;
//		} else {
//			this.yChange = -0.007;
//		}
//
//		this.yaw %= 360;
//		tick--;
//		counter++;
//		super.setYawPitch(this.yaw, this.pitch);
//		super.m();
////		super.getBukkitEntity().setVelocity(new Vector(0,yChange,0));
//
////		if (this.tick == 58) {
////			if (this.turn) {
////				this.tick = 0;
////				this.turn = false;
////			} else {
////				this.tick = 116;
////				this.turn = true;
////			}
////		}
////
////		double var1 = 0.0D;
////		if (Math.abs(this.tick - 58) <= 15) {
////			this.yaw += this.turn ? 5.0F : -5.0F;
////			var1 = this.turn ? -0.015D : 0.015D;
////		} else {
////			this.yaw += this.turn ? 10.0F : -10.0F;
////			var1 = this.turn ? -0.015D : 0.015D;
////		}
////
////		double var3 = (double)(this.lastYaw - 10.0F);
////		if (var3 < -180.0D) {
////			this.lastYaw += 360.0F;
////		}
////
////		if (var3 >= 180.0D) {
////			this.lastYaw -= 360.0F;
////		}
////
////		this.yaw %= 360.0F;
////		this.tick += this.turn ? -1 : 1;
////		super.move(0.0D, var1, 0.0D);
////		super.setYawPitch(this.yaw, this.pitch);
//	}
//}
