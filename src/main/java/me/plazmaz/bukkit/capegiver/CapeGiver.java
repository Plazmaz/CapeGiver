package me.plazmaz.bukkit.capegiver;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Dylan on 2/24/2016.
 */
public class CapeGiver extends JavaPlugin {
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        CapeObject.init(this);
        getCommand("cape").setExecutor(new CapeCommand());
        protocolManager.addPacketListener(new PacketAdapterCape(this));
        getLogger().info(getDescription().getName() + " v" + getDescription().getName() + " enabled!");
        getLogger().info("Written by DylanBytes (@Plazmaz1)");
    }
}
