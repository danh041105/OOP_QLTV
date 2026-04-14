package qltv;

import dao.UserDAO;
import gui.AdminGUI;
import gui.RegisterGUI;
import gui.SinhVienGUI;
import model.User;
import utils.ThemeUtils;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Full-screen gradient background panel
        BackgroundImagePanel mainPanel = new BackgroundImagePanel();
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);

        // Glassmorphism login card
        JPanel loginCard = createLoginCard();
        mainPanel.add(loginCard);
    }

    private JPanel createLoginCard() {
        // Glassmorphism card: semi-transparent white with rounded corners
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // White semi-transparent background
                g2.setColor(new Color(255, 255, 255, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Subtle border
                g2.setColor(new Color(255, 255, 255, 180));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);

                // Shadow-like bottom border for depth
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(0, getHeight() - 8, getWidth(), 8, 0, 0);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(35, 45, 35, 45));
        card.setPreferredSize(new Dimension(400, 480));
        card.setMaximumSize(new Dimension(400, 480));

        // Book icon
        JLabel lblIcon = new JLabel("\uD83D\uDCDA");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblIcon);
        card.add(Box.createVerticalStrut(8));

        // Title
        JLabel lblTitle = new JLabel("THƯ VIỆN PTIT");
        lblTitle.setFont(ThemeUtils.FONT_TITLE);
        lblTitle.setForeground(ThemeUtils.TEXT_PRIMARY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(4));

        // Subtitle
        JLabel lblSubtitle = new JLabel("Đăng nhập để tiếp tục");
        lblSubtitle.setFont(ThemeUtils.FONT_BODY);
        lblSubtitle.setForeground(ThemeUtils.TEXT_MUTED);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblSubtitle);

        card.add(Box.createVerticalStrut(25));

        // Tài khoản label + field
        JLabel lblUser = ThemeUtils.createLabel("Tài khoản");
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblUser);
        card.add(Box.createVerticalStrut(5));

        txtUsername = ThemeUtils.createTextField(20);
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(txtUsername);

        card.add(Box.createVerticalStrut(15));

        // Mật khẩu label + field
        JLabel lblPass = ThemeUtils.createLabel("Mật khẩu");
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblPass);
        card.add(Box.createVerticalStrut(5));

        txtPassword = ThemeUtils.createPasswordField(20);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(txtPassword);

        card.add(Box.createVerticalStrut(25));

        // ĐĂNG NHẬP primary button
        JButton btnLogin = ThemeUtils.createPrimaryButton("ĐĂNG NHẬP");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        card.add(btnLogin);

        card.add(Box.createVerticalStrut(15));

        // Link buttons row
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        linkPanel.setOpaque(false);
        linkPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        linkPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRegister = ThemeUtils.createLinkButton("Đăng ký tài khoản mới", ThemeUtils.PRIMARY);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame.this.setVisible(false);
                new RegisterGUI().setVisible(true);
            }
        });
        linkPanel.add(btnRegister);

        JButton btnForgot = ThemeUtils.createLinkButton("Quên mật khẩu?", ThemeUtils.ACCENT);
        btnForgot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QuenMk(LoginFrame.this).setVisible(true);
            }
        });
        linkPanel.add(btnForgot);

        card.add(linkPanel);

        return card;
    }

    // ===== Business Logic (preserved) =====

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

    // ===== Background Gradient Panel =====

    class BackgroundImagePanel extends JPanel {
        private Image backgroundImage;

        public BackgroundImagePanel() {
            try {
                java.net.URL imgURL = getClass().getResource("/images/login_banner.jpg");
                if (imgURL != null)
                    backgroundImage = javax.imageio.ImageIO.read(imgURL);
            } catch (Exception e) {
                // Fallback: gradient will be painted regardless
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Paint gradient background (blue-to-indigo)
            GradientPaint gp = new GradientPaint(
                    0, 0, ThemeUtils.GRADIENT_PRIMARY[0],
                    0, getHeight(), ThemeUtils.GRADIENT_PRIMARY[1]
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Overlay background image if available (semi-transparent)
            if (backgroundImage != null) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
                g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }

            g2.dispose();
        }
    }
}
