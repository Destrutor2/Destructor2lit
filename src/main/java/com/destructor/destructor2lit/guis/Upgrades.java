package com.destructor.destructor2lit.guis;

import com.destructor.destructor2lit.BwTeam;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Queue;
import java.util.Stack;

import static org.bukkit.ChatColor.*;

public class Upgrades {
	static String[] positionName = {"first", "second", "third"};

	public static void openUI(Player player, int page, Main main) {

//		if (!player.getOpenInventory().getTitle().equalsIgnoreCase("container.crafting") && page == 0) {
//			Bukkit.broadcastMessage(ChatColor.RED + "Please report this to Destructor, issue with " + player.getDisplayName() + "\n topinv: " + player.getOpenInventory().getTopInventory().toString() + " title: " + player.getOpenInventory().getTitle() + " bottom inv: " + player.getOpenInventory().getBottomInventory().toString());
//			return;
//		}

		Inventory inventory = null;
		BwTeam team = main.getTeam(Utils.getMetadata(player, "color").asString());
		Utils utils = new Utils();
		Queue<Byte> traps = team.getTraps();
		int diamonds = utils.countItem(player, Material.DIAMOND);
		byte trapSize = (byte) traps.size();

		switch (page) {
			case 0:
				inventory = Bukkit.createInventory(null, 54, "Upgrades & Traps");

				//		On mets les glass pane:
				for (int i = 18; i <= 26; i++) {
					inventory.setItem(i, utils.customItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), GRAY + "⬆ Purchasable", new String[]{GRAY + "⬇ Traps Queue"}));
				}

				Boolean sharp = team.getSharp();
				byte prot = team.getProtection();
				byte haste = team.getHaste();
				byte forge = team.getForge();
				Boolean healpool = team.getHealpool();
				Boolean dragonbuff = team.getDragonbuff();


