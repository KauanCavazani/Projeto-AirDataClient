package com.airdata.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    
    public Connection getConnectionMYSQL() {
        
        Connection conn = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://3.236.141.100/airData", "airdata_client", "#AirData2022");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return conn;
    }
    
    public Connection getConnectionSQLServer() {
        
        Connection conn = null;
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://airdataserver.database.windows.net:1433;database=airdata;user=CloudSA9549f82c@airdataserver;password=pi-airdata2022;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return conn;
    }
    
}
