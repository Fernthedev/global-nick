package com.github.fernthedev.gnick.core;

import com.github.fernthedev.fernapi.universal.data.network.Channel;
import com.github.fernthedev.fernapi.universal.mysql.DatabaseManager;
import lombok.Getter;

public class Channels {

    private Channels() {
    }




    public static final Channel NICK_CHANNEL = new Channel("ferncommands", "nick", Channel.ChannelAction.BOTH);
    public static final String NICK_RELOADNICKSQL = "ReloadNickSQL";

}
