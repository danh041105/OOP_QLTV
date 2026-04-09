/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Sach {

    private String maSach;
    private String tenSach;

    private String maLoaiSach;
    private int ma_Tg;

    private String tenLoaiSach;
    private String tenTacGia;

    private String nhaXb;
    private int namXb;
    private int soLuong;
    private boolean tinhTrang;
    private String moTa;
    private String image;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, String maLoaiSach, String tenLoaiSach,
                int ma_Tg, String tenTacGia,
                String nhaXb, int namXb, int soLuong,
                boolean tinhTrang, String moTa, String image) {

        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
        this.ma_Tg = ma_Tg;
        this.tenTacGia = tenTacGia;
        this.nhaXb = nhaXb;
        this.namXb = namXb;
        this.soLuong = soLuong;
        this.tinhTrang = tinhTrang;
        this.moTa = moTa;
        this.image = image;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(String maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public int getMa_Tg() {
        return ma_Tg;
    }

    public void setMa_Tg(int ma_Tg) {
        this.ma_Tg = ma_Tg;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public String getNhaXb() {
        return nhaXb;
    }

    public void setNhaXb(String nhaXb) {
        this.nhaXb = nhaXb;
    }

    public int getNamXb() {
        return namXb;
    }

    public void setNamXb(int namXb) {
        this.namXb = namXb;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getTinhTrangText() {
        return tinhTrang ? "Còn sách" : "Hết sách";
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return tenSach;
    }
}

