/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin_book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.TheLoaiDAO;
import model.TheLoai;
import qltv.AdminGUI;
import dao.UserDAO;

/**
 *
 * @author Admin
 */
public class sachGUI extends JFrame implements ActionListener {
    private JTable tblsach;
    private DefaultTableModel model;
    private JList listdanhmuc;
    private DefaultListModel listmodel;
    private JTextField txttimkiem;
    private JButton btnthem, btnxoa, btnsua;
    private JScrollPane scrollList;

    public sachGUI() {
        initGUI();
        loadDataTable();
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
        this.setTitle("Quản lý sách");
        this.setSize(1300,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel pntieude = new JPanel();
        JLabel lbltieude = new JLabel ("QUẢN LÝ SÁCH");
        lbltieude.setForeground(Color.BLUE);
        lbltieude.setFont(new Font("Arial",Font.BOLD,20));
        pntieude.add(lbltieude);
        this.add(pntieude,BorderLayout.NORTH);
 
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS)); 
        this.add(west, BorderLayout.WEST);
        Border  border = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = new TitledBorder(border ,"Danh mục sách");
        west.setBorder(titledBorder);
        listdanhmuc = new JList();
        listmodel = new DefaultListModel();
        listdanhmuc.setModel(listmodel);
        scrollList = new JScrollPane(listdanhmuc);
        scrollList.setPreferredSize(new Dimension(150, 400));
        west.add(scrollList);
        
        List<TheLoai> list = TheLoaiDAO.getAll();
        for (TheLoai tl : list) {
            listmodel.addElement(tl);
        }

        JPanel pnchinh = new JPanel(new BorderLayout(10,10));
        this.add(pnchinh,BorderLayout.CENTER);
       
        JPanel pnnut = new JPanel();
        JLabel lbltimkiem = new JLabel("Tìm kiếm");
        txttimkiem = new JTextField(20);
        btnthem = new JButton ("Thêm");
        btnsua = new JButton ("Sửa");
        btnxoa = new JButton ("Xóa");
        btnthem.setBackground(Color.LIGHT_GRAY);
        btnsua.setBackground(Color.BLUE);
        btnxoa.setBackground(Color.RED);
        pnnut.add(lbltimkiem); 
        pnnut.add(txttimkiem);
        pnnut.add(btnthem); 
        pnnut.add(btnsua); 
        pnnut.add(btnxoa);
        pnchinh.add(pnnut,BorderLayout.NORTH);

