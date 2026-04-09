package qltv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class quenmk extends JDialog {

    private JTextField txtEmail;
    private userDAO userDAO = new userDAO();

    public quenmk(JFrame parent) {
        super(parent, "Quên mật khẩu", true); 
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel lblInstruct = new JLabel("Nhập email đã đăng ký để nhận lại mật khẩu:");
        lblInstruct.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtEmail = new JTextField();
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JButton btnSend = new JButton("Gửi mật khẩu về Email");
        btnSend.setBackground(Color.decode("#003366"));
        btnSend.setForeground(Color.WHITE);
        btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSend.addActionListener(e -> handleSendPassword());

        panel.add(lblInstruct);
        panel.add(Box.createVerticalStrut(15));
        panel.add(txtEmail);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnSend);

        add(panel);
    }

    private void handleSendPassword() {
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Email!");
            return;
        }

        String password = userDAO.getPasswordByEmail(email);

        if (password == null) {
            JOptionPane.showMessageDialog(this, "Email này chưa được đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                emailsender.sendPassword(email, password);
                this.setCursor(Cursor.getDefaultCursor());
                
                JOptionPane.showMessageDialog(this, "Mật khẩu đã được gửi vào email: " + email);
                this.dispose(); 
            } catch (Exception ex) {
                this.setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(this, "Lỗi gửi mail: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}