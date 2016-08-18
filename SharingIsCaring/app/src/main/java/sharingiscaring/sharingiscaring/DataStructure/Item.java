package sharingiscaring.sharingiscaring.DataStructure;

import java.util.ArrayList;

/**
 * @author hoye
 * @version 0.1
 * @since 10/02/16
 *
 * This is the base class that our Pokemon inherit from.  It tracks the generic properties that
 * items in a sharing economy would need to track, allowing it to form a constructive base for that
 * more specific Pokemon class.
 *
 */

public class Item implements java.io.Serializable{
    private String identifier;
    private String owner;
    private String title;
    private String description;
    private int status;
    private ArrayList<Offer> offers;
    //private  Bid bids;

    public static final int UNAVAILABLE = 0;
    public static final int AVAILABLE = 1;
    public static final int BIDDING = 2;
    public static final int BORROWED = 3;
    public static final int RETURNED = 4;

    public Item(){
        this.identifier = "-1";
        this.owner = "default";
        this.title = "default";
        this.description = "default";
        this.status = AVAILABLE;
        this.offers = new ArrayList<Offer>();
        //this.bids=new Bid();
    }

    public Item(String identifier, String owner, String title, String description, ArrayList<Offer> offers){
        this.identifier = identifier;
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.status = AVAILABLE;
        this.offers = offers;
    }

    public String getOwner(){
        return this.owner;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public String getIdentifier(){
        return this.identifier;
    }
    public int getStatus(){
        return this.status;
    }
    public ArrayList<Offer> getOffers(){
        return this.offers;
    }


    public void setOwner(String newOwner){
        this.owner = newOwner;
    }
    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
    public void setIdentifier(String newIdentifier) { this.identifier = newIdentifier; }
    public void setStatus(int newStatus){ this.status = newStatus; }
    public void setOffers(ArrayList<Offer> newOffers){
        this.offers = newOffers;
    }
    public void addOffer(Offer offer){this.offers.add(offer);}

    public void addBid(Offer offer){
        this.offers.add(offer);
    }
    public void clearBids() {
        this.offers.clear();
    }



}
