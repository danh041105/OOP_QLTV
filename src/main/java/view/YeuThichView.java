/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.SessionManager;

import controller.YeuThichController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.List;
import javax.swing.*;

import model.YeuThich;
import gui.SinhVienGUI;
import dao.UserDAO;

/**
 *
 * @author ADMIN
 */
public class YeuThichView extends JFrame {

    private String maSvDangnhap;

    private JPanel mainPanel, gridPanel;
    private JScrollPane scrollPane;

    private YeuThichController controller;

    private static final Color main_color = new Color(236, 240, 241);
    private static final Color title_color = new Color(52, 73, 94);
    private static final Color border_color = new Color(189, 195, 199);

    public YeuThichView() {

    }

    public YeuThichView(String maSvDangnhap) {
        this.maSvDangnhap = maSvDangnhap;
    }

    public void doShow() {
        addControl();

        controller = new YeuThichController(this);
        controller.initView();
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserDAO dao = new UserDAO();
                new SinhVienGUI(SessionManager.getMaNguoiDung()).setVisible(true);
            }
        });
        setVisible(true);
    }

    private void addControl() {
        createMainPanel();

        add(mainPanel);
    }

    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(main_color);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Yêu Thích", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(title_color);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setBackground(main_color);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel createCard(YeuThich yeuThich) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border_color, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblimg = loadImage(yeuThich);
        cardPanel.add(lblimg, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        infoPanel.setBackground(Color.WHITE);

        JLabel lblSach = new JLabel("Tên sách: " + yeuThich.getTenSach(), SwingConstants.CENTER);
        lblSach.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSach.setForeground(Color.BLACK);
        lblSach.setPreferredSize(new Dimension(0, 25));

        JLabel lblTg = new JLabel("Tác giả: " + yeuThich.getTenTg(), SwingConstants.CENTER);
        lblTg.setFont(new Font("Arial", Font.PLAIN, 13));
        lblTg.setForeground(Color.BLACK);
        lblTg.setPreferredSize(new Dimension(0, 25));

        infoPanel.add(lblSach);
        infoPanel.add(lblTg);

        cardPanel.add(infoPanel, BorderLayout.CENTER);

        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cardPanel.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardPanel.setBackground(Color.WHITE);
            }
            
        });
        return cardPanel;
    }

    private JLabel loadImage(YeuThich yeuThich) {
        try {
            URL imgURL = getClass().getResource("/images/" + yeuThich.getImage());
            System.out.println(yeuThich.getImage());
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(120, 150, Image.SCALE_FAST);
                return new JLabel(new ImageIcon(img), SwingConstants.CENTER);
            } else {
                System.out.println("Không tìm thấy file ảnh: " + imgURL);
                return createPlaceholderImage();
            }
        } catch (Exception e) {
            System.err.println("Lỗi load ảnh tác giả: " + e.getMessage());
        }

        return createPlaceholderImage();
    }

    private JLabel createPlaceholderImage() {
        JLabel lbl = new JLabel("No Image", SwingConstants.CENTER);
        lbl.setPreferredSize(new Dimension(120, 150));
        lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return lbl;
    }

    public void displayYeuThich(List<YeuThich> list) {
        gridPanel.removeAll();

        for (YeuThich yt : list) {
            gridPanel.add(createCard(yt));
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {

        YeuThichView ytv = new YeuThichView();
        ytv.doShow();
 
    }

}
