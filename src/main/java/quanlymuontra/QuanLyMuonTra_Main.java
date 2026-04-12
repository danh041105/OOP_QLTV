/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlymuontra;

import gui.QuanLyHinhPhatGUI;
import gui.QuanLyPhieuMuonGUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class QuanLyMuonTra_Main extends JFrame {
    private JPanel pnlMainContent;
    private QuanLyPhieuMuonGUI pnlQLPM;

    public QuanLyMuonTra_Main() {
        this.setTitle("QUẢN LÝ THƯ VIỆN");
        this.setSize(1400, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        JLabel lblWelcome = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
        this.add(lblWelcome, BorderLayout.CENTER);

        setupMenuBar(this);
    }
    
    public static void setupMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        
        menuBar.setBackground(new Color(41, 128, 185));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE)); // Đường kẻ mỏng bên dưới
        
        JMenu menuMuonTra = new JMenu("Quản lý mượn trả");
        menuMuonTra.setForeground(Color.WHITE); // Chữ trắng
        menuMuonTra.setFont(new Font("Segoe UI", Font.BOLD, 14));

        menuMuonTra.setBorder(new EmptyBorder(10, 20, 10, 20));

        JMenuItem itemQLPM = new JMenuItem("Quản lý phiếu mượn");
        JMenuItem itemLSHP = new JMenuItem("Quản lý hình phạt");

        itemQLPM.addActionListener(e -> {
            if (!(frame instanceof QuanLyPhieuMuonGUI)) { 
                new QuanLyPhieuMuonGUI().setVisible(true);
                frame.dispose(); 
            }
        });

        itemLSHP.addActionListener(e -> {
            if (!(frame instanceof QuanLyHinhPhatGUI)) {
                new QuanLyHinhPhatGUI(frame).setVisible(true);
                frame.dispose(); 
            }
        });

        menuMuonTra.add(itemQLPM);
        menuMuonTra.addSeparator();
        menuMuonTra.add(itemLSHP);
        menuBar.add(menuMuonTra);

        frame.setJMenuBar(menuBar); 
    }

    public static void main(String[] args) {
        QuanLyMuonTra_Main main = new QuanLyMuonTra_Main();
        main.setVisible(true);
    }
}
