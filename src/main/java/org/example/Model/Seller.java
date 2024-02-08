package org.example.Model;

public class Seller {
    private String sellername;

/** Constructor for class Seller */
public Seller(String sellername){
    this.sellername = sellername;
    }

    public Seller(){
    }

/** Setters and getters for the Seller class variables. No usage annotations are ok */
    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    /** This code is used to convert Seller objects into a String (displayable format) */
@Override
public String toString() {
    return "\nSeller Name " + sellername;
}
}