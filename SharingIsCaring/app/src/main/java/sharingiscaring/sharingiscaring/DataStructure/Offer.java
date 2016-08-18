package sharingiscaring.sharingiscaring.DataStructure;

/**
 * @author hoye
 * @version 0.1
 * @since 10/02/16
 *
 */

public class Offer implements java.io.Serializable{
    private String bidder;
    private String owner;
    private String ItemUUID;
    private Double rate;

    public Offer(){

    }

    /**
     * This constructor allows for D.I.
     * @param bidder The User that has made the bid.
     * @param rate The amount that the User has offered.
     */

    public Offer(String bidder, String owner, String UUID, double rate){
        this.bidder = bidder;
        this.owner = owner;
        this.rate = rate;
        this.ItemUUID = UUID;
    }

    public String getBidder(){
        return this.bidder;
    }
    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getItemUUID() {return this.ItemUUID; }
    public void setItemUUID(String UUID) { this.ItemUUID = UUID; }

    public Double getRate(){
        return this.rate;
    }
    public void setRate(double newRate){
        this.rate = newRate;
    }
}
