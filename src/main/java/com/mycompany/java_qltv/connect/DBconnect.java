/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_qltv.connect;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ADMIN
 */
public class DBconnect {
    private static final String URL = "jdbc:mysql://localhost:3306/java_qltv";
    private static final String userName = "root";
    private static final String password = "";
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void main(String[] args) {
        Connection conn = getConnection();
        if(conn != null) {
            System.out.println("Ok");
        } else {
            System.out.println("No");
        }
    }
}
