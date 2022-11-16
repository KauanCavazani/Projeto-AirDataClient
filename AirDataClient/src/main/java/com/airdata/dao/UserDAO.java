package com.airdata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.airdata.model.User;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public List<User> signIn(User user) throws ExceptionDAO {
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        String query = "SELECT nomeUsuario, idTorre FROM vw_iniciarSessao WHERE emailUsuario = '" + user.getEmail() + "' AND senhaUsuario = '" + user.getPassword() + "';";
        
        try {
            
            List userList = null;
            
            connection = new ConnectionDatabase().getConnection();
            
            ps = connection.prepareStatement(query);            
            ResultSet rs = ps.executeQuery(query);
            if(rs != null) {
                userList = new ArrayList<User>();
                
                while(rs.next()) {
                    userList.add(rs.getString("nomeUsuario"));
                    userList.add(rs.getInt("idTorre"));
                }
            }
            
            return userList;
            
        } catch (SQLException e) {
            
            throw new ExceptionDAO("Erro ao realizar o Login: " + e);
            
        } 
         
    }
    
}
