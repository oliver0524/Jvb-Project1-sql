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

    public ProductDAO(Connection conn) {
    }

    public void productDAO(Connection conn){

        this.conn = conn;
    }
    public void insertProduct(ProductInfo p){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into Product" +
                    " (Product_id, title, year_made, painted_by) " +
                    "values (?, ?, ?, ?)");
            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setInt(3, p.getPrice());
            ps.setInt(4, p.getSellername());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public List<ProductInfo> getAllProducts(){
        List<ProductInfo> resultProducts = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Product");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int ProductId = rs.getInt("Product_id");
                String title = rs.getString("title");
                int yearMade = rs.getInt("year_made");
                int paintedBy = rs.getInt("painted_by");
                ProductInfo p = new ProductInfo(ProductId, title, yearMade, paintedBy);
                resultProducts.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultProducts;
    }
    public ProductInfo getProductById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Product where Product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int ProductId = rs.getInt("Product_id");
                String title = rs.getString("title");
                int yearMade = rs.getInt("year_made");
                int paintedBy = rs.getInt("painted_by");
                ProductInfo p = new ProductInfo(ProductId, title, yearMade, paintedBy);
                return p;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
