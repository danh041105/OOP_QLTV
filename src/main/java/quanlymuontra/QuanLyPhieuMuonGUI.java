/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlymuontra;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.HinhPhatDAO;
import qltv.AdminGUI;
import dao.UserDAO;
import model.PhieuMuon;
import dao.PhieuMuonDAO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuanLyPhieuMuonGUI extends JFrame {
    private PhieuMuonDAO PhieuMuonDAO = new PhieuMuonDAO();
    private HinhPhatDAO hinhPhatDAO = new HinhPhatDAO();
    private JTable tblPhieuMuon;
    private DefaultTableModel modelPhieuMuon;
    private JTextField txtMaSV, txtTenSV, txtTenSach;
    private JButton btnDaTra, btnDangMuon, btnQuaHan, btnTraCham, btnTatCaPM;
    private JButton btnTimKiem, btnThem, btnSua, btnXoa, btnPhat;

    public QuanLyPhieuMuonGUI() {
        super("Quản lý phiếu mượn");
        initGUI();
        
        QuanLyMuonTra_Main.setupMenuBar(this);
        addEvents();

        loadDataPhieuMuon("");
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
        
        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBackground(Color.WHITE); 
        pnMain.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // thống kê tình trạng
        JPanel pnWest = new JPanel();
        pnWest.setLayout(new BoxLayout(pnWest, BoxLayout.Y_AXIS));
        pnWest.setPreferredSize(new Dimension(200, 0));
        pnWest.setBorder(BorderFactory.createTitledBorder("Thống kê tình trạng"));

        btnDaTra = new JButton("Đã trả: 0");
        btnDangMuon = new JButton("Đang mượn: 0");
        btnQuaHan = new JButton("Quá hạn trả: 0");
        btnTraCham = new JButton("Trả chậm: 0");
        btnTatCaPM = new JButton("Hiển thị tất cả");

        JButton[] sideBtns = {btnDaTra, btnDangMuon, btnQuaHan, btnTraCham, btnTatCaPM};
        for (JButton b : sideBtns) {
            b.setMaximumSize(new Dimension(200, 40));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnWest.add(Box.createVerticalStrut(10));
            pnWest.add(b);
        }

        // 
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 10));
        
        JPanel pnlheader = new JPanel(new BorderLayout());
        JLabel lblHeaderTitle = new JLabel("QUẢN LÝ PHIẾU MƯỢN", JLabel.CENTER);
        lblHeaderTitle.setFont(new Font("Segoe UI", Font.BOLD, 24)); 
        lblHeaderTitle.setForeground(new Color(0, 51, 153)); 
        lblHeaderTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 
        pnlheader.add(lblHeaderTitle, BorderLayout.NORTH);
        
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        txtMaSV = new JTextField(6);
        txtTenSV = new JTextField(10);
        txtTenSach = new JTextField(10);
        btnTimKiem = new JButton("Tìm kiếm");
        btnThem = new JButton("+ Thêm phiếu mượn");
        btnThem.setBackground(new Color(40, 167, 69)); btnThem.setForeground(Color.WHITE);
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(Color.RED); btnXoa.setForeground(Color.WHITE);
        btnPhat = new JButton("Hình phạt");

        pnlTop.add(new JLabel("Mã SV:")); 
        pnlTop.add(txtMaSV);
        pnlTop.add(new JLabel("Tên SV:")); 
        pnlTop.add(txtTenSV);
        pnlTop.add(new JLabel("Tên sách:")); 
        pnlTop.add(txtTenSach);
        
        pnlTop.add(btnTimKiem); pnlTop.add(btnThem);
        pnlTop.add(new JLabel("|")); 
        pnlTop.add(btnSua); 
        pnlTop.add(btnXoa); 
        pnlTop.add(btnPhat);
        
        pnlheader.add(pnlTop, BorderLayout.CENTER);

        // Table
        String[] cols = {"Mã PM", "Mã SV", "Họ Tên", "Mã sách", "Tên sách", "Tác giả", "Nhà XB", "SL", "Ngày mượn", "Ngày trả", "Tình trạng"};
        modelPhieuMuon = new DefaultTableModel(cols, 0) {
        @Override
        public boolean isCellEditable(int row, int column) { 
            return false; 
        }
};
        tblPhieuMuon = new JTable(modelPhieuMuon);
        
        tblPhieuMuon.getColumnModel().getColumn(4).setPreferredWidth(210); // Tên sách
        tblPhieuMuon.getColumnModel().getColumn(2).setPreferredWidth(130); // Họ tên
        tblPhieuMuon.getColumnModel().getColumn(5).setPreferredWidth(120); // Tác giả
        tblPhieuMuon.getColumnModel().getColumn(10).setPreferredWidth(100); // Tình trạng
        
        pnlCenter.add(pnlheader, BorderLayout.NORTH);
        pnlCenter.add(new JScrollPane(tblPhieuMuon), BorderLayout.CENTER);

        pnMain.add(pnWest, BorderLayout.WEST);
        pnMain.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnMain, BorderLayout.CENTER);
    }

    private void addEvents() {
        btnTatCaPM.addActionListener(e -> loadDataPhieuMuon(""));
        btnDaTra.addActionListener(e -> loadDataPhieuMuon("Đã trả"));
        btnDangMuon.addActionListener(e -> loadDataPhieuMuon("Đang mượn"));
        btnQuaHan.addActionListener(e -> loadDataPhieuMuon("Quá hạn trả"));
        btnTraCham.addActionListener(e -> loadDataPhieuMuon("Trả chậm"));

        btnTimKiem.addActionListener(e -> {
            updateTablePM(PhieuMuonDAO.searchPhieuMuon(txtMaSV.getText().trim(), txtTenSV.getText().trim(), txtTenSach.getText().trim()));
        });

        btnThem.addActionListener(e -> {
            FormThemPhieuMuon form = new FormThemPhieuMuon(this);

            form.setVisible(true);

            if (form.isSuccess()) {
                loadDataPhieuMuon("");
            }
        });

        btnSua.addActionListener(e -> {
            int row = tblPhieuMuon.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu mượn từ danh sách để sửa!");
                return;
            }

            int columnCount = tblPhieuMuon.getColumnCount();
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = tblPhieuMuon.getValueAt(row, i);
            }

            FormSuaPhieuMuon formSua = new FormSuaPhieuMuon(this, rowData);
            formSua.setVisible(true);

            if (formSua.isSuccess()) {
                loadDataPhieuMuon(""); 
            }
        });

        btnXoa.addActionListener(e -> {
            int row = tblPhieuMuon.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu mượn cần xóa!");
                return;
            }
            String maPM = tblPhieuMuon.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu mượn " + maPM + "?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                int result = PhieuMuonDAO.deletePhieuMuon(maPM);
                switch (result) {
                    case 1: 
                        JOptionPane.showMessageDialog(this, "Xóa phiếu mượn thành công!");
                        loadDataPhieuMuon(""); 
                        break;

                    case 2: 
                        JOptionPane.showMessageDialog(this, 
                            "Không thể xóa! Phiếu mượn này chưa ở trạng thái 'Đã trả'.", 
                            "Cảnh báo", 
                            JOptionPane.WARNING_MESSAGE);
                        break;

                    case 3: 
                        JOptionPane.showMessageDialog(this, 
                            "Phiếu mượn này có hình phạt chưa hoàn thành nên không thể xóa!", 
                            "Vi phạm chưa xử lý", 
                            JOptionPane.ERROR_MESSAGE);
                        break;

                    default: 
                        JOptionPane.showMessageDialog(this, 
                            "Lỗi hệ thống hoặc không tìm thấy mã phiếu mượn này!", 
                            "Lỗi", 
                            JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });

        
        btnPhat.addActionListener(e -> {
            int row = tblPhieuMuon.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu mượn để xử lý vi phạm!");
                return;
            }

            int columnCount = tblPhieuMuon.getColumnCount();
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = tblPhieuMuon.getValueAt(row, i);
            }

            FormThemHinhPhat formPhat = new FormThemHinhPhat(this, rowData); 
            formPhat.setVisible(true); 

            if (formPhat.isSuccess()) {
                this.dispose(); 
                JOptionPane.showMessageDialog(null, "Hệ thống sẽ chuyển đến trang Hình Phạt!");
                QuanLyHinhPhatGUI qlhp = new QuanLyHinhPhatGUI(); 
                qlhp.setVisible(true);
            }
        });

    }

    public void loadDataPhieuMuon(String tinhTrang) {
        updateTablePM(PhieuMuonDAO.layDuLieuPhieuMuon(tinhTrang));
        int[] counts = PhieuMuonDAO.laySoLuongThongKe();
        btnDaTra.setText("Đã trả: " + counts[0]);
        btnDangMuon.setText("Đang mượn: " + counts[1]);
        btnQuaHan.setText("Quá hạn trả: " + counts[2]);
        btnTraCham.setText("Trả chậm: " + counts[3]);
    }
    
    private void updateTablePM(java.util.List<PhieuMuon> ds) {
        modelPhieuMuon.setRowCount(0); 
        for (PhieuMuon pm : ds) {
            modelPhieuMuon.addRow(new Object[]{
                pm.getMaPM(),      
                pm.getMaSV(),      
                pm.getHoTen(),    
                pm.getMaSach(),   
                pm.getTenSach(),  
                pm.getTentg(),     
                pm.getNhaxb(),    
                pm.getSoLuong(),   
                pm.getNgayMuon(),  
                pm.getNgayTra(),   
                pm.getTinhTrang()  
            });
        }
    }
    
    public static void main(String[] args) {
        QuanLyPhieuMuonGUI qlpm = new QuanLyPhieuMuonGUI();
        qlpm.setVisible(true);
    }
}