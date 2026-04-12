package admin_book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.TheLoaiDAO;
import model.TheLoai;
import qltv.AdminGUI;
import dao.UserDAO;

public class theloaiGUI extends JFrame implements ActionListener {
    private JTable tbltheloai;
    private DefaultTableModel model;
    private JButton btnthem, btnsua, btnxoa;
    private JTextField txttimkiem;

    public theloaiGUI() {
        initGUI();
        loadDataToTable();
        addAction();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                UserDAO dao = new UserDAO();
                new AdminGUI(dao.getMaADMIN_isLogin()).setVisible(true);
            }
        });
        this.setVisible(true);
    }

    private void initGUI() {
        this.setTitle("Quản lý thể loại");
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel pntieude = new JPanel();
        JLabel lbltieude = new JLabel("QUẢN LÝ THỂ LOẠI");
        lbltieude.setForeground(Color.BLUE);
        lbltieude.setFont(new Font("Arial", Font.BOLD, 20));
        pntieude.add(lbltieude);
        this.add(pntieude, BorderLayout.NORTH);

        JPanel pnchinh = new JPanel(new BorderLayout(10, 10));
        this.add(pnchinh, BorderLayout.CENTER);

        JPanel pnnut = new JPanel();
        JLabel lbltimkiem = new JLabel("Tìm kiếm");
        txttimkiem = new JTextField(20);
        btnthem = new JButton("Thêm");
        btnsua = new JButton("Sửa");
        btnxoa = new JButton("Xóa");
        btnthem.setBackground(Color.LIGHT_GRAY);
        btnsua.setBackground(Color.BLUE);
        btnxoa.setBackground(Color.RED);
        pnnut.add(lbltimkiem);
        pnnut.add(txttimkiem);
        pnnut.add(btnthem);
        pnnut.add(btnsua);
        pnnut.add(btnxoa);
        pnchinh.add(pnnut, BorderLayout.NORTH);

        String[] columnName = { "Mã loại sách", "Tên loại sách" };
        model = new DefaultTableModel(columnName, 0);
        tbltheloai = new JTable(model);
        tbltheloai.setRowHeight(25);
        tbltheloai.setFont(new Font("Arial", Font.PLAIN, 14));
        tbltheloai.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tbltheloai.getColumnCount(); i++) {
            tbltheloai.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(tbltheloai);
        pnchinh.add(scrollPane, BorderLayout.CENTER);
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        List<TheLoai> list = TheLoaiDAO.getAll();
        for (TheLoai tl : list) {
            model.addRow(new Object[] {
                    tl.getMa_loai_sach(),
                    tl.getTen_loai_sach()
            });
        }
    }

    private void addAction() {
        btnthem.addActionListener(this);
        btnsua.addActionListener(this);
        btnxoa.addActionListener(this);

        txttimkiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = txttimkiem.getText().trim();
                if (keyword.isEmpty()) {
                    loadDataToTable();
                    return;
                }
                List<TheLoai> list = TheLoaiDAO.searchByName(keyword);
                model.setRowCount(0);
                for (TheLoai tl : list) {
                    model.addRow(new Object[] {
                            tl.getMa_loai_sach(),
                            tl.getTen_loai_sach()
                    });
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnthem) {
            formthemtheloai();
        } else if (e.getSource() == btnsua) {
            int row = tbltheloai.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Hãy chọn 1 thể loại để sửa");
                return;
            }
            String maLoai = model.getValueAt(row, 0).toString();
            formsuatheloai(maLoai, row);
        } else if (e.getSource() == btnxoa) {
            int row = tbltheloai.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Hãy chọn 1 thể loại để xóa");
                return;
            }
            String maLoai = model.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Bạn có chắc chắn muốn xóa thể loại này?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (TheLoaiDAO.remove(maLoai)) {
                    loadDataToTable();
                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại");
                }
            }
        }
    }

    private void formthemtheloai() {
        JDialog formthem = new JDialog(this, "Thêm thể loại", true);
        formthem.setSize(400, 200);
        formthem.setLocationRelativeTo(this);
        formthem.setLayout(new BorderLayout(10, 10));

        JPanel pnnhap = new JPanel();
        pnnhap.setLayout(new BoxLayout(pnnhap, BoxLayout.Y_AXIS));
        Dimension labelsize = new Dimension(100, 25);

        JPanel pnma = new JPanel();
        JLabel lblma = new JLabel("Mã loại:");
        lblma.setPreferredSize(labelsize);
        JTextField txtma = new JTextField(20);
        pnma.add(lblma);
        pnma.add(txtma);
        pnnhap.add(pnma);

        JPanel pnten = new JPanel();
        JLabel lblten = new JLabel("Tên loại:");
        lblten.setPreferredSize(labelsize);
        JTextField txtten = new JTextField(20);
        pnten.add(lblten);
        pnten.add(txtten);
        pnnhap.add(pnten);

        formthem.add(pnnhap, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        JButton btnthemmoi = new JButton("Thêm mới");
        pnButton.add(btnthemmoi);
        formthem.add(pnButton, BorderLayout.SOUTH);

        btnthemmoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = txtma.getText().trim();
                String ten = txtten.getText().trim();
                if (ma.isEmpty() || ten.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không được để trống");
                    return;
                }
                TheLoai tl = new TheLoai(ma, ten);
                if (TheLoaiDAO.insert(tl)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadDataToTable();
                    formthem.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            }
        });

        formthem.setVisible(true);
    }

    private void formsuatheloai(String maLoai, int row) {
        TheLoai tl = TheLoaiDAO.findById(maLoai);
        if (tl == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy thể loại trong CSDL!");
            return;
        }

        JDialog formsua = new JDialog(this, "Sửa thể loại", true);
        formsua.setSize(400, 200);
        formsua.setLocationRelativeTo(this);
        formsua.setLayout(new BorderLayout(10, 10));

        JPanel pnnhap = new JPanel();
        pnnhap.setLayout(new BoxLayout(pnnhap, BoxLayout.Y_AXIS));
        Dimension labelsize = new Dimension(100, 25);

        JPanel pnma = new JPanel();
        JLabel lblma = new JLabel("Mã loại:");
        lblma.setPreferredSize(labelsize);
        JTextField txtma = new JTextField(tl.getMa_loai_sach(), 20);
        txtma.setEditable(false);
        pnma.add(lblma);
        pnma.add(txtma);
        pnnhap.add(pnma);

        JPanel pnten = new JPanel();
        JLabel lblten = new JLabel("Tên loại:");
        lblten.setPreferredSize(labelsize);
        JTextField txtten = new JTextField(tl.getTen_loai_sach(), 20);
        pnten.add(lblten);
        pnten.add(txtten);
        pnnhap.add(pnten);
        formsua.add(pnnhap, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        JButton btncapnhat = new JButton("Cập nhật");
        pnButton.add(btncapnhat);
        formsua.add(pnButton, BorderLayout.SOUTH);

        btncapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ma = txtma.getText().trim();
                String ten = txtten.getText().trim();

                TheLoai tlUpdate = new TheLoai(ma, ten);

                if (TheLoaiDAO.update(tlUpdate)) {
                    model.setValueAt(ma, row, 0);
                    model.setValueAt(ten, row, 1);

                    JOptionPane.showMessageDialog(null, "Cập nhật thể loại thành công!");
                    formsua.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thể loại thất bại!");
                }
            }
        });

        formsua.setVisible(true);
    }

}
