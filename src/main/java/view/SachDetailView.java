/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.PhieuMuonController;
import dao.PhieuMuonDAO;
import dao.YeuThichDAO;
import model.Sach;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import model.YeuThich;
import qltv.userDAO;

public class SachDetailView extends JFrame {

    private Sach sach;
    private boolean checkFavorite;

    JButton btnYeuThich, btnMuon;
    YeuThichDAO dao = new YeuThichDAO();
    PhieuMuonDAO pmdao = new PhieuMuonDAO();
    userDAO uDAO = new userDAO();
    PhieuMuonController controller;
    
    private String maSvDangNhap;

    public SachDetailView(Sach sach, String maSvDangNhap) {
        super("Chi tiết sách");
        controller = new PhieuMuonController(pmdao);
        this.sach = sach;
        this.maSvDangNhap = uDAO.getMSV_isLogin();
    }

    public void doShow() {
        addControl();
        addEvent();
        checkFavorite();
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addControl() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        //Tiêu đề
        JLabel lblTitle = new JLabel(sach.getTenSach());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblTitle);

        mainPanel.add(Box.createVerticalStrut(10));

        //Thông tin + ảnh
        JPanel infoPanel = new JPanel(new BorderLayout(15, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Ảnh
        JLabel lblImage = loadImage();
        infoPanel.add(lblImage, BorderLayout.WEST);

        // chữ
        JPanel textInfo = new JPanel();
        textInfo.setLayout(new BoxLayout(textInfo, BoxLayout.Y_AXIS));
        textInfo.setBackground(Color.WHITE);

        textInfo.add(createInfoLabel("Tác giả: ", sach.getTenTacGia()));
        textInfo.add(createInfoLabel("Nhà xuất bản: ", sach.getNhaXb()));
        textInfo.add(createInfoLabel("Năm xuất bản: ", String.valueOf(sach.getNamXb())));
        textInfo.add(createInfoLabel("Số lượng: ", String.valueOf(sach.getSoLuong())));
        textInfo.add(createInfoLabel("Tình trạng: ", sach.getTinhTrangText()));

        infoPanel.add(textInfo, BorderLayout.CENTER);
        mainPanel.add(infoPanel);

        mainPanel.add(Box.createVerticalStrut(15));

        // mô tả sách
        JTextArea txtMoTa = new JTextArea(sach.getMoTa());
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setEditable(false);
        txtMoTa.setRows(4);
        txtMoTa.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(txtMoTa);
        scroll.setBorder(BorderFactory.createTitledBorder("Mô tả sách"));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(scroll);
        // nút 
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnPanel.setBackground(Color.WHITE);

        btnYeuThich = new JButton("Yêu thích");
        btnYeuThich.setFont(new Font("Arial", Font.BOLD, 12));
        btnYeuThich.setBackground(new Color(255, 182, 193));
        btnYeuThich.setForeground(Color.WHITE);

        btnMuon = new JButton("Mượn sách");
        btnMuon.setFont(new Font("Arial", Font.BOLD, 12));
        btnMuon.setBackground(new Color(173, 216, 230));
        btnMuon.setForeground(Color.WHITE);

        btnPanel.add(btnYeuThich);
        btnPanel.add(btnMuon);
        btnPanel.setAlignmentX(LEFT_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnPanel);

        this.add(mainPanel);
    }

    private JLabel loadImage() {
        try {

            String path = "images/" + sach.getImage();
            URL imgURL = getClass().getClassLoader().getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);

                return new JLabel(new ImageIcon(img));
            } else {
                System.out.println("Khong tim thay file: " + path);
            }
        } catch (Exception e) {
            System.out.println("Loi xu li anh: " + e.getMessage());
        }

        JLabel lbl = new JLabel("No Image", SwingConstants.CENTER);
        lbl.setPreferredSize(new Dimension(200, 280));
        lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return lbl;
    }

    private JPanel createInfoLabel(String title, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
        panel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel lblValue = new JLabel(value != null ? value : "N/A");
        lblValue.setFont(new Font("Arial", Font.PLAIN, 13));

        panel.add(lblTitle);
        panel.add(lblValue);

        return panel;
    }

    private void checkFavorite() {
        checkFavorite = dao.checkExists(maSvDangNhap, sach.getMaSach());
        if (sach.getSoLuong() <= 0) {
                    btnMuon.setEnabled(false);
                    btnMuon.setText("Hết sách");
                    btnMuon.setBackground(new Color(240, 240, 240));
                }
        updateFavorite();
    }

    private void updateFavorite() {
        if (checkFavorite) {
            btnYeuThich.setText("Đã yêu thích");
            btnYeuThich.setBackground(new Color(255, 105, 180));
        } else {
            btnYeuThich.setText("Yêu thích");
            btnYeuThich.setBackground(new Color(255, 182, 193));
        }
    }
    
    private void addEvent() {
        btnYeuThich.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (checkFavorite) {
                    dao.delete(maSvDangNhap, sach.getMaSach());
                    checkFavorite = false;
                } else {

                    dao.insert(maSvDangNhap, sach.getMaSach());
                    checkFavorite = true;
                }
                updateFavorite();
            }
        });
        btnMuon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    
                PhieuMuonView pmv = new PhieuMuonView(sach.getMaSach(), maSvDangNhap, controller);
                pmv.doShow();
            }
        });
    }

}
