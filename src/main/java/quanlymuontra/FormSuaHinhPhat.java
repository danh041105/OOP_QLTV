package quanlymuontra;
import dao.HinhPhatDAO;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormSuaHinhPhat extends JDialog {
    private JTextField txtSinhVien, tLyDo, tNgay, tHinhThuc;
    private JComboBox<String> cbTienDo, cbTinhTrang;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false;
    private int maHP;

    public FormSuaHinhPhat(Frame parent, Object[] data) {
        super(parent, "Cập nhật hình phạt", true);
        this.maHP = Integer.parseInt(data[0].toString());
        
        this.setSize(500, 680);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(ThemeUtils.BG_MAIN);
        ((JPanel) this.getContentPane()).setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setUndecorated(true);

        // ===== TITLE BAR with gradient =====
        JPanel titleBar = ThemeUtils.createGradientPanel(ThemeUtils.GRADIENT_PRIMARY, 60);
        titleBar.setLayout(new BorderLayout());
        titleBar.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel lblTitle = new JLabel("  CẬP NHẬT HÌNH PHẠT", JLabel.LEFT);
        lblTitle.setFont(ThemeUtils.FONT_HEADING);
        lblTitle.setForeground(ThemeUtils.TEXT_WHITE);
        lblTitle.setBorder(new EmptyBorder(15, 20, 15, 10));
        titleBar.add(lblTitle, BorderLayout.CENTER);

        // Close button on title bar
        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnClose.setForeground(ThemeUtils.TEXT_WHITE);
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setBorder(new EmptyBorder(10, 15, 10, 15));
        btnClose.addActionListener(e -> dispose());
        titleBar.add(btnClose, BorderLayout.EAST);
        this.add(titleBar, BorderLayout.NORTH);

        // ===== FORM CARD =====
        JPanel cardPanel = ThemeUtils.createCardPanel(25);
        cardPanel.setLayout(new BorderLayout(0, 0));

        // Icon row at top of card
        JPanel iconRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        iconRow.setOpaque(false);
        iconRow.setBorder(new EmptyBorder(5, 0, 15, 0));

        JPanel iconCircle = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ThemeUtils.PRIMARY_LIGHT);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconCircle.setPreferredSize(new Dimension(56, 56));
        iconCircle.setLayout(new BorderLayout());
        iconCircle.setOpaque(false);
        JLabel lblIcon = new JLabel("✏️", JLabel.CENTER);
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconCircle.add(lblIcon, BorderLayout.CENTER);
        iconRow.add(iconCircle);
        cardPanel.add(iconRow, BorderLayout.NORTH);

        // Form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        int gap = 12;

        // --- Row 1: Sinh viên (read-only) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Sinh viên:"));
        txtSinhVien = ThemeUtils.createTextField(0);
        txtSinhVien.setText(data[1] + " - " + data[2]);
        txtSinhVien.setEditable(false);
        txtSinhVien.setBackground(ThemeUtils.BG_TABLE_ALT);
        txtSinhVien.setForeground(ThemeUtils.TEXT_MUTED);
        txtSinhVien.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtSinhVien);

        // --- Row 2: Tình trạng (disabled combobox) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Tình trạng:"));
        cbTinhTrang = new JComboBox<>(new String[]{"Đã trả", "Đang mượn", "Trả chậm", "Quá hạn trả"});
        cbTinhTrang.setSelectedItem(data[3].toString());
        cbTinhTrang.setEnabled(false);
        cbTinhTrang.setBackground(ThemeUtils.BG_TABLE_ALT);
        cbTinhTrang.setFont(ThemeUtils.FONT_BODY);
        cbTinhTrang.setForeground(ThemeUtils.TEXT_MUTED);
        cbTinhTrang.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        cbTinhTrang.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(cbTinhTrang);

        // --- Row 3: Lý do vi phạm ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createRequiredFormFieldRow("Lý do vi phạm:"));
        tLyDo = ThemeUtils.createTextField(0);
        tLyDo.setText(data[4].toString());
        tLyDo.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(tLyDo);

        // --- Row 4: Ngày phạt ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createRequiredFormFieldRow("Ngày phạt (yyyy-MM-dd):"));
        tNgay = ThemeUtils.createTextField(0);
        tNgay.setText(data[5].toString());
        tNgay.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(tNgay);

        // --- Row 5: Hình thức xử lý ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createRequiredFormFieldRow("Hình thức xử lý:"));
        tHinhThuc = ThemeUtils.createTextField(0);
        tHinhThuc.setText(data[6].toString());
        tHinhThuc.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(tHinhThuc);

        // --- Row 6: Tiến độ (combobox) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createRequiredFormFieldRow("Tiến độ thực hiện:"));
        cbTienDo = new JComboBox<>(new String[]{"Chưa hoàn thành", "Đã hoàn thành"});
        cbTienDo.setSelectedItem(data[7].toString());
        cbTienDo.setFont(ThemeUtils.FONT_BODY);
        cbTienDo.setBackground(ThemeUtils.BG_INPUT);
        cbTienDo.setForeground(ThemeUtils.TEXT_PRIMARY);
        cbTienDo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        cbTienDo.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(cbTienDo);

        cardPanel.add(formPanel, BorderLayout.CENTER);

        // Wrap card in a scrollable center panel
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        centerWrapper.setBackground(ThemeUtils.BG_MAIN);
        centerWrapper.add(cardPanel);
        this.add(centerWrapper, BorderLayout.CENTER);

        // ===== BOTTOM BUTTONS =====
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        pnlSouth.setBackground(ThemeUtils.BG_MAIN);
        pnlSouth.setBorder(new EmptyBorder(5, 20, 20, 20));

        btnLuu = ThemeUtils.createSuccessButton("Lưu cập nhật");
        btnLuu.setPreferredSize(new Dimension(160, 42));
        btnLuu.setFont(ThemeUtils.FONT_BODY_BOLD);

        btnHuy = ThemeUtils.createDangerButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(160, 42));
        btnHuy.setFont(ThemeUtils.FONT_BODY_BOLD);

        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        this.add(pnlSouth, BorderLayout.SOUTH);

        // Events
        btnLuu.addActionListener(e -> {
            String lyDo = tLyDo.getText().trim();
            String hinhThuc = tHinhThuc.getText().trim();
            String ngay = tNgay.getText().trim();

            if (lyDo.isEmpty() || hinhThuc.isEmpty() || ngay.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường (*)");
                return;
            }

            HinhPhatDAO hpDAO = new HinhPhatDAO();
            boolean updated = hpDAO.updateHinhPhat(maHP, lyDo, ngay, hinhThuc, cbTienDo.getSelectedItem().toString());

            if (updated) {
                isSuccess = true;
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật!");
            }
        });
        
        btnHuy.addActionListener(e -> dispose());
    }

    // Helper: Create a styled form field label
    private JLabel createFormFieldRow(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lbl.setForeground(ThemeUtils.TEXT_PRIMARY);
        return lbl;
    }

    private JLabel createRequiredFormFieldRow(String text) {
        JLabel lbl = new JLabel("<html>" + text + " <font color='" + colorToHex(ThemeUtils.DANGER) + "'>*</font></html>");
        lbl.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lbl.setForeground(ThemeUtils.TEXT_PRIMARY);
        return lbl;
    }

    private String colorToHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    public boolean isSuccess() { return isSuccess; }
}
