package quanlymuontra;

import gui.QuanLyHinhPhatGUI;
import gui.QuanLyPhieuMuonGUI;
import utils.ThemeUtils;

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
        this.getContentPane().setBackground(ThemeUtils.BG_MAIN);

        // Welcome screen with gradient background
        JPanel welcomePanel = ThemeUtils.createGradientPanel(ThemeUtils.GRADIENT_PRIMARY, 800);
        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel lblWelcome = new JLabel("HỆ THỐNG QUẢN LÝ THƯ VIỆN", JLabel.CENTER);
        lblWelcome.setFont(ThemeUtils.FONT_TITLE);
        lblWelcome.setForeground(ThemeUtils.TEXT_WHITE);
        welcomePanel.add(lblWelcome, BorderLayout.CENTER);

        JLabel lblSubtitle = new JLabel("Library Management System", JLabel.CENTER);
        lblSubtitle.setFont(ThemeUtils.FONT_BODY);
        lblSubtitle.setForeground(new Color(200, 210, 230));
        welcomePanel.add(lblSubtitle, BorderLayout.SOUTH);
        ((JPanel) lblSubtitle.getParent()).setBorder(new EmptyBorder(0, 0, 20, 0));

        this.add(welcomePanel, BorderLayout.CENTER);

        setupMenuBar(this);
    }

    public static void setupMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(ThemeUtils.BG_SIDEBAR);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ThemeUtils.PRIMARY));
        menuBar.setPreferredSize(new Dimension(menuBar.getPreferredSize().width, 48));

        // Navigation panel as a flat menu approach
        JMenu menuMuonTra = new JMenu("  Quản lý mượn trả  ");
        menuMuonTra.setForeground(ThemeUtils.TEXT_WHITE);
        menuMuonTra.setFont(ThemeUtils.FONT_BODY_BOLD);
        menuMuonTra.setBackground(ThemeUtils.BG_SIDEBAR);
        menuMuonTra.setBorder(new EmptyBorder(10, 20, 10, 20));
        menuMuonTra.setBorderPainted(false);

        JMenuItem itemQLPM = new JMenuItem("  Quản lý phiếu mượn  ");
        itemQLPM.setForeground(ThemeUtils.TEXT_WHITE);
        itemQLPM.setFont(ThemeUtils.FONT_BODY);
        itemQLPM.setBackground(ThemeUtils.BG_SIDEBAR_HOVER);
        itemQLPM.setBorder(new EmptyBorder(8, 30, 8, 20));

        JMenuItem itemLSHP = new JMenuItem("  Quản lý hình phạt  ");
        itemLSHP.setForeground(ThemeUtils.TEXT_WHITE);
        itemLSHP.setFont(ThemeUtils.FONT_BODY);
        itemLSHP.setBackground(ThemeUtils.BG_SIDEBAR_HOVER);
        itemLSHP.setBorder(new EmptyBorder(8, 30, 8, 20));

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

        // Add a back button on the right side of the menu bar
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(ThemeUtils.BG_SIDEBAR);
        rightPanel.setBorder(new EmptyBorder(5, 0, 5, 15));

        JButton btnBack = ThemeUtils.createSmallButton("◀ Quay lại", ThemeUtils.BG_SIDEBAR_HOVER, ThemeUtils.TEXT_WHITE);
        btnBack.setFont(ThemeUtils.FONT_SMALL_BOLD);
        btnBack.addActionListener(e -> {
            frame.dispose();
            new QuanLyMuonTra_Main().setVisible(true);
        });
        rightPanel.add(btnBack);
        menuBar.add(rightPanel);

        frame.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        // Set system look and feel for better compatibility
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        QuanLyMuonTra_Main main = new QuanLyMuonTra_Main();
        main.setVisible(true);
    }
}
