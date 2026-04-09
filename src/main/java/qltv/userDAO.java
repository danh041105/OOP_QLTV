package qltv;

import connect.connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDAO {

    public user checkLogin(String u, String p) {
        user user = null;
        String sql = "SELECT username, password, role FROM user WHERE username = ? AND password = ?";

        connect myConnect = new connect();

        try (Connection conn = myConnect.getConnection()) {

            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, u);
                ps.setString(2, p);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    user = new user();
                    user.setUsername(rs.getString("username"));
                    user.setRole(rs.getString("role"));
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

        connect myConnect = new connect();

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

        connect myConnect = new connect();

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
        connect cn = new connect();
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
        connect myConnect = new connect();

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

    //============================
    public boolean sv_login(String msv) {
        connect cn = new connect();
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = cn.getConnection();
            String sql = "INSERT INTO dang_nhap (ma_sv) VALUES (?)";

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, msv);
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

    public boolean SV_logout() {
        connect cn = new connect();
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = cn.getConnection();
            String sql = "delete from dang_nhap";

            pstm = conn.prepareStatement(sql);
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

    public String getMSV_isLogin() {
        connect cn = new connect();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = cn.getConnection();
            String sql = "select ma_sv from dang_nhap limit 1";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("ma_sv");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean admin_login(String ma_admin) {
        connect cn = new connect();
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = cn.getConnection();
            String sql = "INSERT INTO admin_islogin (ma_admin) VALUES (?)";

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, ma_admin);
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

    public boolean admin_logout() {
        connect cn = new connect();
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = cn.getConnection();
            String sql = "delete from admin_islogin";

            pstm = conn.prepareStatement(sql);
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

    public String getMaADMIN_isLogin() {
        connect cn = new connect();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = cn.getConnection();
            String sql = "select ma_admin from admin_islogin limit 1";

            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("ma_admin");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
