package com.github.fernthedev.gnick.core;


import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.data.database.RowData;
import com.github.fernthedev.fernapi.universal.data.network.Channel;
import com.github.fernthedev.fernapi.universal.data.network.PluginMessageData;
import com.github.fernthedev.fernapi.universal.handlers.PluginMessageHandler;
import com.github.fernthedev.fernapi.universal.handlers.ServerType;
import com.github.fernthedev.fernapi.universal.mysql.DatabaseManager;
import com.github.fernthedev.gnick.spigot.NickDatabaseInfo;
import com.github.fernthedev.gnick.spigot.NickManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NickNetworkManager extends PluginMessageHandler {

    public static DatabaseManager DATABASE_MANAGER;

    /**
     * This is the channel name that will be registered incoming and outgoing
     *
     * @return The channels that will be incoming and outgoing
     */
    @Override
    public List<Channel> getChannels() {
        List<Channel> channels = new ArrayList<>();
        channels.add(Channels.NICK_CHANNEL);
        return channels;
    }


    private static NickDatabaseInfo databaseInfo;

    private void runSqlSync() {
        DATABASE_MANAGER.runOnConnect(() -> {
            try {
                databaseInfo = (NickDatabaseInfo) new NickDatabaseInfo().getFromDatabase(DATABASE_MANAGER);

                Queue<RowData> rowDataStack = new LinkedList<>(databaseInfo.getRowDataList());

                while(!rowDataStack.isEmpty()) {
                    RowData rowData = rowDataStack.remove();
                    String uuid = rowData.getColumn("PLAYERUUID").getValue();
                    String nick = rowData.getColumn("NICK").getValue();
                }

//                String sql = "SELECT * FROM fern_nicks;";
//                PreparedStatement stmt = DatabaseHandler.getConnection().prepareStatement(sql);
//                ResultSet result = stmt.executeQuery();
//
//                while (result.next()) {
//                    String uUID = result.getString("PLAYERUUID");
//                    String com.github.fernthedev.gnick.spigot.nick = result.getString("NICK");
//                    nicknames.put(uUID,com.github.fernthedev.gnick.spigot.nick);
//                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onMessageReceived(PluginMessageData pluginMessageData, Channel channel) {
        if (Universal.getMethods().getServerType() == ServerType.BUKKIT) {
            try {
                String type = pluginMessageData.getProxyChannelType(); //TYPE
                String server = pluginMessageData.getServer(); // Server
                String subChannel = pluginMessageData.getSubChannel(); // Subchannel

                Queue<String> dataList = new LinkedList<>(pluginMessageData.getExtraData());

                if (subChannel.equalsIgnoreCase(Channels.NICK_RELOADNICKSQL)) {
                    String playerName = dataList.remove();
                    String uuid = dataList.remove();

                    runSqlSync();

                    String nick = null;

                    for (RowData rowData : databaseInfo.getRowDataList()) {
                        if (rowData.getColumn("PLAYERUUID").getValue() == null) continue;

                        if (rowData.getColumn("PLAYERUUID").getValue().equals(uuid)) {
                            nick = rowData.getColumn("NICK").getValue();
                        }
                    }


                    NickManager.handleNick(uuid, playerName, nick);


                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