        String[] columnName = {"Mã sách","Tên sách","Loại sách","Tác giả","NXB","Năm XB","Số lượng","Tình trạng","Hình ảnh"};
        model = new DefaultTableModel(columnName, 0); 
        tblsach = new JTable(model);
        tblsach.setRowHeight(25);
        tblsach.setFont(new Font("Arial", Font.PLAIN, 14));
        tblsach.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tblsach.getColumnCount(); i++) {
            tblsach.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(tblsach);
        pnchinh.add(scrollPane,BorderLayout.CENTER);
    
    }
    private void loadDataTable() {
        model.setRowCount(0);
        List<Sach> listSach = sachDAO.getAll();
        for (Sach s : listSach) {
            String tinhTrangText = s.isTinh_trang() ? "Còn" : "Hết";
            Object imageCell;
            String imageName = s.getImage();
            if (imageName == null || imageName.trim().isEmpty()) {
                imageCell = "Không có ảnh";
            } else {
                String imagePath = "/images/" + imageName;
                URL imageUrl = getClass().getResource(imagePath);

                if (imageUrl == null) {
                    imageCell = "Không tìm thấy ảnh";
                } else {
                    ImageIcon icon = new ImageIcon(imageUrl); 
                    Image img = icon.getImage().getScaledInstance(60, 70, Image.SCALE_SMOOTH);
                    imageCell = new ImageIcon(img);
                }

            }
            Object[] row = {
                s.getMa_sach(),
                s.getTen_sach(),
                s.getTen_loai_sach(),
                s.getTen_tg(),
                s.getNha_xb(),
                s.getNam_xb(),
                s.getSo_luong(),
                tinhTrangText,
                imageCell
            };
            model.addRow(row);
        }
        tblsach.setRowHeight(80);
        tblsach.getColumnModel().getColumn(8).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value instanceof ImageIcon) {
                    setIcon((ImageIcon) value); 
                    setText("");                
                } else {
                    setIcon(null);
                    setText(value == null ? "" : value.toString()); 

                }
            }
        });
}
    private void addAction() {
        btnthem.addActionListener(this);
        btnsua.addActionListener(this);
        btnxoa.addActionListener(this);
        txttimkiem.addActionListener(this);
        listdanhmuc.addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            TheLoai selected = (TheLoai) listdanhmuc.getSelectedValue();
        if (selected != null) {
            loadDataByTheLoai(selected.getMa_loai_sach());
        }
        }
    });

}
    private void loadDataByTheLoai(String ma_loai) {
    model.setRowCount(0); 

    List<Sach> listSach = sachDAO.getByTheLoai(ma_loai);
    for (Sach s : listSach) {
        String tinhTrangText = s.isTinh_trang() ? "Còn" : "Hết";

        Object imageCell;
        String imageName = s.getImage();
        if (imageName == null || imageName.trim().isEmpty()) {
            imageCell = "Không có ảnh";
        } else {
            String imagePath = "/images/" + imageName;
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                imageCell = "Không tìm thấy ảnh";
            } else {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image img = icon.getImage().getScaledInstance(60, 70, Image.SCALE_SMOOTH);
                imageCell = new ImageIcon(img);
            }
        }

        Object[] row = {
            s.getMa_sach(),
            s.getTen_sach(),
            s.getTen_loai_sach(),
            s.getTen_tg(),
            s.getNha_xb(),
            s.getNam_xb(),
            s.getSo_luong(),
            tinhTrangText,
            imageCell
        };
        model.addRow(row);
    }
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== btnthem){
            formthemsach();
        }else if(e.getSource() == btnsua){
            int row = tblsach.getSelectedRow(); 
            if(row == -1){ 
                JOptionPane.showMessageDialog(null,"Hãy chọn 1 sách để sửa"); 
                return; 
            } 
            String masach =model.getValueAt(row, 0).toString(); 
            formsuasach(masach,row);
            
        }else if(e.getSource() == btnxoa){
        int row = tblsach.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(null,"Hãy chọn 1 sách để xóa");
            return;
        }
        String maSach = model.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Bạn có chắc muốn xóa sách ? ",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );

        if(confirm == JOptionPane.YES_OPTION){
            if(sachDAO.remove(maSach)){
                JOptionPane.showMessageDialog(null,"Xóa sách thành công!");
                loadDataTable(); 
            } else {
                JOptionPane.showMessageDialog(null,"Xóa thất bại");
            }
        }
        }else if(e.getSource() == txttimkiem){
            String keyword = txttimkiem.getText().trim();
            List<Sach> listSearch = sachDAO.search(keyword);
            model.setRowCount(0);
            for(Sach s : listSearch){
                String tinhTrangText = s.isTinh_trang() ? "Còn" : "Hết";
                Object[] row = {
                    s.getMa_sach(),
                    s.getTen_sach(),
                    s.getTen_loai_sach(),
                    s.getTen_tg(),
                    s.getNha_xb(),
                    s.getNam_xb(),
                    s.getSo_luong(),
                    tinhTrangText,
                    s.getImage()
                };
                model.addRow(row);
            }
        }
    }
    private void formthemsach() {
        JDialog formthem = new JDialog(this, "Thêm sách", true);
        formthem.setSize(600, 500);
        formthem.setLocationRelativeTo(this);
        formthem.setLayout(new BorderLayout(10, 10));

        JPanel pninput = new JPanel();
        pninput.setLayout(new BoxLayout(pninput, BoxLayout.Y_AXIS));
        Dimension labelsize = new Dimension(120, 25);

        JPanel pnma = new JPanel();
        JLabel lblma = new JLabel("Mã sách:");
        lblma.setPreferredSize(labelsize);
        JTextField txtma = new JTextField(20);
        pnma.add(lblma); pnma.add(txtma);
        pninput.add(pnma);

        JPanel pnten = new JPanel();
        JLabel lblten = new JLabel("Tên sách:");
        lblten.setPreferredSize(labelsize);
        JTextField txtten = new JTextField(20);
        pnten.add(lblten); pnten.add(txtten);
        pninput.add(pnten);

        JPanel pnloai = new JPanel();
        JLabel lblloai = new JLabel("Loại sách:");
        lblloai.setPreferredSize(labelsize);
        JComboBox<String> cboloai = new JComboBox<>();
        cboloai.setPreferredSize(new Dimension(225, 25));
        cboloai.setEditable(true); 
        cboloai.setSelectedItem("Chọn loại sách");
        cboloai.setEditable(false);
        for (TheLoai ls : TheLoaiDAO.getAll()) {
            cboloai.addItem(ls.getTen_loai_sach());
        }
        pnloai.add(lblloai); 
        pnloai.add(cboloai);
        pninput.add(pnloai);

        JPanel pntg = new JPanel();
        JLabel lbltg = new JLabel("Tác giả:");
        lbltg.setPreferredSize(labelsize);
        JComboBox<String> cbotg = new JComboBox<>();
        cbotg.setPreferredSize(new Dimension(225, 25));
        cbotg.setEditable(true);
        cbotg.setSelectedItem("Chọn tác giả");
        cbotg.setEditable(false);
        for (tacgia tg : tacgiaDAO.getAll()) {
            cbotg.addItem(tg.getTen_tg());
        }
        pntg.add(lbltg); 
        pntg.add(cbotg);
        pninput.add(pntg);

        JPanel pnxb = new JPanel();
        JLabel lblnxb = new JLabel("NXB:");
        lblnxb.setPreferredSize(labelsize);
        JTextField txtnxb = new JTextField(20);
        pnxb.add(lblnxb); pnxb.add(txtnxb);
        pninput.add(pnxb);

        JPanel pnnxb = new JPanel();
        JLabel lblnam = new JLabel("Năm XB:");
        lblnam.setPreferredSize(labelsize);
        JTextField txtnam = new JTextField(20);
        pnnxb.add(lblnam); pnnxb.add(txtnam);
        pninput.add(pnnxb);

        JPanel pnsl = new JPanel();
        JLabel lblsl = new JLabel("Số lượng:");
        lblsl.setPreferredSize(labelsize);
        JTextField txtsl = new JTextField("0", 20);
        pnsl.add(lblsl); pnsl.add(txtsl);
        pninput.add(pnsl);

        JPanel pntt = new JPanel();
        JLabel lbltt = new JLabel("Tình trạng:");
        lbltt.setPreferredSize(labelsize);
        JComboBox<String> cbotinhtrang = new JComboBox<>(new String[]{"Còn", "Hết"});
        cbotinhtrang.setPreferredSize(new Dimension(225, 25));
        cbotinhtrang.setSelectedItem("Hết");
        pntt.add(lbltt); pntt.add(cbotinhtrang);
        pninput.add(pntt);

        txtsl.addCaretListener(e -> {
            try {
                int sl = Integer.parseInt(txtsl.getText().trim());
                cbotinhtrang.setSelectedItem(sl > 0 ? "Còn" : "Hết");
            } catch (NumberFormatException ex) {
                cbotinhtrang.setSelectedItem("Hết");
            }
        });

        JPanel pnanh = new JPanel();
        JButton btnchonanh = new JButton("Chọn ảnh");
        btnchonanh.setPreferredSize(labelsize);
        JTextField txtanh = new JTextField(20);
        pnanh.add(btnchonanh);
        pnanh.add(txtanh); 
        pninput.add(pnanh);

        JPanel pnmt = new JPanel();
        JLabel lblmt = new JLabel("Mô tả:");
        lblmt.setPreferredSize(labelsize);
        JTextArea txtmota = new JTextArea(5, 20);
        txtmota.setLineWrap(true);
        txtmota.setWrapStyleWord(true);
        JScrollPane scrollMota = new JScrollPane(txtmota);
        scrollMota.setPreferredSize(new Dimension(225, 80));
        pnmt.add(lblmt); pnmt.add(scrollMota);
        pninput.add(pnmt);


        formthem.add(pninput, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        JButton btnthemmoi = new JButton("Thêm mới");
        pnButton.add(btnthemmoi);
        formthem.add(pnButton, BorderLayout.SOUTH);

        btnchonanh.addActionListener(e -> {
            JFileChooser fileanh = new JFileChooser("images");
            if (fileanh.showOpenDialog(formthem) == JFileChooser.APPROVE_OPTION) {
                txtanh.setText(fileanh.getSelectedFile().getName());
            }
        });

        btnthemmoi.addActionListener(e -> {
            String ma = txtma.getText().trim();
            String ten = txtten.getText().trim();
            String loaiTen = cboloai.getSelectedItem().toString();
            String tgTen = cbotg.getSelectedItem().toString();
            String nxb = txtnxb.getText().trim();
            int nam = Integer.parseInt(txtnam.getText().trim());
            int sl = Integer.parseInt(txtsl.getText().trim());
            boolean tinhtrang = cbotinhtrang.getSelectedItem().toString().equals("Còn");
            String hinh = txtanh.getText().trim();
            String mota = txtmota.getText().trim();

            String ma_loai = TheLoaiDAO.getMaByTen(loaiTen);
            int ma_tg = tacgiaDAO.getMaByTen(tgTen);         

            Sach s = new Sach(ma, ten, ma_loai, ma_tg, nxb, nam, sl, tinhtrang, mota, hinh, loaiTen, tgTen);

            if (sachDAO.insert(s)) {
                JOptionPane.showMessageDialog(null, "Thêm sách thành công!");
                loadDataTable();
                formthem.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Thêm sách thất bại!");
            }
        });

        formthem.setVisible(true);
}
    private void formsuasach(String masach, int row) {
        Sach s = sachDAO.findById(masach);
        if (s == null) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy sách trong CSDL!");
            return;
        }

        JDialog formsua = new JDialog(this, "Sửa sách", true);
        formsua.setSize(600, 500);
        formsua.setLocationRelativeTo(this);
        formsua.setLayout(new BorderLayout(10, 10));

        JPanel pninput = new JPanel();
        pninput.setLayout(new BoxLayout(pninput, BoxLayout.Y_AXIS));
        Dimension labelsize = new Dimension(120, 25);

        JPanel pnma = new JPanel();
        JLabel lblma = new JLabel("Mã sách:");
        lblma.setPreferredSize(labelsize);
        JTextField txtma = new JTextField(s.getMa_sach(), 20);
        txtma.setEditable(false);
        pnma.add(lblma); pnma.add(txtma);
        pninput.add(pnma);

        JPanel pnten = new JPanel();
        JLabel lblten = new JLabel("Tên sách:");
        lblten.setPreferredSize(labelsize);
        JTextField txtten = new JTextField(s.getTen_sach(), 20);
        pnten.add(lblten); pnten.add(txtten);
        pninput.add(pnten);

        JPanel pnloai = new JPanel();
        JLabel lblloai = new JLabel("Loại sách:");
        lblloai.setPreferredSize(labelsize);
        JComboBox<String> cboloai = new JComboBox<>();
        cboloai.setPreferredSize(new Dimension(225, 25));
        for (TheLoai ls : TheLoaiDAO.getAll()) {
            cboloai.addItem(ls.getTen_loai_sach());
        }
        cboloai.setSelectedItem(s.getTen_loai_sach());
        pnloai.add(lblloai); pnloai.add(cboloai);
        pninput.add(pnloai);

        JPanel pntg = new JPanel();
        JLabel lbltg = new JLabel("Tác giả:");
        lbltg.setPreferredSize(labelsize);
        JComboBox<String> cbotg = new JComboBox<>();
        cbotg.setPreferredSize(new Dimension(225, 25));
        for (tacgia tg : tacgiaDAO.getAll()) {
            cbotg.addItem(tg.getTen_tg());
        }
        cbotg.setSelectedItem(s.getTen_tg());
        pntg.add(lbltg); pntg.add(cbotg);
        pninput.add(pntg);

        JPanel pnxb = new JPanel();
        JLabel lblnxb = new JLabel("NXB:");
        lblnxb.setPreferredSize(labelsize);
        JTextField txtnxb = new JTextField(s.getNha_xb(), 20);
        pnxb.add(lblnxb); pnxb.add(txtnxb);
        pninput.add(pnxb);

        JPanel pnnam = new JPanel();
        JLabel lblnam = new JLabel("Năm XB:");
        lblnam.setPreferredSize(labelsize);
        JTextField txtnam = new JTextField(String.valueOf(s.getNam_xb()), 20);
        pnnam.add(lblnam); pnnam.add(txtnam);
        pninput.add(pnnam);

        JPanel pnsl = new JPanel();
        JLabel lblsl = new JLabel("Số lượng:");
        lblsl.setPreferredSize(labelsize);
        JTextField txtsl = new JTextField(String.valueOf(s.getSo_luong()), 20);
        pnsl.add(lblsl); pnsl.add(txtsl);
        pninput.add(pnsl);

        JPanel pntt = new JPanel();
        JLabel lbltt = new JLabel("Tình trạng:");
        lbltt.setPreferredSize(labelsize);
        JComboBox<String> cbotinhtrang = new JComboBox<>(new String[]{"Còn", "Hết"});
        cbotinhtrang.setPreferredSize(new Dimension(225, 25));
        cbotinhtrang.setSelectedItem(s.isTinh_trang() ? "Còn" : "Hết");
        pntt.add(lbltt); pntt.add(cbotinhtrang);
        pninput.add(pntt);

        txtsl.addCaretListener(e -> {
            try {
                int sl = Integer.parseInt(txtsl.getText().trim());
                cbotinhtrang.setSelectedItem(sl > 0 ? "Còn" : "Hết");
            } catch (NumberFormatException ex) {
                cbotinhtrang.setSelectedItem("Hết");
            }
        });

        JPanel pnmt = new JPanel();
        JLabel lblmt = new JLabel("Mô tả:");
        lblmt.setPreferredSize(labelsize);
        JTextArea txtmota = new JTextArea(s.getMo_ta(), 5, 20);
        txtmota.setLineWrap(true);
        txtmota.setWrapStyleWord(true);
        JScrollPane scrollMota = new JScrollPane(txtmota);
        scrollMota.setPreferredSize(new Dimension(225, 80));
        pnmt.add(lblmt); pnmt.add(scrollMota);
        pninput.add(pnmt);

        JPanel pnanh = new JPanel();
        JButton btnchonanh = new JButton("Chọn ảnh");
        JTextField txtanh = new JTextField(s.getImage(), 20);
        pnanh.add(btnchonanh);
        pnanh.add(txtanh);
        pninput.add(pnanh);

        formsua.add(pninput, BorderLayout.CENTER);

        JPanel pnButton = new JPanel();
        JButton btncapnhat = new JButton("Cập nhật");
        pnButton.add(btncapnhat);
        formsua.add(pnButton, BorderLayout.SOUTH);

        btnchonanh.addActionListener(e -> {
            JFileChooser fileanh = new JFileChooser("/images");
            if (fileanh.showOpenDialog(formsua) == JFileChooser.APPROVE_OPTION) {
                txtanh.setText(fileanh.getSelectedFile().getName());
            }
        });

        btncapnhat.addActionListener(e -> {
            String tenMoi = txtten.getText().trim();
            String loaiTenMoi = cboloai.getSelectedItem().toString();
            String tgTenMoi = cbotg.getSelectedItem().toString();
            String nxbMoi = txtnxb.getText().trim();
            int namMoi = Integer.parseInt(txtnam.getText().trim());
            int slMoi = Integer.parseInt(txtsl.getText().trim());
            boolean tinhtrangMoi = cbotinhtrang.getSelectedItem().toString().equals("Còn");
            String hinhMoi = txtanh.getText().trim();
            String motaMoi = txtmota.getText().trim();
            String ma_loai = TheLoaiDAO.getMaByTen(loaiTenMoi);
            int ma_tg = tacgiaDAO.getMaByTen(tgTenMoi);

            Sach suaSach = new Sach( masach, tenMoi, ma_loai, ma_tg, nxbMoi, namMoi, slMoi, tinhtrangMoi, motaMoi, hinhMoi, loaiTenMoi, tgTenMoi );

            if (sachDAO.update(suaSach)) {
                JOptionPane.showMessageDialog(null, "Cập nhật sách thành công!");
                loadDataTable();
                formsua.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật sách thất bại!");
            }
        });

        formsua.setVisible(true);
}

     public static void main(String[] args) {
         sachGUI sachgui = new sachGUI();
     }

    

    

    

    

    

    
}
