/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ahmed_jemai
 */
public class MyDB {
    
    private String url = "jdbc:mysql://localhost/gymdegym";
    private String user = "root";
    private String pass = "";
    private Connection connection;
    static MyDB instance;

  public MyDB(){
        try {
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("connection etablit avec sucess");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static MyDB getInstance(){
        if(instance == null)
            instance = new MyDB();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    
    
}
