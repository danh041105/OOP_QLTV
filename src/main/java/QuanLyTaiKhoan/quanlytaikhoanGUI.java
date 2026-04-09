package QuanLyTaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import qltv.giaodienadmin;
import qltv.giaodiensv;
import qltv.userDAO;

public class quanlytaikhoanGUI extends JFrame {

    JPanel pnlMain = new JPanel(new BorderLayout());

    public quanlytaikhoanGUI(String title) {
        super(title);
        initUI();
    }

    public quanlytaikhoanGUI() {
        this("Quản lý tài khoản");
    }
    private void initUI() {
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setJMenuBar(createMenu());
        add(pnlMain);
        showSinhVien();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                userDAO dao = new userDAO();
                new giaodienadmin(dao.getMaADMIN_isLogin()).setVisible(true);
            }
        });
    }

    public void doShow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        new quanlytaikhoanGUI().doShow();
    }

    private JMenuBar createMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu mnuQL = new JMenu("Quản lý tài khoản");

        JMenuItem mnuAdmin = new JMenuItem("Tài khoản Admin");
        JMenuItem mnuSV = new JMenuItem("Tài khoản Sinh viên");
        
        mnuAdmin.addActionListener(e -> showAdmin());
        mnuSV.addActionListener(e -> showSinhVien());

        mnuQL.add(mnuAdmin);
        mnuQL.add(mnuSV);
        bar.add(mnuQL);

        return bar;
    }

    public void showAdmin() {
        pnlMain.removeAll();
        pnlMain.add(new adminPanel(), BorderLayout.CENTER);
        pnlMain.revalidate();
        pnlMain.repaint();
    }

    public void showSinhVien() {
        pnlMain.removeAll();
        pnlMain.add(new sinhvienPanel(), BorderLayout.CENTER);
        pnlMain.revalidate();
        pnlMain.repaint();
    }
}
