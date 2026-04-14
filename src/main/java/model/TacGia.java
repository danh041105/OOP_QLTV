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
    private int ma_tg;
    private String ten_tg;
    private String ngay_sinh;
    private String gioi_tinh;
    private String que;
    private String tieu_su;
    private String hinh;

    public TacGia() {}

    public TacGia(int ma_tg, String ten_tg, String ngay_sinh, String gioi_tinh,
                  String que, String tieu_su, String hinh) {
        this.ma_tg = ma_tg;
        this.ten_tg = ten_tg;
        this.ngay_sinh = ngay_sinh;
        this.gioi_tinh = gioi_tinh;
        this.que = que;
        this.tieu_su = tieu_su;
        this.hinh = hinh;
    }
    public void setMa_tg(int ma_tg) {
        this.ma_tg = ma_tg;
    }

    public int getMa_tg() { return ma_tg; }

    public String getTen_tg() {
        return ten_tg;
    }

    public void setTen_tg(String ten_tg) {
        this.ten_tg = ten_tg;
    }

    public String getNgay_sinh() {
        return ngay_sinh;
    }

    public void setNgay_sinh(String ngay_sinh) {
        this.ngay_sinh = ngay_sinh;
    }

    public String getGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(String gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getTieu_su() {
        return tieu_su;
    }
    public void setTieu_su(String tieu_su) {
        this.tieu_su = tieu_su;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    @Override
    public String toString() {
        return ten_tg;
    }
}
