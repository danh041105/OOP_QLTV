package view;

import controller.LoaiSachController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import model.LoaiSach;
import gui.SinhVienGUI;
import dao.UserDAO;

/**
 * @author ADMIN
 */
public class LoaiSachView extends JFrame {

    private JPanel mainPanel, gridPanel;
    private JScrollPane scrollPane;
    private BufferedImage backgroundImage;

    private LoaiSachController controller;

    // Bảng màu mới (đồng bộ với SachListView)
    private static final Color PRIMARY = new Color(41, 128, 185);
    private static final Color PRIMARY_DARK = new Color(31, 97, 141);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color CARD_HOVER = new Color(41, 128, 185);
    private static final Color TITLE_COLOR = new Color(44, 62, 80);
    private static final Color CARD_SHADOW = new Color(189, 195, 199);

    public LoaiSachView(String title) {
        super(title);
        controller = new LoaiSachController(this);
    }

    private void loadBackgroundImage() {
        try {
            String imagePath = "D:\\project_java\\src\\main\\resources\\images\\admin_banner.jpg";
            File file = new File(imagePath);
            if (file.exists()) {
                backgroundImage = ImageIO.read(file);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi tải ảnh background: " + e.getMessage());
        }
    }

    // Inner class: JPanel với background image
    class BackgroundImagePanel extends JPanel {
        public BackgroundImagePanel(LayoutManager layout) {
            super(layout);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

    public void doShow() {
        loadBackgroundImage();
        addControl();
        controller.loadData();

        setSize(900, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UserDAO dao = new UserDAO();
                new SinhVienGUI(dao.getMSV_isLogin()).setVisible(true);
            }
        });
        setVisible(true);
    }

    private void addControl() {
        createMainPanel();
        add(mainPanel);
    }

    private void createMainPanel() {
        mainPanel = new BackgroundImagePanel(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // === HEADER BANNER ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblIcon = new JLabel("\uD83D\uDCDA");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcon.setForeground(Color.WHITE);
        headerPanel.add(lblIcon, BorderLayout.WEST);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        JLabel lblTitle = new JLabel("THỂ LOẠI SÁCH", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        titleWrapper.add(lblTitle, BorderLayout.NORTH);

        JLabel lblSub = new JLabel("Chọn thể loại để xem danh sách sách", SwingConstants.LEFT);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(189, 195, 199));
        titleWrapper.add(lblSub, BorderLayout.CENTER);

        headerPanel.add(titleWrapper, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // === GRID THỂ LOẠI ===
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setOpaque(false);
        gridWrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        gridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        gridWrapper.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(gridWrapper, BorderLayout.CENTER);
    }

    // Mảng màu cho card thể loại (xen kẽ đẹp)
    private static final Color[] CARD_COLORS = {
            new Color(52, 152, 219),   // Xanh dương
            new Color(155, 89, 182),   // Tím
            new Color(46, 204, 113),   // Xanh lá
            new Color(231, 76, 60),    // Đỏ
            new Color(241, 196, 15),   // Vàng
            new Color(230, 126, 34),   // Cam
            new Color(26, 188, 156),   // Xanh ngọc
            new Color(52, 73, 94),     // Xanh sẫm
            new Color(142, 68, 173),   // Tím đậm
    };

    private int cardCount = 0;

    public JPanel createCard(LoaiSach loaiSach) {
        int colorIndex = cardCount % CARD_COLORS.length;
        Color cardColor = CARD_COLORS[colorIndex];
        cardCount++;

        // Card chính - dạng bordered panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(CARD_BG);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(3, 3, 3, 3, cardColor),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        cardPanel.setPreferredSize(new Dimension(220, 130));
        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thanh màu trên cùng của card
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(cardColor);
        topBar.setPreferredSize(new Dimension(0, 5));
        cardPanel.add(topBar, BorderLayout.NORTH);

        // Panel nội dung
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        // Icon + Tên thể loại
        JLabel lblIcon = new JLabel("\uD83D\uDDC2", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        lblIcon.setForeground(cardColor);

        JLabel lblTen = new JLabel(loaiSach.getTenLoaiSach(), SwingConstants.CENTER);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTen.setForeground(TITLE_COLOR);
        lblTen.setHorizontalAlignment(SwingConstants.CENTER);
        lblTen.setVerticalAlignment(SwingConstants.CENTER);

        // Nhãn "Nhấn để xem"
        JLabel lblHint = new JLabel("Nhấn để xem sách > ", SwingConstants.CENTER);
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblHint.setForeground(CARD_SHADOW);
        lblHint.setVisible(false);

        contentPanel.add(lblIcon, BorderLayout.NORTH);
        contentPanel.add(lblTen, BorderLayout.CENTER);
        contentPanel.add(lblHint, BorderLayout.SOUTH);

        cardPanel.add(contentPanel, BorderLayout.CENTER);

        // Hover effect
        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.viewBooksByCategory(loaiSach);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cardPanel.setBackground(new Color(240, 248, 255));
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(3, 3, 3, 3, cardColor.brighter()),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
                lblHint.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardPanel.setBackground(CARD_BG);
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(3, 3, 3, 3, cardColor),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
                lblHint.setVisible(false);
            }
        });

        return cardPanel;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }
}
