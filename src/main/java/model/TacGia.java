/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class TacGia {
    private int maTg;
    private String tenTg;
    private String ngaySinh;
    private String gioiTinh;
    private String que;
    private String QuocTich;
    private String tieuSu;
    private String hinh;

    public TacGia() {}

    public TacGia(int maTg, String tenTg, String ngaySinh, String gioiTinh, String que,
                  String QuocTich, String tieuSu, String hinh) {
        this.maTg = maTg;
        this.tenTg = tenTg;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.que = que;
        this.QuocTich = QuocTich;
        this.tieuSu = tieuSu;
        this.hinh = hinh;
    }
    public void setMaTg(int maTg) {
        this.maTg = maTg;
    }

    public int getMaTg() { return maTg; }

    public String getTenTg() {
        return tenTg;
    }

    public void setTenTg(String tenTg) {
        this.tenTg = tenTg;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getQuocTich() { return QuocTich; }

    public void setQuocTich(String quoctich) { QuocTich = quoctich; }

    public String getTieuSu() {
        return tieuSu;
    }

    public void setTieuSu(String tieuSu) {
        this.tieuSu = tieuSu;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    @Override
    public String toString() {
        return tenTg;
    }
 
}