				inventory.setItem(10, utils.customItem(Material.IRON_SWORD,
						(sharp ? GREEN : (diamonds >= 4 ? YELLOW : RED)) + "Sharpened Swords",
						new String[]{GRAY + "Your team permanently gains", GRAY + "Sharpness I on all swords and",
								GRAY + "axes!", "",
								GRAY + "Cost: " + AQUA + "4 Diamonds", "",
								(sharp ? GREEN + "UNLOCKED" : (diamonds >= 4 ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"))}, sharp));

				inventory.setItem(11, utils.customItem(Material.IRON_CHESTPLATE,
						(prot == 4 ? GREEN : (diamonds >= ((int) Math.pow(2, prot + 1)) ? YELLOW : RED)) + "Reinforced Armor " + Utils.toRoman(prot + 1),
						new String[]{GRAY + "Your team permanently gains",
								GRAY + "Protection on all armor pieces!", "",
								(prot >= 1 ? GREEN : GRAY) + "Tier 1: Protection I, " + AQUA + "2 Diamonds",
								(prot >= 2 ? GREEN : GRAY) + "Tier 2: Protection II, " + AQUA + "4 Diamonds",
								(prot >= 3 ? GREEN : GRAY) + "Tier 3: Protection III, " + AQUA + "8 Diamonds",
								(prot >= 4 ? GREEN : GRAY) + "Tier 4: Protection IV, " + AQUA + "16 Diamonds", "",
								(prot >= 4 ? GREEN + "UNLOCKED" : (diamonds >= ((int) Math.pow(2, prot + 1)) ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"))}, prot >= 1));

				inventory.setItem(12, utils.customItem(Material.GOLD_PICKAXE,
						(haste == 2 ? GREEN : (diamonds >= ((int) Math.pow(2, haste + 1)) ? YELLOW : RED)) + "Maniac Miner " + Utils.toRoman(haste + 1),
						new String[]{GRAY + "All players on your team",
								GRAY + "permanently gain Haste.", "",
								(haste >= 1 ? GREEN : GRAY) + "Tier 1: Haste I, " + AQUA + "2 Diamonds",
								(haste >= 2 ? GREEN : GRAY) + "Tier 2: Haste II, " + AQUA + "4 Diamonds", "",
								(haste >= 2 ? GREEN + "UNLOCKED" : (diamonds >= ((int) Math.pow(2, haste + 1)) ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"))}, haste >= 1));

				inventory.setItem(13, utils.customItem(Material.FURNACE,
						"" + (forge >= 4 ? GREEN : ((diamonds >= (2 * (forge + 1)) ? YELLOW : RED))) + "" + (forge == 0 ? "Iron Forge" : (forge == 1 ? "Gold Forge" : (forge == 2 ? "Emerald Forge" : (forge == 3 ? "Molten Forge" : "VERIFY! Forge")))),
						new String[]{GRAY + "Upgrade resource spawning on",
								GRAY + "your island.", "",
								(forge >= 1 ? GREEN : GRAY) + "Tier 1: +50% Resources, " + AQUA + "2 Diamonds",
								(forge >= 2 ? GREEN : GRAY) + "Tier 2: +100% Resources, " + AQUA + "4 Diamonds",
								(forge >= 3 ? GREEN : GRAY) + "Tier 3: Spawn emeralds, " + AQUA + "6 Diamonds",
								(forge >= 4 ? GREEN : GRAY) + "Tier 4: +200% Resources, " + AQUA + "8 Diamonds", "",
								(forge >= 4 ? GREEN + "UNLOCKED" : (diamonds >= (2 * (forge + 1)) ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"))}, forge >= 1));
				inventory.setItem(14, utils.customItem(Material.BEACON,
						(healpool ? GREEN : (diamonds >= 1 ? YELLOW : RED)) + "Heal Pool",
						new String[]{GRAY + "Creates a Regeneration field",
								GRAY + "around your base", "",
								GRAY + "Cost: " + AQUA + "1 Diamond", "",
								(healpool ? GREEN + "UNLOCKED" : (diamonds >= 1 ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"))}, healpool));
				inventory.setItem(15, utils.customItem(Material.DRAGON_EGG,
						(dragonbuff ? GREEN : (diamonds >= 5 ? YELLOW : RED)) + "Dragon Buff",
						new String[]{GRAY + "Your team will have 2 dragons",
								GRAY + "instead of 1 during deathmatch!", "",
								GRAY + "Cost: " + AQUA + "5 Diamond", "",
								(dragonbuff ? GREEN + "UNLOCKED" : (diamonds >= 5 ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"))}, dragonbuff));
				inventory.setItem(16, utils.customItem(Material.LEATHER,
						YELLOW + "Buy a trap",
						new String[]{GRAY + "Purchased traps will be queued",
								GRAY + "on the right.", "",
								(trapSize >= 3 ? RED + "Trap queue full!" : YELLOW + "Click to browse!")}));

//				La on mets les 3 traps
				for (int i = 0; i <= 2; i++) {
					ItemStack shownItem;
					switch (trapSize > i ? ((byte) traps.toArray()[i]) : 0) {
						case 1:
							shownItem = utils.customItem(new ItemStack(Material.TRIPWIRE_HOOK, i + 1),
									GREEN + "Trap #" + (i + 1) + ": It's a trap!",
									new String[]{GRAY + "Inflincts Blindness and Slowness",
											GRAY + "for 8 seconds.", "",
											GRAY + "The " + positionName[i] + " enemy to walk into",
											GRAY + "your base will trigger this",
											GRAY + "trap!"
									});
							break;
						case 2:
							shownItem = utils.customItem(new ItemStack(Material.FEATHER, i + 1),
									GREEN + "Trap #" + (i + 1) + ": Counter-Offensive Trap",
									new String[]{GRAY + "Grants Speed I and Jump Boost II",
											GRAY + "for 10 seconds to allied players",
											GRAY + "near your base.", "",
											GRAY + "The " + positionName[i] + " enemy to walk into",
											GRAY + "your base will trigger this",
											GRAY + "trap!"
									});
							break;
						case 3:
							shownItem = utils.customItem(new ItemStack(Material.REDSTONE_TORCH_ON, i + 1),
									GREEN + "Trap #" + (i + 1) + ": Alarm Trap",
									new String[]{GRAY + "Reveals invisible players as",
											GRAY + "well as their name and team.", "",
											GRAY + "The " + positionName[i] + " enemy to walk into",
											GRAY + "your base will trigger this",
											GRAY + "trap!"
									});
							break;
						case 4:
							shownItem = utils.customItem(new ItemStack(Material.IRON_PICKAXE, i + 1),
									GREEN + "Trap #" + (i + 1) + ": Miner Fatigue Trap",
									new String[]{GRAY + "Inflincts Mining Fatigue for 10",
											GRAY + "seconds.", "",
											GRAY + "The " + positionName[i] + " enemy to walk into",
											GRAY + "your base will trigger this",
											GRAY + "trap!"
									});
							break;
						default:
							shownItem = utils.customItem(new ItemStack(Material.STAINED_GLASS, i + 1, (byte) 8),
									RED + "Trap #" + (i + 1) + ": No Trap!",
									new String[]{GRAY + "The " + positionName[i] + " enemy to walk into",
											GRAY + "your base will trigger this",
											GRAY + "trap!", "",
											GRAY + "Purchasing a trap will queue it",
											GRAY + "here. Its cost will scale based",
											GRAY + "on the number of traps queued.", "",
											GRAY + "Next trap: " + AQUA + ((int) Math.pow(2, trapSize)) + " Diamond" + (trapSize != 0 ? "s" : "")
									});
							break;
					}
					inventory.setItem(30 + i, shownItem);
				}

				break;
			case 1:
				inventory = Bukkit.createInventory(null, 27, "Queue a trap");

				inventory.setItem(22, utils.customItem(Material.ARROW,
						GREEN + "Go Back",
						new String[]{GRAY + "To Upgrades & Traps"}));

				inventory.setItem(10, utils.customItem(Material.TRIPWIRE_HOOK,
						(diamonds >= ((int) Math.pow(2, trapSize)) ? YELLOW : RED) + "It's a trap!",
						new String[]{GRAY + "Inflincts Blindness and Slowness",
								GRAY + "for 8 seconds.", "",
								GRAY + "Cost: " + AQUA + ((int) Math.pow(2, trapSize)) + " Diamond" + (trapSize != 0 ? "s" : ""), "",
								(diamonds >= ((int) Math.pow(2, trapSize))) ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"}));
				inventory.setItem(11, utils.customItem(Material.FEATHER,
						(diamonds >= ((int) Math.pow(2, trapSize)) ? YELLOW : RED) + "Counter-Offensive Trap",
						new String[]{GRAY + "Grants Speed I and Jump Boost II",
								GRAY + "for 10 seconds to allied players",
								GRAY + "near your base.", "",
								GRAY + "Cost: " + AQUA + ((int) Math.pow(2, trapSize)) + " Diamond" + (trapSize != 0 ? "s" : ""), "",
								(diamonds >= ((int) Math.pow(2, trapSize))) ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"}));
				inventory.setItem(12, utils.customItem(Material.REDSTONE_TORCH_ON,
						(diamonds >= ((int) Math.pow(2, trapSize)) ? YELLOW : RED) + "Alarm Trap",
						new String[]{GRAY + "Reveals invisible players as",
								GRAY + "well as their name and team.", "",
								GRAY + "Cost: " + AQUA + ((int) Math.pow(2, trapSize)) + " Diamond" + (trapSize != 0 ? "s" : ""), "",
								(diamonds >= ((int) Math.pow(2, trapSize))) ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"}));
				inventory.setItem(13, utils.customItem(Material.IRON_PICKAXE,
						(diamonds >= ((int) Math.pow(2, trapSize)) ? YELLOW : RED) + "Miner Fatigue Trap",
						new String[]{GRAY + "Inflincts Mining Fatigue for 10",
								GRAY + "seconds.", "",
								GRAY + "Cost: " + AQUA + ((int) Math.pow(2, trapSize)) + " Diamond" + (trapSize != 0 ? "s" : ""), "",
								(diamonds >= ((int) Math.pow(2, trapSize))) ? YELLOW + "Click to purchase!" : RED + "You don't have enough Diamonds!"}));


		}

		player.openInventory(inventory);
		player.updateInventory();

	}


	public static void UpgradeClick(InventoryClickEvent e, Main main) {
		if (e.getClick().isKeyboardClick() || e.getClickedInventory().getType().equals(InventoryType.PLAYER) || e.getCurrentItem().getType().equals(Material.AIR)) {
			e.setCancelled(true);
			return;
		}

		Inventory inv = e.getInventory();
		ItemStack it = e.getCurrentItem();
		Player player = (Player) e.getWhoClicked();
		String itName = ChatColor.stripColor(it.getItemMeta().getDisplayName());
		BwTeam team = main.getTeam(Utils.getMetadata(player, "color").asString());
		Utils utils = new Utils();
		Queue<Byte> traps = team.getTraps();
		int diamonds = utils.countItem(player, Material.DIAMOND);
		byte trapSize = (byte) traps.size();


		if (itName.equalsIgnoreCase("Buy a trap")) {
			if (trapSize < 3) {
				openUI(player, 1, main);
				return;
			}
		}
		if (itName.equalsIgnoreCase("Go Back")) {
			openUI(player, 0, main);
			return;
		}

		if (inv.getName().equalsIgnoreCase("Upgrades & Traps")) {
//			La on s'occupe des upgrades
			if (itName.equalsIgnoreCase("Sharpened Swords")) {
				if (canBuy(diamonds, player, 4, team, "Sharpened Swords", team.getSharp())) {
					team.setSharp(true);
					team.updateInventories();
				}
			}
			if (itName.startsWith("Reinforced Armor")) {
				if (canBuy(diamonds, player, ((int) Math.pow(2, (team.getProtection() + 1))), team, "Reinforced Armor " + Utils.toRoman(team.getProtection() + 1), team.getProtection() >= 4)) {
					team.setProtection((byte) (team.getProtection() + 1));
					team.updateInventories();
				}
			}
			if (itName.startsWith("Maniac Miner")) {
				if (canBuy(diamonds, player, ((int) Math.pow(2, team.getHaste()) + 1), team, "Maniac Miner " + Utils.toRoman(team.getHaste() + 1), team.getHaste() >= 2)) {
					team.setHaste((byte) (team.getHaste() + 1));
					team.updateEffects();
				}
			}
			if (itName.endsWith("Forge")) {
				if (canBuy(diamonds, player, (2 * (team.getForge() + 1)), team, (team.getForge() == 0 ? "Iron Forge" : "") + (team.getForge() == 1 ? "Gold Forge" : "") + (team.getForge() == 2 ? "Emerald Forge" : "") + (team.getForge() == 3 ? "Molten Forge" : ""), team.getForge() >= 4)) {
					team.setForge((byte) (team.getForge() + 1));
				}
			}
			if (itName.equalsIgnoreCase("Heal Pool")) {
				if (canBuy(diamonds, player, 1, team, "Heal Pool", team.getHealpool())) {
					team.setHealpool(true);
					team.updateEffects();
				}
			}
			if (itName.equalsIgnoreCase("Dragon Buff")) {
				if (canBuy(diamonds, player, 5, team, "Dragon Buff", team.getDragonbuff())) {
					team.setDragonbuff(true);
				}
			}
			openUI(player,0,main);
		} else if (inv.getName().equalsIgnoreCase("Queue a trap")) {
//			La on s'occupe des traps
			if (e.getSlot() >= 10 && e.getSlot() <= 13) {
//				La on est sur qu'on a bien clicke sur un trap
				if (canBuy(diamonds, player, ((int) Math.pow(2, trapSize)), team, itName, false)) {
					team.addTrap((byte) (e.getSlot() - 9));
					if (trapSize == 2)
						openUI(player, 0, main);
				}

			}
			openUI(player,1,main);
		}

		player.updateInventory();
	}

	private static Boolean canBuy(int diamonds, Player player, int price, BwTeam team, String purchasedItem, boolean hasAlready) {
		if (hasAlready) {
			player.sendMessage(ChatColor.RED + "You've already purchased this upgrade!" + WHITE + " (verify)");
			player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
			return false;
		}
		if (diamonds >= price) {
			player.getInventory().removeItem(new ItemStack(Material.DIAMOND, price));
			player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.GOLD + purchasedItem);
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "You don't have enough diamonds! Need " + (price - diamonds) + " more!");
			player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
			return false;
		}

	}
}
