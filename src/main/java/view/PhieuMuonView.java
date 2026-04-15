package view;

import com.toedter.calendar.JDateChooser;
import controller.PhieuMuonController;
import dao.SachDAO;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

import dao.UserDAO;

public class PhieuMuonView extends JFrame {

    private JTextField txtNgayMuon;
    private JTextField txtSoLuong;
    private JButton btnXacNhan;
    private PhieuMuonController controller;
    private String maSach;
    private UserDAO uDAO;
    private SachDAO sachDAO;
    JDateChooser dt_ngaytra;

    private String maSv;

    public PhieuMuonView(String maSach, String maSv, PhieuMuonController controller) {
        super("Form mượn sách");
        this.maSach = maSach;
        uDAO = new UserDAO();
        sachDAO = new SachDAO();
        this.controller = controller;
    }

    public void doShow() {
        addControl();
        addEvent();
        setSize(420, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        ThemeUtils.addExitConfirmation(this);
        setVisible(true);
    }

    private void addControl() {
        // Gradient header panel
        JPanel headerPanel = ThemeUtils.createGradientPanel(ThemeUtils.GRADIENT_PRIMARY, 70);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));
        headerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel lblHeaderTitle = new JLabel("📖 MƯỢN SÁCH", SwingConstants.CENTER);
        lblHeaderTitle.setFont(ThemeUtils.FONT_HEADING);
        lblHeaderTitle.setForeground(ThemeUtils.TEXT_WHITE);
        
        JButton btnBack = ThemeUtils.createSecondaryButton("✕");
        btnBack.setPreferredSize(new Dimension(50, 40));
        btnBack.addActionListener(e -> dispose());
        
        headerPanel.setLayout(new BorderLayout(15, 0));
        headerPanel.add(lblHeaderTitle, BorderLayout.CENTER);
        headerPanel.add(btnBack, BorderLayout.WEST);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ThemeUtils.BG_MAIN);
        formPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Số lượng
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblSoLuong = ThemeUtils.createBoldLabel("Số lượng mượn:");
        formPanel.add(lblSoLuong, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        txtSoLuong = ThemeUtils.createTextField(20);
        formPanel.add(txtSoLuong, gbc);

        // Ngày mượn
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblNgayMuon = ThemeUtils.createBoldLabel("Ngày mượn:");
        formPanel.add(lblNgayMuon, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        txtNgayMuon = ThemeUtils.createTextField(20);
        txtNgayMuon.setText(LocalDate.now().toString());
        txtNgayMuon.setEnabled(false);
        txtNgayMuon.setForeground(ThemeUtils.TEXT_MUTED);
        formPanel.add(txtNgayMuon, gbc);

        // Ngày trả
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel lblNgayTra = ThemeUtils.createBoldLabel("Ngày trả:");
        formPanel.add(lblNgayTra, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        dt_ngaytra = new JDateChooser();
        dt_ngaytra.setDateFormatString("dd/MM/yyyy");
        dt_ngaytra.setFont(ThemeUtils.FONT_BODY);
        dt_ngaytra.setPreferredSize(new Dimension(200, 38));
        formPanel.add(dt_ngaytra, gbc);

        // Nút xác nhận
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        btnXacNhan = ThemeUtils.createPrimaryButton("XÁC NHẬN MƯỢN");
        btnXacNhan.setPreferredSize(new Dimension(200, 42));
        formPanel.add(btnXacNhan, gbc);

        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeUtils.BG_MAIN);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                new EmptyBorder(0, 0, 0, 0)
        ));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

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
