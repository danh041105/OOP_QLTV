package qltv;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class emailsender {
    
    public static void sendPassword(String toEmail, String passwordContent) {
        String host = "smtp.gmail.com";

        final String user = "hamyngo175@gmail.com"; 
        final String password = "ftih ugiv dses ntqm";   
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

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
            message.setText("Chào bạn,\n\nMật khẩu của bạn là: " + passwordContent + "\n\nVui lòng đăng nhập và đổi lại mật khẩu để bảo mật.");

            Transport.send(message);
            System.out.println("Gửi mail thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }
}