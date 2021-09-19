package com.destructor.destructor2lit.listeners;

import com.destructor.destructor2lit.*;
import com.destructor.destructor2lit.customEntities.BwPet;
import com.destructor.destructor2lit.customItems.PopupTower;
import com.destructor.destructor2lit.customItems.bridgeEgg;
import com.destructor.destructor2lit.customItems.bwFireball;
import com.destructor.destructor2lit.customItems.bwSponge;
import com.destructor.destructor2lit.enums.BwDeaths;
import com.destructor.destructor2lit.enums.GameState;
import com.destructor.destructor2lit.events.BedBreak;
import com.destructor.destructor2lit.events.Die;
import com.destructor.destructor2lit.events.SignGUIUpdateEvent;
import com.destructor.destructor2lit.events.TriggerTrap;
import com.destructor.destructor2lit.guis.GameModifUI;
import com.destructor.destructor2lit.guis.Shop;
import com.destructor.destructor2lit.guis.Upgrades;
import com.destructor.destructor2lit.timers.GamePhaseTimer;
import com.destructor.destructor2lit.timers.WaitingScoreboardTimer;
import com.destructor.destructor2lit.utils.Utils;
import net.minecraft.server.v1_8_R3.*;
import org.apache.logging.log4j.core.helpers.SystemClock;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.List;
import java.util.UUID;

import static org.bukkit.ChatColor.RED;

public class PlayersListeners implements Listener {
    private final Main main;
    private Utils utils = new Utils();


