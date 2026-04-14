package quanlymuontra;
import xulysoluong.LogicMuonTra;
import connect.Connect;
import utils.ThemeUtils;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import javax.swing.text.JTextComponent;
import dao.PhieuMuonDAO;

public class FormThemPhieuMuon extends JDialog {
    private JTextField txtMaPM, txtSoLuong, txtNgayMuon, txtNgayTra;
    private JComboBox<String> cbSinhVien, cbSach;
    private JLabel lblInfoSV, lblInfoSach;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false;

    public FormThemPhieuMuon(Frame parent) {
        super(parent, "Thêm phiếu mượn mới", true);
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

        JLabel lblTitle = new JLabel("  THÊM PHIẾU MƯỢN MỚI", JLabel.LEFT);
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
        JLabel lblIcon = new JLabel("📝", JLabel.CENTER);
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

        // --- Row 1: Mã phiếu (auto, non-editable, gray bg) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Mã phiếu (Tự động):", false));
        txtMaPM = ThemeUtils.createTextField(0);
        txtMaPM.setText(LogicMuonTra.taoMaPhieuMuonTuDong());
        txtMaPM.setEditable(false);
        txtMaPM.setBackground(ThemeUtils.BG_TABLE_ALT);
        txtMaPM.setForeground(ThemeUtils.TEXT_MUTED);
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtMaPM);

        // --- Row 2: Sinh viên (editable JComboBox with autocomplete) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Mã/Tên Sinh viên:", false));
        cbSinhVien = new JComboBox<>();
        cbSinhVien.setEditable(true);
        cbSinhVien.setFont(ThemeUtils.FONT_BODY);
        cbSinhVien.setBackground(ThemeUtils.BG_INPUT);
        cbSinhVien.setForeground(ThemeUtils.TEXT_PRIMARY);
        cbSinhVien.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        cbSinhVien.setPreferredSize(new Dimension(400, 38));
        setupAutoComplete(cbSinhVien, "sinh_vien", "ma_sv", "ho_ten");
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(cbSinhVien);
        lblInfoSV = ThemeUtils.createLabel("Nhập ký tự để tìm kiếm...");
        lblInfoSV.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfoSV.setForeground(ThemeUtils.INFO);
        lblInfoSV.setBorder(new EmptyBorder(2, 5, 0, 0));
        formPanel.add(lblInfoSV);

        // --- Row 3: Sách (editable JComboBox with autocomplete) ---
        formPanel.add(Box.createVerticalStrut(gap));
        JLabel lblSach = createFormFieldRow("Mã/Tên Sách: *", true);
        formPanel.add(lblSach);
        cbSach = new JComboBox<>();
        cbSach.setEditable(true);
        cbSach.setFont(ThemeUtils.FONT_BODY);
        cbSach.setBackground(ThemeUtils.BG_INPUT);
        cbSach.setForeground(ThemeUtils.TEXT_PRIMARY);
        cbSach.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        cbSach.setPreferredSize(new Dimension(400, 38));
        setupAutoComplete(cbSach, "sach", "ma_sach", "ten_sach");
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(cbSach);
        lblInfoSach = ThemeUtils.createLabel("Nhập ký tự để tìm kiếm...");
        lblInfoSach.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfoSach.setForeground(ThemeUtils.INFO);
        lblInfoSach.setBorder(new EmptyBorder(2, 5, 0, 0));
        formPanel.add(lblInfoSach);

