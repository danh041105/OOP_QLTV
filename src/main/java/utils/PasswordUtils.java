package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Băm mật khẩu (Hash password)
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Kiểm tra mật khẩu (Verify password)
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            // Trường hợp mật khẩu trong DB chưa được hash (plain text)
            // Có thể return plainPassword.equals(hashedPassword) để hỗ trợ dữ liệu cũ 
            // Nhưng khuyến khích chuyển hết sang hash.
            return plainPassword.equals(hashedPassword);
        }
    }
}