    public PlayersListeners(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        main.npcManager.showNpcs(main, player);
        player.awardAchievement(Achievement.OPEN_INVENTORY);
//		main.entityHider.hideHiddenEntities(player);

//		On donne le livre de modif si le joueur est le premier op a avoir rejoin
        if (player.isOp()) {
            if (main.gamemodiferPlayer == null || !main.gamemodiferPlayer.isOnline()) {
                main.gamemodiferPlayer = player;
                player.sendMessage(RED + "You were the granted the " + ChatColor.BOLD + "Game Modifier Book");
                player.getInventory().addItem(utils.customItem(Material.BOOK, RED + "" + ChatColor.BOLD + "Game Modifier Book", new String[]{ChatColor.WHITE + "Anyone with this book", ChatColor.WHITE + "can modify game behaviours"}, true));
            }
        }

//		new PacketReader().inject(player, main);

        boolean spec = true;

//        ###################IMPLEMENTER LES COULEURES DE RANK DES QUE POSSIBLE ###################################
        if (!main.isState(GameState.WAITING)) {

            GamePhaseTimer scoreboard = new GamePhaseTimer(main, player);
            scoreboard.runTaskTimer(main, 0, 1);

            for (Entity entity : player.getWorld().getEntities()) {
                if (entity.getCustomName() != null) {
                    if (entity.getCustomName().equalsIgnoreCase(player.getDisplayName()) && entity instanceof Zombie) {
                        Utils utils = new Utils();
                        if (main.hasBed(utils.getMetadata(entity, "color").asString()) || utils.getMetadata(entity, "alive").asBoolean()) {
                            e.setJoinMessage(new Utils().getColor(player) + player.getDisplayName() + ChatColor.GRAY + " reconnected");
                            main.getPlayers().add(player);
                            spec = false;
                            utils.setMetadata(player, "axelevel", utils.getMetadata(entity, "axelevel").asByte());
                            utils.setMetadata(player, "picklevel", utils.getMetadata(entity, "picklevel").asByte());
                            utils.setMetadata(player, "hasShears", utils.getMetadata(entity, "hasShears").asBoolean());
                            utils.setMetadata(player, "armor", utils.getMetadata(entity, "armor").asByte());
                            utils.setMetadata(player, "sharp", utils.getMetadata(entity, "sharp").asBoolean());
                            utils.setMetadata(player, "protection", utils.getMetadata(entity, "protection").asByte());
                            utils.setMetadata(player, "haste", utils.getMetadata(entity, "haste").asByte());
                            utils.setMetadata(player, "alive", utils.getMetadata(entity, "alive").asBoolean());
                            utils.setMetadata(player, "color", utils.getMetadata(entity, "color").asString());
                            utils.setMetadata(player, "spawnX", utils.getMetadata(entity, "spawnX").asDouble());
                            utils.setMetadata(player, "spawnY", utils.getMetadata(entity, "spawnY").asDouble());
                            utils.setMetadata(player, "spawnZ", utils.getMetadata(entity, "spawnZ").asDouble());
                            utils.setMetadata(player, "spawnYaw", utils.getMetadata(entity, "spawnYaw").asFloat());
                            utils.setMetadata(player, "invincible", false);
                            utils.setMetadata(player, "lastdamager", utils.getMetadata(entity, "lastdamager").asString());
                            utils.setMetadata(player, "lastattack", utils.getMetadata(entity, "lastattack").asLong());
                            utils.setMetadata(player, "lastMilk", utils.getMetadata(entity, "lastMilk").asLong());
                            utils.setMetadata(player, "lastfb", 0);

                            if (main.hasBed(utils.getMetadata(entity, "color").asString())) {
                                Die.Die(player, main, BwDeaths.RECONNECT);
                            } else {
                                player.setHealth(((Zombie) entity).getHealth());
                                player.getInventory().setArmorContents(main.offlinePlayersArmor.get(player.getUniqueId()));
                                main.offlinePlayersArmor.remove(player.getUniqueId());
                                player.getInventory().setContents(main.offlinePlayersInventory.get(player.getUniqueId()));
                                main.offlinePlayersArmor.remove(player.getUniqueId());
                                player.getEnderChest().setContents(main.offlinePlayersEnderItems.get(player.getUniqueId()));
                                main.offlinePlayersEnderItems.remove(player.getUniqueId());
                            }
                            entity.remove();
                        } else if (!main.hasBed(utils.getMetadata(entity, "color").asString()) && !utils.getMetadata(entity, "alive").asBoolean()) {
                            entity.remove();
                            player.sendMessage(RED + "You lost your bed and died while you were disconnected");
                        }
                    }
                }
            }
            if (spec) {
                new Utils().setMetadata(player, "alive", false);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
                for (Player p : main.getPlayers()) {
                    p.hidePlayer(player);
                }
                main.getHiddenPlayers().add(player);
                e.setJoinMessage(null);
            }
        } else {
            WaitingScoreboardTimer waitingScoreboardTimer = new WaitingScoreboardTimer(main);
            waitingScoreboardTimer.runTaskTimer(main, 0, 1);
            player.setScoreboard(waitingScoreboardTimer.scoreboard);
            e.setJoinMessage(ChatColor.GRAY + player.getDisplayName() + ChatColor.YELLOW + " has joined (" + ChatColor.AQUA + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/" + ChatColor.AQUA + "8" + ChatColor.YELLOW + ")!");
            if (!main.getPlayers().contains(player)) {
                main.getPlayers().add(player);
                player.setGameMode(GameMode.ADVENTURE);
                new Utils().initPlayerMetadata(player);
            }
            if (main.getPlayers().size() == 2) {
                main.StartStartTimer(false);
            }
        }
        if (!spec) {
            for (Player p : main.getHiddenPlayers()) {
                player.hidePlayer(p);
            }
        }


    }

