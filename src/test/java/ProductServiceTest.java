import org.example.DAO.ProductDAO;
import org.example.Exception.ProductInfoException;
import org.example.Exception.SellerException;
import org.example.Model.ProductInfo;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductServiceTest {

    ProductService ProductService;
    SellerService SellerService;
    long id = (long) (Math.random() * Long.MAX_VALUE);

    /** Instantiate an object before each test */
    @Before
    public void setUp() {
        SellerService = new SellerService();
        ProductService = new ProductService(SellerService);
        //ProductDAO = new ProductDAO();
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
        p1.setId(id);
        p1.setName("Product");
        p1.setSellername("SellerName");
        p1.setPrice(0.0);
        try {
            // if the below method DOES NOT throw an exception, the Assert.fail method is called
            ProductService.addProduct(p1);
            Assert.fail("The Exception is not thrown when price is 0 or less");
        } catch (ProductInfoException e) {
            // Log the exception details
            Main.log.info("Caught ProductInfoException: " + e.getMessage());
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
            Main.log.info("Caught ProductInfoException: " + e.getMessage());
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
            Main.log.info("Caught ProductInfoException: " + e.getMessage());
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
            Main.log.info("Caught ProductInfoException: " + e.getMessage());
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
            Main.log.info("Caught SellerException: " + e.getMessage());
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
            Main.log.info("Caught SellerException: " + e.getMessage());
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
        }catch (ProductInfoException e) {
            // Log the exception details
            Main.log.info("Caught ProductInfoException: " + e.getMessage());
        }
    }

    /** Test if GET is called on a non-existent product-id */
    @Test
    public void testInvlaidProductId() {
        // Arrange

        try {
        // if no errors are produced when a GET by a non-existent product id is performed, the Assert.fail method is called
        ProductService.getProductById(id);
        Assert.fail("The Exception is not thrown when a Search by a non-existent product id is performed");
        } catch (ProductInfoException e) {
            // Log the exception details
            Main.log.info("Caught ProductInfoException for a non-existent product-id: " + e.getMessage());
        }
    }
    }
