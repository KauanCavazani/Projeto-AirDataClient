package com.airdata.dao;

import com.airdata.model.Server;
import com.airdata.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerDAO {
    
    public List<Server> getServer(Server server) throws ExceptionDAO {
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        String query = "SELECT * FROM vw_getServidor WHERE idServidor = '" + server.getMacAddress() + "';";
        
        try {
            
            List serverComponentList = null;
            
            connection = new ConnectionDatabase().getConnection();
            
            ps = connection.prepareStatement(query);            
            ResultSet rs = ps.executeQuery(query);
            if(rs != null) {
                serverComponentList = new ArrayList<User>();
                
                while(rs.next()) {
                    serverComponentList.add(rs.getInt("idComponente"));
                }
            }
            
            return serverComponentList;
            
        } catch (SQLException e) {
            
            throw new ExceptionDAO("Erro ao receber os id's dos componentes: " + e);
            
        } finally {
            
            try {
            
                if(ps != null) { ps.close(); }

            } catch (SQLException e) {
            
                throw new ExceptionDAO("Erro ao fechar o Statement: " + e);
            
            } try {
            
                if(connection != null) { connection.close(); }

            } catch (SQLException e) {
            
                throw new ExceptionDAO("Erro ao fechar a conexão: " + e);
            
            }
   
        }
         
    }
    
    public void saveData(Integer idMetric, Integer idComponent, Integer value, String macAddress) throws ExceptionDAO {
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        String query = String.format("INSERT INTO leitura (fkMetrica, horario, valorLido, fkComponente_idComponente, fkComponente_fkServidor) VALUES (%d, now(), %d, %d, '%s');",
                idMetric, value, idComponent, macAddress);
        
        System.out.println(query);
        
        try {
            
            connection = new ConnectionDatabase().getConnection();
            
            ps = connection.prepareStatement(query);   
       
            ps.execute();
            
            
        } catch (SQLException e) {
            
            throw new ExceptionDAO("Erro ao salvar as leituras: " + e);
            
        } finally {
            
            try {
            
                if(ps != null) { ps.close(); }

            } catch (SQLException e) {
            
                throw new ExceptionDAO("Erro ao fechar o Statement: " + e);
            
            } try {
            
                if(connection != null) { connection.close(); }

            } catch (SQLException e) {
            
                throw new ExceptionDAO("Erro ao fechar a conexão: " + e);
            
            }
   
        }
         
    }
 
}
