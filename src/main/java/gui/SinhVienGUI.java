package gui;

import model.SessionManager;

import dao.UserDAO;
import qltv.LichSuMuonTra;
import qltv.LoginFrame;

import view.LoaiSachView;
import view.TacGiaListView;
import view.YeuThichView;

import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.imageio.ImageIO;

public class SinhVienGUI extends JFrame {

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

        // Left sidebar
        add(createSidebar(), BorderLayout.WEST);

        // Right main content
        add(createMainContent(), BorderLayout.CENTER);

        ThemeUtils.addExitConfirmation(this);
    }

    // ===== SIDEBAR =====

    private JPanel createSidebar() {
        JPanel sidebar = ThemeUtils.createSidebar(250);

        // Logo header
        sidebar.add(ThemeUtils.createSidebarHeader("THƯ VIỆN", "ABC"));

        // Vertical spacer
        sidebar.add(Box.createVerticalStrut(10));

        // Navigation menu items
        sidebar.add(createSidebarButton("THỂ LOẠI", "Danh sách thể loại", () -> {
            new LoaiSachView("Thể Loại").doShow();
            dispose();
        }));

        sidebar.add(createSidebarButton("TÁC GIẢ", "Danh sách tác giả", () -> {
            new TacGiaListView("Danh sách tác giả").doShow();
            dispose();
        }));

        sidebar.add(createSidebarButton("YÊU THÍCH", "Sách yêu thích của bạn", () -> {
            new YeuThichView().doShow();
            dispose();
        }));

        sidebar.add(createSidebarButton("LỊCH SỬ MƯỢN TRẢ", "Xem lịch sử mượn trả", () -> {
            new LichSuMuonTra(currentID, false).setVisible(true);
            dispose();
        }));

        sidebar.add(createSidebarButton("TÀI KHOẢN", "Quản lý tài khoản", () -> {
            new QuanLyTaiKhoanGUI(SessionManager.getMaNguoiDung()).doShow();
            dispose();
        }));

        // Push everything at the bottom (user info + logout)
        sidebar.add(Box.createVerticalGlue());

        // User info panel
        sidebar.add(ThemeUtils.createSidebarUser(currentUsername, "Sinh viên"));

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
                new EmptyBorder(15, 25, 15, 25)));

        JLabel lblTitle = new JLabel("Trang chủ Sinh viên");
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

        // Current date on the right
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
        BackgroundImagePanel body = new BackgroundImagePanel("/images/student_banner.jpg");
        body.setLayout(new GridBagLayout());

        JPanel contentOverlay = new JPanel();
        contentOverlay.setLayout(new BoxLayout(contentOverlay, BoxLayout.Y_AXIS));
        contentOverlay.setOpaque(false);
        contentOverlay.setBackground(new Color(0, 0, 0, 120));
        contentOverlay.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel lblMainText = new JLabel("Kho tri thức dành cho mọi người");
        lblMainText.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblMainText.setForeground(Color.WHITE);
        lblMainText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubText = new JLabel("Chào mừng " + currentUsername);
        lblSubText.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        lblSubText.setForeground(Color.WHITE);
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
                if (imgURL != null) {
                    backgroundImage = ImageIO.read(imgURL);
                } else {
                    setBackground(Color.GRAY);
                }
            } catch (Exception e) {
                setBackground(Color.GRAY);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imageWidth = backgroundImage.getWidth(this);
                int imageHeight = backgroundImage.getHeight(this);

                double panelAspect = (double) panelWidth / panelHeight;
                double imageAspect = (double) imageWidth / imageHeight;

                int drawWidth, drawHeight;
                int x = 0, y = 0;

                if (panelAspect > imageAspect) {
                    drawWidth = panelWidth;
                    drawHeight = (int) (panelWidth / imageAspect);
                    y = (panelHeight - drawHeight) / 2;
                } else {
                    drawHeight = panelHeight;
                    drawWidth = (int) (panelHeight * imageAspect);
                    x = (panelWidth - drawWidth) / 2;
                }

                g2.drawImage(backgroundImage, x, y, drawWidth, drawHeight, this);

                // Add a subtle dark overlay to make text more readable on any image
                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillRect(0, 0, panelWidth, panelHeight);

                g2.dispose();
            }
        }
    }
}
