package connect;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
    private static final Logger logger = Logger.getLogger(Connect.class.getName());
    private static final String user ="root";
    private static final String password ="Khai127@11a3";
    private static final String host ="localhost";
    private static final String port="3306";
    private static final String dbName ="java_qltv";
    private static final String url ="jdbc:mysql://"+host+":"+port+"/"+dbName;
    public static Connection getConnection(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch(Exception e){
            logger.log(Level.SEVERE, "Lỗi khi kết nối database", e);
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
            logger.log(Level.SEVERE, "Lỗi khi tạo Statement", e);
        }
        return stmt;
    }
    public static void main(String[] args) {
        Connect connn = new Connect();
        if(connn.getConnection()!=null){
            System.out.println("connect");
        } else{
            System.out.println("loi");
        }
    }
}