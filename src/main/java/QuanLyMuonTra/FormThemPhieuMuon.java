/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyMuonTra;

import XuLySoLuong.LogicMuonTra;
import connect.connect;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import javax.swing.text.JTextComponent;

/**
 * @author Hien
 */

public class FormThemPhieuMuon extends JDialog {
    private JTextField txtMaPM, txtSoLuong, txtNgayMuon, txtNgayTra;
    private JComboBox<String> cbSinhVien, cbSach;
    private JLabel lblInfoSV, lblInfoSach;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false;

    public FormThemPhieuMuon(Frame parent) {
        super(parent, "Thêm phiếu mượn mới", true);
        this.setSize(450, 620); 
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        // Tiêu đề
        JLabel lblTitle = new JLabel("THÊM PHIẾU MƯỢN MỚI", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 144, 255));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        //nhập thông tin
        JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        JPanel pnInput = new JPanel(new GridLayout(6, 1, 0, 15));
        pnInput.setPreferredSize(new Dimension(350, 420));

        //Hàng 1: Mã phiếu
        JPanel pnMapm = new JPanel(new BorderLayout(0, 5));
        pnMapm.add(new JLabel("Mã phiếu (Tự động):"), BorderLayout.NORTH);
        txtMaPM = new JTextField(LogicMuonTra.taoMaPhieuMuonTuDong());
        txtMaPM.setEditable(false);
        txtMaPM.setBackground(new Color(240, 240, 240));
        txtMaPM.setPreferredSize(new Dimension(0, 30));
        pnMapm.add(txtMaPM, BorderLayout.CENTER);
        pnInput.add(pnMapm);

        //Hàng 2: Sinh viên
        JPanel pnSV = new JPanel(new BorderLayout(0, 2));
        pnSV.add(new JLabel("Mã/Tên Sinh viên:"), BorderLayout.NORTH);
        cbSinhVien = new JComboBox<>();
        cbSinhVien.setEditable(true);
        cbSinhVien.setPreferredSize(new Dimension(0, 30));
        setupAutoComplete(cbSinhVien, "sinh_vien", "ma_sv", "ho_ten");
        pnSV.add(cbSinhVien, BorderLayout.CENTER);
        lblInfoSV = new JLabel(" Nhập ký tự để tìm kiếm...");
        lblInfoSV.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblInfoSV.setForeground(Color.BLUE);
        pnSV.add(lblInfoSV, BorderLayout.SOUTH);
        pnInput.add(pnSV);

