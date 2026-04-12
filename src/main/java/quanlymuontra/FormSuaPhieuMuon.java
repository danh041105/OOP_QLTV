package quanlymuontra;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import connect.Connect;
import dao.PhieuMuonDAO;

public class FormSuaPhieuMuon extends JDialog {
    private JTextField txtSinhVien, txtSoLuong, txtNgayMuon, txtNgayTra;
    private JComboBox<String> cbSach, cbTinhTrang;
    private JButton btnLuu, btnHuy;
    private boolean isSuccess = false;
    private String maPM;
    private String maSachCu;

    public FormSuaPhieuMuon(Frame parent, Object[] data) {
        super(parent, "Cập nhật phiếu mượn", true);
        this.maPM = data[0].toString();
        this.maSachCu = data[3].toString(); 
        
        this.setSize(450, 620); // Chiều cao đồng bộ với Form Thêm
        this.setLocationRelativeTo(parent);
        this.setLayout(new BorderLayout());

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("CẬP NHẬT PHIẾU MƯỢN", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setForeground(new Color(30, 144, 255));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        this.add(lblTitle, BorderLayout.NORTH);

        // 2. Vùng nhập liệu trung tâm
        JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        JPanel pnInput = new JPanel(new GridLayout(6, 1, 0, 15));
        pnInput.setPreferredSize(new Dimension(350, 420));

        // --- Hàng 1: Sinh viên (Chỉ xem) ---
        JPanel p1 = new JPanel(new BorderLayout(0, 5));
        p1.add(new JLabel("Sinh viên:"), BorderLayout.NORTH);
        txtSinhVien = new JTextField(data[1] + " - " + data[2]);
        txtSinhVien.setEditable(false);
        txtSinhVien.setBackground(new Color(240, 240, 240));
        txtSinhVien.setPreferredSize(new Dimension(0, 30));
        p1.add(txtSinhVien, BorderLayout.CENTER);
        pnInput.add(p1);

        // --- Hàng 2: Sách mượn ---
        JPanel p2 = new JPanel(new BorderLayout(0, 5));
        p2.add(new JLabel("Sách mượn:"), BorderLayout.NORTH);
        cbSach = new JComboBox<>();
        cbSach.setPreferredSize(new Dimension(0, 30));
        loadAllBooksToCombo();
        setSelectedSach(this.maSachCu);
        p2.add(cbSach, BorderLayout.CENTER);
        pnInput.add(p2);

        // --- Hàng 3: Số lượng ---
        JPanel p3 = new JPanel(new BorderLayout(0, 5));
        p3.add(new JLabel("Số lượng mượn:"), BorderLayout.NORTH);
        txtSoLuong = new JTextField(data[7].toString());
        txtSoLuong.setPreferredSize(new Dimension(0, 30));
        p3.add(txtSoLuong, BorderLayout.CENTER);
        pnInput.add(p3);

        // --- Hàng 4: Ngày mượn (Chỉ xem) ---
        JPanel p4 = new JPanel(new BorderLayout(0, 5));
        p4.add(new JLabel("Ngày mượn:"), BorderLayout.NORTH);
        txtNgayMuon = new JTextField(data[8].toString());
        txtNgayMuon.setEditable(false);
        txtNgayMuon.setBackground(new Color(240, 240, 240));
        txtNgayMuon.setPreferredSize(new Dimension(0, 30));
        p4.add(txtNgayMuon, BorderLayout.CENTER);
        pnInput.add(p4);

        // --- Hàng 5: Ngày trả ---
        JPanel p5 = new JPanel(new BorderLayout(0, 5));
        p5.add(new JLabel("Ngày trả (yyyy-MM-dd):"), BorderLayout.NORTH);
        txtNgayTra = new JTextField(data[9].toString());
        txtNgayTra.setPreferredSize(new Dimension(0, 30));
        p5.add(txtNgayTra, BorderLayout.CENTER);
        pnInput.add(p5);

        // --- Hàng 6: Tình trạng ---
        JPanel p6 = new JPanel(new BorderLayout(0, 5));
        p6.add(new JLabel("Tình trạng phiếu:"), BorderLayout.NORTH);
        cbTinhTrang = new JComboBox<>(new String[]{
            "Đang mượn", "Đã trả", "Trả chậm", "Quá hạn trả"
        });
        cbTinhTrang.setSelectedItem(data[10].toString());
        cbTinhTrang.setPreferredSize(new Dimension(0, 30));
        p6.add(cbTinhTrang, BorderLayout.CENTER);
        pnInput.add(p6);

        pnlCenter.add(pnInput);
        this.add(pnlCenter, BorderLayout.CENTER);

        // 3. Nút bấm (Đặt cùng một hàng)
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        btnLuu = new JButton("Lưu cập nhật");
        btnLuu.setBackground(new Color(40, 167, 69)); // Xanh lá
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLuu.setPreferredSize(new Dimension(160, 40));

        btnHuy = new JButton("Hủy bỏ");
        btnHuy.setBackground(new Color(220, 53, 69)); // Đỏ
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnHuy.setPreferredSize(new Dimension(160, 40));

        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        this.add(pnlSouth, BorderLayout.SOUTH);

        // Sự kiện
        btnLuu.addActionListener(e -> xuLyCapNhat());
        btnHuy.addActionListener(e -> dispose());
    }

    // --- Các hàm hỗ trợ và Logic ---
    private void loadAllBooksToCombo() {
        try (Connection conn = new Connect().getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT ma_sach, ten_sach FROM sach")) {
            while (rs.next()) {
                cbSach.addItem(rs.getString("ma_sach") + " - " + rs.getString("ten_sach"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void setSelectedSach(String maSach) {
        for (int i = 0; i < cbSach.getItemCount(); i++) {
            if (cbSach.getItemAt(i).startsWith(maSach)) {
                cbSach.setSelectedIndex(i);
                break;
            }
        }
    }

    private void xuLyCapNhat() {
        try {
            String tinhTrangMoi = cbTinhTrang.getSelectedItem().toString();
            String sachChon = cbSach.getSelectedItem().toString();
            String maSachMoi = sachChon.split(" - ")[0];
            int sl = Integer.parseInt(txtSoLuong.getText());
            String ngayT = txtNgayTra.getText();

            PhieuMuonDAO dao = new PhieuMuonDAO();
            boolean res = dao.updatePhieuMuon(maPM, maSachMoi, sl, ngayT, tinhTrangMoi);

            if (res) {
                isSuccess = true;
                JOptionPane.showMessageDialog(this, "Cập nhật phiếu mượn thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật dữ liệu!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
    
    public boolean isSuccess() { return isSuccess; }
}