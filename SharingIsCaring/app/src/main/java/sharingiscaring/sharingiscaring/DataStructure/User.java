package sharingiscaring.sharingiscaring.DataStructure;

import java.util.ArrayList;

/**
 * @author hoye
 * @version 0.1
 * @since 10/02/16
 *
 * This class lets us track information about the users of our application.
 *
 * In save(), code for serialization taken from:
 * http://www.tutorialspoint.com/java/java_serialization.htm
 * Date: March 31, 2016
 */

public class User implements java.io.Serializable {
    protected String name;
    protected String password;
    protected ArrayList<Pokemon> ownedItems;
    protected ArrayList<Pokemon> borrowedItems;
    protected ArrayList<Offer> notifications;
    protected String contact;
    protected String phone;
    protected String email;
    protected String streetAddress;
    protected String city;
    protected String province;
    protected String postalCode;
    protected String UUID;
    protected String connected;

    /**
     * This is the default constructor.  Makes it easy to perform tests.
     */
    public User(){
        this.UUID = "";
        this.name = "default";
        this.ownedItems = new ArrayList<Pokemon>();
        this.borrowedItems = new ArrayList<Pokemon>();
        this.contact = "default";
        this.phone = "default";
        this.email = "default";
        this.streetAddress = "default";
        this.city = "default";
        this.province = "default";
        this.postalCode = "default";
        this.connected = "online";
    }

    public User(UserLocal userLocal){
        this.UUID = userLocal.getUUID();
        this.name = userLocal.getName();
        this.password = userLocal.getPassword();
        this.ownedItems = userLocal.getOwnedItems();
        this.borrowedItems = userLocal.getBorrowedItems();
        this.notifications = userLocal.getNotifications();
        this.contact = userLocal.getContact();
        this.phone = userLocal.getPhone();
        this.email = userLocal.getEmail();
        this.streetAddress = userLocal.getStreetAddress();
        this.city = userLocal.getCity();
        this.province = userLocal.getProvince();
        this.postalCode = userLocal.getPostalCode();
        this.connected = userLocal.getConnectionState();
    }
    /**
     * This constructor permits dependency injection.
     * @param name
     * @param password
     * @param email
     * @param phone
     * @param address
     * @param city
     * @param province
     * @param postalCode
     */
    public User(String name, String password, String email, String phone, String address,
                String city, String province, String postalCode){
        this.UUID = "";
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.streetAddress = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.contact = "default";                   // TODO: do we need this parameter?
        this.ownedItems = new ArrayList<Pokemon>();
        this.borrowedItems = new ArrayList<Pokemon>();
        this.notifications = new ArrayList<Offer>();
    }

    public User(String name, String password, String email, String phone, String address,
                String city, String province, String postalCode, ArrayList<Pokemon> ownedItems, ArrayList<Pokemon> borrowedItems, ArrayList<Offer> notifications){
        this.UUID = "";
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.streetAddress = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.contact = "default";                   // TODO: do we need this parameter?
        this.ownedItems = ownedItems;
        this.borrowedItems = borrowedItems;
        this.notifications = notifications;
    }


    public String getUUID() { return this.UUID; }
    public String getName(){return this.name;}
    public String getPassword(){return this.password;}
    public ArrayList<Pokemon> getOwnedItems(){return this.ownedItems;}
    public ArrayList<Pokemon> getBorrowedItems() {return this.borrowedItems;}
    public ArrayList<Offer> getNotifications() {return this.notifications;}
    public String getContact(){return this.contact;}
    public String getPhone(){return this.phone;}
    public String getEmail(){return this.email;}
    public String getStreetAddress(){return this.streetAddress;}
    public String getCity(){return this.city;}
    public String getProvince(){return this.province;}
    public String getPostalCode(){return this.postalCode;}
    public String getConnectionState(){return "online";}

    public void setUUID(String UUID){ this.UUID = UUID; }
    public void setName(String name) {this.name = name;}
    public void setPassword(String password){this.password = password;}
    public void setOwnedItems(ArrayList<Pokemon> newItems){ this.ownedItems = newItems;}
    public void setBorrowedItems(ArrayList<Pokemon> newItems){ this.borrowedItems = newItems;}
    public void setNotifications(ArrayList<Offer> notifications) { this.notifications = notifications;}
    public void setContact(String newContact){this.contact = newContact;}
    public void setPhone(String newPhone){this.phone = newPhone;}
    public void setEmail(String newEmail){this.email = newEmail;}
    public void setStreetAddress(String newAddress){this.streetAddress = newAddress;}
    public void setCity(String newCity){this.city = newCity;}
    public void setProvince(String newProvince){this.province = newProvince;}
    public void setPostalCode(String newPostalCode){this.postalCode = newPostalCode;}
    public void setConnectionState(String newConnectionState){this.connected = newConnectionState;}

    public void addItem(Pokemon item){this.ownedItems.add(item);}
    public void removeItem(Item item) {this.ownedItems.remove(item);}
    public void updatePokemon(int index, Pokemon pokemon){
        this.ownedItems.set(index, pokemon);
    }

    public void updatePokemon(Pokemon pokemon){
        for (int i = 0; i < ownedItems.size(); i++){
            Pokemon poke = ownedItems.get(i);
            if (poke.getIdentifier().equals(pokemon.getIdentifier())){
                ownedItems.set(i, pokemon);
            }
        }
    }

    public void deletePokemon(Pokemon pokemon){
        for (int i = 0; i < ownedItems.size(); i++){
            Pokemon poke = ownedItems.get(i);
            if (poke.getIdentifier().equals(pokemon.getIdentifier())){
                ownedItems.remove(i);
            }
        }
    }

    public void borrow(Pokemon pokemon){
        borrowedItems.add(pokemon);
    }
}
