/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin_book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import qltv.AdminGUI;
import dao.UserDAO;

public class tacgiaGUI extends JFrame implements ActionListener {
    private JTable tbltacgia;
    private DefaultTableModel model;
    private JButton btnthem, btnsua, btnxoa;
    private JTextField txttimkiem;

    public tacgiaGUI() {
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
        this.setTitle("Quản lý tác giả");
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel pntieude = new JPanel();
        JLabel lbltieude = new JLabel("QUẢN LÝ TÁC GIẢ");
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

        String[] columnName = { "Mã tác giả", "Tên tác giả", "Ngày sinh", "Giới tính", "Quê" };
        model = new DefaultTableModel(columnName, 0);
        tbltacgia = new JTable(model);
        tbltacgia.setRowHeight(25);
        tbltacgia.setFont(new Font("Arial", Font.PLAIN, 14));
        tbltacgia.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tbltacgia.getColumnCount(); i++) {
            tbltacgia.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(tbltacgia);
        pnchinh.add(scrollPane, BorderLayout.CENTER);
    }

    private void loadDataToTable() {
        model.setRowCount(0);
        List<tacgia> list = tacgiaDAO.getAll();
        for (tacgia tg : list) {
            model.addRow(new Object[] {
                    tg.getMa_tg(),
                    tg.getTen_tg(),
                    tg.getNgay_sinh(),
                    tg.getGioi_tinh(),
                    tg.getQue(),
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
                    loadDataToTable(); // nếu rỗng thì load lại toàn bộ
                    return;
                }
                List<tacgia> list = tacgiaDAO.searchByName(keyword);
                model.setRowCount(0);
                for (tacgia tg : list) {
                    model.addRow(new Object[] {
                            tg.getMa_tg(),
                            tg.getTen_tg(),
                            tg.getNgay_sinh(),
                            tg.getGioi_tinh(),
                            tg.getQue()
                    });
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnthem) {
            formthemtacgia();
        } else if (e.getSource() == btnsua) {
            int row = tbltacgia.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Hãy chọn 1 tác giả để sửa");
                return;
            } // Lấy mã tác giả từ cột đầu tiên của bảng
            int matg = Integer.parseInt(model.getValueAt(row, 0).toString());
            formsuatacgia(matg, row);

        } else if (e.getSource() == btnxoa) {
            int row = tbltacgia.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Hãy chọn 1 tác giả để xóa");
                return;
            }
            int matg = Integer.parseInt(model.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa tác giả này?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (tacgiaDAO.remove(matg)) {
                    model.removeRow(row);
                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại");
                }
            }
        }

    }

