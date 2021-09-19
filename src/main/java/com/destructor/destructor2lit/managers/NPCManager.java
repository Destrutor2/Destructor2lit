package com.destructor.destructor2lit.managers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayServerPlayerInfo;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NPCManager {
	MinecraftServer nmsServer;
	public List<Integer> shops = new ArrayList<>();
	public List<Integer> upgrades = new ArrayList<>();
	public List<EntityLiving> npcs = new ArrayList<>();


	public NPCManager() {
		nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
	}

	public void createNPC(Player player, String[] args, Main main) {
		WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
		PlayerInteractManager playerInteractManager = new PlayerInteractManager(nmsWorld);
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "npc");
		EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, playerInteractManager);
		npc.setLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
		Player npcPlayer = npc.getBukkitEntity().getPlayer();
		npcPlayer.setPlayerListName("");
		npcPlayer.setGameMode(GameMode.CREATIVE);
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
		main.getConfig().createSection("npcs." + gameProfile.getId());
		main.saveConfig();
		main.getConfig().set("npcs." + gameProfile.getId() + ".x", npcPlayer.getLocation().getX());
		main.getConfig().set("npcs." + gameProfile.getId() + ".y", npcPlayer.getLocation().getY());
		main.getConfig().set("npcs." + gameProfile.getId() + ".z", npcPlayer.getLocation().getZ());
		main.getConfig().set("npcs." + gameProfile.getId() + ".yaw", npcPlayer.getLocation().getYaw());
		main.getConfig().set("npcs." + gameProfile.getId() + ".pitch", npcPlayer.getLocation().getPitch());
		if (args.length == 2) {

			if (args[1].equalsIgnoreCase("shop")) {
				main.getConfig().set("npcs." + gameProfile.getId() + ".type", "shop");
				player.sendMessage(ChatColor.GREEN + "Le npc " + ChatColor.WHITE + gameProfile.getId().toString() + ChatColor.GREEN + " a été créé avec le type " + ChatColor.WHITE + "shop");
			} else if (args[1].equalsIgnoreCase("upgrade")) {
				main.getConfig().set("npcs." + gameProfile.getId() + ".type", "upgrade");
				player.sendMessage(ChatColor.GREEN + "Le npc " + ChatColor.WHITE + gameProfile.getId().toString() + ChatColor.GREEN + " a été créé avec le type " + ChatColor.WHITE + "upgrade");

			} else {
				player.sendMessage(ChatColor.RED + "Le deuxième argument doit être shop ou upgrade!");
				return;
			}
		} else {
			player.sendMessage(ChatColor.RED + "Nombre d'argument incorrect!");
			return;
		}
		main.saveConfig();
	}

	public void showNpcs(Main main, List<Player> players) {
		if (main.getConfig().getConfigurationSection("npcs") != null && players.size() != 0) {


			for (String npcUUID : main.getConfig().getConfigurationSection("npcs").getKeys(false)) {
				WorldServer nmsWorld = ((CraftWorld) players.get(0).getWorld()).getHandle();
				GameProfile gameProfile = new GameProfile(UUID.fromString(npcUUID), "npc");
				PlayerInteractManager playerInteractManager = new PlayerInteractManager(nmsWorld);
				EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, playerInteractManager);
				npc.setLocation(
						main.getConfig().getDouble("npcs." + npcUUID + ".x"),
						main.getConfig().getDouble("npcs." + npcUUID + ".y"),
						main.getConfig().getDouble("npcs." + npcUUID + ".z"),
						(float) main.getConfig().getDouble("npcs." + npcUUID + ".yaw"),
						(float) main.getConfig().getDouble("npcs." + npcUUID + ".pitch"));
//				Player npcPlayer = npc.getBukkitEntity().getPlayer();
//				npcPlayer.setPlayerListName("");
//				npcPlayer.setGameMode(GameMode.CREATIVE);
				if (!npcs.contains(npc)) {
					npcs.add(npc);
				}
	/*			EntityArmorStand invisArmorStand=new EntityArmorStand(npc.world,npc.lastX,npc.lastY,npc.lastZ);
				invisArmorStand.setInvisible(true);
				invisArmorStand.setCustomNameVisible(false);*/


//				Un peu de code volé ;)
				//Creating the team
				ScoreboardTeam team = new ScoreboardTeam(((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), npc.getName());

				//Setting name visibility
				team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);

				for (Player p : players) {
					PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
					connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
					connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

//					test
//					new BukkitRunnable() {
//						@Override
//						public void run() {
//							connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
//						}
//					}.runTaskTimer(main,0,20);


//					Deuxieme partie du code vole
					//Remove the Team (i assume so if it exists)
					connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
					//Creating the Team
					connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
					//Adding players to the team (You have to use the NPC's name, and add it to a list)
					connection.sendPacket(new PacketPlayOutScoreboardTeam(team, new ArrayList<String>() {{
						add(npc.getName());
					}}, 3));
//					connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
				}

				if (main.getConfig().getString("npcs." + npcUUID + ".type").equalsIgnoreCase("shop")) {
					shops.add(npc.getId());
				}
				if (main.getConfig().getString("npcs." + npcUUID + ".type").equalsIgnoreCase("upgrade")) {
					upgrades.add(npc.getId());
				}
		/*		new BukkitRunnable() {
					@Override
					public void run() {
						for (Player p : players) {
							PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
							connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte) ((npc.yaw % 360.) * 256 / 360), (byte) ((npc.pitch % 360.) * 256 / 360), false));
							connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((npc.yaw % 360.) * 256 / 360)));
						}
					}
				}.runTaskTimer(main, 0, 2);*/
			}

		} else {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.isOp()) {
					p.sendMessage(ChatColor.RED + "Impossible d'aficher les NPCs car aucun n'a été configuré! " + ChatColor.GREEN + "[/bedwars addnpc shop/upgrade]" + ChatColor.RED + " pour en ajouter");
				}
			}
		}
	}

	public void showNpcs(Main main, Player player) {
		ArrayList<Player> players = new ArrayList<>();
		players.add(player);
		showNpcs(main, players);
	}

	public void reloadNpc(EntityLiving npc, Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		if (npc instanceof EntityPlayer)
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn((EntityHuman) npc));
//		else
//			connection.sendPacket(new PacketPlayOutSpawnEntity(npc,npc.getId()));

	}

	public void removeAllNpcs(Main main) {
		for (EntityLiving npc : npcs) {
			PacketContainer packet = main.protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
			packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
			List<PlayerInfoData> dataList = new ArrayList<>();
			dataList.add(new PlayerInfoData(new WrappedGameProfile(npc.getUniqueID(), npc.getName()), 1, EnumWrappers.NativeGameMode.CREATIVE, WrappedChatComponent.fromText(npc.getName())));
			packet.getPlayerInfoDataLists().write(0, dataList);
			main.protocolManager.broadcastServerPacket(packet);
			packet = main.protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
			packet.getIntegerArrays().write(0, new int[]{npc.getId()});
			main.protocolManager.broadcastServerPacket(packet);
//			for (Player player : Bukkit.getOnlinePlayers()) {
//				try {
//					main.protocolManager.sendServerPacket(player, packet);
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

	public void addEntity(EntityLiving entityLiving) {
		npcs.add(entityLiving);
	}


}