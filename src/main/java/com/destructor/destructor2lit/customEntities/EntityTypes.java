package com.destructor.destructor2lit.customEntities;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum EntityTypes {
	//NAME("Entity name", Entity ID, yourcustomclass.class);
	ANGRY_GOLEM("AngryGolem", 99, AngryIronGolem.class, EntityIronGolem.class),
	OWNER_SILVERFISH("Owned Silverfish", 60, OwnedSilverfish.class, EntitySilverfish.class),
	CUSTOM_ARROW("Custom Arrow", 10, CustomArrow.class, EntityArrow.class);
//	GENERATOR("Generator", 30, Generator.class, EntityArmorStand.class);


	private String name;
	private int id;
	private EntityType entityType;
	private Class<? extends EntityInsentient> nmsClass;


	private EntityTypes(String name, int id, Class<? extends Entity> custom, Class<? extends Entity> nmsClass) {
		addToMaps(name, id, nmsClass, custom);
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public Class<? extends EntityInsentient> getNMSClass() {
		return nmsClass;
	}

	public Class<? extends EntityInsentient> getCustomClass() {
		return customClass;
	}

	private Class<? extends EntityInsentient> customClass;

	public static void spawnEntity(Entity entity, Location loc) {
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		((CraftWorld) loc.getWorld()).getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
//		entity.getBukkitEntity().teleport(loc.add(0, 2, 0));

	}

	private static void addToMaps(String name, int id, Class<? extends Entity> nmsClass, Class<? extends Entity> customClass) {
//		//getPrivateField is the method from above.
//		//Remove the lines with // in front of them if you want to override default entities (You'd have to remove the default entity from the map first though).
//		((Map) getPrivateField("c", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(name, clazz);
//		((Map) getPrivateField("d", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, name);
//		//((Map)getPrivateField("e", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
//		((Map) getPrivateField("f", net.minecraft.server.v1_8_R3.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
//		//((Map)getPrivateField("g", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, Integer.valueOf(id));

		try {

			/*
			 * First, we make a list of all HashMap's in the EntityTypes class
			 * by looping through all fields. I am using reflection here so we
			 * have no problems later when minecraft changes the field's name.
			 * By creating a list of these maps we can easily modify them later
			 * on.
			 */
			List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
			for (Field f : net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);
					dataMaps.add((Map<?, ?>) f.get(null));
				}
			}

			/*
			 * since minecraft checks if an id has already been registered, we
			 * have to remove the old entity class before we can register our
			 * custom one
			 *
			 * map 0 is the map with names and map 2 is the map with ids
			 */
			if (dataMaps.get(2).containsKey(id)) {
				dataMaps.get(0).remove(name);
				dataMaps.get(2).remove(id);
			}

			/*
			 * now we call the method which adds the entity to the lists in the
			 * EntityTypes class, now we are actually 'registering' our entity
			 */
			Method method = net.minecraft.server.v1_8_R3.EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			method.setAccessible(true);
			method.invoke(null, customClass, name, id);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Object getPrivateStatic(Class clazz, String f) throws Exception {
		Field field = clazz.getDeclaredField(f);
		field.setAccessible(true);
		return field.get(null);
	}

	private static void a(Class paramClass, String paramString, int paramInt) {
		try {
			((Map) getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
			((Map) getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
			((Map) getPrivateStatic(EntityTypes.class, "e")).put(Integer.valueOf(paramInt), paramClass);
			((Map) getPrivateStatic(EntityTypes.class, "f")).put(paramClass, Integer.valueOf(paramInt));
			((Map) getPrivateStatic(EntityTypes.class, "g")).put(paramString, Integer.valueOf(paramInt));
		} catch (Exception exc) {
// Unable to register the new class.
		}
	}
}
