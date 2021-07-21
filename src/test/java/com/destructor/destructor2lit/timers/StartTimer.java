package com.destructor.destructor2lit.timers;

import com.destructor.destructor2lit.enums.GamePhase;
import com.destructor.destructor2lit.enums.GameState;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.TeamGen;
import com.destructor.destructor2lit.utils.FastBlockUpdate;
import com.destructor.destructor2lit.utils.Title;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Material.AIR;


public class StartTimer extends BukkitRunnable {
	public int timer = 20;
	int[] sound = {20, 10, 5, 4, 3, 2, 1};
	Main main;

	public StartTimer(Main main, int timer) {
		this.main = main;
		this.timer = timer;
	}

	public void fastForwardStartTimer() {
		timer = 1;
	}

	@Override
	public void run() {
		if (Bukkit.getOnlinePlayers().size() >= 2) {
			for (int s : sound) {
				if (timer == s) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1, 1);
						switch (timer) {
							case 20:
								player.sendMessage(ChatColor.YELLOW + "The game starts in 20 seconds!");
								break;
							case 10:
								player.sendMessage(ChatColor.YELLOW + "The game starts in " + ChatColor.GOLD + "10" + ChatColor.YELLOW + " seconds!");
								break;
							default:
								player.sendMessage(ChatColor.YELLOW + "The game starts in " + ChatColor.RED + timer + ChatColor.YELLOW + " seconds!");
								break;
						}
						Title title = new Title(Integer.toString(timer));
						title.clearTitle(player);
						if ((timer <= 5 && timer != 0) || timer == 10) {
							title.setTimingsToTicks();
							title.setFadeInTime(0);
							title.setStayTime(30);
							title.setFadeOutTime(0);
							if (timer == 4 || timer == 5) {
								title.setTitleColor(ChatColor.YELLOW);
							} else if (timer <= 3) {
								title.setTitleColor(ChatColor.RED);
							} else {
								title.setTitleColor(ChatColor.GREEN);
							}
							if (timer == 1) {
								title.setFadeOutTime(20);
							}
							title.send(player);
						}
					}
				}
			}
