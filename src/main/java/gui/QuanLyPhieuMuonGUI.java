package gui;

import model.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.HinhPhatDAO;
import dao.UserDAO;
import model.PhieuMuon;
import dao.PhieuMuonDAO;
import quanlymuontra.FormSuaPhieuMuon;
import quanlymuontra.FormThemHinhPhat;
import quanlymuontra.FormThemPhieuMuon;
import utils.ThemeUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuanLyPhieuMuonGUI extends JFrame {
    private PhieuMuonDAO PhieuMuonDAO = new PhieuMuonDAO();
    private HinhPhatDAO hinhPhatDAO = new HinhPhatDAO();
    private JTable tblPhieuMuon;
    private DefaultTableModel modelPhieuMuon;
    private JTextField txtMaSV, txtTenSV, txtTenSach;
    private JPanel pnlDaTra, pnlDangMuon, pnlQuaHan, pnlTraCham;
    private JLabel lblDaTraVal, lblDangMuonVal, lblQuaHanVal, lblTraChamVal;
    private JButton btnTatCaPM;
    private JButton btnTimKiem, btnThem, btnSua, btnXoa, btnPhat;

    public QuanLyPhieuMuonGUI() {
        super("Quản lý phiếu mượn");
        initGUI();
        addEvents();

        loadDataPhieuMuon("");
        this.setSize(1400, 800);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ThemeUtils.addExitConfirmation(QuanLyPhieuMuonGUI.this);
            }
        });
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        getContentPane().setBackground(ThemeUtils.BG_MAIN);

        // === TOP BAR ===
        JButton btnBack = ThemeUtils.createSecondaryButton("← Quay lại");
        btnBack.addActionListener(e -> {
            dispose();
            new AdminGUI(SessionManager.getMaNguoiDung()).setVisible(true);
        });
        JPanel topBar = ThemeUtils.createTopBar("QUẢN LÝ PHIẾU MƯỢN", "Trang chủ > Quản lý mượn trả > Phiếu mượn", btnBack);
        this.add(topBar, BorderLayout.NORTH);

        // === MAIN CONTENT ===
        JPanel pnMain = new JPanel(new BorderLayout(10, 10));
        pnMain.setBackground(ThemeUtils.BG_MAIN);
        pnMain.setBorder(new EmptyBorder(15, 15, 15, 15));

        // === LEFT STATS PANEL ===
        JPanel pnStats = new JPanel();
        pnStats.setLayout(new BoxLayout(pnStats, BoxLayout.Y_AXIS));
        pnStats.setBackground(ThemeUtils.BG_MAIN);
        pnStats.setPreferredSize(new Dimension(200, 0));
        pnStats.setBorder(new EmptyBorder(0, 0, 0, 0));

        pnStats.add(Box.createVerticalStrut(5));
        pnlDaTra = createClickableStatCard("Đã trả", "0", ThemeUtils.SUCCESS);
        pnStats.add(pnlDaTra);
        pnStats.add(Box.createVerticalStrut(8));
        pnlDangMuon = createClickableStatCard("Đang mượn", "0", ThemeUtils.INFO);
        pnStats.add(pnlDangMuon);
        pnStats.add(Box.createVerticalStrut(8));
        pnlQuaHan = createClickableStatCard("Quá hạn trả", "0", ThemeUtils.DANGER);
        pnStats.add(pnlQuaHan);
        pnStats.add(Box.createVerticalStrut(8));
        pnlTraCham = createClickableStatCard("Trả chậm", "0", ThemeUtils.WARNING);
        pnStats.add(pnlTraCham);
        pnStats.add(Box.createVerticalStrut(15));
        btnTatCaPM = ThemeUtils.createSecondaryButton("Hiển thị tất cả");
        btnTatCaPM.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnTatCaPM.setMaximumSize(new Dimension(185, 38));
        pnStats.add(btnTatCaPM);
        pnStats.add(Box.createVerticalGlue());

        // === RIGHT CONTENT ===
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 10));
        pnlCenter.setBackground(ThemeUtils.BG_MAIN);

        // Toolbar
        JPanel pnlToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        pnlToolbar.setBackground(ThemeUtils.BG_CARD);
        pnlToolbar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeUtils.BORDER),
            new EmptyBorder(10, 15, 10, 15)
        ));

        pnlToolbar.add(ThemeUtils.createLabel("Mã SV:"));
        txtMaSV = ThemeUtils.createTextField(8);
        pnlToolbar.add(txtMaSV);

        pnlToolbar.add(ThemeUtils.createLabel("Tên SV:"));
        txtTenSV = ThemeUtils.createTextField(12);
        pnlToolbar.add(txtTenSV);

        pnlToolbar.add(ThemeUtils.createLabel("Tên sách:"));
        txtTenSach = ThemeUtils.createTextField(12);
        pnlToolbar.add(txtTenSach);

        btnTimKiem = ThemeUtils.createPrimaryButton("Tìm kiếm");
        pnlToolbar.add(btnTimKiem);

        pnlToolbar.add(Box.createHorizontalStrut(10));
        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 25));
        pnlToolbar.add(sep);
        pnlToolbar.add(Box.createHorizontalStrut(5));

        btnThem = ThemeUtils.createSuccessButton("+ Thêm PM");
        pnlToolbar.add(btnThem);

        btnSua = ThemeUtils.createPrimaryButton("Sửa");
        pnlToolbar.add(btnSua);

        btnXoa = ThemeUtils.createDangerButton("Xóa");
        pnlToolbar.add(btnXoa);

        btnPhat = ThemeUtils.createSmallButton("Hình phạt", ThemeUtils.WARNING, ThemeUtils.TEXT_WHITE);
        pnlToolbar.add(btnPhat);

        pnlCenter.add(pnlToolbar, BorderLayout.NORTH);

        // Table
        String[] cols = {"Mã PM", "Mã SV", "Họ Tên", "Mã sách", "Tên sách", "Tác giả", "Nhà XB", "SL", "Ngày mượn", "Ngày trả", "Tình trạng"};
        modelPhieuMuon = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPhieuMuon = new JTable(modelPhieuMuon);
        ThemeUtils.styleTable(tblPhieuMuon);

        tblPhieuMuon.getColumnModel().getColumn(4).setPreferredWidth(210);
        tblPhieuMuon.getColumnModel().getColumn(2).setPreferredWidth(130);
        tblPhieuMuon.getColumnModel().getColumn(5).setPreferredWidth(120);
        tblPhieuMuon.getColumnModel().getColumn(10).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(tblPhieuMuon);
        ThemeUtils.styleScrollPane(scrollPane);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        pnMain.add(pnStats, BorderLayout.WEST);
        pnMain.add(pnlCenter, BorderLayout.CENTER);
        this.add(pnMain, BorderLayout.CENTER);
    }

    private void addEvents() {
        btnTatCaPM.addActionListener(e -> loadDataPhieuMuon(""));
        pnlDaTra.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { loadDataPhieuMuon("Đã trả"); } });
        pnlDangMuon.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { loadDataPhieuMuon("Đang mượn"); } });
        pnlQuaHan.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { loadDataPhieuMuon("Quá hạn trả"); } });
        pnlTraCham.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { loadDataPhieuMuon("Trả chậm"); } });

        btnTimKiem.addActionListener(e -> {
            updateTablePM(PhieuMuonDAO.searchPhieuMuon(txtMaSV.getText().trim(), txtTenSV.getText().trim(), txtTenSach.getText().trim()));
        });

        btnThem.addActionListener(e -> {
            FormThemPhieuMuon form = new FormThemPhieuMuon(this);
            form.setVisible(true);
            if (form.isSuccess()) {
                loadDataPhieuMuon("");
            }
        });

        btnSua.addActionListener(e -> {
            int row = tblPhieuMuon.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu mượn từ danh sách để sửa!");
                return;
            }
            int columnCount = tblPhieuMuon.getColumnCount();
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = tblPhieuMuon.getValueAt(row, i);
            }
            FormSuaPhieuMuon formSua = new FormSuaPhieuMuon(this, rowData);
            formSua.setVisible(true);
            if (formSua.isSuccess()) {
                loadDataPhieuMuon("");
            }
        });

        btnXoa.addActionListener(e -> {
            int row = tblPhieuMuon.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu mượn cần xóa!");
                return;
            }
            String maPM = tblPhieuMuon.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa phiếu mượn " + maPM + "?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                int result = PhieuMuonDAO.deletePhieuMuon(maPM);
                switch (result) {
                    case 1:
                        JOptionPane.showMessageDialog(this, "Xóa phiếu mượn thành công!");
                        loadDataPhieuMuon("");
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(this,
                            "Không thể xóa! Phiếu mượn này chưa ở trạng thái 'Đã trả'.",
                            "Cảnh báo",
                            JOptionPane.WARNING_MESSAGE);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this,
                            "Phiếu mượn này có hình phạt chưa hoàn thành nên không thể xóa!",
                            "Vi phạm chưa xử lý",
                            JOptionPane.ERROR_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this,
                            "Lỗi hệ thống hoặc không tìm thấy mã phiếu mượn này!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        });

        btnPhat.addActionListener(e -> {
            int row = tblPhieuMuon.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu mượn để xử lý vi phạm!");
                return;
            }
            int columnCount = tblPhieuMuon.getColumnCount();
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = tblPhieuMuon.getValueAt(row, i);
            }
            FormThemHinhPhat formPhat = new FormThemHinhPhat(this, rowData);
            formPhat.setVisible(true);
            if (formPhat.isSuccess()) {
                this.dispose();
                JOptionPane.showMessageDialog(null, "Hệ thống sẽ chuyển đến trang Hình Phạt!");
                QuanLyHinhPhatGUI qlhp = new QuanLyHinhPhatGUI();
                qlhp.setVisible(true);
            }
        });
    }

    public void loadDataPhieuMuon(String tinhTrang) {
        updateTablePM(PhieuMuonDAO.layDuLieuPhieuMuon(tinhTrang));
        int[] counts = PhieuMuonDAO.laySoLuongThongKe();
        lblDaTraVal.setText(String.valueOf(counts[0]));
        lblDangMuonVal.setText(String.valueOf(counts[1]));
        lblQuaHanVal.setText(String.valueOf(counts[2]));
        lblTraChamVal.setText(String.valueOf(counts[3]));
    }

    private JPanel createClickableStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ThemeUtils.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(accentColor);
                g2.fillRect(0, 0, 4, getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(185, 65));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(ThemeUtils.FONT_BODY);
        lblTitle.setForeground(ThemeUtils.TEXT_SECONDARY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(accentColor);

        JPanel content = new JPanel(new BorderLayout());
        content.setOpaque(false);
        content.add(lblTitle, BorderLayout.NORTH);
        content.add(lblValue, BorderLayout.CENTER);
        card.add(content, BorderLayout.CENTER);

        // Store value label reference for updating later
        if (title.equals("Đã trả")) lblDaTraVal = lblValue;
        else if (title.equals("Đang mượn")) lblDangMuonVal = lblValue;
        else if (title.equals("Quá hạn trả")) lblQuaHanVal = lblValue;
        else if (title.equals("Trả chậm")) lblTraChamVal = lblValue;

        return card;
    }

    private void updateTablePM(java.util.List<PhieuMuon> ds) {
        modelPhieuMuon.setRowCount(0);
        for (PhieuMuon pm : ds) {
            modelPhieuMuon.addRow(new Object[]{
                pm.getMaPM(),
                pm.getMaSV(),
                pm.getHoTen(),
                pm.getMaSach(),
                pm.getTenSach(),
                pm.getTentg(),
                pm.getNhaxb(),
                pm.getSoLuong(),
                pm.getNgayMuon(),
                pm.getNgayTra(),
                pm.getTinhTrang()
            });
        }
    }

    public static void main(String[] args) {
        QuanLyPhieuMuonGUI qlpm = new QuanLyPhieuMuonGUI();
        qlpm.setVisible(true);
    }
}
