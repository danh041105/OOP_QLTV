package controller;

import dao.SachDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.util.List;
import model.Sach;
import view.SachDetailView;
import view.SachListView;

public class SachListController {
    private SachListView view;
    private SachDAO sachDAO;
    private String maDangNhap;

    public SachListController(SachListView view, String maDangNhap) {
        this.view = view;
        this.maDangNhap = maDangNhap;
        this.sachDAO = new SachDAO();
    }
    private void initController() {
        refresh();
        addEvents();
    }
    public void initView() {
        refresh();
        addEvents();
        initController();
    }

    private void loadAll(String maLoaiSach) {
        try {
            List<Sach> DanhSach = sachDAO.getByLoaiSach(maLoaiSach);
            view.displaySach(DanhSach);
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
            List<Sach> DanhSach = sachDAO.search(view.getMaLoaiSach(), keyword);
            view.displaySach(DanhSach);
            
            if (DanhSach.isEmpty()) {
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
                SachDetailView detailView = new SachDetailView(sach, maDangNhap);
                detailView.doShow();
            } else {
                view.showError("Không tìm thấy thông tin sách!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.showError("Lỗi khi lấy thông tin sách: " + e.getMessage());
        }
    }
    private void addSach() {
        // Tùy thuộc vào thiết kế của bạn:
        // 1. Có thể lấy dữ liệu trực tiếp từ các TextField trên SachListView
        // 2. Hoặc mở một SachFormView mới để nhập dữ liệu
        view.showMessage("Tính năng Thêm sách đã được kích hoạt. Hãy kết nối form nhập liệu của bạn tại đây!");
        // Code ví dụ lấy dữ liệu:
        // Sach s = view.getSachInputData();
        // if(sachDAO.insert(s)) { view.showMessage("Thêm thành công!"); refresh(); }
    }

    private void updateSach() {
        String maSach = view.getSelectedMaSach();
        if (maSach == null || maSach.isEmpty()) {
            view.showError("Vui lòng chọn một quyển sách trong bảng để sửa!");
            return;
        }
        view.showMessage("Bạn đang chọn sửa sách mã: " + maSach + ". Hãy hiển thị data lên form!");
        // Code ví dụ:
        // Sach s = view.getSachInputData(); // Nhớ set sẵn mã sách cần sửa vào model
        // if(sachDAO.update(s)) { view.showMessage("Sửa thành công!"); refresh(); }
    }

    private void deleteSach() {
        String maSach = view.getSelectedMaSach();
        if (maSach == null || maSach.isEmpty()) {
            view.showError("Vui lòng chọn một quyển sách để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sách có mã: " + maSach + "?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (sachDAO.remove(maSach)) {
                view.showMessage("Đã xóa sách thành công!");
                refresh();
            } else {
                view.showError("Xóa thất bại! Sách này có thể đang nằm trong phiếu mượn.");
            }
        }
    }
}