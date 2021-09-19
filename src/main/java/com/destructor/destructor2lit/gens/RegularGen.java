package com.destructor.destructor2lit.gens;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.enums.RegularGenType;
import com.destructor.destructor2lit.utils.ArmorStandUtils;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class RegularGen {
	int delay;
	int timer;
	RegularGenType genType;
	Main main;
	public EntityArmorStand armorStand;
	public Material displayItem;


	public RegularGen(RegularGenType type, Location location, Main main) {
		timer = main.getGamePhase().getGenDelay(type.getDroppedItem());
//		Hologram hologram = HologramsAPI.createHologram(main, location);
//		hologram.appendTextLine(YELLOW + "Tier " + ChatColor.RED + "I");
//		hologram.appendTextLine(type.getColor() + "" + ChatColor.BOLD + type.getDisplayName());
//		hologram.appendTextLine(YELLOW + "Spawns in " + ChatColor.RED + timer + YELLOW + " seconds");
		String[] lines = {YELLOW + "Tier " + RED + "I", (type.getColor() + "" + BOLD + type.getDisplayName()), YELLOW + "Spawns in " + RED + timer + YELLOW + " seconds"};
		List<ArmorStand> textArmorStands = ArmorStandUtils.createTextArmorstand(location.clone().add(0, 0.5, 0), lines);

		this.displayItem = type.getDisplayItem();
		this.genType = type;
		this.main = main;
//		ArmorStand armorStand = location.getWorld().spawn(location.add(0, -3, 0), ArmorStand.class);
//		armorStand.setVisible(false);
//		armorStand.setGravity(false);
//		armorStand.setBasePlate(false);
//		armorStand.setHelmet(new ItemStack(type.getDisplayItem()));
//		this.armorStand = armorStand;
//		Generator generator = new Generator(((CraftWorld) location.getWorld()).getHandle());
//		generator.setPositionRotation(new BlockPosition(location.getX(), location.getY() - 2.5, location.getZ()), 0F, 0F);
//		ArmorStand armorStand = (ArmorStand) generator.getBukkitEntity();
		EntityArmorStand armorStand = new EntityArmorStand(((CraftWorld) location.getWorld()).getHandle());
		armorStand.setPositionRotation(new BlockPosition(location.getX(), location.getY() - 2.5, location.getZ()), 0F, 0F);
		armorStand.setInvisible(true);

		List<Float> pi = new ArrayList<>();
		for (int count = 0; (((float) count - 1) / 50) < 2; count++) {
			pi.add((float) (Math.sin((((float) count / 50)) * Math.PI)));
		}
//		Bukkit.broadcastMessage("yaws: " + yaws);
//		Bukkit.broadcastMessage("yValues: " + yValues);
//		double test=0;
//		for(float y:pi){
//			test+=y;
//		}
//		Bukkit.broadcastMessage(String.valueOf(test));

		for (Player player : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntity(armorStand, 78));
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), true));
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(armorStand.getId(), 4, CraftItemStack.asNMSCopy(new ItemStack(type.getDisplayItem()))));
		}

		new BukkitRunnable() {
			int counter = 0;

			@Override
			public void run() {
				if (counter > pi.size() - 2) {
					counter = 0;
				} else {
					counter++;
				}
//				Byte y = (byte) (yValues.get((counter + 500) % (yValues.size() - 1)) * 4096);
//				Byte yaw = (byte) (yaws.get(counter) * 360);
//				PacketContainer packet = main.protocolManager.createPacket(WrapperPlayServerRelEntityMove.TYPE);
//				WrapperPlayServerRelEntityMove wrapperPlayServerRelEntityMove = new WrapperPlayServerRelEntityMove(packet);
//				wrapperPlayServerRelEntityMove.setEntityID(armorStand.getId());
//				wrapperPlayServerRelEntityMove.setDy(y);
//				wrapperPlayServerRelEntityMove.setYaw(yaw);
//				wrapperPlayServerRelEntityMove.setOnGround(false);
//				main.protocolManager.broadcastServerPacket(packet);
				for (Player player : Bukkit.getOnlinePlayers()) {
//					if(counter==0){
//						((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(armorStand));
//					}
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(armorStand.getId(), (byte) 0,
							(byte) (pi.get((counter)) * 1.3), (byte) 0,
							(byte) ((pi.get((counter + 31) % (pi.size() - 1)) * 256)), (byte) 0, false));
				}
			}
		}.runTaskTimerAsynchronously(main, 0, 1);

		this.armorStand = armorStand;

		new BukkitRunnable() {
			@Override
			public void run() {

				if (timer == 0) {
					int nearbySimilarItems = 0;
					for (Entity e : location.getWorld().getNearbyEntities(location.clone().add(0, -3, 0), 2, 2, 2)) {
						if (e.getType() == EntityType.DROPPED_ITEM) {
							if (((Item) e).getItemStack().getType().equals(type.getDroppedItem())) {
								nearbySimilarItems += ((Item) e).getItemStack().getAmount();
							}
						}
					}
					if (nearbySimilarItems < type.getMaxSpawn()) {
						Item item = location.getWorld().dropItem(location.clone().add(0, -3, 0), new ItemStack(type.getDroppedItem()));
						item.setVelocity(new Vector());
						item.setMetadata("nonstackable", new FixedMetadataValue(main, false));
						item.setPickupDelay(0);
					}
					timer = main.getGamePhase().getGenDelay(type.getDroppedItem());
				}


				textArmorStands.get(0).setCustomName(YELLOW + "Tier " + RED + StringUtils.repeat("I", main.getGamePhase().getGenLevel(type.getDroppedItem())));
				textArmorStands.get(2).setCustomName(YELLOW + "Spawns in " + RED + timer + YELLOW + " seconds");
				timer--;
			}
		}.runTaskTimer(main, 0, 20);
	}

	public RegularGenType getGenType() {
		return genType;
	}
//	private void start() {
////		armorStand.setCustomNameVisible(true);
//		List<Double> yValues = new ArrayList<>();
//		List<Float> yawValues = new ArrayList<>();
//		List<Double> piValues = new ArrayList<>();
//		Location location = armorStand.getLocation().clone();
//		double baseY = location.getY();
//
//		for (int counter = 0; counter < Math.PI * 100; counter++) {
//			piValues.add(Math.sin(0.02 * counter));
//		}
//
//		new BukkitRunnable() {
//			int counter = 0;
//			Location baseLoc = armorStand.getLocation().clone().add(0, 2, 0);
//
//			@Override
//			public void run() {
//				armorStand.teleport(baseLoc.clone().add(0, piValues.get(counter), 0));
//
//				if (counter > piValues.size() - 2) {
//					counter = 0;
//				} else {
//					counter++;
//				}
//			}
//		}.runTaskTimer(main, 0, 1);
//	}


}
