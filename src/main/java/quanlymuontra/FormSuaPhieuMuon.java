package quanlymuontra;

import utils.ThemeUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import connect.Connect;
import dao.PhieuMuonDAO;

public class FormSuaPhieuMuon extends JDialog {
    private JTextField txtSinhVien, txtSoLuong, txtNgayMuon, txtNgayTra;
    private JComboBox<String> cbSach, cbTinhTrang;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false;
    private String maPM;
    private String maSachCu;

    public FormSuaPhieuMuon(Frame parent, Object[] data) {
        super(parent, "Cập nhật phiếu mượn", true);
        this.maPM = data[0].toString();
        this.maSachCu = data[3].toString();
        
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

        JLabel lblTitle = new JLabel("  CẬP NHẬT PHIẾU MƯỢN", JLabel.LEFT);
        lblTitle.setFont(ThemeUtils.FONT_HEADING);
        lblTitle.setForeground(ThemeUtils.TEXT_WHITE);
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
        JLabel lblIcon = new JLabel("✏️", JLabel.CENTER);
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        iconRow.add(lblIcon);
        cardPanel.add(iconRow, BorderLayout.NORTH);

        // --- Initialize Components ---
        txtSinhVien = ThemeUtils.createTextField(0);
        txtSinhVien.setText(data[1] + " - " + data[2]);
        txtSinhVien.setEditable(false);
        txtSinhVien.setBackground(ThemeUtils.BG_TABLE_ALT);
        txtSinhVien.setForeground(ThemeUtils.TEXT_MUTED);

        cbSach = new JComboBox<>();
        cbSach.setFont(ThemeUtils.FONT_BODY);
        cbSach.setBackground(ThemeUtils.BG_INPUT);
        cbSach.setForeground(ThemeUtils.TEXT_PRIMARY);
        cbSach.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        cbSach.setPreferredSize(new Dimension(400, 38));
        loadAllBooksToCombo();
        setSelectedSach(this.maSachCu);

        txtSoLuong = ThemeUtils.createTextField(0);
        txtSoLuong.setText(data[7].toString());

        txtNgayMuon = ThemeUtils.createTextField(0);
        txtNgayMuon.setText(data[8].toString());
        txtNgayMuon.setEditable(false);
        txtNgayMuon.setBackground(ThemeUtils.BG_TABLE_ALT);

        txtNgayTra = ThemeUtils.createTextField(0);
        txtNgayTra.setText(data[9].toString());

        cbTinhTrang = new JComboBox<>(new String[]{
            "Đang mượn", "Đã trả", "Trả chậm", "Quá hạn trả"
        });
        cbTinhTrang.setSelectedItem(data[10].toString());
        cbTinhTrang.setFont(ThemeUtils.FONT_BODY);
        cbTinhTrang.setBackground(ThemeUtils.BG_INPUT);
        cbTinhTrang.setForeground(ThemeUtils.TEXT_PRIMARY);
        cbTinhTrang.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        cbTinhTrang.setPreferredSize(new Dimension(400, 38));

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
        g.gridy = r++; formPanel.add(createFormFieldRow("Sinh viên:"), g);
        g.gridy = r++; formPanel.add(txtSinhVien, g);
        
        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++; formPanel.add(createFormFieldRow("Sách mượn:"), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++; formPanel.add(cbSach, g);
        
        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++; formPanel.add(createFormFieldRow("Số lượng mượn:"), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++; formPanel.add(txtSoLuong, g);
        
        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++; formPanel.add(createFormFieldRow("Ngày mượn:"), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++; formPanel.add(txtNgayMuon, g);
        
        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++; formPanel.add(createFormFieldRow("Ngày trả (yyyy-MM-dd):"), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++; formPanel.add(txtNgayTra, g);
        
        g.insets = new Insets(12, 0, 4, 0);
        g.gridy = r++; formPanel.add(createFormFieldRow("Tình trạng phiếu:"), g);
        g.insets = new Insets(0, 0, 0, 0);
        g.gridy = r++; formPanel.add(cbTinhTrang, g);

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

        btnLuu = ThemeUtils.createSuccessButton("Lưu cập nhật");
        btnLuu.setPreferredSize(new Dimension(160, 42));
        btnLuu.setFont(ThemeUtils.FONT_BODY_BOLD);

        btnHuy = ThemeUtils.createDangerButton("Hủy bỏ");
        btnHuy.setPreferredSize(new Dimension(160, 42));
        btnHuy.setFont(ThemeUtils.FONT_BODY_BOLD);

        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        this.add(pnlSouth, BorderLayout.SOUTH);

        btnLuu.addActionListener(e -> xuLyCapNhat());
        btnHuy.addActionListener(e -> dispose());
    }

    private JLabel createFormFieldRow(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lbl.setForeground(ThemeUtils.TEXT_PRIMARY);
        return lbl;
    }

    private void loadAllBooksToCombo() {
        try (Connection conn = new Connect().getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT ma_sach, ten_sach FROM sach")) {
            while (rs.next()) {
                cbSach.addItem(rs.getString("ma_sach") + " - " + rs.getString("ten_sach"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void setSelectedSach(String maSach) {
        for (int i = 0; i < cbSach.getItemCount(); i++) {
            if (cbSach.getItemAt(i).startsWith(maSach)) {
                cbSach.setSelectedIndex(i);
                break;
            }
        }
    }

    private void xuLyCapNhat() {
        try {
            String tinhTrangMoi = cbTinhTrang.getSelectedItem().toString();
            String sachChon = cbSach.getSelectedItem().toString();
            String maSachMoi = sachChon.split(" - ")[0];
            int sl = Integer.parseInt(txtSoLuong.getText());
            String ngayT = txtNgayTra.getText();

            PhieuMuonDAO dao = new PhieuMuonDAO();
            boolean res = dao.updatePhieuMuon(maPM, maSachMoi, sl, ngayT, tinhTrangMoi);

            if (res) {
                isSuccess = true;
                JOptionPane.showMessageDialog(this, "Cập nhật phiếu mượn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật dữ liệu!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
    
    public boolean isSuccess() { return isSuccess; }
}
