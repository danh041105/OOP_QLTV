package view;

import dao.UserDAO;
import model.SessionManager;
import controller.PhieuMuonController;
import dao.PhieuMuonDAO;
import dao.YeuThichDAO;
import model.Sach;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class SachDetailView extends JFrame {

    private Sach sach;
    private boolean checkFavorite;

    JButton btnYeuThich, btnMuon;
    YeuThichDAO dao = new YeuThichDAO();
    PhieuMuonDAO pmdao = new PhieuMuonDAO();
    UserDAO uDAO = new UserDAO();
    PhieuMuonController controller;

    private String maSvDangNhap;

    // Colors for favorite button
    private static final Color FAVORITE_PINK = new Color(244, 114, 182);
    private static final Color FAVORITE_HOT_PINK = new Color(219, 39, 119);

    public SachDetailView(Sach sach, String maSvDangNhap) {
        super("Chi tiết sách");
        controller = new PhieuMuonController(pmdao);
        this.sach = sach;
        this.maSvDangNhap = SessionManager.getMaNguoiDung();
    }

    public void doShow() {
        addControl();
        addEvent();
        checkFavorite();
        setSize(800, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addControl() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeUtils.BG_MAIN);
        mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Top bar with book title and breadcrumb
        JPanel topBar = ThemeUtils.createTopBar(sach.getTenSach(), "Danh mục sách > Chi tiết sách");
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Content area
        JPanel contentPanel = new JPanel(new BorderLayout(20, 15));
        contentPanel.setBackground(ThemeUtils.BG_MAIN);
        contentPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Book info panel (image + text info)
        JPanel infoPanel = new JPanel(new BorderLayout(20, 10));
        infoPanel.setBackground(ThemeUtils.BG_CARD);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                new EmptyBorder(20, 20, 20, 20)));

        // Book image with rounded border and shadow
        JLabel lblImage = loadImage();
        JPanel imageWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imageWrapper.setBackground(ThemeUtils.BG_CARD);
        imageWrapper.add(lblImage);
        infoPanel.add(imageWrapper, BorderLayout.WEST);

        // Text info on the right
        JPanel textInfo = new JPanel();
        textInfo.setLayout(new BoxLayout(textInfo, BoxLayout.Y_AXIS));
        textInfo.setBackground(ThemeUtils.BG_CARD);
        textInfo.setBorder(new EmptyBorder(10, 10, 10, 10));

        textInfo.add(createInfoLabel("Tác giả", sach.getTenTacGia()));
        textInfo.add(Box.createVerticalStrut(8));
        textInfo.add(createInfoLabel("Nhà xuất bản", sach.getNhaXb()));
        textInfo.add(Box.createVerticalStrut(8));
        textInfo.add(createInfoLabel("Năm xuất bản", String.valueOf(sach.getNamXb())));
        textInfo.add(Box.createVerticalStrut(8));
        textInfo.add(createInfoLabel("Số lượng", String.valueOf(sach.getSoLuong())));

        infoPanel.add(textInfo, BorderLayout.CENTER);
        contentPanel.add(infoPanel, BorderLayout.CENTER);

        // Mô tả section
        JTextArea txtMoTa = new JTextArea(sach.getMoTa());
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        txtMoTa.setEditable(false);
        txtMoTa.setRows(5);
        txtMoTa.setFont(ThemeUtils.FONT_BODY);
        txtMoTa.setBackground(ThemeUtils.BG_INPUT);
        txtMoTa.setForeground(ThemeUtils.TEXT_PRIMARY);
        txtMoTa.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        scrollMoTa.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(ThemeUtils.BORDER, 1),
                "  Mô tả sách  ",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                ThemeUtils.FONT_BODY_BOLD,
                ThemeUtils.TEXT_SECONDARY));
        scrollMoTa.setBackground(ThemeUtils.BG_CARD);

        contentPanel.add(scrollMoTa, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Bottom action bar
        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        actionBar.setBackground(ThemeUtils.BG_CARD);
        actionBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, ThemeUtils.BORDER),
                new EmptyBorder(15, 25, 15, 25)));

        btnYeuThich = new JButton("♡ Yêu thích") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover() ? getBackground().brighter() : getBackground();
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnYeuThich.setFont(ThemeUtils.FONT_BUTTON);
        btnYeuThich.setForeground(Color.WHITE);
        btnYeuThich.setBackground(FAVORITE_PINK);
        btnYeuThich.setBorderPainted(false);
        btnYeuThich.setFocusPainted(false);
        btnYeuThich.setContentAreaFilled(false);
        btnYeuThich.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnYeuThich.setBorder(new EmptyBorder(8, 20, 8, 20));
        btnYeuThich.setPreferredSize(new Dimension(150, 40));

        btnMuon = ThemeUtils.createPrimaryButton("📖 Mượn sách");
        btnMuon.setPreferredSize(new Dimension(150, 40));

        actionBar.add(btnYeuThich);
        actionBar.add(btnMuon);

        mainPanel.add(actionBar, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    private JLabel loadImage() {
        try {
            String path = "images/" + sach.getImage();
            URL imgURL = getClass().getClassLoader().getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);

                // Create a label with rounded border and shadow
                JLabel lbl = new JLabel(new ImageIcon(img)) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        // Shadow
                        g2.setColor(new Color(0, 0, 0, 25));
                        g2.fillRoundRect(3, 3, getWidth(), getHeight(), 12, 12);
                        // Border
                        g2.setColor(ThemeUtils.BORDER);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                        g2.setColor(ThemeUtils.BG_CARD);
                        g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 11, 11);
                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                lbl.setPreferredSize(new Dimension(200, 280));
                lbl.setBorder(new EmptyBorder(8, 8, 8, 8));
                return lbl;
            } else {
                System.out.println("Khong tim thay file: " + path);
            }
        } catch (Exception e) {
            System.out.println("Loi xu li anh: " + e.getMessage());
        }

        // Placeholder
        JLabel lbl = new JLabel("No Image", SwingConstants.CENTER);
        lbl.setFont(ThemeUtils.FONT_BODY);
        lbl.setForeground(ThemeUtils.TEXT_MUTED);
        lbl.setPreferredSize(new Dimension(200, 280));
        lbl.setBackground(ThemeUtils.BG_INPUT);
        lbl.setOpaque(true);
        lbl.setBorder(BorderFactory.createLineBorder(ThemeUtils.BORDER, 1));
        return lbl;
    }

    private JPanel createInfoLabel(String title, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        panel.setBackground(ThemeUtils.BG_CARD);

        JLabel lblTitle = new JLabel(title + ":");
        lblTitle.setFont(ThemeUtils.FONT_LABEL_BOLD);
        lblTitle.setForeground(ThemeUtils.TEXT_SECONDARY);
        lblTitle.setPreferredSize(new Dimension(110, 20));

        JLabel lblValue = new JLabel(value != null ? value : "N/A");
        lblValue.setFont(ThemeUtils.FONT_LABEL);
        lblValue.setForeground(ThemeUtils.TEXT_PRIMARY);

        panel.add(lblTitle);
        panel.add(lblValue);

        return panel;
    }

    private void checkFavorite() {
        checkFavorite = dao.checkExists(maSvDangNhap, sach.getMaSach());
        if (sach.getSoLuong() <= 0) {
            btnMuon.setEnabled(false);
            btnMuon.setText("Hết sách");
            btnMuon.setBackground(ThemeUtils.BG_INPUT);
            btnMuon.setForeground(ThemeUtils.TEXT_MUTED);
        }
        updateFavorite();
    }

    private void updateFavorite() {
        if (checkFavorite) {
            btnYeuThich.setText("♥ Đã yêu thích");
            btnYeuThich.setBackground(FAVORITE_HOT_PINK);
        } else {
            btnYeuThich.setText("♡ Yêu thích");
            btnYeuThich.setBackground(FAVORITE_PINK);
        }
    }

    private void addEvent() {
        btnYeuThich.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkFavorite) {
                    dao.delete(maSvDangNhap, sach.getMaSach());
                    checkFavorite = false;
                } else {
                    dao.insert(maSvDangNhap, sach.getMaSach());
                    checkFavorite = true;
                }
                updateFavorite();
            }
        });
        btnMuon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PhieuMuonView pmv = new PhieuMuonView(sach.getMaSach(), maSvDangNhap, controller);
                pmv.doShow();
            }
        });
    }
}
