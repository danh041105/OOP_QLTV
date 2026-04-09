package QuanLyTKSV;

import connect.connect;
import java.sql.*;
import javax.swing.JOptionPane;

public class svDAO {
    private Connection conn;

    public svDAO() {
        conn = new connect().getConnection();
    }
    private sv readResultSet(ResultSet rs) throws SQLException {
        sv sv = new sv(
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
    public sv getByUsername(String username) {
    String sql = "SELECT u.id, u.username, u.password, u.email AS email_user, "
               + "sv.ma_sv, sv.ho_ten, sv.lop, sv.gioi_tinh, sv.ngay_sinh, "
               + "sv.dia_chi, sv.email AS email_sv, sv.sdt "
               + "FROM user u LEFT JOIN sinh_vien sv ON u.id = sv.id "
               + "WHERE u.username = ?";
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            sv student = new sv();
            student.setId(rs.getInt("id"));
            student.setUsername(rs.getString("username"));
            student.setPassword(rs.getString("password"));

            String emailSV = rs.getString("email_sv");
            String emailUser = rs.getString("email_user");
            student.setEmail(emailSV != null ? emailSV : emailUser);

            student.setMasv(rs.getString("ma_sv"));
            student.setHoten(rs.getString("ho_ten"));
            student.setLop(rs.getString("lop"));
            student.setGioitinh(rs.getString("gioi_tinh"));
            student.setNgaysinh(rs.getDate("ngay_sinh"));
            student.setDiachi(rs.getString("dia_chi"));
            student.setSdt(rs.getString("sdt"));

            return student;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    
    public boolean insertProfile(sv sv) {
    String sql = """
        INSERT INTO sinh_vien
        (id, ma_sv, ho_ten, lop, gioi_tinh, ngay_sinh, dia_chi, email, sdt)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)""";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, sv.getId());
        ps.setString(2, sv.getMasv());
        ps.setString(3, sv.getHoten());
        ps.setString(4, sv.getLop());
        ps.setString(5, sv.getGioitinh());
        ps.setDate(6, sv.getNgaysinh());
        ps.setString(7, sv.getDiachi());
        ps.setString(8, sv.getEmail());
        ps.setString(9, sv.getSdt());

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    
    public boolean updateProfile(sv sv) {
        if (sv.getPassword().isEmpty() || sv.getEmail().isEmpty() || sv.getSdt().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không được để trống thông tin!");
            return false;
        }
        if (!sv.getEmail().contains("@") || !sv.getEmail().contains(".")) {
        JOptionPane.showMessageDialog(null, "Email không hợp lệ!");
        return false;
        }
    
        if (!sv.getSdt().matches("\\d{10}")) {
        JOptionPane.showMessageDialog(null, "Số điện thoại phải là 10 chữ số!");
        return false;
        }

        try {
            conn.setAutoCommit(false); 
            
            String sqlUser = "UPDATE user SET password=?, email=? WHERE id=?";
            PreparedStatement psU = conn.prepareStatement(sqlUser);
            psU.setString(1, sv.getPassword());
            psU.setString(2, sv.getEmail());
            psU.setInt(3, sv.getId());
            psU.executeUpdate();

            String sqlSV = "UPDATE sinh_vien SET email=?, sdt=? WHERE id=?";
            PreparedStatement psS = conn.prepareStatement(sqlSV);
            psS.setString(1, sv.getEmail());
            psS.setString(2, sv.getSdt());
            psS.setInt(3, sv.getId());
            
            int res = psS.executeUpdate();
            
            conn.commit(); 
            return res > 0;
        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        }
    }
}