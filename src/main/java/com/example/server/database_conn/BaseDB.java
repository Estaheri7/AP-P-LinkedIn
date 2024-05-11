package com.example.server.database_conn;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDB {
    protected final Connection conn;

    public BaseDB() throws SQLException {
        this.conn = SQLConnection.getConnection();
        this.createTable();
    }

    abstract void createTable() throws SQLException;
}
