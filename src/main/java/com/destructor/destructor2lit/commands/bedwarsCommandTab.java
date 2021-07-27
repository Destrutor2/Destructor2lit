package com.destructor.destructor2lit.commands;

import com.destructor.destructor2lit.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class bedwarsCommandTab implements TabCompleter {
	Main main;

	public bedwarsCommandTab(Main main) {
		this.main = main;
	}

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
		List<String> result = new ArrayList<>();
		if (command.getName().equalsIgnoreCase("bedwars")) {
			if (args.length == 0 || args[0].equals("")) {
				if (!(commandSender.isOp())) {
					result.addAll(Arrays.asList("buildlimit", "help"));
				} else {
					result.addAll(Arrays.asList("setspawn", "setbed", "addgen", "delgen", "buildlimit", "setbuildlimit", "help"));
				}
			} else if (commandSender.isOp()) {
				if (args.length == 1) {
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
			return null;
		}
		return null;
	}
}
