/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mycompany.java_qltv.connect.DBconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Sach;

public class SachDAO {

    public List<Sach> getByLoaiSach(String maLoaiSach) {
        List<Sach> list = new ArrayList<>();

        String sql
                = "SELECT s.*, ls.ten_loai_sach, tg.ten_tg "
                + "FROM sach s "
                + "JOIN loai_sach ls ON s.ma_loai_sach = ls.ma_loai_sach "
                + "JOIN tac_gia tg ON s.ma_tg = tg.ma_tg "
                + "WHERE s.ma_loai_sach = ? "
                + "ORDER BY s.ten_sach";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maLoaiSach);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sach s = new Sach();
                s.setMaSach(rs.getString("ma_sach"));
                s.setTenSach(rs.getString("ten_sach"));
                s.setMaLoaiSach(rs.getString("ma_loai_sach"));
                s.setTenLoaiSach(rs.getString("ten_loai_sach"));
                s.setMa_Tg(rs.getInt("ma_tg"));
                s.setTenTacGia(rs.getString("ten_tg"));
                s.setNhaXb(rs.getString("nha_xb"));
                s.setNamXb(rs.getInt("nam_xb"));
                s.setSoLuong(rs.getInt("so_luong"));
                s.setTinhTrang(rs.getBoolean("tinh_trang"));
                s.setMoTa(rs.getString("mo_ta"));
                s.setImage(rs.getString("image"));

                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Sach> search(String maLoaiSach, String keyword) {
        List<Sach> list = new ArrayList<>();

        String sql = "select s.*, ls.ten_loai_sach, tg.ten_tg "
                + "from sach s "
                + "join loai_sach ls on s.ma_loai_sach=ls.ma_loai_sach "
                + "join tac_gia tg on tg.ma_tg=s.ma_tg "
                + "where s.ma_loai_sach = ? and "
                + "(s.ten_sach like ? "
                + "or tg.ten_tg like ? "
                + "or s.nha_xb like ? "
                + "or cast(s.nam_xb as char) like ? )"
                + "order by s.ten_sach";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, maLoaiSach);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            ps.setString(5, kw);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sach s = new Sach();
                s.setMaSach(rs.getString("ma_sach"));
                s.setTenSach(rs.getString("ten_sach"));
                s.setMaLoaiSach(rs.getString("ma_loai_sach"));
                s.setTenLoaiSach(rs.getString("ten_loai_sach"));
                s.setMa_Tg(rs.getInt("ma_tg"));
                s.setTenTacGia(rs.getString("ten_tg"));
                s.setNhaXb(rs.getString("nha_xb"));
                s.setNamXb(rs.getInt("nam_xb"));
                s.setSoLuong(rs.getInt("so_luong"));
                s.setTinhTrang(rs.getBoolean("tinh_trang"));
                s.setMoTa(rs.getString("mo_ta"));
                s.setImage(rs.getString("image"));

                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Sach getById(String maSach) {

        String sql
                = "SELECT s.*, ls.ten_loai_sach, tg.ten_tg "
                + "FROM sach s "
                + "JOIN loai_sach ls ON s.ma_loai_sach = ls.ma_loai_sach "
                + "JOIN tac_gia tg ON s.ma_tg = tg.ma_tg "
                + "WHERE s.ma_sach = ?";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSach);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Sach s = new Sach();
                s.setMaSach(rs.getString("ma_sach"));
                s.setTenSach(rs.getString("ten_sach"));
                s.setMaLoaiSach(rs.getString("ma_loai_sach"));
                s.setTenLoaiSach(rs.getString("ten_loai_sach"));
                s.setMa_Tg(rs.getInt("ma_tg"));
                s.setTenTacGia(rs.getString("ten_tg"));
                s.setNhaXb(rs.getString("nha_xb"));
                s.setNamXb(rs.getInt("nam_xb"));
                s.setSoLuong(rs.getInt("so_luong"));
                s.setTinhTrang(rs.getBoolean("tinh_trang"));
                s.setMoTa(rs.getString("mo_ta"));
                s.setImage(rs.getString("image"));

                return s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Sach> getSachByTacGia(int maTg) {
        List<Sach> ds = new ArrayList<>();
        String sql = "select * from sach where ma_tg=?";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTg);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sach s = new Sach();
                s.setMaSach(rs.getString("ma_sach"));
                s.setTenSach(rs.getString("ten_sach"));
                s.setNamXb(rs.getInt("nam_xb"));

                ds.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ds;
    }

    
    public int getSoLuongTon(String maSach) {
        int sl = 0;
        String sql = "select so_luong from sach where ma_sach=?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSach);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sl = rs.getInt("so_luong");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sl;
    }
    
    public boolean truSoLuong(String maSach, int soLuongMuon) {
        String sql = "update sach set so_luong = so_luong - ? where ma_sach = ? and so_luong >= ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongMuon);
            ps.setString(2, maSach);
            ps.setInt(3, soLuongMuon);

            return ps.executeLargeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean capNhatTinhTrang(String maSach) {
        String sql = "UPDATE sach SET tinh_trang = CASE "
                + "WHEN so_luong = 0 THEN 0 "
                + "ELSE 1 "
                + "END "
                + "WHERE ma_sach = ?";
        try (Connection conn = DBconnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
