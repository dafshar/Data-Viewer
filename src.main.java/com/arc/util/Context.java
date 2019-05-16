package com.arc.util;

import java.sql.Connection;

import com.arc.controllers.AccountScreenController;



public class Context {
    private final static Context instance = new Context();
    public static Context getInstance() {
        return instance;
    }

    private Connection con;
    public void setConnection(Connection con)
    {
        this.con=con;
    }
    public Connection getConnection() {
        return con;
    }

    private AccountScreenController accountScreenController;
    public void setAccountScreenController(AccountScreenController accountScreenController) {
        this.accountScreenController=accountScreenController;
    }

    public AccountScreenController getAccountScreenController() {
        return accountScreenController;
    }
    

}