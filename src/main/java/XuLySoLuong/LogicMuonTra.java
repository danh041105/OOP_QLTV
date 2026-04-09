/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package XuLySoLuong;

import connect.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LogicMuonTra {
 
    
    public static boolean ktraNgayTra(String ngayMuon, String ngayTra) {
        try {
            LocalDate dMuon = LocalDate.parse(ngayMuon);
            LocalDate dTra = LocalDate.parse(ngayTra);
            
            if (dTra.isBefore(dMuon)) return false;
            
            long soNgay = ChronoUnit.DAYS.between(dMuon, dTra);
            return soNgay <= 31;
        } catch (Exception e) {
            return false;
        }
    }
    
    //Kiểm tra tồn kho
    public static int ktraTonKho(String maSach) {
        String sql = "SELECT so_luong FROM sach WHERE ma_sach = ?";
        try (Connection conn = new connect().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSach);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("so_luong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public static String taoMaPhieuMuonTuDong() {
        long timestamp = System.currentTimeMillis(); 
        return "PM" + timestamp;
    }
}