        //Hàng 3: Sách
        JPanel p3 = new JPanel(new BorderLayout(0, 2));
        p3.add(new JLabel("<html>Mã/Tên Sách: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        cbSach = new JComboBox<>();
        cbSach.setEditable(true);
        cbSach.setPreferredSize(new Dimension(0, 30));
        setupAutoComplete(cbSach, "sach", "ma_sach", "ten_sach");
        p3.add(cbSach, BorderLayout.CENTER);
        lblInfoSach = new JLabel(" Nhập ký tự để tìm kiếm...");
        lblInfoSach.setFont(new Font("Tahoma", Font.ITALIC, 11));
        lblInfoSach.setForeground(Color.BLUE);
        p3.add(lblInfoSach, BorderLayout.SOUTH);
        pnInput.add(p3);

        //Hàng 4: Số lượng 
        JPanel p4 = new JPanel(new BorderLayout(0, 5));
        p4.add(new JLabel("<html>Số lượng mượn: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        txtSoLuong = new JTextField("1");
        txtSoLuong.setPreferredSize(new Dimension(0, 30));
        p4.add(txtSoLuong, BorderLayout.CENTER);
        pnInput.add(p4);

        //Hàng 5: Ngày mượn
        JPanel pnNgayMuon = new JPanel(new BorderLayout(0, 5));
        pnNgayMuon.add(new JLabel("<html>Ngày mượn (yyyy-MM-dd): <font color='red'>*</font></html>"), BorderLayout.NORTH);
        txtNgayMuon = new JTextField(LocalDate.now().toString());
        txtNgayMuon.setPreferredSize(new Dimension(0, 30));
        pnNgayMuon.add(txtNgayMuon, BorderLayout.CENTER);
        pnInput.add(pnNgayMuon);

        //Hàng 6: Ngày trả
        JPanel pnNgayTra = new JPanel(new BorderLayout(0, 5));
        pnNgayTra.add(new JLabel("<html>Ngày trả (yyyy-MM-dd): <font color='red'>*</font></html>"), BorderLayout.NORTH);
        txtNgayTra = new JTextField();
        txtNgayTra.setPreferredSize(new Dimension(0, 30));
        pnNgayTra.add(txtNgayTra, BorderLayout.CENTER);
        pnInput.add(pnNgayTra);

        pnlCenter.add(pnInput);
        this.add(pnlCenter, BorderLayout.CENTER);

        //Vùng Nút bấm (Cùng một hàng)
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        btnLuu = new JButton("Lưu phiếu");
        btnLuu.setBackground(new Color(40, 167, 69));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLuu.setPreferredSize(new Dimension(150, 40));

        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setBackground(new Color(220, 53, 69));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnHuy.setPreferredSize(new Dimension(150, 40));

        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        this.add(pnlSouth, BorderLayout.SOUTH);

        btnLuu.addActionListener(e -> xuLyLuu());
        btnHuy.addActionListener(e -> dispose());
    }

    private void setupAutoComplete(JComboBox<String> comboBox, String table, String colMa, String colTen) {
        JTextComponent editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_ENTER) return;
                String input = editor.getText();
                if (input.isEmpty()) return;
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                String sql = "SELECT " + colMa + ", " + colTen + " FROM " + table + 
                        " WHERE " + colMa + " LIKE ? OR " + colTen + " LIKE ? LIMIT 10";
                try (Connection conn = new connect().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, "%" + input + "%");
                    ps.setString(2, "%" + input + "%");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) model.addElement(rs.getString(colMa) + " - " + rs.getString(colTen));
                    comboBox.setModel(model);
                    comboBox.getEditor().setItem(input);
                    if (model.getSize() > 0) comboBox.showPopup();
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        });
    }
    
    private void xuLyLuu() {
        try {
            String svChon = (cbSinhVien.getSelectedItem() != null) ? cbSinhVien.getSelectedItem().toString() : "";
            String sachChon = (cbSach.getSelectedItem() != null) ? cbSach.getSelectedItem().toString() : "";
            String ngayMuon = txtNgayMuon.getText().trim();
            String ngayTra = txtNgayTra.getText().trim();
            String maPM = txtMaPM.getText().trim();
            
            if (svChon.isEmpty() || sachChon.isEmpty() || ngayTra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }
            String maSV = svChon.split(" - ")[0].trim();
            String maSach = sachChon.split(" - ")[0].trim();
            String tenSach = sachChon.contains(" - ") ? sachChon.split(" - ")[1].trim() : "sách";
            int sl;
            try {
                sl = Integer.parseInt(txtSoLuong.getText().trim());
                if (sl <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng mượn phải là số nguyên dương!");
                return;
            }

            if (!LogicMuonTra.ktraNgayTra(ngayMuon, ngayTra)) {
                JOptionPane.showMessageDialog(this, "Ngày trả không hợp lệ! (Phải sau ngày mượn và không quá 30 ngày)");
                return;
            }

            int tonKho = LogicMuonTra.ktraTonKho(maSach);
            if (tonKho == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin sách trong hệ thống!");
                return;
            }
        
            if (sl > tonKho) {
                JOptionPane.showMessageDialog(this, 
                    "Số lượng trong kho của " + tenSach + " (" + tonKho + ") không đủ để cho mượn " + sl + " cuốn!",
                    "Cảnh báo thiếu hàng", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            PhieuMuonDAO dao = new PhieuMuonDAO();
            if (dao.insertPhieuMuon(maPM, maSV, maSach, sl, ngayMuon, ngayTra)) {
                isSuccess = true;
                JOptionPane.showMessageDialog(this, "Thêm phiếu mượn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu thất bại! Vui lòng kiểm tra lại kết nối Database.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    public boolean isSuccess() { return isSuccess; }
}