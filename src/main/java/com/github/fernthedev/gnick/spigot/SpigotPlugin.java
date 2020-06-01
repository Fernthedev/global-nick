package com.github.fernthedev.gnick.spigot;

import com.github.fernthedev.config.common.Config;
import com.github.fernthedev.config.gson.GsonConfig;
import com.github.fernthedev.fernapi.server.spigot.FernSpigotAPI;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.gnick.core.DBManager;
import com.github.fernthedev.gnick.core.MySQLData;
import com.github.fernthedev.gnick.core.NickNetworkManager;

import java.io.File;

public class SpigotPlugin extends FernSpigotAPI {

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
        getServer().getPluginManager().registerEvents(new NickManager(), this);
        Universal.getMessageHandler().registerMessageHandler(new NickNetworkManager());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
