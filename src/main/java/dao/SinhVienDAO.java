package dao;

import connect.Connect;
import model.SinhVien;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class SinhVienDAO {
    private Connection conn;
    public SinhVienDAO() {
        conn = new Connect().getConnection();
    }
    private SinhVien readResultSet(ResultSet rs) throws SQLException {
        SinhVien sv = new SinhVien(
            rs.getInt("id"),
            rs.getString("ma_sv"),
            rs.getString("ho_ten"),
            rs.getString("lop"),
            rs.getString("gioi_tinh"),
            rs.getDate("ngay_sinh"),
            rs.getString("dia_chi"),
            rs.getString("email"),
            rs.getString("sdt")
        );
        sv.setUsername(rs.getString("username"));
        sv.setPassword(rs.getString("password"));
        return sv;
    }

    public ArrayList<SinhVien> getAll() {
        ArrayList<SinhVien> list = new ArrayList<>();
        String sql = "SELECT u.id, sv.*, u.username, u.password "
                + "FROM user u JOIN sinh_vien sv ON u.id = sv.id "
                + "WHERE u.role = 1";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(readResultSet(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public ArrayList<SinhVien> search(String txt) {
        ArrayList<SinhVien> list = new ArrayList<>();
        String sql = "SELECT u.id, sv.*, u.username, u.password "
                + "FROM user u JOIN sinh_vien sv ON u.id = sv.id "
                + "WHERE u.role = 1 AND (sv.ho_ten LIKE ? OR sv.ma_sv LIKE ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + txt + "%");
            ps.setString(2, "%" + txt + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(readResultSet(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean delete(int id) {
        try {
            conn.setAutoCommit(false); 
            String sqlSV = "DELETE FROM sinh_vien WHERE id = ?";
            String sqlUser = "DELETE FROM user WHERE id = ?";
            
            PreparedStatement ps1 = conn.prepareStatement(sqlSV);
            ps1.setInt(1, id); ps1.executeUpdate();
            
            PreparedStatement ps2 = conn.prepareStatement(sqlUser);
            ps2.setInt(1, id);
            int res = ps2.executeUpdate();
            
            conn.commit();
            return res > 0;
        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ex) {}
            return false;
        }
    }
    
    public boolean insert(SinhVien sv, String user, String pass) {
        if (sv.getMasv().isEmpty() || sv.getHoten().isEmpty() || sv.getLop().isEmpty()
            || sv.getGioitinh().isEmpty() || sv.getNgaysinh() == null
            || sv.getDiachi().isEmpty() || sv.getEmail().isEmpty() || sv.getSdt().isEmpty()
            || user.isEmpty() || pass.isEmpty()) {
        
        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ tất cả thông tin!");
        return false;
    }
        if (!checkDinhDang(sv)) return false;
    try {
        String checkUserSql = "SELECT count(*) FROM user WHERE username = ?";
        PreparedStatement psCheckUser = conn.prepareStatement(checkUserSql);
        psCheckUser.setString(1, user);
        ResultSet rsUser = psCheckUser.executeQuery();
        if (rsUser.next() && rsUser.getInt(1) > 0) {
            JOptionPane.showMessageDialog(null, "Username đã tồn tại!");
            return false;
        }

        String checkMsvSql = "SELECT count(*) FROM sinh_vien WHERE ma_sv = ?";
        PreparedStatement psCheckMsv = conn.prepareStatement(checkMsvSql);
        psCheckMsv.setString(1, sv.getMasv());
        ResultSet rsMsv = psCheckMsv.executeQuery();
        if (rsMsv.next() && rsMsv.getInt(1) > 0) {
            JOptionPane.showMessageDialog(null, "Mã sinh viên đã tồn tại!");
            return false;
        }
        
        conn.setAutoCommit(false);
        PreparedStatement psU = conn.prepareStatement("INSERT INTO user(username, password, email, role) VALUES(?,?,?,1)", Statement.RETURN_GENERATED_KEYS);
        psU.setString(1, user); 
        psU.setString(2, pass);
        psU.setString(3, sv.getEmail());
        psU.executeUpdate();

        ResultSet rs = psU.getGeneratedKeys();
        rs.next(); 
        int id = rs.getInt(1);

        String sql = "INSERT INTO sinh_vien(ma_sv, ho_ten, lop, gioi_tinh, ngay_sinh, dia_chi, email, sdt, id) "
                   + "VALUES(?,?,?,?,?,?,?,?,?)";        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, sv.getMasv()); ps.setString(2, sv.getHoten());
        ps.setString(3, sv.getLop());  ps.setString(4, sv.getGioitinh());
        ps.setDate(5, new java.sql.Date(sv.getNgaysinh().getTime()));
        ps.setString(6, sv.getDiachi()); ps.setString(7, sv.getEmail());
        ps.setString(8, sv.getSdt());   ps.setInt(9, id);

        int res = ps.executeUpdate();
        conn.commit();
        return res > 0;
    } catch (Exception e) {
    e.printStackTrace(); // Hiện lỗi ở Console để lập trình viên xem
    try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
    JOptionPane.showMessageDialog(null, "Lỗi khi thêm: " + e.getMessage()); // Hiện cho người dùng xem
    return false;
} finally {
    try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
}
    }

   public boolean update(SinhVien sv) {
    if (sv.getPassword().isEmpty() || sv.getHoten().isEmpty() || sv.getLop().isEmpty() 
            || sv.getGioitinh().isEmpty() || sv.getNgaysinh() == null
            || sv.getDiachi().isEmpty() || sv.getEmail().isEmpty() 
            || sv.getSdt().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nhập đầy đủ thông tin!");
        return false;
    }
    if (!checkDinhDang(sv)) return false;
    try {
        String sqlCheck = "SELECT count(*) FROM user WHERE username = ? AND id != ?";
        try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
             psCheck.setString(1, sv.getUsername());
             psCheck.setInt(2, sv.getId());
             ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Lỗi: Username đã tồn tại!");
                return false;
            }
        }
        conn.setAutoCommit(false);

        String sqlUser = "UPDATE user SET password=? WHERE id=?";
        try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
             psUser.setString(1, sv.getPassword());
             psUser.setInt(2, sv.getId());
             psUser.executeUpdate();
        }

        
        String sqlSV = "UPDATE sinh_vien SET ho_ten=?, lop=?, gioi_tinh=?, ngay_sinh=?, dia_chi=?, email=?, sdt=? WHERE id=?";
        int res;
        try (PreparedStatement psSV = conn.prepareStatement(sqlSV)) {
             psSV.setString(1, sv.getHoten());
             psSV.setString(2, sv.getLop());
             psSV.setString(3, sv.getGioitinh());
             psSV.setDate(4, new java.sql.Date(sv.getNgaysinh().getTime()));
             psSV.setString(5, sv.getDiachi());
             psSV.setString(6, sv.getEmail());
             psSV.setString(7, sv.getSdt());
             psSV.setInt(8, sv.getId());
             res = psSV.executeUpdate();
        }

        conn.commit();
        JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        return res > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
   private boolean checkDinhDang(SinhVien sv) {
    if (!sv.getEmail().contains("@") || !sv.getEmail().contains(".")) {
        JOptionPane.showMessageDialog(null, "Email không hợp lệ!");
        return false;
    }
    if (!sv.getSdt().matches("\\d{10}")) {
        JOptionPane.showMessageDialog(null, "Số điện thoại phải là 10 chữ số!");
        return false;
    }
    return true;
}
}