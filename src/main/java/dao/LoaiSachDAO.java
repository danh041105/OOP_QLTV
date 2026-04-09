/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.LoaiSach;
import com.mycompany.java_qltv.connect.DBconnect;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class LoaiSachDAO {
    public List<LoaiSach> getAll(){
        List<LoaiSach> list = new ArrayList<>();
        String sql = "select * from loai_sach order by ten_loai_sach";
        try (Connection conn = DBconnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while(rs.next()) {
                LoaiSach ls = new LoaiSach();
                ls.setMaLoaiSach(rs.getString(1));
                ls.setTenLoaiSach(rs.getString(2));
                list.add(ls);
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("Loi lay du lieu: "+ e.getMessage());
        }
        return list;
    }
    
    public LoaiSach getById(String maLoaiSach) {
        String sql = "select * from loai_sach where ma_loai_sach=?";
        try(Connection conn = DBconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maLoaiSach);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                LoaiSach ls = new LoaiSach();
                ls.setMaLoaiSach(rs.getString(1));
                ls.setTenLoaiSach(rs.getString(2));
                
                return ls;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi lấy LoaiSach theo ID: " + e.getMessage());
        }
        return null;
    }
}
