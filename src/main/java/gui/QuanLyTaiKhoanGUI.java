package gui;

import model.SessionManager;
import utils.ThemeUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        getContentPane().setBackground(ThemeUtils.BG_MAIN);

        // Phân quyền giao diện
        if (SessionManager.currentUser != null && SessionManager.currentUser.getRole() == 0) {
            // Admin mode: styled menu bar
            setJMenuBar(createStyledMenu());
            pnlMain.setBackground(ThemeUtils.BG_MAIN);
            showSinhVien();
        } else {
            pnlMain.setBackground(ThemeUtils.BG_MAIN);
            showProfile();
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

    private JMenuBar createStyledMenu() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(ThemeUtils.BG_CARD);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ThemeUtils.BORDER));

        JMenu mnuQL = new JMenu("  Quản lý tài khoản  ");
        mnuQL.setFont(ThemeUtils.FONT_BODY_BOLD);
        mnuQL.setForeground(ThemeUtils.TEXT_PRIMARY);
        mnuQL.setBackground(ThemeUtils.BG_CARD);
        mnuQL.setBorderPainted(false);
        mnuQL.setOpaque(true);

        JMenuItem mnuAdmin = new JMenuItem("  Tài khoản Admin  ");
        mnuAdmin.setFont(ThemeUtils.FONT_BODY);
        mnuAdmin.setForeground(ThemeUtils.TEXT_PRIMARY);
        mnuAdmin.setBackground(ThemeUtils.BG_CARD);
        mnuAdmin.setBorder(new EmptyBorder(8, 20, 8, 20));

        JMenuItem mnuSV = new JMenuItem("  Tài khoản Sinh viên  ");
        mnuSV.setFont(ThemeUtils.FONT_BODY);
        mnuSV.setForeground(ThemeUtils.TEXT_PRIMARY);
        mnuSV.setBackground(ThemeUtils.BG_CARD);
        mnuSV.setBorder(new EmptyBorder(8, 20, 8, 20));

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
