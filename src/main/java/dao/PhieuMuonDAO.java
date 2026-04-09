/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mycompany.java_qltv.connect.DBconnect;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.PhieuMuon;

/**
 *
 * @author ADMIN
 */
public class PhieuMuonDAO {
    public boolean insert (PhieuMuon pm) {
        String sql = "insert into phieu_muon (ma_pm, ma_sv, ma_sach, ngay_muon, ngay_tra, so_luong, tinh_trang)"
                + "values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pm.getMaPm());
            ps.setString(2, pm.getMaSv());
            ps.setString(3, pm.getMaSach());
            ps.setDate(4, new java.sql.Date(
                    pm.getNgayMuon().getTime()
            ));

            ps.setDate(5, new java.sql.Date(
                    pm.getNgayTra().getTime()
            ));
            ps.setInt(6, pm.getSoLuong());
            ps.setString(7, pm.getTinhTrang());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
