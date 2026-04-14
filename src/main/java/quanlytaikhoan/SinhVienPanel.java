package quanlytaikhoan;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SinhVienPanel extends JPanel {
    private JTable tblSV;
    private DefaultTableModel model;
    private SinhVienDAO svDAO = new SinhVienDAO();

    private JTextField txtMasv, txtTen, txtLop, txtNgaySinh, txtDiaChi, txtEmail, txtSdt, txtTimkiem, txtUsername, txtPassword;
    private JComboBox<String> cbGioiTinh;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimkiem;
    private boolean isStudentMode = false;

    // Bảng màu mới
    private static final Color PRIMARY = new Color(41, 128, 185);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);
    private static final Color ACCENT_ORANGE = new Color(243, 156, 18);
    private static final Color ACCENT_RED = new Color(231, 76, 60);
    private static final Color ACCENT_GRAY = new Color(149, 165, 166);
    private static final Color ROW_EVEN = new Color(236, 240, 241);
    private static final Color ROW_ODD = new Color(255, 255, 255);
    private static final Color HEADER_BG = new Color(44, 62, 80);
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private static final Color TEXT_SECONDARY = new Color(127, 140, 141);
    private static final Color FIELD_BG = new Color(250, 252, 255);
    private static final Color CARD_BG = new Color(255, 255, 255);
    private static final Color PANEL_BG = new Color(245, 246, 250);

    // Constructor mặc định cho Admin
    public SinhVienPanel() {
        this.isStudentMode = false;
        setLayout(new BorderLayout(10, 10));
        setOpaque(false);
        buildUI();
        loadData();
        addEvents();
    }

    // Constructor cho Sinh viên
    public SinhVienPanel(String msv) {
        this.isStudentMode = true;
        setLayout(new BorderLayout(10, 10));
        setOpaque(false);
        buildUI();
        loadStudentData(msv);
        addEvents();
    }

    private void buildUI() {
        // === PHẦN TRÊN: Tiêu đề + Tìm kiếm ===
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setOpaque(false);

        // Tiêu đề
        JLabel lblTitle = new JLabel(isStudentMode ? "THÔNG TIN CÁ NHÁN" : "QUẢN LÝ TÀI KHOẢN SINH VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(TEXT_DARK);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        pnlNorth.add(lblTitle, BorderLayout.CENTER);

        // Tìm kiếm (chỉ Admin)
        if (!isStudentMode) {
            JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
            pnlSearch.setOpaque(true);
            pnlSearch.setBackground(CARD_BG);
            pnlSearch.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));

            JLabel lblSearch = new JLabel("Tìm kiếm:");
            lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblSearch.setForeground(TEXT_DARK);

            txtTimkiem = new JTextField(15);
            txtTimkiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txtTimkiem.setPreferredSize(new Dimension(180, 30));
            txtTimkiem.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)
            ));

            btnTimkiem = createStyledButton("Tìm kiếm", PRIMARY, Color.WHITE, 90, 30);
            pnlSearch.add(lblSearch);
            pnlSearch.add(txtTimkiem);
            pnlSearch.add(btnTimkiem);

            pnlNorth.add(pnlSearch, BorderLayout.EAST);
        }

        add(pnlNorth, BorderLayout.NORTH);

        // === PHẦN GIỮA: Form + Bảng ===
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setOpaque(false);

        // Card form bên trái
        JPanel formCard = createFormCard();
        pnlCenter.add(formCard, BorderLayout.WEST);

        // Bảng bên phải (chỉ Admin)
        if (!isStudentMode) {
            pnlCenter.add(createTablePanel(), BorderLayout.CENTER);
        } else {
            // Sinh viên: thêm phần thông báo nhỏ bên phải
            pnlCenter.add(createStudentInfoPanel(), BorderLayout.CENTER);
        }

        add(pnlCenter, BorderLayout.CENTER);

        // === PHẦN DƯỚI: Nút bấm ===
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    // === CARD FORM THÔNG TIN SINH VIÊN ===
    private JPanel createFormCard() {
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        card.setPreferredSize(new Dimension(320, 0));

        // Card header
        JPanel cardHeader = new JPanel(new BorderLayout());
        cardHeader.setBackground(PRIMARY);
        cardHeader.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblCardTitle = new JLabel("Thông tin sinh viên");
        lblCardTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCardTitle.setForeground(Color.WHITE);
        cardHeader.add(lblCardTitle, BorderLayout.WEST);

        JLabel lblIcon = new JLabel("\uD83D\uDCDD");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        cardHeader.add(lblIcon, BorderLayout.EAST);

        card.add(cardHeader, BorderLayout.NORTH);

        // Card body - Form
        JPanel formBody = new JPanel(new GridBagLayout());
        formBody.setBackground(CARD_BG);
        formBody.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng labels (cột 0)
        String[] labels = {"Mã SV:", "Họ tên:", "Lớp:", "Giới tính:", "Ngày sinh:", "Địa chỉ:", "Email:", "SĐT:", "Username:", "Password:"};
        JLabel[] lbls = new JLabel[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            lbls[i] = new JLabel(labels[i]);
            lbls[i].setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbls[i].setForeground(TEXT_DARK);
            formBody.add(lbls[i], gbc);
        }

        // Hàng fields (cột 1)
        gbc.weightx = 0.7;
        gbc.gridx = 1;

        txtMasv = createStyledTextField();
        gbc.gridy = 0; formBody.add(txtMasv, gbc);

        txtTen = createStyledTextField();
        gbc.gridy = 1; formBody.add(txtTen, gbc);

        txtLop = createStyledTextField();
        gbc.gridy = 2; formBody.add(txtLop, gbc);

        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        cbGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbGioiTinh.setBackground(FIELD_BG);
        cbGioiTinh.setPreferredSize(new Dimension(170, 28));
        cbGioiTinh.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        gbc.gridy = 3; formBody.add(cbGioiTinh, gbc);

        txtNgaySinh = createStyledTextField();
        gbc.gridy = 4; formBody.add(txtNgaySinh, gbc);

        txtDiaChi = createStyledTextField();
        gbc.gridy = 5; formBody.add(txtDiaChi, gbc);

        txtEmail = createStyledTextField();
        gbc.gridy = 6; formBody.add(txtEmail, gbc);

        txtSdt = createStyledTextField();
        gbc.gridy = 7; formBody.add(txtSdt, gbc);

        txtUsername = createStyledTextField();
        gbc.gridy = 8; formBody.add(txtUsername, gbc);

        txtPassword = createStyledTextField();
        gbc.gridy = 9; formBody.add(txtPassword, gbc);

        card.add(formBody, BorderLayout.CENTER);

        return card;
    }

    // Tạo JTextField với style đẹp
    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt.setPreferredSize(new Dimension(170, 28));
        txt.setBackground(FIELD_BG);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return txt;
    }

    // === BẢNG DỮ LIỆU (Admin) ===
    private JScrollPane createTablePanel() {
        model = new DefaultTableModel(new String[]{"Mã SV", "Họ tên", "Lớp", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "SĐT", "Username", "Password"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblSV = new JTable(model);
        tblSV.setRowHeight(32);
        tblSV.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblSV.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblSV.setSelectionBackground(new Color(52, 152, 219));
        tblSV.setSelectionForeground(Color.WHITE);
        tblSV.setShowGrid(false);
        tblSV.setIntercellSpacing(new Dimension(0, 1));

        // Style header
        JTableHeader header = tblSV.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(Color.WHITE);
        header.setBackground(HEADER_BG);
        header.setPreferredSize(new Dimension(header.getWidth(), 34));
        header.setReorderingAllowed(false);

        // Tô màu xen kẽ
        tblSV.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                    c.setForeground(TEXT_DARK);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblSV);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }

    // === PANEL THÔNG BÁO (Sinh viên mode) ===
    private JPanel createStudentInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);

        // Card hướng dẫn
        JPanel guideCard = new JPanel(new BorderLayout());
        guideCard.setBackground(CARD_BG);
        guideCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        JPanel guideHeader = new JPanel(new BorderLayout());
        guideHeader.setBackground(new Color(39, 174, 96));
        guideHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        JLabel lblGuideTitle = new JLabel("Hướng dẫn");
        lblGuideTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblGuideTitle.setForeground(Color.WHITE);
        guideHeader.add(lblGuideTitle, BorderLayout.WEST);
        guideCard.add(guideHeader, BorderLayout.NORTH);

        JPanel guideBody = new JPanel(new GridLayout(0, 1, 8, 8));
        guideBody.setBackground(CARD_BG);
        guideBody.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] tips = {
                "1. Xem thông tin cá nhân của bạn bên trái",
                "2. Sửa thông tin trong các ô nhập liệu",
                "3. Nhấn 'Cập nhật' để lưu thay đổi",
                "4. Nhấn 'Làm mới' để hoàn tác",
                "5. Mã SV và Username không thể thay đổi"
        };

        for (String tip : tips) {
            JLabel lblTip = new JLabel(tip);
            lblTip.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblTip.setForeground(TEXT_SECONDARY);
            guideBody.add(lblTip);
        }

        guideCard.add(guideBody, BorderLayout.CENTER);

        // Card thống kê nhỏ
        JPanel statsCard = new JPanel(new BorderLayout());
        statsCard.setBackground(CARD_BG);
        statsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        JPanel statsHeader = new JPanel(new BorderLayout());
        statsHeader.setBackground(new Color(52, 152, 219));
        statsHeader.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        JLabel lblStatsTitle = new JLabel("Lưu ý");
        lblStatsTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatsTitle.setForeground(Color.WHITE);
        statsHeader.add(lblStatsTitle, BorderLayout.WEST);
        statsCard.add(statsHeader, BorderLayout.NORTH);

        JPanel statsBody = new JPanel(new GridLayout(0, 1, 5, 5));
        statsBody.setBackground(CARD_BG);
        statsBody.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] notes = {
                "Thông tin sẽ được cập nhật vào hệ thống",
                "Liên hệ Admin nếu cần hỗ trợ",
                "Đăng nhập lại để thấy thay đổi"
        };
        for (String note : notes) {
            JLabel lblNote = new JLabel("  " + note);
            lblNote.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblNote.setForeground(TEXT_SECONDARY);
            statsBody.add(lblNote);
        }
        statsCard.add(statsBody, BorderLayout.CENTER);

        // Thêm cả 2 card vào panel
        panel.add(guideCard, BorderLayout.NORTH);
        panel.add(statsCard, BorderLayout.CENTER);

        return panel;
    }

    // === PANEL NÚT BẤM ===
    private JPanel createButtonPanel() {
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        pnlSouth.setOpaque(false);

        if (isStudentMode) {
            pnlSouth.add(btnLamMoi = createStyledButton("Làm mới", ACCENT_GRAY, Color.WHITE, 110, 34));
            pnlSouth.add(btnSua = createStyledButton("Cập nhật", ACCENT_GREEN, Color.WHITE, 110, 34));
        } else {
            pnlSouth.add(btnThem = createStyledButton("+ Thêm mới", ACCENT_GREEN, Color.WHITE, 120, 34));
            pnlSouth.add(btnSua = createStyledButton("Cập nhật", ACCENT_ORANGE, Color.WHITE, 110, 34));
            pnlSouth.add(btnXoa = createStyledButton("Xóa", ACCENT_RED, Color.WHITE, 90, 34));
            pnlSouth.add(btnLamMoi = createStyledButton("Làm mới", ACCENT_GRAY, Color.WHITE, 110, 34));
        }

        return pnlSouth;
    }

    // Tạo button với style đẹp + hover
    private JButton createStyledButton(String text, Color bgColor, Color fgColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    private void addEvents() {
        if (!isStudentMode) {
            tblSV.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int row = tblSV.getSelectedRow();
                    if (row != -1) {
                        try {
                            txtMasv.setText(model.getValueAt(row, 0).toString());
                            txtMasv.setEditable(false);
                            txtMasv.setBackground(new Color(240, 240, 240));
                            txtTen.setText(model.getValueAt(row, 1).toString());
                            txtLop.setText(model.getValueAt(row, 2).toString());
                            cbGioiTinh.setSelectedItem(model.getValueAt(row, 3).toString());
                            txtNgaySinh.setText(model.getValueAt(row, 4).toString());
                            txtDiaChi.setText(model.getValueAt(row, 5).toString());
                            txtEmail.setText(model.getValueAt(row, 6).toString());
                            txtSdt.setText(model.getValueAt(row, 7).toString());
                            txtUsername.setText(model.getValueAt(row, 8).toString());
                            txtUsername.setEditable(false);
                            txtUsername.setBackground(new Color(240, 240, 240));
                            txtPassword.setText(model.getValueAt(row, 9).toString());
                        } catch (Exception ex) {
                        }
                    }
                }
            });

            btnThem.addActionListener(e -> {
                SinhVien sv = getForm();
                if (svDAO.insert(sv, txtUsername.getText().trim(), txtPassword.getText().trim())) {
                    loadData();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                }
            });

            btnXoa.addActionListener(e -> {
                int row = tblSV.getSelectedRow();
                if (row == -1) return;
                int id = svDAO.getAll().get(row).getId();
                if (JOptionPane.showConfirmDialog(this, "Xóa sinh viên này?", "Xác nhận", 0) == 0) {
                    if (svDAO.delete(id)) loadData();
                }
            });

            btnTimkiem.addActionListener(e -> fillTable(svDAO.search(txtTimkiem.getText().trim())));
        }

        btnSua.addActionListener(e -> {
            if (isStudentMode) {
                SinhVien sv = getForm();
                SinhVien current = svDAO.getByMSV(txtMasv.getText().trim());
                if (current != null) {
                    sv.setId(current.getId());
                    if (svDAO.update(sv)) {
                        loadStudentData(txtMasv.getText().trim());
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    }
                }
            } else {
                int row = tblSV.getSelectedRow();
                if (row == -1) return;
                int id = svDAO.getAll().get(row).getId();
                SinhVien sv = getForm();
                sv.setId(id);
                if (svDAO.update(sv)) {
                    loadData();
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                }
            }
        });

        if (!isStudentMode && btnLamMoi != null) {
            btnLamMoi.addActionListener(e -> clearForm());
        }
        if (isStudentMode && btnLamMoi != null) {
            btnLamMoi.addActionListener(e -> {
                String msv = txtMasv.getText().trim();
                loadStudentData(msv);
            });
        }
    }

    private SinhVien getForm() {
        try {
            String sNgay = txtNgaySinh.getText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            java.sql.Date sqlDate;
            try {
                java.util.Date utDate = sdf.parse(sNgay);
                sqlDate = new java.sql.Date(utDate.getTime());
            } catch (Exception e) {
                sqlDate = new java.sql.Date(System.currentTimeMillis());
            }

            SinhVien sv = new SinhVien(
                    0,
                    txtMasv.getText().trim(),
                    txtTen.getText().trim(),
                    txtLop.getText().trim(),
                    cbGioiTinh.getSelectedItem().toString(),
                    sqlDate,
                    txtDiaChi.getText().trim(),
                    txtEmail.getText().trim(),
                    txtSdt.getText().trim()
            );
            sv.setUsername(txtUsername.getText().trim());
            sv.setPassword(txtPassword.getText().trim());
            return sv;
        } catch (Exception e) {
            return null;
        }
    }

    private void clearForm() {
        txtMasv.setText(""); txtMasv.setEditable(true); txtMasv.setBackground(FIELD_BG);
        txtTen.setText("");
        txtLop.setText("");
        txtNgaySinh.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSdt.setText("");
        txtUsername.setText(""); txtUsername.setEditable(true); txtUsername.setBackground(FIELD_BG);
        txtPassword.setText("");
        tblSV.clearSelection();
    }

    private void fillTable(ArrayList<SinhVien> list) {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (SinhVien sv : list) {
            model.addRow(new Object[]{sv.getMasv(), sv.getHoten(), sv.getLop(), sv.getGioitinh(),
                    sv.getNgaysinh() != null ? sdf.format(sv.getNgaysinh()) : "", sv.getDiachi(),
                    sv.getEmail(), sv.getSdt(), sv.getUsername(), sv.getPassword()});
        }
    }

    private void loadData() { fillTable(svDAO.getAll()); }

    private void loadStudentData(String msv) {
        SinhVien sv = svDAO.getByMSV(msv);
        if (sv != null) {
            txtMasv.setText(sv.getMasv());
            txtMasv.setEditable(false); txtMasv.setBackground(new Color(240, 240, 240));
            txtTen.setText(sv.getHoten());
            txtLop.setText(sv.getLop());
            cbGioiTinh.setSelectedItem(sv.getGioitinh());
            txtNgaySinh.setText(sv.getNgaysinh() != null ? new SimpleDateFormat("dd/MM/yyyy").format(sv.getNgaysinh()) : "");
            txtDiaChi.setText(sv.getDiachi());
            txtEmail.setText(sv.getEmail());
            txtSdt.setText(sv.getSdt());
            txtUsername.setText(sv.getUsername());
            txtUsername.setEditable(false); txtUsername.setBackground(new Color(240, 240, 240));
            txtPassword.setText(sv.getPassword());
        }
    }
}