//			On mets la vie des joueurs presque au max pck on va la chnger apres pour update les health scoreboards
			if (timer == 1) {
				for (Player p : main.getPlayers()) {
					p.setHealth(p.getMaxHealth() - 1);
				}
			}
			if (timer == 0) {

				main.setGameState(GameState.PLAYING);

				for (World w : Bukkit.getWorlds()) {
					w.setPVP(true);
				}

				ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
				ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
				ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
				ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
				LeatherArmorMeta coloredArmorMeta = (LeatherArmorMeta) chestplate.getItemMeta();
				coloredArmorMeta.spigot().setUnbreakable(true);
				coloredArmorMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				ItemStack woodSword = new ItemStack(Material.WOOD_SWORD,1);
				ItemMeta woodSwordMeta= woodSword.getItemMeta();
				woodSwordMeta.spigot().setUnbreakable(true);
				woodSwordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				woodSword.setItemMeta(woodSwordMeta);
				for (Player player : Bukkit.getOnlinePlayers()) {
					player.sendMessage(
							ChatColor.GREEN + "" +ChatColor.BOLD+ "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n" +
									ChatColor.WHITE + "" + ChatColor.BOLD + "                            Bed Wars\n" +
									ChatColor.YELLOW + "" + ChatColor.BOLD + "         Protect your bed and destroy the enemy beds.\n" +
									"          Upgrade yourself and your team by collecting\n" +
									"        Iron, Gold, Emerald and Diamond from generators\n" +
									"                  to access powerful upgrades.\n" +
									ChatColor.GREEN +""+ChatColor.BOLD+ "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");

				}

//                On associe une couleure à chaque joueur
				Random random = new Random();
				Utils utils = new Utils();
				List<Player> uncolorredPlayers = new ArrayList<>(main.getPlayers());
				List<String> unusedcolors = new ArrayList<>();
				String[] colors = {"red", "blue", "green", "yellow", "aqua", "white", "pink", "gray"};
				for (String s : colors) {
					if (uncolorredPlayers.size() > 0) {
						int r = random.nextInt(uncolorredPlayers.size());
						utils.setMetadata(uncolorredPlayers.get(r), "color", s);
						utils.setMetadata(uncolorredPlayers.get(r), "spawnX", main.getConfig().getDouble("Spawns." + s + ".x"));
						utils.setMetadata(uncolorredPlayers.get(r), "spawnY", main.getConfig().getDouble("Spawns." + s + ".y"));
						utils.setMetadata(uncolorredPlayers.get(r), "spawnZ", main.getConfig().getDouble("Spawns." + s + ".z"));
						utils.setMetadata(uncolorredPlayers.get(r), "spawnYaw", main.getConfig().getDouble("Spawns." + s + ".yaw"));
						utils.setMetadata(uncolorredPlayers.get(r), "lastdamager", "null");
						uncolorredPlayers.remove(r);
					} else {
						main.removeBed(s);
						unusedcolors.add(s);
					}

				}


//                On crée les armures de bonnes couleurs pour chaque joueur

				for (Player player : main.getPlayers()) {
					switch (utils.getMetadata(player, "color").asString()) {
						case "red":
							coloredArmorMeta.setColor(Color.RED);
							break;
						case "blue":
							coloredArmorMeta.setColor(Color.BLUE);
							break;
						case "green":
							coloredArmorMeta.setColor(Color.GREEN);
							break;
						case "yellow":
							coloredArmorMeta.setColor(Color.YELLOW);
							break;
						case "aqua":
							coloredArmorMeta.setColor(Color.AQUA);
							break;
						case "white":
							coloredArmorMeta.setColor(Color.WHITE);
							break;
						case "pink":
							coloredArmorMeta.setColor(Color.PURPLE);
							break;
						case "gray":
							coloredArmorMeta.setColor(Color.GRAY);
							break;

					}
					boots.setItemMeta(coloredArmorMeta);
					leggings.setItemMeta(coloredArmorMeta);
					chestplate.setItemMeta(coloredArmorMeta);
					helmet.setItemMeta(coloredArmorMeta);
					player.getInventory().setHelmet(helmet);
					player.getInventory().setChestplate(chestplate);
					player.getInventory().setLeggings(leggings);
					player.getInventory().setBoots(boots);
					player.teleport(new Location(player.getWorld(), utils.getMetadata(player, "spawnX").asDouble(), utils.getMetadata(player, "spawnY").asDouble(), utils.getMetadata(player, "spawnZ").asDouble(), utils.getMetadata(player, "spawnYaw").asFloat(), 0f));
					player.setFlying(false);
					player.setAllowFlight(false);
					player.setGameMode(GameMode.SURVIVAL);
					player.getInventory().addItem(woodSword);
				}


//				on enlève les lits des couleurs sans joueur
				for (String color : unusedcolors) {
					Bukkit.getWorlds().get(0).getBlockAt(main.getConfig().getInt("Beds." + color + ".block1.x"), main.getConfig().getInt("Beds." + color + ".block1.y"), main.getConfig().getInt("Beds." + color + ".block1.z")).setType(Material.AIR);
					Bukkit.getWorlds().get(0).getBlockAt(main.getConfig().getInt("Beds." + color + ".block2.x"), main.getConfig().getInt("Beds." + color + ".block2.y"), main.getConfig().getInt("Beds." + color + ".block2.z")).setType(Material.AIR);
				}

				if (main.getConfig().getConfigurationSection("Gen.team") != null) {
					for (String gen : main.getConfig().getConfigurationSection("Gen.team").getKeys(false)) {
						main.getGens().add(new TeamGen(main.getConfig().getString("Gen.team." + gen + ".color"), new Location(Bukkit.getWorlds().get(0), main.getConfig().getDouble("Gen.team." + gen + ".x"), main.getConfig().getDouble("Gen.team." + gen + ".y") + 0.5, main.getConfig().getDouble("Gen.team." + gen + ".z")), main));
					}
				} else {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (player.isOp()) {
							player.sendMessage(ChatColor.RED + "Aucun générateur de team n'a été ajouté!" + ChatColor.GREEN + " [/bw addgen team] " + ChatColor.RED + "pour en ajouter un");
						}
					}
				}

				main.setGamePhase(GamePhase.Start);
				main.globalTimer = 0;
				new BukkitRunnable() {
					@Override
					public void run() {
						if (main.getGamePhase().getTime() * 60 > main.globalTimer) {
							main.globalTimer++;
						} else {
							if (main.getGamePhase().getNext() != null) {
								main.setGamePhase(main.getGamePhase().getNext());
								main.globalTimer = 0;
							} else {
//								CAS D'EGALITE, C'EST ICI QUE CA SE PASSE QUAND LA GAME SE FINIE AVEC PLUS d'1 JOUEUR
								Bukkit.broadcastMessage(ChatColor.RED+"GAME END");
								this.cancel();
							}
						}
					}
				}.runTaskTimer(main, 0, 20);
				for (Player player : main.getPlayers()) {
//					peut etre changer le refresh rate du scoreboard si il fait trop de lag
					new GamePhaseTimer(main, player).runTaskTimer(main, 0, 1);
				}
				for (Player player : main.getPlayers()) {
					player.setHealth(player.getMaxHealth());
				}

				FastBlockUpdate fastBlockUpdate = new FastBlockUpdate(main,1);
				for (int x = -12; x <= 12; x++) {
					for (int y = -5; y <= 4; y++) {
						for (int z = -12; z <= 12; z++) {
							if (main.spawn.getBlock().getRelative(x, y, z).getType() != AIR)
								fastBlockUpdate.addBlock(main.spawn.getBlock().getRelative(x, y, z).getLocation(), AIR, (byte) 0);
						}
					}
				}
				fastBlockUpdate.run();
				this.cancel();
			}
			timer--;

		} else {
			timer = 20;
			this.cancel();
		}
	}

}
