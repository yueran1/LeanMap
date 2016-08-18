package sharingiscaring.sharingiscaring.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;

import java.io.ObjectInputStream;

import java.util.ArrayList;

import sharingiscaring.sharingiscaring.ElasticSearch.EsPokemonControl;
import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * @author rbrewer
 * @version 0.1
 * @since 04/03/16
 *
 * This is the nexus of the application's functionality.
 * From here, the user can access the list of all items in their possession, search for items to
 * borrow, or view & edit their profile information.
 *
 */

public class MainMenuActivity extends AppCompatActivity {
    private String UUID;
    private final String problemTag = "Add Pokemon Error";

    private ListView notificationList;
    private ArrayAdapter<String> notificationAdapter;
    private ArrayList<String> notifications = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notificationList = (ListView) findViewById(R.id.viewNotifications);
        notificationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notifications);
        notificationList.setAdapter(notificationAdapter);

        // Sets the current user's username at the top of the page
        TextView currentTrainer = (TextView) findViewById(R.id.currentTrainerMain);
        currentTrainer.setText("Current Trainer:  " + UserLocal.getInstance().getName());


    }



    //=============================Start Map activity==========================================//

    public void openMap(View view){

        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);

    }

    //===============================End Map activity==========================================//




    // Logs user out, returning to MainActivity where the LocalUser singleton is cleared of data.
    public void logout(View view) {
        finish();
    }

    public void myPC(View view) {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE ){
            connected=true;
            TextView textView = (TextView)findViewById(R.id.currentConnection);
            textView.setText("Connection:         Online");
        }
        else {
            connected = false;
            TextView textView = (TextView)findViewById(R.id.currentConnection);
            textView.setText("Connection:         Offline");
        }
        Intent intent = new Intent(this, MyPCActivity.class);
        intent.putExtra("connected",connected);
        startActivity(intent);
    }

    public void marketplace(View view) {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE ){
            connected=true;
            TextView textView = (TextView)findViewById(R.id.currentConnection);
            textView.setText("Connection:         Online");
        }
        else {
            connected = false;
            TextView textView = (TextView)findViewById(R.id.currentConnection);
            textView.setText("Connection:         Offline");
        }
        Intent intent = new Intent(this, MarketplaceActivity.class);
        if(connected){startActivity(intent);}
    }

    // Placeholder skeleton for the push updates button.
    //should the user be offline in order to interact with this button.
    public void pushUpdates(View view){
        UserLocal user = UserLocal.getInstance();
        // Iterate over the queue of tasks & execute each of them.
        String filename = "Single_Pokemon_File.data";

        //first try reads in serialized user object
        try {
            //copied from http://www.javacoffeebreak.com/articles/serialization/
            //the sub heading "Restoring objects from a serialized state"
            //Read from disk using FileInputStream
            FileInputStream inputStream = new FileInputStream(filename);

            //Read object using ObjectInputStream
            ObjectInputStream inputObject = new ObjectInputStream(inputStream);

            //Read an object
            Object obj = inputObject.readObject();

            if(obj instanceof UserLocal){
                user = (UserLocal) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //second try pushes all ArrayLists found in LocalUser and User; borrowedItems, Notifications, and ownedItems
        try {

            EsPokemonControl.AddPokemonTask addPokemon = new EsPokemonControl.AddPokemonTask();
            EsPokemonControl.UpdatePokemonTask updatePokemon = new EsPokemonControl.UpdatePokemonTask();
            //EsOfferControl.AddOfferTask addOffer = new EsOfferControl.AddOfferTask();

            for(int i=0;i<user.getOwnedItems().size();i++) {
                //EsPokemonControl.AddPokemonTask addPokemon = new EsPokemonControl.AddPokemonTask();
                if(user.getOwnedItems().get(i).getIdentifier().equals("")){
                    addPokemon.execute(user.getOwnedItems().get(i)); //get from LocalUser object
                    UUID = addPokemon.get();
                    user.getOwnedItems().get(i).setIdentifier(UUID);
                }
                else{
                    updatePokemon.execute(user.getOwnedItems().get(i));
                }
            }

            ArrayList<Pokemon> borrowedItems = user.getBorrowedItems();
            for(int i=0;i<borrowedItems.size();i++){
                //EsPokemonControl.AddPokemonTask addPokemon = new EsPokemonControl.AddPokemonTask();
                //also note this seems inconsistent with owned pokemon consider adding methods or
                //changing private to protected in UserLocal for borrowed items
                if(user.getBorrowedItems().get(i).getIdentifier().equals("")) {
                    addPokemon.execute(user.getBorrowedItems().get(i)); //get from LocalUser object
                    UUID = addPokemon.get();
                    user.getBorrowedItems().get(i).setIdentifier(UUID);
                }
                else {
                    updatePokemon.execute(user.getBorrowedItems().get(i));
                }
            }
            /*cant make offers, while offline double check and delete (check for new offers made by others)?
            for(int i=0;i<user.getNotifications().size();i++){ // can someone make sure I am using the methods correctly what makes an offer unique
                //EsOfferControl.AddOfferTask addOffer = new EsOfferControl.AddOfferTask();
                addOffer.execute(user.getNotifications().get(i));
            }*/

        } catch (Exception e) {
            Log.i(problemTag, "Did not upload Pokemon successfully.");
        }

        //user.addItem(addedPokemon);
        User updatingUser = new User(user);
        EsUserControl.UpdateUserTask updateUser = new EsUserControl.UpdateUserTask();

        //third try pushes the user to the server
        try {
            updateUser.execute(updatingUser);
        } catch (Exception e){
            Log.i(problemTag, "Did not update User successfully.");
        }

    }

    // Called from main_menu.xml, starts the TrainerCardActivity. Sends a boolean to it telling it
    // to display the current trainer's own profile (true). See TrainerCardActivity for more details.
    public void ownTrainerCard(View view) {
        // mixed from http://stackoverflow.com/questions/5474089/how-to-check-currently-internet-connection-is-available-or-not-in-android
        // http://stackoverflow.com/questions/32547006/connectivitymanager-getnetworkinfoint-deprecated
        // checks connection to the internet
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE ){
            connected=true;
            TextView textView = (TextView)findViewById(R.id.currentConnection);
            textView.setText("Connection:         Online");
        }
        else {
            connected = false;
            TextView textView = (TextView)findViewById(R.id.currentConnection);
            textView.setText("Connection:         Offline");
        }

        Intent intent = new Intent(this, TrainerCardActivity.class);
        boolean ownProfile = true;   // In this case, is going to user's own profile; therefore false.
        intent.putExtra("check", ownProfile);
        startActivity(intent);
    }

    public void damageCalculator(View view){
        Intent intent = new Intent(this,CalculateDisplayActivity.class);
        startActivity(intent);
    }

}
