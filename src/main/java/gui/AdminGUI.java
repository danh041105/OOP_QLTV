package gui;

import model.SessionManager;
import qltv.LichSuMuonTra;
import qltv.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;

import dao.UserDAO;

import utils.ThemeUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminGUI extends JFrame {

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

        // Left sidebar
        add(createSidebar(), BorderLayout.WEST);

        // Right main content
        add(createMainContent(), BorderLayout.CENTER);

        // Window close behavior
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(AdminGUI.this,
                        "Đóng chương trình?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    SessionManager.logout();
                    System.exit(0);
                }
            }
        });
    }

    // ===== SIDEBAR =====

    private JPanel createSidebar() {
        JPanel sidebar = ThemeUtils.createSidebar(250);

        // Logo header
        sidebar.add(ThemeUtils.createSidebarHeader("THƯ VIỆN", "PTIT"));

        // Vertical spacer
        sidebar.add(Box.createVerticalStrut(10));

        // Navigation menu items
        sidebar.add(createSidebarButton("QUẢN LÝ SÁCH", "Thể loại sách", () -> {
            dispose();
            new view.LoaiSachView("Quản lý Thể loại").doShow();
        }));

        sidebar.add(createSidebarButton("DANH MỤC SÁCH", "Danh sách sách", () -> {
            dispose();
            view.SachListView sachView = new view.SachListView("Admin");
            sachView.setLoaiSach("", "Tất cả");
            sachView.doShow();
        }));

        sidebar.add(createSidebarButton("TÁC GIẢ", "Quản lý tác giả", () -> {
            dispose();
            new view.TacGiaListView("Quản lý Tác giả").doShow();
        }));

        sidebar.add(createSidebarButton("PHIẾU MƯỢN", "Quản lý phiếu mượn", () -> {
            dispose();
            new QuanLyPhieuMuonGUI().setVisible(true);
        }));

        sidebar.add(createSidebarButton("HÌNH PHẠT", "Lịch sử hình phạt", () -> {
            dispose();
            new QuanLyHinhPhatGUI().setVisible(true);
        }));

        sidebar.add(createSidebarButton("LỊCH SỬ MƯỢN TRẢ", "Xem lịch sử mượn trả", () -> {
            dispose();
            new LichSuMuonTra(currentUsername, true).setVisible(true);
        }));

        // Separator
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(ThemeUtils.createSidebarSeparator());
        sidebar.add(Box.createVerticalStrut(5));

        // Account management
        sidebar.add(createSidebarButton("TÀI KHOẢN ADMIN", "Quản lý tài khoản admin", () -> {
            dispose();
            QuanLyTaiKhoanGUI guiAdmin = new QuanLyTaiKhoanGUI();
            guiAdmin.doShow();
            guiAdmin.showAdmin();
        }));

        sidebar.add(createSidebarButton("TÀI KHOẢN SINH VIÊN", "Quản lý tài khoản sinh viên", () -> {
            dispose();
            QuanLyTaiKhoanGUI guiSV = new QuanLyTaiKhoanGUI();
            guiSV.doShow();
            guiSV.showSinhVien();
        }));

        // Push everything at the bottom (user info + logout)
        sidebar.add(Box.createVerticalGlue());

        // User info panel
        sidebar.add(ThemeUtils.createSidebarUser(currentUsername, "Quản trị viên"));

        // Logout button (danger style)
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(createLogoutButton());

        // Bottom padding
        sidebar.add(Box.createVerticalStrut(10));

        return sidebar;
    }

    private JButton createSidebarButton(String text, String tooltip, Runnable action) {
        JButton btn = ThemeUtils.createSidebarButton(text, false);
        btn.setToolTipText(tooltip);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ThemeUtils.BG_SIDEBAR_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(ThemeUtils.BG_SIDEBAR);
            }
        });
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private JButton createLogoutButton() {
        JButton btn = ThemeUtils.createSidebarButton("ĐĂNG XUẤT", false);
        btn.setForeground(ThemeUtils.DANGER);
        btn.setToolTipText("Đăng xuất khỏi hệ thống");
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ThemeUtils.BG_SIDEBAR_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(ThemeUtils.BG_SIDEBAR);
            }
        });
        btn.addActionListener(e -> {
            SessionManager.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });
        return btn;
    }

    // ===== MAIN CONTENT =====

    private JPanel createMainContent() {
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(ThemeUtils.BG_MAIN);

        // Top bar with breadcrumb
        mainContent.add(createTopBar(), BorderLayout.NORTH);

        // Body with banner
        mainContent.add(createBodyPanel(), BorderLayout.CENTER);

        return mainContent;
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(ThemeUtils.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeUtils.BORDER),
                new EmptyBorder(15, 25, 15, 25)
        ));

        JLabel lblTitle = new JLabel("Trang chủ Admin");
        lblTitle.setFont(ThemeUtils.FONT_HEADING);
        lblTitle.setForeground(ThemeUtils.TEXT_PRIMARY);

        JLabel lblBreadcrumb = new JLabel("Thư viện PTIT  /  Trang chủ");
        lblBreadcrumb.setFont(ThemeUtils.FONT_SMALL);
        lblBreadcrumb.setForeground(ThemeUtils.TEXT_MUTED);

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.add(lblTitle, BorderLayout.NORTH);
        left.add(lblBreadcrumb, BorderLayout.SOUTH);

        topBar.add(left, BorderLayout.WEST);

        // Current date/time on the right
        JLabel lblDate = new JLabel(java.time.LocalDate.now().toString());
        lblDate.setFont(ThemeUtils.FONT_SMALL);
        lblDate.setForeground(ThemeUtils.TEXT_SECONDARY);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(lblDate);

        topBar.add(right, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createBodyPanel() {
        BackgroundImagePanel body = new BackgroundImagePanel("/images/admin_banner.jpg");
        body.setLayout(new GridBagLayout());

        JPanel contentOverlay = new JPanel();
        contentOverlay.setLayout(new BoxLayout(contentOverlay, BoxLayout.Y_AXIS));
        contentOverlay.setOpaque(false);

        JLabel lblMainText = new JLabel("Kho tri thức dành cho mọi người");
        lblMainText.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblMainText.setForeground(Color.WHITE);
        lblMainText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubText = new JLabel("Chào mừng Quản trị viên: " + currentUsername);
        lblSubText.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubText.setForeground(new Color(240, 240, 240));
        lblSubText.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentOverlay.add(lblMainText);
        contentOverlay.add(Box.createVerticalStrut(20));
        contentOverlay.add(lblSubText);

        body.add(contentOverlay);
        return body;
    }

    // ===== BACKGROUND IMAGE PANEL =====

    class BackgroundImagePanel extends JPanel {

        private Image backgroundImage;

        public BackgroundImagePanel(String imagePath) {
            try {
                java.net.URL imgURL = getClass().getResource(imagePath);
                if (imgURL != null) backgroundImage = ImageIO.read(imgURL);
            } catch (Exception e) {
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
