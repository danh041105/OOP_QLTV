package qltv;

import dao.UserDAO;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QuenMk extends JFrame {
    private JTextField txtEmail;
    private UserDAO userDAO = new UserDAO();

    public QuenMk() {
        setTitle("Quên mật khẩu");
        setSize(600, 450);
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(ThemeUtils.BG_MAIN);
        this.setLayout(new BorderLayout(0, 0));
        ThemeUtils.addExitConfirmation(this);

        // ===== GRADIENT HEADER =====
        JPanel header = ThemeUtils.createGradientPanel(ThemeUtils.GRADIENT_PRIMARY, 80);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel headerContent = new JPanel(new BorderLayout(15, 0));
        headerContent.setOpaque(false);
        headerContent.setBorder(new EmptyBorder(20, 25, 20, 20));

        // Lock icon circle
        JPanel iconCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 40)); // semi-transparent white
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconCircle.setPreferredSize(new Dimension(48, 48));
        iconCircle.setLayout(new BorderLayout());
        iconCircle.setOpaque(false);
        JLabel lblIcon = new JLabel("🔒", JLabel.CENTER);
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        iconCircle.add(lblIcon, BorderLayout.CENTER);

        JLabel lblHeaderTitle = new JLabel("Quên mật khẩu?");
        lblHeaderTitle.setFont(ThemeUtils.FONT_HEADING);
        lblHeaderTitle.setForeground(ThemeUtils.TEXT_WHITE);

        headerContent.add(iconCircle, BorderLayout.WEST);
        headerContent.add(lblHeaderTitle, BorderLayout.CENTER);
        header.add(headerContent, BorderLayout.CENTER);

        // Close button on header
        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnClose.setForeground(ThemeUtils.TEXT_WHITE);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setBorder(new EmptyBorder(10, 15, 10, 15));
        btnClose.addActionListener(e -> dispose());
        header.add(btnClose, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);

        // ===== CONTENT CARD =====
        JPanel cardPanel = ThemeUtils.createCardPanel(25);
        cardPanel.setLayout(new BorderLayout(0, 15));

        // Instruction text with icon
        JPanel instructionPanel = new JPanel(new BorderLayout(8, 0));
        instructionPanel.setOpaque(false);
        instructionPanel.setBorder(new EmptyBorder(10, 5, 5, 5));

        JLabel lblInfoIcon = new JLabel("📧");
        lblInfoIcon.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        JLabel lblInstruct = new JLabel("<html><span style='font-size:13px; color:#64748b;'>"
                + "Nhập email đã đăng ký để nhận lại mật khẩu.<br>"
                + "Hệ thống sẽ gửi mật khẩu mới đến địa chỉ email của bạn."
                + "</span></html>");
        lblInstruct.setFont(ThemeUtils.FONT_BODY);
        lblInstruct.setForeground(ThemeUtils.TEXT_SECONDARY);

        instructionPanel.add(lblInfoIcon, BorderLayout.WEST);
        instructionPanel.add(lblInstruct, BorderLayout.CENTER);

        // Email input field with label
        JPanel emailFieldPanel = new JPanel(new BorderLayout(0, 6));
        emailFieldPanel.setOpaque(false);

        JLabel lblEmailLabel = new JLabel("Địa chỉ Email:");
        lblEmailLabel.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lblEmailLabel.setForeground(ThemeUtils.TEXT_PRIMARY);

        txtEmail = ThemeUtils.createTextField(0);
        txtEmail.setPreferredSize(new Dimension(500, 44));
        txtEmail.setFont(ThemeUtils.FONT_BODY);

        emailFieldPanel.add(lblEmailLabel, BorderLayout.NORTH);
        emailFieldPanel.add(txtEmail, BorderLayout.CENTER);

        // Buttons panel
        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        actionPanel.setOpaque(false);
        
        // Send button
        JButton btnSend = ThemeUtils.createPrimaryButton("GỬI MẬT KHẨU VỀ EMAIL");
        btnSend.setPreferredSize(new Dimension(500, 50));
        btnSend.setFont(ThemeUtils.FONT_BODY_BOLD);
        btnSend.addActionListener(e -> handleSendPassword());
        
        // Back button
        JButton btnBack = ThemeUtils.createSecondaryButton("← Quay lại đăng nhập");
        btnBack.setPreferredSize(new Dimension(500, 44));
        btnBack.addActionListener(e -> dispose());

        actionPanel.add(btnSend);
        actionPanel.add(btnBack);

        cardPanel.add(instructionPanel, BorderLayout.NORTH);
        cardPanel.add(emailFieldPanel, BorderLayout.CENTER);
        cardPanel.add(actionPanel, BorderLayout.SOUTH);

        // Wrap card in center panel
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        centerWrapper.setBackground(ThemeUtils.BG_MAIN);
        centerWrapper.setBorder(new EmptyBorder(0, 20, 20, 20));
        centerWrapper.add(cardPanel);
        this.add(centerWrapper, BorderLayout.CENTER);
    }

    private void handleSendPassword() {
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Email!");
            return;
        }

        // Kiểm tra email tồn tại
        if (!userDAO.checkEmailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email này chưa được đăng ký trong hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo mật khẩu mới ngẫu nhiên (6 ký tự)
        String newPassword = String.valueOf((int)((Math.random() * 900000) + 100000));

        // Background task to send email and update DB
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // 1. Cập nhật mật khẩu mới vào DB (đã hash)
                if (userDAO.updatePasswordByEmail(email, newPassword)) {
                    // 2. Gửi email mật khẩu rõ cho người dùng
                    EmailSender.sendPassword(email, newPassword);
                    return true;
                }
                return false;
            }

            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(QuenMk.this, 
                            "Mật khẩu mới đã được gửi vào email: " + email + "\nVui lòng kiểm tra hộp thư (bao gồm cả thư rác).");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(QuenMk.this, "Lỗi: Không thể cập nhật mật khẩu mới!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(QuenMk.this, "Lỗi gửi mail: " + ex.getCause().getMessage());
                    ex.printStackTrace();
                }
            }
        };

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        worker.execute();
    }
}
