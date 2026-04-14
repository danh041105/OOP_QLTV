/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connect.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.YeuThich;

/**
 *
 * @author ADMIN
 */

public class YeuThichDAO {
    
    public List<YeuThich> getYeuthichByMaSv (String maSv) {
        List<YeuThich> list = new ArrayList<>();
        String sql = "select s.ma_sach, s.ten_sach, s.image, tg.ten_tg"
                + " from yeu_thich yt "
                + " join sach s on s.ma_sach =yt.ma_sach"
                + " join tac_gia tg on tg.ma_tg=s.ma_tg"
                + " where yt.ma_sv=?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, maSv);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            list.add(new YeuThich(rs.getString("image"),rs.getString("ten_tg"),rs.getString("ten_sach"),rs.getString("ma_sach")));
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
            return list;
        }
    
    public boolean checkExists(String maSv, String maSach) {
        String sql = "select 1 from yeu_thich where ma_sv=? and ma_sach =?";
        try (Connection c = Connect.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
           ps.setString(1, maSv);
           ps.setString(2, maSach);
           
           return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean insert(String maSv, String maSach) {
        String sql = "insert into yeu_thich(ma_sach, ma_sv) values (?,?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maSach);
            ps.setString(2, maSv);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean delete(String maSv, String maSach) {
        String sql = "delete from yeu_thich where ma_sv = ? AND ma_sach = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSv);
            ps.setString(2, maSach);
            
            return ps.executeUpdate() >0;
            
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
    
