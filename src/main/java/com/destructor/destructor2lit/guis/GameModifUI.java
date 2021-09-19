package com.destructor.destructor2lit.guis;
//created by: oscar at 23:42 of 20/08/2021

import com.comphenix.protocol.wrappers.Pair;
import com.destructor.destructor2lit.BwConfig;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.events.SignGUIUpdateEvent;
import com.destructor.destructor2lit.utils.SignGUI;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.bukkit.ChatColor.*;

public class GameModifUI {
//	public static void bookOpenUI(Player player, Main main) {
//		List<String> pages = new ArrayList<String>();
//		TextComponent page0 = new TextComponent("");
//		page0.addExtra("Build Limit: ");
//		page0.addExtra(ChatColor.GREEN + "" + main.bwConfig.getBuildlimit());
//		page0.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/bw modifyvalue buildlimit "));
//		page0.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to modify the value!").create()));
//
//
//		pages.add(ComponentSerializer.toString(page0));
//		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
//		BookMeta meta = (BookMeta) book.getItemMeta();
//		meta.setTitle(ChatColor.RED + "Game Modifier Book");
//		meta.setAuthor("Destructor2ku");
//		BookUtil.setPages(meta, pages);
//		book.setItemMeta(meta);
//		BookUtil.openBook(book, player);
//	}

    static Map<Field, ItemStack> items;

