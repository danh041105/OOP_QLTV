/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlymuontra;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.HinhPhatDAO;
import model.HinhPhat;
import qltv.AdminGUI;
import dao.UserDAO;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.border.LineBorder;

public class QuanLyHinhPhatGUI extends JFrame {
    private JTextField txtMaSV, txtTenSV;
    private JButton btnTimKiemHP, btnTatCaHP, btnSuaHP, btnXoaHP;
    private JTable tblHinhPhat;
    private DefaultTableModel modelHinhPhat;
    private HinhPhatDAO hinhPhatDAO = new HinhPhatDAO();
    private JLabel lblHeaderTitle;

    public QuanLyHinhPhatGUI() {
        
        this(null);
    }

    
    public QuanLyHinhPhatGUI(Frame parent) {
        super("Quản lý hình phạt");
        initGUI();
        QuanLyMuonTra_Main.setupMenuBar(this); 
        addAction();
        loadDataHinhPhat(""); 
        this.setSize(1400, 800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                UserDAO dao = new UserDAO();
                new AdminGUI(dao.getMaADMIN_isLogin()).setVisible(true);
            }
        });
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 246, 250));

        JLabel lblHeaderTitle = new JLabel("QUẢN LÝ HÌNH PHẠT", JLabel.CENTER);
        lblHeaderTitle.setFont(new Font("Segoe UI", Font.BOLD, 24)); 
        lblHeaderTitle.setForeground(new Color(0, 51, 153)); 
        lblHeaderTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 
        this.add(lblHeaderTitle, BorderLayout.NORTH);

        //PHẦN THÂN (CHỨA TÌM KIẾM VÀ BẢNG)
        JPanel pnlMain = new JPanel(new BorderLayout(0, 15));
        pnlMain.setOpaque(false); 
        pnlMain.setBorder(new EmptyBorder(0, 30, 20, 30));
        
        JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnTimKiem.setBackground(Color.WHITE);
        pnTimKiem.setBorder(new LineBorder(new Color(210, 210, 210), 1)); 
        
        txtMaSV = new JTextField(10);
        txtTenSV = new JTextField(15);

        btnTimKiemHP = new JButton("Tìm kiếm");
        btnTatCaHP = new JButton("Hiển thị tất cả");
        btnSuaHP = new JButton("Sửa");
        btnSuaHP.setBackground(new Color(52, 152, 219));
        btnSuaHP.setForeground(Color.WHITE);
        btnXoaHP = new JButton("Xóa");
        btnXoaHP.setBackground(new Color(231, 76, 60));
        btnXoaHP.setForeground(Color.WHITE);

        pnTimKiem.add(new JLabel("Mã SV:")); 
        pnTimKiem.add(txtMaSV);
        pnTimKiem.add(new JLabel("Tên SV:")); 
        pnTimKiem.add(txtTenSV);
        pnTimKiem.add(btnTimKiemHP); 
        pnTimKiem.add(btnTatCaHP);
        pnTimKiem.add(new JLabel("|")); 
        pnTimKiem.add(btnSuaHP); 
        pnTimKiem.add(btnXoaHP);
        
        pnlMain.add(pnTimKiem, BorderLayout.NORTH);

        String[] columns = {"Mã HP", "Mã SV", "Tên SV", "Tình trạng", "Lý do", "Ngày phạt", "Hình thức", "Tiến độ"};
        modelHinhPhat = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblHinhPhat = new JTable(modelHinhPhat);
        tblHinhPhat.setRowHeight(35); 
        tblHinhPhat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblHinhPhat.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblHinhPhat.getTableHeader().setBackground(new Color(41, 128, 185)); 
        tblHinhPhat.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tblHinhPhat);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        this.add(pnlMain, BorderLayout.CENTER);
    }

    private void addAction() {
        btnTimKiemHP.addActionListener(e -> loadDataHinhPhat("search"));
        btnTatCaHP.addActionListener(e -> {
            txtMaSV.setText("");
            txtTenSV.setText("");
            loadDataHinhPhat("");
        });

        btnSuaHP.addActionListener(e -> {
            int row = tblHinhPhat.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Hãy chọn 1 hình phạt để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Object[] rowData = new Object[tblHinhPhat.getColumnCount()];
            for (int i = 0; i < tblHinhPhat.getColumnCount(); i++) {
                rowData[i] = tblHinhPhat.getValueAt(row, i);
            }
            FormSuaHinhPhat formSua = new FormSuaHinhPhat(this, rowData);
            formSua.setVisible(true);
            if (formSua.isSuccess()) loadDataHinhPhat("");
        });

        btnXoaHP.addActionListener(e -> {
            int row = tblHinhPhat.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
                return;
            }
            String maHP = tblHinhPhat.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hình phạt mã " + maHP + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int res = hinhPhatDAO.deleteHinhPhat(Integer.parseInt(maHP));
                if (res == 1) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadDataHinhPhat("");
                } else if (res == 2) {
                    JOptionPane.showMessageDialog(this, "Chỉ được xóa khi tiến độ 'Đã hoàn thành'!");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!");
                }
            }
        });
    }

    private void loadDataHinhPhat(String mode) {
        String maSV = mode.equals("search") ? txtMaSV.getText().trim() : "";
        String tenSV = mode.equals("search") ? txtTenSV.getText().trim() : "";

        java.util.List<HinhPhat> ds = hinhPhatDAO.layDanhSachHinhPhat(maSV, tenSV);

        modelHinhPhat.setRowCount(0); 
        for (HinhPhat hp : ds) {
            modelHinhPhat.addRow(new Object[]{
                hp.getMaHP(),
                hp.getMaSV(),
                hp.getHoTen(),
                hp.getTinhTrang(),
                hp.getLyDo(),
                hp.getNgayPhat(),
                hp.getHinhThuc(),
                hp.getTienDo()
            });
        }
        setupTableColumns(); 
    }

    private void setupTableColumns() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblHinhPhat.getColumnCount(); i++) {
            tblHinhPhat.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static void main(String[] args) {
        QuanLyHinhPhatGUI qlhp = new QuanLyHinhPhatGUI();
        qlhp.setVisible(true);
    }
}