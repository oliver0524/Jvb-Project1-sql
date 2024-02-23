import org.example.Application;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exception.ProductInfoException;
import org.example.Exception.SellerException;
import org.example.Model.ProductInfo;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    ProductService ProductService;
    SellerService SellerService;

    @Mock
  ProductDAO productDAO;
    @Mock
  SellerDAO sellerDAO;

    /** Instantiate an object before each test */
    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        Connection conn = ConnectionSingleton.getConnection();
        sellerDAO = new SellerDAO(conn);
        productDAO = new ProductDAO(conn);
        SellerService = new SellerService(sellerDAO);
        ProductService = new ProductService(productDAO, sellerDAO);
    }

    /** Verify that when the Product service is first created, it is empty.*/
    @Test
    public void productServiceEmptyAtStart() {
        List<ProductInfo> Productinfo = ProductService.getAllProducts();
        Assert.assertTrue(Productinfo.size() == 0);
    }


    /** When price 0 or less is provided, the Product Info Exception should be thrown.*/
    @Test
    public void inputProductInvalidPrice() {
        ProductInfo p1 = new ProductInfo();
        //p1.setId(id);
        p1.setName("Product");
        p1.setSellername("SellerName");
        p1.setPrice(0.0);
        try {
            // if the below method DOES NOT throw an exception, the Assert.fail method is called
            ProductService.addProduct(p1);
            Assert.fail("The Exception is not thrown when price is 0 or less");
        } catch (ProductInfoException e) {
            // Log the exception details
            Application.log.info("Caught ProductInfoException: " + e.getMessage());
        }
    }

    /** When Product name is empty, the Product Info Exception should be thrown.*/
    @Test
    public void inputProductNameBlank() {
        ProductInfo p1 = new ProductInfo();
        p1.setName("");
        p1.setSellername("SellerName");
        p1.setPrice(1.0);
        try {
            // if the below method DOES NOT throw an exception, the Assert.fail method is called
            ProductService.addProduct(p1);
            Assert.fail("The Exception is not thrown when Product name is blank");
        } catch (ProductInfoException e) {
            // Log the exception details
            Application.log.info("Caught ProductInfoException: " + e.getMessage());
        }
    }

    /** When Seller name is empty in the Product service, the Product Info Exception should be thrown.*/
    @Test
    public void inputSellerNameBlank() {
        ProductInfo p1 = new ProductInfo();
        p1.setName("Product");
        p1.setSellername("");
        p1.setPrice(1.0);
        try {
            // if the below method DOES NOT throw an exception, the Assert.fail method is called
            ProductService.addProduct(p1);
            Assert.fail("The Exception is not thrown when Product name is blank");
        } catch (ProductInfoException e) {
            // Log the exception details
            Application.log.info("Caught ProductInfoException: " + e.getMessage());
        }
    }

    /** When a duplicate Product is added, the Product Info Exception should be thrown.*/
    @Test
    public void duplicateProduct() throws ProductInfoException, SellerException {
        Seller s1 = new Seller();
        s1.setSellername("SellerName");
        SellerService.addSeller(s1);

        ProductInfo p1 = new ProductInfo();
        p1.setName("Product1");
        p1.setSellername("SellerName");
        p1.setPrice(10.0);
        ProductService.addProduct(p1);

        ProductInfo p2 = new ProductInfo();
        p2.setName("Product1");
        p2.setSellername("SellerName");
        p2.setPrice(10.0);
        try {
            // if the below method DOES NOT throw an exception, the Assert.fail method is called
            ProductService.addProduct(p2);
            Assert.fail("The Exception is not thrown when Product name is not unique");
        } catch (ProductInfoException e) {
            // Log the exception details
            Application.log.info("Caught ProductInfoException: " + e.getMessage());
        }
    }

    /** When a blank Seller is added, the Seller Exception should be thrown.*/
    @Test
    public void blankSeller() {
        Seller s1 = new Seller();
        s1.setSellername("");

        try {
            // if the below method DOES NOT throw an exception, the Assert.fail method is called
            SellerService.addSeller(s1);
            Assert.fail("The Exception is not thrown when Seller name is blank");
        } catch (SellerException e) {
            // Log the exception details
            Application.log.info("Caught SellerException: " + e.getMessage());
        }
    }

    /** When a duplicate Seller is added, the Seller Exception should be thrown.*/
    @Test
    public void duplicateSeller() throws SellerException {
        Seller s1 = new Seller();
        s1.setSellername("SellerName1");
        SellerService.addSeller(s1);

        Seller s2 = new Seller();
        s2.setSellername("SellerName1");
        try {
            // if the below method DOES NOT throw an exception, the Assert.fail method is called
            SellerService.addSeller(s2);
            Assert.fail("The Exception is not thrown when Seller name is not unique");
        } catch (SellerException e) {
            // Log the exception details
            Application.log.info("Caught SellerException: " + e.getMessage());
        }
    }

    /** When a seller by name is not found, the Seller Exception should be thrown.*/
    @Test
    public void testGetSellerByName() throws SellerException {
        Seller s1 = new Seller();
        s1.setSellername("SellerName1");
        SellerService.addSeller(s1);

        String s2 = "SellerName2";
        try {
            // if the below method DOES NOT throw an exception, the Assert.fail method is called
            SellerService.getSellerByName(s2);
            Assert.fail("The Exception is not thrown when search by Seller is not successful");
        } catch (SellerException e) {
            // Log the exception details
            Application.log.info("Caught SellerException: " + e.getMessage());
        }
    }


    /** Test if appropriate messaging is displayed when a non-existent Seller is provided in the Product service*/
    @Test
    public void testNonExistentSeller() throws SellerException {
        // Arrange
        Seller s1 = new Seller();
        s1.setSellername("Seller1");
        SellerService.addSeller(s1);

        ProductInfo p1 = new ProductInfo();
        p1.setName("Product");
        p1.setSellername("Seller2");
        p1.setPrice(10.0);
        try {
            // if no errors are produced when the non-existent Seller is added, the Assert.fail method is called
            ProductService.addProduct(p1);
            Assert.fail("The Exception is not thrown when non-existent Seller is added to Product");
        }catch (ProductInfoException e) {
            // Log the exception details
            Application.log.info("Caught ProductInfoException: " + e.getMessage());
        }
    }

    /** Test if GET is called on a non-existent product-id */
    @Test
    public void testInvalidProductId() {
        // Arrange
        int id = 99;

        try {
            // if no errors are produced when a GET by a non-existent product id is performed, the Assert.fail method is called
            ProductService.getProductById(id);
            Assert.fail("The Exception is not thrown when searched by a non-existent product id");
        } catch (ProductInfoException e) {
            // Log the exception details
            Application.log.info("Caught ProductInfoException for a non-existent product-id: " + e.getMessage());
        }
    }

    /** When a Product search by a partial name is not found, the Product Exception should be thrown.*/
    @Test
    public void testGetProductByPartialName() throws ProductInfoException, SellerException {
        // Arrange
        Seller s1 = new Seller();
        s1.setSellername("SellerName");
        SellerService.addSeller(s1);

        ProductInfo p1 = new ProductInfo();
        p1.setName("Product1");
        p1.setSellername("SellerName");
        p1.setPrice(10.0);
        ProductService.addProduct(p1);

        // Act & Assert
        try {
            ProductService.getProductByPartialName("none");
            Assert.fail("Expected ProductInfoException for a partial search");
        } catch (ProductInfoException e) {
            Application.log.info("Caught ProductInfoException for a partial product search: ", e.getMessage());
        }
    }

    /** When an update by id in Products is not successful, the Product Exception should be thrown.*/
    @Test
    public void testUpdateProductById() throws ProductInfoException, SellerException {
        // Arrange
        Seller s1 = new Seller();
        s1.setSellername("SellerName");
        SellerService.addSeller(s1);

        ProductInfo p1 = new ProductInfo();
        p1.setName("Product3");
        p1.setSellername("SellerName");
        p1.setPrice(20.0);
        p1.setId(99);
        ProductService.addProduct(p1);

        // Act & Assert
        try {
            ProductService.updateProductById(p1);
            Assert.fail("Expected ProductInfoException for an update product by id");
        } catch (ProductInfoException e) {
            Application.log.info("Caught ProductInfoException for an update product by id: ", e.getMessage());
        }
    }

    /** Reset a database*/
    @After
    public void dbReset(){
        ConnectionSingleton.resetTestDatabase();
    }

}
