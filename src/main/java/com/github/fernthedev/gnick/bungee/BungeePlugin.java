package com.github.fernthedev.gnick.bungee;

import com.github.fernthedev.config.common.Config;
import com.github.fernthedev.config.gson.GsonConfig;
import com.github.fernthedev.fernapi.server.bungee.FernBungeeAPI;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.gnick.core.DBManager;
import com.github.fernthedev.gnick.core.MySQLData;
import com.github.fernthedev.gnick.core.NickNetworkManager;

import java.io.File;

public class BungeePlugin extends FernBungeeAPI {

    private Config<MySQLData> mySQLDataConfig;

    @Override
    public void onEnable() {
        super.onEnable();

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }


//            FernCommands.getInstance().addMessageListener(new NickManager());

        mySQLDataConfig = new GsonConfig<>(new MySQLData(), new File(getDataFolder(), "mysql.json"));
        NickNetworkManager.DATABASE_MANAGER = new DBManager(mySQLDataConfig.getConfigData().getDatabaseAuthInfo());

        Universal.getCommandHandler().registerCommand(new FernNick());
        Universal.getMessageHandler().registerMessageHandler(new NickNetworkManager());
        getLogger().info("Registered fern nicks bungee channels.");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
