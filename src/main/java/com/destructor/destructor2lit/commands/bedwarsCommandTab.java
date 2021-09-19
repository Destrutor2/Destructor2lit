package com.destructor.destructor2lit.commands;

import com.destructor.destructor2lit.Main;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.*;

public class bedwarsCommandTab implements TabCompleter {
	Main main;

	public bedwarsCommandTab(Main main) {
		this.main = main;
		subCommands2.put("setspawn", main.colors);
		subCommands2.put("setbed", main.colors);
		subCommands2.put("addgen", Arrays.asList("team", "diamond", "emerald"));
		subCommands2.put("delgen", Arrays.asList("team", "diamond", "emerald"));
		subCommands2.put("addnpc", Arrays.asList("shop", "upgrade"));
		subCommands3.put("addgenteam", main.colors);
	}

	private List<String> subCommands = Arrays.asList("setspawn", "setbed", "addgen", "delgen", "buildlimit", "setbuildlimit", "help", "forcestart", "teststart", "addnpc");
	private Map<String, List<String>> subCommands2 = new HashMap<>();
	private Map<String, List<String>> subCommands3 = new HashMap<>();

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
		List<String> result = new ArrayList<>();
//		Bukkit.broadcastMessage("args: \"" + StringUtils.join(args, "\n") + "\"");
//		Bukkit.broadcastMessage("args len: " + args.length);
		if (command.getName().equalsIgnoreCase("bedwars")) {
			if (((!subCommands.contains(args[0].toLowerCase())) && args.length == 2))
//				cas d'erreur dans la command
				return null;
			if (((subCommands.contains(args[0].toLowerCase())) && args.length == 1))
//				cas ou on rentre la commande entiere a la main puis on fait tab
				return null;
			if ((!subCommands.contains(args[0].toLowerCase())) && args.length == 1) {
				for (String sub : subCommands) {
					if (sub.startsWith(args[0].toLowerCase()))
						result.add(sub);
				}
			} else if ((!subCommands2.containsKey(args[1].toLowerCase())) && args.length == 2) {
				if (subCommands2.get(args[0].toLowerCase()) != null) {
					for (String sub : subCommands2.get(args[0].toLowerCase())) {
						if (sub.startsWith(args[1]))
							result.add(sub);
					}
				}
			} else if ((!subCommands3.containsKey(args[2].toLowerCase())) && args.length == 3) {
				if (subCommands3.get((args[0] + args[1]).toLowerCase()) != null) {
					for (String sub : subCommands3.get((args[0] + args[1]).toLowerCase())) {
						if (sub.startsWith(args[2]))
							result.add(sub);
					}
				}
				if (args[0].equalsIgnoreCase("delgen") && (args[1].equalsIgnoreCase("diamond") || args[1].equalsIgnoreCase("emerald") || args[1].equalsIgnoreCase("team"))) {
					if (!(main.getConfig().getConfigurationSection("Gen." + args[1].toLowerCase()) == null)) {
						for (String key : main.getConfig().getConfigurationSection("Gen." + args[1].toLowerCase()).getKeys(false)) {
							result.add(key.substring(3));
						}
					}
				}
			}
			return result;
/*			if (args[0].equals("")) {
				if (!(commandSender.isOp())) {
					result.addAll(Arrays.asList("buildlimit", "help"));
				} else {
					result.addAll(Arrays.asList("setspawn", "setbed", "addgen", "delgen", "buildlimit", "setbuildlimit", "help"));
				}
			} else if (commandSender.isOp()) {

				if (args.length == 2) {
					if ("setspawn".startsWith(args[0].toLowerCase()) || "setbed".startsWith(args[0].toLowerCase())) {
						result.addAll(Arrays.asList("red", "blue", "green", "yellow", "aqua", "white", "pink", "gray"));
					} else if (args[0].equalsIgnoreCase("addgen") || args[0].equalsIgnoreCase("delgen")) {
						return Arrays.asList("team", "diamond", "emerald");
					}
				} else if (args.length == 2 || args[2].equalsIgnoreCase("")) {
					if (args[1].equalsIgnoreCase("team") && args[0].equalsIgnoreCase("addgen")) {
						return Arrays.asList("red", "blue", "green", "yellow", "aqua", "white", "pink", "gray");
					} else if ((args[1].equalsIgnoreCase("team") || args[1].equalsIgnoreCase("diamond") || args[1].equalsIgnoreCase("emerald")) && args[0].equalsIgnoreCase("delgen")) {
						if (!(main.getConfig().getConfigurationSection("Gen." + args[1].toLowerCase()) == null)) {
							List<String> keys = new ArrayList<>();
							for (String key : main.getConfig().getConfigurationSection("Gen." + args[1].toLowerCase()).getKeys(false)) {
								keys.add(key.substring(3));
							}
							return keys;
						}
					}
				}
			}
			return null;*/
		}
		return null;
	}
}
