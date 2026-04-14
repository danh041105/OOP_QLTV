package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import dao.UserDAO;
import quanlytaikhoan.AdminPanel;
import quanlytaikhoan.SinhVienPanel;

public class QuanLyTaiKhoanGUI extends JFrame {

    private JPanel pnlContent; // Panel chứa nội dung chính (thay pnlMain)
    private String msvFilter = null;
    private JButton btnAdmin, btnSinhVien;
    private JPanel sidebar;

    // Bảng màu mới
    private static final Color PRIMARY = new Color(41, 128, 185);
    private static final Color PRIMARY_DARK = new Color(31, 97, 141);
    private static final Color SIDEBAR_BG = new Color(44, 62, 80);
    private static final Color SIDEBAR_HOVER = new Color(52, 73, 94);
    private static final Color SIDEBAR_ACTIVE = new Color(41, 128, 185);
    private static final Color TEXT_WHITE = Color.WHITE;
    private static final Color TEXT_LIGHT = new Color(189, 195, 199);
    private static final Color CONTENT_BG = new Color(245, 246, 250);
    private static final Color CARD_BG = new Color(255, 255, 255);
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private Image backgroundImage;

    public QuanLyTaiKhoanGUI(String title) {
        super(title);
        initUI();
    }

    public QuanLyTaiKhoanGUI() {
        this("Quản lý tài khoản");
    }

    public QuanLyTaiKhoanGUI(String msv, boolean isSinhVien) {
        super("Thông tin cá nhân");
        this.msvFilter = msv;
        initUI();
    }

