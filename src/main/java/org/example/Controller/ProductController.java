package org.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.example.Exception.ProductInfoException;
import org.example.Exception.SellerException;
import org.example.Model.ProductInfo;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;


import java.util.*;

import static org.example.Service.ProductService.*;


public class ProductController {

    SellerService sellerService;
    ProductService productService;

    public ProductController(SellerService sellerService, ProductService productService){
        this.sellerService = sellerService;
        this.productService = productService;
    }

    /** Method to create & configure a Javalin api; start the service; define endpoints
     * With the Create method we are referencing the Javalin class directly */
    public Javalin getAPI(){
        Javalin api = Javalin.create();

        api.get("/health",
                context ->
                {
                    context.result("the server is UP");
                }
        );
        api.get("/seller", context -> {
            Set<String> sellerSet = sellerService.getAllSellers();
            context.json(sellerSet);
        });

        api.get("/product", context -> {
            ArrayList<ProductInfo> productList = (ArrayList<ProductInfo>) productService.getAllProducts();
            context.json(productList);
        });

        api.post("/seller", context -> {
            try{
                ObjectMapper om = new ObjectMapper();
                Seller s = om.readValue(context.body(), Seller.class);
                sellerService.addSeller(s);
                context.status(201);
            }catch(JsonProcessingException | SellerException e){
                e.printStackTrace();
                context.result(e.getMessage());
                context.status(400);
            }
        });

        api.post("/product", context -> {
            try{
                ObjectMapper om = new ObjectMapper();
                ProductInfo p = om.readValue(context.body(), ProductInfo.class);
                ProductInfo newProduct = productService.addProduct(p);
                context.status(201);
                context.json(newProduct);
            }catch(JsonProcessingException e){
                //e.printStackTrace();
                context.status(400);
            }catch(ProductInfoException e){
                //e.printStackTrace();
                context.result(e.getMessage());
                context.status(400);
            }
        });

        /** If product id is found, respond with status 200
         *  If product id is not found, respond with status 404 */
        api.get("product/{id}", context -> {
            long id = Long.parseLong(context.pathParam("id"));
            ProductInfo p = productService.getProductById(id);
            if(p == null){
                context.status(404);
            }else{
                context.json(p);
                context.status(200);
            }
        });

        /** If product id is found, delete and respond with status 200
         *  If product id is not found, respond with status 200 anyway (this is a convention) */
        api.delete("product/{id}", context -> {
            long id = Long.parseLong(context.pathParam("id"));
            productService.deleteProductById(id);
            context.status(200);
        });

        /** If product id is found, update and respond with status 200 */
        api.put("product/{id}", context -> {
            try{
                long id = Long.parseLong(context.pathParam("id"));
                ObjectMapper om = new ObjectMapper();
                ProductInfo p = om.readValue(context.body(), ProductInfo.class);
                p.setId(id);
                productService.updateProductById(p);
                context.json(p);
                context.status(200);
            }catch(ProductInfoException e){
            context.result(e.getMessage());
            context.status(400);
            }
        });

        return api;
    }

}


