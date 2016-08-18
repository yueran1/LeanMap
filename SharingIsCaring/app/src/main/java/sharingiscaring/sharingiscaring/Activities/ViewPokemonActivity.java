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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.DataStructure.ListOffer;
import sharingiscaring.sharingiscaring.DataStructure.ListOfferAdapter;
import sharingiscaring.sharingiscaring.DataStructure.Offer;
import sharingiscaring.sharingiscaring.DataStructure.Serializer;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.ElasticSearch.EsOfferControl;
import sharingiscaring.sharingiscaring.ElasticSearch.EsPokemonControl;
import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;
import sharingiscaring.sharingiscaring.ViewPokemon.ViewPokemonState;
import sharingiscaring.sharingiscaring.ViewPokemon.ViewPokemonStateOtherAvailable;
import sharingiscaring.sharingiscaring.ViewPokemon.ViewPokemonStateOtherBorrowed;
import sharingiscaring.sharingiscaring.ViewPokemon.ViewPokemonStateOwnedAvailable;
import sharingiscaring.sharingiscaring.ViewPokemon.ViewPokemonStateOwnedBorrowed;
import sharingiscaring.sharingiscaring.ViewPokemon.ViewPokemonStateOwnedUnavailable;

/**
 * @author rbrewer
 * @version 0.1
 * @since 04/03/16
 *
 * This displays a Pokemon's information.
 * Uses a ViewFlipper to display the appropriate .xml depending on an integer value passed to this activity.
 *      0: displays a trainer's own Pokemon which is not on the Marketplace
 *      1: displays a trainer's own Pokemon which is on the Marketplace
 *      2: displays a trainer's own Pokemon which is currently borrowed by another trainer
 *      3: displays another trainer's pokemon on the Marketplace
 *      4: displays another trainer's pokemon the current trainer has borrowed
 *
 * Based on which of these is currently displayed, different options and buttons will be available
 * to the user. Information can only be edited in the OWNED_UNAVAILABLE (0) state.
 *
 * Video Tutorial on introducing photo resource:
 * https://www.youtube.com/watch?v=S8E5GdF0RBA&feature=youtu.be
 * Date: April 1, 2016
 *
 */

public class ViewPokemonActivity extends AppCompatActivity {
    private ViewPokemonState state;
    private UserLocal user;
    private Pokemon pokemon;
    private int status;
    private int desiredStatus;
    private int index;
    private String UUID;
    private ViewFlipper viewFlipper;

    private ListView offerList;
    private ListOfferAdapter offerAdapter;
    private ArrayList<ListOffer> listOffers;
    private ArrayList<Offer> offers;

    public static final int OWNED_UNAVAILABLE = 0;
    public static final int OWNED_AVAILABLE = 1;
    public static final int OWNED_BORROWED = 2;
    public static final int OTHER_AVAILABLE = 3;
    public static final int OTHER_BORROWED = 4;

