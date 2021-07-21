package com.destructor.destructor2lit.utils;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.enums.BwItem;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class Utils {

	public int countItem(ItemStack[] content, Material it) {
		int icount = 0;
		for (ItemStack itStack : content) {
			if (itStack != null) {
				if (itStack.getType().equals(it)) {
					icount = icount + itStack.getAmount();
				}
			}
		}
		return icount;
	}

	public int countItem(Player player, Material it) {
		return (countItem(player.getInventory().getContents(), it));
	}

	public ItemStack customItem(ItemStack it, String customName, String[] Lore) {
		ItemMeta itMeta = it.getItemMeta();
		itMeta.setDisplayName(customName);
		itMeta.setLore(Arrays.asList(Lore));
		itMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		it.setItemMeta(itMeta);
		return it;
	}

	public ItemStack customItem(Material material, String customName, int Nombre, String[] Lore) {
		ItemStack it = new ItemStack(material, Nombre);
		ItemMeta itMeta = it.getItemMeta();
		itMeta.setDisplayName(customName);
		itMeta.setLore(Arrays.asList(Lore));
		itMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		it.setItemMeta(itMeta);
		return it;
	}

	public ItemStack customItem(Material material, String customName, String[] Lore) {
		return customItem(material, customName, 1, Lore);
	}

	public ItemStack customItem(Material material, String customName) {
		String[] rien = {};
		return customItem(material, customName, 1, rien);

	}

	public ItemStack customItem(Material material, int amount, String customName) {
		String[] rien = {};
		return customItem(material, customName, amount, rien);

	}

	public ItemStack customItem(ItemStack itemStack, String name) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public ItemStack customItem(Material material, String customName, int Nombre, List<String> Lore) {
		ItemStack it = new ItemStack(material, Nombre);
		ItemMeta itMeta = it.getItemMeta();
		itMeta.setDisplayName(customName);
		itMeta.setLore(Lore);
		itMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		it.setItemMeta(itMeta);
		return it;
	}

	public ItemStack customItem(ItemStack it, String customName, List<String> Lore) {
		ItemMeta itMeta = it.getItemMeta();
		itMeta.setDisplayName(customName);
		itMeta.setLore(Lore);
		itMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		it.setItemMeta(itMeta);
		return it;
	}

	//    Petit truc simpatique qui va nous donner la couleur du text en fonction de l'item demandé
	public ChatColor costColor(Material costItem) {
		switch (costItem) {
			case IRON_INGOT:
				return ChatColor.WHITE;
			case GOLD_INGOT:
				return ChatColor.GOLD;
			case EMERALD:
				return ChatColor.DARK_GREEN;
			case DIAMOND:
				return ChatColor.BLUE;
			default:
				return ChatColor.BLACK;
		}
	}

	public String bwName(Material Item) {
		switch (Item) {
			case IRON_INGOT:
				return "Iron";
			case GOLD_INGOT:
				return "Gold";
			case EMERALD:
				return "Emerald";
			case DIAMOND:
				return "Diamond";
			default:
				return "Default bwName (à changer dans Utils)";
		}
	}

	public static void setMetadata(Metadatable obj, String key, Object value) {
		obj.setMetadata(key, new FixedMetadataValue(Main.getPlugin(Main.class), value));
	}

	public static MetadataValue getMetadata(Metadatable obj, String key) {
		for (MetadataValue value : obj.getMetadata(key)) {
			if (value.getOwningPlugin().getDescription().getName().equals(Main.getPlugin(Main.class).getDescription().getName())) {
				return value;
			}
		}
		return null;
	}

	public ChatColor getColor(Player player) {
		switch (getMetadata(player, "color").asString()) {
			case "red":
				return ChatColor.RED;
			case "blue":
				return ChatColor.BLUE;
			case "green":
				return ChatColor.GREEN;
			case "yellow":
				return ChatColor.YELLOW;
			case "aqua":
				return ChatColor.AQUA;
			case "white":
				return ChatColor.WHITE;
			case "pink":
				return ChatColor.LIGHT_PURPLE;
			case "gray":
				return ChatColor.DARK_GRAY;
			default:
				return ChatColor.GRAY;
		}
	}

	public ChatColor getTeamColor(String team) {
		switch (team) {
			case "red":
				return ChatColor.RED;
			case "blue":
				return ChatColor.BLUE;
			case "green":
				return ChatColor.GREEN;
			case "yellow":
				return ChatColor.YELLOW;
			case "aqua":
				return ChatColor.AQUA;
			case "white":
				return ChatColor.WHITE;
			case "pink":
				return ChatColor.LIGHT_PURPLE;
			case "gray":
				return ChatColor.DARK_GRAY;
			default:
				return ChatColor.GRAY;
		}
	}

	public void initPlayerMetadata(Player player) {
		setMetadata(player, "axelevel", (byte) 0);
		setMetadata(player, "picklevel", (byte) 0);
		setMetadata(player, "hasShears", false);
		setMetadata(player, "armor", 0);
		setMetadata(player, "sharp", false);
		setMetadata(player, "protection", 0);
		setMetadata(player, "haste", 0);
		setMetadata(player, "alive", true);
		setMetadata(player, "color", "none");
		setMetadata(player, "spawnX", 0.5);
		setMetadata(player, "spawnY", 101);
		setMetadata(player, "spawnZ", 0.5);
		setMetadata(player, "spawnYaw", 0);
		setMetadata(player, "invincible", false);
		setMetadata(player, "lastdamager", "null");
		setMetadata(player, "lastattack", 0);
		setMetadata(player, "lastMilk", 0);
		setMetadata(player, "lastfb", 0);
	}

	public Material getCostMaterial(String name) {
		for (BwItem item : BwItem.values()) {
			if (item.getItemName().equalsIgnoreCase(name)) {
				return item.getItemCostMaterial();
			}
		}
		return Material.BARRIER;
	}

	public int getPrice(String name) {
		for (BwItem item : BwItem.values()) {
			if (item.getItemName().equalsIgnoreCase(name)) {
				return item.getItemCost();
			}
		}
		return 0;
	}

	public boolean doKnockback(Location center, double radius, double multiplier, double explosionCenterModifierX, double explosionCenterModifierY, double explosionCenterModifierZ, Player thrower, double throwerMultiplier, double throwerVelocityModifier, Main main) {
		Collection<Entity> nearbyEntities = center.getWorld().getNearbyEntities(center, radius, radius, radius);

		if (nearbyEntities == null) {
			return false;
		}

		if (!(thrower instanceof Player)) {
//			Bukkit.broadcastMessage("Did a ghast just shoot a fireball or what?");
			return false;
		}

		List<Entity> affectedEntities = new ArrayList<>();

		for (Entity e : nearbyEntities) {
			if (e.isValid() && (e instanceof LivingEntity)) {
				affectedEntities.add(e);
			}
		}

//		center.getWorld().playEffect(center, Effect.SLIME,20,20);
		SystemClock clock = new SystemClock();

		for (Entity e : affectedEntities) {
			double distance = e.getLocation().distance(center);
//			Bukkit.broadcastMessage(Double.toString(distance));

//			theoriquement en baissant le point de la ou est la fireball, l'explosion devrait faire monter les joueurs un peu plus
			Vector vector = e.getLocation().toVector();
			center.add(explosionCenterModifierX, explosionCenterModifierY, explosionCenterModifierZ);
			vector.subtract(center.toVector());
			vector.normalize();
			vector.multiply(multiplier);

			if (e.equals(thrower)) {
				vector.multiply(throwerMultiplier);
				Location lastloc = e.getLocation();

				new BukkitRunnable() {
					@Override
					public void run() {
						Location newloc = e.getLocation();
						newloc.subtract(lastloc);
						newloc.setX(newloc.getX() * throwerVelocityModifier);
						newloc.setZ(newloc.getZ() * throwerVelocityModifier);
						vector.add(newloc.toVector());
						e.setVelocity(vector);
					}
				}.runTaskLater(main, 1);

			} else {
				e.setVelocity(vector.multiply(1.0 / distance).add(e.getVelocity()));
				if (e instanceof Player || e instanceof Zombie) {
					logAttack(thrower.getUniqueId().toString(),e);
				}
			}
		}

		return true;
	}

	public void useItem(Player player, int amount) {
		ItemStack it = player.getItemInHand();
		it.setAmount(it.getAmount() - amount);
		if (it.getAmount() != 0) {
			player.setItemInHand(it);
		} else {
			player.setItemInHand(null);
		}
	}

	public void doFire(Location center, float fireprobability, int radius) {
		Random random = new Random();
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					if (center.getBlock().getRelative(x, y + 1, z).getType().equals(Material.AIR) && center.getBlock().getRelative(x, y, z).getType().isSolid()) {
						if (center.getBlock().getRelative(x, y, z).hasMetadata("PlacedBlock")) {
							if (random.nextFloat() <= fireprobability / 100) {
								center.getBlock().getRelative(x, y + 1, z).setType(Material.FIRE);
							}
						}
					}
				}
			}
		}
	}

	public void hideArmor(Player player) {
		PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), 1, null);
		PacketPlayOutEntityEquipment chestplatePacket = new PacketPlayOutEntityEquipment(player.getEntityId(), 2, null);
		PacketPlayOutEntityEquipment leggingsPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), 3, null);
		PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), 4, null);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (getMetadata(player, "color") != getMetadata(p, "color")) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(helmetPacket);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(chestplatePacket);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(leggingsPacket);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(bootsPacket);
			}
		}
	}

	public void showArmor(Player player){
		PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), 4, CraftItemStack.asNMSCopy(player.getInventory().getHelmet()));
		PacketPlayOutEntityEquipment chestplatePacket = new PacketPlayOutEntityEquipment(player.getEntityId(), 3, CraftItemStack.asNMSCopy(player.getInventory().getChestplate()));
		PacketPlayOutEntityEquipment leggingsPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), 2, CraftItemStack.asNMSCopy(player.getInventory().getLeggings()));
		PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), 1, CraftItemStack.asNMSCopy(player.getInventory().getBoots()));
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (getMetadata(player, "color") != getMetadata(p, "color")) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(helmetPacket);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(chestplatePacket);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(leggingsPacket);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(bootsPacket);
			}
		}
	}

	public void logAttack(String damagerUUID, Metadatable victim){
		setMetadata(victim,"lastdamager",damagerUUID);
		setMetadata(victim, "lastattack",new SystemClock().currentTimeMillis());
	}

   /* public ItemStack enchantedItemStack(Material material, Enchantment[] enchantments, int[] levels){
        if (!(enchantments.length==levels.length)){return new ItemStack(Material.BARRIER,1);}
        ItemStack itemStack=new ItemStack(material,1);
        for(int i=0;i<levels.length;i++){
            itemStack.addUnsafeEnchantment();
        }
    }*/
}
