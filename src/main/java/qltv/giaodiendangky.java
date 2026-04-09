package qltv;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class giaodiendangky extends JFrame {

    private JTextField txtUser, txtEmail;
    private JPasswordField txtPass, txtConfirmPass;
    private userDAO userDAO = new userDAO(); 

    public giaodiendangky() {
        setTitle("Đăng Ký Tài Khoản");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.decode("#003366")); 
        add(mainPanel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lblTitle = new JLabel("ĐĂNG KÝ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(lblTitle);
        formPanel.add(Box.createVerticalStrut(20));
        
        addInput(formPanel, "Tên đăng nhập:", txtUser = new JTextField(20));
        addInput(formPanel, "Email:", txtEmail = new JTextField(20));
        addInput(formPanel, "Mật khẩu:", txtPass = new JPasswordField(20));
        addInput(formPanel, "Nhập lại mật khẩu:", txtConfirmPass = new JPasswordField(20));

        JButton btnRegister = new JButton("Đăng Ký Ngay");
        btnRegister.setBackground(Color.decode("#00CC66"));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(200, 40));
        
        btnRegister.addActionListener(e -> handleRegister());

        JButton btnBack = new JButton("Quay lại Đăng nhập");
        btnBack.setForeground(Color.RED);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnBack.addActionListener(e -> {
            this.dispose(); 
            new loginframe().setVisible(true); 
        });

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(btnRegister);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(btnBack);

        mainPanel.add(formPanel);
    }

    private void addInput(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        ((JComponent) field).setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    private void handleRegister() {
        String u = txtUser.getText().trim();
        String e = txtEmail.getText().trim();
        String p = new String(txtPass.getPassword());
        String cp = new String(txtConfirmPass.getPassword());

        if(u.isEmpty() || e.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        System.out.println("INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)");
        System.out.println(u + " - " + e + " - "+ p +" - " +cp);
        if(!p.equals(cp)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
            return;
        }

        boolean result = userDAO.registerUser(u, p, e, 1); 

        if(result) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy đăng nhập.");
            this.dispose();
            new loginframe().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại. Tên đăng nhập có thể đã tồn tại.");
        }
    }
}