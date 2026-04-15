package qltv;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import utils.Env;

public class EmailSender {

    public static void sendPassword(String toEmail, String passwordContent) {
        final String user = Env.get("EMAIL_USER");
        final String password = Env.get("EMAIL_PASSWORD");
        
        if (user == null || password == null) {
            throw new RuntimeException("Cấu hình Email trống! Vui lòng kiểm tra file .env");
        }

        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        
        // Bảo mật và Timeout
        props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Khôi phục mật khẩu - Quản Lý Thư Viện");
            message.setText("Chào bạn,\n\nMật khẩu của bạn là: " + passwordContent 
                    + "\n\nVui lòng đăng nhập và đổi lại mật khẩu để bảo mật.");

            Transport.send(message);
            System.out.println("Gửi mail thành công!");

        } catch (MessagingException e) {
            System.err.println("Lỗi SMTP: " + e.getMessage());
            throw new RuntimeException("Lỗi: " + e.getMessage(), e);
        }
    }
}