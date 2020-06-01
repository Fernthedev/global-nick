package com.github.fernthedev.gnick.bungee;

import com.github.fernthedev.fernapi.server.bungee.FernBungeeAPI;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.gnick.core.NickNetworkManager;

public class BungeePlugin extends FernBungeeAPI {

    @Override
    public void onEnable() {
        super.onEnable();

        Universal.getMessageHandler().registerMessageHandler(new NickNetworkManager());
        getLogger().info("Registered fern nicks bungee channels.");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
