/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.Connect;
import model.HinhPhat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Hien
 */
public class HinhPhatDAO {

    public List<HinhPhat> layDanhSachHinhPhat(String maSV, String tenSV) {
        List<HinhPhat> ds = new ArrayList<>();
        String sql = "SELECT hp.ma_hp, hp.ma_sv, sv.ho_ten, pm.tinh_trang, hp.ly_do, hp.ngay_phat, hp.hinh_thuc, hp.tien_do, hp.ma_pm "
                    + "FROM hinh_phat hp "
                    + "JOIN sinh_vien sv ON hp.ma_sv = sv.ma_sv "
                    + "JOIN phieu_muon pm ON hp.ma_pm = pm.ma_pm "
                    + "WHERE hp.ma_sv LIKE ? AND sv.ho_ten LIKE ?";

        Connect db = new Connect();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + maSV + "%");
            ps.setString(2, "%" + tenSV + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HinhPhat hp = new HinhPhat();
                hp.setMaHP(rs.getInt("ma_hp"));
                hp.setMaSV(rs.getString("ma_sv"));
                hp.setHoTen(rs.getString("ho_ten"));
                hp.setTinhTrang(rs.getString("tinh_trang"));
                hp.setLyDo(rs.getString("ly_do"));
                hp.setNgayPhat(rs.getDate("ngay_phat"));
                hp.setHinhThuc(rs.getString("hinh_thuc"));
                hp.setTienDo(rs.getString("tien_do"));
                hp.setMaPM(rs.getString("ma_pm"));
                
                ds.add(hp);
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return ds;
    }

    //Thêm mới hình phạt
    public int insertHinhPhat(String maSV, String maPM, String lyDo, String ngay, String hinhThuc, String tienDo) {
        String sql = "INSERT INTO hinh_phat (ma_sv, ma_pm, ly_do, ngay_phat, hinh_thuc, tien_do) VALUES (?,?,?,?,?,?)";
        Connect db = new Connect();
        try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSV); 
            ps.setString(2, maPM); 
            ps.setString(3, lyDo);
            ps.setDate(4, java.sql.Date.valueOf(ngay)); 
            ps.setString(5, hinhThuc);
            ps.setString(6, tienDo);
            return ps.executeUpdate();
        } catch (SQLException e) { 
            e.printStackTrace();
            return 0; 
        }
    }


    //Cập nhật 
    
    public boolean updateHinhPhat(int maHP, String lyDo, String ngay, String hinhThuc, String tienDo) {
        String sql = "UPDATE hinh_phat SET ly_do = ?, ngay_phat = ?, hinh_thuc = ?, tien_do = ? WHERE ma_hp = ?";
        Connect db = new Connect();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lyDo);
            ps.setDate(2, java.sql.Date.valueOf(ngay));
            ps.setString(3, hinhThuc);
            ps.setString(4, tienDo);
            ps.setInt(5, maHP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Xóa hình phạt
 
    public int deleteHinhPhat(int maHP) {
        Connect db = new Connect();
        String checkSql = "SELECT tien_do FROM hinh_phat WHERE ma_hp = ?";
        
        try (Connection conn = db.getConnection();
             PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
            
            psCheck.setInt(1, maHP);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                if ("Đã hoàn thành".equals(rs.getString("tien_do"))) {
                    try (PreparedStatement psDel = conn.prepareStatement("DELETE FROM hinh_phat WHERE ma_hp = ?")) {
                        psDel.setInt(1, maHP);
                        psDel.executeUpdate();
                        return 1; 
                    }
                } else {
                    return 2; 
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return 0; 
    }
}