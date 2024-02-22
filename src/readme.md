# This application is using javalin service to initiate a web server. 
# The application is handling two models, Products and Sellers through http requests

# Product Model
-  Product Model structure:
  * Product Product Id (Primary key) 
  * Product Name 
  * Price 
  * Seller Name (foreign key to the Seller table)

-  Product Service requests:
  * GET all products
  * GET by product-id (404 for a non-existed product)
  * GET by a partial product name
  * GET products by seller
  * POST product 
    * Add a single product
    * Product ids should be non-null and unique
    * Product names should be non-null
    * Price should be over 0
    * Seller name should refer to an actually existing seller (referential integrity)
  * PUT by product-id
    * Update a single product
    * Product names should be non-null
    * Price should be over 0
    * Seller name should refer to an actually existing seller
  * DELETE by product-id
    * Delete a single product
    * DELETE should always return 200, regardless of if the item existed at the start or not. This is convention.

# Seller Model
- Seller Model structure:
  * Seller Name

- Seller Service requests:
    * GET all sellers
    * POST seller
      * Selle name must be non-null & unique 
    
# JUnit
 - JUnit test can be executed by running the ProductServiceTest.java class
 

# Logging
- Logging is not working with Javalin