    static {
        items = new HashMap<>();
        try {
            items.put(BwConfig.class.getField("buildlimit"), Utils.customItem(Material.PAPER, AQUA + "Build limit", new String[]{
                    WHITE + "The max height for",
                    WHITE + "blocks to be placed"}));
            items.put(BwConfig.class.getField("voidheight"), Utils.customItem(Material.PAPER, AQUA + "Void height", new String[]{
                    WHITE + "Height at which players",
                    WHITE + "will die"}));
            items.put(BwConfig.class.getField("attacktagstaytimemillis"), Utils.customItem(Material.PAPER, AQUA + "Attack tag persistance", new String[]{
                    WHITE + "The time it will",
                    WHITE + "take for a player to",
                    WHITE + "be able to die without it",
                    WHITE + "being a kill"}));
            items.put(BwConfig.class.getField("minbowcharge"), Utils.customItem(Material.PAPER, AQUA + "Bow buff", new String[]{
                    WHITE + "The amount of charge",
                    WHITE + "bow needs to be at",
                    WHITE + "to be able to shoot arrows"}));
            items.put(BwConfig.class.getField("popuptowerspeedmultiplier"), Utils.customItem(Material.PAPER, AQUA + "Popup tower speed multiplier", new String[]{
                    WHITE + "The speed at which",
                    WHITE + "Popup Towers will deploy",
                    WHITE + "in blocks/s"}));
            items.put(BwConfig.class.getField("teamgenmultiplier"), Utils.customItem(Material.PAPER, AQUA + "Generator multiplier", new String[]{
                    WHITE + "The speed at which",
                    WHITE + "team generators",
                    WHITE + "item spawning will",
                    WHITE + "spawn",
                    GRAY + "(relative to the map",
                    GRAY + "usual spawn rates)"
            }));
            items.put(BwConfig.class.getField("healpoolradius"), Utils.customItem(Material.PAPER, AQUA + "Heal pool radius", new String[]{
                    WHITE + "The distance from",
                    WHITE + "their bed at which",
                    WHITE + "player will be healed",
                    WHITE + "with the heal pool upgrade",
                    GRAY + "(the value is squared)"
            }));
            items.put(BwConfig.class.getField("spawnprotectionradius"), Utils.customItem(Material.PAPER, AQUA + "Spawn protection radius", new String[]{
                    WHITE + "The distance from",
                    WHITE + "each team's spawn",
                    WHITE + "at which players",
                    WHITE + "wont be able to place",
                    WHITE + "blocks",
                    GRAY + "(the value is squared)"
            }));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
//	Arrays.asList(Utils.customItem(Material.PAPER, "Build limit", new String[]{WHITE + "The max height for", WHITE + "blocks to be placed"}),
//			Utils.customItem(Material.PAPER, "Void height", new String[]{WHITE + "Height at which players", WHITE + "will die"}));

    public static void openUI(Player player, Main main) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.RED + "Game Modifier Interface");

        Iterator<Map.Entry<Field, ItemStack>> itemStackIterator = items.entrySet().iterator();
        for (int i = 10; (i < 44) && itemStackIterator.hasNext(); i++) {
            if ((i % 9) != 0 && ((i + 1) % 9) != 0) {
                Map.Entry<Field, ItemStack> current = itemStackIterator.next();
                ItemStack it = current.getValue().clone();
                try {
                    it = Utils.addLoreLine(it, "");
                    it = Utils.addLoreLine(it, WHITE + "Current value: " + AQUA + current.getKey().get(main.bwConfig).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                it = Utils.addLoreLine(it, YELLOW + "Click to edit!");
                inventory.setItem(i, it);
            }
        }

        player.openInventory(inventory);

    }

    public static void click(InventoryClickEvent e, Main main) {
        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName())
            return;
        if (!(e.getWhoClicked() instanceof Player))
            return;

        ItemStack item = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();

        for (Map.Entry<Field, ItemStack> it :
                items.entrySet()) {
            if (it.getValue().getItemMeta().getDisplayName().equalsIgnoreCase(item.getItemMeta().getDisplayName())) {
                player.closeInventory();
                try {
                    SignGUI.openSignEditor(player, new String[]{"->", it.getValue().getItemMeta().getDisplayName(), "old value: ", it.getKey().get(main.bwConfig).toString()});
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }
    }

    public static void onSignGUIUpdateEvent(SignGUIUpdateEvent e, Main main) {
        String property = e.getSignText()[1];
        String value = e.getSignText()[0].replaceAll("-", "").replaceAll(">", "");
        Field modifiedField = null;
        for (Field field :
                items.keySet()) {
            if (items.get(field).getItemMeta().getDisplayName().equalsIgnoreCase(property)) {
                modifiedField = field;
//                Bukkit.broadcastMessage("property: \"" + property + "\"");
//                Bukkit.broadcastMessage("value: \"" + value + "\"");
            }
        }
        Object formattedValue = checkInput(modifiedField, value, e.getPlayer());
        if (formattedValue == null) {
            e.getPlayer().sendMessage(RED + "" + BOLD + "Error: Incorrect input");
            return;
        }
        modifiedField.setAccessible(true);
        try {
            modifiedField.set(main.bwConfig, formattedValue);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    private static Object checkInput(Field modifiedField, String value, Player player) {
        Object result = null;

        if (modifiedField == null)
            return result;

        if (value == "") {
            player.sendMessage("Please provide a value on the first line");
            return result;
        }
        if (Long.class.isAssignableFrom(modifiedField.getType())) {
            try {
                result = Long.parseLong(value);
            } catch (NumberFormatException e) {
                player.sendMessage("The value must be a Long (" + Long.MIN_VALUE + "<=val<=" + Long.MAX_VALUE + ", and no decimal)");
                return null;
            }
        } else if (Double.class.isAssignableFrom(modifiedField.getType())) {
            try {
                result = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                player.sendMessage("The value must be an Double (" + Double.MIN_VALUE + "<=val<=" + Double.MAX_VALUE + ")");
                return null;
            }
        } else if (Integer.class.isAssignableFrom(modifiedField.getType()) || int.class.isAssignableFrom(modifiedField.getType())) {
            try {
                result = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                player.sendMessage("The value must be an Integer (" + Integer.MIN_VALUE + "<=val<=" + Integer.MAX_VALUE + ", and no decimal)");
                return null;
            }
        } else if (Float.class.isAssignableFrom(modifiedField.getType()) || float.class.isAssignableFrom(modifiedField.getType())) {
            try {
                result = Float.parseFloat(value);
            } catch (NumberFormatException e) {
                player.sendMessage("The value must be a Float (" + Float.MIN_VALUE + "<=val<=" + Float.MAX_VALUE + ")");
                return null;
            }
        } else if (Byte.class.isAssignableFrom(modifiedField.getType()) || byte.class.isAssignableFrom(modifiedField.getType())) {
            try {
                result = Byte.parseByte(value);
            } catch (NumberFormatException e) {
                player.sendMessage("The value must be a Byte (" + Byte.MIN_VALUE + "<=val<=" + Byte.MAX_VALUE + ", and no decimal)");
                return null;
            }
        } else {
            player.sendMessage("This type of value has not been implemented yet: " + modifiedField.getType().toString());
        }
        return result;
    }
}
