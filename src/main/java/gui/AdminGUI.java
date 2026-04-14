package gui;

import model.SessionManager;
import qltv.LichSuMuonTra;
import qltv.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.UserDAO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AdminGUI extends JFrame {

    private final Color PRIMARY_BLUE = Color.decode("#005a9e");
    private final Color TEXT_BLUE = Color.decode("#005a9e");
    private final Color RED_BUTTON = Color.decode("#dc3545");
    private final Color MENU_BG_HOVER = Color.decode("#f0f0f0");

    private final Font MAIN_FONT = new Font("Arial", Font.PLAIN, 14);
    private final Font MENU_FONT = new Font("Arial", Font.BOLD, 15);
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 22);

    private String currentUsername;
    private UserDAO userDAO = new UserDAO();

    public AdminGUI(String username) {
        String tenThat = userDAO.getHoTenAdmin(username);
        this.currentUsername = tenThat;

        setTitle("Hệ thống Quản lý Thư viện - Trang chủ Admin");
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
                int choice = JOptionPane.showConfirmDialog(AdminGUI.this, "Đóng chương trình?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
        btnLogout.setForeground(Color.WHITE); // Đã sửa màu chữ thành trắng cho dễ nhìn
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(new EmptyBorder(5, 15, 5, 15));
        btnLogout.addActionListener(e -> {
            SessionManager.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });

        rightPanel.add(lblWelcome);
        rightPanel.add(btnLogout);

        panel.add(lblTitle, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createWhiteMenuBar() {
        JPanel navPanel = new JPanel(new GridLayout(1, 1));
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.setBorderPainted(false);

        menuBar.setLayout(new GridLayout(1, 4, 0, 0));

        menuBar.add(createBlueStyleMenu("Quản lý sách", new String[]{"Danh mục sách", "Thể loại", "Tác giả"}));

        menuBar.add(createBlueStyleMenu("Quản lý mượn trả", new String[]{"Quản lý phiếu mượn", "Xem lịch sử hình phạt"}));

        JMenuItem menuLichSu = createBlueStyleMenuItem("Lịch sử mượn trả sách");
        menuLichSu.addActionListener(e -> {
            this.dispose();
            new LichSuMuonTra(this.currentUsername, true).setVisible(true);
        });
        menuBar.add(menuLichSu);

        menuBar.add(createBlueStyleMenu("Quản lý tài khoản", new String[]{"Tài khoản Admin", "Tài khoản Sinh viên"}));

        navPanel.add(menuBar);
        return navPanel;
    }

    private JMenu createBlueStyleMenu(String title, String[] subItems) {
        JMenu menu = new JMenu(title);
        menu.setFont(MENU_FONT);
        menu.setForeground(TEXT_BLUE);
        menu.setBackground(Color.WHITE);
        menu.setOpaque(true);

        menu.setPreferredSize(new Dimension(250, 40));

        menu.setHorizontalAlignment(SwingConstants.CENTER);
        menu.setHorizontalTextPosition(SwingConstants.CENTER);

        for (String itemText : subItems) {
            JMenuItem item = new JMenuItem(itemText);
            item.setFont(MAIN_FONT);
            item.setBackground(Color.WHITE);
            item.setForeground(Color.BLACK);
            item.setPreferredSize(new Dimension(240, 35));

            item.setHorizontalAlignment(SwingConstants.LEFT); 
 
            item.setBorder(new EmptyBorder(0, 30, 0, 0));
            // ------------------------------------------
            item.addActionListener(e -> {
                switch (itemText) {
                    // --- NHÓM SÁCH ---
                    case "Danh mục sách":
                        this.dispose();
                        // Truyền tham số maSvDangNhap là "Admin" (hoặc biến lưu mã Admin)
                        view.SachListView sachView = new view.SachListView("Admin");
                        // Mặc định truyền rỗng mã loại để load toàn bộ sách, tránh lỗi tiêu đề Null
                        sachView.setLoaiSach("", "Tất cả");
                        sachView.doShow(); // doShow() đã bao gồm khởi tạo Controller và setVisible
                        break;

                    case "Thể loại":
                        this.dispose();
                        // Truyền tham số title vào theo yêu cầu của constructor
                        new view.LoaiSachView("Quản lý Thể loại").doShow();
                        break;

                    case "Tác giả":
                        this.dispose();
                        // Truyền tham số title vào theo yêu cầu của constructor
                        new view.TacGiaListView("Quản lý Tác giả").doShow();
                        break;

                    // --- CÁC CHỨC NĂNG KHÁC (Giữ nguyên) ---
                    case "Quản lý phiếu mượn":
                        this.dispose();
                        new QuanLyPhieuMuonGUI().setVisible(true);
                        break;
                    case "Xem lịch sử hình phạt":
                        this.dispose();
                        new QuanLyHinhPhatGUI().setVisible(true);
                        break;

                    case "Tài khoản Sinh viên":
                        this.dispose();
                        QuanLyTaiKhoanGUI guiSV = new QuanLyTaiKhoanGUI();
                        guiSV.doShow();
                        guiSV.showSinhVien();
                        break;

                    case "Tài khoản Admin":
                        this.dispose();
                        QuanLyTaiKhoanGUI guiAdmin = new QuanLyTaiKhoanGUI();
                        guiAdmin.doShow();
                        guiAdmin.showAdmin();
                        break;
                }
            });
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
        item.setHorizontalAlignment(SwingConstants.CENTER);
        item.setHorizontalTextPosition(SwingConstants.CENTER);

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

    private JPanel createBodyPanel() {
        BackgroundImagePanel body = new BackgroundImagePanel("Pictures/banner1.jpg");
        body.setLayout(new GridBagLayout());

        JPanel contentOverlay = new JPanel();
        contentOverlay.setLayout(new BoxLayout(contentOverlay, BoxLayout.Y_AXIS));
        contentOverlay.setOpaque(false);

        JLabel lblMainText = new JLabel("Kho tri thức dành cho mọi người");
        lblMainText.setFont(new Font("Arial", Font.BOLD, 36));
        lblMainText.setForeground(Color.WHITE);
        lblMainText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubText = new JLabel("Chào mừng Quản trị viên: " + currentUsername);
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

    class BackgroundImagePanel extends JPanel {

        private Image backgroundImage;

        public BackgroundImagePanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                setBackground(Color.GRAY);
            }
        }

    }
}
