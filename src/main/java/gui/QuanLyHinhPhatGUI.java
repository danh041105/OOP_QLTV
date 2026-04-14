/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import dao.HinhPhatDAO;
import model.HinhPhat;
import dao.UserDAO;
import quanlymuontra.FormSuaHinhPhat;
import quanlymuontra.QuanLyMuonTra_Main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class QuanLyHinhPhatGUI extends JFrame {
    private JTextField txtMaSV, txtTenSV;
    private JButton btnTimKiemHP, btnTatCaHP, btnSuaHP, btnXoaHP;
    private JTable tblHinhPhat;
    private DefaultTableModel modelHinhPhat;
    private HinhPhatDAO hinhPhatDAO = new HinhPhatDAO();
    private Image backgroundImage;

    // Bảng màu mới (đồng bộ với các GUI khác)
    private static final Color PRIMARY = new Color(41, 128, 185);
    private static final Color ACCENT_ORANGE = new Color(243, 156, 18);
    private static final Color ACCENT_RED = new Color(231, 76, 60);
    private static final Color ROW_EVEN = new Color(236, 240, 241);
    private static final Color ROW_ODD = new Color(255, 255, 255);
    private static final Color HEADER_BG = new Color(44, 62, 80);

    public QuanLyHinhPhatGUI() {
        this(null);
    }

    public QuanLyHinhPhatGUI(Frame parent) {
        super("Quản lý hình phạt");
        loadBackgroundImage();
        initGUI();
        QuanLyMuonTra_Main.setupMenuBar(this);
        addAction();
        loadDataHinhPhat("");
        this.setSize(1400, 800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                UserDAO dao = new UserDAO();
                new AdminGUI(dao.getMaADMIN_isLogin()).setVisible(true);
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

    private void initGUI() {
        this.setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 246, 250));

        // === HEADER BANNER ===
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblIcon = new JLabel("\u26A0\uFE0F");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcon.setForeground(Color.WHITE);
        headerPanel.add(lblIcon, BorderLayout.WEST);

        JPanel titleWrapper = new JPanel(new BorderLayout());
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        JLabel lblHeaderTitle = new JLabel("QUẢN LÝ HÌNH PHẠT", JLabel.LEFT);
        lblHeaderTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeaderTitle.setForeground(Color.WHITE);
        titleWrapper.add(lblHeaderTitle, BorderLayout.NORTH);

        JLabel lblSub = new JLabel("Quản lý vi phạm và hình phạt sinh viên", JLabel.LEFT);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(189, 195, 199));
        titleWrapper.add(lblSub, BorderLayout.CENTER);

        headerPanel.add(titleWrapper, BorderLayout.CENTER);
        this.add(headerPanel, BorderLayout.NORTH);

        // === PHẦN THÂN ===
        JPanel pnlMain = new BackgroundImagePanel(new BorderLayout(0, 15));
        pnlMain.setBorder(new EmptyBorder(15, 30, 20, 30));

        // Thanh toolbar tìm kiếm + action
        JPanel pnTimKiem = new JPanel(new BorderLayout(10, 0));
        pnTimKiem.setOpaque(true);
        pnTimKiem.setBackground(new Color(255, 255, 255, 230));
        pnTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Phần tìm kiếm bên trái
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);

        JLabel lblMaSV = new JLabel("Mã SV:");
        lblMaSV.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMaSV.setForeground(new Color(44, 62, 80));

        txtMaSV = new JTextField(10);
        txtMaSV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMaSV.setPreferredSize(new Dimension(120, 32));
        txtMaSV.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JLabel lblTenSV = new JLabel("Tên SV:");
        lblTenSV.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTenSV.setForeground(new Color(44, 62, 80));

        txtTenSV = new JTextField(15);
        txtTenSV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTenSV.setPreferredSize(new Dimension(150, 32));
        txtTenSV.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        btnTimKiemHP = createStyledButton("Tìm kiếm", PRIMARY, Color.WHITE, 100, 32);
        btnTatCaHP = createStyledButton("Hiển thị tất cả", new Color(149, 165, 166), Color.WHITE, 150, 32);

        searchPanel.add(lblMaSV);
        searchPanel.add(txtMaSV);
        searchPanel.add(lblTenSV);
        searchPanel.add(txtTenSV);
        searchPanel.add(btnTimKiemHP);
        searchPanel.add(btnTatCaHP);
        pnTimKiem.add(searchPanel, BorderLayout.WEST);

        // Phần action bên phải
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        actionPanel.setOpaque(false);

        btnSuaHP = createStyledButton("Sửa", ACCENT_ORANGE, Color.WHITE, 80, 32);
        btnXoaHP = createStyledButton("Xóa", ACCENT_RED, Color.WHITE, 80, 32);

        actionPanel.add(btnSuaHP);
        actionPanel.add(btnXoaHP);
        pnTimKiem.add(actionPanel, BorderLayout.EAST);

        pnlMain.add(pnTimKiem, BorderLayout.NORTH);

        // === BẢNG DỮ LIỆU ===
        String[] columns = {"Mã HP", "Mã SV", "Tên SV", "Tình trạng", "Lý do", "Ngày phạt", "Hình thức", "Tiến độ"};
        modelHinhPhat = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblHinhPhat = new JTable(modelHinhPhat);
        tblHinhPhat.setRowHeight(35);
        tblHinhPhat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblHinhPhat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblHinhPhat.setSelectionBackground(new Color(52, 152, 219));
        tblHinhPhat.setSelectionForeground(Color.WHITE);
        tblHinhPhat.setShowGrid(false);
        tblHinhPhat.setIntercellSpacing(new Dimension(0, 1));

        // Style header
        JTableHeader header = tblHinhPhat.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(Color.WHITE);
        header.setBackground(HEADER_BG);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        header.setReorderingAllowed(false);

        // Tô màu xen kẽ hàng + renderer cho cột Tình trạng
        tblHinhPhat.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                    c.setForeground(new Color(44, 62, 80));
                }
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        });

        // Cột Tên SV, Lý do, Hình thức canh trái cho dễ đọc
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        tblHinhPhat.getColumnModel().getColumn(2).setCellRenderer(leftRenderer); // Tên SV
        tblHinhPhat.getColumnModel().getColumn(4).setCellRenderer(leftRenderer); // Lý do
        tblHinhPhat.getColumnModel().getColumn(6).setCellRenderer(leftRenderer); // Hình thức

        JScrollPane scrollPane = new JScrollPane(tblHinhPhat);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        this.add(pnlMain, BorderLayout.CENTER);
    }

    // Tạo button với style đẹp + hover effect
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

    private void addAction() {
        btnTimKiemHP.addActionListener(e -> loadDataHinhPhat("search"));
        btnTatCaHP.addActionListener(e -> {
            txtMaSV.setText("");
            txtTenSV.setText("");
            loadDataHinhPhat("");
        });

        btnSuaHP.addActionListener(e -> {
            int row = tblHinhPhat.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Hãy chọn 1 hình phạt để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Object[] rowData = new Object[tblHinhPhat.getColumnCount()];
            for (int i = 0; i < tblHinhPhat.getColumnCount(); i++) {
                rowData[i] = tblHinhPhat.getValueAt(row, i);
            }
            FormSuaHinhPhat formSua = new FormSuaHinhPhat(this, rowData);
            formSua.setVisible(true);
            if (formSua.isSuccess()) loadDataHinhPhat("");
        });

        btnXoaHP.addActionListener(e -> {
            int row = tblHinhPhat.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
                return;
            }
            String maHP = tblHinhPhat.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hình phạt mã " + maHP + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int res = hinhPhatDAO.deleteHinhPhat(Integer.parseInt(maHP));
                if (res == 1) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadDataHinhPhat("");
                } else if (res == 2) {
                    JOptionPane.showMessageDialog(this, "Chỉ được xóa khi tiến độ 'Đã hoàn thành'!");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!");
                }
            }
        });
    }

    private void loadDataHinhPhat(String mode) {
        String maSV = mode.equals("search") ? txtMaSV.getText().trim() : "";
        String tenSV = mode.equals("search") ? txtTenSV.getText().trim() : "";

        java.util.List<HinhPhat> ds = hinhPhatDAO.layDanhSachHinhPhat(maSV, tenSV);

        modelHinhPhat.setRowCount(0);
        for (HinhPhat hp : ds) {
            modelHinhPhat.addRow(new Object[]{
                    hp.getMaHP(),
                    hp.getMaSV(),
                    hp.getHoTen(),
                    hp.getTinhTrang(),
                    hp.getLyDo(),
                    hp.getNgayPhat(),
                    hp.getHinhThuc(),
                    hp.getTienDo()
            });
        }
    }

    public static void main(String[] args) {
        QuanLyHinhPhatGUI qlhp = new QuanLyHinhPhatGUI();
        qlhp.setVisible(true);
    }
}
