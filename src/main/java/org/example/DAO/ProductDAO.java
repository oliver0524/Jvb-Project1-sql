package org.example.DAO;

import org.example.Model.ProductInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    Connection conn;

    public ProductDAO(Connection conn){
        this.conn = conn;
    }


    public ProductInfo insertProduct(ProductInfo p){
        //List<ProductInfo> newProduct = new ArrayList<>();
        try {
            //System.out.println("Connection status Product: " + conn);
            PreparedStatement ps = conn.prepareStatement("insert into PRODUCTS" +
                    " (product_name, price, sold_by) " +
                    "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setString(3, p.getSellername());
            ps.executeUpdate();
            // retrieve automatically generated key
            ResultSet generatedKeys = ps.getGeneratedKeys();
            System.out.println("generatedKeys: " + generatedKeys);
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                p.setId(generatedId);
                return p;
            }
        }catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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

    public ProductInfo getProductById(Integer id){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from PRODUCTS where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //int id = rs.getInt("product_id");
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

    public ProductInfo getProductByName(String name){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from PRODUCTS where product_name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("product_id");
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

    public List<ProductInfo> getProductBySeller(String sname){
        List<ProductInfo> resultProducts = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from PRODUCTS where sold_by = ?");
            ps.setString(1, sname);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("product_id");
                double price= rs.getInt("price");
                String name = rs.getString("product_name");
                ProductInfo p = new ProductInfo(id, name, price, sname);
                resultProducts.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return resultProducts;
    }

    public ProductInfo updateProductById(ProductInfo p){
        try{
            PreparedStatement ps = conn.prepareStatement("update PRODUCTS set price = ?, sold_by = ? " +
                    "where product_id = ?");
            ps.setDouble(1, p.getPrice());
            ps.setString(2, p.getSellername());
            ps.setInt(3, p.getId());
            int rs = ps.executeUpdate();
            if (rs == 1){
                return p;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteProductById(Integer id){
        try{
            PreparedStatement ps = conn.prepareStatement("delete from PRODUCTS where product_id = ?");
            ps.setInt(1, id);
            int rs = ps.executeUpdate();
            if (rs == 1){
                return true;
            }
         }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
