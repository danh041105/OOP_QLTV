package quanlymuontra;
import dao.HinhPhatDAO;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormThemHinhPhat extends JDialog {
    private JTextField txtMaSV, txtMaPM, txtTenSV, txtTinhTrang, txtLyDo, txtNgayPhat, txtHinhThuc;
    private JComboBox<String> cbTienDo;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false;

    public FormThemHinhPhat(Frame parent, Object[] data) {
        super(parent, "Xử lý hình phạt", true);
        this.setLayout(new BorderLayout(10, 10));
        this.setSize(500, 750);
        this.setLocationRelativeTo(parent);
        this.getContentPane().setBackground(ThemeUtils.BG_MAIN);
        ((JPanel) this.getContentPane()).setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setUndecorated(true);

        // ===== TITLE BAR with RED accent gradient =====
        JPanel titleBar = ThemeUtils.createGradientPanel(ThemeUtils.GRADIENT_DANGER, 60);
        titleBar.setLayout(new BorderLayout());
        titleBar.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel lblTitle = new JLabel("  XỬ LÝ HÌNH PHẠT", JLabel.LEFT);
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
                g2.setColor(new Color(254, 226, 226)); // light red bg
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        iconCircle.setPreferredSize(new Dimension(56, 56));
        iconCircle.setLayout(new BorderLayout());
        iconCircle.setOpaque(false);
        JLabel lblIcon = new JLabel("⚠️", JLabel.CENTER);
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconCircle.add(lblIcon, BorderLayout.CENTER);
        iconRow.add(iconCircle);
        cardPanel.add(iconRow, BorderLayout.NORTH);

        // Form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        int gap = 10;

        // --- Row 1: Mã phiếu mượn (read-only) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Mã phiếu mượn:"));
        txtMaPM = createReadOnlyTextField(data[0].toString());
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtMaPM);

        // --- Row 2: Mã sinh viên (read-only) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Mã sinh viên:"));
        txtMaSV = createReadOnlyTextField(data[1].toString());
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtMaSV);

        // --- Row 3: Tên sinh viên (read-only) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Tên sinh viên:"));
        txtTenSV = createReadOnlyTextField(data[2].toString());
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtTenSV);

        // --- Row 4: Tình trạng (read-only) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Tình trạng phiếu:"));
        txtTinhTrang = createReadOnlyTextField(data[10].toString());
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtTinhTrang);

        // --- Row 5: Lý do (editable) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createRequiredFormFieldRow("Lý do vi phạm:"));
        txtLyDo = ThemeUtils.createTextField(0);
        txtLyDo.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtLyDo);

        // --- Row 6: Ngày phạt (auto today) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createRequiredFormFieldRow("Ngày phạt:"));
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtNgayPhat = ThemeUtils.createTextField(0);
        txtNgayPhat.setText(today);
        txtNgayPhat.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtNgayPhat);

        // --- Row 7: Hình thức (auto-filled) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createRequiredFormFieldRow("Hình thức xử lý:"));
        txtHinhThuc = ThemeUtils.createTextField(0);
        txtHinhThuc.setText("Vi phạm: " + data[10].toString());
        txtHinhThuc.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtHinhThuc);

        // --- Row 8: Tiến độ (combobox) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createRequiredFormFieldRow("Tiến độ:"));
        cbTienDo = new JComboBox<>(new String[]{"Chưa hoàn thành", "Đang xử lý", "Đã hoàn thành"});
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

        btnLuu = ThemeUtils.createDangerButton("Lưu hình phạt");
        btnLuu.setPreferredSize(new Dimension(170, 42));
        btnLuu.setFont(ThemeUtils.FONT_BODY_BOLD);

        btnHuy = ThemeUtils.createSecondaryButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(170, 42));
        btnHuy.setFont(ThemeUtils.FONT_BODY_BOLD);

        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        this.add(pnlSouth, BorderLayout.SOUTH);

        // Events
        btnLuu.addActionListener(e -> xuLyLuu());
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

    // Helper: Create read-only text field with gray background
    private JTextField createReadOnlyTextField(String text) {
        JTextField tf = ThemeUtils.createTextField(0);
        tf.setText(text);
        tf.setEditable(false);
        tf.setBackground(ThemeUtils.BG_TABLE_ALT);
        tf.setForeground(ThemeUtils.TEXT_MUTED);
        tf.setPreferredSize(new Dimension(400, 38));
        return tf;
    }

    private void xuLyLuu() {
        String lyDo = txtLyDo.getText().trim();
        String hinhThuc = txtHinhThuc.getText().trim();

        if (lyDo.isEmpty() || hinhThuc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc (*)");
            return;
        }

        HinhPhatDAO hpDAO = new HinhPhatDAO();
        int result = hpDAO.insertHinhPhat(
            txtMaSV.getText(),
            txtMaPM.getText(),   
            lyDo,
            txtNgayPhat.getText(),
            hinhThuc,
            cbTienDo.getSelectedItem().toString()
        );

        if (result > 0) {
            isSuccess = true;
            JOptionPane.showMessageDialog(this, "Thêm hình phạt thành công!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hình phạt!");
        }
    }

    public boolean isSuccess() { return isSuccess; }
}
