package com.destructor.destructor2lit.commands;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.events.TriggerTrap;
import com.destructor.destructor2lit.utils.FastBlockUpdate;
import com.destructor.destructor2lit.utils.Utils;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.time.Clock;
import java.util.Arrays;
import java.util.Set;

import static org.bukkit.Material.AIR;

public class bedwarsCommand implements CommandExecutor {

	private final Main main;

	public bedwarsCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		Player player;
		if (commandSender instanceof Player) {
			player = (Player) commandSender;
		} else {
			commandSender.sendMessage("This command only is available to players.");
			return false;
		}

		if (command.getName().equalsIgnoreCase("bedwars")) {
			if (strings.length == 0) {
				player.sendMessage(
						ChatColor.GREEN + "----------------------------------------------------------------\n" +
								ChatColor.WHITE + "                            Bed Wars\n" +
								ChatColor.YELLOW + "         Plugin Bêta par Destructor2ku\n" +
								"   Utilisez /bedwars help pour obtenir de l'aide\n" +
								"                        Eldolfin#0001\n" +
								ChatColor.GREEN + "----------------------------------------------------------------");
				return true;
			} else if (strings.length == 1 && strings[0].equalsIgnoreCase("help")) {
				player.sendMessage(ChatColor.RED + "/bedwars" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "montre la version");
				player.sendMessage(ChatColor.RED + "/bedwars setspawn (red|blue|green|yellow|aqua|white|pink|gray)" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "défini le spawn de la team spécifiée à la position centrée du joueur (le milieu du block sur lequel se trouve le joueur)");
				player.sendMessage(ChatColor.RED + "/bedwars setbed (red|blue|green|yellow|aqua|white|pink|gray)" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "défini le lit de la team au lit regardé par le joueur");
				player.sendMessage(ChatColor.RED + "/bedwars addgen (team|diamond|emerald) [red|blue|green|yellow|aqua|white|pink|gray]" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "ajoute un générateur");
				player.sendMessage(ChatColor.RED + "/bedwars delgen (team|diamond|emerald) (id)" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "supprime le générateur spécifié");
				player.sendMessage(ChatColor.RED + "/bedwars addnpc (shop|upgrade)" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "permet de créer un npc de type shop, upgrade");
				player.sendMessage(ChatColor.RED + "/bedwars buildlimit" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "affiche la limit de build sur ce serveur");
				player.sendMessage(ChatColor.RED + "/bedwars setbuildlimit (0-255)" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "défini la limit de build sur ce serveur");
				player.sendMessage(ChatColor.RED + "/bedwars teststart" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "permet de commencer une game à une personne");
				player.sendMessage(ChatColor.RED + "/bedwars help" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "affiche cette page");
				return true;
			} else if (strings.length == 1 && strings[0].equalsIgnoreCase("buildlimit")) {
				player.sendMessage(ChatColor.WHITE + "La limite de cette map est: " + ChatColor.GOLD + main.getConfig().get("buildlimit"));
			}
			if (player.isOp()) {
				if (strings.length == 2 && strings[0].equalsIgnoreCase("setspawn")) {
					Location spawn = player.getLocation();
					spawn.setX(spawn.getBlockX() + 0.5);
					spawn.setY(spawn.getY() + 0.5);
					spawn.setZ(spawn.getBlockZ() + 0.5);
					if (-45 < spawn.getYaw() || spawn.getYaw() <= -315) {
						spawn.setYaw(0);
					} else if (-315 < spawn.getYaw() && spawn.getYaw() <= -225) {
						spawn.setYaw(90);
					} else if (-225 < spawn.getYaw() && spawn.getYaw() < -135) {
						spawn.setYaw(180);
					} else {
						spawn.setYaw(-90);
					}
					if (strings[1].equalsIgnoreCase("red") || strings[1].equalsIgnoreCase("blue") || strings[1].equalsIgnoreCase("green") || strings[1].equalsIgnoreCase("yellow") || strings[1].equalsIgnoreCase("aqua") || strings[1].equalsIgnoreCase("white") || strings[1].equalsIgnoreCase("pink") || strings[1].equalsIgnoreCase("gray")) {
						main.getConfig().set("Spawns." + strings[1].toLowerCase() + ".x", spawn.getX());
						main.getConfig().set("Spawns." + strings[1].toLowerCase() + ".y", spawn.getY());
						main.getConfig().set("Spawns." + strings[1].toLowerCase() + ".z", spawn.getZ());
						main.getConfig().set("Spawns." + strings[1].toLowerCase() + ".yaw", spawn.getYaw());
						main.saveConfig();
						player.sendMessage(ChatColor.GREEN + "Le spawn de la team " + new Utils().getTeamColor(strings[1].toLowerCase()).toString() + strings[1].toLowerCase() + ChatColor.GREEN + " a été défini à " + ChatColor.WHITE + spawn.getX() + " " + spawn.getY() + " " + spawn.getZ() + ChatColor.GREEN + " avec un yaw à " + ChatColor.WHITE + spawn.getYaw());
					} else {
						player.sendMessage(ChatColor.RED + "Team incorrecte");
					}
				} else if (strings.length == 2 && strings[0].equalsIgnoreCase("setbed")) {
					Block block = player.getTargetBlock((Set<Material>) null, 10);
					if (strings[1].equalsIgnoreCase("red") || strings[1].equalsIgnoreCase("blue") || strings[1].equalsIgnoreCase("green") || strings[1].equalsIgnoreCase("yellow") || strings[1].equalsIgnoreCase("aqua") || strings[1].equalsIgnoreCase("white") || strings[1].equalsIgnoreCase("pink") || strings[1].equalsIgnoreCase("gray")) {
						if (!block.getType().equals(Material.BED_BLOCK)) {
							player.sendMessage(ChatColor.RED + "Vous ne regardez pas un lit!");
						} else {
							Block block1 = block;
							Block block2 = null;
							if (block.getRelative(0, 0, 1).getType().equals(Material.BED_BLOCK)) {
								block2 = block.getRelative(0, 0, 1);
							} else if (block.getRelative(0, 0, -1).getType().equals(Material.BED_BLOCK)) {
								block2 = block.getRelative(0, 0, -1);
							} else if (block.getRelative(1, 0, 0).getType().equals(Material.BED_BLOCK)) {
								block2 = block.getRelative(1, 0, 0);
							} else if (block.getRelative(-1, 0, 0).getType().equals(Material.BED_BLOCK)) {
								block2 = block.getRelative(-1, 0, 0);
							}

							main.getConfig().set("Beds." + strings[1].toLowerCase() + ".block1.x", block1.getX());
							main.getConfig().set("Beds." + strings[1].toLowerCase() + ".block1.y", block1.getY());
							main.getConfig().set("Beds." + strings[1].toLowerCase() + ".block1.z", block1.getZ());
							main.getConfig().set("Beds." + strings[1].toLowerCase() + ".block2.x", block2.getX());
							main.getConfig().set("Beds." + strings[1].toLowerCase() + ".block2.y", block2.getY());
							main.getConfig().set("Beds." + strings[1].toLowerCase() + ".block2.z", block2.getZ());
							main.saveConfig();
							player.sendMessage(ChatColor.GREEN + "Le bed de la team " + new Utils().getTeamColor(strings[1].toLowerCase()).toString() + strings[1].toLowerCase() + ChatColor.GREEN + " a été défini aux blocks en " + block1.getX() + " " + block1.getY() + " " + block1.getZ() + " et en " + block2.getX() + " " + block2.getY() + " " + block2.getZ());
						}
					} else {
						player.sendMessage(ChatColor.RED + "Team incorrecte");
						return false;
					}

				} else if (strings.length == 1 && strings[0].equalsIgnoreCase("forcestart")) {
					main.startTimer.fastForwardStartTimer();
				} else if (strings.length == 3 && strings[0].equalsIgnoreCase("test")) {
					player.sendMessage("Coucou");
				} else if ((strings.length == 2 || strings.length == 3) && strings[0].equalsIgnoreCase("addgen")) {
					if (strings[1].equalsIgnoreCase("team") || strings[1].equalsIgnoreCase("diamond") || strings[1].equalsIgnoreCase("emerald")) {
						String color = "";
						if (strings[1].equalsIgnoreCase("team")) {
							if (strings.length == 2) {
//							Si on crée un générateur team sans préciser de couleur, on essaye de la deviner, pour cela, on crée une
//							variable qui correspond à la distance entre le spawn de la couleur et le générateur et on cherche la
//							plus petite parmis toutes les couleurs
								double dist = Double.MAX_VALUE;
								double temp;
								for (String c : main.colors) {
									temp = Math.pow(main.getConfig().getDouble("Spawns." + c + ".x") - player.getLocation().getX(), 2) + Math.pow(main.getConfig().getDouble("Spawns." + c + ".y") - player.getLocation().getY(), 2) + Math.pow(main.getConfig().getDouble("Spawns." + c + ".z") - player.getLocation().getZ(), 2);
									if (dist > temp) {
										dist = temp;
										color = c;
									}
								}
							} else {
								if (main.colors.contains(strings[2].toLowerCase())) {
									color = strings[2].toLowerCase();
								} else {
									player.sendMessage(ChatColor.RED + "Team incorrecte");
									return false;
								}
							}
						} else if (strings.length == 3) {
							player.sendMessage(ChatColor.RED + "Une team ne peut pas être définie à un générateur de ce type, la couleur est alors ignoré");
						}

//						Au cas ou c'est le premier gen créé, on créé la section
						if (main.getConfig().getConfigurationSection("Gen." + strings[1].toLowerCase()) == null) {
							main.getConfig().createSection("Gen." + strings[1].toLowerCase());
							main.saveConfig();
						}

						int counter = 0;
						while (main.getConfig().isConfigurationSection("Gen." + strings[1].toLowerCase() + ".gen" + counter)) {
							counter++;
						}


//						Puisque le type de générateur demandé est correct, il sera créé
//						1. on crée les sections
						main.getConfig().createSection("Gen." + strings[1].toLowerCase() + ".gen" + counter);
						main.saveConfig();
			/*			main.getConfig().createSection("Gen." + strings[1].toLowerCase() + ".gen" + counter + ".x");
						main.getConfig().createSection("Gen." + strings[1].toLowerCase() + ".gen" + counter + ".y");
						main.getConfig().createSection("Gen." + strings[1].toLowerCase() + ".gen" + counter + ".z");
						main.saveConfig();*/

//						2. on ajoute les valeurs
						main.getConfig().set("Gen." + strings[1].toLowerCase() + ".gen" + counter + ".x", player.getLocation().getBlockX() + 0.5);
						main.getConfig().set("Gen." + strings[1].toLowerCase() + ".gen" + counter + ".y", player.getLocation().getBlockY() + 0.5);
						main.getConfig().set("Gen." + strings[1].toLowerCase() + ".gen" + counter + ".z", player.getLocation().getBlockZ() + 0.5);
						main.saveConfig();

//						3. on envois un message de confirmation
						player.sendMessage(ChatColor.GREEN + "Un générateur de type " + strings[1].toLowerCase() + " a été ajouté en: " + ChatColor.WHITE + (player.getLocation().getBlockX() + 0.5) + " " + (player.getLocation().getBlockY() + 0.5) + " " + (player.getLocation().getBlockZ() + 0.5) + ChatColor.GREEN + " c'est le générateur " + ChatColor.WHITE + counter);


//						Pour les générateurs de teams, on ajoute un paramètre color, et on annonce au joueur la couleur utilisée
						if (strings[1].equalsIgnoreCase("team")) {
							main.getConfig().set("Gen.team.gen" + counter + ".color", color);
							main.saveConfig();
							player.sendMessage(ChatColor.GREEN + "Ce générateur a été " + (strings.length == 2 ? "automatiquement " : "") + "associé à la couleur " + new Utils().getTeamColor(color) + color);
						}

					} else {

//						Si le générateur demandé n'est pas correct, on rappelle comment utiliser la commande
						player.sendMessage(ChatColor.RED + "Generateur incorrect, la commande est: /bedwars addgen team/diamond/emerald");

					}

				} else if (strings.length == 3 && strings[0].equalsIgnoreCase("delgen")) {
					if (main.getConfig().isConfigurationSection("Gen." + strings[1].toLowerCase() + ".gen" + strings[2].toLowerCase())) {
						main.getConfig().set("Gen." + strings[1].toLowerCase() + ".gen" + strings[2].toLowerCase(), null);
						main.saveConfig();
						player.sendMessage(ChatColor.RED + "Le générateur de type " + ChatColor.WHITE + strings[1].toLowerCase() + ChatColor.RED + " et d'identifiant " + ChatColor.WHITE + strings[2].toLowerCase() + ChatColor.RED + " a correctement été supprimé");
					} else {
						player.sendMessage(ChatColor.RED + "/bedwars delgen (team|diamond|emerald) (id)");
					}
				}
				if (strings.length == 2 && strings[0].equalsIgnoreCase("setbuildlimit")) {
					int buildlimit = -1;
					try {
						buildlimit = Integer.parseInt(strings[1]);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "/bw setbuildlimit (0-255)");
					}
					if (0 <= buildlimit && buildlimit <= 255) {
						main.getConfig().set("buildlimit", strings[1]);
						main.saveConfig();
					}
				}
				if (strings[0].equalsIgnoreCase("teststart")) {
					if (Bukkit.getOnlinePlayers().size() != 1) {
						player.sendMessage(ChatColor.RED + "Test Start is only available when 1 player is online!");
						return false;
					}
					main.StartStartTimer(true);
				}

				if (strings[0].equalsIgnoreCase("addnpc")) {
					main.npcManager.createNPC(player, strings, main);
				}
			}

		}
		return false;
	}
}
