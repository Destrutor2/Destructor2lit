package com.destructor.destructor2lit.listeners;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.customEntities.BwPet;
import com.destructor.destructor2lit.customEntities.CustomArrow;
import com.destructor.destructor2lit.utils.Utils;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class WorldListeners implements Listener {
	private final Main main;
	private Utils utils = new Utils();

	public WorldListeners(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onExplosion(EntityExplodeEvent e) {
		Entity entity = e.getEntity();
		for (Block block : new ArrayList<Block>(e.blockList())) {
			if (!(block.hasMetadata("PlacedBlock"))) {
				e.blockList().remove(block);
			} else {
				Location vector = entity.getLocation();
				vector.subtract(block.getLocation());
				vector.multiply(0.01);
				Location tmp = new Location(block.getWorld(), 0, 0, 0);
				for (double i = 0; i < 100; i++) {
					Location testLocation = block.getLocation();
					tmp.add(vector);
					testLocation.add(tmp);
					if (block.getWorld().getBlockAt(testLocation).getType().equals(Material.STAINED_GLASS)) {
						e.blockList().remove(block);
					}
				}
/*                Vector vector = new Vector((entity.getLocation().getX() - block.getX()), (entity.getLocation().getY() - block.getY()), (entity.getLocation().getZ() - block.getZ()));
                for (double i = 0.1; i < 1; i = i + 0.1) {
                    if (block.getWorld().getBlockAt(block.getLocation().add(vector.multiply(i))).getType().equals(Material.STAINED_GLASS)) {
                        e.blockList().remove(block);
                    }
                    if (!block.getWorld().getBlockAt(block.getLocation().add(vector.multiply(i))).equals(net.minecraft.server.v1_8_R3.Block.getById(0))) {
                        Bukkit.broadcastMessage("Entre le block " + block.getType().name() + "et la tnt, un block de " + block.getWorld().getBlockAt(block.getLocation().add(vector.multiply(i))).getType().name() + " a été trouvé en position "+block.getWorld().getBlockAt(block.getLocation().add(vector.multiply(i))).getLocation().toString());
                    }
                }*/
			}
		}
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent e) {
		if (e.getEntity().getItemStack().getType().equals(Material.BED)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onGolemDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof IronGolem) {
			e.getDrops().clear();
			e.setDroppedExp(0);
		}
	}

	@EventHandler
	public void onZombieDeath(EntityDamageEvent e) {
		Utils utils = new Utils();
		if (e.getEntity() instanceof Zombie) {
			if (e.getFinalDamage() >= ((Zombie) e.getEntity()).getHealth()) {

				if (!utils.getMetadata(e.getEntity(), "lastdamager").asString().equals("null") && utils.getMetadata(e.getEntity(), "lastattack").asLong() > (new SystemClock().currentTimeMillis() - 30000)) {
					Player killer = Bukkit.getPlayer(UUID.fromString(utils.getMetadata(e.getEntity(), "lastdamager").asString()));
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (main.hasBed(utils.getMetadata(e.getEntity(), "color").asString())) {
							p.sendMessage(utils.getTeamColor(utils.getMetadata(e.getEntity(), "color").asString()) + e.getEntity().getCustomName() + ChatColor.GRAY + " was knocked off a cliff by " + utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY + ".");
						} else {
							p.sendMessage(utils.getTeamColor(utils.getMetadata(e.getEntity(), "color").asString()) + e.getEntity().getCustomName() + ChatColor.GRAY + " was knocked off a cliff by " + utils.getColor(killer) + killer.getDisplayName() + ChatColor.GRAY + "." + ChatColor.AQUA + " FINAL KILL!");
						}
					}
					if (utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.IRON_INGOT) != 0) {
						killer.getInventory().addItem(new ItemStack(Material.IRON_INGOT, utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.IRON_INGOT)));
						killer.sendMessage(ChatColor.WHITE + "+" + utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.IRON_INGOT) + " Iron");
					}
					if (utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.GOLD_INGOT) != 0) {
						killer.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.GOLD_INGOT)));
						killer.sendMessage(ChatColor.GOLD + "+" + utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.GOLD_INGOT) + " Gold");
					}
					if (utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.DIAMOND) != 0) {
						killer.getInventory().addItem(new ItemStack(Material.DIAMOND, utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.DIAMOND)));
						killer.sendMessage(ChatColor.AQUA + "+" + utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.DIAMOND) + " Diamonds");
					}
					if (utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.EMERALD) != 0) {
						killer.getInventory().addItem(new ItemStack(Material.EMERALD, utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.EMERALD)));
						killer.sendMessage(ChatColor.DARK_GREEN + "+" + utils.countItem(main.offlinePlayersInventory.get(UUID.fromString(utils.getMetadata(e.getEntity(), "UUID").asString())), Material.EMERALD) + " Emeralds");
					}
				}

				e.setCancelled(true);
				e.getEntity().teleport(main.spawn);
				e.getEntity().setFallDistance(0);
				((Zombie) e.getEntity()).setHealth(((Zombie) e.getEntity()).getMaxHealth());


				utils.setMetadata(e.getEntity(), "alive", false);
				((Zombie) e.getEntity()).getEquipment().clear();
				((Zombie) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
			}
		}
	}

	@EventHandler
	public void onZombieCatchFire(EntityCombustEvent e) {
		if (e.getEntity() instanceof Zombie) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onFireballExplosion(EntityExplodeEvent e) {
		if (e.getEntity() instanceof Fireball) {
			Utils utils = new Utils();
			e.setCancelled(true);
			utils.doKnockback(e.getLocation(),
					main.getConfig().getDouble("Fireballs.knockbackradius"),
					main.getConfig().getDouble("Fireballs.knockbackmodifier"),
					0,
					main.getConfig().getDouble("Fireballs.explosionheightmodifier"),
					0,
					(Player) ((Fireball) e.getEntity()).getShooter(),
					1,
					1,
					main);

			e.getLocation().getWorld().createExplosion(e.getLocation(), (float) main.getConfig().getDouble("Fireballs.explosionpower"), true);
			utils.doFire(e.getLocation(), (float) main.getConfig().getDouble("Fireballs.fireprob"), main.getConfig().getInt("Fireballs.fireradius"));
		}
	}

	@EventHandler
	public void onBlockExplosion(BlockExplodeEvent e) {
		for (Block block : new ArrayList<Block>(e.blockList())) {
			if (!(block.hasMetadata("PlacedBlock"))) {
				e.blockList().remove(block);
			} else {
				Location vector = e.getBlock().getLocation();
				vector.subtract(block.getLocation());
				vector.multiply(0.01);
				Location tmp = new Location(block.getWorld(), 0, 0, 0);
				for (double i = 0; i < 100; i++) {
					Location testLocation = block.getLocation();
					tmp.add(vector);
					testLocation.add(tmp);
					if (block.getWorld().getBlockAt(testLocation).getType().equals(Material.STAINED_GLASS)) {
						e.blockList().remove(block);
					}
				}
/*                Vector vector = new Vector((entity.getLocation().getX() - block.getX()), (entity.getLocation().getY() - block.getY()), (entity.getLocation().getZ() - block.getZ()));
                for (double i = 0.1; i < 1; i = i + 0.1) {
                    if (block.getWorld().getBlockAt(block.getLocation().add(vector.multiply(i))).getType().equals(Material.STAINED_GLASS)) {
                        e.blockList().remove(block);
                    }
                    if (!block.getWorld().getBlockAt(block.getLocation().add(vector.multiply(i))).equals(net.minecraft.server.v1_8_R3.Block.getById(0))) {
                        Bukkit.broadcastMessage("Entre le block " + block.getType().name() + "et la tnt, un block de " + block.getWorld().getBlockAt(block.getLocation().add(vector.multiply(i))).getType().name() + " a été trouvé en position "+block.getWorld().getBlockAt(block.getLocation().add(vector.multiply(i))).getLocation().toString());
                    }
                }*/
			}
		}
	}

	@EventHandler
	public void onSpongeBreak(BlockBreakEvent e) {
		if (e.getBlock().getType().equals(Material.SPONGE)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemMerge(ItemMergeEvent e) {
		if (e.getEntity().hasMetadata("nonstackable") || e.getTarget().hasMetadata("nonstackable")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent e) {
		if (!e.getBlock().hasMetadata("PlacedBlock")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onGolemSufocation(EntityDamageEvent e) {
		if (e.getEntity() instanceof Golem && e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
			if (Bukkit.getPlayer(UUID.fromString(Utils.getMetadata(e.getEntity(), "owner").asString())).isOnline())
				if (Utils.getMetadata(Bukkit.getPlayer(UUID.fromString(Utils.getMetadata(e.getEntity(), "owner").asString())), "alive").asBoolean()) {
					Bukkit.getPlayer(UUID.fromString(Utils.getMetadata(e.getEntity(), "owner").asString())).sendMessage(ChatColor.RED + "Your Dream Defender was suffocating so it got teleported to you.");
					e.getEntity().teleport(Bukkit.getPlayer(UUID.fromString(Utils.getMetadata(e.getEntity(), "owner").asString())));
				}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWeatherChange(WeatherChangeEvent event) {
		if (event.toWeatherState())
			event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onThunderChange(ThunderChangeEvent event) {
		if (event.toThunderState())
			event.setCancelled(true);
	}

	@EventHandler
	public void onSnowBallHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Snowball) {
			new BwPet(EntityType.SILVERFISH, 15, (Player) e.getEntity().getShooter(), e.getEntity().getLocation().add(0, 1, 0), main);
		}
	}

	@EventHandler
	public void onArrow(EntityShootBowEvent e) {
		EntityArrow nmsOldArrow = ((CraftArrow) e.getProjectile()).getHandle();
		net.minecraft.server.v1_8_R3.World nmsWorld = nmsOldArrow.world;
		EntityLiving shooter = (EntityLiving) nmsOldArrow.shooter;

		CustomArrow customArrow = new CustomArrow(nmsWorld, shooter, shooter, 0, 0);
		customArrow.motX = nmsOldArrow.motX;
		customArrow.motY = nmsOldArrow.motY;
		customArrow.motZ = nmsOldArrow.motZ;
		customArrow.lastPitch = nmsOldArrow.lastPitch;
		customArrow.lastYaw = nmsOldArrow.lastYaw;

		((CraftArrow) e.getProjectile()).setHandle(customArrow);
	}

	@EventHandler
	public void onArmorStandDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof ArmorStand)
			e.setCancelled(true);
	}

	private ArrayList<Player> getNearbyPlayers(Location source) {
		ArrayList<Player> players = new ArrayList<>();
		// "server" is a server object basically just in place of getServer().
		for (Entity p : source.getWorld().getNearbyEntities(source, 15, 15, 15)) {
			if (p instanceof Player) {
				if (((Player) p).getGameMode().equals(GameMode.SURVIVAL) && new Utils().getMetadata(p, "alive").asBoolean()) {
					//Finds distance between source and player
					if (p.getLocation().distance(source) <= (double) 15)
						players.add((Player) p);

				}
			}
		}
		return players;
	}
}

