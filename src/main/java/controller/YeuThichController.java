/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.SachDAO;
import dao.YeuThichDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import model.Sach;
import model.YeuThich;
import dao.UserDAO;
import view.SachDetailView;
import view.YeuThichView;
import model.SessionManager;

/**
 *
 * @author ADMIN
 */
public class YeuThichController {
    private YeuThichView view;
    private YeuThichDAO dao;
    private String maSvDangNhap;

    private JMenu menuHome, menuDanhMuc, menuYeuThich, menuMuonSach;
    private JMenuItem itemTheLoai, itemTacGia;

    public YeuThichController(YeuThichView view) {
        this.dao = new YeuThichDAO();
        this.view = view;
        this.maSvDangNhap = SessionManager.getMaNguoiDung();
    }

    public void initView() {
        loadData();
    }

    public boolean daYeuThich(String maSV, String maSach) {
        return dao.checkExists(maSV, maSach);
    }

    public boolean insertYeuThich(String maSv, String maSach) {
        if (daYeuThich(maSv, maSach)) {
            return false;
        }
        return dao.insert(maSv, maSach);
    }

    public boolean deleteYeuThich(String maSv, String maSach) {
        return dao.delete(maSv, maSach);
    }

    private void loadData() {
        try {
            List<YeuThich> list = dao.getYeuthichByMaSv(maSvDangNhap);
            if (list == null || list.isEmpty()) {
                view.showMessage("Bạn chưa có sách yêu thích nào!");
                return;
            }
            view.displayYeuThich(list);

            attachCardRvents();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loi 73");
        }

    }

    private void attachCardRvents() {
        JPanel gridPanel = view.getGridPanel();
        for (Component comp : gridPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel card = (JPanel) comp;
                // Xóa listener cũ (tránh bị add trùng)
                for (MouseListener ml : card.getMouseListeners().clone()) {
                    if (ml instanceof MouseAdapter) {
                        card.removeMouseListener(ml);
                    }
                }
                card.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            int index = getCardIndex(card);
                            if (index != -1) {
                                handleCardClick(index);

                            }
                        }
                    }
                });
            }
        }
    }

    private int getCardIndex(JPanel card) {
        Component[] comp = view.getGridPanel().getComponents();
        for (int i = 0; i < comp.length; i++) {
            if (comp[i] == card) {
                return i;
            }
        }
        return -1;
    }

    private void handleCardClick(int index) {
        try {
            List<YeuThich> ds = dao.getYeuthichByMaSv(maSvDangNhap);
            if (index >= 0 && index < ds.size()) {
                YeuThich yt = ds.get(index);
                openSachDetail(yt.getMaSach());
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi mở chi tiết sách!");
        }
    }

    private void setCardBackground(JPanel card, Color color) {
        card.setBackground(color);
        for (Component comp : card.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(color);
            }
        }
    }

    private void openSachDetail(String maSach) {
        try {
            SachDAO sachDAO = new SachDAO();
            Sach sach = sachDAO.getById(maSach);
            if (sach == null) {
                view.showError("Không tìm thấy sách!");
                return;
            }
            SachDetailView sdv = new SachDetailView(sach, maSvDangNhap);
            sdv.doShow();
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi mở chi tiết sách!");
        }
    }

}
