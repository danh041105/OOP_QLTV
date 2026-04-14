package view;

import model.SessionManager;
import controller.LoaiSachController;
import utils.ThemeUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import model.LoaiSach;
import gui.SinhVienGUI;

/**
 * @author ADMIN
 */
public class LoaiSachView extends JFrame {

    private JPanel mainPanel, gridPanel;
    private JScrollPane scrollPane;

    private LoaiSachController controller;

    public LoaiSachView(String title) {
        super(title);
        controller = new LoaiSachController(this);
    }

    public void doShow() {
        addControl();
        controller.loadData();

        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeUtils.BG_MAIN);
        mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Top bar with title and breadcrumb
        JPanel topBar = ThemeUtils.createTopBar("THỂ LOẠI SÁCH", "Trang chủ > Thể loại sách");
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(ThemeUtils.BG_MAIN);
        contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        gridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        gridPanel.setBackground(ThemeUtils.BG_MAIN);

        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(ThemeUtils.BG_MAIN);
        scrollPane.getViewport().setBackground(ThemeUtils.BG_MAIN);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    public JPanel createCard(LoaiSach loaiSach) {
        // Create a rounded card panel with left blue accent border
        JPanel cardPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Shadow effect
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 12, 12);
                // Card background
                g2.setColor(ThemeUtils.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 12, 12);
                // Left accent border (4px blue)
                g2.setColor(ThemeUtils.PRIMARY);
                g2.fillRoundRect(0, 0, 4, getHeight() - 2, 4, 4);
                g2.fillRect(0, 4, 4, getHeight() - 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new Dimension(220, 120));
        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardPanel.setBorder(new EmptyBorder(15, 20, 15, 15));

        JLabel lblTen = new JLabel(loaiSach.getTenLoaiSach(), SwingConstants.CENTER);
        lblTen.setFont(ThemeUtils.FONT_SUBHEADING);
        lblTen.setForeground(ThemeUtils.TEXT_PRIMARY);
        lblTen.setHorizontalAlignment(SwingConstants.CENTER);
        lblTen.setVerticalAlignment(SwingConstants.CENTER);

        cardPanel.add(lblTen, BorderLayout.CENTER);

        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.viewBooksByCategory(loaiSach);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Re-paint with hover accent color
                cardPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardPanel.repaint();
            }
        });

        return cardPanel;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }
}
