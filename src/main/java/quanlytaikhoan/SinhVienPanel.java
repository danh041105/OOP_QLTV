package quanlytaikhoan;

import dao.SinhVienDAO;
import model.SinhVien;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SinhVienPanel extends JPanel {
    private JTable tblSV;
    private DefaultTableModel model;
    private SinhVienDAO svDAO = new SinhVienDAO();

    private JTextField txtMasv, txtTen, txtLop, txtNgaySinh, txtDiaChi, txtEmail, txtSdt, txtTimkiem, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbGioiTinh;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimkiem;

    private boolean isProfileMode = false;
    private SinhVien currentSV;

    public SinhVienPanel() {
        this(null);
    }

    public SinhVienPanel(SinhVien sv) {
        if (sv != null) {
            this.isProfileMode = true;
            this.currentSV = sv;
        }
        setLayout(new BorderLayout(10, 10));
        setBackground(ThemeUtils.BG_MAIN);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // === TITLE ===
        if (!isProfileMode) {
            JPanel pnlNorth = new JPanel(new BorderLayout(0, 10));
            pnlNorth.setBackground(ThemeUtils.BG_MAIN);

            JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN SINH VIÊN", JLabel.LEFT);
            lblTitle.setFont(ThemeUtils.FONT_HEADING);
            lblTitle.setForeground(ThemeUtils.TEXT_PRIMARY);

            JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
            pnlSearch.setBackground(ThemeUtils.BG_CARD);
            pnlSearch.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeUtils.BORDER),
                    new EmptyBorder(8, 15, 8, 15)));

            pnlSearch.add(ThemeUtils.createLabel("Tìm kiếm:"));
            txtTimkiem = ThemeUtils.createTextField(15);
            pnlSearch.add(txtTimkiem);
            btnTimkiem = ThemeUtils.createPrimaryButton("Tìm kiếm");
            pnlSearch.add(btnTimkiem);

            pnlNorth.add(lblTitle, BorderLayout.NORTH);
            pnlNorth.add(pnlSearch, BorderLayout.CENTER);
            add(pnlNorth, BorderLayout.NORTH);
        } else {
            JLabel lblTitle = new JLabel("HỒ SƠ CÁ NHÂN", JLabel.CENTER);
            lblTitle.setFont(ThemeUtils.FONT_HEADING);
            lblTitle.setForeground(ThemeUtils.TEXT_PRIMARY);
            lblTitle.setBorder(new EmptyBorder(5, 0, 10, 0));
            add(lblTitle, BorderLayout.NORTH);
        }

        // === CENTER AREA ===
        JPanel pnlCenter = new JPanel(new BorderLayout(15, 0));
        pnlCenter.setBackground(ThemeUtils.BG_MAIN);

        // === LEFT FORM CARD ===
        JPanel pnlFormCard = ThemeUtils.createCardPanel(20);
        pnlFormCard.setPreferredSize(new Dimension(380, 0));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setOpaque(false);
        pnlForm.setBackground(ThemeUtils.BG_CARD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 3, 5, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form header
        JLabel lblFormTitle = new JLabel("Thông tin sinh viên");
        lblFormTitle.setFont(ThemeUtils.FONT_SUBHEADING);
        lblFormTitle.setForeground(ThemeUtils.PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pnlForm.add(lblFormTitle, gbc);
        gbc.gridwidth = 1;

        // Row 1: Mã SV
        gbc.gridy = 1;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Mã SV:"), gbc);
        gbc.gridx = 1;
        txtMasv = ThemeUtils.createTextField(15);
        pnlForm.add(txtMasv, gbc);

        // Row 2: Họ tên
        gbc.gridy = 2;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        txtTen = ThemeUtils.createTextField(15);
        pnlForm.add(txtTen, gbc);

        // Row 3: Lớp
        gbc.gridy = 3;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Lớp:"), gbc);
        gbc.gridx = 1;
        txtLop = ThemeUtils.createTextField(15);
        pnlForm.add(txtLop, gbc);

        // Row 4: Giới tính
        gbc.gridy = 4;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        cbGioiTinh = ThemeUtils.createComboBox(new String[] { "Nam", "Nữ", "Khác" });
        pnlForm.add(cbGioiTinh, gbc);

        // Row 5: Ngày sinh
        gbc.gridy = 5;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1;
        txtNgaySinh = ThemeUtils.createTextField(15);
        pnlForm.add(txtNgaySinh, gbc);

        // Row 6: Địa chỉ
        gbc.gridy = 6;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        txtDiaChi = ThemeUtils.createTextField(15);
        pnlForm.add(txtDiaChi, gbc);

        // Row 7: Email
        gbc.gridy = 7;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = ThemeUtils.createTextField(15);
        pnlForm.add(txtEmail, gbc);

        // Row 8: SĐT
        gbc.gridy = 8;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("SĐT:"), gbc);
        gbc.gridx = 1;
        txtSdt = ThemeUtils.createTextField(15);
        pnlForm.add(txtSdt, gbc);

        // Row 9: Username
        gbc.gridy = 9;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = ThemeUtils.createTextField(15);
        pnlForm.add(txtUsername, gbc);

        // Row 10: Password
        gbc.gridy = 10;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = ThemeUtils.createPasswordField(15);
        pnlForm.add(txtPassword, gbc);

        JScrollPane formScroll = new JScrollPane(pnlForm);
        formScroll.setBorder(null);
        formScroll.setOpaque(false);
        formScroll.getViewport().setOpaque(false);
        pnlFormCard.add(formScroll, BorderLayout.CENTER);

        pnlCenter.add(pnlFormCard, BorderLayout.WEST);

        // === RIGHT CONTENT ===
        if (!isProfileMode) {
            // Admin mode: Table
            model = new DefaultTableModel(new String[] { "Mã SV", "Họ tên", "Lớp", "Giới tính", "Ngày sinh", "Địa chỉ",
                    "Email", "SĐT", "Username" }, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tblSV = new JTable(model);
            ThemeUtils.styleTable(tblSV);

            JScrollPane scrollPane = new JScrollPane(tblSV);
            ThemeUtils.styleScrollPane(scrollPane);
            pnlCenter.add(scrollPane, BorderLayout.CENTER);
        } else {
            // Profile mode: Banner image
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/images/student_banner.jpg"));
                Image img = icon.getImage().getScaledInstance(900, 300, Image.SCALE_SMOOTH);
                JLabel lblBanner = new JLabel(new ImageIcon(img));
                lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
                lblBanner.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
                pnlCenter.add(lblBanner, BorderLayout.CENTER);
            } catch (Exception ex) {
                // Fallback if image not found
                JPanel pnlPlaceholder = ThemeUtils.createCardPanel(20);
                JLabel lblPlaceholder = new JLabel("Banner sinh viên");
                lblPlaceholder.setFont(ThemeUtils.FONT_HEADING);
                lblPlaceholder.setForeground(ThemeUtils.TEXT_MUTED);
                lblPlaceholder.setHorizontalAlignment(SwingConstants.CENTER);
                pnlPlaceholder.add(lblPlaceholder, BorderLayout.CENTER);
                pnlCenter.add(pnlPlaceholder, BorderLayout.CENTER);
            }
        }

        add(pnlCenter, BorderLayout.CENTER);

        // === BOTTOM BUTTONS ===
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        pnlSouth.setBackground(ThemeUtils.BG_MAIN);
        pnlSouth.setBorder(new EmptyBorder(10, 0, 0, 0));

        if (!isProfileMode) {
            btnThem = ThemeUtils.createSuccessButton("Thêm mới");
            pnlSouth.add(btnThem);

            btnSua = ThemeUtils.createPrimaryButton("Cập nhật");
            pnlSouth.add(btnSua);

            btnXoa = ThemeUtils.createDangerButton("Xóa");
            pnlSouth.add(btnXoa);

            btnLamMoi = ThemeUtils.createSecondaryButton("Làm mới");
            pnlSouth.add(btnLamMoi);
        } else {
            btnSua = ThemeUtils.createSuccessButton("Lưu thay đổi");
            pnlSouth.add(btnSua);

            JLabel lblInfo = ThemeUtils.createLabel("(Mã SV và Username không được phép thay đổi)");
            lblInfo.setFont(ThemeUtils.FONT_SMALL);
            lblInfo.setForeground(ThemeUtils.TEXT_MUTED);
            pnlSouth.add(lblInfo);
        }
        add(pnlSouth, BorderLayout.SOUTH);

        if (isProfileMode) {
            setForm(currentSV);
        } else {
            loadData();
        }
        addEvents();
    }

    private void addEvents() {
        if (!isProfileMode) {
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
                            txtPassword.setText(""); // Không hiển thị mật khẩu đã băm (security)
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
                if (row == -1)
                    return;
                int id = svDAO.getAll().get(row).getId();
                if (JOptionPane.showConfirmDialog(this, "Xóa sinh viên này?", "Xác nhận", 0) == 0) {
                    if (svDAO.delete(id))
                        loadData();
                }
            });

            btnLamMoi.addActionListener(e -> clearForm());
            btnTimkiem.addActionListener(e -> fillTable(svDAO.search(txtTimkiem.getText().trim())));
        }

        btnSua.addActionListener(e -> {
            int id;
            if (isProfileMode) {
                id = currentSV.getId();
            } else {
                int row = tblSV.getSelectedRow();
                if (row == -1)
                    return;
                id = svDAO.getAll().get(row).getId();
            }
            SinhVien sv = getForm();
            sv.setId(id);
            if (svDAO.update(sv)) {
                if (!isProfileMode)
                    loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            }
        });
    }

    private void setForm(SinhVien sv) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtMasv.setText(sv.getMasv());
        txtMasv.setEditable(false);
        txtMasv.setBackground(new Color(240, 240, 240));
        txtTen.setText(sv.getHoten());
        txtLop.setText(sv.getLop());
        cbGioiTinh.setSelectedItem(sv.getGioitinh());
        txtNgaySinh.setText(sv.getNgaysinh() != null ? sdf.format(sv.getNgaysinh()) : "");
        txtDiaChi.setText(sv.getDiachi());
        txtEmail.setText(sv.getEmail());
        txtSdt.setText(sv.getSdt());
        txtUsername.setText(sv.getUsername());
        txtUsername.setEditable(false);
        txtUsername.setBackground(new Color(240, 240, 240));
        txtPassword.setText(""); // Security: Không hiển thị ngược lại mật khẩu đã mã hóa
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
                    txtSdt.getText().trim());
            sv.setUsername(txtUsername.getText().trim());
            sv.setPassword(new String(txtPassword.getPassword()));
            return sv;
        } catch (Exception e) {
            return null;
        }
    }

    private void clearForm() {
        txtMasv.setText("");
        txtMasv.setEditable(true);
        txtMasv.setBackground(Color.WHITE);
        txtTen.setText("");
        txtLop.setText("");
        txtNgaySinh.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSdt.setText("");
        txtUsername.setText("");
        txtUsername.setEditable(true);
        txtUsername.setBackground(Color.WHITE);
        txtPassword.setText("");
        tblSV.clearSelection();
    }

    private void fillTable(ArrayList<SinhVien> list) {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (SinhVien sv : list) {
            model.addRow(new Object[] { sv.getMasv(), sv.getHoten(), sv.getLop(), sv.getGioitinh(),
                    sv.getNgaysinh() != null ? sdf.format(sv.getNgaysinh()) : "", sv.getDiachi(),
                    sv.getEmail(), sv.getSdt(), sv.getUsername() });
        }
    }

    private void loadData() {
        fillTable(svDAO.getAll());
    }
}
