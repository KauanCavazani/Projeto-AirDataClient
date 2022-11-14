package com.airdata.model;

public class User {
   
    private String name;
    private String email;
    private String password;
    private Integer idTower;

    public User(String email, String password) {
        this.name = null;
        this.email = email;
        this.password = password;
        this.idTower = null;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public Integer IdTower() {
        return idTower;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdTower(Integer idTower) {
        this.idTower = idTower;
    }

    public Integer getIdTower() {
        return idTower;
    }

}
