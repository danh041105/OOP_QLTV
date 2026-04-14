package dao;

import connect.Connect;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User checkLogin(String u, String p) {
        User user = null;
        String sql = "SELECT username, password, role FROM user WHERE username = ? AND password = ?";
        Connect myConnect = new Connect();

        try (Connection conn = myConnect.getConnection()) {

            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, u);
                ps.setString(2, p);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setRole(rs.getInt("role"));
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

    public boolean registerUser(String username, String password, String email, int role) {
        Connect cn = new Connect();
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = cn.getConnection();
            String sql = "INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)";

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, password);
            pstm.setString(3, email);
            pstm.setInt(4, role);

            int row = pstm.executeUpdate();
            return row > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
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
