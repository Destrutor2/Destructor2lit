package com.destructor.destructor2lit.guis;

import com.destructor.destructor2lit.BwTeam;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.enums.BwItem;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class Shop {

	public static void openShopUi(Player player, int page, Main main) {
//		if (!(player.getOpenInventory().getTitle().equalsIgnoreCase("container.crafting")) && page == 0) {
//			Bukkit.broadcastMessage(ChatColor.RED + "Please report this to Destructor, issue with " + player.getDisplayName() + "\n topinv: " + player.getOpenInventory().getTitle() + " bottom inv: " + player.getOpenInventory().getBottomInventory().getTitle());
//			return;
//		}
//        On cree 8 pages pour le shop
		Inventory[] pages = {
				Bukkit.createInventory(null, 54, "Quick Buy"),
				Bukkit.createInventory(null, 54, "Blocks"),
				Bukkit.createInventory(null, 54, "Melee"),
				Bukkit.createInventory(null, 54, "Armor"),
				Bukkit.createInventory(null, 54, "Tools"),
				Bukkit.createInventory(null, 54, "Ranged"),
				Bukkit.createInventory(null, 54, "Potions"),
				Bukkit.createInventory(null, 54, "Utility")};

//    On défini à chaque page une icon
		Material[] pagesIcons = {Material.NETHER_STAR, Material.HARD_CLAY, Material.GOLD_SWORD, Material.CHAINMAIL_BOOTS, Material.STONE_PICKAXE, Material.BOW, Material.BREWING_STAND_ITEM, Material.TNT};

		//   On initialise un utils pck c'est tres util
		Utils utils = new Utils();

		BwTeam team = main.getTeam(Utils.getMetadata(player, "color").asString());

//    On remplit les pages avec les éléments qu'elles ont en commun

		for (int j = 0; j < pages.length; j++) {
			pages[page].setItem(j, utils.customItem(pagesIcons[j], ChatColor.GREEN + pages[j].getName(), new String[]{ChatColor.YELLOW + "Click to view!"}));
			if (page == j) {
				pages[page].setItem(j + 9, utils.customItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13), ChatColor.GRAY + "⬆ Categories", new String[]{ChatColor.GRAY + "⬇ Items"}));

			} else {
				pages[page].setItem(j + 9, utils.customItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), ChatColor.GRAY + "⬆ Categories", new String[]{ChatColor.GRAY + "⬇ Items"}));
			}
		}
