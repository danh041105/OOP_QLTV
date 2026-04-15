package model;

public class SessionManager {
    public static User currentUser;
    public static String currentMaSv; // Lưu mã thực tế (SV01, SV02...)
    public static String currentRole;  // "admin" hoặc "sinhvien"

    public static String getMaNguoiDung() {
        if (currentMaSv != null) {
            return currentMaSv;
        }
        if (currentUser != null) {
            return currentUser.getUsername();
        }
        return null;
    }

    public static String getCurrentRole() {
        return currentRole;
    }

    public static void setCurrentRole(String role) {
        currentRole = role;
    }

    // Đăng xuất ra khỏi tài khoản
    public static void logout() {
        currentUser = null;
        currentMaSv = null;
        currentRole = null;
    }
}