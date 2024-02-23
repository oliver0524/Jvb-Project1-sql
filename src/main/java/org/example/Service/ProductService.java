package org.example.Service;

import org.example.Application;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exception.ProductInfoException;
import org.example.Model.ProductInfo;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Class ProductService handles the application functionality for Products
 * Methods here have functionality for the following actions: add, view, search, delete, exit
 * There are being called from CLIParser */
public class ProductService {

    /** Dependency Injection */
    SellerService sellerService;
   List<ProductInfo> ProductinfoList;
   ProductDAO productDAO;
   SellerDAO sellertDAO;

    public ProductService(ProductDAO productDAO, SellerDAO sellerDAO){
        this.productDAO = productDAO;
        this.sellertDAO = sellerDAO;
        this.sellerService = sellerService;
        this.ProductinfoList = new ArrayList<>();
    }

    public List<ProductInfo> getProductinfo() {
        return ProductinfoList;
    }


    /** This method handles the Product addition (POST) and throws the ProductInfoException if
     * validations are not passed  */
    public ProductInfo addProduct(ProductInfo p) throws ProductInfoException {

        if (inputValuesValidated(p)) {
            //int id = p.getId();
            productDAO.insertProduct(p);
                // get the newly created id
            p.getId();
            Application.log.info("ADD: New Product: " + p);
            return p;
        }
        throw new ProductInfoException("Product is not inserted");
        //return null;
    }

    /** Method to validate all input values based on requirements */
    public boolean inputValuesValidated(ProductInfo p) throws ProductInfoException {
        if (p.getName().isBlank() || p.getSellername().isBlank()){
            Application.log.warn("ADD: Product or Seller name are missing: "
                        + p.getName() + "| " + p.getSellername());
                throw new ProductInfoException("Product name or Seller name cannot be blank");
            } else if (p.getPrice() <= 0) {
                Application.log.warn("ADD: Price is <= 0: " + p.getPrice());
                throw new ProductInfoException("Product price should be greater than 0");
            } else if (productDAO.getProductByName(p.getName()) != null){
                Application.log.warn("ADD: Product name is duplicate: " + p.getName());
                throw new ProductInfoException("Product name already exists");
           } else if (sellertDAO.getSellerByName(p.getSellername()) == null){
                Application.log.warn("ADD: Non-existent seller name: " + p.getSellername());
                throw new ProductInfoException("Seller does not exist in the Seller set");
        } else {
            return true;
        }
    }

    /** This method handles the 'GET' by product_id. The product-id is obtained from a corresponding post request */
    public ProductInfo getProductById(Integer id) throws ProductInfoException {
        ProductInfo p = productDAO.getProductById(id);
        if (p == null) {
            throw new ProductInfoException("Product ID is not found");
        } else {
            return p;
        }
    }


    /** This method handles the 'GET' by partial product_name.  */
    public List<ProductInfo> getProductByPartialName(String name) throws ProductInfoException {
        List<ProductInfo> productList = productDAO.getProductByPartialName(name);
        if (productList.isEmpty()) {
            throw new ProductInfoException("No products found");
        } else {
            return productList;
        }
    }

      /** This method handles the 'GET' action and displays all ProductInfo objects from the Productinfo list */
    public List<ProductInfo> getAllProducts(){
        List<ProductInfo> productList = productDAO.getAllProducts();
        Application.log.info("VIEW: List of all Products in the collection: "+ productList);
        return productList;
    }


    /** This method handles the 'DELETE' action by product-id. The product-id is obtained from a corresponding post request */
    public void deleteProductById(Integer id){
        ProductInfo p = productDAO.getProductById(id);
        if (p != null){
            if (productDAO.deleteProductById(id)) {
                Application.log.info("DELETE: Successful delete for Product: " + p);
            }
        }
    }

    /** This method handles the 'PUT' action by product-id. The product-id is obtained from a corresponding post request  */
    public ProductInfo updateProductById(ProductInfo p) throws ProductInfoException {
        if ((productDAO.getProductById(p.getId()) != null) && inputValuesValidated(p)) {
            productDAO.updateProductById(p);
            Application.log.info("UPDATE: product is modified: " + p);
            return p;
        } else {
            throw new ProductInfoException("Product is not updated: " + p);
        }
        //return null;
    }


}

