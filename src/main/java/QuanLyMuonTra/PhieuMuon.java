/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyMuonTra;

import java.util.Date;

/**
 *
 * @author Hien
 */
public class PhieuMuon {
    private String maPM, maSV, hoTen, maSach, tenSach, tentg, tinhTrang, nhaxb;
    private int soLuong;
    private Date ngayMuon, ngayTra;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPM, String maSV, String hoTen, String maSach, String tenSach, String tentg, String tinhTrang, String nhaxb, int soLuong, Date ngayMuon, Date ngayTra) {
        this.maPM = maPM;
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tentg = tentg;
        this.tinhTrang = tinhTrang;
        this.nhaxb = nhaxb;
        this.soLuong = soLuong;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
    }

    public String getMaPM() {
        return maPM;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getMaSach() {
        return maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getTentg() {
        return tentg;
    }
    
    public String getTinhTrang() {
        return tinhTrang;
    }

    public String getNhaxb() {
        return nhaxb;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public void setTentg(String tentg) {
        this.tentg = tentg;
    }
    
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setNhaxb(String nhaxb) {
        this.nhaxb = nhaxb;
    }
    
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }
    
}

