package org.example.DAO;

import org.example.Exception.SellerException;
import org.example.Model.Seller;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SellerDAO {
    Connection conn;
    public SellerDAO(Connection conn){
        this.conn = conn;
    }

    public Set<String> getAllSellers(){
        Set<String> SellerResults = new LinkedHashSet<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Sellers");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String sellerName = resultSet.getString("seller_name");
                SellerResults.add(sellerName);
            }
                }catch(SQLException e){
                e.printStackTrace();
        }
        return SellerResults;
    }

    public void insertSeller(Seller s) throws SellerException {
        try{
            //System.out.println("Connection status Seller: " + conn);
            PreparedStatement ps = conn.prepareStatement("insert into " +
                    "Sellers (seller_name) values (?)");
            ps.setString(1, s.getSellername());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new SellerException("Error adding seller" + e.getMessage());
        }
    }

    public String getSellerByName(String s){
        try{
            PreparedStatement ps = conn.prepareStatement(
                    "select * from Sellers where seller_name = ?");
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String sellerName = rs.getString("seller_name");
                return sellerName;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
