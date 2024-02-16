package org.example.Service;

import org.example.Application;
import org.example.DAO.ProductDAO;
import org.example.Exception.ProductInfoException;
import org.example.Model.ProductInfo;

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

    public ProductService(SellerService sellerService){
        this.sellerService = sellerService;
        this.ProductinfoList = new ArrayList<>();
    }

    public ProductService(ProductDAO productDAO) {

    }

    public List<ProductInfo> getProductinfo() {
        return ProductinfoList;
    }

    /** This method: handles the Product addition (POST) and throws the ProductInfoException at the end if
     * either if Product or Seller name is blank or Product name already exists  */
    public ProductInfo addProduct(ProductInfo p) throws ProductInfoException {

        if (inputValuesValidated(p)) {
            long id = (long) (Math.random() * Long.MAX_VALUE);
            p.setId(id);
            Application.log.info("ADD: Attempting to add a Product: " + id + "| "
                    + p.getName() + "| " + p.getPrice() + "| " + p.getSellername());
            ProductinfoList.add(p);
            return p;
        }
        return null;
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
            } else if (ProductinfoList.contains(p)){
                Application.log.warn("ADD: Product name is duplicate"
                        + p.getName());
                throw new ProductInfoException("Product name already exists");
            } else if (checkIfSellerDoesNotExist(p)) {
            Application.log.warn("ADD: Should be an existing Seller: " + p.getSellername());
            throw new ProductInfoException("Seller does not exist in the Seller set");
        } else {
            return true;
        }
    }

    /** Method to check if seller exists in the Seller set */
    public boolean checkIfSellerDoesNotExist(ProductInfo p){
        // Get seller names from sellerService
        Set<String> sellerNames = sellerService.getAllSellers();

        // Check if the seller name of productInfo matches any seller name
        if (sellerNames.contains(p.getSellername())) {
            return false;
        }
        return true;
    }


    /** This method handles the 'GET' by product-id. The product-id is obtained from a corresponding
     * post request (it's a randomly generated number) */
    public ProductInfo getProductById(Long id) throws ProductInfoException {
        for(int i = 0; i < ProductinfoList.size(); i++){
            ProductInfo currentProduct = ProductinfoList.get(i);
            if(currentProduct.getId() == id){
                return currentProduct;
            }
        }
        throw new ProductInfoException ("Product ID is not found");
        //return null;
    }

    /** This method handles the 'GET' action and displays all ProductInfo objects from
     * the Productinfo list */
    public List<ProductInfo> getAllProducts(){
        Application.log.info("VIEW: List of all Products in the collection: "+ProductinfoList);
        return ProductinfoList;
    }


    /** This method handles the 'DELETE' action by product-id. The product-id is obtained from a corresponding
     * post request (it's a randomly generated number) */
    public void deleteProductById(Long id){
        for(int i = 0; i < ProductinfoList.size(); i++){
            ProductInfo currentProduct = ProductinfoList.get(i);
            if(currentProduct.getId() == id){
                Application.log.info("DELETE: Successful delete for Product: " + currentProduct);
                ProductinfoList.remove(i);
                //return currentProduct;
            }
        }
        //return null;
    }

    /** This method handles the 'PUT' action by product-id. The product-id is obtained from a corresponding
     * post request (it's a randomly generated number) */
    public ProductInfo updateProductById(ProductInfo p) throws ProductInfoException {

        if (ProductinfoList == null) {
            throw new ProductInfoException("Product List is empty");
        }

        for(int i = 0; i < ProductinfoList.size(); i++){
            ProductInfo currentProduct = ProductinfoList.get(i);
            if(currentProduct.getId() == p.getId() && inputValuesValidated(p)){
                currentProduct.setId(p.getId());
                currentProduct.setName(p.getName());
                currentProduct.setPrice(p.getPrice());
                currentProduct.setSellername(p.getSellername());
                Application.log.info("UPDATE: product information updated for: " + currentProduct);
                return currentProduct;
            }
        }
        Application.log.info("UPDATE: product with the provided id is not found: " + p.getId());
        throw new ProductInfoException("Product id is not found: " + p.getId());
        //return null;
    }


}

