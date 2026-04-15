package gui;

import dao.UserDAO;
import qltv.LoginFrame;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

    private JTextField txtUser, txtEmail, txtHoTen, txtLop, txtDiaChi, txtSdt;
    private JComboBox<String> cbGioiTinh;
    private JPasswordField txtPass, txtConfirmPass;
    private UserDAO userDAO = new UserDAO();

    public RegisterGUI() {
        setTitle("Đăng Ký Tài Khoản");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        ThemeUtils.addExitConfirmation(this);

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
        card.setBorder(new EmptyBorder(25, 45, 25, 45));
        card.setPreferredSize(new Dimension(650, 720));
        card.setMaximumSize(new Dimension(650, 720));

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

        card.add(Box.createVerticalStrut(15));

        // Group 1: Mã SV + Họ tên
        JPanel row1 = new JPanel(new GridLayout(1, 2, 20, 0));
        row1.setOpaque(false);

        JPanel pnlMaSV = new JPanel(new BorderLayout());
        pnlMaSV.setOpaque(false);
        pnlMaSV.add(ThemeUtils.createLabel("Mã sinh viên (B21DCCN123)"), BorderLayout.NORTH);
        txtUser = ThemeUtils.createTextField(20);
        pnlMaSV.add(txtUser, BorderLayout.CENTER);

        JPanel pnlHoTen = new JPanel(new BorderLayout());
        pnlHoTen.setOpaque(false);
        pnlHoTen.add(ThemeUtils.createLabel("Họ và tên"), BorderLayout.NORTH);
        txtHoTen = ThemeUtils.createTextField(20);
        pnlHoTen.add(txtHoTen, BorderLayout.CENTER);

        row1.add(pnlMaSV);
        row1.add(pnlHoTen);
        card.add(row1);

        card.add(Box.createVerticalStrut(10));

        // Group 2: Lớp + Email
        JPanel row2 = new JPanel(new GridLayout(1, 2, 20, 0));
        row2.setOpaque(false);

        JPanel pnlLop = new JPanel(new BorderLayout());
        pnlLop.setOpaque(false);
        pnlLop.add(ThemeUtils.createLabel("Lớp"), BorderLayout.NORTH);
        txtLop = ThemeUtils.createTextField(20);
        pnlLop.add(txtLop, BorderLayout.CENTER);

        JPanel pnlEmail = new JPanel(new BorderLayout());
        pnlEmail.setOpaque(false);
        pnlEmail.add(ThemeUtils.createLabel("Email"), BorderLayout.NORTH);
        txtEmail = ThemeUtils.createTextField(20);
        pnlEmail.add(txtEmail, BorderLayout.CENTER);

        row2.add(pnlLop);
        row2.add(pnlEmail);
        card.add(row2);

        card.add(Box.createVerticalStrut(10));

        // Group 3: Giới tính + SĐT
        JPanel row3 = new JPanel(new GridLayout(1, 2, 20, 0));
        row3.setOpaque(false);

        JPanel pnlGioiTinh = new JPanel(new BorderLayout());
        pnlGioiTinh.setOpaque(false);
        pnlGioiTinh.add(ThemeUtils.createLabel("Giới tính"), BorderLayout.NORTH);
        cbGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ", "Khác" });
        cbGioiTinh.setFont(ThemeUtils.FONT_BODY);
        cbGioiTinh.setPreferredSize(new Dimension(0, 38));
        pnlGioiTinh.add(cbGioiTinh, BorderLayout.CENTER);

        JPanel pnlSdt = new JPanel(new BorderLayout());
        pnlSdt.setOpaque(false);
        pnlSdt.add(ThemeUtils.createLabel("Số điện thoại"), BorderLayout.NORTH);
        txtSdt = ThemeUtils.createTextField(20);
        pnlSdt.add(txtSdt, BorderLayout.CENTER);

        row3.add(pnlGioiTinh);
        row3.add(pnlSdt);
        card.add(row3);

        card.add(Box.createVerticalStrut(10));

        // Group 4: Địa chỉ (Full width)
        JPanel pnlDiaChi = new JPanel(new BorderLayout());
        pnlDiaChi.setOpaque(false);
        pnlDiaChi.add(ThemeUtils.createLabel("Địa chỉ hiện tại"), BorderLayout.NORTH);
        txtDiaChi = ThemeUtils.createTextField(20);
        pnlDiaChi.add(txtDiaChi, BorderLayout.CENTER);
        card.add(pnlDiaChi);

        card.add(Box.createVerticalStrut(10));

        // Group 5: Mật khẩu
        JPanel row5 = new JPanel(new GridLayout(1, 2, 20, 0));
        row5.setOpaque(false);

        JPanel pnlPass = new JPanel(new BorderLayout());
        pnlPass.setOpaque(false);
        pnlPass.add(ThemeUtils.createLabel("Mật khẩu"), BorderLayout.NORTH);
        txtPass = ThemeUtils.createPasswordField(20);
        pnlPass.add(txtPass, BorderLayout.CENTER);

        JPanel pnlConfirm = new JPanel(new BorderLayout());
        pnlConfirm.setOpaque(false);
        pnlConfirm.add(ThemeUtils.createLabel("Nhập lại mật khẩu"), BorderLayout.NORTH);
        txtConfirmPass = ThemeUtils.createPasswordField(20);
        pnlConfirm.add(txtConfirmPass, BorderLayout.CENTER);

        row5.add(pnlPass);
        row5.add(pnlConfirm);
        card.add(row5);

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
        String u = txtUser.getText().trim().toUpperCase();
        String e = txtEmail.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String lop = txtLop.getText().trim();
        String gioiTinh = (String) cbGioiTinh.getSelectedItem();
        String diaChi = txtDiaChi.getText().trim();
        String sdt = txtSdt.getText().trim();
        String p = new String(txtPass.getPassword());
        String cp = new String(txtConfirmPass.getPassword());

        if (u.isEmpty() || e.isEmpty() || hoTen.isEmpty() || lop.isEmpty() || diaChi.isEmpty() || sdt.isEmpty()
                || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tất cả các trường thông tin!");
            return;
        }

        // Kiểm tra định dạng BxxDCyyzzz (Ví dụ: B21DCCN123)
        String regex = "^B\\d{2}DC[A-Z]{2}\\d{3}$";
        if (!u.matches(regex)) {
            JOptionPane.showMessageDialog(this,
                    "Mã sinh viên không đúng định dạng!\nVí dụ đúng: B21DCCN123 (B + 2 số + DC + 2 chữ + 3 số)");
            return;
        }

        if (!p.equals(cp)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
            return;
        }

        // Kiểm tra độ mạnh mật khẩu
        boolean hasUppercase = !p.equals(p.toLowerCase());
        boolean hasSpecial = p.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        boolean isAllNumbers = p.matches("^[0-9]+$");

        if (isAllNumbers || !hasUppercase || !hasSpecial) {
            StringBuilder errorMsg = new StringBuilder("Mật khẩu không đạt yêu cầu bảo mật:\n");
            if (isAllNumbers)
                errorMsg.append("- Mật khẩu không được chỉ chứa toàn số.\n");
            if (!hasUppercase)
                errorMsg.append("- Phải có ít nhất một chữ cái viết hoa.\n");
            if (!hasSpecial)
                errorMsg.append("- Phải có ít nhất một ký tự đặc biệt (!@#$%^...).\n");

            JOptionPane.showMessageDialog(this, errorMsg.toString(), "Lỗi bảo mật", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean result = userDAO.registerUser(u, p, e, 1, hoTen, lop, gioiTinh, diaChi, sdt);

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
                    0, getHeight(), ThemeUtils.GRADIENT_PRIMARY[1]);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.dispose();
        }
    }
}