//        On oublie pas le dernier glass pane gris on sait pas ce qu'il fait la mais il existe bel et bien
		pages[page].setItem(17, utils.customItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), ChatColor.GRAY + "⬆ Categories", new String[]{ChatColor.GRAY + "⬇ Items"}));

		BwItemType pagetype = BwItemType.Default;
		switch (page) {
			case 0: //Il faut mettre ici l'implementation de quick buy
				break;
			case 1:
				pagetype = BwItemType.Blocks;
				break;
			case 2:
				pagetype = BwItemType.Melee;
				break;
			case 3:
				pagetype = BwItemType.Armor;
				break;
			case 4:
				pagetype = BwItemType.Tools;
				break;
			case 5:
				pagetype = BwItemType.Ranged;
				break;
			case 6:
				pagetype = BwItemType.Potions;
				break;
			case 7:
				pagetype = BwItemType.Utility;
				break;
			default:
				break;
		}
		int[] itemSlots = {19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

		for (BwItem item : BwItem.values()) {

//                On set ici les icons speciaux, pas définis dans BwItem
			Material itemMaterial = item.getItemMaterial();
			Material costMaterial = item.getItemCostMaterial();
			int cost = item.getItemCost();
			ArrayList<String> itemLore = new ArrayList<>();
			String itemName = item.getItemName();

			ItemStack itemStack = new ItemStack(itemMaterial, item.getItemCount());


//                Cas particulier pour les potions et le golem qui n'avaient pas d'item avant ca
			switch (item) {
				case JumpPot:
					itemStack = new ItemStack(Material.POTION, 1, (byte) 8235);
					break;
				case SpeedPot:
					itemStack = new ItemStack(Material.POTION, 1, (byte) 8226);
					break;
				case InvisPot:
					itemStack = new ItemStack(Material.POTION, 1, (byte) 8238);
					break;
				case IronGolem:
					itemStack = new ItemStack(Material.MONSTER_EGG, 1, (byte) 56);
					break;
				default:
					break;
			}

			ItemMeta itemMeta = itemStack.getItemMeta();

//                    Juste pour ajouter les enchant glint au kbstick, power bow et punch bow
			switch (item) {
				case KbStick:
				case PowerBow:
				case PunchBow:
				case Axe:
				case Pickaxe:
					itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					break;
				default:
					break;
			}

			itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			itemMeta.setLore(Arrays.asList(""));
			itemStack.setItemMeta(itemMeta);


			itemLore = new ArrayList<>(item.getItemLore());
//                On mets le lore en gris sauf pour les potions pck c'est bleu pour les pots :c
			for (int k = 0; k < itemLore.size(); k++) {
				if (!(item.getBwItemType() == BwItemType.Potions)) {
					itemLore.set(k, ChatColor.GRAY + itemLore.get(k));
				} else {
					itemLore.set(k, ChatColor.BLUE + itemLore.get(k));
				}
			}


//                Le Lore va dependre du type d'item, il est le meme pour tous sauf pour les melee items qui n'ont pas de Lore speciaux et les outils qui ont un Lore qui depends de leur tier
			if (!(item.getBwItemType() == BwItemType.Melee || item.getBwItemType() == BwItemType.Tools || item.getBwItemType() == BwItemType.Ranged)) {

//                On crée ici le Lore complet de l'item et on en profite pour changer la couleur du nom en fonction de si le joueur a assez pour acheter ou non


				itemLore.add(0, "");
				itemLore.add(0, ChatColor.GRAY + "Cost: " + utils.costColor(item.getItemCostMaterial()) + item.getItemCost() + " " + utils.bwName(item.getItemCostMaterial()));
				itemLore.add("");

			} else if (item.getBwItemType() == BwItemType.Melee || item.getBwItemType() == BwItemType.Ranged) {
				itemLore = new ArrayList<>();
				itemLore.add(0, "");
				if (team.getSharp() && item.getBwItemType() == BwItemType.Melee && item.getItemMaterial() != Material.STICK) {
					itemLore.add(0, ChatColor.GRAY + "Upgraded: " + ChatColor.YELLOW + "Sharpness I");
					itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
				}
				itemLore.add(0, ChatColor.GRAY + "Cost: " + utils.costColor(item.getItemCostMaterial()) + item.getItemCost() + " " + utils.bwName(item.getItemCostMaterial()));

			}

//                On est sur ici que c'est un outil puisque c'est tout ce qu'il reste, si une categorie venait s'ajouter, un if devrait etre ajouté
			else if (!(item == BwItem.Sheers)) {
				itemLore.add("");
				itemLore.add(0, "");
				Material[] toolcostMaterials = {Material.IRON_INGOT, Material.IRON_INGOT, Material.GOLD_INGOT, Material.GOLD_INGOT, Material.GOLD_INGOT};
				int[] toolscosts = {10, 10, 3, 6, 0};
				String[] tiers = {"I", "II", "III", "IV", "V"};
				Material[] toolMaterials = {Material.BARRIER};
				int toollvl = 0;
				String[] toolNames = new String[0];
				if (item.equals(BwItem.Pickaxe)) {
					toollvl = utils.getMetadata(player, "picklevel").asInt();
					toolMaterials = new Material[]{Material.WOOD_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_PICKAXE};
					toolNames = new String[]{"Wooden Pickaxe (Efficiency I)", "Iron Pickaxe (Efficiency II)", "Gold Pickaxe (Efficiency III, Sharpness II)", "Diamond Pickaxe (Efficiency III)", ChatColor.RED + "Diamond Pickaxe (Efficiency III)"};

//                    C'etait vraiment trop long j'ai fini par faire avec des Arrays c'est plus court
                    /*
                    switch (utils.getMetadata(player, "picklevel").asByte()) {
                        case 0:
                            itemStack = new ItemStack(Material.WOOD_PICKAXE, 1);
                            itemName = "Wooden Pickaxe (Efficiency I)";
                            costMaterial = Material.IRON_INGOT;
                            cost = 10;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "I");
                            break;
                        case 1:
                            itemStack = new ItemStack(Material.IRON_PICKAXE, 1);
                            itemName = "Iron Pickaxe (Efficiency II)";
                            costMaterial = Material.IRON_INGOT;
                            cost = 10;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "II");
                            break;
                        case 2:
                            itemStack = new ItemStack(Material.GOLD_PICKAXE, 1);
                            itemName = "Gold Pickaxe (Efficiency III, Sharpness II)";
                            costMaterial = Material.GOLD_INGOT;
                            cost = 3;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "III");
                            break;
                        case 3:
                            itemStack = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                            itemName = "Diamond Pickaxe (Efficiency III)";
                            costMaterial = Material.GOLD_INGOT;
                            cost = 6;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "IV");
                            break;
                        case 4:
                            itemStack = new ItemStack(Material.DIAMOND_PICKAXE, 1);
                            itemName = ChatColor.RED + "Diamond Pickaxe (Efficiency III)";
                            costMaterial = Material.GOLD_INGOT;
                            cost = 0;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "V");
                            break;
                        default:
                            break;
                    }
*/

				}
				if (item.equals(BwItem.Axe)) {
					toollvl = utils.getMetadata(player, "axelevel").asInt();
					toolMaterials = new Material[]{Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.DIAMOND_AXE, Material.DIAMOND_AXE};
					toolNames = new String[]{"Wooden Axe (Efficiency I)", "Stone Axe (Efficiency I)", "Iron Axe (Efficiency II)", "Diamond Axe (Efficiency III)", ChatColor.RED + "Diamond Axe (Efficiency III)"};
/*
                    switch (utils.getMetadata(player, "axelevel").asByte()) {
                        case 0:
                            itemStack = new ItemStack(Material.WOOD_AXE, 1);
                            itemName = "Wooden Axe (Efficiency I)";
                            costMaterial = Material.IRON_INGOT;
                            cost = 10;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "I");
                            break;
                        case 1:
                            itemStack = new ItemStack(Material.STONE_AXE, 1);
                            itemName = "Stone Axe (Efficiency I)";
                            costMaterial = Material.IRON_INGOT;
                            cost = 10;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "II");
                            break;
                        case 2:
                            itemStack = new ItemStack(Material.IRON_AXE, 1);
                            itemName = "Iron Axe (Efficiency II)";
                            costMaterial = Material.GOLD_INGOT;
                            cost = 3;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "III");
                            break;
                        case 3:
                            itemStack = new ItemStack(Material.DIAMOND_AXE, 1);
                            itemName = "Diamond Axe (Efficiency III)";
                            costMaterial = Material.GOLD_INGOT;
                            cost = 6;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "IV");
                            break;
                        case 4:
                            itemStack = new ItemStack(Material.DIAMOND_AXE, 1);
                            itemName = ChatColor.RED + "Diamond Axe (Efficiency III)";
                            costMaterial = Material.GOLD_INGOT;
                            cost = 0;
                            itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + "V");
                            break;
                        default:
                            break;
                    }
*/
				}
				itemStack = new ItemStack(toolMaterials[toollvl], 1);
				if (team.getSharp()) {
					itemLore.add(0, ChatColor.GRAY + "Upgraded: " + ChatColor.YELLOW + "Sharpness I");
					itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				}
				itemName = toolNames[toollvl];
				costMaterial = toolcostMaterials[toollvl];
				cost = toolscosts[toollvl];
				itemLore.add(0, ChatColor.GRAY + "Tier: " + ChatColor.YELLOW + tiers[toollvl]);

				if (!((item.equals(BwItem.Pickaxe) && utils.getMetadata(player, "picklevel").asInt() == 4) || (item.equals(BwItem.Axe) && utils.getMetadata(player, "axelevel").asInt() == 4))) {
					itemLore.add(0, ChatColor.GRAY + "Cost: " + utils.costColor(costMaterial) + cost + " " + utils.bwName(costMaterial));
				} else {
					itemLore.add(ChatColor.GREEN + "MAXED!");
				}
			} else if (item == BwItem.Sheers) {
				itemLore.add("");
				itemLore.add(0, "");
				itemLore.add(0, ChatColor.GRAY + "Cost: " + utils.costColor(costMaterial) + cost + " " + utils.bwName(costMaterial));
			}


//                ####### NE PAS OUBLIER DE CHANGER LE true AVEC UN CHECK DE SI L'ITEM EST DEJA DANS LE QUICK BUY DU JOUEUR #############################
			if (true) {
				itemLore.add(ChatColor.AQUA + "Sneak Click to add to Quick Buy");
			}

//                C'est ici que le changement de couleur se fait precisement
			if (utils.countItem(player, costMaterial) < cost) {
				itemLore.add(ChatColor.RED + "You don't have enough " + utils.bwName(costMaterial) + "!");
			} else if (!((item.equals(BwItem.Pickaxe) && utils.getMetadata(player, "picklevel").asInt() == 4) || (item.equals(BwItem.Axe) && utils.getMetadata(player, "axelevel").asInt() == 4))) {
				itemLore.add(ChatColor.YELLOW + "Click to purchase!");
			}
			if ((utils.getMetadata(player, "hasShears").asBoolean() && item.equals(BwItem.Sheers)) || (item.equals(BwItem.ChainmailArmor) && utils.getMetadata(player, "armor").asInt() >= 1) || (item.equals(BwItem.IronArmor) && utils.getMetadata(player, "armor").asInt() >= 2) || (item.equals(BwItem.DiamondArmor) && utils.getMetadata(player, "armor").asInt() >= 3)) {
				itemLore.remove(itemLore.size() - 1);
				itemLore.add(ChatColor.GREEN + "UNLOCKED");
			}
			if (item.getBwItemType() == BwItemType.Armor) {
				if(team.getProtection()!=0){
					itemLore.add(1,ChatColor.GRAY+"Upgraded: "+ChatColor.YELLOW+"Protection "+Utils.toRoman(team.getProtection()));
					Utils.addEnchantGlint(itemStack);
				}
			}

//                Ici on change la couleur du nom de l'item en fonction de si le joueur a assez de materiaux requis ou non, commun à tout les items contrairement au Lore
			if (utils.countItem(player, costMaterial) < cost) {
				itemName = ChatColor.RED + itemName;
			} else {
				itemName = ChatColor.GREEN + itemName;
			}


//                Ici on place les items
			outer:
			if (item.getBwItemType() == pagetype) {
				for (int i : itemSlots) {
					if (pages[page].getItem(i) == null) {
						ItemMeta itemMeta1 = itemStack.getItemMeta();
						itemMeta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						itemStack.setItemMeta(itemMeta1);
						pages[page].setItem(i, utils.customItem(itemStack, itemName, itemLore));
						break outer;
					}
				}

			}
		}


//    On fait ouvrir l'inventaire créé au joueur
		player.openInventory(pages[page]);
		player.updateInventory();
	}


	public static void ShopClick(InventoryClickEvent e, Main main) {
//        on vérifie avant tout que le joueur ne fait pas de la merde
		if (e.getClick().isKeyboardClick() || !(e.getClickedInventory().getSize() == 54) || e.getCurrentItem().getType().equals(Material.AIR)) {
			e.setCancelled(true);
			return;
		}
		Inventory inv = e.getInventory();
		ItemStack it = e.getCurrentItem();
		Player player = (Player) e.getWhoClicked();
		String itName = ChatColor.stripColor(it.getItemMeta().getDisplayName());
		Utils utils = new Utils();
		BwTeam team = main.getTeam(Utils.getMetadata(player, "color").asString());

		if (e.getSlot() < 18) {
			switch (it.getType()) {
				case NETHER_STAR:
					openShopUi(player, 0, main);
					return;
				case HARD_CLAY:
					openShopUi(player, 1, main);
					return;
				case GOLD_SWORD:
					openShopUi(player, 2, main);
					return;
				case CHAINMAIL_BOOTS:
					openShopUi(player, 3, main);
					return;
				case STONE_PICKAXE:
					openShopUi(player, 4, main);
					return;
				case BOW:
					openShopUi(player, 5, main);
					return;
				case BREWING_STAND_ITEM:
					openShopUi(player, 6, main);
					return;
				case TNT:
					openShopUi(player, 7, main);
					return;
				default:
					return;
			}
		}


//        Ici on est sur que l'item cliqué n'est pas un bouton de Menu

//        cette partie la est pour gerer l'achat d'outils, je ne souhaite à personne de devoir modifier cette partie pck c'est n'importe quoi
		Material[] toolcostMaterials = {Material.IRON_INGOT, Material.IRON_INGOT, Material.GOLD_INGOT, Material.GOLD_INGOT, Material.GOLD_INGOT};
		int[] toolscosts = {10, 10, 3, 6, 0};
		ItemMeta toolMeta = new ItemStack(Material.WOOD_PICKAXE).getItemMeta();
		toolMeta.spigot().setUnbreakable(true);
		toolMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		if (it.getType().equals(Material.WOOD_PICKAXE) || it.getType().equals(Material.IRON_PICKAXE) || it.getType().equals(Material.GOLD_PICKAXE) || it.getType().equals(Material.DIAMOND_PICKAXE)) {
			if (utils.getMetadata(player, "picklevel").asInt() < 4 && utils.countItem(player, toolcostMaterials[utils.getMetadata(player, "picklevel").asInt()]) >= toolscosts[utils.getMetadata(player, "picklevel").asInt()]) {
				String[] toolNames = new String[]{"Wooden Pickaxe (Efficiency I)", "Iron Pickaxe (Efficiency II)", "Gold Pickaxe (Efficiency III, Sharpness II)", "Diamond Pickaxe (Efficiency III)", ChatColor.RED + "Diamond Pickaxe (Efficiency III)"};
				player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.GOLD + toolNames[utils.getMetadata(player, "picklevel").asInt()]);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
				player.getInventory().removeItem(new ItemStack(toolcostMaterials[utils.getMetadata(player, "picklevel").asInt()], toolscosts[utils.getMetadata(player, "picklevel").asInt()]));
				utils.setMetadata(player, "picklevel", (utils.getMetadata(player, "picklevel").asInt() + 1));

//                Creation de la liste des ItemStacks de pioches qui seront données au joueur : pas de nom, de lore et tout MAIS il y a des enchants
				ItemStack[] itempioches = {new ItemStack(Material.WOOD_PICKAXE), new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.GOLD_PICKAXE), new ItemStack(Material.DIAMOND_PICKAXE)};
				int[] effilevel = {1, 2, 3, 3};
				for (int i = 0; i < 4; i++) {
					itempioches[i].setItemMeta(toolMeta);
					itempioches[i].addUnsafeEnchantment(Enchantment.DIG_SPEED, effilevel[i]);
				}
				itempioches[2].addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
				if (utils.getMetadata(player, "picklevel").asInt() > 1) {
					player.getInventory().removeItem(itempioches[utils.getMetadata(player, "picklevel").asInt() - 2]);
				}
				player.getInventory().addItem(itempioches[utils.getMetadata(player, "picklevel").asInt() - 1]);
			} else if (utils.getMetadata(player, "picklevel").asInt() > 3) {
				player.sendMessage(ChatColor.RED + "You've already purchased this item!");
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
			} else {
				player.sendMessage(ChatColor.RED + "You don't have enough " + utils.bwName(toolcostMaterials[utils.getMetadata(player, "picklevel").asInt()]) + "! Need " + (toolscosts[utils.getMetadata(player, "picklevel").asInt()] - utils.countItem(player, toolcostMaterials[utils.getMetadata(player, "picklevel").asInt()])) + " more!");
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
			}
		} else if (it.getType().equals(Material.WOOD_AXE) || it.getType().equals(Material.STONE_AXE) || it.getType().equals(Material.IRON_AXE) || it.getType().equals(Material.DIAMOND_AXE)) {
			String[] toolNames = new String[]{"Wooden Axe (Efficiency I)", "Stone Axe (Efficiency I)", "Iron Axe (Efficiency II)", "Diamond Axe (Efficiency III)", ChatColor.RED + "Diamond Axe (Efficiency III)"};
			if (utils.getMetadata(player, "axelevel").asInt() < 4 && utils.countItem(player, toolcostMaterials[utils.getMetadata(player, "axelevel").asInt()]) >= toolscosts[utils.getMetadata(player, "axelevel").asInt()]) {
				player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.GOLD + toolNames[utils.getMetadata(player, "axelevel").asInt()]);
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
				player.getInventory().removeItem(new ItemStack(toolcostMaterials[utils.getMetadata(player, "axelevel").asInt()], toolscosts[utils.getMetadata(player, "axelevel").asInt()]));
				utils.setMetadata(player, "axelevel", (utils.getMetadata(player, "axelevel").asInt() + 1));


				ItemStack[] itemhaches = {new ItemStack(Material.WOOD_AXE), new ItemStack(Material.STONE_AXE), new ItemStack(Material.IRON_AXE), new ItemStack(Material.DIAMOND_AXE)};
				int[] effilevel = {1, 1, 2, 3};
				for (int i = 0; i < 4; i++) {
					itemhaches[i].setItemMeta(toolMeta);
					itemhaches[i].addUnsafeEnchantment(Enchantment.DIG_SPEED, effilevel[i]);
//                    ############################## remplacer le false par un check de si le joueur a sharpness unlocké pour le mettre sur les haches aussi ######################
					if (team.getSharp()) {
						itemhaches[i].addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
					}
				}
				if (utils.getMetadata(player, "axelevel").asInt() > 1) {
					player.getInventory().removeItem(itemhaches[utils.getMetadata(player, "axelevel").asInt() - 2]);
				}
				player.getInventory().addItem(itemhaches[utils.getMetadata(player, "axelevel").asInt() - 1]);


			} else if (utils.getMetadata(player, "axelevel").asInt() > 3) {
				player.sendMessage(ChatColor.RED + "You've already purchased this item!");
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
			} else {
				player.sendMessage(ChatColor.RED + "You don't have enough " + utils.bwName(toolcostMaterials[utils.getMetadata(player, "axelevel").asInt()]) + " Need " + (toolscosts[utils.getMetadata(player, "axelevel").asInt()] - utils.countItem(player, toolcostMaterials[utils.getMetadata(player, "axelevel").asInt()])) + " more!");
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
			}


		} else if (it.getType() == Material.SHEARS) {
			if (!utils.getMetadata(player, "hasShears").asBoolean() && utils.countItem(player, Material.IRON_INGOT) >= 20) {
				utils.setMetadata(player, "hasShears", true);
				ItemStack shears = new ItemStack(Material.SHEARS);
				shears.setItemMeta(toolMeta);
				player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 20));
				player.getInventory().addItem(shears);
				player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.GOLD + "Permanent Shears");
				player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
			} else if (!utils.getMetadata(player, "hasShears").asBoolean()) {
				player.sendMessage(ChatColor.RED + "You don't have enough Iron! Need " + (20 - utils.countItem(player, Material.IRON_INGOT)) + " more!");
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
			} else {
				player.sendMessage(ChatColor.RED + "You've already purchased this item!");
				player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
			}
		} else
