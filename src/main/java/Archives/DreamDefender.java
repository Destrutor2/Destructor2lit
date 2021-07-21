//package com.destructor.destructor2lit;
//
//import com.destructor.destructor2lit.utils.Utils;
//import net.minecraft.server.v1_8_R3.*;
//import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
//import org.bukkit.craftbukkit.v1_8_R3.entity.CraftBoat;
//import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
//import org.bukkit.entity.*;
//import org.bukkit.entity.Entity;
//import org.bukkit.event.player.PlayerInteractEvent;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.scheduler.BukkitRunnable;
//
//public class DreamDefender {
//	private final Double targetDistance = Double.MAX_VALUE;
//	private int timer;
//	private LivingEntity target;
//	private Double entityDistance;
//	Utils utils = new Utils();
//
//	public DreamDefender(PlayerInteractEvent e, Main main) {
//		bwPet golemPet = new bwPet(EntityType.IRON_GOLEM, 240, e.getPlayer(), e.getClickedBlock().getLocation().add(0, 2, 0), main);
//		LivingEntity entitygolem = golemPet.getEntity();
//		IronGolem golem = (IronGolem) entitygolem;
//		golem.setPlayerCreated(false);
//
///*		ArmorStand armorStand = (ArmorStand) golem.getWorld().spawnEntity(golem.getLocation(), EntityType.ARMOR_STAND);
//		armorStand.setCustomName(golem.getUniqueId().toString());
//		main.entityHider.addHiddenEntity(armorStand);
//		utils.setMetadata(armorStand,"serverOnly",true);
//		golem.setPassenger(armorStand);
////		com.destructor.destructor2lit.customEntities.AngryIronGolem entityIronGolem = new com.destructor.destructor2lit.customEntities.AngryIronGolem(entitygolem.getWorld());
////		((CraftEntity) entitygolem).setHandle(entityIronGolem);
//
////		Le bukkit runnable qui s'occupe de l'attack et de l'animation
//		new BukkitRunnable() {
//			IronGolem innergolem=golem;
//			@Override
//			public void run() {
//				if (innergolem.isDead()) {
//					this.cancel();
//					return;
//				}
//				if(innergolem.getTarget() !=null){
//				if (innergolem.getLocation().distance(innergolem.getTarget().getLocation()) <= main.golemReach) {
////						target.damage(main.golemDamage, golem);
////						trucs de metadata a mettre ici et possiblement la mort par golem
//
////					innergolem.getTarget().damage(main.golemDamage, innergolem.getPassenger());
////					innergolem.getTarget().setVelocity(target.getLocation().toVector().subtract(innergolem.getLocation().toVector()).normalize().setY(0.5));
//
//					new BukkitRunnable() {
//						int i = 9;
//						@Override
//						public void run() {
//							i--;
//							if (i == 0) {
//								this.cancel();
//							}
////							entityIronGolem.world.broadcastEntityEffect(entityIronGolem, (byte) 4);
////							entityIronGolem.world.makeSound(entityIronGolem, "mob.irongolem.throw", 1.0F, 1.0F);
//						}
//					}.runTaskTimer(main, 0, 1);
//				}
//			}}
//
//		}.runTaskTimer(main, 0, 10);*/
//
//		/*new BukkitRunnable() {
//			@Override
//			public void run() {
////				Si le golem a pas de target on lui en cherche une
//				for (Entity entity : golem.getNearbyEntities(10, 10, 10)) {
//					if (entity instanceof Player) {
//						if (!entity.hasMetadata("NPC")) {
//							golem.setTarget((LivingEntity) entity);
//							break;
////							if (!utils.getMetadata((Player) entity, "color").asString().equals(color)) {
////								entityDistance = Math.pow(entity.getLocation().getX() - golem.getLocation().getX(), 2) + Math.pow(entity.getLocation().getY() - golem.getLocation().getY(), 2) + Math.pow(entity.getLocation().getZ() - golem.getLocation().getZ(), 2);
////								if (entityDistance > targetDistance) {
////									target = (LivingEntity) entity;
////									targetDistance = entityDistance;
////								}
////							}
//						}
//					}
//				}
//				golem.setTarget(target);
//
//
//				golem.setCustomName(String.valueOf((timer - timer % 10) / 10) + timer % 10);
//
//				if (golem.isDead()) {
////					R.I.P. in peace
//					this.cancel();
//				}
//
//
//			}
//		}.runTaskTimer(main, 0, 40);*/
//	}
//
//
//}
//
