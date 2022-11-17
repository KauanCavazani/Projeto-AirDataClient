package com.airdata.dao;

import com.airdata.model.Server;
import com.airdata.model.User;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServerDAO {
    
    public List<Server> getServer(Server server) throws ExceptionDAO {
        
        // Connection connectionMySql = null;
        Connection connectionSqlServer = null;
        //PreparedStatement ps = null;
        Statement statement = null;
        
        String query = "SELECT * FROM vw_getServidor WHERE idServidor = '" + server.getMacAddress() + "';";
        
        try {
            
            List serverComponentList = null;
            
            // connectionMySql = new ConnectionDatabase().getConnectionMYSQL();
            connectionSqlServer = new ConnectionDatabase().getConnectionSQLServer();
            
            statement = connectionSqlServer.createStatement();            
            ResultSet rs = statement.executeQuery(query);
            if(rs != null) {
                serverComponentList = new ArrayList<User>();
                
                while(rs.next()) {
                    serverComponentList.add(rs.getInt("idComponente"));
                }
            }
            
            return serverComponentList;
            
        } catch (SQLException e) {
            
            throw new ExceptionDAO("Erro ao receber os id's dos componentes: " + e);
            
        } 
    }
    
    public void saveData(Integer idMetric, Integer idComponent, Integer value, String macAddress) throws ExceptionDAO, IOException {
        
        Connection connectionMySql = null;
        Connection connectionSqlServer = null;
        PreparedStatement ps = null;
        Statement statement = null;
        
        String query = String.format("INSERT INTO leitura (fkMetrica, horario, valorLido, fkComponente_idComponente, fkComponente_fkServidor) VALUES (%d, now(), %d, %d, '%s');",
                idMetric, value, idComponent, macAddress);
        
        String querySqlServer = String.format("INSERT INTO leitura (fkMetrica, horario, valorLido, fkComponente_idComponente, fkComponente_fkServidor) VALUES (%d, GETDATE(), %d, %d, '%s');",
                idMetric, value, idComponent, macAddress);
        
        System.out.println("Executando...");
        
        try {
            
            connectionMySql = new ConnectionDatabase().getConnectionMYSQL();
            connectionSqlServer = new ConnectionDatabase().getConnectionSQLServer();
            
            ps = connectionMySql.prepareStatement(query);  
            statement = connectionSqlServer.createStatement();  
       
            ps.execute();
            statement.execute(querySqlServer);
            
            
        } catch (SQLException e) {
            
            throw new ExceptionDAO("Erro ao salvar as leituras: " + e);
            
        } 
    }
 
}
