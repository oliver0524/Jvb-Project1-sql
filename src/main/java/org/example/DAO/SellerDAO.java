package org.example.DAO;

import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SellerDAO {
    Connection conn;
    public SellerDAO(Connection conn){
        this.conn = conn;
    }
    public Set<Seller> getAllSeller(){
        Set<Seller> SellerResults = new HashSet<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Seller");
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int SellerId = resultSet.getInt("Seller_id");
                String SellerName = resultSet.getString("name");
                Seller s = new Seller(SellerId, SellerName);
                SellerResults.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return SellerResults;
    }

    public void insertSeller(Seller s){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into " +
                    "Seller (Seller_id, name) values (?, ?)");
            ps.setInt(1, s.getSellerid());
            ps.setString(2, s.getSellername());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Seller getSellerById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select * from Seller where Seller_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int SellerId = rs.getInt("Seller_id");
                String name = rs.getString("name");
                Seller s = new Seller(SellerId, name);
                return s;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
