package com.github.fernthedev.gnick.core;

import com.github.fernthedev.fernapi.universal.data.database.DatabaseAuthInfo;
import lombok.Getter;

public class MySQLData {

    @Getter
    private DatabaseAuthInfo databaseAuthInfo = new DatabaseAuthInfo(
            "root",
            "pass",
            "3306",
            "localhost",
            "database");

}
