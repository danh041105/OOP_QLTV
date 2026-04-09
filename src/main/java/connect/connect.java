/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class connect {
    private String user ="root";
    private String password ="";
    private String host ="localhost";
    private String port="3306";
    private String dbName ="java_qltv";
    private String url ="jdbc:mysql://"+host+":"+port+"/"+dbName;
    private Connection conn =null;
    
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(conn != null){
                return conn;
            }
            conn = DriverManager.getConnection(url, user, password);
        } catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    public Statement getStatement(){
        Connection conn = this.getConnection();
        Statement stmt = null;
        try {
            if(conn!=null){
                stmt = conn.createStatement();
                return stmt;
            }
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmt;
    }
   public static void main(String[] args) {
      connect connn = new connect();
      if(connn.getConnection()!=null){
          System.out.println("connect");
      } else{
          System.out.println("loi");
      }
   }
}