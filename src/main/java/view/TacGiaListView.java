package view;

import model.SessionManager;
import controller.TacGiaController;
import utils.ThemeUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.TacGia;
import gui.SinhVienGUI;
import gui.AdminGUI;  // ✅ THÊM IMPORT NÀY

public class TacGiaListView extends JFrame {

    private JPanel gridPanel;
    private JScrollPane scrollPane;

    private TacGiaController controller;

    public TacGiaListView(String title) {
        super(title);
    }

    public void doShow() {
        addControl();
        controller = new TacGiaController(this);
        controller.initView();
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Sử dụng xác nhận thoát chung
                ThemeUtils.addExitConfirmation(TacGiaListView.this);
            }
        });
        setVisible(true);
    }

    private void addControl() {
        createMainPanel();
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeUtils.BG_MAIN);
        mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Top bar with title and breadcrumb
        JButton btnBack = ThemeUtils.createSecondaryButton("← Quay lại");
        btnBack.addActionListener(e -> {
            dispose();
            String role = SessionManager.getCurrentRole();
            if ("admin".equals(role)) {
                new AdminGUI(SessionManager.getMaNguoiDung()).setVisible(true);
            } else {
                new gui.SinhVienGUI(SessionManager.getMaNguoiDung()).setVisible(true);
            }
        });

        JPanel topBar = ThemeUtils.createTopBar("DANH SÁCH TÁC GIẢ", "Trang chủ > Tác giả", btnBack);
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Content area
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
        add(mainPanel);
    }

    public JPanel createCard(TacGia tacGia) {
        // Create a rounded card panel with shadow and hover effects
        JPanel cardPanel = new JPanel(new BorderLayout(0, 8)) {
            private boolean hovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Shadow
                g2.setColor(new Color(0, 0, 0, hovered ? 35 : 20));
                g2.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 12, 12);
                // Card background
                g2.setColor(ThemeUtils.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 12, 12);
                // Top accent line on hover
                if (hovered) {
                    g2.setColor(ThemeUtils.PRIMARY);
                    g2.fillRoundRect(0, 0, getWidth() - 2, 3, 12, 12);
                    g2.fillRect(0, 3, getWidth() - 2, 2);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new Dimension(200, 280));
        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Photo at top
        JLabel lblImg = loadImage(tacGia);
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        photoPanel.setBackground(ThemeUtils.BG_CARD);
        photoPanel.add(lblImg);
        cardPanel.add(photoPanel, BorderLayout.NORTH);

        // Info at bottom
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 0, 4));
        infoPanel.setBackground(ThemeUtils.BG_CARD);

        JLabel lblTen = new JLabel(tacGia.getTen_tg(), SwingConstants.CENTER);
        lblTen.setFont(ThemeUtils.FONT_BODY_BOLD);
        lblTen.setForeground(ThemeUtils.TEXT_PRIMARY);

        JLabel lblNgaySinh = new JLabel("📅 " + tacGia.getNgay_sinh(), SwingConstants.CENTER);
        lblNgaySinh.setFont(ThemeUtils.FONT_SMALL);
        lblNgaySinh.setForeground(ThemeUtils.TEXT_SECONDARY);

        JLabel lblQue = new JLabel("📍 " + tacGia.getQue(), SwingConstants.CENTER);
        lblQue.setFont(ThemeUtils.FONT_SMALL);
        lblQue.setForeground(ThemeUtils.TEXT_SECONDARY);

        infoPanel.add(lblTen);
        infoPanel.add(lblNgaySinh);
        infoPanel.add(lblQue);

        cardPanel.add(infoPanel, BorderLayout.CENTER);

        // Hover effect
        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                cardPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardPanel.repaint();
            }
        });

        return cardPanel;
    }

    private JLabel loadImage(TacGia tacGia) {
        try {
            URL imgURL = getClass().getResource("/images/" + tacGia.getHinh());

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);

                JLabel lbl = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
                lbl.setPreferredSize(new Dimension(120, 150));
                return lbl;
            } else {
                System.out.println("Không tìm thấy file ảnh");
            }
        } catch (Exception e) {
            System.err.println("Lỗi load ảnh tác giả: " + e.getMessage());
        }

        return createPlaceholderImage();
    }

    private JLabel createPlaceholderImage() {
        JLabel lbl = new JLabel("No Image", SwingConstants.CENTER);
        lbl.setFont(ThemeUtils.FONT_SMALL);
        lbl.setForeground(ThemeUtils.TEXT_MUTED);
        lbl.setPreferredSize(new Dimension(120, 150));
        lbl.setBackground(ThemeUtils.BG_INPUT);
        lbl.setOpaque(true);
        lbl.setBorder(BorderFactory.createLineBorder(ThemeUtils.BORDER, 1));
        return lbl;
    }

    public void displayTacGia(List<TacGia> danhSach) {
        gridPanel.removeAll();

        for (TacGia tacGia : danhSach) {
            gridPanel.add(createCard(tacGia));
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
}
