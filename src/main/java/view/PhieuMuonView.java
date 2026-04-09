package view;

import com.toedter.calendar.JDateChooser;
import controller.PhieuMuonController;
import dao.PhieuMuonDAO;
import dao.SachDAO;
import model.PhieuMuon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import qltv.userDAO;

public class PhieuMuonView extends JFrame {

    private JTextField txtNgayMuon;
    private JTextField txtSoLuong;
    private JButton btnXacNhan;
    private PhieuMuonController controller;
    private String maSach;
    private userDAO uDAO;
    private SachDAO sachDAO;
    JDateChooser dt_ngaytra;

    private String maSv;

    public PhieuMuonView(String maSach, String maSv, PhieuMuonController controller) {
        super("Form mượn sách");
        this.maSach = maSach;
        uDAO = new userDAO();
        sachDAO = new SachDAO();
        this.controller = controller;
    }

    public void doShow() {
        addControl();
        addEvent();
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void addControl() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Số lượng
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Số lượng mượn:"), gbc);
        gbc.gridx = 1;
        txtSoLuong = new JTextField(20);
        mainPanel.add(txtSoLuong, gbc);
        // Ngày mượn
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Ngày mượn (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        txtNgayMuon = new JTextField(20);
        txtNgayMuon.setText(LocalDate.now().toString());
        mainPanel.add(txtNgayMuon, gbc);
        // Ngày trả
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Ngày trả (yyyy-MM-dd):"), gbc);
        dt_ngaytra = new JDateChooser();
        dt_ngaytra.setDateFormatString("dd/MM/yyyy");
        gbc.gridx = 1;

        mainPanel.add(dt_ngaytra, gbc);
        // nút xác nhận
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        btnXacNhan = new JButton("Xác nhận mượn");
        mainPanel.add(btnXacNhan, gbc);

        this.add(mainPanel);
    }

    private void confirmMuon() {
        try {
            String soLuongStr = txtSoLuong.getText().trim();
            String ngayMuonStr = txtNgayMuon.getText().trim();
            Date ngayTra = dt_ngaytra.getDate();

            boolean success = controller.addPhieuMuon(maSach, soLuongStr, ngayMuonStr, ngayTra);

            if (success) {
                JOptionPane.showMessageDialog(this, "Mượn sách thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Mượn sách thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ: " + e.getMessage());
        }
    }

    private void addEvent() {
        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmMuon();
            }
        });
    }

}
