package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date; // Import thư viện SQL Date
import java.util.ArrayList;
import java.util.List;
import model.TacGia;
import connect.Connect;

public class TacGiaDAO {

    // 1. Lấy toàn bộ danh sách
    public List<TacGia> getAll() {
        List<TacGia> list = new ArrayList<>();
        String sql = "SELECT * FROM tac_gia ORDER BY ten_tg";
        try (Connection conn = Connect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TacGia tg = new TacGia();
                tg.setMa_tg(rs.getInt("ma_tg"));
                tg.setTen_tg(rs.getString("ten_tg"));
                tg.setNgay_sinh(rs.getString("ngay_sinh")); // Lấy ngày từ DB
                tg.setGioi_tinh(rs.getString("gioi_tinh"));
                list.add(tg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Lấy 1 Tác giả theo Mã
    public TacGia getById(int maTg) {
        String sql = "SELECT * FROM tac_gia WHERE ma_tg = ?";
        try (Connection conn = Connect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTg);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                TacGia tg = new TacGia();
                tg.setMa_tg(rs.getInt("ma_tg"));
                tg.setTen_tg(rs.getString("ten_tg"));
                tg.setNgay_sinh(rs.getString("ngay_sinh"));
                tg.setGioi_tinh(rs.getString("gioi_tinh"));
                return tg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. Thêm mới Tác giả (Xử lý ép kiểu Ngày tháng)
    public boolean insert(TacGia tg) {
        String sql = "INSERT INTO tac_gia(ma_tg, ten_tg, ngay_sinh, gioi_tinh, quoc_tich) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Connect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tg.getMa_tg());
            ps.setString(2, tg.getTen_tg());
            ps.setString(3, tg.getNgay_sinh());
            ps.setString(4, tg.getGioi_tinh());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Cập nhật Tác giả
    public boolean update(TacGia tg) {
        String sql = "UPDATE tac_gia SET ten_tg=?, ngay_sinh=?, gioi_tinh=?, quoc_tich=? WHERE ma_tg=?";
        try (Connection conn = Connect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tg.getTen_tg());
            ps.setString(2, tg.getNgay_sinh());
            ps.setString(3, tg.getGioi_tinh());
            ps.setInt(5, tg.getMa_tg());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xóa Tác giả (Có kiểm tra ràng buộc sách)
    public boolean remove(int maTg) {
        String checkSql = "SELECT COUNT(*) AS kiemtra FROM sach WHERE ma_tg = ?";
        try (Connection conn = Connect.getConnection();
                PreparedStatement psCheck = conn.prepareStatement(checkSql)) {

            psCheck.setInt(1, maTg);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt("kiemtra") > 0) {
                System.out.println("Tác giả này đang có sách trong thư viện, không thể xóa!");
                return false;
            }

            String deleteSql = "DELETE FROM tac_gia WHERE ma_tg = ?";
            try (PreparedStatement psDelete = conn.prepareStatement(deleteSql)) {
                psDelete.setInt(1, maTg);
                return psDelete.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Tìm kiếm theo Tên
    public List<TacGia> searchByName(String name) {
        List<TacGia> list = new ArrayList<>();
        String sql = "SELECT * FROM tac_gia WHERE ten_tg LIKE ?";
        try (Connection conn = Connect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TacGia tg = new TacGia();
                tg.setMa_tg(rs.getInt("ma_tg"));
                tg.setTen_tg(rs.getString("ten_tg"));
                tg.setNgay_sinh(rs.getString("ngay_sinh"));
                tg.setGioi_tinh(rs.getString("gioi_tinh"));
                list.add(tg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}