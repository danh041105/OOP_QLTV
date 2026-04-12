package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import dao.UserDAO;
import quanlytaikhoan.AdminPanel;
import quanlytaikhoan.SinhVienPanel;

public class QuanLyTaiKhoanGUI extends JFrame {

    JPanel pnlMain = new JPanel(new BorderLayout());

    public QuanLyTaiKhoanGUI(String title) {
        super(title);
        initUI();
    }

    public QuanLyTaiKhoanGUI() {
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
                UserDAO dao = new UserDAO();
                new AdminGUI(dao.getMaADMIN_isLogin()).setVisible(true);
            }
        });
    }

    public void doShow() {
        setVisible(true);
    }

    public static void main(String[] args) {
        new QuanLyTaiKhoanGUI().doShow();
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
        pnlMain.add(new AdminPanel(), BorderLayout.CENTER);
        pnlMain.revalidate();
        pnlMain.repaint();
    }

    public void showSinhVien() {
        pnlMain.removeAll();
        pnlMain.add(new SinhVienPanel(), BorderLayout.CENTER);
        pnlMain.revalidate();
        pnlMain.repaint();
    }
}
