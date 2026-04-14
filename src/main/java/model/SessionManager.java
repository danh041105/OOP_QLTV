package model;

public class SessionManager {
    public static User currentUser;

    public static String getMaNguoiDung() {
        if (currentUser != null) {
            return currentUser.getUsername();
        }
        return null;
    }

    // Đăng xuất ra khỏi tài khoản
    public static void logout() {
        currentUser = null;
    }
}