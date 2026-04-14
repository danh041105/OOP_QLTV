package view;

import model.SessionManager;
import model.Sach;
import controller.SachListController;
import utils.ThemeUtils;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import gui.AdminGUI;  // ✅ THÊM IMPORT NÀY

public class SachListView extends JFrame {
    private JTable table;
    private JButton btnSearch, btnRefresh, btnThem, btnSua, btnXoa;
    private JTextField txtSearch, txtMaSach, txtTenSach, txtNhaXb, txtNamXb, txtSoLuong, txtMoTa;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbLoaiSach, cbTacGia;
    private String maSvDangNhap;

    private SachListController controller;

    private String maLoaiSach;
    private String tenLoaiSach;

    public SachListView() {
    }
    public SachListView(String maSvDangNhap) {
        this.maSvDangNhap = maSvDangNhap;
    }

    public void setLoaiSach(String maLoaiSach, String tenLoaiSach) {
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
    }

    public void doShow() {
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
                // ✅ THAY ĐỔI:
                String role = SessionManager.getCurrentRole();
                if ("admin".equals(role)) {
                    new AdminGUI(SessionManager.getMaNguoiDung()).setVisible(true);
                } else {
                    new LoaiSachView("Thể Loại").doShow();
                }
            }
        });
        setVisible(true);
    }

    private void addControl() {
        createMainPanel();
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeUtils.BG_MAIN);
        mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Top bar with title and breadcrumb
        String breadcrumb = "Trang chủ > Thể loại > " + (tenLoaiSach != null ? tenLoaiSach : "");
        JPanel topBar = ThemeUtils.createTopBar("DANH MỤC SÁCH", breadcrumb);
        mainPanel.add(topBar, BorderLayout.NORTH);

        // Toolbar with search field and buttons
        txtSearch = ThemeUtils.createTextField(25);
        btnSearch = ThemeUtils.createPrimaryButton("Tìm kiếm");
        btnRefresh = ThemeUtils.createSecondaryButton("Làm mới");

        JPanel toolbar = ThemeUtils.createToolbar(txtSearch, btnSearch, btnRefresh);
        mainPanel.add(toolbar, BorderLayout.CENTER);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(ThemeUtils.BG_MAIN);
        tablePanel.setBorder(new EmptyBorder(0, 25, 20, 25));

        JScrollPane scrollPane = createTablePanel();
        ThemeUtils.styleScrollPane(scrollPane);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
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
        ThemeUtils.styleTable(table);

        setColumnWidths();

        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    private void setColumnWidths() {
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
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
                (s.getSoLuong() > 0) ? "Còn sách" : "Hết sách"
            });
        }        
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public JTable getTable() {
        return table;
    }

    public String getSearchKeyWord() {
        return txtSearch.getText().trim();
    }

    public void clearSearch() {
        txtSearch.setText("");
    }

    // Button cho thêm sửa xóa
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }

    public String getSelectedMaSach() {
        int row = table.getSelectedRow();
        if (row == -1) {
            return null;
        }
        return tableModel.getValueAt(row, 0).toString();
    }

    public String getMaLoaiSach() {
        return maLoaiSach;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
