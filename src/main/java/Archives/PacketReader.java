//package com.destructor.destructor2lit;
//
//import com.destructor.destructor2lit.events.NpcClickEvent;
//import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayClientUseEntity;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToMessageDecoder;
//import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
//import org.bukkit.Bukkit;
//import org.bukkit.craftbukkit.libs.joptsimple.internal.Reflection;
//import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
//import org.bukkit.entity.Player;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//public class PacketReader {
//	Channel channel;
//	public static Map<UUID, Channel> channels = new HashMap<>();
//
//	public void inject(Player player, Main main) {
//		CraftPlayer craftPlayer = (CraftPlayer) player;
//		channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
//		channels.put(player.getUniqueId(), channel);
//
//		if (channel.pipeline().get("PacketInjector") != null) {
//			return;
//		}
//
////        Maintenant on fait le genre de truc injection
//		channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
//			@Override
//			protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> list) {
//				list.add(packet);
//				readPacket(player, packet, main);
//
//			}
//		});
//
//		WrapperPlayClientUseEntity wrapperPlayClientUseEntity = new WrapperPlayClientUseEntity();
//
//
//	}
//
//
//	private void readPacket(Player player, PacketPlayInUseEntity packet, Main main) {
//		if (packet.a().name().equalsIgnoreCase("interact") || packet.a().name().equalsIgnoreCase("attack")) {
//			try {
////            On peut choisir a cette ligne si on veut que lorsqu'un joueur click sur un npc avec click droit ou cick gauche ce qu'il se passera...
//				NpcClickEvent event = new NpcClickEvent(player, (int) getValue(packet, "a"), main);
//				Bukkit.getPluginManager().callEvent(event);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	private void setValue(Object instance, String fieldName, Object value) throws Exception {
//		Field field = instance.getClass().getDeclaredField(fieldName);
//		field.setAccessible(true);
//		field.set(instance, value);
//	}
//
//	private Object getValue(Object instance, String fieldName) throws Exception {
//		Field field = instance.getClass().getDeclaredField(fieldName);
//		field.setAccessible(true);
//		return field.get(instance);
//	}
//}
