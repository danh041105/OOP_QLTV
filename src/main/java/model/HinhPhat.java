package model;
import java.util.Date;
public class HinhPhat {
    private int maHP;     
    private String maSV;   
    private String hoTen; 
    private String tinhTrang, lyDo;   
    private Date ngayPhat; 
    private String hinhThuc; 
    private String tienDo; 
    private String maPM;

    public HinhPhat() {}
    public HinhPhat(int maHP, String maSV, String hoTen, String tinhTrang, String lyDo, Date ngayPhat, String hinhThuc, String tienDo, String maPM) {
        this.maHP = maHP;
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.tinhTrang = tinhTrang;
        this.lyDo = lyDo;
        this.ngayPhat = ngayPhat;
        this.hinhThuc = hinhThuc;
        this.tienDo = tienDo;
        this.maPM = maPM;
    }

    public int getMaHP() {
        return maHP;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }
    
    public String getHoTen() {
        return hoTen;
    }

    public String getLyDo() {
        return lyDo;
    }

    public Date getNgayPhat() {
        return ngayPhat;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public String getTienDo() {
        return tienDo;
    }

    public String getMaPM() {
        return maPM;
    }

    public void setMaHP(int maHP) {
        this.maHP = maHP;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public void setNgayPhat(Date ngayPhat) {
        this.ngayPhat = ngayPhat;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public void setTienDo(String tienDo) {
        this.tienDo = tienDo;
    }

    public void setMaPM(String maPM) {
        this.maPM = maPM;
    }
}