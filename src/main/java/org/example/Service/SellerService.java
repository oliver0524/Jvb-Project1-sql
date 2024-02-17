package org.example.Service;

import org.example.Application;
import org.example.DAO.SellerDAO;
import org.example.Exception.SellerException;
import org.example.Model.Seller;

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

    /**
     * This method: handles the Seller addition and throws the SellerException at the end if
     * at least one variable did not pass the validation test
     */
    public void addSeller(Seller s) throws SellerException {
        Application.log.info("ADD: Attempting to add a Seller:" + s);

        if (s.getSellername().isBlank()) {
            Application.log.warn("ADD: Seller name is missing: " + s.getSellername());
            throw new SellerException("Seller name cannot be blank");
//        }

//        if (sellerSet.contains(s)) {
//            Application.log.warn("ADD: Seller name already exists: " + s.getSellername());
//            throw new SellerException("Seller name already exists");
        } else {
            sellerDAO.insertSeller(s);
        }
    }

    /**
     * This method handles the 'view' action and displays all Seller objects from the Seller Set
     */
    public Set<String> getAllSellers() {
        Set<String> sellerNames = sellerDAO.getAllSellers();
        Application.log.info("VIEW: List of all Sellers in the collection: " + sellerNames);
        return sellerNames;
    }

    public String getSellerByName(String name) throws SellerException {
        String s = sellerDAO.getSellerByName(name);
        if (s == null) {
            throw new SellerException("No Seller " + s + " found");
        } else {
            return s;
        }
    }

}