    @EventHandler
    public void onSpawnLocation(PlayerSpawnLocationEvent e) {
        if (!main.isState(GameState.WAITING)) {
            for (Entity entity : e.getSpawnLocation().getWorld().getEntities()) {
                if (entity.getCustomName() != null) {
                    if (entity.getCustomName().equalsIgnoreCase(e.getPlayer().getDisplayName()) && entity instanceof Zombie) {
                        e.setSpawnLocation(entity.getLocation());
                        return;
                    }
                }
            }
        }
        e.setSpawnLocation(main.spawn);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (main.getPlayers().contains(player)) {
            if (main.isState(GameState.WAITING)) {
//        ###################IMPLEMENTER LES COULEURES DE RANK DES QUE POSSIBLE ###################################
                e.setQuitMessage(ChatColor.GRAY + player.getDisplayName() + ChatColor.YELLOW + " has quit!");
                main.getPlayers().remove(player);
                if (main.getPlayers().size() == 1) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(RED + "We don't have enough players! Start cancelled.");
                    }
                }
            } else {
                Zombie zombie = e.getPlayer().getWorld().spawn(e.getPlayer().getLocation(), Zombie.class);
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, false, false));
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 255, false, false));
                zombie.setRemoveWhenFarAway(false);
                zombie.setBaby(false);
                zombie.setVillager(false);

                zombie.getEquipment().setArmorContents(e.getPlayer().getEquipment().getArmorContents());
                zombie.getEquipment().setItemInHand(e.getPlayer().getItemInHand());
                zombie.getEquipment().setItemInHandDropChance(0);
                zombie.getEquipment().setHelmetDropChance(0);
                zombie.getEquipment().setChestplateDropChance(0);
                zombie.getEquipment().setLeggingsDropChance(0);
                zombie.getEquipment().setBootsDropChance(0);
                zombie.setCustomName(e.getPlayer().getDisplayName());
                zombie.setCustomNameVisible(true);
                zombie.setMaxHealth(20);
                zombie.setHealth(player.getHealth());
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                meta.setOwner(player.getName());
                skull.setItemMeta(meta);
                zombie.getEquipment().setHelmet(skull);

                Utils utils = new Utils();
                utils.setMetadata(zombie, "axelevel", utils.getMetadata(e.getPlayer(), "axelevel").asByte());
                utils.setMetadata(zombie, "picklevel", utils.getMetadata(e.getPlayer(), "picklevel").asByte());
                utils.setMetadata(zombie, "hasShears", utils.getMetadata(e.getPlayer(), "hasShears").asBoolean());
                utils.setMetadata(zombie, "armor", utils.getMetadata(e.getPlayer(), "armor").asByte());
                utils.setMetadata(zombie, "sharp", utils.getMetadata(e.getPlayer(), "sharp").asBoolean());
                utils.setMetadata(zombie, "protection", utils.getMetadata(e.getPlayer(), "protection").asByte());
                utils.setMetadata(zombie, "haste", utils.getMetadata(e.getPlayer(), "haste").asByte());
                utils.setMetadata(zombie, "alive", utils.getMetadata(e.getPlayer(), "alive").asBoolean());
                utils.setMetadata(zombie, "color", utils.getMetadata(e.getPlayer(), "color").asString());
                utils.setMetadata(zombie, "spawnX", utils.getMetadata(e.getPlayer(), "spawnX").asDouble());
                utils.setMetadata(zombie, "spawnY", utils.getMetadata(e.getPlayer(), "spawnY").asDouble());
                utils.setMetadata(zombie, "spawnZ", utils.getMetadata(e.getPlayer(), "spawnZ").asDouble());
                utils.setMetadata(zombie, "spawnYaw", utils.getMetadata(e.getPlayer(), "spawnYaw").asFloat());
                utils.setMetadata(zombie, "invincible", false);
                utils.setMetadata(zombie, "lastdamager", utils.getMetadata(e.getPlayer(), "lastdamager").asString());
                utils.setMetadata(zombie, "lastattack", utils.getMetadata(e.getPlayer(), "lastattack").asLong());
                utils.setMetadata(zombie, "lastMilk", utils.getMetadata(e.getPlayer(), "lastMilk").asLong());
                utils.setMetadata(zombie, "UUID", player.getUniqueId().toString());

                main.getPlayers().remove(player);

                main.offlinePlayersInventory.put(player.getUniqueId(), player.getInventory().getContents());
                main.offlinePlayersArmor.put(player.getUniqueId(), player.getEquipment().getArmorContents());
                main.offlinePlayersEnderItems.put(player.getUniqueId(), player.getEnderChest().getContents());


                e.setQuitMessage(new Utils().getColor(player) + player.getDisplayName() + ChatColor.GRAY + " disconnected");
            }


        } else {
            e.setQuitMessage(null);
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlockPlaced();
        Material m = block.getType();
        Location location = block.getLocation();

        for (BwTeam team : main.getTeams()) {
            if (team.getSpawn().distanceSquared(e.getBlock().getLocation()) <= main.bwConfig.spawnprotectionradius) {
                e.setCancelled(true);
                player.updateInventory();
                return;
            }
        }

        if (e.getBlock().getY() >= main.bwConfig.buildlimit) {
            e.setCancelled(true);
            player.updateInventory();
            if (new Utils().getMetadata(player, "alive").asBoolean()) {
                e.getPlayer().sendMessage(RED + "Build height limit reached!");
            }
            return;
        }

        if (m == Material.TNT) {
            Location tntloc = new Location(location.getWorld(), location.getX() + 0.5, location.getY() + 0.5, location.getZ() + 0.5);
            block.setType(Material.AIR);
            TNTPrimed tnt = player.getWorld().spawn(tntloc, TNTPrimed.class);
            tnt.setFuseTicks(60);
            Utils.setMetadata(tnt, "owner", player.getUniqueId());
            return;
        }

        if (block.getType().equals(Material.SPONGE)) {
            new bwSponge(main, block);
        }

        if (block.getType().equals(Material.CHEST)) {
            new PopupTower(main, block, player, main.bwConfig.popuptowerspeedmultiplier);
        }

        setPlacedBlock(block);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();

        if (!(isPlacedBlock(block)) && !(block.getType().equals(Material.BED_BLOCK))) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(RED + "You can't break blocks here!");
        }

        if (!new Utils().getMetadata(e.getPlayer(), "alive").asBoolean()) {
            e.setCancelled(true);
            return;
        }

        if (block.getType().equals(Material.BED_BLOCK)) {
            BedBreak.Break(e, main);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        ItemStack it = e.getCurrentItem();
        InventoryType.SlotType slotType = e.getSlotType();
        Player player = (Player) e.getWhoClicked();

        if (it == null) {
            return;
        }

        if (slotType.equals(InventoryType.SlotType.ARMOR)) {
            e.setCancelled(true);
        }

        if (inv.getName().equals("Quick Buy") || inv.getName().equals("Blocks") || inv.getName().equals("Melee") || inv.getName().equals("Armor") || inv.getName().equals("Tools") || inv.getName().equals("Ranged") || inv.getName().equals("Potions") || inv.getName().equals("Utility")) {
            Shop.ShopClick(e, main);
            e.setCancelled(true);
        }

        if (inv.getName().equalsIgnoreCase("Queue a trap") || inv.getName().equalsIgnoreCase("Upgrades & Traps")) {
            Upgrades.UpgradeClick(e, main);
            e.setCancelled(true);
        }

        if (inv.getName().equalsIgnoreCase(RED + "Game Modifier Interface")) {
            GameModifUI.click(e, main);
            e.setCancelled(true);
        }

        if (player.getFallDistance() >= 1 && (e.getAction() == InventoryAction.DROP_ONE_CURSOR || e.getAction() == InventoryAction.DROP_ALL_CURSOR || e.getAction() == InventoryAction.DROP_ONE_SLOT || e.getAction() == InventoryAction.DROP_ALL_SLOT)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Item it = e.getItemDrop();
        if (it.getItemStack().getType() == Material.WOOD_PICKAXE || it.getItemStack().getType() == Material.IRON_PICKAXE || it.getItemStack().getType() == Material.GOLD_PICKAXE || it.getItemStack().getType() == Material.DIAMOND_PICKAXE || it.getItemStack().getType() == Material.WOOD_AXE || it.getItemStack().getType() == Material.STONE_AXE || it.getItemStack().getType() == Material.IRON_AXE || it.getItemStack().getType() == Material.DIAMOND_AXE || it.getItemStack().getType() == Material.SHEARS || it.getItemStack().getType() == Material.WOOD_SWORD) {
            e.setCancelled(true);
        }
        if (!new Utils().getMetadata(e.getPlayer(), "alive").asBoolean() || e.getPlayer().getFallDistance() > 3) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamagedByEntity(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        Utils utils = new Utils();

        //		Pour empecher de se tirer soit meme a l'arc et log l'attack si c'est pas soit meme:
        if (damager instanceof Arrow && entity instanceof Player) {
            if (((Arrow) damager).getShooter().equals(entity)) {
                e.setCancelled(true);
                return;
            } else if (((Projectile) damager).getShooter() instanceof Player) {
                utils.logAttack(((Player) ((Projectile) damager).getShooter()).getUniqueId().toString(), entity);
            }
        }

        if (damager instanceof Projectile && !(entity instanceof Player)) {
            if (Utils.getMetadata(entity, "color") != null) {
                if (Utils.getMetadata((Player) (((Projectile) damager).getShooter()), "color").asString().equals(Utils.getMetadata(entity, "color").asString())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }

        if (damager instanceof IronGolem) {
            e.setDamage(e.getDamage() * main.bwConfig.golemdamagemultiplier);
        }

        if (damager instanceof Player && entity instanceof Zombie) {
            if (utils.getMetadata(damager, "alive").asBoolean() && damager.getWorld().getPVP()) {
                utils.logAttack(damager.getUniqueId().toString(), entity);
            } else {
                e.setCancelled(true);
                return;
            }
        }

        if (damager instanceof TNTPrimed && entity instanceof Entity) {
            e.setDamage(e.getDamage() / 7);
            entity.setVelocity(new Vector((entity.getLocation().getX() - damager.getLocation().getX()), (entity.getLocation().getY() - damager.getLocation().getY()), (entity.getLocation().getZ() - damager.getLocation().getZ())).normalize().multiply(1.2));
            Utils.logAttack(Utils.getMetadata((TNTPrimed) damager, "owner").asString(), entity);
        }

//		Si on lance une fleche ou une fireball a un pet, on cancel le degat
        if ((entity instanceof IronGolem || entity instanceof Silverfish) && damager instanceof Projectile) {
            if (((Projectile) damager).getShooter() instanceof Metadatable)
                if (utils.getMetadata((Metadatable) ((Projectile) damager).getShooter(), "color") != null)
                    if (utils.getMetadata((Metadatable) ((Projectile) damager).getShooter(), "color").equals(utils.getMetadata(entity, "color"))) {
                        e.setCancelled(true);
                        return;
                    }
        }

//		On multiplie les degats recu par un ironGolem, on aurait aussi pu reduire leur vie hmm
        if (entity instanceof IronGolem) {
            if (damager instanceof Fireball)
                e.setDamage(e.getFinalDamage() * 15);
            else
                e.setDamage(e.getFinalDamage() * 5);
        }

//		On ne veut pas que les zombies des joueurs afk attaquent d'autres joueurs mais on veut quand meme qu'ils les regardent
        if (damager instanceof Zombie) {
            e.setCancelled(true);
        }

        if (utils.getMetadata(entity, "color") != null && utils.getMetadata(damager, "color") != null) {
            if (utils.getMetadata(entity, "color").asString().equals(utils.getMetadata(damager, "color").asString())) {
                e.setCancelled(true);
                return;
            }
            if (!(damager instanceof Player) && entity instanceof Player)
                utils.logAttack(utils.getMetadata(damager, "owner").asString(), entity);
        }

        if (damager instanceof Player) {
            if (!new Utils().getMetadata(e.getDamager(), "alive").asBoolean()) {
                e.setCancelled(true);
            }

            if (utils.getMetadata(damager, "invincible").asBoolean() && !damager.equals(entity)) {
                utils.setMetadata(damager, "invincible", false);
            }
        }

        if (damager instanceof Player && entity instanceof Player) {
//			if (utils.getMetadata(entity, "invincible").asBoolean()) {
//              c'est ici qu'on peut mettre un son ou un message à l'attaquant quand la victime est en spawn prot
// 				((Player) damager).playSound(damager.getLocation(), Sound.BURP, 1, 1);
//			}
//        Lorsqu'un joueur en tape un autre, on set son lastdamager et son lastattack à l'heure actuelle
            if (!damager.equals(entity) && utils.getMetadata(damager, "alive").asBoolean() && damager.getWorld().getPVP()) {
                utils.logAttack(damager.getUniqueId().toString(), entity);
            }

//			Si la victime était invis et que l'attaquant a utilisé une épée, on retire l'invis de la victime et on lui remets son armure
            if (((Player) entity).hasPotionEffect(PotionEffectType.INVISIBILITY) && (
                    ((Player) damager).getItemInHand().getType().equals(Material.WOOD_SWORD) ||
                            ((Player) damager).getItemInHand().getType().equals(Material.STONE_SWORD) ||
                            ((Player) damager).getItemInHand().getType().equals(Material.IRON_SWORD) ||
                            ((Player) damager).getItemInHand().getType().equals(Material.DIAMOND_SWORD))) {
                ((Player) entity).removePotionEffect(PotionEffectType.INVISIBILITY);
                utils.showArmor((Player) entity);

            }
        }

//		Cas de kill:
        if (entity instanceof Player) {
            if (e.getFinalDamage() >= ((Player) entity).getHealth()) {
                if (damager instanceof Arrow) {
                    Die.Die((Player) entity, main, BwDeaths.ARROW);
                } else if (damager instanceof IronGolem)
                    Die.Die((Player) entity, main, BwDeaths.GOLEM);
                else if (damager instanceof Silverfish)
                    Die.Die((Player) entity, main, BwDeaths.BUG);
                else if (damager instanceof Fireball)
                    Die.Die((Player) entity, main, BwDeaths.EXPLOSION);
                else
                    Die.Die((Player) entity, main, BwDeaths.KILL);


                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    void onPlayerDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (new Utils().getMetadata(e.getEntity(), "invincible").asBoolean()) {
            e.setCancelled(true);
            return;
        }

        Player player = (Player) e.getEntity();
//		Cas de mort:
        if (e.getFinalDamage() >= ((Player) e.getEntity()).getHealth()) {
            e.setCancelled(true);
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
                Die.Die(player, main, BwDeaths.FALL);
            else if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK))
                Die.Die(player, main, BwDeaths.FIRE);
        }

    }

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent e) {
        Bukkit.broadcastMessage(RED + "Oops le type de mort de " + e.getEntity().getDisplayName() + " n'a pas encore été implémenté :o");
        e.setKeepInventory(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInterract(PlayerInteractEvent e) {


//		cette partie commentee est surement la pire chose que jai jamais fait
//		new BukkitRunnable() {
//			@Override
//			public void run() {
//				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//					if (e.getClickedBlock().getRelative(e.getBlockFace()).getType().equals(Material.AIR) && e.getItem().getType().isBlock()) {
//
//						boolean placeable = true;
//						for (Entity entity : e.getClickedBlock().getWorld().getNearbyEntities(e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 1, 1, 1)) {
//							if (entity instanceof Player) {
//								if (e.getPlayer().canSee((Player) entity)) {
//									placeable = false;
//								}
//							}
//						}
//
//						if (placeable) {
//							e.getClickedBlock().getRelative(e.getBlockFace()).setType(e.getItem().getType());
//							e.getClickedBlock().setData(e.getItem().getData().getData());
//							e.getClickedBlock().setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
//							new Utils().useItem(e.getPlayer(), 1);
//						}
//					}
//				}
//			}
//		}.runTaskLater(main, 1);

//        Bukkit.broadcastMessage(e.getPlayer().getOpenInventory().getBottomInventory().getType().toString()+"\n"+e.getPlayer().getOpenInventory().getTopInventory().getType().toString());

        if (e.getItem() != null &&
                e.getItem().hasItemMeta() &&
                e.getItem().getItemMeta().getDisplayName() != null &&
                ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Game Modifier Book")) {
            GameModifUI.openUI(e.getPlayer(), main);
        }

        if (!new Utils().getMetadata(e.getPlayer(), "alive").asBoolean()) {
            e.setCancelled(true);
        }

        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType().equals(Material.BED_BLOCK) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !(e.getPlayer().isSneaking() && e.getItem() != null)) {
                e.setCancelled(true);
            }
            if (e.getItem() != null) {
                if (e.getItem().hasItemMeta()) {
                    if (e.getItem().getItemMeta().hasDisplayName()) {
                        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("dream defender") && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                            new BwPet(EntityType.IRON_GOLEM, 240, e.getPlayer(), e.getClickedBlock().getLocation().add(0, 2, 0), main);
                            new Utils().useItem(e.getPlayer(), 1);
                        }
                    }
                }

                if (e.getItem().getType().equals(Material.WATER_BUCKET) && !e.isCancelled() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            e.getPlayer().getInventory().remove(Material.BUCKET);
                        }
                    }.runTaskLater(main, 1);
                }
            }
        }

        if (e.hasItem()) {
            if (e.getItem().hasItemMeta()) {
                if (e.getItem().getItemMeta().hasDisplayName()) {
                    if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("fireball") && (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))) {
                        e.setCancelled(true);
                        new bwFireball(e, main);

                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        if (!new Utils().getMetadata(e.getPlayer(), "alive").asBoolean()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        Player player = e.getPlayer();
        ItemStack it = e.getItem();
        Utils utils = new Utils();

//		Si c'est un milk bucket, on enlève un bucket de l'inv du joueur après un tick
        if (it.getType().equals(Material.MILK_BUCKET)) {
            utils.setMetadata(player, "lastMilk", new SystemClock().currentTimeMillis());
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().remove(new ItemStack(Material.BUCKET, 1));
                }
            }.runTaskLater(main, 1);

//			On en profite d'être ici pour envoyer le message milk wore off au joueur après 30s
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (utils.getMetadata(player, "lastMilk").asLong() + 29000 < new SystemClock().currentTimeMillis()) {
                        player.sendMessage(RED + "Your Magic Milk wore off!");
                    }
                }
            }.runTaskLater(main, 20 * 30);
        }