//Pour les armures ont fait une partie spéciale puisqu'ils modifient la metadata du joueur "armor"
			if (it.getType() == Material.CHAINMAIL_BOOTS || it.getType() == Material.IRON_BOOTS || it.getType() == Material.DIAMOND_BOOTS) {
				switch (it.getType()) {
					case CHAINMAIL_BOOTS:
						if (utils.getMetadata(player, "armor").asInt() < 1 && utils.countItem(player, Material.IRON_INGOT) >= 40) {
							utils.setMetadata(player, "armor", 1);
							ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
							ItemStack leggins = new ItemStack(Material.CHAINMAIL_LEGGINGS);
							boots.setItemMeta(toolMeta);
							leggins.setItemMeta(toolMeta);
							if (team.getProtection() != 0) {
								boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, team.getProtection());
								leggins.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, team.getProtection());
							}
							player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 40));
							player.getInventory().setBoots(boots);
							player.getInventory().setLeggings(leggins);
							player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.GOLD + "Permanent Chainmail Armor");
							if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {

							}
							player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
						} else if (utils.getMetadata(player, "armor").asInt() >= 1) {
							player.sendMessage(ChatColor.RED + "You've already purchased this item!");
							player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
						} else {
							player.sendMessage(ChatColor.RED + "You don't have enough Iron! Need " + (40 - utils.countItem(player, Material.IRON_INGOT)) + " more!");
							player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
						}
						break;
					case IRON_BOOTS:
						if (utils.getMetadata(player, "armor").asInt() < 2 && utils.countItem(player, Material.GOLD_INGOT) >= 12) {
							utils.setMetadata(player, "armor", 2);
							ItemStack boots = new ItemStack(Material.IRON_BOOTS);
							ItemStack leggins = new ItemStack(Material.IRON_LEGGINGS);
							boots.setItemMeta(toolMeta);
							leggins.setItemMeta(toolMeta);
							if (team.getProtection() != 0) {
								boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, team.getProtection());
								leggins.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, team.getProtection());
							}
							player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 12));
							player.getInventory().setBoots(boots);
							player.getInventory().setLeggings(leggins);
							player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.GOLD + "Permanent Iron Armor");
							player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
						} else if (utils.getMetadata(player, "armor").asInt() >= 2) {
							player.sendMessage(ChatColor.RED + "You've already purchased this item!");
							player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
						} else {
							player.sendMessage(ChatColor.RED + "You don't have enough Gold! Need " + (12 - utils.countItem(player, Material.GOLD_INGOT)) + " more!");
							player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
						}
						break;
					case DIAMOND_BOOTS:
						if (utils.getMetadata(player, "armor").asInt() < 3 && utils.countItem(player, Material.EMERALD) >= 6) {
							utils.setMetadata(player, "armor", 3);
							ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
							ItemStack leggins = new ItemStack(Material.DIAMOND_LEGGINGS);
							boots.setItemMeta(toolMeta);
							leggins.setItemMeta(toolMeta);
							if (team.getProtection() != 0) {
								boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, team.getProtection());
								leggins.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, team.getProtection());
							}
							player.getInventory().removeItem(new ItemStack(Material.EMERALD, 6));
							player.getInventory().setBoots(boots);
							player.getInventory().setLeggings(leggins);
							player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.GOLD + "Permanent Diamond Armor");
							player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
						} else if (utils.getMetadata(player, "armor").asInt() >= 1) {
							player.sendMessage(ChatColor.RED + "You've already purchased this item!");
							player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
						} else {
							player.sendMessage(ChatColor.RED + "You don't have enough Emerald! Need " + (6 - utils.countItem(player, Material.EMERALD)) + " more!");
							player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
						}
						break;

				}
			} else {
				ItemStack itemStack = new ItemStack(Material.BARRIER, 42);
				PotionMeta potionMeta = null;

//        Et maintenant on va sicko mode et on mets tout les autres items :D
				if (utils.countItem(player, utils.getCostMaterial(itName)) >= utils.getPrice(itName)) {
					itemStack = new ItemStack(it.getType(), it.getAmount());
					switch (ChatColor.stripColor(it.getItemMeta().getDisplayName())) {
						case "Jump V Potion (45 seconds)":
							itemStack = new ItemStack(Material.POTION, 1, (byte) 8235);
							potionMeta = (PotionMeta) itemStack.getItemMeta();
							potionMeta.clearCustomEffects();
							potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 45 * 20, 4, false, false), false);
							potionMeta.setDisplayName("Jump Boost V (0:45)");
							potionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
							itemStack.setItemMeta(potionMeta);
							break;
						case "Speed II Potion (45 seconds)":
							itemStack = new ItemStack(Material.POTION, 1, (byte) 8226);
							potionMeta = (PotionMeta) itemStack.getItemMeta();
							potionMeta.clearCustomEffects();
							potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 45 * 20, 1, false, false), false);
							potionMeta.setDisplayName("Speed II Potion (45 seconds)");
							potionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
							itemStack.setItemMeta(potionMeta);
							break;
						case "Invisibility Potion (30 seconds)":
							itemStack = new ItemStack(Material.POTION, 1, (byte) 8238);
							potionMeta = (PotionMeta) itemStack.getItemMeta();
							potionMeta.clearCustomEffects();
							potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30 * 20, 0, false, false), false);
							potionMeta.setDisplayName("Invisibility Potion (30 seconds)");
							potionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
							itemStack.setItemMeta(potionMeta);
							break;
						case "Stone Sword":
						case "Iron Sword":
						case "Diamond Sword":
							itemStack.setItemMeta(toolMeta);
							if (team.getSharp()) {
								itemStack.addEnchantment(Enchantment.DAMAGE_ALL, 1);
							}
							player.getInventory().remove(Material.WOOD_SWORD);
							break;
						case "Bow (Power I, Punch I)":
							itemStack.setItemMeta(toolMeta);
							itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
							itemStack.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
							break;

						case "Bow (Power I)":
							itemStack.setItemMeta(toolMeta);
							itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);

							break;

						case "Bow":
							itemStack.setItemMeta(toolMeta);
							break;


						case "Stick (Knockback I)":
							itemStack.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
							itemStack = utils.customItem(itemStack, "Knockback Stick");
							break;

						case "Bedbug":
						case "Dream Defender":
						case "Fireball":
						case "Bridge Egg":
						case "Magic Milk":
							itemStack = utils.customItem(itemStack, itName);
							break;
						case "Blast-Proof Glass":
							itemStack.setType(Material.STAINED_GLASS);
						case "Wool":
						case "Hardened Clay":
							byte itemData;
							itemData = Utils.colorData(Utils.getMetadata(player, "color").asString());
							itemStack = new ItemStack(itemStack.getType(), it.getAmount(), itemData);
						default:
							break;
					}

					player.getInventory().removeItem(new ItemStack(utils.getCostMaterial(itName), utils.getPrice(itName)));
					player.getInventory().addItem(itemStack);
					player.sendMessage(ChatColor.GREEN + "You purchased " + ChatColor.GOLD + itName);
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);

				} else {
					player.sendMessage(ChatColor.RED + "You don't have enough " + utils.bwName(utils.getCostMaterial(itName)) + "! Need " + (utils.getPrice(itName) - utils.countItem(player, utils.getCostMaterial(itName))) + " more!");
					player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 0.5f);
				}


			}


		String[] pagesName = {"Quick Buy", "Blocks", "Melee", "Armor", "Tools", "Ranged", "Potions", "Utility"};
		for (int i = 0; i < pagesName.length; i++) {
			if (inv.getName().equalsIgnoreCase(pagesName[i])) {
				openShopUi(player, i, main);
			}
		}
		player.updateInventory();
	}

}
