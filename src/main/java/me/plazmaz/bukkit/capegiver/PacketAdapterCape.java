package me.plazmaz.bukkit.capegiver;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

import java.util.UUID;

/**
 * Created by Dylan on 2/24/2016.
 */
public class PacketAdapterCape extends PacketAdapter {
    public PacketAdapterCape(CapeGiver plugin) {
        super(plugin, ListenerPriority.NORMAL, Server.PLAYER_INFO);
    }
    @Override
    public void onPacketSending(PacketEvent event) {
        if(event.getPacketType() == Server.PLAYER_INFO) {
            PacketContainer packet = event.getPacket();
            WrappedGameProfile profile = SkinUtil.getProfileForPacket(packet);
            if(profile != null) {
                UUID uuid = profile.getUUID();
                if (CapeObject.PLAYER_CAPES.containsKey(uuid)) {
                    CapeObject cObj = CapeObject.PLAYER_CAPES.get(uuid);
                    event.setPacket(SkinUtil.modifyPacketForSkin(packet, cObj.getValue(), cObj.getSignature()));

                }
            }
        }
    }
}
