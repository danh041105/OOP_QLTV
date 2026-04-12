package view;

import model.Sach;
import controller.SachListController;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    private static final Color main_color = new Color(236, 240, 241);
    private static final Color border_color = new Color(52, 152, 219);
    private static final Color btn_color = new Color(149, 165, 166);

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

        setSize(900, 600);
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
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(main_color);

        mainPanel.add(createSearchPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border_color, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        txtSearch = new JTextField(25);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(250, 35));

        btnSearch = createButton("Tìm kiếm");
        btnRefresh = createButton("Làm mới");

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        return searchPanel;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(btn_color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 30));
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
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setColumnWidths();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));

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
                s.isTinhTrang() ? "Còn sách" : "Hết sách"
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
