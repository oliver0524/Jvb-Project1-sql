package org.example.DAO;

import org.example.Model.ProductInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    Connection conn;

    public ProductDAO(Connection conn){
        this.conn = conn;
    }

    //public ProductDAO() {
    //}

    public void insertProduct(ProductInfo p){
        try{
            System.out.println("Connection status Product: " + conn);
            PreparedStatement ps = conn.prepareStatement("insert into PRODUCTS" +
                    " (product_id, product_name, price, sold_by) " +
                    "values (?, ?, ?, ?)");
            ps.setLong(1, p.getId());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getSellername());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public List<ProductInfo> getAllProducts(){
        List<ProductInfo> resultProducts = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Products");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id= rs.getInt("product_id");
                String name = rs.getString("product_name");
                double price= rs.getDouble("price");
                String sellername = rs.getString("sold_by");
                ProductInfo p = new ProductInfo(id, name, price, sellername);
                resultProducts.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultProducts;
    }
    public ProductInfo getProductById(long id){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from PRODUCTS where product_id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int prodId= rs.getInt("product_id");
                String name = rs.getString("product_name");
                double price= rs.getInt("price");
                String sellername = rs.getString("sold_by");
                ProductInfo p = new ProductInfo(id, name, price, sellername);
                return p;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
