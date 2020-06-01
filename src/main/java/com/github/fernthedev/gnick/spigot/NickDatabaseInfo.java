package com.github.fernthedev.gnick.spigot;

import com.github.fernthedev.fernapi.universal.data.database.ColumnData;
import com.github.fernthedev.fernapi.universal.data.database.RowDataTemplate;
import com.github.fernthedev.fernapi.universal.data.database.TableInfo;

public class NickDatabaseInfo extends TableInfo {

    private static RowDataTemplate rowDataTemplate = new RowDataTemplate(
            new ColumnData("PLAYERUUID", ""),
            new ColumnData("NICK", ""));

    public NickDatabaseInfo() {
        super("fern_nicks", rowDataTemplate);
    }
}
