package sharingiscaring.sharingiscaring.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ViewFlipper;

import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.TrainerCard.TrainerCardState;
import sharingiscaring.sharingiscaring.TrainerCard.TrainerCardStateOther;
import sharingiscaring.sharingiscaring.TrainerCard.TrainerCardStateOwn;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * @author rbrewer
 * @version 0.1
 * @since 04/03/16
 *
 * This displays a user's information. If the current user, allows editing and saving of this information.
 * Uses a ViewFlipper to display the appropriate .xml depending on a boolean value passed to this activity.
 *      true: displays trainer's own profile
 *      false: displays another user's profile
 *
 */

public class TrainerCardActivity extends AppCompatActivity {
    private TrainerCardState state;
    private String name;
    private UserLocal currentUser;
    private User otherUser;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_card);
        viewFlipper = (ViewFlipper) findViewById(R.id.pokemonViewFlipper);

        currentUser = UserLocal.getInstance();
        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        setView(ownProfileCheck());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private boolean ownProfileCheck(){
        return currentUser.getName().equalsIgnoreCase(currentUser.getViewedPokemon().getOwner());
    }
    // Uses the ViewFlipper (found in activity_trainer_card), to select the appropriate view to
    // display. The boolean assigned to each view is as follows:
    //      true: trainer_card_own
    //      false: trainer_card_other
    //
    // The check boolean is passed from a previous intent, and is gathered via getExtra.
    // The ViewFlipper simply checks if the boolean is false or not. If it is, it flips once to
    // the other trainer's view. Otherwise, it remains on the current trainer's own view.
    // The view is then populated via the appropriate function call below.
    private void setView(Boolean ownProfileCheck) {
        if(!ownProfileCheck()){

            EsUserControl.GetUsersTask getUsersTask = new EsUserControl.GetUsersTask();
            getUsersTask.execute(currentUser.getViewedPokemon().getOwner());
            try {
                otherUser = getUsersTask.get().get(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            viewFlipper.showNext();
            // otherUser = ???
            state = new TrainerCardStateOther(TrainerCardActivity.this, otherUser);
        }
        else {
            // currentUser = UserLocal.getInstance();
            state = new TrainerCardStateOwn(TrainerCardActivity.this);
        }
        state.populate();
    }

    // Saves the values in the current user's EditText's to the UserLocal singleton.
    public void saveTrainerCard(View view) {
        state.save();
        finish();
    }
}
