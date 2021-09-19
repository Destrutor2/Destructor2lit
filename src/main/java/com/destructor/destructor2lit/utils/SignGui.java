package com.destructor.destructor2lit.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Client;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.events.SignGUIUpdateEvent;
import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayClientUpdateSign;
import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayServerBlockChange;
import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayServerOpenSignEntity;
import com.destructor.destructor2lit.utils.packetWrappers.WrapperPlayServerUpdateSign;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SignGUI {

    public SignGUI(Main main) {
        this.registerSignUpdateListener(main);
    }

//    public void enable(Main main) {
//        this.registerSignUpdateListener();
//    }

    public void onDisable() {
    }

    public static void openSignEditor(Player player, String[] text) {
        int x = 0;
        int y = 0;
        int z = 0;
        BlockPosition bp = new BlockPosition(x, y, z);
        WrapperPlayServerBlockChange blockChangePacket = new WrapperPlayServerBlockChange();
        WrappedBlockData blockData = WrappedBlockData.createData(Material.SIGN_POST);
        blockChangePacket.setBlockData(blockData);
        blockChangePacket.setLocation(bp);
        blockChangePacket.sendPacket(player);
        WrapperPlayServerUpdateSign updateSignPacket = new WrapperPlayServerUpdateSign();
//        updateSignPacket.setLocation(new BlockPosition(x, y, z));
        WrappedChatComponent[] lines = new WrappedChatComponent[]{WrappedChatComponent.fromText(text[0]), WrappedChatComponent.fromText(text[1]), WrappedChatComponent.fromText(text[2]), WrappedChatComponent.fromText(text[3])};
        updateSignPacket.setLines(lines);
        updateSignPacket.sendPacket(player);
        WrapperPlayServerOpenSignEntity packet = new WrapperPlayServerOpenSignEntity();
        packet.setLocation(new BlockPosition(x, y, z));
        packet.sendPacket(player);
    }

    private void registerSignUpdateListener(Main main) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        manager.addPacketListener(new PacketAdapter(main, new PacketType[]{Client.UPDATE_SIGN}) {
            public void onPacketReceiving(PacketEvent event) {
                String[] text = new String[4];
                Player player = event.getPlayer();
                WrapperPlayClientUpdateSign packet = new WrapperPlayClientUpdateSign(event.getPacket());
                BlockPosition bp = packet.getLocation();
                BlockPosition playerBlockPos = new BlockPosition(0, 0, 0);
                if (playerBlockPos != null && bp.getX() == playerBlockPos.getX() && bp.getY() == playerBlockPos.getY() && bp.getZ() == playerBlockPos.getZ()) {
                    for (int i = 0; i < packet.getLines().length; ++i) {
                        WrappedChatComponent chat = packet.getLines()[i];
                        String str = StringEscapeUtils.unescapeJavaScript(chat.getJson());
                        str = str.substring(1, str.length() - 1);
                        text[i] = str;
                    }

                    WrapperPlayServerBlockChange blockChangePacket = new WrapperPlayServerBlockChange();
                    WrappedBlockData blockData = WrappedBlockData.createData(Material.AIR);
                    blockChangePacket.setBlockData(blockData);
                    blockChangePacket.setLocation(playerBlockPos);
                    blockChangePacket.sendPacket(player);
                    SignGUIUpdateEvent updateEvent = new SignGUIUpdateEvent(player, text);
                    Bukkit.getServer().getPluginManager().callEvent(updateEvent);
                }

            }

            public void onPacketSending(PacketEvent event) {
            }
        });
    }
}
