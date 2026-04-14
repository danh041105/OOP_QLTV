package qltv;

import dao.UserDAO;
import gui.AdminGUI;
import gui.RegisterGUI;
import gui.SinhVienGUI;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private UserDAO userDAO = new UserDAO();

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Đăng nhập hệ thống");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundImagePanel mainPanel = new BackgroundImagePanel("/images/login_banner.jpg");
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);

        JPanel loginCard = createLoginCard();
        mainPanel.add(loginCard);
    }

    private JPanel createLoginCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(40, 40, 40, 40)); // Căn lề trong

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.decode("#333333"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = new JLabel("Tài khoản");
        lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsername = new JTextField(20);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPass = new JLabel("Mật khẩu");
        lblPass.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtPassword = new JPasswordField(20);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(Color.decode("#00CC66")); // Màu xanh lá
        btnLogin.setForeground(Color.WHITE); // Chữ trắng
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setOpaque(true);

        btnLogin.setPreferredSize(new Dimension(200, 40));
        btnLogin.setMaximumSize(new Dimension(200, 40));

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(20));

        card.add(lblUser);
        card.add(Box.createVerticalStrut(5));
        card.add(txtUsername);

        card.add(Box.createVerticalStrut(15));

        card.add(lblPass);
        card.add(Box.createVerticalStrut(5));
        card.add(txtPassword);

        card.add(Box.createVerticalStrut(25));
        card.add(btnLogin);

        card.add(Box.createVerticalStrut(10));

        JButton btnRegister = new JButton("Đăng ký tài khoản mới");
        btnRegister.setFont(new Font("Arial", Font.ITALIC, 12));
        btnRegister.setForeground(Color.BLUE);
        btnRegister.setContentAreaFilled(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame.this.setVisible(false);

                new RegisterGUI().setVisible(true);
            }
        });

        card.add(btnRegister);

        JButton btnForgot = new JButton("Quên mật khẩu?");
        btnForgot.setFont(new Font("Arial", Font.PLAIN, 12));
        btnForgot.setForeground(Color.RED);
        btnForgot.setContentAreaFilled(false);
        btnForgot.setBorderPainted(false);
        btnForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnForgot.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnForgot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new QuenMk(LoginFrame.this).setVisible(true);
            }
        });

        card.add(btnForgot);
        // ------------------------------------

        return card;
    }

    // Hàm xử lý logic
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
            return;
        }

        User user = userDAO.checkLogin(username, password);

        if (user != null) {
            int role = user.getRole();
            this.dispose();

            // Lưu session vào RAM
            model.SessionManager.currentUser = user;

            // Logic phân quyền: 0 là Admin, 1 là SV
            if (role == 0) {
                new AdminGUI(user.getUsername()).setVisible(true);
            } else {
                new SinhVienGUI(user.getUsername()).setVisible(true);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    class BackgroundImagePanel extends JPanel {
        private Image backgroundImage;

        public BackgroundImagePanel(String imagePath) {
            try {
                java.net.URL imgURL = getClass().getResource(imagePath);
                if (imgURL != null)
                    backgroundImage = javax.imageio.ImageIO.read(imgURL);
            } catch (Exception e) {
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }
}
