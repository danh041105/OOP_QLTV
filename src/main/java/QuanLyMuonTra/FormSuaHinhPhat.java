/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyMuonTra;

import javax.swing.*;
import java.awt.*;

public class FormSuaHinhPhat extends JDialog {
    private JTextField txtSinhVien, tLyDo, tNgay, tHinhThuc;
    private JComboBox<String> cbTienDo, cbTinhTrang;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false;
    private int maHP;

    public FormSuaHinhPhat(Frame parent, Object[] data) {
        super(parent, "Cập nhật hình phạt", true);
        this.maHP = Integer.parseInt(data[0].toString());
        
        this.setSize(450, 620); 
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("CẬP NHẬT HÌNH PHẠT", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 144, 255));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // 2. Vùng nhập liệu trung tâm
        JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        JPanel pnInput = new JPanel(new GridLayout(6, 1, 0, 15)); // 6 hàng nhập liệu
        pnInput.setPreferredSize(new Dimension(350, 420));

        // --- Hàng 1: Sinh viên (Chỉ xem) ---
        JPanel pnSV = new JPanel(new BorderLayout(0, 5));
        pnSV.add(new JLabel("Sinh viên:"), BorderLayout.NORTH);
        txtSinhVien = new JTextField(data[1] + " - " + data[2]);
        txtSinhVien.setEditable(false);
        txtSinhVien.setBackground(new Color(240, 240, 240));
        txtSinhVien.setPreferredSize(new Dimension(0, 30));
        pnSV.add(txtSinhVien, BorderLayout.CENTER);
        pnInput.add(pnSV);

        // --- Hàng 2: Tình trạng ---
        JPanel pnTinhTrang = new JPanel(new BorderLayout(0, 5));
        pnTinhTrang.add(new JLabel("Tình trạng:"), BorderLayout.NORTH);
        cbTinhTrang = new JComboBox<>(new String[]{"Đã trả", "Đang mượn", "Trả chậm", "Quá hạn trả"});
        cbTinhTrang.setSelectedItem(data[3].toString());
        cbTinhTrang.setEnabled(false);
        cbTinhTrang.setBackground(new Color(240, 240, 240));
        cbTinhTrang.setPreferredSize(new Dimension(0, 30));
        pnTinhTrang.add(cbTinhTrang, BorderLayout.CENTER);
        pnInput.add(pnTinhTrang);

        // --- Hàng 3: Lý do vi phạm ---
        JPanel pnLyDo = new JPanel(new BorderLayout(0, 5));
        pnLyDo.add(new JLabel("<html>Lý do vi phạm: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        tLyDo = new JTextField(data[4].toString());
        tLyDo.setPreferredSize(new Dimension(0, 30));
        pnLyDo.add(tLyDo, BorderLayout.CENTER);
        pnInput.add(pnLyDo);

        // --- Hàng 4: Ngày phạt ---
        JPanel pnNgayPhat = new JPanel(new BorderLayout(0, 5));
        pnNgayPhat.add(new JLabel("<html>Ngày phạt (yyyy-MM-dd): <font color='red'>*</font></html>"), BorderLayout.NORTH);
        tNgay = new JTextField(data[5].toString());
        tNgay.setPreferredSize(new Dimension(0, 30));
        pnNgayPhat.add(tNgay, BorderLayout.CENTER);
        pnInput.add(pnNgayPhat);

        // --- Hàng 5: Hình thức xử lý ---
        JPanel pnHinhThuc = new JPanel(new BorderLayout(0, 5));
        pnHinhThuc.add(new JLabel("<html>Hình thức xử lý: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        tHinhThuc = new JTextField(data[6].toString());
        tHinhThuc.setPreferredSize(new Dimension(0, 30));
        pnHinhThuc.add(tHinhThuc, BorderLayout.CENTER);
        pnInput.add(pnHinhThuc);

        // --- Hàng 6: Tiến độ ---
        JPanel pnTienDo = new JPanel(new BorderLayout(0, 5));
        pnTienDo.add(new JLabel("<html>Tiến độ thực hiện: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        cbTienDo = new JComboBox<>(new String[]{"Chưa hoàn thành", "Đã hoàn thành"});
        cbTienDo.setSelectedItem(data[7].toString());
        cbTienDo.setPreferredSize(new Dimension(0, 30));
        pnTienDo.add(cbTienDo, BorderLayout.CENTER);
        pnInput.add(pnTienDo);

        pnlCenter.add(pnInput);
        this.add(pnlCenter, BorderLayout.CENTER);

        // 3. Nút bấm
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        btnLuu = new JButton("Lưu cập nhật");
        btnLuu.setBackground(new Color(40, 167, 69));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLuu.setPreferredSize(new Dimension(160, 40));

        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setBackground(new Color(220, 53, 69));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnHuy.setPreferredSize(new Dimension(160, 40));

        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        this.add(pnlSouth, BorderLayout.SOUTH);

        // Sự kiện
        btnLuu.addActionListener(e -> {
            String lyDo = tLyDo.getText().trim();
            String hinhThuc = tHinhThuc.getText().trim();
            String ngay = tNgay.getText().trim();

            if (lyDo.isEmpty() || hinhThuc.isEmpty() || ngay.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường (*)");
                return;
            }

            HinhPhatDAO hpDAO = new HinhPhatDAO();
            boolean updated = hpDAO.updateHinhPhat(maHP, lyDo, ngay, hinhThuc, cbTienDo.getSelectedItem().toString());

            if (updated) {
                isSuccess = true;
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật!");
            }
        });
        
        btnHuy.addActionListener(e -> dispose());
    }

    public boolean isSuccess() { return isSuccess; }
}