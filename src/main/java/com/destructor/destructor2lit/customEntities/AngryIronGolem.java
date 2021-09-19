package com.destructor.destructor2lit.customEntities;


import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Utils;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.*;

import static net.md_5.bungee.api.ChatColor.RED;
import static org.bukkit.ChatColor.*;

public class AngryIronGolem extends EntityIronGolem {
	private Main main;
	private EntityIronGolem entityIronGolem;
	public AngryIronGolem(World world,Main main) {
		super(((CraftWorld) world).getHandle());
		List goalB = (List) getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
		goalB.clear();
		List goalC = (List) getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
		goalC.clear();
		List targetB = (List) getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
		targetB.clear();
		List targetC = (List) getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
		targetC.clear();


		this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, true));
		this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
		this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
		this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		this.goalSelector.a(5, new PathfinderGoalOfferFlowerToBwPlayer(this));
//		this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
		this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
//		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
//		this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
//		this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
		this.targetSelector.a(1, new AngryIronGolem.PathfinderGoalNearestAttackableBwPlayer<EntityPlayer>(this, EntityPlayer.class, false));

		this.main=main;
		this.entityIronGolem=this;
	}

//	private Class customPathfinderGoalMeleeAttack(EntityCreature var1, Class<? extends Entity> var2, double var3, boolean var5){
//
//	}

	public void setTarget(EntityLiving entityLiving, EntityTargetEvent.TargetReason targetReason, boolean fireEvent) {
		this.setGoalTarget(entityLiving, targetReason, fireEvent);
	}


	public boolean r(Entity entity){
		new BukkitRunnable() {
			int i=6;
			@Override
			public void run() {
				if(i==0||entityIronGolem.dead){
					this.cancel();
				}
				if(i==4){
					boolean flag = entity.damageEntity(DamageSource.mobAttack(entityIronGolem), (float)(7 + new Random().nextInt(15)));
				}
				i--;
				entity.world.broadcastEntityEffect(entityIronGolem,(byte)4);
				entityIronGolem.makeSound("mob.irongolem.throw", 1.0F, 1.0F);

			}
		}.runTaskTimer(main,0,1);
		return false;
	}

	public static Object getPrivateField(String fieldName, Class clazz, Object object) {
		Field field;
		Object o = null;

		try {
			field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			o = field.get(object);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return o;
	}

	public class PathfinderGoalNearestAttackableBwPlayer<T extends EntityLiving> extends PathfinderGoalTarget {
		protected final Class<T> a;
		private final int g;
		protected final net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget.DistanceComparator b;
		protected Predicate<? super T> c;
		protected EntityLiving d;
		private EntityCreature entityCreature;

		public PathfinderGoalNearestAttackableBwPlayer(EntityCreature entitycreature, Class<T> oclass, boolean flag) {
			this(entitycreature, oclass, flag, false);
		}

		public PathfinderGoalNearestAttackableBwPlayer(EntityCreature entitycreature, Class<T> oclass, boolean flag, boolean flag1) {
			this(entitycreature, oclass, 10, flag, flag1, (Predicate) null);
		}

		public PathfinderGoalNearestAttackableBwPlayer(EntityCreature entitycreature, Class<T> oclass, int i, boolean flag, boolean flag1, final Predicate<? super T> predicate) {
			super(entitycreature, flag, flag1);
			this.a = oclass;
			this.g = i;
			this.b = new net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget.DistanceComparator(entitycreature);
			this.a(1);
			this.entityCreature = entitycreature;
			this.c = new Predicate() {
				public boolean a(T t0) {
					if (predicate != null && !predicate.apply(t0)) {
						return false;
					} else {
						if (t0 instanceof EntityHuman) {
							double d0 = PathfinderGoalNearestAttackableBwPlayer.this.f();
							if (t0.isSneaking()) {
								d0 *= 0.800000011920929D;
							}

							if (t0.isInvisible()) {
								float f = ((EntityHuman) t0).bY();
								if (f < 0.1F) {
									f = 0.1F;
								}

								d0 *= (double) (0.7F * f);
							}

							if ((double) t0.g(PathfinderGoalNearestAttackableBwPlayer.this.e) > d0) {
								return false;
							}
						}

						return PathfinderGoalNearestAttackableBwPlayer.this.a(t0, false);
					}
				}

				public boolean apply(Object object) {
					return this.a((T) object);
				}
			};
		}

		public boolean a() {
			if (this.g > 0 && this.e.bc().nextInt(this.g) != 0) {
				return false;
			} else {
				double d0 = this.f();
				List list = this.e.world.a(this.a, this.e.getBoundingBox().grow(d0, 5.0D, d0), Predicates.and(this.c));
				for (Object e : new ArrayList<>(list)) {
					if (Utils.getMetadata(((EntityHuman) e).getBukkitEntity(), "color") != null) {
						if (Utils.getMetadata(((EntityPlayer) e).getBukkitEntity(), "color").asString().equals(Utils.getMetadata(entityCreature.getBukkitEntity(), "color").asString())) {
							list.remove(e);
						}
					} else {
						list.remove(e);
					}
				}
				Collections.sort(list, this.b);
				if (list.isEmpty()) {
					return false;
				} else {
					this.d = (EntityLiving) list.get(0);
					return true;
				}
			}
		}

		public void c() {
			this.e.setGoalTarget(this.d, this.d instanceof EntityPlayer ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER : EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
			super.c();
		}

		public class DistanceComparator implements Comparator<Entity> {
			private final Entity a;

			public DistanceComparator(Entity entity) {
				this.a = entity;
			}

			public int a(Entity entity, Entity entity1) {
				double d0 = this.a.h(entity);
				double d1 = this.a.h(entity1);
				return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
			}

			public int compare(Entity object, Entity object1) {
				return this.a(object, object1);
			}
		}
	}

	private class PathfinderGoalOfferFlowerToBwPlayer extends PathfinderGoal {
		private AngryIronGolem a;
		private EntityHuman b;
		private int c;

		public PathfinderGoalOfferFlowerToBwPlayer(AngryIronGolem var1) {
			this.a = var1;
			this.a(3);
		}

		public boolean a() {
			if (!this.a.world.w()) {
				return false;
			} else if (this.a.bc().nextInt(24000) != 0) {
				return false;
			} else {
				this.b = (EntityHuman) this.a.world.a(EntityHuman.class, this.a.getBoundingBox().grow(3, 2, 3), this.a);
				if(this.b==null)
					return false;
				if(Utils.getMetadata(b.getBukkitEntity(),"color")==null)
					return false;
				if(Utils.getMetadata(a.getBukkitEntity(),"color").asString().equals(Utils.getMetadata(b.getBukkitEntity(),"color").asString())){
					this.b.getBukkitEntity().sendMessage(ChatColor.AQUA+""+ITALIC + "The golem gave you a sussy flower °_°");
					ItemStack flower = new Utils().customItem(Material.RED_ROSE, RED + "" + BOLD + "Golem's gift", new String[]{RED + "█████", WHITE + "███"+RED+"███",WHITE + "███"+RED+"███",RED+"██████",RED+"█████",RED+"█     █"});
					ItemMeta flowerMeta = flower.getItemMeta();
					flowerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					flower.setItemMeta(flowerMeta);
					this.b.getBukkitEntity().getInventory().addItem(flower);

				return this.b != null;}
				else {
					return false;
				}
			}
		}

		public boolean b() {
			return this.c > 0;
		}

		public void c() {
			this.c = 50;
			this.a.a(true);
		}

		public void d() {
			this.a.a(false);
			this.b = null;
		}

		public void e() {
			this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
			this.c--;
		}
	}
}



	/*private class PathfinderGoalBwEnemy extends PathfinderGoal {
		private final Logger a = LogManager.getLogger();
		private EntityInsentient b;
		private final Predicate<Entity> c;
		private final PathfinderGoalNearestAttackableTarget.DistanceComparator d;
		private EntityLiving e;

		public PathfinderGoalBwEnemy(EntityInsentient entityinsentient) {
			this.b = entityinsentient;
			if (entityinsentient instanceof EntityCreature) {
				a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
			}

			this.c = new Predicate() {
				public boolean a(Entity entity) {
					if (!(entity instanceof EntityHuman)) {
						return false;
					} else if (((EntityHuman) entity).abilities.isInvulnerable||Utils.getMetadata(entity.getBukkitEntity(),"color").equals(Utils.getMetadata(entityinsentient.getBukkitEntity(),"color"))) {
						return false;
					} else {
						double d0 = PathfinderGoalBwEnemy.this.f();
						if (entity.isSneaking()) {
							d0 *= 0.800000011920929D;
						}

						if (entity.isInvisible()) {
							float f = ((EntityHuman) entity).bY();
							if (f < 0.1F) {
								f = 0.1F;
							}

							d0 *= (double) (0.7F * f);
						}

						return (double) entity.g(PathfinderGoalBwEnemy.this.b) > d0 ? false : PathfinderGoalTarget.a(PathfinderGoalBwEnemy.this.b, (EntityLiving) entity, false, true);
					}
				}

				public boolean apply(Object object) {
					return this.a((Entity) object);
				}
			};
			this.d = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entityinsentient);
		}

		public boolean a() {
			double d0 = this.f();
			List list = this.b.world.a(EntityHuman.class, this.b.getBoundingBox().grow(d0, 4.0D, d0), this.c);
			Collections.sort(list, this.d);
			if (list.isEmpty()) {
				return false;
			} else {
				this.e = (EntityLiving) list.get(0);
				return true;
			}
		}

		public boolean b() {
			EntityLiving entityliving = this.b.getGoalTarget();
			if (entityliving == null) {
				return false;
			} else if (!entityliving.isAlive()) {
				return false;
			} else if (entityliving instanceof EntityHuman && ((EntityHuman) entityliving).abilities.isInvulnerable) {
				return false;
			} else {
				ScoreboardTeamBase scoreboardteambase = this.b.getScoreboardTeam();
				ScoreboardTeamBase scoreboardteambase1 = entityliving.getScoreboardTeam();
				if (scoreboardteambase != null && scoreboardteambase1 == scoreboardteambase) {
					return false;
				} else {
					double d0 = this.f();
					return this.b.h(entityliving) > d0 * d0 ? false : !(entityliving instanceof EntityPlayer) || !((EntityPlayer) entityliving).playerInteractManager.isCreative();
				}
			}
		}

		public void c() {
			this.b.setGoalTarget(this.e, EntityTargetEvent.TargetReason.CLOSEST_PLAYER, true);
			super.c();
		}

		public void d() {
			this.b.setGoalTarget((EntityLiving) null);
			super.c();
		}

		protected double f() {
			AttributeInstance attributeinstance = this.b.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
			return attributeinstance == null ? 16.0D : attributeinstance.getValue();
		}
	}*/
