package com.github.fernthedev.gnick.bungee;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandIssuer;
import co.aikar.commands.MessageKeys;
import co.aikar.commands.MessageType;
import co.aikar.commands.annotation.*;
import com.github.fernthedev.fernapi.universal.Universal;
import com.github.fernthedev.fernapi.universal.api.IFPlayer;
import com.github.fernthedev.fernapi.universal.data.database.ColumnData;
import com.github.fernthedev.fernapi.universal.data.database.RowData;
import com.github.fernthedev.fernapi.universal.data.network.PluginMessageData;
import com.github.fernthedev.fernapi.universal.exceptions.database.DatabaseException;
import com.github.fernthedev.fernapi.universal.mysql.DatabaseManager;
import com.github.fernthedev.gnick.core.Channels;
import com.github.fernthedev.gnick.core.NickNetworkManager;
import com.github.fernthedev.gnick.spigot.NickDatabaseInfo;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

@CommandAlias("fnick|gnick|nick|fnick")
public class FernNick extends BaseCommand {
    public FernNick() {

        setupTable();
    }

    private static NickDatabaseInfo databaseInfo;

    private void setupTable() {
        DatabaseManager databaseManager = NickNetworkManager.DATABASE_MANAGER;
        try {
            databaseInfo = (NickDatabaseInfo) new NickDatabaseInfo().getFromDatabase(databaseManager);
            databaseManager.createTable(databaseInfo);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
//            Statement statement = DatabaseHandler.statement();
//            if(statement != null) {
//                //statement.executeUpdate("CREATE TABLE IF NOT EXISTS fern_nicks (PlayerUUID varchar(200), com.github.fernthedev.gnick.spigot.nick varchar(40))");
//                String sql = "CREATE TABLE IF NOT EXISTS fern_nicks(PLAYERUUID varchar(200), NICK varchar(40));";
//
//                PreparedStatement stmt = DatabaseHandler.getConnection().prepareStatement(sql);
//
//                stmt.executeUpdate();
//            }
    }

    @Description("Change nickname using MySQL")
    @CommandPermission("fernc.nick")
    @HelpCommand
    @Default
    @CommandCompletion("* *")
    public void onNick(CommandIssuer sender, @CommandPermission("fernc.nick.others") @Optional IFPlayer<?> player, String newNick) {

//        Connection connection = DatabaseHandler.getConnection();


        DatabaseManager databaseManager = NickNetworkManager.DATABASE_MANAGER;


        if (!sender.isPlayer() && player == null) {
            sender.sendError(MessageKeys.PLEASE_SPECIFY_ONE_OF, "{valid}", "player");
            return;
        }

        if (player == null) player = (IFPlayer<?>) sender;

        if (newNick.contains("&") && !sender.hasPermission("fernc.nick.color")) {

            sender.sendError(MessageKeys.PERMISSION_DENIED_PARAMETER);
//            sendMessage(sender, "&cYou do not have permissions to use color nicknames.");
            return;
        }

        try {
//                if(connection != null) {

            if (databaseManager.isConnected()) {
                databaseInfo = (NickDatabaseInfo) new NickDatabaseInfo().getFromDatabase(databaseManager);
                applyNick(player, newNick, databaseManager);

//                    ProxyServer.getInstance().getServers().values().forEach(serverInfo -> serverInfo.sendData("BungeeCord", outputStream.toByteArray()));


                //sendMessage(sender,"&aSuccessfully set your com.github.fernthedev.gnick.spigot.nick to " + args[0]);
                //sendMessage(sender,"&aPlease relog for this to take effect.");
            } else {
                sender.sendMessage(MessageType.ERROR, MessageKeys.ERROR_GENERIC_LOGGED);
                throw new SQLException("Connection is null");
            }
        } catch (SQLException | ClassNotFoundException e) {
            sender.sendMessage(MessageType.ERROR, MessageKeys.ERROR_GENERIC_LOGGED);
            e.printStackTrace();
        }
    }

    private void applyNick(IFPlayer<?> player, String newNick, DatabaseManager databaseManager) throws DatabaseException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String formattedUUID = player.getUuid().toString();

        databaseManager.removeRowIfColumnContainsValue(databaseInfo, "PLAYERUUID", formattedUUID);


////                    sql = "SELECT CASE WHEN PLAYERUUID="+player.getUniqueId().toString()+ "THEN NICK "+args[0];
//                    sql = "UPDATE fern_nicks SET PLAYERUUID='" + player.getUniqueId().toString().replaceAll("-", "") + "NICK='" + args[0] + "' WHERE PLAYERUUID='" + player.getUniqueId().toString().replaceAll("-", "") + "';";
        ColumnData playerColumn = new ColumnData("PLAYERUUID", player.getUuid().toString());
        ColumnData nickColumn = new ColumnData("NICK", newNick);
        RowData rowData = new RowData(playerColumn, nickColumn);
        databaseManager.insertIntoTable(databaseInfo, rowData);

//                    stmt = connection.prepareStatement(sql);
//
//                    connection.prepareStatement
//                            ("INSERT INTO fern_nicks (PLAYERUUID, NICK) VALUES ('" +
//                                    ProxyServer.getInstance().getPlayer(sender.getName()).getUniqueId().toString().replaceAll("-", "") + "','" + args[0] + "');").executeUpdate();


//
//                    out.writeUTF("Forward"); //TYPE wwwwwwwwwwww
//                    out.writeUTF("ALL"); //SERVER
//                    out.writeUTF("ReloadNickSQL"); //SUBCHANNEL
//
//                    out.writeUTF(player.getName()); //NAME
//                    out.writeUTF(player.getUniqueId().toString().replaceAll("-", "")); //UUID wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww

        //                    Universal.getMethods().getLogger().info(outputStream.toString() + " is the info sent.");


        PluginMessageData data = new PluginMessageData(stream, player.getCurrentServerName(), Channels.NICK_RELOADNICKSQL, Channels.NICK_CHANNEL);

        data.addData(player.getName());
        data.addData(player.getUuid().toString());

        Universal.getMessageHandler().sendPluginData(data);
    }
}
