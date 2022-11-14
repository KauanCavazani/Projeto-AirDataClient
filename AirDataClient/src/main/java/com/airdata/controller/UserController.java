package com.airdata.controller;

import com.airdata.dao.ExceptionDAO;
import com.airdata.model.User;
import com.airdata.dao.UserDAO;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserController {
    
    public User signIn(String email, String password) throws ExceptionDAO {
                
        if(validateEmail(email) && validatePassword(password)) {
            
            password = encryptPassword(password);
            
            User user = new User(email, password);
            UserDAO userDAO = new UserDAO();
            
            List userList = userDAO.signIn(user);
            
            if(!userList.isEmpty()) {
                
                user.setName((String) userList.get(0));
                user.setIdTower((Integer) userList.get(1));
                return user;
                
            }
            
        }
        
        return null;
    }
    
    private Boolean validateEmail(String email) {
        
        if(email != null && email.length() > 0) {
            if(email.contains("@") && (email.endsWith(".com") || email.endsWith(".br"))) {
                return true;
            }
        }
        
        return false;
    }
    
    private Boolean validatePassword(String password) {
        return password != null && password.length() > 0;
    }
    
    private String encryptPassword(String password) {
        String passwordEncrypt = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            passwordEncrypt = sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordEncrypt;
    }
    
}
