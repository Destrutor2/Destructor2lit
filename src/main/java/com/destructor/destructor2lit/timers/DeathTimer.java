package com.destructor.destructor2lit.timers;

import com.destructor.destructor2lit.BwTeam;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.Title;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DeathTimer extends BukkitRunnable {
    public int timer;
    Player player;
    List<Player> players;
    Main main;

    public DeathTimer(Player player, List<Player> players,Main main) {
        this.player = player;
        this.timer = 5;
        this.players = players;
        this.main=main;
    }

            Title title = new Title("YOU DIED!");
    @Override
    public void run() {
        if (timer == 0) {
            title.setTitle("RESPAWNED!");
            title.setTitleColor(ChatColor.GREEN);
            title.setTimingsToTicks();
            title.setSubtitle(" ");
            title.setFadeInTime(0);
            title.setStayTime(30);
            title.setFadeOutTime(20);
            title.clearTitle(player);
            title.send(player);
            player.sendMessage(ChatColor.YELLOW+"You have respawned!");
            Utils utils = new Utils();
            utils.setMetadata(player,"alive",true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
//            player.setVelocity(new Vector());
            player.setFallDistance(0);
            player.teleport(new Location(player.getWorld(), utils.getMetadata(player, "spawnX").asDouble(), utils.getMetadata(player, "spawnY").asDouble(), utils.getMetadata(player, "spawnZ").asDouble(), utils.getMetadata(player, "spawnYaw").asFloat(), 0f));
            for (Player p : players) {
                p.showPlayer(player);
            }
            main.getHiddenPlayers().remove(player);
            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta coloredArmorMeta = (LeatherArmorMeta) chestplate.getItemMeta();
            coloredArmorMeta.spigot().setUnbreakable(true);
            coloredArmorMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
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
//            on cree les itemstacks en fonction du niveau d'armure et du niveau de prot
            ItemMeta unbreakableMeta=new ItemStack(Material.IRON_BOOTS,1).getItemMeta();
            unbreakableMeta.spigot().setUnbreakable(true);
            unbreakableMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            ItemStack woodSword = new ItemStack(Material.WOOD_SWORD,1);
            woodSword.setItemMeta(unbreakableMeta);

//            On check pour healpool
            for(BwTeam team:main.getTeams()){
                if (team.contains(player)) {
                    if(team.getHealpool()){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,Integer.MAX_VALUE,0,true,true),true);
                    }
                    break;
                }
            }
            switch (utils.getMetadata(player, "armor").asInt()) {
                case 1:
                    boots = new ItemStack(Material.CHAINMAIL_BOOTS);
                    leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                    boots.setItemMeta(unbreakableMeta);
                    leggings.setItemMeta(unbreakableMeta);
                    break;
                case 2:
                    boots = new ItemStack(Material.IRON_BOOTS);
                    leggings = new ItemStack(Material.IRON_LEGGINGS);
                    boots.setItemMeta(unbreakableMeta);
                    leggings.setItemMeta(unbreakableMeta);
                    break;
                case 3:
                    boots = new ItemStack(Material.DIAMOND_BOOTS);
                    leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                    boots.setItemMeta(unbreakableMeta);
                    leggings.setItemMeta(unbreakableMeta);
                    break;
                default:
                    break;
            }
//            On mets le niveau de protection
            if (utils.getMetadata(player, "protection").asInt() != 0) {
                boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, utils.getMetadata(player, "protection").asInt());
                leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, utils.getMetadata(player, "protection").asInt());
                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, utils.getMetadata(player, "protection").asInt());
                helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, utils.getMetadata(player, "protection").asInt());

            }
            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
            player.getInventory().addItem(woodSword);
            new SpawnProt(player).runTaskLater(main,60);
            this.cancel();
        } else {
            title.setSubtitle(ChatColor.YELLOW + "You will respawn in " + ChatColor.RED + timer + ChatColor.YELLOW + " seconds!");
            title.setTitleColor(ChatColor.RED);
            title.setTimingsToTicks();
            title.setFadeOutTime(0);
            title.setStayTime(30);
            title.setFadeInTime(0);
            title.send(player);
            timer--;
        }
    }

}
