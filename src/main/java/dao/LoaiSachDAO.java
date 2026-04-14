package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.LoaiSach;
import connect.Connect;
import java.sql.ResultSet;
/**
 *
 * @author ADMIN
 */
public class LoaiSachDAO {
    public List<LoaiSach> getAll(){
        List<LoaiSach> list = new ArrayList<>();
        String sql = "select * from loai_sach order by ten_loai_sach";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                LoaiSach ls = new LoaiSach();
                ls.setMaLoaiSach(rs.getString(1));
                ls.setTenLoaiSach(rs.getString(2));
                list.add(ls);
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("Loi lay du lieu: "+ e.getMessage());
        }
        return list;
    }
    
    public LoaiSach getById(String maLoaiSach) {
        String sql = "select * from loai_sach where ma_loai_sach=?";
        try(Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLoaiSach);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                LoaiSach ls = new LoaiSach();
                ls.setMaLoaiSach(rs.getString(1));
                ls.setTenLoaiSach(rs.getString(2));
                
                return ls;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi lấy LoaiSach theo ID: " + e.getMessage());
        }
        return null;
    }
    // 3. Thêm mới Thể loại
    public boolean insert(LoaiSach ls) {
        String sql = "INSERT INTO loai_sach(ma_loai_sach, ten_loai_sach) VALUES (?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ls.getMaLoaiSach());
            ps.setString(2, ls.getTenLoaiSach());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(LoaiSach ls) {
        String sql = "UPDATE loai_sach SET ten_loai_sach = ? WHERE ma_loai_sach = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ls.getTenLoaiSach());
            ps.setString(2, ls.getMaLoaiSach());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5. Xóa Thể loại (Có kiểm tra ràng buộc sách)
    public boolean remove(String maLoaiSach) {
        // Bước 1: Kiểm tra xem thể loại này đã có sách nào dùng chưa
        String checkSql = "SELECT COUNT(*) AS kiemtra FROM sach WHERE ma_loai_sach = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement psCheck = conn.prepareStatement(checkSql)) {

            psCheck.setString(1, maLoaiSach);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt("kiemtra") > 0) {
                System.out.println("Thể loại đang có sách, không thể xóa để bảo toàn dữ liệu!");
                return false;
            }

            // Bước 2: Nếu chưa có sách nào, tiến hành xóa
            String deleteSql = "DELETE FROM loai_sach WHERE ma_loai_sach = ?";
            try (PreparedStatement psDelete = conn.prepareStatement(deleteSql)) {
                psDelete.setString(1, maLoaiSach);
                return psDelete.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Tìm kiếm theo Tên
    public List<LoaiSach> searchByName(String name) {
        List<LoaiSach> list = new ArrayList<>();
        // Sử dụng toán tử LIKE với PreparedStatement để chống hack
        String sql = "SELECT * FROM loai_sach WHERE ten_loai_sach LIKE ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LoaiSach ls = new LoaiSach();
                ls.setMaLoaiSach(rs.getString("ma_loai_sach"));
                ls.setTenLoaiSach(rs.getString("ten_loai_sach"));
                list.add(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 7. Lấy Mã từ Tên (Dùng cho các ComboBox giao diện)
    public String getMaByTen(String tenLoai) {
        String sql = "SELECT ma_loai_sach FROM loai_sach WHERE ten_loai_sach = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenLoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ma_loai_sach");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
