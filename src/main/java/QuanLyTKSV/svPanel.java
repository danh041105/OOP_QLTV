package QuanLyTKSV;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class svPanel extends JPanel {
    private svDAO svDAO = new svDAO();
    private sv currentSV;

    private JTextField txtMasv, txtTen, txtLop, txtNgaySinh, txtDiaChi, txtEmail, txtSdt, txtUsername, txtPassword;
    private JComboBox<String> cbGioiTinh;
    private JButton btnSua, btnThem;

    public svPanel(String username) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        JLabel lblTitle = new JLabel("Thông tin sinh viên", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Thông tin chi tiết"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(8, 10, 8, 10);    

        initComponent();

        addControl(pnlForm, "Mã sinh viên:", txtMasv, 0, gbc);
        addControl(pnlForm, "Họ và tên:", txtTen, 1, gbc);
        addControl(pnlForm, "Lớp:", txtLop, 2, gbc);
        addControl(pnlForm, "Giới tính:", cbGioiTinh, 3, gbc);
        addControl(pnlForm, "Ngày sinh:", txtNgaySinh, 4, gbc);
        addControl(pnlForm, "Địa chỉ:", txtDiaChi, 5, gbc);
        addControl(pnlForm, "Email:", txtEmail, 6, gbc);
        addControl(pnlForm, "Sđt:", txtSdt, 7, gbc);
        addControl(pnlForm, "Username:", txtUsername, 8, gbc);
        addControl(pnlForm, "Password:", txtPassword, 9, gbc);

        add(new JScrollPane(pnlForm), BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButtons.add(btnSua);
        pnlButtons.add(btnThem);
        add(pnlButtons, BorderLayout.SOUTH);

        loadData(username);
        addEvents();
    }

    private void addControl(JPanel pnl, String labelText, JComponent field, int row, GridBagConstraints gbc) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.1; 
        pnl.add(new JLabel(labelText), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.9; 
        pnl.add(field, gbc);
    }

    private void initComponent() {
        btnSua = new JButton("Cập nhật");
        btnSua.setPreferredSize(new Dimension(150, 35));
        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(150, 35));


        txtMasv = new JTextField(); txtMasv.setEditable(false);
        txtTen = new JTextField(); txtTen.setEditable(false);
        txtLop = new JTextField(); txtLop.setEditable(false);
        cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"}); cbGioiTinh.setEnabled(false);
        txtNgaySinh = new JTextField(); txtNgaySinh.setEditable(false);
        txtUsername = new JTextField(); txtUsername.setEditable(false);
        txtDiaChi = new JTextField();txtDiaChi.setEditable(false);
        txtEmail = new JTextField();
        txtSdt = new JTextField();
        txtPassword = new JTextField(); 
    }
    
    private void loadData(String username) {
    currentSV = svDAO.getByUsername(username);
    if (currentSV == null) return;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    txtUsername.setText(currentSV.getUsername());
    txtPassword.setText(currentSV.getPassword());
    txtEmail.setText(currentSV.getEmail() != null ? currentSV.getEmail() : "");

    if (currentSV.getMasv() == null) {

        txtMasv.setEditable(true);
        txtTen.setEditable(true);
        txtLop.setEditable(true);
        cbGioiTinh.setEnabled(true);
        txtNgaySinh.setEditable(true);
        txtDiaChi.setEditable(true);

        btnSua.setVisible(false);
        btnThem.setVisible(true);

    }
    else {
        txtMasv.setText(currentSV.getMasv());
        txtTen.setText(currentSV.getHoten());
        txtLop.setText(currentSV.getLop());
        cbGioiTinh.setSelectedItem(currentSV.getGioitinh());
        txtNgaySinh.setText(
        currentSV.getNgaysinh() != null ? sdf.format(currentSV.getNgaysinh()) : "" );
        txtDiaChi.setText(currentSV.getDiachi());
        txtEmail.setText(currentSV.getEmail());
        txtSdt.setText(currentSV.getSdt());

        txtMasv.setEditable(false);
        txtTen.setEditable(false);
        txtLop.setEditable(false);
        cbGioiTinh.setEnabled(false);
        txtNgaySinh.setEditable(false);
        
        txtDiaChi.setEditable(true);
        txtEmail.setEditable(true);
        txtSdt.setEditable(true);

        btnThem.setVisible(false);
        btnSua.setVisible(true);
    }
}


    private void addEvents() {
            btnSua.addActionListener(e -> {
            currentSV.setDiachi(txtDiaChi.getText().trim());
            currentSV.setEmail(txtEmail.getText().trim());
            currentSV.setSdt(txtSdt.getText().trim());
            currentSV.setPassword(txtPassword.getText().trim());

            if (svDAO.updateProfile(currentSV)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi");
            }
        });
            btnThem.addActionListener(e -> {
            try {
                sv sv = new sv(
                currentSV.getId(),                
                txtMasv.getText().trim(),
                txtTen.getText().trim(),
                txtLop.getText().trim(),
                cbGioiTinh.getSelectedItem().toString(),
                new java.sql.Date(
                new SimpleDateFormat("dd/MM/yyyy")
                    .parse(txtNgaySinh.getText().trim()).getTime()),
                txtDiaChi.getText().trim(),
                txtEmail.getText().trim(),
                txtSdt.getText().trim());

        if (svDAO.insertProfile(sv)) {
            JOptionPane.showMessageDialog(this, "Thêm thông tin sinh viên thành công!");
            loadData(currentSV.getUsername()); 
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    });
    }
}
