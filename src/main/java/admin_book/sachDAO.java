package admin_book;

import connect.connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class sachDAO {

    public static List<sach> getAll() {
        List<sach> list = new ArrayList<>();
        connect db = new connect();
        Connection conn = db.getConnection();

        String sql = "SELECT s.ma_sach, s.ten_sach, s.ma_loai_sach, s.ma_tg, " +
                     "s.nha_xb, s.nam_xb, s.so_luong, s.tinh_trang, s.mo_ta, s.image, " +
                     "ls.ten_loai_sach, tg.ten_tg " +
                     "FROM sach s " +
                     "JOIN loai_sach ls ON s.ma_loai_sach = ls.ma_loai_sach " +
                     "JOIN tac_gia tg ON s.ma_tg = tg.ma_tg";

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    sach s = new sach(
                        rs.getString("ma_sach"),
                        rs.getString("ten_sach"),
                        rs.getString("ma_loai_sach"),
                        rs.getInt("ma_tg"),
                        rs.getString("nha_xb"),
                        rs.getInt("nam_xb"),
                        rs.getInt("so_luong"),
                        rs.getBoolean("tinh_trang"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getString("ten_loai_sach"),
                        rs.getString("ten_tg")
                    );
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
        return list;
    }

    public static boolean insert(sach s) {
        connect db = new connect();
        Connection conn = db.getConnection();
        String sql = "INSERT INTO sach(ma_sach, ten_sach, ma_loai_sach, ma_tg, nha_xb, nam_xb, so_luong, tinh_trang, mo_ta, image) VALUES ('"
                + s.getMa_sach() + "', '"
                + s.getTen_sach() + "', '"
                + s.getMa_loai_sach() + "', "
                + s.getMa_tg() + ", '"
                + s.getNha_xb() + "', "
                + s.getNam_xb() + ", "
                + s.getSo_luong() + ", "
                + (s.isTinh_trang() ? 1 : 0) + ", '"
                + s.getMo_ta() + "', '"
                + s.getImage() + "')";
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                int i = stmt.executeUpdate(sql);
                return i > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
        return false;
    }

    public static boolean update(sach s) {
        connect db = new connect();
        Connection conn = db.getConnection();

        String sql = "UPDATE sach SET "
                + "ten_sach = '" + s.getTen_sach() + "', "
                + "ma_loai_sach = '" + s.getMa_loai_sach() + "', "
                + "ma_tg = " + s.getMa_tg() + ", "
                + "nha_xb = '" + s.getNha_xb() + "', "
                + "nam_xb = " + s.getNam_xb() + ", "
                + "so_luong = " + s.getSo_luong() + ", "
                + "tinh_trang = " + (s.isTinh_trang() ? 1 : 0) + ", "
                + "mo_ta = '" + s.getMo_ta() + "', "
                + "image = '" + s.getImage() + "' "
                + "WHERE ma_sach = '" + s.getMa_sach() + "'";

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                int i = stmt.executeUpdate(sql);
                return i > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
        }
        return false;
    }
    // Xóa sách theo mã sách, có kiểm tra ràng buộc
    public static boolean remove(String ma_sach) {
        connect db = new connect();
        Connection conn = db.getConnection();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                String checkMuon = "SELECT COUNT(*) AS kiemtra FROM phieu_muon WHERE ma_sach = '" + ma_sach + "'";
                ResultSet rs1 = stmt.executeQuery(checkMuon);
                if (rs1.next() && rs1.getInt("kiemtra") > 0) {
                    System.out.println("Sách đang có trong phiếu mượn, không thể xóa");
                    return false;
                }

                String checkYT = "SELECT COUNT(*) AS kiemtra FROM yeu_thich WHERE ma_sach = '" + ma_sach + "'";
                ResultSet rs2 = stmt.executeQuery(checkYT);
                if (rs2.next() && rs2.getInt("kiemtra") > 0) {
                    System.out.println("Sách đang có trong danh sách yêu thích, không thể xóa");
                    return false;
                }

                String sql = "DELETE FROM sach WHERE ma_sach = '" + ma_sach + "'";
                int i = stmt.executeUpdate(sql);
                return i > 0;
            } else {
                System.out.println("Kết nối thất bại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    // Tìm kiếm sách theo nhiều tiêu chí
public static List<sach> search(String keyword) {
    List<sach> list = new ArrayList<>();
    connect db = new connect();
    Connection conn = db.getConnection();

    // Câu SQL: tìm theo tên sách, loại sách, tác giả, NXB, năm XB
    String sql = "SELECT s.ma_sach, s.ten_sach, s.ma_loai_sach, s.ma_tg, " +
                 "s.nha_xb, s.nam_xb, s.so_luong, s.tinh_trang, s.mo_ta, s.image, " +
                 "ls.ten_loai_sach, tg.ten_tg " +
                 "FROM sach s " +
                 "JOIN loai_sach ls ON s.ma_loai_sach = ls.ma_loai_sach " +
                 "JOIN tac_gia tg ON s.ma_tg = tg.ma_tg " +
                 "WHERE s.ten_sach LIKE '%" + keyword + "%' " +
                 "   OR ls.ten_loai_sach LIKE '%" + keyword + "%' " +
                 "   OR tg.ten_tg LIKE '%" + keyword + "%' " +
                 "   OR s.nha_xb LIKE '%" + keyword + "%' " +
                 "   OR s.nam_xb LIKE '%" + keyword + "%'";

    try {
        if (conn != null) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sach s = new sach(
                    rs.getString("ma_sach"),
                    rs.getString("ten_sach"),
                    rs.getString("ma_loai_sach"),
                    rs.getInt("ma_tg"),
                    rs.getString("nha_xb"),
                    rs.getInt("nam_xb"),
                    rs.getInt("so_luong"),
                    rs.getBoolean("tinh_trang"),
                    rs.getString("mo_ta"),
                    rs.getString("image"),
                    rs.getString("ten_loai_sach"),
                    rs.getString("ten_tg")
                );
                list.add(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { conn.close(); } catch (Exception e) {}
    }
    return list;
}


    public static sach findById(String maSach) {
        sach s = null;
        String sql = "SELECT s.ma_sach, s.ten_sach, s.ma_loai_sach, s.ma_tg, " +
                     "s.nha_xb, s.nam_xb, s.so_luong, s.tinh_trang, s.mo_ta, s.image, " +
                     "ls.ten_loai_sach, tg.ten_tg " +
                     "FROM sach s " +
                     "JOIN loai_sach ls ON s.ma_loai_sach = ls.ma_loai_sach " +
                     "JOIN tac_gia tg ON s.ma_tg = tg.ma_tg " +
                     "WHERE s.ma_sach = '" + maSach + "'";

        connect db = new connect();
        try (Connection conn = db.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                s = new sach(
                    rs.getString("ma_sach"),
                    rs.getString("ten_sach"),
                    rs.getString("ma_loai_sach"),
                    rs.getInt("ma_tg"),
                    rs.getString("nha_xb"),
                    rs.getInt("nam_xb"),
                    rs.getInt("so_luong"),
                    rs.getBoolean("tinh_trang"),
                    rs.getString("mo_ta"),
                    rs.getString("image"),
                    rs.getString("ten_loai_sach"),
                    rs.getString("ten_tg")
                );
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
}
    public static List<sach> getByTheLoai(String ma_loai_sach) {
    List<sach> list = new ArrayList<>();
    connect db = new connect();
    Connection conn = db.getConnection();

    String sql = "SELECT s.ma_sach, s.ten_sach, s.ma_loai_sach, s.ma_tg, " +
                 "s.nha_xb, s.nam_xb, s.so_luong, s.tinh_trang, s.mo_ta, s.image, " +
                 "ls.ten_loai_sach, tg.ten_tg " +
                 "FROM sach s " +
                 "JOIN loai_sach ls ON s.ma_loai_sach = ls.ma_loai_sach " +
                 "JOIN tac_gia tg ON s.ma_tg = tg.ma_tg " +
                 "WHERE s.ma_loai_sach = '" + ma_loai_sach + "'";

    try {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            sach s = new sach(
                rs.getString("ma_sach"),
                rs.getString("ten_sach"),
                rs.getString("ma_loai_sach"),
                rs.getInt("ma_tg"),
                rs.getString("nha_xb"),
                rs.getInt("nam_xb"),
                rs.getInt("so_luong"),
                rs.getBoolean("tinh_trang"),
                rs.getString("mo_ta"),
                rs.getString("image"),
                rs.getString("ten_loai_sach"),
                rs.getString("ten_tg")
            );
            list.add(s);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { conn.close(); } catch (Exception e) {}
    }
    return list;
}

    
    

}

