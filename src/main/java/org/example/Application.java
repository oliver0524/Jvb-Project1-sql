package org.example;

import io.javalin.Javalin;
import org.example.Controller.ProductController;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class Application {

    public static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        Connection conn = ConnectionSingleton.getConnection();
        SellerDAO sellerDAO = new SellerDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn);
        SellerService sellerService = new SellerService(sellerDAO);
        ProductService productService = new ProductService(productDAO, sellerDAO);
        ProductController productController = new ProductController(sellerService, productService);

        Javalin api = productController.getAPI();

        api.start(9002);


    }
}