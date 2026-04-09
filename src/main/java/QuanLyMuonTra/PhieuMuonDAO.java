/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyMuonTra;

import connect.connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hien
 */
public class PhieuMuonDAO {

    //Lấy dữ liệu phiếu mượn (kèm lọc theo tình trạng)
    public List<PhieuMuon> layDuLieuPhieuMuon(String tinhTrangLoc) {
        List<PhieuMuon> ds = new ArrayList<>();
        String sql = "SELECT pm.ma_pm, pm.ma_sv, sv.ho_ten, pm.ma_sach, s.ten_sach, "
                   + "tg.ten_tg, s.nha_xb, pm.so_luong, pm.ngay_muon, pm.ngay_tra, pm.tinh_trang "
                   + "FROM phieu_muon pm "
                   + "JOIN sinh_vien sv ON pm.ma_sv = sv.ma_sv "
                   + "JOIN sach s ON pm.ma_sach = s.ma_sach "
                   + "JOIN tac_gia tg ON s.ma_tg = tg.ma_tg ";

        if (tinhTrangLoc != null && !tinhTrangLoc.isEmpty()) {
            sql += " WHERE pm.tinh_trang = ?";
        }
        sql += " ORDER BY pm.ma_pm ASC";

        connect db = new connect(); 
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            if (tinhTrangLoc != null && !tinhTrangLoc.isEmpty()) {
                ps.setString(1, tinhTrangLoc);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon();
                
                pm.setMaPM(rs.getString("ma_pm"));  
                pm.setMaSV(rs.getString("ma_sv"));    
                pm.setHoTen(rs.getString("ho_ten"));    
                pm.setMaSach(rs.getString("ma_sach"));  
                pm.setTenSach(rs.getString("ten_sach"));   
                pm.setTentg(rs.getString("ten_tg"));   
                pm.setNhaxb(rs.getString("nha_xb"));   
                pm.setSoLuong(rs.getInt("so_luong"));     
                pm.setNgayMuon(rs.getDate("ngay_muon"));    
                pm.setNgayTra(rs.getDate("ngay_tra"));     
                pm.setTinhTrang(rs.getString("tinh_trang"));  
                
                ds.add(pm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    //Thống kê số lượng theo tình trạng
    public int[] laySoLuongThongKe() {
        int[] kq = {0, 0, 0, 0}; 
        String sql = "SELECT tinh_trang, Count(*) AS soluong FROM phieu_muon GROUP BY tinh_trang";

        connect db = new connect();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tinhTrang = rs.getString("tinh_trang");
                int soLuong = rs.getInt("soluong");
                if (tinhTrang.equals("Đã trả")) kq[0] = soLuong;
                else if (tinhTrang.equals("Đang mượn")) kq[1] = soLuong;
                else if (tinhTrang.equals("Quá hạn trả")) kq[2] = soLuong;
                else if (tinhTrang.equals("Trả chậm")) kq[3] = soLuong;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return kq;
    }
    
    
    //thêm:
    public boolean insertPhieuMuon(String maPM, String maSV, String maSach, int sl, String ngayM, String ngayT) {
        connect db = new connect();
        Connection conn = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false); 

            String sqlInsert = "INSERT INTO phieu_muon (ma_pm, ma_sv, ma_sach, so_luong, ngay_muon, ngay_tra, tinh_trang) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setString(1, maPM);
            psInsert.setString(2, maSV);
            psInsert.setString(3, maSach);
            psInsert.setInt(4, sl);
            psInsert.setString(5, ngayM);
            psInsert.setString(6, ngayT);
            psInsert.setString(7, "Đang mượn"); 
            psInsert.executeUpdate();

            String sqlUpdateSach = "UPDATE sach SET so_luong = so_luong - ? WHERE ma_sach = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateSach);
            psUpdate.setInt(1, sl);
            psUpdate.setString(2, maSach);
            psUpdate.executeUpdate();

            conn.commit(); 
            return true;
        } catch (Exception e) {
            try { 
                if (conn != null) 
                    conn.rollback(); 
            } catch (SQLException ex) 
            {
            } 
            e.printStackTrace();
            return false;
        } finally {
            try { 
                if (conn != null) { 
                    conn.setAutoCommit(true); 
                    conn.close(); 
                } 
            } catch (SQLException ex) {}
        }
    }

    //Cập nhật phiếu mượn (Xử lý Transaction và cập nhật kho sách)
    
        public boolean updatePhieuMuon(String maPM, String maSachMoi, int soLuongMoi, String ngayTraMoi, String tinhTrangMoi) {
        connect db = new connect();
        Connection conn = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false); 

            String sqlCu = "SELECT ma_sach, so_luong, tinh_trang FROM phieu_muon WHERE ma_pm = ?";
            PreparedStatement psCu = conn.prepareStatement(sqlCu);
            psCu.setString(1, maPM);
            ResultSet rs = psCu.executeQuery();

            if (rs.next()) {
                String maSachCu = rs.getString("ma_sach");
                int soLuongCu = rs.getInt("so_luong");
                String tinhTrangCu = rs.getString("tinh_trang");

                boolean daTraTruocDo = tinhTrangCu.equals("Đã trả") || tinhTrangCu.equals("Trả chậm");
                boolean seTraBayGio = tinhTrangMoi.equals("Đã trả") || tinhTrangMoi.equals("Trả chậm");
                //th1: chưa trả -> đã trả
                if (!daTraTruocDo && seTraBayGio) {
                    PreparedStatement psTra = conn.prepareStatement("UPDATE sach SET so_luong = so_luong + ? WHERE ma_sach = ?");
                    psTra.setInt(1, soLuongCu);
                    psTra.setString(2, maSachCu);
                    psTra.executeUpdate();
                } 
                //th2: đã trả -> chưa trả
                else if (daTraTruocDo && !seTraBayGio) {
                    PreparedStatement psMuonLai = conn.prepareStatement("UPDATE sach SET so_luong = so_luong - ? WHERE ma_sach = ?");
                    psMuonLai.setInt(1, soLuongMoi);
                    psMuonLai.setString(2, maSachMoi);
                    psMuonLai.executeUpdate();
                }
                //th3: chưa trả nhưng thay đổi về số lượng hoặc sách khác
                else if (!daTraTruocDo && !seTraBayGio) {
                    if (maSachMoi.equals(maSachCu)) {
                        int chenhLech = soLuongMoi - soLuongCu;
                        PreparedStatement psSua = conn.prepareStatement("UPDATE sach SET so_luong = so_luong - ? WHERE ma_sach = ?");
                        psSua.setInt(1, chenhLech);
                        psSua.setString(2, maSachMoi);
                        psSua.executeUpdate();
                    } else {
                        // Hoàn lại sách cũ, trừ sách mới
                        PreparedStatement psHoanCu = conn.prepareStatement("UPDATE sach SET so_luong = so_luong + ? WHERE ma_sach = ?");
                        psHoanCu.setInt(1, soLuongCu);
                        psHoanCu.setString(2, maSachCu);
                        psHoanCu.executeUpdate();

                        PreparedStatement psTruMoi = conn.prepareStatement("UPDATE sach SET so_luong = so_luong - ? WHERE ma_sach = ?");
                        psTruMoi.setInt(1, soLuongMoi);
                        psTruMoi.setString(2, maSachMoi);
                        psTruMoi.executeUpdate();
                    }
                }
                //th4: đã trả giữ nguyên -> tránh cộng dồn

                String sqlUpdatePM = "UPDATE phieu_muon SET ngay_tra = ?, tinh_trang = ?, ma_sach = ?, so_luong = ? WHERE ma_pm = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdatePM);
                psUpdate.setString(1, ngayTraMoi);
                psUpdate.setString(2, tinhTrangMoi);
                psUpdate.setString(3, maSachMoi);
                psUpdate.setInt(4, soLuongMoi);
                psUpdate.setString(5, maPM);
                psUpdate.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            try { 
                if(conn != null) 
                    conn.rollback(); 
            } catch(Exception ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { 
                if(conn != null) { 
                    conn.setAutoCommit(true); 
                    conn.close(); 
                } 
            } catch(Exception ex) {}
        }
    }

    // Xóa phiếu mượn (Chỉ cho phép nếu đã trả và đã hoàn thành hình phạt)
    public int deletePhieuMuon(String maPM) {
        connect db = new connect();
        try (Connection conn = db.getConnection()) {
            String sqlCheckTinhTrang = "SELECT tinh_trang FROM phieu_muon WHERE ma_pm = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheckTinhTrang)) {
                psCheck.setString(1, maPM);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    String tinhTrang = rs.getString("tinh_trang");
                    if (!tinhTrang.equals("Đã trả")) {
                        return 2;
                    }
                } else {
                    return 0;
                }
            }
            String sqlCheckHinhPhat = "SELECT COUNT(*) FROM hinh_phat WHERE ma_pm = ? AND tien_do != N'Đã hoàn thành'";
            try (PreparedStatement psHP = conn.prepareStatement(sqlCheckHinhPhat)) {
                psHP.setString(1, maPM);
                ResultSet rsHP = psHP.executeQuery();
                if (rsHP.next()) {
                    int count = rsHP.getInt(1);
                    if (count > 0) {
                        return 3; 
                    }
                }
            }

            // Bước 3: Nếu vượt qua hết kiểm tra, thực hiện xóa phiếu mượn
            String sqlDelete = "DELETE FROM phieu_muon WHERE ma_pm = ?";
            try (PreparedStatement psDel = conn.prepareStatement(sqlDelete)) {
                psDel.setString(1, maPM);
                psDel.executeUpdate();
                return 1; 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; 
    }

    // Tìm kiếm
    public List<PhieuMuon> searchPhieuMuon(String maSV, String hoTen, String tenSach) {
        String sql = "SELECT pm.ma_pm, pm.ma_sv, sv.ho_ten, pm.ma_sach, s.ten_sach, tg.ten_tg, s.nha_xb, pm.so_luong, pm.ngay_muon, pm.ngay_tra, pm.tinh_trang "
                   + "FROM phieu_muon pm "
                   + "JOIN sinh_vien sv ON pm.ma_sv = sv.ma_sv "
                   + "JOIN sach s ON pm.ma_sach = s.ma_sach "
                   + "JOIN tac_gia tg ON s.ma_tg = tg.ma_tg WHERE 1=1";

        if (maSV != null && !maSV.isEmpty()) sql+=" AND sv.ma_sv LIKE ?";
        if (hoTen != null && !hoTen.isEmpty()) sql+= " AND sv.ho_ten LIKE ?";
        if (tenSach != null && !tenSach.isEmpty()) sql += " AND s.ten_sach LIKE ?";

        List<PhieuMuon> ds = new ArrayList<>();
        connect db = new connect();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            int index = 1;
            if (maSV != null && !maSV.isEmpty()) ps.setString(index++, "%" + maSV + "%");
            if (hoTen != null && !hoTen.isEmpty()) ps.setString(index++, "%" + hoTen + "%");
            if (tenSach != null && !tenSach.isEmpty()) ps.setString(index++, "%" + tenSach + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon();
                pm.setMaPM(rs.getString("ma_pm"));
                pm.setMaSV(rs.getString("ma_sv"));
                pm.setHoTen(rs.getString("ho_ten"));
                pm.setMaSach(rs.getString("ma_sach"));
                pm.setTenSach(rs.getString("ten_sach"));
                pm.setTentg(rs.getString("ten_tg"));
                pm.setNhaxb(rs.getString("nha_xb"));
                pm.setSoLuong(rs.getInt("so_luong"));
                pm.setNgayMuon(rs.getDate("ngay_muon"));
                pm.setNgayTra(rs.getDate("ngay_tra"));
                pm.setTinhTrang(rs.getString("tinh_trang"));

                ds.add(pm); 
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return ds;
    }
    
    
}