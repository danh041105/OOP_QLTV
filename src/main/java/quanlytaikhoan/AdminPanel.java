package quanlytaikhoan;

import dao.AdminDAO;
import model.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminPanel extends JPanel {

    private JTable tblAdmin;
    private DefaultTableModel model;
    private AdminDAO adminDAO = new AdminDAO();
    private JTextField txtMaadmin, txtHoten, txtEmail, txtSdt, txtUsername, txtPassword;
    private JComboBox<String> cbGioiTinh;
    private JButton btnSua, btnLamMoi;
    
    public AdminPanel() {
        setLayout(new BorderLayout(10, 10));
        JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN ADMIN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 51, 153));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlWest = new JPanel(new GridLayout(8, 2, 5, 5));
        pnlWest.setBorder(BorderFactory.createTitledBorder("Thông tin Admin"));
        pnlWest.setPreferredSize(new Dimension(250, 0));

        pnlWest.add(new JLabel(" Mã Admin:")); pnlWest.add(txtMaadmin = new JTextField()); txtMaadmin.setEditable(false);
        pnlWest.add(new JLabel(" Họ tên:")); pnlWest.add(txtHoten = new JTextField());
        pnlWest.add(new JLabel(" Giới tính:")); pnlWest.add(cbGioiTinh = new JComboBox<>(new String[]{"Nam","Nữ", "Khác"}));
        pnlWest.add(new JLabel(" SĐT:")); pnlWest.add(txtSdt = new JTextField());
        pnlWest.add(new JLabel(" Email:")); pnlWest.add(txtEmail = new JTextField());
        pnlWest.add(new JLabel(" Username:")); pnlWest.add(txtUsername = new JTextField());txtUsername.setEditable(false);
        pnlWest.add(new JLabel(" Password:")); pnlWest.add(txtPassword = new JTextField());

        add(pnlWest, BorderLayout.WEST);

        model = new DefaultTableModel(
                new String[]{"Mã Admin", "Họ tên", "Giới tính", "SĐT", "Email","Username","Password"}, 0);
        tblAdmin = new JTable(model);
        add(new JScrollPane(tblAdmin), BorderLayout.CENTER);

        JPanel pnlBtn = new JPanel();
        btnSua = new JButton("Cập nhật");
        btnLamMoi = new JButton("Làm mới");

        pnlBtn.add(btnSua);
        pnlBtn.add(btnLamMoi);
        add(pnlBtn, BorderLayout.SOUTH);

        loadData();
        addEvents();
    }

    private void addEvents() {
        tblAdmin.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                int row = tblAdmin.getSelectedRow();
                if(row != -1){
                    txtMaadmin.setText(model.getValueAt(row,0).toString());
                    txtHoten.setText(model.getValueAt(row,1).toString());
                    cbGioiTinh.setSelectedItem(model.getValueAt(row,2).toString());
                    txtSdt.setText(model.getValueAt(row,3).toString());
                    txtEmail.setText(model.getValueAt(row,4).toString());
                    txtUsername.setText(model.getValueAt(row,5).toString());
                    txtPassword.setText(model.getValueAt(row,6).toString());
                }
            }
        });

        btnSua.addActionListener(e -> {
            int row = tblAdmin.getSelectedRow();
            if(row == -1) return;
            int id = adminDAO.getAll().get(row).getId();
            Admin ad = getForm();
            ad.setId(id);
            if(adminDAO.update(ad)){
                loadData();
                JOptionPane.showMessageDialog(this,"Cập nhật thành công!");
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
        ad.setPassword(txtPassword.getText().trim());
        return ad;
    }

    private void clearForm() {
        txtMaadmin.setText(""); txtHoten.setText(""); txtSdt.setText(""); txtEmail.setText("");
        txtUsername.setText(""); txtPassword.setText(""); cbGioiTinh.setSelectedIndex(0);
        tblAdmin.clearSelection();
    }

    private void loadData() {
    model.setRowCount(0); 
    for (Admin ad : adminDAO.getAll()) {
        model.addRow(new Object[]{
            ad.getMaadmin(), ad.getHoten(), ad.getGioitinh(), ad.getSdt(),
            ad.getEmail(), ad.getUsername(), ad.getPassword()
        });
    }
}
}
