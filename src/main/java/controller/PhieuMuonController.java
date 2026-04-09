package controller;

import admin_book.sachDAO;
import dao.PhieuMuonDAO;
import dao.SachDAO;
import java.text.SimpleDateFormat;
import model.PhieuMuon;
import view.PhieuMuonView;

import java.util.Date;
import java.util.List;
import qltv.userDAO;

public class PhieuMuonController {

    private PhieuMuonDAO dao;
    private PhieuMuonView view;
    private SachDAO sachDAO;
    private userDAO uDAO;

    private String maSv;

    public PhieuMuonController() {
    }

    public PhieuMuonController(PhieuMuonDAO dao) {
        this.dao = dao;
        sachDAO = new SachDAO();
        uDAO = new userDAO();
        this.maSv = uDAO.getMSV_isLogin();

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
            pm.setMaPm("PM" + System.currentTimeMillis());
            pm.setMaSv(maSv);
            pm.setMaSach(maSach);
            pm.setSoLuong(soLuong);
            pm.setNgayMuon(ngayMuon);
            pm.setNgayTra(ngayTra);
            pm.setTinhTrang("Đang mượn");

            boolean themPM = dao.insert(pm);

            sachDAO.capNhatTinhTrang(maSach);

            return themPM;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
