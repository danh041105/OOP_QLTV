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

        JLabel lblTitle = new JLabel("  CẬP NHẬT PHIẾU MƯỢN", JLabel.LEFT);
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

        // --- Row 2: Sách mượn (combobox) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Sách mượn:"));
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
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(cbSach);

        // --- Row 3: Số lượng ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Số lượng mượn:"));
        txtSoLuong = ThemeUtils.createTextField(0);
        txtSoLuong.setText(data[7].toString());
        txtSoLuong.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtSoLuong);

        // --- Row 4: Ngày mượn (read-only) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Ngày mượn:"));
        txtNgayMuon = ThemeUtils.createTextField(0);
        txtNgayMuon.setText(data[8].toString());
        txtNgayMuon.setEditable(false);
        txtNgayMuon.setBackground(ThemeUtils.BG_TABLE_ALT);
        txtNgayMuon.setForeground(ThemeUtils.TEXT_MUTED);
        txtNgayMuon.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtNgayMuon);

        // --- Row 5: Ngày trả ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Ngày trả (yyyy-MM-dd):"));
        txtNgayTra = ThemeUtils.createTextField(0);
        txtNgayTra.setText(data[9].toString());
        txtNgayTra.setPreferredSize(new Dimension(400, 38));
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(txtNgayTra);

        // --- Row 6: Tình trạng (combobox) ---
        formPanel.add(Box.createVerticalStrut(gap));
        formPanel.add(createFormFieldRow("Tình trạng phiếu:"));
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
        formPanel.add(Box.createVerticalStrut(4));
        formPanel.add(cbTinhTrang);

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
        btnLuu.addActionListener(e -> xuLyCapNhat());
        btnHuy.addActionListener(e -> dispose());
    }

    // Helper: Create a styled form field label
    private JLabel createFormFieldRow(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lbl.setForeground(ThemeUtils.TEXT_PRIMARY);
        return lbl;
    }

    // --- Các hàm hỗ trợ và Logic ---
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
