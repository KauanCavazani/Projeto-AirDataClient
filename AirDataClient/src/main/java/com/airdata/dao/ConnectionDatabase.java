package com.airdata.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    
    public Connection getConnection() {
        
        Connection conn = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://c28/airData", "airdata_client", "#AirData2022");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return conn;
    }
    
}