    ImageView attachPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pokemon);
        viewFlipper = (ViewFlipper) findViewById(R.id.pokemonViewFlipper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Bundle extras = getIntent().getExtras();

        this.user = UserLocal.getInstance();
        //EsPokemonControl.GetPokemonTask getPokemonTask = new EsPokemonControl.GetPokemonTask();
        //EsPokemonControl.GetPokemonTask.execute(extras.getString("UUID"));
        this.pokemon = user.getViewedPokemon();
        this.status = pokemon.getStatus();

        String ownerName = pokemon.getOwner();
        String userName = user.getName();
        if (ownerName.equalsIgnoreCase(userName)){
            switch (pokemon.getStatus()){
                case Item.AVAILABLE:
                    this.desiredStatus = this.OWNED_AVAILABLE;
                    break;
                case Item.UNAVAILABLE:
                    this.desiredStatus = this.OWNED_UNAVAILABLE;
                    break;
                case Item.BORROWED:
                    this.desiredStatus = this.OWNED_BORROWED;
                    break;
                default:
                    Log.i("View Pokemon", "We should not be here!");
            }
        } else {
            switch (pokemon.getStatus()){
                case Item.AVAILABLE:
                    this.desiredStatus = this.OTHER_AVAILABLE;
                    break;
                case Item.BIDDING:
                    this.desiredStatus = this.OTHER_AVAILABLE;
                    break;
                case Item.BORROWED:
                    this.desiredStatus = this.OTHER_BORROWED;
                    break;
                default:
                    break;
            }
        }

        setView2(this.status);

        setSupportActionBar(toolbar);


        //=============This is photo part===================//
        attachPhotoView=(ImageView) findViewById(R.id.photoOB);

    }

    public void photo(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);

    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        if (resCode==RESULT_OK){
            if (resCode == 1)
                attachPhotoView.setImageURI(null);
                attachPhotoView.setImageURI(data.getData());
                //attachPhotoView.notify();
        }

    }

    // Uses the ViewFlipper (found in activity_view_pokemon), to select the appropriate view to
    // display. The integer assigned to each view is as follows:
    //      0: OWNED_UNAVAILABLE
    //      1: OWNED_AVAILABLE
    //      2: OWNED_BORROWED
    //      3: OTHER_AVAILABLE/pending
    //      4: OTHER_BORROWED
    //
    // The status integer is passed from a previous intent, and is gathered via getExtra.
    // The ViewFlipper simply uses a for loop to flip the appropriate number of times (using the
    // status integer) to the desired view.
    // setContentView(R.layout.activity_view_pokemon);
    private void setView(int current_status, int desired_status){
        for (int i = 0; i < current_status; i++){
            viewFlipper.showPrevious();
        }
        for (int i = 0; i < desired_status; i++) {
            viewFlipper.showNext();
        }

        this.status = desired_status;
        if(this.status == OWNED_UNAVAILABLE) {
            state = new ViewPokemonStateOwnedUnavailable(ViewPokemonActivity.this, pokemon);
        }
        if(this.status == OWNED_AVAILABLE) {
            state = new ViewPokemonStateOwnedAvailable(ViewPokemonActivity.this, pokemon);
        }
        if(this.status == OWNED_BORROWED) {
            state = new ViewPokemonStateOwnedBorrowed(ViewPokemonActivity.this, pokemon);
        }
        if(this.status == OTHER_AVAILABLE) {
            state = new ViewPokemonStateOtherAvailable(ViewPokemonActivity.this, pokemon);
        }
        if(this.status == OTHER_BORROWED) {
            state = new ViewPokemonStateOtherBorrowed(ViewPokemonActivity.this, pokemon);
        }
        state.populate();
    }

    private void setView2(int desired_status){
        for (int i = 0; i < desired_status; i++) {
            viewFlipper.showNext();
        }

        this.status = desired_status;
        if(this.status == OWNED_UNAVAILABLE) {
            state = new ViewPokemonStateOwnedUnavailable(ViewPokemonActivity.this, pokemon);
        }
        if(this.status == OWNED_AVAILABLE) {
            state = new ViewPokemonStateOwnedAvailable(ViewPokemonActivity.this, pokemon);
        }
        if(this.status == OWNED_BORROWED) {
            state = new ViewPokemonStateOwnedBorrowed(ViewPokemonActivity.this, pokemon);
        }
        if(this.status == OTHER_AVAILABLE) {
            state = new ViewPokemonStateOtherAvailable(ViewPokemonActivity.this, pokemon);
        }
        if(this.status == OTHER_BORROWED) {
            state = new ViewPokemonStateOtherBorrowed(ViewPokemonActivity.this, pokemon);
        }

        state.populate();
    }

    // Called from main_menu.xml, starts the TrainerCardActivity. Sends a boolean to it telling it
    // to display another trainer's profile (false). See TrainerCardActivity for more details.
    public void otherTrainerCard(View view){
        Intent intent = new Intent(this, TrainerCardActivity.class);
        boolean ownProfile = false;   // In this case, is going to another user's profile; therefore false.
        intent.putExtra("check", ownProfile);
        startActivity(intent);
    }

    // TODO: Make changes persistent; also, shouldn't probably finish the activity. This is
    // TODO: placeholder to give the button some purpose, for now.
    public void savePokemon(View view){
        pokemon = state.getItem();
        update(pokemon);
        finish();
    }

    public void deletePokemon(View view){
        user.deletePokemon(pokemon);
        EsUserControl.UpdateUserTask updateUserTask = new EsUserControl.UpdateUserTask();
        updateUserTask.execute(new User(user));
        EsPokemonControl.DeletePokemonTask deletePokemonTask = new EsPokemonControl.DeletePokemonTask();
        deletePokemonTask.execute(pokemon);
        finish();
    }

    // These next two functions currently only use the viewFlipper to switch between each other.
    // TODO: Actually change the state of the current Pokemon Item. Make changes persistent.
    public void makeAvailable(View view){
        pokemon.setStatus(Item.AVAILABLE);
        update(pokemon);
        finish();
    }

    public void makeUnavailable(View view){
        pokemon.setStatus(Item.UNAVAILABLE);
        update(pokemon);
        finish();
    }

    // Changes the current status to "Pending" from available by gathering the ID of the TextView to
    // change, and using setText to change it. Grabs the new status value from strings.xml
    // TODO: Needs to save the amount in the current bid's EditText. Also needs to save the status
    // TODO: as a boolean somehow, and on load when status = 3, use it to determine if the status
    // TODO: is availabe, or pending.
    public void makeOffer(View view){
        TextView otherStatus = (TextView)findViewById(R.id.editStatusTA);
        EditText rateText = (EditText)findViewById(R.id.editOfferTA);

        double rate = Double.parseDouble(rateText.getText().toString());
        pokemon.setStatus(Item.BIDDING);


        Offer offer = new Offer(user.getName(), pokemon.getOwner(), pokemon.getIdentifier(), rate);
        EsOfferControl.AddOfferTask addOfferTask = new EsOfferControl.AddOfferTask();
        addOfferTask.execute(offer);
        otherStatus.setText(R.string.status_pending);
        pokemon.addOffer(offer);
        update(pokemon);
        finish();
    }

    // TODO: Will change status to Unavailable. Should make changes persistent and then finish,
    // TODO: As user should not be able to see another user's unavailable Pokemon.
    public void returnPokemon(View view){
        finish();
    }

    public void update(Pokemon pokemon){
        Log.i("Flippers tag", pokemon.getIdentifier());
        EsPokemonControl.UpdatePokemonTask updatePokemonTask = new EsPokemonControl.UpdatePokemonTask();
        updatePokemonTask.execute(pokemon);
        if (user.getName().equals(pokemon.getOwner())) {
            user.updatePokemon(index, pokemon);
            EsUserControl.UpdateUserTask updateUser = new EsUserControl.UpdateUserTask();
            updateUser.execute(new User(user));
            Serializer serializer = new Serializer(getApplicationContext());
            serializer.save();
        } else {
            User temp = new User();
            temp.updatePokemon(pokemon);
            EsUserControl.GetUsersTask getUsersTask = new EsUserControl.GetUsersTask();
            getUsersTask.execute(pokemon.getOwner());
            try {
                temp = getUsersTask.get().get(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void acceptOffer(View view){



    }
    public void declineOffer(View view){}
}
