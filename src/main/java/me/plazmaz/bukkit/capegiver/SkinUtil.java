package me.plazmaz.bukkit.capegiver;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;

import java.util.Collections;
import java.util.List;

public class SkinUtil {
    public static WrappedGameProfile getProfileForPacket(PacketContainer packet) {
        if(packet.getType() == Server.PLAYER_INFO) {
            if(packet.getPlayerInfoAction().read(0) == PlayerInfoAction.ADD_PLAYER) {
                List<PlayerInfoData> values = packet.getPlayerInfoDataLists().read(0);
                if(values.size() <= 0) {
                    return null;
                }
                PlayerInfoData playerInfoData = values.get(0);
                return playerInfoData.getProfile();
            }
        }
        return null;

    }

    public static PacketContainer getPlayerInfoDataPacket(ProtocolManager manager, WrappedGameProfile profile) {
        PacketContainer packet = manager.createPacket(Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, PlayerInfoAction.ADD_PLAYER);
        List<PlayerInfoData> info = Collections.singletonList(new PlayerInfoData(profile, 0, NativeGameMode.NOT_SET, profile.getName() != null ? WrappedChatComponent.fromText(profile.getName()) : null));
        packet.getPlayerInfoDataLists().write(0, info);
        return packet;
    }

    public static PacketContainer getPlayerInfoDataRemove(ProtocolManager manager, WrappedGameProfile profile) {
        PacketContainer packet = manager.createPacket(Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, PlayerInfoAction.REMOVE_PLAYER);
        List<PlayerInfoData> info = Collections.singletonList(new PlayerInfoData(profile, 0, NativeGameMode.NOT_SET, WrappedChatComponent.fromText(profile.getName() != null ? profile.getName() : "")));
        packet.getPlayerInfoDataLists().write(0, info);
        return packet;
    }

    public static PacketContainer modifyPacketForSkin(PacketContainer packet, WrappedGameProfile profile) {
        if(packet.getType() == Server.PLAYER_INFO) {
            if(packet.getPlayerInfoAction().read(0) == PlayerInfoAction.ADD_PLAYER) {
                List<PlayerInfoData> values = packet.getPlayerInfoDataLists().read(0);
                PlayerInfoData data = new PlayerInfoData(profile, 0, NativeGameMode.NOT_SET, values.get(0).getDisplayName());
                values.set(0, data);
                packet.getPlayerInfoDataLists().write(0, values);
            }

        }

        return packet;
    }

    public static PacketContainer modifyPacketForSkin(PacketContainer packet, WrappedSignedProperty textures) {
        if(packet.getType() == Server.PLAYER_INFO) {
            if(packet.getPlayerInfoAction().read(0) == PlayerInfoAction.ADD_PLAYER) {
                List<PlayerInfoData> values = packet.getPlayerInfoDataLists().read(0);
                PlayerInfoData playerInfoData = values.get(0);
                WrappedGameProfile profile = playerInfoData.getProfile();
                profile.getProperties().put("textures", textures);
                playerInfoData = new PlayerInfoData(profile, playerInfoData.getPing(), playerInfoData.getGameMode(), playerInfoData.getDisplayName());

                values.set(0, playerInfoData);
                packet.getPlayerInfoDataLists().write(0, values);
            }

        }

        return packet;
    }

    public static PacketContainer modifyPacketForSkin(PacketContainer packet, String blob, String signature) {
        if(packet.getType() == Server.PLAYER_INFO) {
            if(packet.getPlayerInfoAction().read(0) == PlayerInfoAction.ADD_PLAYER) {
                List<PlayerInfoData> values = packet.getPlayerInfoDataLists().read(0);
                PlayerInfoData playerInfoData = values.get(0);
                if(values.size() <= 0) {
                    return packet;
                }

                WrappedGameProfile profile = playerInfoData.getProfile();
                if(profile == null || profile.getName() == null)
                    return packet;
                WrappedGameProfile newProfile = new WrappedGameProfile(profile.getId(), profile.getName());

                if(profile.getProperties() != null && profile.getProperties().containsKey("textures")) {
                    WrappedSignedProperty texture = new WrappedSignedProperty("textures", blob, signature);
                    newProfile.getProperties().put("textures", texture);
                }

                playerInfoData = new PlayerInfoData(newProfile, playerInfoData.getPing(), playerInfoData.getGameMode(), playerInfoData.getDisplayName());

                values.set(0, playerInfoData);
                packet.getPlayerInfoDataLists().write(0, values);
            }
        }

        return packet;
    }

}