    private void loadBackgroundImage() {
        try {
            String imagePath = "D:\\project_java\\src\\main\\resources\\images\\admin_banner.jpg";
            File file = new File(imagePath);
            if (file.exists()) {
                backgroundImage = ImageIO.read(file);
            }
        } catch (IOException e) {
            System.out.println("Loi khi tai anh background: " + e.getMessage());
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

    private void initUI() {
        loadBackgroundImage();

        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        if (msvFilter == null) {
            // Admin mode - có sidebar + header
            setLayout(new BorderLayout());
            getContentPane().setBackground(CONTENT_BG);

            // HEADER
            add(createHeader("QUẢN LÝ TÀI KHOẢN", "\uD83D\uDC65", "Quản lý tài khoản Admin và Sinh viên"), BorderLayout.NORTH);

            // SIDEBAR + CONTENT
            JPanel bodyPanel = new JPanel(new BorderLayout());
            bodyPanel.setBackground(CONTENT_BG);
            bodyPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            sidebar = createSidebar(true);
            bodyPanel.add(sidebar, BorderLayout.WEST);

            pnlContent = new BackgroundImagePanel(new BorderLayout(10, 10));
            pnlContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            bodyPanel.add(pnlContent, BorderLayout.CENTER);

            add(bodyPanel, BorderLayout.CENTER);

            // FOOTER
            add(createFooter(), BorderLayout.SOUTH);

            showSinhVien();
            highlightSidebarButton(btnSinhVien);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    dispose();
                    UserDAO dao = new UserDAO();
                    new AdminGUI(dao.getMaADMIN_isLogin()).setVisible(true);
                }
            });
        } else {
            // Sinh vien mode - chỉ có header (không sidebar)
            setLayout(new BorderLayout());
            getContentPane().setBackground(CONTENT_BG);

            add(createHeader("THÔNG TIN CÁ NHÂN", "\uD83D\uDC64", "Xem và cập nhật thông tin tài khoản của bạn"), BorderLayout.NORTH);

            pnlContent = new BackgroundImagePanel(new BorderLayout(10, 10));
            pnlContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            add(pnlContent, BorderLayout.CENTER);

            add(createFooter(), BorderLayout.SOUTH);

            showSinhVien(msvFilter);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    dispose();
                    UserDAO dao = new UserDAO();
                    String msv = dao.getMSV_isLogin();
                    if (msv != null) {
                        new SinhVienGUI(msv).setVisible(true);
                    } else {
                        new qltv.LoginFrame().setVisible(true);
                    }
                }
            });
        }
    }

    // === HEADER ===
    private JPanel createHeader(String title, String icon, String subtitle) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcon.setForeground(TEXT_WHITE);
        headerPanel.add(lblIcon, BorderLayout.WEST);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        JLabel lblTitle = new JLabel(title, SwingConstants.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(TEXT_WHITE);
        titleWrapper.add(lblTitle, BorderLayout.NORTH);

        JLabel lblSub = new JLabel(subtitle, SwingConstants.LEFT);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(TEXT_LIGHT);
        titleWrapper.add(lblSub, BorderLayout.CENTER);

        headerPanel.add(titleWrapper, BorderLayout.CENTER);

        // Thời gian hiện tại bên phải
        JLabel lblTime = new JLabel("\uD83D\uDD52 " + java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")), SwingConstants.RIGHT);
        lblTime.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTime.setForeground(TEXT_LIGHT);
        headerPanel.add(lblTime, BorderLayout.EAST);

        return headerPanel;
    }

    // === SIDEBAR (chỉ Admin) ===
    private JPanel createSidebar(boolean isAdmin) {
        JPanel sidebarPanel = new JPanel(new BorderLayout(0, 0));
        sidebarPanel.setOpaque(true);
        sidebarPanel.setBackground(SIDEBAR_BG);
        sidebarPanel.setPreferredSize(new Dimension(220, 0));

        // Sidebar header
        JPanel sidebarHeader = new JPanel(new BorderLayout());
        sidebarHeader.setOpaque(true);
        sidebarHeader.setBackground(PRIMARY_DARK);
        sidebarHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblMenu = new JLabel("DANH MỤC");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMenu.setForeground(TEXT_WHITE);
        sidebarHeader.add(lblMenu, BorderLayout.WEST);
        sidebarPanel.add(sidebarHeader, BorderLayout.NORTH);

        // Sidebar buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 5));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(SIDEBAR_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // Nút Tài khoản Admin
        btnAdmin = createSidebarButton("\uD83D\uDC51  Tài khoản Admin");
        btnAdmin.addActionListener(e -> {
            showAdmin();
            highlightSidebarButton(btnAdmin);
        });

        // Nút Tài khoản Sinh viên
        btnSinhVien = createSidebarButton("\uD83D\uDC68\u200D\uD83C\uDF93  Tài khoản Sinh viên");
        btnSinhVien.addActionListener(e -> {
            showSinhVien();
            highlightSidebarButton(btnSinhVien);
        });

        buttonPanel.add(btnAdmin);
        buttonPanel.add(btnSinhVien);

        // Separator
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(new Color(52, 73, 94));
        buttonPanel.add(sep);

        // Nút thống kê (chỉ hiển thị)
        JLabel lblStats = new JLabel("  Thống kê nhanh");
        lblStats.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStats.setForeground(TEXT_LIGHT);
        lblStats.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        buttonPanel.add(lblStats);

        JLabel lblInfo = new JLabel("  <html>&#8226; Quản lý người dùng<br>&#8226; Thêm / Sửa / Xóa<br>&#8226; Phân quyền</html>");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfo.setForeground(new Color(127, 140, 141));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));
        buttonPanel.add(lblInfo);

        sidebarPanel.add(buttonPanel, BorderLayout.CENTER);

        // Sidebar footer
        JPanel sidebarFooter = new JPanel(new BorderLayout());
        sidebarFooter.setOpaque(true);
        sidebarFooter.setBackground(PRIMARY_DARK);
        sidebarFooter.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblVersion = new JLabel("QLTV v1.0");
        lblVersion.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblVersion.setForeground(new Color(127, 140, 141));
        sidebarFooter.add(lblVersion, BorderLayout.CENTER);
        sidebarPanel.add(sidebarFooter, BorderLayout.SOUTH);

        return sidebarPanel;
    }

    // Tạo sidebar button
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(SIDEBAR_BG);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(SIDEBAR_ACTIVE)) {
                    btn.setBackground(SIDEBAR_HOVER);
                }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!btn.getBackground().equals(SIDEBAR_ACTIVE)) {
                    btn.setBackground(SIDEBAR_BG);
                }
            }
        });

        return btn;
    }

    // Highlight nút đang chọn trong sidebar
    private void highlightSidebarButton(JButton activeBtn) {
        if (btnAdmin != null && btnSinhVien != null) {
            btnAdmin.setBackground(SIDEBAR_BG);
            btnSinhVien.setBackground(SIDEBAR_BG);
            activeBtn.setBackground(SIDEBAR_ACTIVE);
        }
    }

    // === FOOTER ===
    private JPanel createFooter() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(true);
        footerPanel.setBackground(new Color(44, 62, 80));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JLabel lblLeft = new JLabel("Hệ thống Quản lý Thư viện - OOP_QLTV");
        lblLeft.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblLeft.setForeground(new Color(127, 140, 141));

        JLabel lblRight = new JLabel("Developed by danh041105");
        lblRight.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblRight.setForeground(new Color(127, 140, 141));
        lblRight.setHorizontalAlignment(SwingConstants.RIGHT);

        footerPanel.add(lblLeft, BorderLayout.WEST);
        footerPanel.add(lblRight, BorderLayout.EAST);

        return footerPanel;
    }

    public void doShow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        new QuanLyTaiKhoanGUI().doShow();
    }

    public void showAdmin() {
        pnlContent.removeAll();
        pnlContent.add(new AdminPanel(), BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    public void showSinhVien() {
        pnlContent.removeAll();
        pnlContent.add(new SinhVienPanel(), BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }

    public void showSinhVien(String msv) {
        pnlContent.removeAll();
        pnlContent.add(new SinhVienPanel(msv), BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }
}
