/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package admin_book;

/**
 *
 * @author Admin
 */
public class theloai {
    private String ma_loai_sach;
    private String ten_loai_sach;

    public theloai() {
    }

    public theloai(String ma_loai_sach, String ten_loai_sach) {
        this.ma_loai_sach = ma_loai_sach;
        this.ten_loai_sach = ten_loai_sach;
    }

    public String getMa_loai_sach() {
        return ma_loai_sach;
    }

    public void setMa_loai_sach(String ma_loai_sach) {
        this.ma_loai_sach = ma_loai_sach;
    }

    public String getTen_loai_sach() {
        return ten_loai_sach;
    }

    public void setTen_loai_sach(String ten_loai_sach) {
        this.ten_loai_sach = ten_loai_sach;
    }

    @Override
    public String toString() {
        return ten_loai_sach;
    }

    
    
}
