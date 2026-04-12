package model;

public class Admin {

    private int id;          
    private String maadmin;
    private String hoten;
    private String gioitinh;
    private String sdt;
    private String email;
    private String username;
    private String password;

    public Admin(int id, String maadmin, String hoten, String gioitinh, String sdt, String email) {
        this.id = id;
        this.maadmin = maadmin;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.sdt = sdt;
        this.email = email;
    }

    public Admin() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMaadmin() {
        return maadmin;
    }
    public void setMaadmin(String maadmin) {
        this.maadmin = maadmin;
    }
    public String getHoten() {
        return hoten;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
    public String getGioitinh() {
        return gioitinh;
    }
    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
     public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    } 

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return hoten;
    }
}
