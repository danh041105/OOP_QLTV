package qltv;

import connect.connect;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class PhieumuonDAO {

    public Vector<String> getColumnNames() {
        Vector<String> columns = new Vector<>();
        columns.add("Mã Phiếu");
        columns.add("Mã SV");
        columns.add("Họ Tên SV");
        columns.add("Tên Sách");
        columns.add("Ngày Mượn");
        columns.add("Ngày Trả");
        columns.add("Trạng Thái");
        columns.add("Hình Phạt");
        return columns;
    }

    public DefaultTableModel getLichSuMuonTra(String maSVXem) {
        DefaultTableModel model = new DefaultTableModel(getColumnNames(), 0);
 
        String sql = "SELECT pm.ma_pm, pm.ma_sv, sv.ho_ten, s.ten_sach, pm.ngay_muon, pm.ngay_tra, pm.tinh_trang, hp.hinh_thuc, hp.tien_do " +
             "FROM phieu_muon pm " +
             "LEFT JOIN sinh_vien sv ON pm.ma_sv = sv.ma_sv " +  
             "LEFT JOIN sach s ON pm.ma_sach = s.ma_sach " +     
             "LEFT JOIN hinh_phat hp ON pm.ma_sv = hp.ma_sv ";

        if (maSVXem != null && !maSVXem.isEmpty()) {
            sql += " WHERE pm.ma_sv = '" + maSVXem + "'";
        }
        
        sql += " ORDER BY pm.ngay_muon DESC"; 

        connect myConnect = new connect();
        try (Connection conn = myConnect.getConnection()) {
            if (conn != null) {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getString("ma_pm"));
                    row.add(rs.getString("ma_sv"));
                    row.add(rs.getString("ho_ten"));
                    row.add(rs.getString("ten_sach"));
                    row.add(rs.getDate("ngay_muon"));
                    row.add(rs.getDate("ngay_tra"));
      
                    String trangThai = rs.getString("tinh_trang"); 
                    row.add(trangThai);

                    String hinhPhat = rs.getString("hinh_thuc");
                    String tienDo = rs.getString("tien_do");
                    
                    if (hinhPhat == null || hinhPhat.isEmpty()) {
                        row.add("Không");
                    } else {

                        row.add(hinhPhat + " (" + tienDo + ")");
                    }
                    
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}