//		Si c'est une potion, on enlève une bouteille de l'inv du joueur après un tick
        if (it.getType().equals(Material.POTION)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().remove(new ItemStack(Material.GLASS_BOTTLE, 1));
                }
            }.runTaskLater(main, 1);


//			On profite d'être la pour faire les trucs avec l'invis
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                        utils.hideArmor(player);
                    }
                }
            }.runTaskLater(main, 1);

//			Au cas ou le joueur n'aurais pas perdu son invis par lui même, on lui remets son armure normalement SAUF si il a bu une autre invis
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                        utils.showArmor(player);
                    }
                }
            }.runTaskLater(main, 20 * 30 + 2);

        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            e.setCancelled(true);
            boolean illegaltp = false;

            for (UUID npc : main.npcLocations.keySet()) {
                if (main.npcLocations.get(npc).distanceSquared(e.getTo()) < 4) {
                    illegaltp = true;
                }
            }

            if (!illegaltp) {
                e.getPlayer().damage(2);
                e.getPlayer().teleport(e.getTo());
                e.getPlayer().setFallDistance(0);
            } else {
                e.getPlayer().getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
                e.getPlayer().sendMessage(RED + "Tu ne peux pas te tp sur un npc!");
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
//		Le truc pour si on tombe dans le void:
        if (e.getTo().getY() < main.bwConfig.voidheight) {
            if (!Utils.getMetadata(e.getPlayer(), "alive").asBoolean()) {
                e.getPlayer().teleport(main.spawn);
                return;
            }
            for (Entity entity : e.getPlayer().getWorld().getEntitiesByClasses(EnderPearl.class))
                if (((EnderPearl) entity).getShooter().equals(e.getPlayer()))
//					de cette manière, si le joueur a lancé une pearl, on attends un peu avant de le faire tomber dans le void
                    if (entity.getLocation().getY() > main.bwConfig.voidheight)
                        return;


            Die.Die(e.getPlayer(), main, BwDeaths.VOID);
        }

        if (Utils.getMetadata(e.getPlayer(), "alive").asBoolean()) {
            for (BwTeam team : main.getTeams()) {
                if ((e.getTo().distanceSquared(team.getBaseCenter()) < main.bwConfig.healpoolradius)
//                        && (e.getFrom().distanceSquared(team.getBaseCenter()) >= main.bwConfig.healpoolradius)
                ) {
                    if (team.contains(e.getPlayer()) && team.getHealpool() && !e.getPlayer().hasPotionEffect(PotionEffectType.REGENERATION)) {
                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, true, true), true);
                    }
                } else if ((e.getFrom().distanceSquared(team.getBaseCenter()) < main.bwConfig.healpoolradius) && (e.getTo().distanceSquared(team.getBaseCenter()) >= main.bwConfig.healpoolradius)) {
                    if (team.contains(e.getPlayer()) && team.getHealpool()) {
                        e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
                    }
                }

                if (e.getTo().distanceSquared(team.getBaseCenter()) < main.bwConfig.traptriggerradius) {
                    new TriggerTrap(e.getPlayer(), team, main);
                    break;
                }
            }
        }

        for (EntityLiving npc : main.npcManager.npcs) {
//			On fait bouger la tete des npcs
            if (npc.getBukkitEntity().getLocation().distance(e.getTo()) < 15 && new Utils().getMetadata(e.getPlayer(), "alive").asBoolean()) {
                Player target = e.getPlayer();
                for (Entity entity : npc.getBukkitEntity().getNearbyEntities(15, 15, 15)) {
                    if (entity instanceof Player && entity.getLocation().distance(npc.getBukkitEntity().getLocation()) < target.getLocation().distance(npc.getBukkitEntity().getLocation())) {
                        if (new Utils().getMetadata(e.getPlayer(), "alive").asBoolean()) {
                            target = (Player) entity;
                        }
                    }
                }
                Location npcloc = npc.getBukkitEntity().getLocation();
                npcloc.setDirection(target.getLocation().subtract(npcloc).toVector());
                npc.yaw = npcloc.getYaw();
                npc.pitch = npcloc.getPitch();
//				et la on va envoyer un packet pour changer la direction de la tete a seulement les joeuurs a moins de 30 blocks pour reduire le lag
                for (Entity entity : npc.getBukkitEntity().getNearbyEntities(30, 30, 30)) {
                    if (entity instanceof Player) {
                        PlayerConnection connection = ((CraftPlayer) entity).getHandle().playerConnection;
                        connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte) ((npc.yaw % 360) * 256 / 360), (byte) ((npc.pitch % 360) * 256 / 360), false));
                        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((npc.yaw % 360) * 256 / 360)));
                    }
                }
            }


