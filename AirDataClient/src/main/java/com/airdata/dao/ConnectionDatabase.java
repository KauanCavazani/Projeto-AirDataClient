package com.airdata.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    
    public Connection getConnectionMYSQL() throws IOException {
        
        Connection conn = null;
        String ipv4 = getIpv4();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + ipv4 + "/airData", "root", "urubu100");
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
    
    private String getIpv4() throws IOException {
        
        String urlString = "http://checkip.amazonaws.com/";
        URL url = new URL(urlString);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
        
            return br.readLine();
        }
    }
    
}
