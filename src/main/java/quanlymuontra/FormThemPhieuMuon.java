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
        this.setSize(550, 720);
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(ThemeUtils.BG_MAIN);
        ((JPanel) this.getContentPane()).setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setUndecorated(true);

        // ===== TITLE BAR with gradient =====
        JPanel titleBar = ThemeUtils.createGradientPanel(ThemeUtils.GRADIENT_PRIMARY, 60);
        titleBar.setLayout(new BorderLayout());
        titleBar.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel lblTitle = new JLabel("THÊM PHIẾU MƯỢN MỚI", JLabel.CENTER);
        lblTitle.setFont(ThemeUtils.FONT_HEADING);
        lblTitle.setForeground(ThemeUtils.TEXT_BLACK);
        lblTitle.setBorder(new EmptyBorder(15, 20, 15, 10));
        titleBar.add(lblTitle, BorderLayout.CENTER);

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
        JPanel cardPanel = ThemeUtils.createCardPanel(20);
        cardPanel.setLayout(new BorderLayout(0, 0));

        JPanel iconRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        iconRow.setOpaque(false);
        iconRow.setBorder(new EmptyBorder(5, 0, 10, 0));
        JLabel lblIcon = new JLabel("📝", JLabel.CENTER);
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        iconRow.add(lblIcon);
        cardPanel.add(iconRow, BorderLayout.NORTH);

        // --- Initialize Components ---
        txtMaPM = ThemeUtils.createTextField(0);
        txtMaPM.setText(LogicMuonTra.taoMaPhieuMuonTuDong());
        txtMaPM.setEditable(false);
        txtMaPM.setBackground(ThemeUtils.BG_TABLE_ALT);
        txtMaPM.setForeground(ThemeUtils.TEXT_MUTED);

        cbSinhVien = new JComboBox<>();
        cbSinhVien.setEditable(true);
        setupAutoComplete(cbSinhVien, "sinh_vien", "ma_sv", "ho_ten");
        lblInfoSV = ThemeUtils.createLabel("Nhập ký tự để tìm kiếm...");
        lblInfoSV.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfoSV.setForeground(ThemeUtils.INFO);

        cbSach = new JComboBox<>();
        cbSach.setEditable(true);
        setupAutoComplete(cbSach, "sach", "ma_sach", "ten_sach");
        lblInfoSach = ThemeUtils.createLabel("Nhập ký tự để tìm kiếm...");
        lblInfoSach.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfoSach.setForeground(ThemeUtils.INFO);

        txtSoLuong = ThemeUtils.createTextField(0);
        txtSoLuong.setText("1");

        txtNgayMuon = ThemeUtils.createTextField(0);
        txtNgayMuon.setText(LocalDate.now().toString());
        txtNgayMuon.setEditable(false);
        txtNgayMuon.setBackground(ThemeUtils.BG_TABLE_ALT);

        txtNgayTra = ThemeUtils.createTextField(0);

        // --- Layout inside formPanel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        g.gridx = 0;
        int r = 0;

        g.insets = new Insets(0, 0, 4, 0);
        g.gridy = r++;
        formPanel.add(createFormFieldRow("Mã phiếu (Tự động):", false), g);
        g.gridy = r++;
        formPanel.add(txtMaPM, g);

        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++;
        formPanel.add(createFormFieldRow("Mã/Tên Sinh viên:", false), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++;
        formPanel.add(cbSinhVien, g);
        g.gridy = r++;
        formPanel.add(lblInfoSV, g);

        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++;
        formPanel.add(createFormFieldRow("Mã/Tên Sách: *", true), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++;
        formPanel.add(cbSach, g);
        g.gridy = r++;
        formPanel.add(lblInfoSach, g);

        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++;
        formPanel.add(createFormFieldRow("Số lượng mượn: *", true), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++;
        formPanel.add(txtSoLuong, g);

        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++;
        formPanel.add(createFormFieldRow("Ngày mượn (yyyy-MM-dd):", false), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++;
        formPanel.add(txtNgayMuon, g);

        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++;
        formPanel.add(createFormFieldRow("Ngày trả (yyyy-MM-dd): *", true), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++;
        formPanel.add(txtNgayTra, g);

        JScrollPane formScroll = new JScrollPane(formPanel);
        ThemeUtils.styleScrollPane(formScroll);
        formScroll.setPreferredSize(new Dimension(480, 420));
        formScroll.setBorder(null);
        cardPanel.add(formScroll, BorderLayout.CENTER);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
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

        btnLuu.addActionListener(e -> xuLyLuu());
        btnHuy.addActionListener(e -> dispose());
    }

    private JLabel createFormFieldRow(String text, boolean required) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lbl.setForeground(ThemeUtils.TEXT_PRIMARY);
        if (required) {
            lbl.setText("<html>" + text.replace(" *", "") + " <font color='" + colorToHex(ThemeUtils.DANGER)
                    + "'>*</font></html>");
        }
        return lbl;
    }

    private String colorToHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    private void setupAutoComplete(JComboBox<String> comboBox, String table, String colMa, String colTen) {
        comboBox.setFont(ThemeUtils.FONT_BODY);
        comboBox.setBackground(ThemeUtils.BG_INPUT);
        comboBox.setForeground(ThemeUtils.TEXT_PRIMARY);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        comboBox.setPreferredSize(new Dimension(400, 38));

        JTextComponent editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
                        || e.getKeyCode() == KeyEvent.VK_ENTER)
                    return;
                String input = editor.getText();
                if (input.isEmpty())
                    return;
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                String sql = "SELECT " + colMa + ", " + colTen + " FROM " + table +
                        " WHERE " + colMa + " LIKE ? OR " + colTen + " LIKE ? LIMIT 10";
                try (Connection conn = new Connect().getConnection();
                        PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, "%" + input + "%");
                    ps.setString(2, "%" + input + "%");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next())
                        model.addElement(rs.getString(colMa) + " - " + rs.getString(colTen));
                    comboBox.setModel(model);
                    comboBox.getEditor().setItem(input);
                    if (model.getSize() > 0)
                        comboBox.showPopup();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
                if (sl <= 0)
                    throw new NumberFormatException();
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

    public boolean isSuccess() {
        return isSuccess;
    }
}
