package dao;

import connect.Connect;
import model.Admin;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AdminDAO {

    private Connection conn;

    public AdminDAO() {
        Connect db = new Connect();
        conn = db.getConnection();
    }
    private Admin readResultSet(ResultSet rs) throws SQLException {
        Admin ad = new Admin(
            rs.getInt("id"),
            rs.getString("ma_admin"),
            rs.getString("ho_ten"),
            rs.getString("gioi_tinh"),
            rs.getString("sdt"),
            rs.getString("email")
        );
        ad.setUsername(rs.getString("username"));
        ad.setPassword(rs.getString("password"));
        return ad;   }

    public ArrayList<Admin> getAll() {
        ArrayList<Admin> list = new ArrayList<>();
        String sql = "SELECT u.id, ad.*, u.username, u.password FROM user u JOIN admin ad ON u.id = ad.id WHERE u.role = 0";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(readResultSet(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public boolean update(Admin ad) {
        if ( ad.getPassword().isEmpty() || ad.getHoten().isEmpty() || ad.getEmail().isEmpty() || ad.getSdt().isEmpty() || ad.getGioitinh().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ tất cả thông tin!");
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

        try {
            conn.setAutoCommit(false);

            String sqlUser = "UPDATE user SET password=? WHERE id=?";
            PreparedStatement psUser = conn.prepareStatement(sqlUser);
            psUser.setString(1, ad.getPassword());
            psUser.setInt(2, ad.getId());
            psUser.executeUpdate();

            String sqlAdmin = "UPDATE admin SET ho_ten=?, gioi_tinh=?, email=?, sdt=? WHERE id=?";
            PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin);
            psAdmin.setString(1, ad.getHoten());
            psAdmin.setString(2, ad.getGioitinh());
            psAdmin.setString(3, ad.getEmail());
            psAdmin.setString(4, ad.getSdt());
            psAdmin.setInt(5, ad.getId());
            int res = psAdmin.executeUpdate();

            conn.commit();
            return res > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
