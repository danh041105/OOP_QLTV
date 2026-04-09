package QuanLyTKSV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import qltv.giaodiensv;
import qltv.userDAO;

public class taikhoansvGUI extends JFrame {

    private String loggedInUser;
    JPanel pnlMain = new JPanel(new BorderLayout());

    public taikhoansvGUI(String username) {
        super("Sinh viên " + username);
        this.loggedInUser = username;
        initUI();

    }

    public taikhoansvGUI() {
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
                userDAO dao = new userDAO();
                dispose();
                new giaodiensv(dao.getMSV_isLogin()).setVisible(true);
            }
        });
    }

    public void doShow() {
        setVisible(true);
    }

    private JMenuBar createMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu mnuQL = new JMenu("Quản lý tài khoản");
        JMenuItem mnuSV = new JMenuItem("Tài khoản Sinh viên");

        mnuQL.add(mnuSV);
        bar.add(mnuQL);

        return bar;
    }

    private void showSinhVien() {
        pnlMain.removeAll();
        pnlMain.add(new svPanel(loggedInUser), BorderLayout.CENTER);
        pnlMain.revalidate();
        pnlMain.repaint();
    }
}
