package org.example.Service;

import org.example.Application;
import org.example.DAO.SellerDAO;
import org.example.Exception.SellerException;
import org.example.Model.Seller;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


public class SellerService {

    /** Class SellerService handles the application functionality for Sellers.
     * Methods here have functionality for the following actions: add, view all */
    Set<Seller> sellerSet;

    public SellerService() {
        this.sellerSet = new LinkedHashSet<>();
    }

    public SellerService(SellerDAO sellerDAO) {
    }

    /** This method: handles the Seller addition and throws the SellerException at the end if
         * at least one variable did not pass the validation test  */
        public void addSeller (Seller s) throws SellerException {
            Application.log.info("ADD: Attempting to add a Seller:" + s);

            if (s.getSellername().isBlank()) {
                Application.log.warn("ADD: Seller name is missing: " + s.getSellername());
                throw new SellerException("Seller name cannot be blank");
            }

            if (sellerSet.contains(s)){
                Application.log.warn("ADD: Seller name already exists: " + s.getSellername());
                throw new SellerException("Seller name already exists");
            } else {
                long id = (long) (Math.random() * Long.MAX_VALUE);
                s.setSellerid(id);
                sellerSet.add(s);
            }
        }

        /**
         * This method handles the 'view' action and displays all Seller objects from the Seller Set
         */
        public Set<String> getAllSellers () {
            Application.log.info("VIEW: List of all Sellers in the collection: " + sellerSet);
            Set <String> sellerNames = new HashSet<>();
            for (Seller seller : sellerSet){
                sellerNames.add(seller.getSellername());
            }
            return sellerNames;
        }
    }


