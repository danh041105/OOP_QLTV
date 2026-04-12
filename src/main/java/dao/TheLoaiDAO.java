/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.Connect;
import model.TheLoai;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TheLoaiDAO {
    public static List<TheLoai> getAll() {
        List<TheLoai> list = new ArrayList<>();
        Connect db = new Connect();
        Connection conn = db.getConnection();
        String sql = "select * from loai_sach";
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    list.add(new TheLoai(
                        rs.getString("ma_loai_sach"),
                        rs.getString("ten_loai_sach")
                    ));
                }
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
        return list;
    }

    public static boolean insert(TheLoai tl) {
        Connect db = new Connect();
        Connection conn = db.getConnection();
        String sql = "insert into loai_sach(ma_loai_sach, ten_loai_sach) values ('"
                + tl.getMa_loai_sach() + "','" + tl.getTen_loai_sach() + "')";
        System.out.println(sql);
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                int i = stmt.executeUpdate(sql);
                if (i > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
        return false;
    }

    public static boolean remove(String ma_loai_sach) {
        Connect db = new Connect();
        Connection conn = db.getConnection();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                String check = "select count(*) as kiemtra from sach where ma_loai_sach = '" + ma_loai_sach + "'";
                ResultSet rs = stmt.executeQuery(check);
                if (rs.next() && rs.getInt("kiemtra") > 0) {
                    System.out.println("Thể loại có sách, không thể xóa");
                    return false;
                }

                String sql = "delete from loai_sach where ma_loai_sach = '" + ma_loai_sach + "'";
                int i = stmt.executeUpdate(sql);
                if (i > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
        return false;
    }

    public static boolean update(TheLoai tl) {
        Connect db = new Connect();
        Connection conn = db.getConnection();
        String sql = "update loai_sach set ten_loai_sach = '" + tl.getTen_loai_sach()
                + "' where ma_loai_sach = '" + tl.getMa_loai_sach() + "'";
        System.out.println(sql);
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                int i = stmt.executeUpdate(sql);
                if (i > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
        return false;
    }

    public static List<TheLoai> searchByName(String name) {
        List<TheLoai> list = new ArrayList<>();
        Connect db = new Connect();
        Connection conn = db.getConnection();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "select * from loai_sach where ten_loai_sach like '%" + name + "%'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    TheLoai tl = new TheLoai(
                        rs.getString("ma_loai_sach"),
                        rs.getString("ten_loai_sach")
                    );
                    list.add(tl);
                }
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) { }
        }
        return list;
    }

    public static TheLoai findById(String ma_loai_sach) {
        TheLoai tl = null;
        String sql = "select * from loai_sach where ma_loai_sach = '" + ma_loai_sach + "'";
        Connect db = new Connect();
        try (Connection conn = db.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                tl = new TheLoai(
                    rs.getString("ma_loai_sach"),
                    rs.getString("ten_loai_sach")
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tl;
    }
    public static String getMaByTen(String tenLoai) {
        String ma = null;
        Connect db = new Connect();
        String sql = "SELECT ma_loai_sach FROM loai_sach WHERE ten_loai_sach='" + tenLoai + "'";
        try (Connection conn = db.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                ma = rs.getString("ma_loai_sach");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ma;
    }

}
