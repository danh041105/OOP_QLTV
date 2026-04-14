/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class YeuThich {
    private int id;
    private String image;
    private String tenTg;
    private String tenSach;
    private String maSach;


    public YeuThich() {
    }

    public YeuThich(String image, String tenTg, String tenSach, String maSach) {
        this.image = image;
        this.tenTg = tenTg;
        this.tenSach = tenSach;
        this.maSach = maSach;
    }
    

    public YeuThich(int id, String image, String tenTg, String tenSach, String maSach) {
        this.id = id;
        this.image = image;
        this.tenTg = tenTg;
        this.tenSach = tenSach;
        this.maSach = maSach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTenTg() {
        return tenTg;
    }

    public void setTenTg(String tenTg) {
        this.tenTg = tenTg;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    
   
}
