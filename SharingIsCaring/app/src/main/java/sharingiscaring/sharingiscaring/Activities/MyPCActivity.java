package sharingiscaring.sharingiscaring.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.ListItem;
import sharingiscaring.sharingiscaring.DataStructure.ListItemAdapter;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.Serializer;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * @author rbrewer
 * @version 0.1
 * @since 04/03/16
 *
 * This allows a user to view all of their Pokemon and all of other user's Pokemon they're
 * interacting with. Passes information to AddPokemonActivity, and on return, creates an entry in
 * the MyBox ListView for that Pokemon.
 *
 * Resource for selecting items from listview:
 * http://www.i-programmer.info/programming/android/7849-android-adventures-listview-and-adapters.html?start=1
 * Date: March 25, 2015
 */

public class MyPCActivity extends AppCompatActivity {

    private UserLocal user;
    private ListView borrowedBoxList;
    private ListView myBoxList;
    private ListItemAdapter borrowedAdapter;
    private ListItemAdapter myAdapter;
    private ArrayList<ListItem> borrowedPokemonListItems = new ArrayList<ListItem>();
    private ArrayList<ListItem> myPokemonListItems = new ArrayList<ListItem>();
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pc);
        //variable from main menu activity telling us whether or not we are connected
        Bundle bundle = getIntent().getExtras();
        connected = bundle.getBoolean("connected");

        user = UserLocal.getInstance();

        // Define the ListView, the ArrayList and .xml for its adapter, and set the adapter
        // for both the Borrowed Box ListView and My Box ListView.
        borrowedBoxList = (ListView) findViewById(R.id.viewBorrowedBox);
        borrowedAdapter = new ListItemAdapter(this, borrowedPokemonListItems);
        borrowedBoxList.setOnItemClickListener(viewBorrowedPokemon);
        borrowedBoxList.setAdapter(borrowedAdapter);
        myBoxList = (ListView) findViewById(R.id.viewMyBox);
        myAdapter = new ListItemAdapter(this, myPokemonListItems);
        myBoxList.setOnItemClickListener(viewMyPokemon);
        myBoxList.setAdapter(myAdapter);
        updateBoxLists();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // First clears, and then updates, the current "boxes" with their appropriate Pokemon
    private void updateBoxLists() {
        myPokemonListItems.clear();
        ArrayList<Pokemon> myPokemon = user.getOwnedItems();
        for(int i = 0; i < myPokemon.size(); i++) {
            myPokemonListItems.add(fillBoxSlot(myPokemon.get(i)));
        }
        myAdapter.notifyDataSetChanged();


        borrowedPokemonListItems.clear();
        ArrayList<Pokemon> borrowedPokemon = user.getBorrowedItems();
        for(int i = 0; i < borrowedPokemon.size(); i++) {
            borrowedPokemonListItems.add(fillBoxSlot(borrowedPokemon.get(i)));
        }
        borrowedAdapter.notifyDataSetChanged();

    }

    public ListItem fillBoxSlot(Pokemon pokemon){
        ListItem listItem;
        String name = pokemon.getTitle();
        String offer = "";
        String status = "Unavailable";
        String description = pokemon.getDescription();

        switch (pokemon.getStatus()){
            case (Item.AVAILABLE):
                status = "Available";
                break;

            case (Item.BIDDING):
                status = "Pending";
                Double rate = 0.00;
                for(int j = 0; j < pokemon.getOffers().size(); j++) {
                    if (pokemon.getOffers().get(j).getBidder() == user.getName()) {
                        if (pokemon.getOffers().get(j).getRate() >= rate) {
                            rate = pokemon.getOffers().get(j).getRate();
                        }
                    }
                }
                offer = "$" + Double.toString(rate) + "/hr";
                break;

            case (Item.BORROWED):
                status = "Borrowed";
                offer = "$" + Double.toString(pokemon.getOffers().get(0).getRate()) + "/hr";
                break;

            case (Item.RETURNED):
                status += "Unavailable";
                break;

            case (Item.UNAVAILABLE):
                status = "Unavailable";
                break;

            default:
                break;
        }

        listItem = new ListItem(name, offer, status, description);
        return listItem;
    }

    // Called from listitem_item.xml starts the ViewPokemonActivity. Sends the availability status
    // of the current Pokemon to that activity, which will then determine which view to display based
    // on it. See ViewPokemonActivity for details on which status is represented by which integer.
    private AdapterView.OnItemClickListener viewBorrowedPokemon = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
            Intent intent = new Intent(MyPCActivity.this, ViewPokemonActivity.class);

            int status = user.getBorrowedItems().get(position).getStatus();
            String UUID = user.getBorrowedItems().get(position).getIdentifier();

            // Assign the correct value to status based on ownership.
            switch (status){
                case 1: status = 3;
                    break;
                case 3: status = 4;
                    break;
                default:
                    break;
            }

            user.setViewedPokemon(user.getBorrowedItems().get(position));
            intent.putExtra("status", status);
            intent.putExtra("UUID", UUID);
            intent.putExtra("position", position);
            startActivityForResult(intent, 1);
        }
    };

    private AdapterView.OnItemClickListener viewMyPokemon = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
            Intent intent = new Intent(MyPCActivity.this, ViewPokemonActivity.class);

            int status = user.getOwnedItems().get(position).getStatus();
            String UUID = user.getOwnedItems().get(position).getIdentifier();

            switch (status){
                case 0: break;
                case 1: break;
                case 2: status = 1;
                    break;
                case 3: status = 2;
                    break;
                case 4: status = 0;
                    break;
                default:
                    break;
            }

            user.setViewedPokemon(user.getOwnedItems().get(position));
            intent.putExtra("status", status);
            intent.putExtra("UUID", UUID);
            intent.putExtra("position", position);
            startActivityForResult(intent, 1);
        }
    };

    // Called from my_pc.xml, starts the AddPokemonActivity. When it finishes, uses the data passed
    // from AddPokemonActivity to create a new entry in My Box, with the status "Available".
    // onActivityResult is called automatically, and can be considered an extension of this function.
    // Note: Clicking on a bid entry will take you to ViewPokemonActivity. This is specified in
    // listitem_iteml
    public void addPokemon(View view){
        Intent intent = new Intent(this,AddPokemonActivity.class);
        intent.putExtra("connected",connected);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent extras) {
        Serializer serializer = new Serializer(getApplicationContext());
        serializer.save();
        updateBoxLists();
    }
}

