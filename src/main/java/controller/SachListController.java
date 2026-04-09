package controller;

import dao.SachDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.Sach;
import view.SachDetailView;
import view.SachListView;

public class SachListController {
    
    private SachListView view;
    private SachDAO sachDAO;

    private String maSvDangNhap;
    
    public SachListController(SachListView view, String maSvDangNhap) {
        this.view = view;
        this.maSvDangNhap = maSvDangNhap;
        this.sachDAO = new SachDAO();
    }

    public void initView() {
        refresh();
        addEvents();
    }

    private void loadAll(String maLoaiSach) {
        try {
            List<Sach> danhSach = sachDAO.getByLoaiSach(maLoaiSach);
            view.displaySach(danhSach);
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi tải sách theo thể loại!");
        }
    }

    private void addEvents() {
        view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        view.getBtnRefresh().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewSachDetail();
                }
            }
        });
    }

    private void search() {
        String keyword = view.getSearchKeyWord();
        
        if (keyword.isEmpty()) {
            view.showError("Vui lòng nhập từ khóa để tìm kiếm!");
            return;
        }

        try {
            List<Sach> danhSach = sachDAO.search(view.getMaLoaiSach(), keyword);
            view.displaySach(danhSach);
            
            if (danhSach.isEmpty()) {
                view.showMessage("Không tìm thấy sách với từ khóa: \"" + keyword + "\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    private void refresh() {
        view.clearSearch();
        loadAll(view.getMaLoaiSach());
    }

    private void viewSachDetail() {
        String maSach = view.getSelectedMaSach();
        
        if (maSach == null || maSach.isEmpty()) {
            view.showError("Vui lòng chọn sách để xem chi tiết!");
            return;
        }

        try {
            Sach sach = sachDAO.getById(maSach);
            
            if (sach != null) {
                SachDetailView detailView = new SachDetailView(sach, maSvDangNhap);
                detailView.doShow();
            } else {
                view.showError("Không tìm thấy thông tin sách!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi lấy thông tin sách: " + e.getMessage());
        }
    }
}