/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class PhieuMuon {
    private String maPm;
    private String maSv;
    private String maSach;
    private Date ngayMuon;
    private Date ngayTra;
    private int soLuong;
    private String tinhTrang;
    
    private String hoTen;
    private String tenSach;

    public PhieuMuon() {
    }

    public PhieuMuon(String maPm, String maSv, String maSach, Date ngayMuon, Date ngayTra, int soLuong, String tinhTrang, String hoTen, String tenSach) {
        this.maPm = maPm;
        this.maSv = maSv;
        this.maSach = maSach;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;
        this.hoTen = hoTen;
        this.tenSach = tenSach;
    }

    public String getMaPm() {
        return maPm;
    }

    public void setMaPm(String maPm) {
        this.maPm = maPm;
    }

    public String getMaSv() {
        return maSv;
    }

    public void setMaSv(String maSv) {
        this.maSv = maSv;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    
}
