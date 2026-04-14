package dao;

import connect.Connect;
import model.SinhVien;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SinhVienDAO {
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
                rs.getString("sdt"));
        sv.setUsername(rs.getString("username"));
        sv.setPassword(rs.getString("password"));
        return sv;
    }

    public SinhVien getByUserId(int userId) {
        String sql = "SELECT u.id, u.email, sv.*, u.username, u.password "
                + "FROM user u JOIN sinh_vien sv ON u.id = sv.id "
                + "WHERE u.id = ?";
        try (Connection conn = new Connect().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return readResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<SinhVien> getAll() {
        ArrayList<SinhVien> list = new ArrayList<>();
        String sql = "SELECT u.id, u.email, sv.*, u.username, u.password "
                + "FROM user u JOIN sinh_vien sv ON u.id = sv.id "
                + "WHERE u.role = 1";
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

    public ArrayList<SinhVien> search(String txt) {
        ArrayList<SinhVien> list = new ArrayList<>();
        String sql = "SELECT u.id, u.email, sv.*, u.username, u.password "
                + "FROM user u JOIN sinh_vien sv ON u.id = sv.id "
                + "WHERE u.role = 1 AND (sv.ma_sv LIKE ? OR sv.ho_ten LIKE ?)";
        try (Connection conn = new Connect().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + txt + "%");
            ps.setString(2, "%" + txt + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(readResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean delete(int id) {
        String sqlUser = "DELETE FROM user WHERE id = ?";
        try (Connection conn = new Connect().getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlUser)) {
            ps.setInt(1, id);
            int res = ps.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
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
        if (!checkDinhDang(sv))
            return false;
        try (Connection conn = new Connect().getConnection()) {

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

            // 1. Thêm User BẢO GỒM EMAIL
            PreparedStatement psU = conn.prepareStatement(
                    "INSERT INTO user(username, password, email, role) VALUES(?,?,?,1)",
                    Statement.RETURN_GENERATED_KEYS);
            psU.setString(1, user);
            psU.setString(2, pass);
            psU.setString(3, sv.getEmail());
            psU.executeUpdate();

            ResultSet rs = psU.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            // 2. Thêm Sinh Viên KHÔNG CÓ EMAIL, gọt bớt 1 dấu hỏi
            String sql = "INSERT INTO sinh_vien(ma_sv, ho_ten, lop, gioi_tinh, ngay_sinh, dia_chi, sdt, id) "
                    + "VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sv.getMasv());
            ps.setString(2, sv.getHoten());
            ps.setString(3, sv.getLop());
            ps.setString(4, sv.getGioitinh());
            ps.setDate(5, new java.sql.Date(sv.getNgaysinh().getTime()));
            ps.setString(6, sv.getDiachi());
            ps.setString(7, sv.getSdt()); // Đôn sdt lên thay chỗ email
            ps.setInt(8, id);

            int res = ps.executeUpdate();
            conn.commit();
            return res > 0;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm: " + e.getMessage());
            return false;
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
        if (!checkDinhDang(sv))
            return false;

        try (Connection conn = new Connect().getConnection()) {
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

            // 1. Cập nhật User (Thêm Email vào đây)
            String sqlUser = "UPDATE user SET password=?, email=? WHERE id=?";
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                psUser.setString(1, sv.getPassword());
                psUser.setString(2, sv.getEmail());
                psUser.setInt(3, sv.getId());
                psUser.executeUpdate();
            }

            // 2. Cập nhật Sinh Viên (Bỏ Email ở đây)
            String sqlSV = "UPDATE sinh_vien SET ho_ten=?, lop=?, gioi_tinh=?, ngay_sinh=?, dia_chi=?, sdt=? WHERE id=?";
            int res;
            try (PreparedStatement psSV = conn.prepareStatement(sqlSV)) {
                psSV.setString(1, sv.getHoten());
                psSV.setString(2, sv.getLop());
                psSV.setString(3, sv.getGioitinh());
                psSV.setDate(4, new java.sql.Date(sv.getNgaysinh().getTime()));
                psSV.setString(5, sv.getDiachi());
                psSV.setString(6, sv.getSdt());
                psSV.setInt(7, sv.getId());
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