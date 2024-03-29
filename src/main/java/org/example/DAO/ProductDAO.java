package org.example.DAO;

import org.example.Exception.ProductInfoException;
import org.example.Model.ProductInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    Connection conn;

    public ProductDAO(Connection conn){
        this.conn = conn;
    }

    /** This method handles the call to a db with sql - insert a row into PRODUCTS */
    public ProductInfo insertProduct(ProductInfo p) throws ProductInfoException {
        //List<ProductInfo> newProduct = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("insert into PRODUCTS" +
                    " (product_name, price, sold_by) " +
                    "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setString(3, p.getSellername());
            ps.executeUpdate();
            // retrieve automatically generated key
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                p.setId(generatedId);
                return p;
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new ProductInfoException("Error inserting product " + e.getMessage());
        }
        return null;
    }

    /** This method handles the call to a db with sql - select all from PRODUCTS */
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

    /** This method handles the call to a db with sql - select a record with a given id from PRODUCTS */
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

    /** This method handles the call to a db with sql - select a record with a given name from PRODUCTS. Needed for validations */
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

    /** This method handles the call to a db with sql - select a record with a given Partial name from PRODUCTS */
    public List<ProductInfo> getProductByPartialName(String name){
        List<ProductInfo> resultProducts = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from PRODUCTS where product_name like ?");
            ps.setString(1, name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("product_id");
                String fullName = rs.getString("product_name");
                double price = rs.getInt("price");
                String sellername = rs.getString("sold_by");
                ProductInfo p = new ProductInfo(id, fullName, price, sellername);
                resultProducts.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return resultProducts;
    }


    /** This method handles the call to a db with sql - update Products for a given id */
    public ProductInfo updateProductById(ProductInfo p) throws ProductInfoException {
        try{
            PreparedStatement ps = conn.prepareStatement("update PRODUCTS set product_name = ?, " +
                    "price = ?, sold_by = ? " +
                    "where product_id = ?");
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setString(3, p.getSellername());
            ps.setInt(4, p.getId());
            int rs = ps.executeUpdate();
            if (rs == 1){
                return p;
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new ProductInfoException("Error updating product " + e.getMessage());
        }
        return null;
    }

    /** This method handles the call to a db with sql - delete a record from Products for a given id */
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
