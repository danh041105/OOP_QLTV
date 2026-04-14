package gui;

import model.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import dao.SinhVienDAO;
import quanlytaikhoan.AdminPanel;
import quanlytaikhoan.SinhVienPanel;

public class QuanLyTaiKhoanGUI extends JFrame {

    JPanel pnlMain = new JPanel(new BorderLayout());

    public QuanLyTaiKhoanGUI() {
        this("Hồ sơ người dùng");
    }

    public QuanLyTaiKhoanGUI(String title) {
        super(title);
        initUI();
    }

    private void initUI() {
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Phân quyền giao diện
        if (SessionManager.currentUser != null && SessionManager.currentUser.getRole() == 0) {
            setJMenuBar(createMenu());
            showSinhVien(); // Mặc định admin xem danh sách SV
        } else {
            showProfile(); // Sinh viên xem profile chính mình
        }

        add(pnlMain);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });
    }

    private void goBack() {
        dispose();
        if (SessionManager.currentUser != null && SessionManager.currentUser.getRole() == 0) {
            new AdminGUI(SessionManager.currentUser.getUsername()).setVisible(true);
        } else if (SessionManager.currentUser != null) {
            new SinhVienGUI(SessionManager.currentUser.getUsername()).setVisible(true);
        }
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

    public void showProfile() {
        pnlMain.removeAll();
        SinhVienDAO svDAO = new SinhVienDAO();
        model.SinhVien sv = svDAO.getByUserId(SessionManager.currentUser.getId());
        pnlMain.add(new SinhVienPanel(sv), BorderLayout.CENTER);
        pnlMain.revalidate();
        pnlMain.repaint();
    }
}
