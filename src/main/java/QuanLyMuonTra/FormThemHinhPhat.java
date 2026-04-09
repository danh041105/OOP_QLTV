/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyMuonTra;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author Hien
 */
public class FormThemHinhPhat extends JDialog {
    private JTextField txtMaSV, txtMaPM, txtTenSV, txtTinhTrang, txtLyDo, txtNgayPhat, txtHinhThuc;
    private JComboBox<String> cbTienDo;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false;

    public FormThemHinhPhat(Frame parent, Object[] data) {
        super(parent, "Xử lý hình phạt", true);
        this.setLayout(new BorderLayout());
        this.setSize(450, 700);
        this.setLocationRelativeTo(parent);

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("XỬ LÝ HÌNH PHẠT", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitle.setForeground(new Color(220, 53, 69)); 
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // 2. Vùng nhập liệu trung tâm
        JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JPanel pnInput = new JPanel(new GridLayout(8, 1, 0, 10));
        pnInput.setPreferredSize(new Dimension(350, 520));

        // Hàng 1: Mã phiếu mượn
        JPanel p1 = new JPanel(new BorderLayout(0, 5));
        p1.add(new JLabel("Mã phiếu mượn:"), BorderLayout.NORTH);
        txtMaPM = new JTextField(data[0].toString());
        txtMaPM.setEditable(false);
        txtMaPM.setBackground(new Color(240, 240, 240));
        txtMaPM.setPreferredSize(new Dimension(0, 25));
        p1.add(txtMaPM, BorderLayout.CENTER);
        pnInput.add(p1);

        // Hàng 2: Mã sinh viên
        JPanel p2 = new JPanel(new BorderLayout(0, 5));
        p2.add(new JLabel("Mã sinh viên:"), BorderLayout.NORTH);
        txtMaSV = new JTextField(data[1].toString());
        txtMaSV.setEditable(false);
        txtMaSV.setBackground(new Color(240, 240, 240));
        txtMaSV.setPreferredSize(new Dimension(0, 25));
        p2.add(txtMaSV, BorderLayout.CENTER);
        pnInput.add(p2);

        // Hàng 3: Tên sinh viên
        JPanel p3 = new JPanel(new BorderLayout(0, 5));
        p3.add(new JLabel("Tên sinh viên:"), BorderLayout.NORTH);
        txtTenSV = new JTextField(data[2].toString());
        txtTenSV.setEditable(false);
        txtTenSV.setBackground(new Color(240, 240, 240));
        txtTenSV.setPreferredSize(new Dimension(0, 25));
        p3.add(txtTenSV, BorderLayout.CENTER);
        pnInput.add(p3);

        // Hàng 4: Tình trạng
        JPanel p4 = new JPanel(new BorderLayout(0, 5));
        p4.add(new JLabel("Tình trạng phiếu:"), BorderLayout.NORTH);
        txtTinhTrang = new JTextField(data[10].toString());
        txtTinhTrang.setEditable(false);
        txtTinhTrang.setBackground(new Color(240, 240, 240));
        txtTinhTrang.setPreferredSize(new Dimension(0, 25));
        p4.add(txtTinhTrang, BorderLayout.CENTER);
        pnInput.add(p4);

        // Hàng 5: Lý do
        JPanel p5 = new JPanel(new BorderLayout(0, 5));
        p5.add(new JLabel("<html>Lý do vi phạm: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        txtLyDo = new JTextField();
        txtLyDo.setPreferredSize(new Dimension(0, 25));
        p5.add(txtLyDo, BorderLayout.CENTER);
        pnInput.add(p5);

        // Hàng 6: Ngày phạt 
        JPanel p6 = new JPanel(new BorderLayout(0, 5));
        p6.add(new JLabel("<html>Ngày phạt: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtNgayPhat = new JTextField(today);
        txtNgayPhat.setPreferredSize(new Dimension(0, 25));
        p6.add(txtNgayPhat, BorderLayout.CENTER);
        pnInput.add(p6);

        // Hàng 7: Hình thức 
        JPanel p7 = new JPanel(new BorderLayout(0, 5));
        p7.add(new JLabel("<html>Hình thức xử lý: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        txtHinhThuc = new JTextField("Vi phạm: " + data[10].toString());
        txtHinhThuc.setPreferredSize(new Dimension(0, 25));
        p7.add(txtHinhThuc, BorderLayout.CENTER);
        pnInput.add(p7);

        // Hàng 8: Tiến độ 
        JPanel p8 = new JPanel(new BorderLayout(0, 5));
        p8.add(new JLabel("<html>Tiến độ: <font color='red'>*</font></html>"), BorderLayout.NORTH);
        cbTienDo = new JComboBox<>(new String[]{"Chưa hoàn thành", "Đang xử lý", "Đã hoàn thành"});
        cbTienDo.setPreferredSize(new Dimension(0, 25));
        p8.add(cbTienDo, BorderLayout.CENTER);
        pnInput.add(p8);

        pnlCenter.add(pnInput);
        this.add(new JScrollPane(pnlCenter), BorderLayout.CENTER);

        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        btnLuu = new JButton("Lưu hình phạt");
        btnLuu.setBackground(new Color(220, 53, 69));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnLuu.setPreferredSize(new Dimension(150, 40));

        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setBackground(new Color(108, 117, 125));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnHuy.setPreferredSize(new Dimension(150, 40));

        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        this.add(pnlSouth, BorderLayout.SOUTH);

        btnLuu.addActionListener(e -> xuLyLuu());
        btnHuy.addActionListener(e -> dispose());
    }

    private void xuLyLuu() {
        String lyDo = txtLyDo.getText().trim();
        String hinhThuc = txtHinhThuc.getText().trim();

        if (lyDo.isEmpty() || hinhThuc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường bắt buộc (*)");
            return;
        }

        HinhPhatDAO hpDAO = new HinhPhatDAO();
        int result = hpDAO.insertHinhPhat(
            txtMaSV.getText(),
            txtMaPM.getText(),   
            lyDo,
            txtNgayPhat.getText(),
            hinhThuc,
            cbTienDo.getSelectedItem().toString()
        );

        if (result > 0) {
            isSuccess = true;
            JOptionPane.showMessageDialog(this, "Thêm hình phạt thành công!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu hình phạt!");
        }
    }

    public boolean isSuccess() { return isSuccess; }
}