        // --- Row 4: Số lượng mượn ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Số lượng mượn: *", true));
        txtSoLuong = ThemeUtils.createTextField(0);
        txtSoLuong.setText("1");
        txtSoLuong.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtSoLuong);

        // --- Row 5: Ngày mượn (auto today) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Ngày mượn (yyyy-MM-dd):", false));
        txtNgayMuon = ThemeUtils.createTextField(0);
        txtNgayMuon.setText(LocalDate.now().toString());
        txtNgayMuon.setEditable(false);
        txtNgayMuon.setBackground(ThemeUtils.BG_TABLE_ALT);
        txtNgayMuon.setForeground(ThemeUtils.TEXT_MUTED);
        txtNgayMuon.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtNgayMuon);

        // --- Row 6: Ngày trả ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Ngày trả (yyyy-MM-dd): *", true));
        txtNgayTra = ThemeUtils.createTextField(0);
        txtNgayTra.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtNgayTra);

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

        btnLuu = ThemeUtils.createSuccessButton("Lưu phiếu");
        btnLuu.setPreferredSize(new Dimension(160, 42));
        btnLuu.setFont(ThemeUtils.FONT_BODY_BOLD);

        btnHuy = ThemeUtils.createDangerButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(160, 42));
        btnHuy.setFont(ThemeUtils.FONT_BODY_BOLD);

        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        this.add(pnlSouth, BorderLayout.SOUTH);

        // Events
        btnLuu.addActionListener(e -> xuLyLuu());
        btnHuy.addActionListener(e -> dispose());
    }

    // Helper: Create a styled form field label
    private JLabel createFormFieldRow(String text, boolean required) {
        String display = required ? text : text;
        JLabel lbl = new JLabel(display);
        lbl.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lbl.setForeground(ThemeUtils.TEXT_PRIMARY);
        if (required) {
            lbl.setText(text.replace(" *", ""));
            lbl.setText("<html>" + text.replace(" *", "") + " <font color='" + colorToHex(ThemeUtils.DANGER) + "'>*</font></html>");
        }
        return lbl;
    }

    private String colorToHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    private void setupAutoComplete(JComboBox<String> comboBox, String table, String colMa, String colTen) {
        JTextComponent editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) return;
                String input = editor.getText();
                if (input.isEmpty()) return;
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                String sql = "SELECT " + colMa + ", " + colTen + " FROM " + table + 
                        " WHERE " + colMa + " LIKE ? OR " + colTen + " LIKE ? LIMIT 10";
                try (Connection conn = new Connect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, "%" + input + "%");
                    ps.setString(2, "%" + input + "%");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) model.addElement(rs.getString(colMa) + " - " + rs.getString(colTen));
                    comboBox.setModel(model);
                    comboBox.getEditor().setItem(input);
                    if (model.getSize() > 0) comboBox.showPopup();
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        });
    }
    
    private void xuLyLuu() {
        try {
            String svChon = (cbSinhVien.getSelectedItem() != null) ? cbSinhVien.getSelectedItem().toString() : "";
            String sachChon = (cbSach.getSelectedItem() != null) ? cbSach.getSelectedItem().toString() : "";
            String ngayMuon = txtNgayMuon.getText().trim();
            String ngayTra = txtNgayTra.getText().trim();
            String maPM = txtMaPM.getText().trim();
            
            if (svChon.isEmpty() || sachChon.isEmpty() || ngayTra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            String maSV = svChon.split(" - ")[0].trim();
            String maSach = sachChon.split(" - ")[0].trim();
            String tenSach = sachChon.contains(" - ") ? sachChon.split(" - ")[1].trim() : "sách";
            int sl;
            try {
                sl = Integer.parseInt(txtSoLuong.getText().trim());
                if (sl <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng mượn phải là số nguyên dương!");
                return;
            }

            if (!LogicMuonTra.ktraNgayTra(ngayMuon, ngayTra)) {
                JOptionPane.showMessageDialog(this, "Ngày trả không hợp lệ! (Phải sau ngày mượn và không quá 30 ngày)");
                return;
            }

            int tonKho = LogicMuonTra.ktraTonKho(maSach);
            if (tonKho == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin sách trong hệ thống!");
                return;
            }
        
            if (sl > tonKho) {
                JOptionPane.showMessageDialog(this, 
                    "Số lượng trong kho của " + tenSach + " (" + tonKho + ") không đủ để cho mượn " + sl + " cuốn!",
                    "Cảnh báo thiếu hàng", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            PhieuMuonDAO dao = new PhieuMuonDAO();
            if (dao.insertPhieuMuon(maPM, maSV, maSach, sl, ngayMuon, ngayTra)) {
                isSuccess = true;
                JOptionPane.showMessageDialog(this, "Thêm phiếu mượn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu thất bại! Vui lòng kiểm tra lại kết nối Database.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    public boolean isSuccess() { return isSuccess; }
}
