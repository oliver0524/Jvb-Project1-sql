package org.example.Service;

import org.example.Application;
import org.example.DAO.SellerDAO;
import org.example.Exception.SellerException;
import org.example.Model.Seller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


public class SellerService {

    /**
     * Class SellerService handles the application functionality for Sellers.
     * Methods here have functionality for the following actions: add, view all
     */
    Set<Seller> sellerSet;
    SellerDAO sellerDAO;

    public SellerService(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    /** This method handles the Seller addition and throws the SellerException if it does not pass validations */

    public void addSeller(Seller s) throws SellerException {
        Application.log.info("ADD: Attempting to add a Seller:" + s);

        if (s.getSellername().isBlank()) {
            Application.log.warn("ADD: Seller name is missing: " + s.getSellername());
            throw new SellerException("Seller name cannot be blank");
        } else if (sellerDAO.getSellerByName(s.getSellername()) != null) {
            Application.log.warn("ADD: Seller name is duplicate: " + s.getSellername());
            throw new SellerException("Seller name already exists ");
        } else {
            sellerDAO.insertSeller(s);
        }
    }

    /**  This method handles the 'view' action and displays all Seller objects from the Seller Set */
    public Set<String> getAllSellers() {
        Set<String> sellerNames = sellerDAO.getAllSellers();
        Application.log.info("VIEW: List of all Sellers in the collection: " + sellerNames);
        return sellerNames;
    }

    /**  This method handles the 'view' action to retrieve a single seller by name */
    public String getSellerByName(String name) throws SellerException {
        Application.log.info("VIEW: Searching for a seller: " + name);
        String s = sellerDAO.getSellerByName(name);
        if (s == null) {
            throw new SellerException("No Seller found ");
        } else {
            return s;
        }
    }

}



