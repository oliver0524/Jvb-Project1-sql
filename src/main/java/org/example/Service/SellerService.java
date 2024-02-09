package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Seller;

import java.util.*;


public class SellerService {

    /** Class SellerService handles the application functionality for Sellers.
     * Methods here have functionality for the following actions: add, view all */
    Set<Seller> sellerSet;

    public SellerService() {
        this.sellerSet = new LinkedHashSet<>();
    }


        /** This method: handles the Seller addition and throws the SellerException at the end if
         * at least one variable did not pass the validation test  */
        public void addSeller (Seller s) throws SellerException {
            Main.log.info("ADD: Attempting to add a Seller:" + s);

            if (s.getSellername().length() < 1) {
                Main.log.warn("ADD: Seller name is missing: " + s.getSellername());
                throw new SellerException("Seller name cannot be blank");
            }

            if (sellerSet.contains(s)){
                Main.log.warn("ADD: Seller name already exists: " + s.getSellername());
                throw new SellerException("Seller name already exists");
            } else {
                sellerSet.add(s);
            }
        }

        /**
         * This method handles the 'view' action and displays all Seller objects from the Seller Set
         */
        public Set<String> getAllSellers () {
            Main.log.info("VIEW: List of all Sellers in the collection: " + sellerSet);
            Set <String> sellerNames = new HashSet<>();
            for (Seller seller : sellerSet){
                sellerNames.add(seller.getSellername());
            }
            return sellerNames;
        }
    }


