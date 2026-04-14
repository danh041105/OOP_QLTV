package controller;

import dao.PhieuMuonDAO;
import dao.SachDAO;
import java.text.SimpleDateFormat;
import model.PhieuMuon;
import view.PhieuMuonView;
import model.SessionManager;
import java.util.Date;

public class PhieuMuonController {

    private PhieuMuonDAO dao;
    private PhieuMuonView view;
    private SachDAO sachDAO;

    private String maSv;

    public PhieuMuonController() {
    }

    public PhieuMuonController(PhieuMuonDAO dao) {
        this.dao = dao;
        sachDAO = new SachDAO();
        this.maSv = SessionManager.getMaNguoiDung();

    }

    public boolean addPhieuMuon(String maSach, String soLuongStr, String ngayMuonStr, Date ngayTra) {
        try {
            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                return false;
            }
            int soLuongTon = sachDAO.getSoLuongTon(maSach);
            if (soLuong > soLuongTon) {
                return false;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ngayMuon = sdf.parse(ngayMuonStr);
            if (ngayTra == null || ngayTra.before(ngayMuon)) {
                return false;
            }
            boolean tru = sachDAO.truSoLuong(maSach, soLuong);
            if (!tru) {
                return false;
            }
            PhieuMuon pm = new PhieuMuon();
            pm.setMaPM("PM" + System.currentTimeMillis());
            pm.setMaSV(maSv);
            pm.setMaSach(maSach);
            pm.setSoLuong(soLuong);
            pm.setTinhTrang("Đang mượn");
            pm.setNgayMuon(ngayMuon);
            pm.setNgayTra(ngayTra);
            String strNgayMuon = sdf.format(pm.getNgayMuon());
            String strNgayTra = sdf.format(pm.getNgayTra());

            boolean themPM = dao.insertPhieuMuon(pm.getMaPM(), pm.getMaSV(), pm.getMaSach(),
                    pm.getSoLuong(), strNgayMuon, strNgayTra);
            return themPM;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
