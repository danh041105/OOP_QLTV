package controller;

import dao.TacGiaDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JPanel;
import model.TacGia;
import view.TacGiaDetailView;
import view.TacGiaListView;

public class TacGiaController {

    private final TacGiaDAO tacGiaDAO;
    private final TacGiaListView view;

    public TacGiaController(TacGiaListView view) {
        this.view = view;
        this.tacGiaDAO = new TacGiaDAO();
    }

    public void initView() {
        loadAll();
    }

    private void loadAll() {
        try {
            List<TacGia> danhSach = tacGiaDAO.getAll();
            view.displayTacGia(danhSach);
            attachCardEvents();
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi tải danh sách tác giả!");
        }
    }

    private void attachCardEvents() {
        JPanel gridPanel = view.getGridPanel();

        for (Component comp : gridPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel card = (JPanel) comp;

                for (MouseListener listener : card.getMouseListeners().clone()) {
                    if (listener instanceof MouseAdapter) {
                        card.removeMouseListener(listener);
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
        JPanel gridPanel = view.getGridPanel();
        Component[] components = gridPanel.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i] == card) {
                return i;
            }
        }
        return -1;
    }

    private void handleCardClick(int index) {
        try {
            List<TacGia> danhSach = tacGiaDAO.getAll();

            if (index >= 0 && index < danhSach.size()) {
                TacGia tacGia = danhSach.get(index);
                viewTacGiaDetail(tacGia.getMaTg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi xử lý click!");
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

    private void viewTacGiaDetail(int maTg) {
        try {
            TacGia tacGia = tacGiaDAO.getById(maTg);

            if (tacGia == null) {
                view.showError("Không tìm thấy tác giả!");
                return;
            }

            TacGiaDetailView detailView = new TacGiaDetailView(tacGia);
            detailView.doShow();

        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi xem chi tiết tác giả!");
        }
    }
}
