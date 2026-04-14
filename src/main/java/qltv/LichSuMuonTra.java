package qltv;

import dao.UserDAO;
import gui.AdminGUI;
import gui.SinhVienGUI;
import model.PhieuMuon;
import dao.PhieuMuonDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class LichSuMuonTra extends JFrame {

    private String currentUser;
    private boolean isAdmin;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private PhieuMuonDAO PhieuMuonDAO = new PhieuMuonDAO();
    private Image backgroundImage;

    // Bảng màu mới (đồng bộ với các GUI khác)
    private static final Color PRIMARY = new Color(41, 128, 185);
    private static final Color PRIMARY_DARK = new Color(31, 97, 141);
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);
    private static final Color ACCENT_RED = new Color(231, 76, 60);
    private static final Color ACCENT_GRAY = new Color(149, 165, 166);
    private static final Color ROW_EVEN = new Color(236, 240, 241);
    private static final Color ROW_ODD = new Color(255, 255, 255);
    private static final Color HEADER_BG = new Color(44, 62, 80);
    private static final Color TEXT_DARK = new Color(44, 62, 80);
    private static final Color TEXT_SECONDARY = new Color(127, 140, 141);

    public LichSuMuonTra(String username, boolean isAdmin) {
        this.currentUser = username;
        this.isAdmin = isAdmin;

        loadBackgroundImage();

        setTitle("Lịch Sử Mượn Trả Sách");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 246, 250));

        // === HEADER BANNER ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);

        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1));
        titleTextPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("LỊCH SỬ MƯỢN TRẢ SÁCH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Chao, " + username + " | Trang chu > Lich su");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(189, 195, 199));

        titleTextPanel.add(lblTitle);
        titleTextPanel.add(lblSub);

        JLabel lblIcon = new JLabel("\uD83D\uDCD6");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcon.setForeground(Color.WHITE);

        JPanel iconTitlePanel = new JPanel(new BorderLayout());
        iconTitlePanel.setOpaque(false);
        iconTitlePanel.add(lblIcon, BorderLayout.WEST);
        iconTitlePanel.add(titleTextPanel, BorderLayout.CENTER);
        iconTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        headerPanel.add(iconTitlePanel, BorderLayout.WEST);

        // Nút quay lại + tìm kiếm bên phải
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchPanel.setOpaque(false);

        JButton btnBack = createStyledButton("Quay lai", ACCENT_GRAY, Color.WHITE, 120, 32);
        btnBack.addActionListener(e -> {
            this.dispose();
            if (isAdmin) {
                new AdminGUI(currentUser).setVisible(true);
            } else {
                new SinhVienGUI(currentUser).setVisible(true);
            }
        });

        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(220, 32));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton btnSearch = createStyledButton("Tim kiem", PRIMARY_DARK, Color.WHITE, 100, 32);

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(txtSearch.getText());
            }
        });

        searchPanel.add(btnBack);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // === BẢNG DỮ LIỆU ===
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new EmptyBorder(15, 20, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);

        table = new JTable();
        table.setRowHeight(36);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Style header
        JTableHeader tblHeader = table.getTableHeader();
        tblHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblHeader.setForeground(Color.WHITE);
        tblHeader.setBackground(HEADER_BG);
        tblHeader.setPreferredSize(new Dimension(tblHeader.getWidth(), 38));
        tblHeader.setReorderingAllowed(false);

        loadData();
        table.setDefaultRenderer(Object.class, new CustomStatusRenderer());

        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                UserDAO dao = new UserDAO();
                if (isAdmin) {
                    new AdminGUI(dao.getMaADMIN_isLogin()).setVisible(true);
                } else {
                    new SinhVienGUI(dao.getMSV_isLogin()).setVisible(true);
                }
            }
        });
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

    // Tạo button với style đẹp + hover
    private JButton createStyledButton(String text, Color bgColor, Color fgColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    private void loadData() {
        String[] columnNames = {"Ma PM", "Ma SV", "Ho Ten", "Ma Sach", "Ten Sach", "So Luong", "Ngay Muon", "Ngay Tra", "Trang Thai", "Hinh Phat"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<PhieuMuon> dsPhieuMuon;
        if (isAdmin) {
            dsPhieuMuon = PhieuMuonDAO.searchPhieuMuon(null, null, null);
        } else {
            dsPhieuMuon = PhieuMuonDAO.searchPhieuMuon(currentUser, null, null);
        }

        for (PhieuMuon pm : dsPhieuMuon) {
            Object[] row = {
                    pm.getMaPM(),
                    pm.getMaSV(),
                    pm.getHoTen(),
                    pm.getMaSach(),
                    pm.getTenSach(),
                    pm.getSoLuong(),
                    pm.getNgayMuon(),
                    pm.getNgayTra(),
                    pm.getTinhTrang(),
                    "Khong"
            };
            model.addRow(row);
        }
        table.setModel(model);

        // Canh giữa các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Cột Tên Sách, Họ Tên canh trái
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer); // Họ Tên
        table.getColumnModel().getColumn(4).setCellRenderer(leftRenderer); // Tên Sách

        if (!isAdmin) {
            try {
                table.removeColumn(table.getColumn("Ma SV"));
                table.removeColumn(table.getColumn("Ho Ten"));
            } catch (IllegalArgumentException e) {
                System.out.println("Khong tim thay cot can xoa: " + e.getMessage());
            }
        }
    }

    private void filterTable(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        if (query.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    class CustomStatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String colName = table.getColumnName(column);
            String text = (value != null) ? value.toString() : "";

            setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

            if (!isSelected) {
                // Tô màu xen kẽ hàng
                c.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                c.setForeground(TEXT_DARK);
            }

            if (colName.equals("Trang Thai")) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (text.equalsIgnoreCase("Dang muon") || text.contains("Tre") || text.contains("Qua han") || text.contains("Tra cham")) {
                    c.setForeground(ACCENT_RED);
                    c.setFont(new Font("Segoe UI", Font.BOLD, 13));
                } else if (text.equalsIgnoreCase("Da tra")) {
                    c.setForeground(ACCENT_GREEN);
                    c.setFont(new Font("Segoe UI", Font.BOLD, 13));
                }
            } else if (colName.equals("Hinh Phat")) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (text.equals("Khong")) {
                    c.setForeground(ACCENT_GREEN);
                } else {
                    c.setForeground(ACCENT_RED);
                    c.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    setText(text);
                }
            } else {
                setHorizontalAlignment(SwingConstants.CENTER);
            }
            return c;
        }
    }
}
