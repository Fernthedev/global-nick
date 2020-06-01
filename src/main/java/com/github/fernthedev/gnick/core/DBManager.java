package com.github.fernthedev.gnick.core;

import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.data.database.DatabaseAuthInfo;
import com.github.fernthedev.fernapi.universal.mysql.DatabaseManager;


public class DBManager extends DatabaseManager {
    public DBManager(String username, String password, String port, String URLHost, String database) {
        connect(new DatabaseAuthInfo(username, password, port, URLHost, database));
    }

    public DBManager(DatabaseAuthInfo authInfo) {
        connect(authInfo);
    }


    /**
     * This is called after you attempt a connection
     *
     * @param connected Returns true if successful
     * @see DatabaseManager#connect(DatabaseAuthInfo)
     */
    @Override
    public void onConnectAttempt(boolean connected) {
        if(connected) {
            Universal.getMethods().getLogger().info("Connected successfully");
        }else{
            Universal.getMethods().getLogger().warning("Unable to connect successfully");
        }
    }
}
