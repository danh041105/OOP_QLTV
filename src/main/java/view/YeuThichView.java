package view;

import model.SessionManager;
import controller.YeuThichController;
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
import model.YeuThich;
import gui.SinhVienGUI;

/**
 * @author ADMIN
 */
public class YeuThichView extends JFrame {

    private String maSvDangnhap;

    private JPanel mainPanel, gridPanel;
    private JScrollPane scrollPane;

    private YeuThichController controller;

    public YeuThichView() {
    }

    public YeuThichView(String maSvDangnhap) {
        this.maSvDangnhap = maSvDangnhap;
    }

    public void doShow() {
        addControl();
        controller = new YeuThichController(this);
        controller.initView();
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        // Top bar with title and heart icon
        JPanel topBar = ThemeUtils.createTopBar("❤ SÁCH YÊU THÍCH", "Trang chủ > Sách yêu thích");
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
    }

    public JPanel createCard(YeuThich yeuThich) {
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
                    g2.setColor(new Color(244, 63, 94)); // ACCENT pink
                    g2.fillRoundRect(0, 0, getWidth() - 2, 3, 12, 12);
                    g2.fillRect(0, 3, getWidth() - 2, 2);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Book image at top
        JLabel lblimg = loadImage(yeuThich);
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        photoPanel.setBackground(ThemeUtils.BG_CARD);
        photoPanel.add(lblimg);
        cardPanel.add(photoPanel, BorderLayout.NORTH);

        // Info at bottom
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        infoPanel.setBackground(ThemeUtils.BG_CARD);

        JLabel lblSach = new JLabel(yeuThich.getTenSach(), SwingConstants.CENTER);
        lblSach.setFont(ThemeUtils.FONT_BODY_BOLD);
        lblSach.setForeground(ThemeUtils.TEXT_PRIMARY);

        JLabel lblTg = new JLabel("✍ " + yeuThich.getTenTg(), SwingConstants.CENTER);
        lblTg.setFont(ThemeUtils.FONT_SMALL);
        lblTg.setForeground(ThemeUtils.TEXT_SECONDARY);

        infoPanel.add(lblSach);
        infoPanel.add(lblTg);

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

    private JLabel loadImage(YeuThich yeuThich) {
        try {
            URL imgURL = getClass().getResource("/images/" + yeuThich.getImage());
            System.out.println(yeuThich.getImage());
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(120, 150, Image.SCALE_SMOOTH);

                JLabel lbl = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
                lbl.setPreferredSize(new Dimension(120, 150));
                return lbl;
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
        lbl.setFont(ThemeUtils.FONT_SMALL);
        lbl.setForeground(ThemeUtils.TEXT_MUTED);
        lbl.setPreferredSize(new Dimension(120, 150));
        lbl.setBackground(ThemeUtils.BG_INPUT);
        lbl.setOpaque(true);
        lbl.setBorder(BorderFactory.createLineBorder(ThemeUtils.BORDER, 1));
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
}
