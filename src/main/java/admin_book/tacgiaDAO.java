/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin_book;

import connect.connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class tacgiaDAO {
    public static List<tacgia> getAll() {
        List<tacgia> list = new ArrayList<>();
        connect db = new connect();
        Connection conn = db.getConnection();
        String sql = "select * from tac_gia";
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    list.add(new tacgia( rs.getInt("ma_tg"),rs.getString("ten_tg"),rs.getString("ngay_sinh"),rs.getString("gioi_tinh"),rs.getString("que"),rs.getString("tieu_su"),rs.getString("hinh")));
                }
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
        }finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return list;
    }
    public static boolean insert(tacgia tg){
        connect db = new connect();
        Connection conn = db.getConnection();
        String sql="insert into tac_gia(ten_tg, ngay_sinh, gioi_tinh, que, tieu_su, hinh) values ('"+tg.getTen_tg()+"','"+tg.getNgay_sinh()+"','"+tg.getGioi_tinh()+"','"+tg.getQue()+"','"+tg.getTieu_su()+"','"+tg.getHinh()+"')";
        System.out.println(sql);
        try {
            if(conn!=null){
                Statement stmt = conn.createStatement();
                int i = stmt.executeUpdate(sql);
                if(i>0){
                    return true;
                }else{
                    return false;
                }
            }else{
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
        }finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return false;
    }
    public static boolean remove(int ma_tg ){
        connect db = new connect();
        Connection conn = db.getConnection();
        
        try {
            if(conn!=null){
                Statement stmt = conn.createStatement();
                String check = "select count(*) as kiemtra from sach where ma_tg= "+ma_tg;
                ResultSet rs = stmt.executeQuery(check);
                if(rs.next()&& rs.getInt("kiemtra")>0){
                    System.out.println("tác giả có sách, không thể xóa");
                    return false;
                }
                
                String sql = "delete from tac_gia where ma_tg = "+ma_tg;
                int i = stmt.executeUpdate(sql);
                if(i>0){
                    return true;
                }else{
                    return false;
                }
            }else{
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
        }finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return false;
    }
    public static boolean update( tacgia tg){
        connect db = new connect();
        Connection conn = db.getConnection();
        String sql = "update tac_gia set ten_tg ='"+tg.getTen_tg()+"',ngay_sinh='"+tg.getNgay_sinh()+"',gioi_tinh='"+tg.getGioi_tinh()+"',que='"+tg.getQue()+"',tieu_su='"+tg.getTieu_su()+"',hinh='"+tg.getHinh()+"' where ma_tg ="+tg.getMa_tg()+"";
        System.out.println(sql);
        try {
            if(conn!=null){
                Statement stmt = conn.createStatement();
                int i = stmt.executeUpdate(sql);
                if(i>0){
                    return true;
                }else{
                    return false;
                }
            }else{
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
        }finally{
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
        return false;
        
    }
    public static List<tacgia> searchByName(String name) {
        List<tacgia> list = new ArrayList<>();
        connect db = new connect();
        Connection conn = db.getConnection();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                String sql = "SELECT * FROM tac_gia WHERE ten_tg LIKE '%" + name + "%'";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    tacgia tg = new tacgia(
                        rs.getInt("ma_tg"),
                        rs.getString("ten_tg"),
                        rs.getString("ngay_sinh"),
                        rs.getString("gioi_tinh"),
                        rs.getString("que"),
                        rs.getString("tieu_su"),
                        rs.getString("hinh")
                    );
                    list.add(tg);
                }
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
}

    public static tacgia findById(int matg) {
        tacgia tg = null;
        String sql = "SELECT * FROM tac_gia WHERE ma_tg = " + matg; // nối trực tiếp vào câu lệnh
        connect db = new connect();

        try (Connection conn = db.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                tg = new tacgia(
                    rs.getInt("ma_tg"),
                    rs.getString("ten_tg"),
                    rs.getString("ngay_sinh"),
                    rs.getString("gioi_tinh"),
                    rs.getString("que"),
                    rs.getString("tieu_su"),
                    rs.getString("hinh")
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tg;
}
    public static int getMaByTen(String tenTG) {
        int ma = -1;
        connect db = new connect();
        String sql = "SELECT ma_tg FROM tac_gia WHERE ten_tg='" + tenTG + "'";
        try (Connection conn = db.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                ma = rs.getInt("ma_tg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ma;
}

}
