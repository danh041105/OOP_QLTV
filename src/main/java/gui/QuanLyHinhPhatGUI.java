package gui;

import model.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.HinhPhatDAO;
import model.HinhPhat;
import dao.UserDAO;
import quanlymuontra.FormSuaHinhPhat;
import utils.ThemeUtils;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuanLyHinhPhatGUI extends JFrame {
    private JTextField txtMaSV, txtTenSV;
    private JButton btnTimKiemHP, btnTatCaHP, btnSuaHP, btnXoaHP;
    private JTable tblHinhPhat;
    private DefaultTableModel modelHinhPhat;
    private HinhPhatDAO hinhPhatDAO = new HinhPhatDAO();

    public QuanLyHinhPhatGUI() {
        this(null);
    }

    public QuanLyHinhPhatGUI(Frame parent) {
        super("Quản lý hình phạt");
        initGUI();
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
                new AdminGUI(SessionManager.getMaNguoiDung()).setVisible(true);
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
        JPanel topBar = ThemeUtils.createTopBar("QUẢN LÝ HÌNH PHẠT", "Trang chủ > Quản lý mượn trả > Hình phạt", btnBack);
        this.add(topBar, BorderLayout.NORTH);

        // === MAIN CONTENT ===
        JPanel pnlMain = new JPanel(new BorderLayout(0, 15));
        pnlMain.setBackground(ThemeUtils.BG_MAIN);
        pnlMain.setBorder(new EmptyBorder(15, 25, 20, 25));

        // === TOOLBAR ===
        JPanel pnToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        pnToolbar.setBackground(ThemeUtils.BG_CARD);
        pnToolbar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeUtils.BORDER),
            new EmptyBorder(10, 15, 10, 15)
        ));

        pnToolbar.add(ThemeUtils.createLabel("Mã SV:"));
        txtMaSV = ThemeUtils.createTextField(10);
        pnToolbar.add(txtMaSV);

        pnToolbar.add(ThemeUtils.createLabel("Tên SV:"));
        txtTenSV = ThemeUtils.createTextField(15);
        pnToolbar.add(txtTenSV);

        btnTimKiemHP = ThemeUtils.createPrimaryButton("Tìm kiếm");
        pnToolbar.add(btnTimKiemHP);

        btnTatCaHP = ThemeUtils.createSecondaryButton("Hiển thị tất cả");
        pnToolbar.add(btnTatCaHP);

        pnToolbar.add(Box.createHorizontalStrut(10));
        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 25));
        pnToolbar.add(sep);
        pnToolbar.add(Box.createHorizontalStrut(5));

        btnSuaHP = ThemeUtils.createSmallButton("Sửa", ThemeUtils.INFO, ThemeUtils.TEXT_WHITE);
        pnToolbar.add(btnSuaHP);

        btnXoaHP = ThemeUtils.createDangerButton("Xóa");
        pnToolbar.add(btnXoaHP);

        pnlMain.add(pnToolbar, BorderLayout.NORTH);

        // === TABLE ===
        String[] columns = {"Mã HP", "Mã SV", "Tên SV", "Tình trạng", "Lý do", "Ngày phạt", "Hình thức", "Tiến độ"};
        modelHinhPhat = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblHinhPhat = new JTable(modelHinhPhat);
        ThemeUtils.styleTable(tblHinhPhat);

        JScrollPane scrollPane = new JScrollPane(tblHinhPhat);
        ThemeUtils.styleScrollPane(scrollPane);
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        this.add(pnlMain, BorderLayout.CENTER);
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
        setupTableColumns();
    }

    private void setupTableColumns() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblHinhPhat.getColumnCount(); i++) {
            tblHinhPhat.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static void main(String[] args) {
        QuanLyHinhPhatGUI qlhp = new QuanLyHinhPhatGUI();
        qlhp.setVisible(true);
    }
}