    private void formthemtacgia() {
        JDialog formthem = new JDialog(this, "Thêm tác giả", true);
        formthem.setSize(500, 450);
        formthem.setLocationRelativeTo(this);
        formthem.setLayout(new BorderLayout(10, 10));

        JPanel pnnorth = new JPanel();
        JLabel lbltieude = new JLabel("Thêm thông tin tác giả");
        lbltieude.setForeground(Color.BLUE);
        lbltieude.setFont(new Font("Arial", Font.BOLD, 20));
        pnnorth.add(lbltieude);
        formthem.add(pnnorth, BorderLayout.NORTH);

        JPanel pnnhap = new JPanel();
        pnnhap.setLayout(new BoxLayout(pnnhap, BoxLayout.Y_AXIS));

        Dimension labelsize = new Dimension(100, 25);

        JPanel pnten = new JPanel();
        JLabel lblten = new JLabel("Tên tác giả:");
        lblten.setPreferredSize(labelsize);
        JTextField txtten = new JTextField(20);
        pnten.add(lblten);
        pnten.add(txtten);
        pnnhap.add(pnten);

        JPanel pnngaysinh = new JPanel();
        JLabel lblngaysinh = new JLabel("Ngày sinh:");
        lblngaysinh.setPreferredSize(labelsize);
        JTextField txtngaysinh = new JTextField(20);
        pnngaysinh.add(lblngaysinh);
        pnngaysinh.add(txtngaysinh);
        pnnhap.add(pnngaysinh);

        JPanel pngioitinh = new JPanel();
        JLabel lblgioitinh = new JLabel("Giới tính:");
        lblgioitinh.setPreferredSize(labelsize);
        JComboBox<String> cbogioitinh = new JComboBox<>();
        cbogioitinh.addItem("Chọn giới tính");
        cbogioitinh.addItem("Nam");
        cbogioitinh.addItem("Nữ");
        cbogioitinh.setSelectedIndex(0);
        cbogioitinh.setPreferredSize(new Dimension(225, 25));
        pngioitinh.add(lblgioitinh);
        pngioitinh.add(cbogioitinh);
        pnnhap.add(pngioitinh);

        JPanel pnque = new JPanel();
        JLabel lblque = new JLabel("Quê:");
        lblque.setPreferredSize(labelsize);
        JTextField txtque = new JTextField(20);
        pnque.add(lblque);
        pnque.add(txtque);
        pnnhap.add(pnque);

        JPanel pntieusu = new JPanel();
        JLabel lbltieusu = new JLabel("Tiểu sử:");
        lbltieusu.setPreferredSize(labelsize);
        JTextArea txttieusu = new JTextArea(3, 20);
        JScrollPane sptieusu = new JScrollPane(txttieusu);
        pntieusu.add(lbltieusu);
        pntieusu.add(sptieusu);
        pnnhap.add(pntieusu);

        JPanel pnanh = new JPanel();
        JButton btnchonanh = new JButton("Chọn ảnh");
        btnchonanh.setPreferredSize(labelsize);
        JTextField txtanh = new JTextField(20);
        pnanh.add(btnchonanh);
        pnanh.add(txtanh);
        pnnhap.add(pnanh);

        formthem.add(pnnhap, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        JButton btnthemmoi = new JButton("Thêm mới");
        btnthemmoi.setFont(new Font("Arial", Font.BOLD, 14));
        pnButton.add(btnthemmoi);
        formthem.add(pnButton, BorderLayout.SOUTH);

        btnchonanh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileanh = new JFileChooser("images");
                int result = fileanh.showOpenDialog(formthem);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File chonfile = fileanh.getSelectedFile();
                    txtanh.setText(chonfile.getName());
                }
            }
        });
        btnthemmoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ten = txtten.getText().trim();
                String ngaysinh = txtngaysinh.getText().trim();
                String gioitinh = cbogioitinh.getSelectedItem().toString();
                String que = txtque.getText().trim();
                String tieusu = txttieusu.getText().trim();
                String hinh = txtanh.getText().trim();
                if (ten.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tên không được để trống");
                    return;
                }
                tacgia tg = new tacgia(ten, ngaysinh, gioitinh, que, tieusu, hinh);
                if (tacgiaDAO.insert(tg)) {
                    model.addRow(new Object[] { ten, ngaysinh, gioitinh, que });
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

    private void formsuatacgia(int matg, int row) {

        tacgia tg = tacgiaDAO.findById(matg);
        if (tg == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy tác giả trong CSDL!");
            return;
        }

        String ten = tg.getTen_tg();
        String ngaysinh = tg.getNgay_sinh();
        String gioitinh = tg.getGioi_tinh();
        String que = tg.getQue();
        String tieusu = tg.getTieu_su();
        String hinh = tg.getHinh();

        JDialog formsua = new JDialog(this, "Sửa tác giả", true);
        formsua.setSize(500, 450);
        formsua.setLocationRelativeTo(this);
        formsua.setLayout(new BorderLayout(10, 10));

        JPanel pnnhap = new JPanel();
        pnnhap.setLayout(new BoxLayout(pnnhap, BoxLayout.Y_AXIS));
        Dimension labelsize = new Dimension(100, 25);

        JPanel pnten = new JPanel();
        JLabel lblten = new JLabel("Tên tác giả:");
        lblten.setPreferredSize(labelsize);
        JTextField txtten = new JTextField(ten, 20);
        txtten.setEditable(false);
        pnten.add(lblten);
        pnten.add(txtten);
        pnnhap.add(pnten);

        JPanel pnngaysinh = new JPanel();
        JLabel lblngaysinh = new JLabel("Ngày sinh:");
        lblngaysinh.setPreferredSize(labelsize);
        JTextField txtngaysinh = new JTextField(ngaysinh, 20);
        pnngaysinh.add(lblngaysinh);
        pnngaysinh.add(txtngaysinh);
        pnnhap.add(pnngaysinh);

        JPanel pngioitinh = new JPanel();
        JLabel lblgioitinh = new JLabel("Giới tính:");
        lblgioitinh.setPreferredSize(labelsize);
        JComboBox<String> cbogioitinh = new JComboBox<>();
        cbogioitinh.setPreferredSize(new Dimension(225, 25));
        cbogioitinh.addItem("Nam");
        cbogioitinh.addItem("Nữ");
        cbogioitinh.setSelectedItem(gioitinh);
        pngioitinh.add(lblgioitinh);
        pngioitinh.add(cbogioitinh);
        pnnhap.add(pngioitinh);

        JPanel pnque = new JPanel();
        JLabel lblque = new JLabel("Quê:");
        lblque.setPreferredSize(labelsize);
        JTextField txtque = new JTextField(que, 20);
        pnque.add(lblque);
        pnque.add(txtque);
        pnnhap.add(pnque);

        JPanel pntieusu = new JPanel();
        JLabel lbltieusu = new JLabel("Tiểu sử:");
        lbltieusu.setPreferredSize(labelsize);
        JTextArea txttieusu = new JTextArea(tieusu, 3, 20);
        JScrollPane sptieusu = new JScrollPane(txttieusu);
        pntieusu.add(lbltieusu);
        pntieusu.add(sptieusu);
        pnnhap.add(pntieusu);

        JPanel pnanh = new JPanel();
        JButton btnchonanh = new JButton("Chọn ảnh");
        JTextField txtanh = new JTextField(hinh, 20);
        pnanh.add(btnchonanh);
        pnanh.add(txtanh);
        pnnhap.add(pnanh);

        formsua.add(pnnhap, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        JButton btncapnhat = new JButton("Cập nhật");
        pnButton.add(btncapnhat);
        formsua.add(pnButton, BorderLayout.SOUTH);

        btnchonanh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileanh = new JFileChooser("images");
                int result = fileanh.showOpenDialog(formsua);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File chonfile = fileanh.getSelectedFile();
                    txtanh.setText(chonfile.getName());
                }
            }
        });

        btncapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ten = txtten.getText().trim();
                String ngaysinh = txtngaysinh.getText().trim();
                String gioitinh = cbogioitinh.getSelectedItem().toString();
                String que = txtque.getText().trim();
                String tieusu = txttieusu.getText().trim();
                String hinh = txtanh.getText().trim();

                tacgia tgUpdate = new tacgia(matg, ten, ngaysinh, gioitinh, que, tieusu, hinh);

                if (tacgiaDAO.update(tgUpdate)) {
                    model.setValueAt(matg, row, 0);
                    model.setValueAt(ten, row, 1);
                    model.setValueAt(ngaysinh, row, 2);
                    model.setValueAt(gioitinh, row, 3);
                    model.setValueAt(que, row, 4);

                    JOptionPane.showMessageDialog(null, "Cập nhật tác giả thành công!");
                    formsua.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật tác giả thất bại!");
                }
            }
        });

        formsua.setVisible(true);
    }

}
