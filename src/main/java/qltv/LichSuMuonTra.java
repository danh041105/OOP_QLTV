package qltv;

import model.SessionManager;
import gui.AdminGUI;
import gui.SinhVienGUI;
import model.PhieuMuon;
import dao.PhieuMuonDAO;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class LichSuMuonTra extends JFrame {

    private String currentUser;
    private boolean isAdmin;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private PhieuMuonDAO PhieuMuonDAO = new PhieuMuonDAO();

    public LichSuMuonTra(String username, boolean isAdmin) {
        this.currentUser = username;
        this.isAdmin = isAdmin;

        setTitle("Lịch Sử Mượn Trả Sách");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ThemeUtils.BG_MAIN);

        // === TOP BAR ===
        JButton btnBack = ThemeUtils.createSecondaryButton("← Quay lại trang chủ");
        btnBack.addActionListener(e -> {
            this.dispose();
            if (isAdmin) {
                new AdminGUI(currentUser).setVisible(true);
            } else {
                new SinhVienGUI(currentUser).setVisible(true);
            }
        });

        String breadcrumb = "Chào, " + username + " | Trang chủ > Lịch sử";
        JPanel topBar = ThemeUtils.createTopBar("LỊCH SỬ MƯỢN TRẢ SÁCH", breadcrumb, btnBack);
        this.add(topBar, BorderLayout.NORTH);

        // === SEARCH BAR ===
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        pnlSearch.setBackground(ThemeUtils.BG_CARD);
        pnlSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeUtils.BORDER),
            new EmptyBorder(10, 25, 10, 25)
        ));

        pnlSearch.add(ThemeUtils.createLabel("Tìm kiếm:"));
        txtSearch = ThemeUtils.createTextField(20);
        txtSearch.setPreferredSize(new Dimension(250, 38));
        pnlSearch.add(txtSearch);

        JButton btnSearch = ThemeUtils.createPrimaryButton("Tìm kiếm");
        pnlSearch.add(btnSearch);

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTable(txtSearch.getText());
            }
        });

        btnSearch.addActionListener(e -> filterTable(txtSearch.getText()));

        this.add(pnlSearch, BorderLayout.CENTER);

        // === TABLE ===
        table = new JTable();
        ThemeUtils.styleTable(table);

        // Override the default renderer to keep CustomStatusRenderer working
        loadData();
        table.setDefaultRenderer(Object.class, new CustomStatusRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 25, 20, 25));
        ThemeUtils.styleScrollPane(scrollPane);

        // Re-add search panel above the table in a wrapper
        JPanel pnlContent = new JPanel(new BorderLayout());
        pnlContent.setBackground(ThemeUtils.BG_MAIN);
        pnlContent.add(pnlSearch, BorderLayout.NORTH);
        pnlContent.add(scrollPane, BorderLayout.CENTER);

        // Remove the standalone search panel that was added before
        this.remove(pnlSearch);
        this.add(pnlContent, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ThemeUtils.addExitConfirmation(LichSuMuonTra.this);
            }
        });
    }

    private void loadData() {
        String[] columnNames = {"Mã PM", "Mã SV", "Họ Tên", "Mã Sách", "Tên Sách", "Số Lượng", "Ngày Mượn", "Ngày Trả",
                "Trạng Thái", "Hình Phạt"};
        model = new DefaultTableModel(columnNames, 0);

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
                    "Không"
            };
            model.addRow(row);
        }
        table.setModel(model);
        // Hide columns for student mode
        if (!isAdmin) {
            if (table.getColumnModel().getColumnCount() > 0) {
                try {
                    table.removeColumn(table.getColumn("Mã SV"));
                    table.removeColumn(table.getColumn("Họ Tên"));
                } catch (IllegalArgumentException e) {
                    System.out.println("Không tìm thấy cột cần xóa: " + e.getMessage());
                }
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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String colName = table.getColumnName(column);
            String text = (value != null) ? value.toString() : "";

            setFont(ThemeUtils.FONT_TABLE_CELL);
            setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

            if (!isSelected) {
                c.setBackground(row % 2 == 0 ? ThemeUtils.BG_CARD : ThemeUtils.BG_TABLE_ALT);
                c.setForeground(ThemeUtils.TEXT_PRIMARY);
            } else {
                c.setBackground(ThemeUtils.PRIMARY_LIGHT);
                c.setForeground(ThemeUtils.PRIMARY_DARK);
            }

            if (colName.equals("Trạng Thái")) {
                setHorizontalAlignment(SwingConstants.CENTER);
                if (text.equalsIgnoreCase("Đang mượn") || text.contains("Trễ")) {
                    c.setForeground(ThemeUtils.DANGER);
                    setFont(ThemeUtils.FONT_BODY_BOLD);
                } else if (text.equalsIgnoreCase("Đã trả")) {
                    c.setForeground(ThemeUtils.SUCCESS);
                    setFont(ThemeUtils.FONT_BODY_BOLD);
                }
            } else if (colName.equals("Hình Phạt")) {
                if (text.equals("Không")) {
                    c.setForeground(ThemeUtils.SUCCESS);
                    setText("Không");
                } else {
                    c.setForeground(ThemeUtils.DANGER);
                    setFont(ThemeUtils.FONT_BODY_BOLD);
                    setText("⚠ " + text);
                }
            } else {
                setHorizontalAlignment(SwingConstants.LEFT);
            }
            return c;
        }
    }
}
