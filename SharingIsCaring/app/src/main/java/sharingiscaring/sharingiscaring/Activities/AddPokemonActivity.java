package sharingiscaring.sharingiscaring.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import sharingiscaring.sharingiscaring.ElasticSearch.EsPokemonControl;
import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * @author hoye
 * @version 0.1
 * @since 03/03/16
 *
 */

public class AddPokemonActivity extends AppCompatActivity{
    private final String problemTag = "Add Pokemon Error";
    private Pokemon pokemon;    // The pokemon that we will assign to the user.

    // References to the resources onscreen
    private EditText name;
    private EditText description;
    private EditText ability;
    private EditText level;
    private EditText health;
    private EditText attack;
    private EditText defense;
    private EditText s_attack;
    private EditText s_defense;
    private EditText speed;
    private EditText type1;
    private EditText type2;
    private EditText move1;
    private EditText move2;
    private EditText move3;
    private EditText move4;
    private EditText nature;
    private EditText item;
    private Button add;
    private String UUID;
    private boolean connected;

    /**
     * Sets references to EditTexts.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pokemon);
        this.name = (EditText) findViewById(R.id.editNameAdd);
        this.description = (EditText) findViewById(R.id.editDescriptionAdd);
        this.ability = (EditText) findViewById(R.id.editAbilityAdd);
        this.level = (EditText) findViewById(R.id.editLevelAdd);
        this.health = (EditText) findViewById(R.id.editHPAdd);
        this.attack = (EditText) findViewById(R.id.editAttackAdd);
        this.defense = (EditText) findViewById(R.id.editDefenseAdd);
        this.s_attack = (EditText) findViewById(R.id.editSpAtkAdd);
        this.s_defense = (EditText) findViewById(R.id.editSpDefAdd);
        this.speed = (EditText) findViewById(R.id.editSpeedAdd);
        this.type1 = (EditText) findViewById(R.id.editType1Add);
        this.type2 = (EditText) findViewById(R.id.editType2Add);
        this.move1 = (EditText) findViewById(R.id.editMove1Add);
        this.move2 = (EditText) findViewById(R.id.editMove2Add);
        this.move3 = (EditText) findViewById(R.id.editMove3Add);
        this.move4 = (EditText) findViewById(R.id.editMove4Add);
        this.nature = (EditText) findViewById(R.id.editNatureAdd);
        this.item = (EditText) findViewById(R.id.editItemAdd);

        //change the button add to local variable
        this.add = (Button) findViewById(R.id.buttonAddAdd);

        Bundle bundle = getIntent().getExtras();
        connected= bundle.getBoolean("connected");
    }

    protected void onStart(){
        super.onStart();
    }

    /**
     * Ensures that user input that must be of integer type is such.
     * <p>
     * @param values An ArrayList of all user input values.
     * @return True/False depending on whether input is valid.
     */
    protected Boolean areValid(ArrayList<String> values){
        // TODO: Iterate over the array & ensure data is correct
        try {
            Integer.decode(values.get(3));
            Integer.decode(values.get(4));
            Integer.decode(values.get(5));
            Integer.decode(values.get(6));
            Integer.decode(values.get(7));
            Integer.decode(values.get(8));
            Integer.decode(values.get(9));
        } catch (Exception e){
            Log.i(problemTag, "Error Validating Input");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * Pulls info from the view, verifies it, creates the Pokemon, inserts it, and finishes.
     * @param view
     */
    public void savePokemon(View view) {
        Intent returnIntent = new Intent();
        ArrayList<String> stringValues = new ArrayList<String>();

        String nameText = name.getText().toString();
        String descriptionText = description.getText().toString();
        String abilityText = ability.getText().toString();
        String levelText = level.getText().toString();
        String healthText = health.getText().toString();
        String attackText = attack.getText().toString();
        String defenseText = defense.getText().toString();
        String s_attackText = s_attack.getText().toString();
        String s_defenseText = s_defense.getText().toString();
        String speedText = speed.getText().toString();
        String type1Text = type1.getText().toString();
        String type2Text = type2.getText().toString();
        String move1Text = move1.getText().toString();
        String move2Text = move2.getText().toString();
        String move3Text = move3.getText().toString();
        String move4Text = move4.getText().toString();
        String natureText = nature.getText().toString();
        String itemText = item.getText().toString();

        stringValues.add(nameText);
        stringValues.add(descriptionText);
        stringValues.add(abilityText);
        stringValues.add(levelText);
        stringValues.add(healthText);
        stringValues.add(attackText);
        stringValues.add(defenseText);
        stringValues.add(s_attackText);
        stringValues.add(s_defenseText);
        stringValues.add(speedText);
        stringValues.add(type1Text);
        stringValues.add(type2Text);
        stringValues.add(move1Text);
        stringValues.add(move2Text);
        stringValues.add(move3Text);
        stringValues.add(move4Text);
        stringValues.add(natureText);
        stringValues.add(itemText);


        Log.i("Connectivity", Boolean.toString(connected));
        // Call areValid to confirm the stringvalues are indeed valid. If so, create a Pokemon and
        // pass it to the user. For now, we also return the stringValues array to the previous intent
        // as described below. This may change as we all pulling Pokemon information from ElasticSearch.
        if (areValid(stringValues)) {
            UserLocal user = UserLocal.getInstance();

            Pokemon addedPokemon = new Pokemon("", user.getName(), nameText, descriptionText, null, abilityText,
                    Integer.decode(levelText), Integer.decode(healthText),
                    Integer.decode(attackText), Integer.decode(defenseText),
                    Integer.decode(s_attackText), Integer.decode(s_defenseText),
                    Integer.decode(speedText), type1Text, type2Text, move1Text,
                    move2Text, move3Text, move4Text, natureText, itemText);
            user.addItem(addedPokemon);
            User updatingUser = new User(user);

            if(connected) {
                try {
                    EsPokemonControl.AddPokemonTask addPokemon = new EsPokemonControl.AddPokemonTask();
                    addPokemon.execute(addedPokemon);
                } catch (Exception e) {
                    Log.i(problemTag, "Did not upload Pokemon successfully.");
                }


                EsUserControl.UpdateUserTask updateUser = new EsUserControl.UpdateUserTask();

                try {
                    updateUser.execute(updatingUser);
                } catch (Exception e) {
                    Log.i(problemTag, "Did not update User successfully.");
                }
            }


            // After checking the validity of the strings, add the array of strings to the return
            // intent, tell it you are returning data (RESULT_OK), and finish, returning back to
            // MyPCActivity, where it will use the array to create a new entry in its ListView.
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }
}
