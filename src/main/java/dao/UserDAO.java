package dao;

import connect.Connect;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSet;

public class UserDAO {

    public User checkLogin(String u, String p) {
        User user = null;
        String sql = "SELECT id, username, password, role FROM user WHERE username = ?";
        Connect myConnect = new Connect();

        try (Connection conn = myConnect.getConnection()) {

            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, u);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    
                    // So sánh mật khẩu bằng BCrypt
                    if (utils.PasswordUtils.checkPassword(p, storedHash)) {
                        user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setRole(rs.getInt("role"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getHoTenAdmin(String maAdmin) {
        String hoTen = "";
        String sql = "SELECT ho_ten FROM admin WHERE ma_admin = ?";

        Connect myConnect = new Connect();

        try (Connection conn = myConnect.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, maAdmin);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    hoTen = rs.getString("ho_ten");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (hoTen.isEmpty()) ? maAdmin : hoTen;
    }

    public String getHoTenSinhVien(String maSV) {
        String hoTen = "";
        String sql = "SELECT ho_ten FROM sinh_vien WHERE ma_sv = ?";

        Connect myConnect = new Connect();

        try (Connection conn = myConnect.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, maSV);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    hoTen = rs.getString("ho_ten");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (hoTen.isEmpty()) ? maSV : hoTen;
    }

    public boolean registerUser(String username, String password, String email, int role,
                               String hoTen, String lop, String gioiTinh, String diaChi, String sdt) {
        Connect cn = new Connect();
        Connection conn = null;

        try {
            conn = cn.getConnection();
            conn.setAutoCommit(false);

            // 1. INSERT vào bảng user
            String sqlUser = "INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            
            String hashedPassword = utils.PasswordUtils.hashPassword(password);
            
            pstm.setString(1, username);
            pstm.setString(2, hashedPassword);
            pstm.setString(3, email);
            pstm.setInt(4, role);
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);

                // 2. INSERT vào bảng sinh_vien (nếu role = 1)
                if (role == 1) {
                    String sqlSV = "INSERT INTO sinh_vien (ma_sv, ho_ten, lop, gioi_tinh, ngay_sinh, dia_chi, sdt, id) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmSV = conn.prepareStatement(sqlSV);
                    pstmSV.setString(1, username);   // ma_sv
                    pstmSV.setString(2, hoTen);      // ho_ten
                    pstmSV.setString(3, lop);        // lop
                    pstmSV.setString(4, gioiTinh);   // gioi_tinh
                    pstmSV.setDate(5, java.sql.Date.valueOf("2000-01-01")); // Ngày sinh tạm để mặc định
                    pstmSV.setString(6, diaChi);     // dia_chi
                    pstmSV.setString(7, sdt);        // sdt
                    pstmSV.setInt(8, userId);
                    pstmSV.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    public String getPasswordByEmail(String email) {
        String password = null;
        String sql = "SELECT password FROM user WHERE email = ?";
        Connect myConnect = new Connect();

        try (Connection conn = myConnect.getConnection()) {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    password = rs.getString("password");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
}
