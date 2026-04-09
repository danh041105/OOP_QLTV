/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin_book;

/**
 *
 * @author Admin
 */
public class sach {
    private String ma_sach;
    private String ten_sach;
    private String ma_loai_sach;
    private int ma_tg;
    private String nha_xb;
    private int nam_xb;
    private int so_luong;
    private boolean tinh_trang;
    private String mo_ta;
    private String image;
    private String ten_loai_sach;
    private String ten_tg;
    
    
    public sach() {
    }

    public sach(String ma_sach, String ten_sach, String ma_loai_sach, int ma_tg, String nha_xb, int nam_xb, int so_luong, boolean tinh_trang, String mo_ta, String image, String ten_loai_sach, String ten_tg) {
        this.ma_sach = ma_sach;
        this.ten_sach = ten_sach;
        this.ma_loai_sach = ma_loai_sach;
        this.ma_tg = ma_tg;
        this.nha_xb = nha_xb;
        this.nam_xb = nam_xb;
        this.so_luong = so_luong;
        this.tinh_trang = tinh_trang;
        this.mo_ta = mo_ta;
        this.image = image;
        this.ten_loai_sach= ten_loai_sach;
        this.ten_tg = ten_tg;
        
    }

    public String getMa_sach() {
        return ma_sach;
    }

    public void setMa_sach(String ma_sach) {
        this.ma_sach = ma_sach;
    }

    public String getTen_sach() {
        return ten_sach;
    }

    public void setTen_sach(String ten_sach) {
        this.ten_sach = ten_sach;
    }

    public String getMa_loai_sach() {
        return ma_loai_sach;
    }

    public void setMa_loai_sach(String ma_loai_sach) {
        this.ma_loai_sach = ma_loai_sach;
    }

    public int getMa_tg() {
        return ma_tg;
    }

    public void setMa_tg(int ma_tg) {
        this.ma_tg = ma_tg;
    }

    public String getNha_xb() {
        return nha_xb;
    }

    public void setNha_xb(String nha_xb) {
        this.nha_xb = nha_xb;
    }

    public int getNam_xb() {
        return nam_xb;
    }

    public void setNam_xb(int nam_xb) {
        this.nam_xb = nam_xb;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public boolean isTinh_trang() {
        return tinh_trang;
    }

    public void setTinh_trang(boolean tinh_trang) {
        this.tinh_trang = tinh_trang;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTen_loai_sach() {
        return ten_loai_sach;
    }

    public void setTen_loai_sach(String ten_loai_sach) {
        this.ten_loai_sach = ten_loai_sach;
    }

    public String getTen_tg() {
        return ten_tg;
    }

    public void setTen_tg(String ten_tg) {
        this.ten_tg = ten_tg;
    }
    

    @Override 
    public String toString() { 
        return "sach{" + "ma_sach=" + ma_sach + ", ten_sach=" + ten_sach + ", ma_loai_sach=" + ma_loai_sach + ", ma_tg=" + ma_tg + ", nha_xb=" + nha_xb + ", nam_xb=" + nam_xb + ", so_luong=" + so_luong + ", tinh_trang=" + tinh_trang + ", mo_ta=" + mo_ta + ", image=" + image + ", ten_loai_sach=" + ten_loai_sach + ", ten_tg=" + ten_tg + '}'; 
    }
    
    
    
    
    
}
