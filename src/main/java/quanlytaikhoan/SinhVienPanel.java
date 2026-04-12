package quanlytaikhoan;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    public SinhVienPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlNorth = new JPanel(new GridLayout(2, 1));
        JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN SINH VIÊN", 0);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        JPanel pnlSearch = new JPanel();
        pnlSearch.add(new JLabel("Tìm kiếm:"));
        pnlSearch.add(txtTimkiem = new JTextField(15));
        pnlSearch.add(btnTimkiem = new JButton("Tìm kiếm"));
        
        pnlNorth.add(lblTitle); pnlNorth.add(pnlSearch);
        add(pnlNorth, BorderLayout.NORTH);

        JPanel pnlWest = new JPanel(new GridLayout(10, 2, 5, 5));
        pnlWest.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        pnlWest.setPreferredSize(new Dimension(250, 0));

        pnlWest.add(new JLabel(" Mã SV:")); pnlWest.add(txtMasv = new JTextField()); 
        pnlWest.add(new JLabel(" Họ tên:")); pnlWest.add(txtTen = new JTextField());
        pnlWest.add(new JLabel(" Lớp:")); pnlWest.add(txtLop = new JTextField());
        pnlWest.add(new JLabel(" Giới tính:")); pnlWest.add(cbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ" , "Khác"}));
        pnlWest.add(new JLabel(" Ngày sinh:")); pnlWest.add(txtNgaySinh = new JTextField());
        pnlWest.add(new JLabel(" Địa chỉ:")); pnlWest.add(txtDiaChi = new JTextField());
        pnlWest.add(new JLabel(" Email:")); pnlWest.add(txtEmail = new JTextField());
        pnlWest.add(new JLabel(" SĐT:")); pnlWest.add(txtSdt = new JTextField());
        pnlWest.add(new JLabel(" Username:")); pnlWest.add(txtUsername = new JTextField());
        pnlWest.add(new JLabel(" Password:")); pnlWest.add(txtPassword = new JTextField());

        add(pnlWest, BorderLayout.WEST);

        model = new DefaultTableModel(new String[]{"Mã SV", "Họ tên", "Lớp", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "SĐT", "Username", "Password"}, 0);
        add(new JScrollPane(tblSV = new JTable(model)), BorderLayout.CENTER);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(btnThem = new JButton("Thêm mới"));
        pnlSouth.add(btnSua = new JButton("Cập nhật"));
        pnlSouth.add(btnXoa = new JButton("Xóa"));
        pnlSouth.add(btnLamMoi = new JButton("Làm mới"));
        add(pnlSouth, BorderLayout.SOUTH);

        loadData();
        addEvents();
    }
    private void addEvents() {
    tblSV.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int row = tblSV.getSelectedRow();
        if (row != -1) {
            try {
                txtMasv.setText(model.getValueAt(row, 0).toString()); 
                txtMasv.setEditable(false); txtMasv.setBackground(new Color(240, 240, 240));
                txtTen.setText(model.getValueAt(row, 1).toString());
                txtLop.setText(model.getValueAt(row, 2).toString());
                cbGioiTinh.setSelectedItem(model.getValueAt(row, 3).toString());
                txtNgaySinh.setText(model.getValueAt(row, 4).toString());
                txtDiaChi.setText(model.getValueAt(row, 5).toString());
                txtEmail.setText(model.getValueAt(row, 6).toString());
                txtSdt.setText(model.getValueAt(row, 7).toString());
                txtUsername.setText(model.getValueAt(row, 8).toString());
                txtUsername.setEditable(false); txtUsername.setBackground(new Color(240, 240, 240));
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

        btnSua.addActionListener(e -> {
            int row = tblSV.getSelectedRow();
            if (row == -1) return;
            int id = svDAO.getAll().get(row).getId(); 
            SinhVien sv = getForm();
            sv.setId(id);
            if(svDAO.update(sv)) {
                loadData();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            }
        });

        btnXoa.addActionListener(e -> {
            int row = tblSV.getSelectedRow();
            if (row == -1) return;
            int id = svDAO.getAll().get(row).getId();
            if(JOptionPane.showConfirmDialog(this, "Xóa sinh viên này?", "Xác nhận", 0) == 0) {
                if(svDAO.delete(id)) loadData();
            }
        });

        btnLamMoi.addActionListener(e -> clearForm());
        btnTimkiem.addActionListener(e -> fillTable(svDAO.search(txtTimkiem.getText().trim())));
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
        txtMasv.setText(""); txtMasv.setEditable(true); txtMasv.setBackground(Color.WHITE);
        txtTen.setText(""); 
        txtLop.setText(""); 
        txtNgaySinh.setText("");
        txtDiaChi.setText(""); 
        txtEmail.setText(""); 
        txtSdt.setText(""); 
        txtUsername.setText(""); txtUsername.setEditable(true); txtUsername.setBackground(Color.WHITE);
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
}