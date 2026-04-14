package view;

import controller.TacGiaController;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.swing.*;
import javax.imageio.ImageIO;
import model.TacGia;
import gui.SinhVienGUI;
import dao.UserDAO;

public class TacGiaListView extends JFrame {

    private JPanel gridPanel;
    private JScrollPane scrollPane;
    private Image backgroundImage;

    private TacGiaController controller;

    // Bảng màu mới (đồng bộ với các view khác)
    private static final Color PRIMARY = new Color(41, 128, 185);
    private static final Color PRIMARY_LIGHT = new Color(174, 214, 241);
    private static final Color TITLE_COLOR = new Color(44, 62, 80);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color CARD_HOVER_BG = new Color(240, 248, 255);
    private static final Color TEXT_SECONDARY = new Color(127, 140, 141);
    private static final Color SHADOW_COLOR = new Color(189, 195, 199);

    // Màu viền card xen kẽ
    private static final Color[] CARD_ACCENTS = {
            new Color(52, 152, 219),
            new Color(155, 89, 182),
            new Color(46, 204, 113),
            new Color(231, 76, 60),
            new Color(241, 196, 15),
            new Color(230, 126, 34),
            new Color(26, 188, 156),
            new Color(52, 73, 94),
            new Color(142, 68, 173),
    };

    private int cardCount = 0;

    public TacGiaListView(String title) {
        super(title);
    }

    private void loadBackgroundImage() {
        try {
            String imagePath = "images/tacgia_background.jpg";
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
        controller = new TacGiaController(this);
        controller.initView();
        setSize(900, 650);
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
    }

    private void createMainPanel() {
        JPanel mainPanel = new BackgroundImagePanel(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // === HEADER BANNER ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblIcon = new JLabel("\u270F\uFE0F");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcon.setForeground(Color.WHITE);
        headerPanel.add(lblIcon, BorderLayout.WEST);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        JLabel lblTitle = new JLabel("TÁC GIẢ", SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        titleWrapper.add(lblTitle, BorderLayout.NORTH);

        JLabel lblSub = new JLabel("Danh sách tác giả trong thư viện", SwingConstants.LEFT);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(189, 195, 199));
        titleWrapper.add(lblSub, BorderLayout.CENTER);

        headerPanel.add(titleWrapper, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // === GRID TÁC GIẢ ===
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setOpaque(false);
        gridWrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        gridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        gridPanel.setOpaque(false);

        scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setPreferredSize(new Dimension(850, 600));

        gridWrapper.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(gridWrapper, BorderLayout.CENTER);

        add(mainPanel);
    }

    public JPanel createCard(TacGia tacGia) {
        int colorIndex = cardCount % CARD_ACCENTS.length;
        Color accentColor = CARD_ACCENTS[colorIndex];
        cardCount++;

        // Card chính
        JPanel cardPanel = new JPanel(new BorderLayout(0, 0));
        cardPanel.setBackground(CARD_BG);
        cardPanel.setPreferredSize(new Dimension(220, 320));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 2, 2, 2, accentColor),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thanh accent trên cùng
        JPanel topAccent = new JPanel(new BorderLayout());
        topAccent.setBackground(accentColor);
        topAccent.setPreferredSize(new Dimension(0, 4));
        cardPanel.add(topAccent, BorderLayout.NORTH);

        // Panel ảnh tác giả
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(CARD_BG);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));

        Component imgComponent = loadImage(tacGia);
        imagePanel.add(imgComponent, BorderLayout.CENTER);
        cardPanel.add(imagePanel, BorderLayout.CENTER);

        // Panel thông tin tác giả
        JPanel infoPanel = new JPanel(new BorderLayout(0, 3));
        infoPanel.setBackground(CARD_BG);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10));

        // Tên tác giả
        JLabel lblTen = new JLabel(tacGia.getTenTg(), SwingConstants.CENTER);
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTen.setForeground(TITLE_COLOR);
        infoPanel.add(lblTen, BorderLayout.NORTH);

        // Thông tin chi tiết
        JPanel detailPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        detailPanel.setOpaque(false);

        JLabel lblNgaySinh = new JLabel("Ngày sinh: " + tacGia.getNgaySinh(), SwingConstants.CENTER);
        lblNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblNgaySinh.setForeground(TEXT_SECONDARY);

        JLabel lblQue = new JLabel("Quê quán: " + tacGia.getQue(), SwingConstants.CENTER);
        lblQue.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblQue.setForeground(TEXT_SECONDARY);

        detailPanel.add(lblNgaySinh);
        detailPanel.add(lblQue);
        infoPanel.add(detailPanel, BorderLayout.CENTER);

        // Thanh divider nhỏ
        JPanel divider = new JPanel();
        divider.setBackground(accentColor);
        divider.setPreferredSize(new Dimension(60, 2));
        JPanel dividerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dividerWrapper.setOpaque(false);
        dividerWrapper.add(divider);
        infoPanel.add(dividerWrapper, BorderLayout.SOUTH);

        cardPanel.add(infoPanel, BorderLayout.SOUTH);

        // Hover effect
        cardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cardPanel.setBackground(CARD_HOVER_BG);
                imagePanel.setBackground(CARD_HOVER_BG);
                infoPanel.setBackground(CARD_HOVER_BG);
                topAccent.setBackground(accentColor.brighter());
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(2, 2, 2, 2, accentColor.brighter()),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cardPanel.setBackground(CARD_BG);
                imagePanel.setBackground(CARD_BG);
                infoPanel.setBackground(CARD_BG);
                topAccent.setBackground(accentColor);
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(2, 2, 2, 2, accentColor),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
                ));
            }
        });

        return cardPanel;
    }

    private Component loadImage(TacGia tacGia) {
        try {
            URL imgURL = getClass().getResource("/images/" + tacGia.getHinh());
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(140, 170, Image.SCALE_SMOOTH);
                JLabel lbl = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
                lbl.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
                return lbl;
            } else {
                System.out.println("Không tìm thấy file ảnh: " + tacGia.getHinh());
            }
        } catch (Exception e) {
            System.err.println("Lỗi load ảnh tác giả: " + e.getMessage());
        }
        return createPlaceholderImage();
    }

    private Component createPlaceholderImage() {
        // Placeholder đẹp hơn: icon người + chữ
        JPanel placeholder = new JPanel(new BorderLayout());
        placeholder.setBackground(new Color(245, 247, 250));
        placeholder.setPreferredSize(new Dimension(140, 170));
        placeholder.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

        JLabel lblIcon = new JLabel("\uD83D\uDC64", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setForeground(new Color(189, 195, 199));

        JLabel lblText = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblText.setForeground(new Color(189, 195, 199));

        placeholder.add(lblIcon, BorderLayout.CENTER);
        placeholder.add(lblText, BorderLayout.SOUTH);

        return placeholder;
    }

    public void displayTacGia(List<TacGia> danhSach) {
        gridPanel.removeAll();
        cardCount = 0; // Reset màu mỗi lần load lại

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
