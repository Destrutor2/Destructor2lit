package com.destructor.destructor2lit.commands;

import com.destructor.destructor2lit.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class toggleparticulesCommand implements CommandExecutor {
	Main main;
	public toggleparticulesCommand(Main main) {
		this.main=main;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if(!(commandSender instanceof Player)){
			commandSender.sendMessage("This command only is available to players.");
			return false;
		}
		if(command.getName().equalsIgnoreCase("toggleparticules") && commandSender instanceof Player){
			if(main.noAnimationPlayers.contains(commandSender)){
				main.noAnimationPlayers.remove(commandSender);
				commandSender.sendMessage("Bedwars Particules: "+ChatColor.GREEN+""+ChatColor.BOLD+"ON");
			} else {
				main.noAnimationPlayers.add((Player) commandSender);
				commandSender.sendMessage("Bedwars Particules: "+ChatColor.RED+""+ChatColor.BOLD+"OFF");

			}
			return true;
		}
		return false;
	}
}
