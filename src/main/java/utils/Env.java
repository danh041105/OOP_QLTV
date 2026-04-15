package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Env {
    private static final Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(".env")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Cảnh báo: Không tìm thấy file .env! Sẽ sử dụng giá trị mặc định hệ thống.");
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
