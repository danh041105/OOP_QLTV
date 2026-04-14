package gui;

import dao.UserDAO;
import qltv.LoginFrame;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

    private JTextField txtUser, txtEmail;
    private JPasswordField txtPass, txtConfirmPass;
    private UserDAO userDAO = new UserDAO();

    public RegisterGUI() {
        setTitle("Đăng Ký Tài Khoản");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(900, 650);
        setLocationRelativeTo(null);

        // Full-screen gradient background
        GradientBackgroundPanel mainPanel = new GradientBackgroundPanel();
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);

        // Glassmorphism registration card
        JPanel registerCard = createRegisterCard();
        mainPanel.add(registerCard);
    }

    private JPanel createRegisterCard() {
        // Glassmorphism card: semi-transparent white with rounded corners
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // White semi-transparent background
                g2.setColor(new Color(255, 255, 255, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Subtle glass border
                g2.setColor(new Color(255, 255, 255, 180));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

                // Shadow-like bottom edge for depth
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(0, getHeight() - 8, getWidth(), 8, 0, 0);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(35, 45, 35, 45));
        card.setPreferredSize(new Dimension(420, 560));
        card.setMaximumSize(new Dimension(420, 560));

        // Icon
        JLabel lblIcon = new JLabel("\uD83D\uDCDD");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 44));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblIcon);
        card.add(Box.createVerticalStrut(8));

        // Title
        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN");
        lblTitle.setFont(ThemeUtils.FONT_HEADING);
        lblTitle.setForeground(ThemeUtils.TEXT_PRIMARY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(4));

        // Subtitle
        JLabel lblSubtitle = new JLabel("Tạo tài khoản mới để sử dụng hệ thống");
        lblSubtitle.setFont(ThemeUtils.FONT_BODY);
        lblSubtitle.setForeground(ThemeUtils.TEXT_MUTED);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblSubtitle);

        card.add(Box.createVerticalStrut(20));

        // Tên đăng nhập
        JLabel lblUser = ThemeUtils.createLabel("Tên đăng nhập");
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblUser);
        card.add(Box.createVerticalStrut(5));
        txtUser = ThemeUtils.createTextField(20);
        txtUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(txtUser);

        card.add(Box.createVerticalStrut(12));

        // Email
        JLabel lblEmail = ThemeUtils.createLabel("Email");
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblEmail);
        card.add(Box.createVerticalStrut(5));
        txtEmail = ThemeUtils.createTextField(20);
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(txtEmail);

        card.add(Box.createVerticalStrut(12));

        // Mật khẩu
        JLabel lblPass = ThemeUtils.createLabel("Mật khẩu");
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblPass);
        card.add(Box.createVerticalStrut(5));
        txtPass = ThemeUtils.createPasswordField(20);
        txtPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(txtPass);

        card.add(Box.createVerticalStrut(12));

        // Nhập lại mật khẩu
        JLabel lblConfirmPass = ThemeUtils.createLabel("Nhập lại mật khẩu");
        lblConfirmPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblConfirmPass);
        card.add(Box.createVerticalStrut(5));
        txtConfirmPass = ThemeUtils.createPasswordField(20);
        txtConfirmPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtConfirmPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(txtConfirmPass);

        card.add(Box.createVerticalStrut(22));

        // ĐĂNG KÝ NGAY primary button
        JButton btnRegister = ThemeUtils.createPrimaryButton("ĐĂNG KÝ NGAY");
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnRegister.addActionListener((ActionEvent e) -> handleRegister());
        card.add(btnRegister);

        card.add(Box.createVerticalStrut(15));

        // Quay lại đăng nhập link (DANGER color)
        JButton btnBack = ThemeUtils.createLinkButton("Quay lại đăng nhập", ThemeUtils.DANGER);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
        card.add(btnBack);

        return card;
    }

    // ===== Business Logic (preserved) =====

    private void handleRegister() {
        String u = txtUser.getText().trim();
        String e = txtEmail.getText().trim();
        String p = new String(txtPass.getPassword());
        String cp = new String(txtConfirmPass.getPassword());

        if (u.isEmpty() || e.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        System.out.println("INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)");
        System.out.println(u + " - " + e + " - " + p + " - " + cp);
        if (!p.equals(cp)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
            return;
        }

        boolean result = userDAO.registerUser(u, p, e, 1);

        if (result) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy đăng nhập.");
            this.dispose();
            new LoginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại. Tên đăng nhập có thể đã tồn tại.");
        }
    }

    // ===== Background Gradient Panel =====

    class GradientBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Blue-to-indigo gradient
            GradientPaint gp = new GradientPaint(
                    0, 0, ThemeUtils.GRADIENT_PRIMARY[0],
                    0, getHeight(), ThemeUtils.GRADIENT_PRIMARY[1]
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();
        }
    }
}
