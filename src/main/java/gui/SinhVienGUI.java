package gui;

import model.SessionManager;

import dao.UserDAO;
import qltv.LichSuMuonTra;
import qltv.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import view.LoaiSachView;
import view.TacGiaListView;
import view.YeuThichView;

public class SinhVienGUI extends JFrame {

    private final Color PRIMARY_BLUE = Color.decode("#005a9e");
    private final Color TEXT_BLUE = Color.decode("#005a9e");
    private final Color RED_BUTTON = Color.decode("#dc3545");
    private final Color MENU_BG_HOVER = Color.decode("#f0f0f0");

    private final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 14);
    private final Font MENU_FONT = new Font("Arial", Font.BOLD, 15);
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 22);

    private String currentUsername;
    private String currentID;
    private UserDAO userDAO = new UserDAO();

    public SinhVienGUI(String username) {

        String tenThat = userDAO.getHoTenSinhVien(username);
        this.currentUsername = tenThat;

        this.currentID = username;

        setTitle("Hệ thống Thư viện - Trang dành cho Sinh viên");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainHeader = new JPanel();
        mainHeader.setLayout(new BoxLayout(mainHeader, BoxLayout.Y_AXIS));

        mainHeader.add(createTopBluePanel());
        mainHeader.add(createWhiteMenuBar());

        add(mainHeader, BorderLayout.NORTH);

        add(createBodyPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(SinhVienGUI.this, "Đóng chương trình?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    SessionManager.logout();
                    System.exit(0);
                }
            }
        });

    }

    private JPanel createTopBluePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_BLUE);
        panel.setBorder(new EmptyBorder(10, 30, 10, 30));
        panel.setPreferredSize(new Dimension(1200, 60));

        JLabel lblTitle = new JLabel("Thư viện ABC");
        lblTitle.setFont(TITLE_FONT);
        lblTitle.setForeground(Color.WHITE);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        JLabel lblWelcome = new JLabel("Chào, " + currentUsername);
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setBackground(RED_BUTTON);
        btnLogout.setForeground(Color.BLUE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(new EmptyBorder(5, 15, 5, 15));
        btnLogout.addActionListener(e -> {
            SessionManager.logout();
            this.dispose();
            new LoginFrame().setVisible(true);
        });

        rightPanel.add(lblWelcome);
        rightPanel.add(btnLogout);

        panel.add(lblTitle, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createWhiteMenuBar() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10)); // FlowLayout căn trái
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Đường kẻ mờ dưới đáy

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.setBorderPainted(false);

        JMenuItem menuItemTheloai = new JMenuItem("Thể loại");
        JMenuItem menuItemTacgia = new JMenuItem("Tác giả");

        JMenu menuDanhmuc = createStyleMenu(
                "Danh mục",
                menuItemTheloai,
                menuItemTacgia);

        menuBar.add(menuDanhmuc);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenuItem menuYeuThich = createBlueStyleMenuItem("Mục yêu thích");
        menuBar.add(menuYeuThich);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenuItem menuLichsumuontra = createBlueStyleMenuItem("Lịch sử mượn trả");
        menuLichsumuontra.addActionListener(e -> {

            new LichSuMuonTra(this.currentID, false).setVisible(true);
            this.dispose();
        });

        menuBar.add(menuLichsumuontra);

        menuBar.add(Box.createHorizontalStrut(20));

        JMenuItem menuTaiKhoan = createBlueStyleMenuItem("Quản lý tài khoản");
        menuBar.add(menuTaiKhoan);

        navPanel.add(menuBar);

        menuItemTacgia.addActionListener(e -> {
            new TacGiaListView("Danh sách tác giả").doShow();
            this.dispose();
        });
        menuItemTheloai.addActionListener(e -> {
            new LoaiSachView("Thể Loại").doShow();
            this.dispose();
        });
        menuYeuThich.addActionListener(e -> {
            new YeuThichView().doShow();
            this.dispose();
        });

        menuTaiKhoan.addActionListener(e -> {

            new QuanLyTaiKhoanGUI(SessionManager.getMaNguoiDung()).doShow();
            this.dispose();
        });
        return navPanel;
    }

    private JPanel createBodyPanel() {
        BackgroundImagePanel body = new BackgroundImagePanel("/images/student_banner.jpg"); // Nhớ check đường dẫn ảnh
        body.setLayout(new GridBagLayout());

        JPanel contentOverlay = new JPanel();
        contentOverlay.setLayout(new BoxLayout(contentOverlay, BoxLayout.Y_AXIS));
        contentOverlay.setOpaque(false);

        JLabel lblMainText = new JLabel("Kho tri thức dành cho mọi người");
        lblMainText.setFont(new Font("Arial", Font.BOLD, 36));
        lblMainText.setForeground(Color.WHITE);
        lblMainText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubText = new JLabel("Chào mừng " + currentUsername);
        lblSubText.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSubText.setForeground(new Color(240, 240, 240));
        lblSubText.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentOverlay.add(lblMainText);
        contentOverlay.add(Box.createVerticalStrut(20));
        contentOverlay.add(lblSubText);

        body.add(contentOverlay);
        return body;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(PRIMARY_BLUE);
        footer.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel lblCopyright = new JLabel("@2025 Thư viện ABC | Thiết kế bởi sinh viên CNTT");
        lblCopyright.setForeground(Color.WHITE);
        footer.add(lblCopyright);
        return footer;
    }

    private JMenu createBlueStyleMenu(String title, String[] subItems) {
        JMenu menu = new JMenu(title);
        menu.setFont(MENU_FONT);
        menu.setForeground(TEXT_BLUE);
        menu.setBackground(Color.WHITE);
        menu.setOpaque(true);

        for (String itemText : subItems) {
            JMenuItem item = new JMenuItem(itemText);
            item.setFont(MAIN_FONT);
            item.setBackground(Color.WHITE);
            item.setForeground(Color.BLACK);
            item.setPreferredSize(new Dimension(150, 35));
            menu.add(item);
        }
        return menu;
    }

    private JMenu createStyleMenu(String title, JMenuItem... items) {
        JMenu menu = new JMenu(title);
        menu.setFont(MENU_FONT);
        menu.setForeground(TEXT_BLUE);
        menu.setBackground(Color.WHITE);
        menu.setOpaque(true);

        for (JMenuItem item : items) {
            item.setFont(MAIN_FONT);
            item.setBackground(Color.WHITE);
            item.setForeground(Color.BLACK);
            item.setPreferredSize(new Dimension(150, 35));
            menu.add(item);
        }
        return menu;
    }

    private JMenuItem createBlueStyleMenuItem(String title) {
        JMenuItem item = new JMenuItem(title);
        item.setFont(MENU_FONT);
        item.setForeground(TEXT_BLUE);
        item.setBackground(Color.WHITE);
        item.setOpaque(true);
        item.setBorder(new EmptyBorder(5, 10, 5, 10));

        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                item.setBackground(MENU_BG_HOVER);
            }

            public void mouseExited(MouseEvent evt) {
                item.setBackground(Color.WHITE);
            }
        });
        return item;
    }

    class BackgroundImagePanel extends JPanel {

        private Image backgroundImage;

        public BackgroundImagePanel(String imagePath) {
            try {
                java.net.URL imgURL = getClass().getResource(imagePath);
                if (imgURL != null) {
                    backgroundImage = ImageIO.read(imgURL);
                } else {
                    setBackground(Color.GRAY);
                }
            } catch (IOException e) {
                setBackground(Color.GRAY);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }

}
