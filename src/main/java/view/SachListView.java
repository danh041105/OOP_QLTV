package view;

import model.Sach;
import controller.SachListController;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import javax.imageio.ImageIO;

public class SachListView extends JFrame {
    private JTable table;
    private JButton btnSearch, btnRefresh, btnThem, btnSua, btnXoa;
    private JTextField txtSearch;
    private DefaultTableModel tableModel;
    private String maSvDangNhap;
    private Image backgroundImage;

    private SachListController controller;

    private String maLoaiSach;
    private String tenLoaiSach;

    // Bảng màu mới
    private static final Color PRIMARY = new Color(41, 128, 185);       // Xanh dương chính
    private static final Color PRIMARY_DARK = new Color(31, 97, 141);   // Xanh dương đậm
    private static final Color ACCENT_GREEN = new Color(39, 174, 96);   // Xanh lá (Thêm)
    private static final Color ACCENT_ORANGE = new Color(243, 156, 18); // Cam (Sửa)
    private static final Color ACCENT_RED = new Color(231, 76, 60);     // Đỏ (Xóa)
    private static final Color ROW_EVEN = new Color(236, 240, 241);     // Hàng chẵn
    private static final Color ROW_ODD = new Color(255, 255, 255);      // Hàng lẻ
    private static final Color HEADER_BG = new Color(44, 62, 80);       // Header bảng

    public SachListView() {
    }
    public SachListView(String maSvDangNhap) {
        this.maSvDangNhap = maSvDangNhap;
    }

    public void setLoaiSach(String maLoaiSach, String tenLoaiSach) {
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
    }

    private void loadBackgroundImage() {
        try {
            // Thay đường dẫn này thành đường dẫn ảnh background của bạn
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
        controller = new SachListController(this, maSvDangNhap);
        controller.initView();

        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Danh sách sách - Thể loại: " + tenLoaiSach);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new LoaiSachView("Thể Loại").doShow();
            }
        });
        setVisible(true);
    }

    private void addControl() {
        createMainPanel();
    }

    private void createMainPanel() {
        // Panel chính với background
        JPanel mainPanel = new BackgroundImagePanel(new BorderLayout(0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // === HEADER: Tiêu đề ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        // Icon sách + Tiêu đề
        JLabel lblIcon = new JLabel("\uD83D\uDCDA");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcon.setForeground(Color.WHITE);
        headerPanel.add(lblIcon, BorderLayout.WEST);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        JLabel lblTitle = new JLabel("DANH SÁCH SÁCH", JLabel.LEFT);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle, BorderLayout.NORTH);

        JLabel lblSubTitle = new JLabel("Thể loại: " + (tenLoaiSach != null ? tenLoaiSach : "Tất cả"), JLabel.LEFT);
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubTitle.setForeground(new Color(189, 195, 199));
        titlePanel.add(lblSubTitle, BorderLayout.CENTER);

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // === PANEL NỘI DUNG: Search + Action + Table ===
        JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Search + Action buttons
        JPanel toolbarPanel = new JPanel(new BorderLayout(10, 0));
        toolbarPanel.setOpaque(true);
        toolbarPanel.setBackground(new Color(255, 255, 255, 230));
        toolbarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Phần tìm kiếm bên trái
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchPanel.setOpaque(false);

        JLabel lblSearch = new JLabel("Tìm kiếm:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSearch.setForeground(new Color(44, 62, 80));

        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(220, 32));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        btnSearch = createStyledButton("Tìm kiếm", PRIMARY, Color.WHITE, 100, 32);
        btnRefresh = createStyledButton("Làm mới", new Color(149, 165, 166), Color.WHITE, 100, 32);

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);
        toolbarPanel.add(searchPanel, BorderLayout.WEST);

        // Phần action buttons bên phải (Thêm / Sửa / Xóa)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        actionPanel.setOpaque(false);

        btnThem = createStyledButton("+ Thêm mới", ACCENT_GREEN, Color.WHITE, 120, 32);
        btnSua = createStyledButton("Sửa", ACCENT_ORANGE, Color.WHITE, 80, 32);
        btnXoa = createStyledButton("Xóa", ACCENT_RED, Color.WHITE, 80, 32);

        actionPanel.add(btnThem);
        actionPanel.add(btnSua);
        actionPanel.add(btnXoa);
        toolbarPanel.add(actionPanel, BorderLayout.EAST);

        contentPanel.add(toolbarPanel, BorderLayout.NORTH);
        contentPanel.add(createTablePanel(), BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    // Tạo button với style đẹp
    private JButton createStyledButton(String text, Color bgColor, Color fgColor, int width, int height) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Hiệu ứng hover (sáng hơn khi di chuột vào)
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

    private JScrollPane createTablePanel() {
        String[] columns = {"Mã sách", "Tên sách", "Thể loại", "Tác giả",
                "NXB", "Năm XB", "Số lượng", "Tình trạng"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        // Style header bảng
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setForeground(Color.WHITE);
        header.setBackground(HEADER_BG);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        header.setReorderingAllowed(false);

        // Tô màu xen kẽ các hàng
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                    c.setForeground(new Color(44, 62, 80));
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        });

        // Center-align cho cột số lượng, năm XB, tình trạng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Năm XB
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Số lượng
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer); // Tình trạng

        setColumnWidths();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        return scrollPane;
    }

    private void setColumnWidths() {
        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(130);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(100);
    }

    public void displaySach(List<Sach> danhSach) {
        tableModel.setRowCount(0);
        if (danhSach.isEmpty()) {
            tableModel.addRow(new Object[]{"", "Không tìm thấy sách nào", "", "", "", "", "", ""});
            return;
        }
        for (Sach s : danhSach) {
            tableModel.addRow(new Object[]{
                    s.getMaSach(),
                    s.getTenSach(),
                    s.getTenLoaiSach(),
                    s.getTenTacGia(),
                    s.getNhaXb(),
                    s.getNamXb(),
                    s.getSoLuong(),
                    s.isTinhTrang() ? "Còn sách" : "Hết sách"
            });
        }
    }

    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JTable getTable() { return table; }
    public String getSearchKeyWord() { return txtSearch.getText().trim(); }
    public void clearSearch() { txtSearch.setText(""); }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }

    public String getSelectedMaSach() {
        int row = table.getSelectedRow();
        if (row == -1) return null;
        return tableModel.getValueAt(row, 0).toString();
    }

    public String getMaLoaiSach() { return maLoaiSach; }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
