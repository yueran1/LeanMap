package sharingiscaring.sharingiscaring.DataStructure;

/**
 * Created by Ryan on 2016-04-03.
 */
public class ListItem {
    private String name;
    private String offer;
    private String status;
    private String description;

    public ListItem(String name, String offer, String status, String description) {
        this.name = name;
        this.offer = offer;
        this.status = status;
        this.description = description;
    }

    public String getName() { return name; }
    public String getOffer() { return offer; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
}

