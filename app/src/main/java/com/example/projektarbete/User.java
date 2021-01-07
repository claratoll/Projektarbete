package com.example.projektarbete;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String userName;
    private String user;


    public User (String name, String userName) {
        this.name = name;
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUser (String user){
        this.user = user;
    }

    public String getUser() {
        return "Your saved user: \n" + name + "\n" + userName;
    }

}
