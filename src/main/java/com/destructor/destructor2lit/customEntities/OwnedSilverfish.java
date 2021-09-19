package com.destructor.destructor2lit.customEntities;

import com.destructor.destructor2lit.utils.Utils;
import com.google.common.base.Predicates;
import net.minecraft.server.v1_8_R3.*;

import java.util.Collections;
import java.util.List;

import static com.destructor.destructor2lit.customEntities.AngryIronGolem.getPrivateField;

public class OwnedSilverfish extends EntitySilverfish {

	public OwnedSilverfish(net.minecraft.server.v1_8_R3.World world) {
		super(world);
		List goalB = (List) getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
		goalB.clear();
		List goalC = (List) getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
		goalC.clear();
		List targetB = (List) getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
		targetB.clear();
		List targetC = (List) getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
		targetC.clear();

		this.goalSelector.a(1, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableBwPlayer(this, EntityHuman.class, true));
	}

	public void initAttributes() {
		super.initAttributes();
		this.getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4D);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(3.0D);
	}

	private class PathfinderGoalNearestAttackableBwPlayer<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget {
		public PathfinderGoalNearestAttackableBwPlayer(EntityCreature entitycreature, Class oclass, boolean flag) {
			super(entitycreature, oclass, flag);
		}

		public boolean a() {
			double d0 = this.f();
			List list = this.e.world.a(this.a, this.e.getBoundingBox().grow(d0, 4.0D, d0), Predicates.and(this.c, IEntitySelector.d));
			Collections.sort(list, this.b);
			if (list.isEmpty()) {
				return false;
			} else {
				for (Object e : list) {
					if (e instanceof EntityHuman) {
						if (Utils.getMetadata(((EntityHuman) e).getBukkitEntity(), "color").asString().equals(Utils.getMetadata(this.e.getBukkitEntity(), "color").asString())||!Utils.getMetadata(((EntityHuman)e).getBukkitEntity(),"alive").asBoolean()) {
							continue;
						}
						this.d = (EntityLiving) e;
						return true;
					}
				}
				return false;
			}

		}
	}

}
