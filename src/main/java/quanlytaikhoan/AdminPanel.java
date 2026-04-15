package quanlytaikhoan;

import dao.AdminDAO;
import model.Admin;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminPanel extends JPanel {

    private JTable tblAdmin;
    private DefaultTableModel model;
    private AdminDAO adminDAO = new AdminDAO();
    private JTextField txtMaadmin, txtHoten, txtEmail, txtSdt, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbGioiTinh;
    private JButton btnThem, btnSua, btnLamMoi;

    public AdminPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(ThemeUtils.BG_MAIN);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // === TITLE ===
        JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN ADMIN", JLabel.CENTER);
        lblTitle.setFont(ThemeUtils.FONT_HEADING);
        lblTitle.setForeground(ThemeUtils.TEXT_PRIMARY);
        lblTitle.setBorder(new EmptyBorder(5, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        // === CENTER AREA ===
        JPanel pnlCenter = new JPanel(new BorderLayout(15, 0));
        pnlCenter.setBackground(ThemeUtils.BG_MAIN);

        // === LEFT FORM PANEL (Styled Card) ===
        JPanel pnlFormCard = ThemeUtils.createCardPanel(20);
        pnlFormCard.setPreferredSize(new Dimension(320, 0));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setOpaque(false);
        pnlForm.setBackground(ThemeUtils.BG_CARD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form header
        JLabel lblFormTitle = new JLabel("Thông tin Admin");
        lblFormTitle.setFont(ThemeUtils.FONT_SUBHEADING);
        lblFormTitle.setForeground(ThemeUtils.PRIMARY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pnlForm.add(lblFormTitle, gbc);
        gbc.gridwidth = 1;

        // Row 1: Mã Admin
        gbc.gridy = 1;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Mã Admin:"), gbc);
        gbc.gridx = 1;
        txtMaadmin = ThemeUtils.createTextField(15);
        txtMaadmin.setEditable(true);
        txtMaadmin.setBackground(Color.WHITE);
        pnlForm.add(txtMaadmin, gbc);

        // Row 2: Họ tên
        gbc.gridy = 2;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        txtHoten = ThemeUtils.createTextField(15);
        pnlForm.add(txtHoten, gbc);

        // Row 3: Giới tính
        gbc.gridy = 3;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        cbGioiTinh = ThemeUtils.createComboBox(new String[] { "Nam", "Nữ", "Khác" });
        pnlForm.add(cbGioiTinh, gbc);

        // Row 4: SĐT
        gbc.gridy = 4;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("SĐT:"), gbc);
        gbc.gridx = 1;
        txtSdt = ThemeUtils.createTextField(15);
        pnlForm.add(txtSdt, gbc);

        // Row 5: Email
        gbc.gridy = 5;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = ThemeUtils.createTextField(15);
        pnlForm.add(txtEmail, gbc);

        // Row 6: Username
        gbc.gridy = 6;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = ThemeUtils.createTextField(15);
        txtUsername.setEditable(true);
        txtUsername.setBackground(Color.WHITE);
        pnlForm.add(txtUsername, gbc);

        // Row 7: Password
        gbc.gridy = 7;
        gbc.gridx = 0;
        pnlForm.add(ThemeUtils.createLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = ThemeUtils.createPasswordField(15);
        pnlForm.add(txtPassword, gbc);

        pnlFormCard.add(pnlForm, BorderLayout.CENTER);
        pnlCenter.add(pnlFormCard, BorderLayout.WEST);

        // === RIGHT TABLE ===
        model = new DefaultTableModel(
                new String[] { "Mã Admin", "Họ tên", "Giới tính", "SĐT", "Email", "Username" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAdmin = new JTable(model);
        ThemeUtils.styleTable(tblAdmin);

        JScrollPane scrollPane = new JScrollPane(tblAdmin);
        ThemeUtils.styleScrollPane(scrollPane);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        // === BOTTOM BUTTONS ===
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlBtn.setBackground(ThemeUtils.BG_MAIN);
        pnlBtn.setBorder(new EmptyBorder(10, 0, 0, 0));

        btnThem = ThemeUtils.createPrimaryButton("Thêm mới");
        pnlBtn.add(btnThem);

        btnSua = ThemeUtils.createSuccessButton("Cập nhật");
        pnlBtn.add(btnSua);

        btnLamMoi = ThemeUtils.createSecondaryButton("Làm mới");
        pnlBtn.add(btnLamMoi);

        add(pnlBtn, BorderLayout.SOUTH);

        loadData();
        addEvents();
    }

    private void addEvents() {
        tblAdmin.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblAdmin.getSelectedRow();
                if (row != -1) {
                    txtMaadmin.setText(model.getValueAt(row, 0).toString());
                    txtHoten.setText(model.getValueAt(row, 1).toString());
                    cbGioiTinh.setSelectedItem(model.getValueAt(row, 2).toString());
                    txtSdt.setText(model.getValueAt(row, 3).toString());
                    txtEmail.setText(model.getValueAt(row, 4).toString());
                    txtUsername.setText(model.getValueAt(row, 5).toString());
                    txtPassword.setText(""); // Security

                    // Lock Maadmin and Username when selecting from table
                    txtMaadmin.setEditable(false);
                    txtMaadmin.setBackground(new Color(240, 240, 240));
                    txtUsername.setEditable(false);
                    txtUsername.setBackground(new Color(240, 240, 240));
                }
            }
        });

        btnThem.addActionListener(e -> {
            Admin ad = getForm();

            // 1. Kiểm tra bỏ trống
            if (ad.getMaadmin().isEmpty() || ad.getHoten().isEmpty() || ad.getEmail().isEmpty()
                    || ad.getSdt().isEmpty() || ad.getUsername().isEmpty() || ad.getPassword().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tất cả thông tin!", "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Kiểm tra trùng Username
            if (adminDAO.checkUsernameExists(ad.getUsername())) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập (Username) '" + ad.getUsername() + "' đã tồn tại!",
                        "Lỗi trùng lặp", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Kiểm tra trùng Mã Admin
            if (adminDAO.checkMaAdminExists(ad.getMaadmin())) {
                JOptionPane.showMessageDialog(this, "Mã Admin '" + ad.getMaadmin() + "' đã tồn tại!", "Lỗi trùng lặp",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (adminDAO.insert(ad)) {
                loadData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Thêm Admin thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu. Vui lòng thử lại.", "Lỗi hệ thống",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            int row = tblAdmin.getSelectedRow();
            if (row == -1)
                return;
            int id = adminDAO.getAll().get(row).getId();
            Admin ad = getForm();
            ad.setId(id);
            if (adminDAO.update(ad)) {
                loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            }
        });

        btnLamMoi.addActionListener(e -> clearForm());
    }

    private Admin getForm() {
        Admin ad = new Admin();
        ad.setMaadmin(txtMaadmin.getText().trim());
        ad.setHoten(txtHoten.getText().trim());
        ad.setGioitinh(cbGioiTinh.getSelectedItem().toString());
        ad.setSdt(txtSdt.getText().trim());
        ad.setEmail(txtEmail.getText().trim());
        ad.setUsername(txtUsername.getText().trim());
        ad.setPassword(new String(txtPassword.getPassword()));
        return ad;
    }

    private void clearForm() {
        txtMaadmin.setText("");
        txtHoten.setText("");
        txtSdt.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cbGioiTinh.setSelectedIndex(0);

        txtMaadmin.setEditable(true);
        txtMaadmin.setBackground(Color.WHITE);
        txtUsername.setEditable(true);
        txtUsername.setBackground(Color.WHITE);

        tblAdmin.clearSelection();
    }

    private void loadData() {
        model.setRowCount(0);
        for (Admin ad : adminDAO.getAll()) {
            model.addRow(new Object[] {
                    ad.getMaadmin(), ad.getHoten(), ad.getGioitinh(), ad.getSdt(),
                    ad.getEmail(), ad.getUsername()
            });
        }
    }
}
