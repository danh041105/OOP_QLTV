package controller;

import dao.LoaiSachDAO;
import dao.SachDAO;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.LoaiSach;
import model.Sach;
import view.LoaiSachView;
import view.SachListView;

/**
 * @author ADMIN
 */
public class LoaiSachController {

    private LoaiSachDAO loaiSachDAO;
    private SachDAO sachDAO;
    private LoaiSachView view;

    public LoaiSachController(LoaiSachView view) {
        this.view = view;
        this.loaiSachDAO = new LoaiSachDAO();
        this.sachDAO = new SachDAO();
    }

    public void loadData() {
        try {
            List<LoaiSach> danhSach = loaiSachDAO.getAll();
            displayGrid(danhSach);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi khi tải dữ liệu thể loại!");
        }
    }

    private void displayGrid(List<LoaiSach> danhSach) {
        JPanel gridPanel = view.getGridPanel();
        gridPanel.removeAll();

        if (danhSach == null || danhSach.isEmpty()) {
            showEmptyMessage(gridPanel);
        } else {
            for (LoaiSach loaiSach : danhSach) {
                JPanel card = view.createCard(loaiSach);
                gridPanel.add(card);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void showEmptyMessage(JPanel panel) {
        JLabel lblEmpty = new JLabel("Không có thể loại nào cả");
        lblEmpty.setFont(new Font("Arial", Font.ITALIC, 16));
        lblEmpty.setForeground(Color.GRAY);
        panel.add(lblEmpty);
    }

    public void viewBooksByCategory(LoaiSach loaiSach) {
        try {
            String maLoai = loaiSach.getMaLoaiSach();
            String tenLoai = loaiSach.getTenLoaiSach();

            List<Sach> danhSachSach = sachDAO.getByLoaiSach(maLoai);

            if (danhSachSach.isEmpty()) {
                showMessage("Thể loại này chưa có sách nào!");
                return;
            }

            // view ds sach
            SachListView sachListView = new SachListView();
            sachListView.setLoaiSach(maLoai, tenLoai);
            sachListView.doShow();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi khi xem sách theo thể loại!");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(view, message);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}