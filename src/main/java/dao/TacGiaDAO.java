/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mycompany.java_qltv.connect.DBconnect;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.TacGia;

/**
 *
 * @author ADMIN
 */
public class TacGiaDAO {
    public List<TacGia> getAll() {
        List<TacGia> list = new ArrayList<>();
        String sql = "select * from tac_gia order by ten_tg";
        try(Connection conn = DBconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TacGia tg = new TacGia();
                tg.setMaTg(rs.getInt("ma_tg"));
                tg.setTenTg(rs.getString("ten_tg"));
                tg.setNgaySinh(rs.getString("ngay_sinh"));
                tg.setGioiTinh(rs.getString("gioi_tinh"));
                tg.setQue(rs.getString("que"));
                tg.setTieuSu(rs.getString("tieu_su"));
                tg.setHinh(rs.getString("hinh"));
                list.add(tg);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public TacGia getById(int maTg) {
        String sql = "select * from tac_gia where ma_tg= ?";
        
       try(Connection conn = DBconnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
           ps.setInt(1, maTg);
           
           ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TacGia tg = new TacGia();
                tg.setMaTg(rs.getInt(1));
                tg.setTenTg(rs.getString(2));
                tg.setNgaySinh(rs.getString(3));
                tg.setGioiTinh(rs.getString(4));
                tg.setQue(rs.getString(5));
                tg.setTieuSu(rs.getString(6));
                tg.setHinh(rs.getString(7));
                return tg;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