//			Tant qu'on est la, si le joueur s'approche a plus de 30 blocks du npc, il va se faire respawn
            if (e.getTo().distance(npc.getBukkitEntity().getLocation()) < 90 && e.getFrom().distance(npc.getBukkitEntity().getLocation()) >= 90) {
                main.npcManager.reloadNpc(npc, e.getPlayer());
            }

        }
    }

    //	Ca c'est pour bypass la verification, ainsi que les joueurs morts ne puissent pas faire disparaitre les blocks
    @EventHandler
    public void onBlockCanBuild(BlockCanBuildEvent e) {
        e.setBuildable(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent e) {
        if (e.getForce() < main.bwConfig.minbowcharge && e.getEntity() instanceof Player) {
            e.setCancelled(true);
            ((Player) e.getEntity()).updateInventory();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player)
            ((Player) e.getPlayer()).updateInventory();
    }

    @EventHandler
    public void onEggThrow(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Egg && e.getEntity().getShooter() instanceof Player) {
            Player player = (Player) e.getEntity().getShooter();
//			if (player.getItemInHand().getItemMeta().hasDisplayName()) {
//				if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("bridge egg")) {
            if (utils.getMetadata(player, "lastfb").asLong() < (new SystemClock().currentTimeMillis() - 500)) {
                new bridgeEgg(new Utils().getMetadata(player, "color").asString(), (Egg) e.getEntity(), main, 2);
                utils.setMetadata((Player) e.getEntity().getShooter(), "lastfb", new SystemClock().currentTimeMillis());
            } else {
                player.sendMessage(RED + "Please wait 0.5s to use that again");
                e.setCancelled(true);
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() + 1);
//						e.getEntity().remove();
            }
//				}
//			}
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerTeleportNearNpc(PlayerTeleportEvent e) {
        for (EntityLiving npc : main.npcManager.npcs)
            if (e.getTo().distance(npc.getBukkitEntity().getLocation()) < 90 && e.getFrom().distance(npc.getBukkitEntity().getLocation()) >= 90) {
                main.npcManager.reloadNpc(npc, e.getPlayer());
            }

    }

    @EventHandler
    public void onSignGUIUpdateEvent(SignGUIUpdateEvent e) {
        GameModifUI.onSignGUIUpdateEvent(e, main);
    }

    public void setPlacedBlock(Block b) {
        b.setMetadata("PlacedBlock", new FixedMetadataValue(main, true));
    }

    public boolean isPlacedBlock(Block b) {
        List<MetadataValue> metaDataValues = b.getMetadata("PlacedBlock");
        for (MetadataValue value : metaDataValues) {
            return value.asBoolean();
        }
        return false;
    }

}
