package org.example.Model;

import java.util.Objects;

public class Seller {
    private long sellerid;
    private String sellername;

    /** Constructor for class Seller */
    public Seller(int sellerid, String sellername) {
        this.sellerid = sellerid;
        this.sellername = sellername;
    }

    public Seller(){
    }

    /** Setters and getters for the Seller class variables. No usage annotations are ok */
    public long getSellerid() {
        return sellerid;
    }

    public void setSellerid(long sellerid) {
        this.sellerid = sellerid;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller = (Seller) o;
        return Objects.equals(sellername, seller.sellername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellername);
    }

    /** This code is used to convert Seller objects into a String (displayable format) */
@Override

public String toString() {
    return "\n" +
            "Seller id=" + sellerid +
            "Seller Name: " + sellername;
}
}