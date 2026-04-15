package dao;

import model.Admin;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import connect.Connect;

public class AdminDAO {
    private Admin readResultSet(ResultSet rs) throws SQLException {
        Admin ad = new Admin(
                rs.getInt("id"),
                rs.getString("ma_admin"),
                rs.getString("ho_ten"),
                rs.getString("gioi_tinh"),
                rs.getString("sdt"),
                rs.getString("email"));
        ad.setUsername(rs.getString("username"));
        ad.setPassword(rs.getString("password"));
        return ad;
    }

    public ArrayList<Admin> getAll() {
        ArrayList<Admin> list = new ArrayList<>();
        String sql = "SELECT u.id, u.email, ad.*, u.username, u.password FROM user u JOIN admin ad ON u.id = ad.id WHERE u.role = 0";
        try (Connection conn = new Connect().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                list.add(readResultSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Admin ad) {
        // Bỏ kiểm tra password.isEmpty()
        if (ad.getHoten().isEmpty() || ad.getEmail().isEmpty() || ad.getSdt().isEmpty()
                || ad.getGioitinh().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ các thông tin!");
            return false;
        }
        if (!ad.getEmail().contains("@") || !ad.getEmail().contains(".")) {
            JOptionPane.showMessageDialog(null, "Email không đúng định dạng!");
            return false;
        }

        if (!ad.getSdt().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải là 10 chữ số!");
            return false;
        }
        try (Connection conn = new Connect().getConnection();) {
            conn.setAutoCommit(false);

            boolean hasNewPassword = ad.getPassword() != null && !ad.getPassword().trim().isEmpty();
            String sqlUser = hasNewPassword ? "UPDATE user SET password=?, email=? WHERE id=?"
                    : "UPDATE user SET email=? WHERE id=?";

            try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                if (hasNewPassword) {
                    String hashedPassword = utils.PasswordUtils.hashPassword(ad.getPassword());
                    psUser.setString(1, hashedPassword);
                    psUser.setString(2, ad.getEmail());
                    psUser.setInt(3, ad.getId());
                } else {
                    psUser.setString(1, ad.getEmail());
                    psUser.setInt(2, ad.getId());
                }
                psUser.executeUpdate();
            }

            String sqlAdmin = "UPDATE admin SET ho_ten=?, gioi_tinh=?, sdt=? WHERE id=?";
            PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin);
            psAdmin.setString(1, ad.getHoten());
            psAdmin.setString(2, ad.getGioitinh());
            psAdmin.setString(3, ad.getSdt());
            psAdmin.setInt(4, ad.getId());
            int res = psAdmin.executeUpdate();

            conn.commit();
            return res > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insert(Admin ad) {
        if (ad.getMaadmin().isEmpty() || ad.getHoten().isEmpty() || ad.getEmail().isEmpty()
                || ad.getSdt().isEmpty() || ad.getUsername().isEmpty() || ad.getPassword().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ tất cả thông tin để tạo Admin mới!");
            return false;
        }

        try (Connection conn = new Connect().getConnection()) {
            conn.setAutoCommit(false);

            // 1. Insert into user
            String sqlUser = "INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, 0)";
            PreparedStatement psUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            psUser.setString(1, ad.getUsername());
            psUser.setString(2, utils.PasswordUtils.hashPassword(ad.getPassword()));
            psUser.setString(3, ad.getEmail());
            psUser.executeUpdate();

            ResultSet rs = psUser.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);

                // 2. Insert into admin
                String sqlAdmin = "INSERT INTO admin (ma_admin, ho_ten, gioi_tinh, sdt, id) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin);
                psAdmin.setString(1, ad.getMaadmin());
                psAdmin.setString(2, ad.getHoten());
                psAdmin.setString(3, ad.getGioitinh());
                psAdmin.setString(4, ad.getSdt());
                psAdmin.setInt(5, id);
                psAdmin.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkUsernameExists(String username) {
        String sql = "SELECT id FROM user WHERE username = ?";
        try (Connection conn = new Connect().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkMaAdminExists(String maAdmin) {
        String sql = "SELECT id FROM admin WHERE ma_admin = ?";
        try (Connection conn = new Connect().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maAdmin);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}
