package sharingiscaring.sharingiscaring.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sharingiscaring.sharingiscaring.DataStructure.Move;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.DataStructure.Type;
import sharingiscaring.sharingiscaring.ElasticSearch.EsMoveControl;
import sharingiscaring.sharingiscaring.ElasticSearch.EsPokemonControl;
import sharingiscaring.sharingiscaring.R;

/**
 * @author klark
 * @since 4/1/16.
 */
public class CalculateDisplayActivity extends AppCompatActivity {
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private TextView move;
    protected EsMoveControl baseDamage;
    private Type type;
    private Move moves;

    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.calculate_display);
        

        //"Pull" pokemon 1 and 2 from previous intents which don't exist yet.
    }

    /**takes the two pokemon found from previous search results and calculates the damage
     * each move would do to the other pokemon. I tried to have one event handler manage all
     * 8 textviews 
     * @param view
     */
    public void calculateDamage(View view) {
        //Structure of switch case to handle multiple textviews with one event handler came from:
        //http://stackoverflow.com/questions/7873480/android-one-onclick-method-for-multiple-buttons
        TextView percent = (TextView) findViewById(R.id.PercentDamage);
        double Damage;
        String stringDouble;


        switch (view.getId()) {
            case R.id.Move1:
                //code for text view 1 click
                EsMoveControl.GetMoveTask getMove = new EsMoveControl.GetMoveTask();
                getMove.execute(pokemon1.getMove1());
                try {
                    moves = getMove.get().get(0);
                } catch (Exception e) {
                    Log.i("ISSUES", "ISSUES");
                }

                if (moves.getAttackType().equals("physical")) {
                    Damage = (pokemon1.getAttack() / pokemon2.getDefense());
                } else {
                    Damage = (pokemon1.getSpecialAttack() / pokemon2.getSpecialDefense());
                }

                Damage = ((((2 * pokemon1.getLevel() + 10) / 250) * Damage * (moves.getDamagePower())) + 2) *
                        type.getSTAB(pokemon1.getPrimaryType(), pokemon1.getSecondaryType(), moves.getType()) *
                        type.typeEffectiveness(moves.getType(), pokemon2.getPrimaryType(), pokemon2.getSecondaryType());
                Damage = (int) Math.floor(Damage / pokemon2.getHealthPoints() * 100);
                stringDouble = Double.toString(Damage);
                percent.setText(stringDouble);
                break;

            case R.id.Move2:
                // code for text view 2 click
                EsMoveControl.GetMoveTask getMove2 = new EsMoveControl.GetMoveTask();
                getMove2.execute(pokemon1.getMove2());
                try {
                    moves = getMove2.get().get(0);
                } catch (Exception e) {
                    Log.i("ISSUES", "ISSUES");
                }

                if (moves.getAttackType().equals("physical")) {
                    Damage = (pokemon1.getAttack() / pokemon2.getDefense());
                } else {
                    Damage = (pokemon1.getSpecialAttack() / pokemon2.getSpecialDefense());
                }

                Damage = ((((2 * pokemon1.getLevel() + 10) / 250) * Damage * (moves.getDamagePower())) + 2) *
                        type.getSTAB(pokemon1.getPrimaryType(), pokemon1.getSecondaryType(), moves.getType()) *
                        type.typeEffectiveness(moves.getType(), pokemon2.getPrimaryType(), pokemon2.getSecondaryType());
                Damage = (int) Math.floor(Damage / pokemon2.getHealthPoints() * 100);
                stringDouble = Double.toString(Damage);
                percent.setText(stringDouble);
                break;

            case R.id.Move3:
                // code for text view 3 click
                EsMoveControl.GetMoveTask getMove3 = new EsMoveControl.GetMoveTask();
                getMove3.execute(pokemon1.getMove3());
                try {
                    moves = getMove3.get().get(0);
                } catch (Exception e) {
                    Log.i("ISSUES", "ISSUES");
                }

                if (moves.getAttackType().equals("physical")) {
                    Damage = (pokemon1.getAttack() / pokemon2.getDefense());
                } else {
                    Damage = (pokemon1.getSpecialAttack() / pokemon2.getSpecialDefense());
                }

                Damage = ((((2 * pokemon1.getLevel() + 10) / 250) * Damage * (moves.getDamagePower())) + 2) *
                        type.getSTAB(pokemon1.getPrimaryType(), pokemon1.getSecondaryType(), moves.getType()) *
                        type.typeEffectiveness(moves.getType(), pokemon2.getPrimaryType(), pokemon2.getSecondaryType());
                Damage = (int) Math.floor(Damage / pokemon2.getHealthPoints() * 100);
                stringDouble = Double.toString(Damage);
                percent.setText(stringDouble);
                break;

            case R.id.Move4:
                // code for text view 4 click
                EsMoveControl.GetMoveTask getMove4 = new EsMoveControl.GetMoveTask();
                getMove4.execute(pokemon1.getMove4());
                try {
                    moves = getMove4.get().get(0);
                } catch (Exception e) {
                    Log.i("ISSUES", "ISSUES");
                }

                if (moves.getAttackType().equals("physical")) {
                    Damage = (pokemon1.getAttack() / pokemon2.getDefense());
                } else {
                    Damage = (pokemon1.getSpecialAttack() / pokemon2.getSpecialDefense());
                }

                Damage = ((((2 * pokemon1.getLevel() + 10) / 250) * Damage * (moves.getDamagePower())) + 2) *
                        type.getSTAB(pokemon1.getPrimaryType(), pokemon1.getSecondaryType(), moves.getType()) *
                        type.typeEffectiveness(moves.getType(), pokemon2.getPrimaryType(), pokemon2.getSecondaryType());
                Damage = (int) Math.floor(Damage / pokemon2.getHealthPoints() * 100);
                stringDouble = Double.toString(Damage);
                percent.setText(stringDouble);
                break;
            case R.id.Move5:
                // code for text view 5 click
                EsMoveControl.GetMoveTask getMove5 = new EsMoveControl.GetMoveTask();
                getMove5.execute(pokemon2.getMove1());
                try {
                    moves = getMove5.get().get(0);
                } catch (Exception e) {
                    Log.i("ISSUES", "ISSUES");
                }

                if (moves.getAttackType().equals("physical")) {
                    Damage = (pokemon2.getAttack() / pokemon1.getDefense());
                } else {
                    Damage = (pokemon2.getSpecialAttack() / pokemon1.getSpecialDefense());
                }

                Damage = ((((2 * pokemon2.getLevel() + 10) / 250) * Damage * (moves.getDamagePower())) + 2) *
                        type.getSTAB(pokemon2.getPrimaryType(), pokemon2.getSecondaryType(), moves.getType()) *
                        type.typeEffectiveness(moves.getType(), pokemon1.getPrimaryType(), pokemon1.getSecondaryType());
                Damage = (int) Math.floor(Damage / pokemon1.getHealthPoints() * 100);
                stringDouble = Double.toString(Damage);
                percent.setText(stringDouble);
                break;

            case R.id.Move6:
                // code for text view 6 click
                EsMoveControl.GetMoveTask getMove6 = new EsMoveControl.GetMoveTask();
                getMove6.execute(pokemon2.getMove2());
                try {
                    moves = getMove6.get().get(0);
                } catch (Exception e) {
                    Log.i("ISSUES", "ISSUES");
                }

                if (moves.getAttackType().equals("physical")) {
                    Damage = (pokemon2.getAttack() / pokemon1.getDefense());
                } else {
                    Damage = (pokemon2.getSpecialAttack() / pokemon1.getSpecialDefense());
                }

                Damage = ((((2 * pokemon2.getLevel() + 10) / 250) * Damage * (moves.getDamagePower())) + 2) *
                        type.getSTAB(pokemon2.getPrimaryType(), pokemon2.getSecondaryType(), moves.getType()) *
                        type.typeEffectiveness(moves.getType(), pokemon1.getPrimaryType(), pokemon1.getSecondaryType());
                Damage = (int) Math.floor(Damage / pokemon1.getHealthPoints() * 100);
                stringDouble = Double.toString(Damage);
                percent.setText(stringDouble);
                break;

            case R.id.Move7:
                // code for text view 7 click
                EsMoveControl.GetMoveTask getMove7 = new EsMoveControl.GetMoveTask();
                getMove7.execute(pokemon2.getMove3());
                try {
                    moves = getMove7.get().get(0);
                } catch (Exception e) {
                    Log.i("ISSUES", "ISSUES");
                }

                if (moves.getAttackType().equals("physical")) {
                    Damage = (pokemon2.getAttack() / pokemon1.getDefense());
                } else {
                    Damage = (pokemon2.getSpecialAttack() / pokemon1.getSpecialDefense());
                }

                Damage = ((((2 * pokemon2.getLevel() + 10) / 250) * Damage * (moves.getDamagePower())) + 2) *
                        type.getSTAB(pokemon2.getPrimaryType(), pokemon2.getSecondaryType(), moves.getType()) *
                        type.typeEffectiveness(moves.getType(), pokemon1.getPrimaryType(), pokemon1.getSecondaryType());
                Damage = (int) Math.floor(Damage / pokemon1.getHealthPoints() * 100);
                stringDouble = Double.toString(Damage);
                percent.setText(stringDouble);
                break;

            case R.id.Move8:
                // code for text view 8 click
                EsMoveControl.GetMoveTask getMove8 = new EsMoveControl.GetMoveTask();
                getMove8.execute(pokemon2.getMove4());
                try {
                    moves = getMove8.get().get(0);
                } catch (Exception e) {
                    Log.i("ISSUES", "ISSUES");
                }

                if (moves.getAttackType().equals("physical")) {
                    Damage = (pokemon2.getAttack() / pokemon1.getDefense());
                } else {
                    Damage = (pokemon2.getSpecialAttack() / pokemon1.getSpecialDefense());
                }

                Damage = ((((2 * pokemon2.getLevel() + 10) / 250) * Damage * (moves.getDamagePower())) + 2) *
                        type.getSTAB(pokemon2.getPrimaryType(), pokemon2.getSecondaryType(), moves.getType()) *
                        type.typeEffectiveness(moves.getType(), pokemon1.getPrimaryType(), pokemon1.getSecondaryType());
                Damage = (int) Math.floor(Damage / pokemon1.getHealthPoints() * 100);
                stringDouble = Double.toString(Damage);
                percent.setText(stringDouble);
                break;
        }
    }
    public void selectPokemon1(View view){
        Intent intent = new Intent(this, CalculateSearchActivity.class);
        intent.putExtra("Tag",1);
        startActivityForResult(intent, 1);
    }
    public void selectPokemon2(View view){
        Intent intent = new Intent(this, CalculateSearchActivity.class);
        intent.putExtra("Tag",2);
        startActivityForResult(intent, 2);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == 1){
            Bundle bundle = data.getExtras();
            pokemon1=(Pokemon)bundle.getSerializable("SelectedPokemon");
            TextView pokemonName = (TextView)findViewById(R.id.Pokemon1);
            pokemonName.setText(pokemon1.getTitle());
            TextView owner = (TextView)findViewById(R.id.OwnerTag1);
            owner.setText("Owner: "+pokemon1.getOwner());
            TextView level = (TextView)findViewById(R.id.LevelTag1);
            level.setText("Lv. " + Integer.toString(pokemon1.getLevel()));
            TextView primaryType = (TextView)findViewById(R.id.Type1Pokemon1);
            primaryType.setText(pokemon1.getPrimaryType()+" / ");
            TextView secondaryType = (TextView)findViewById(R.id.Type2Pokemon1);
            secondaryType.setText(pokemon1.getSecondaryType());
            TextView move1 = (TextView)findViewById(R.id.Move1);
            move1.setText(pokemon1.getMove1());
            TextView move2 = (TextView)findViewById(R.id.Move2);
            move2.setText(pokemon1.getMove2());
            TextView move3 = (TextView)findViewById(R.id.Move3);
            move3.setText(pokemon1.getMove3());
            TextView move4 = (TextView)findViewById(R.id.Move4);
            move4.setText(pokemon1.getMove4());
        }
        if(resultCode == 2){
            Bundle bundle = data.getExtras();
            pokemon2=(Pokemon)bundle.getSerializable("SelectedPokemon");
            TextView pokemonName2 = (TextView)findViewById(R.id.Pokemon2);
            pokemonName2.setText(pokemon2.getTitle());
            TextView owner2 = (TextView)findViewById(R.id.OwnerTag2);
            owner2.setText("Owner: "+pokemon2.getOwner());
            TextView level2 = (TextView)findViewById(R.id.LevelTag2);
            level2.setText("Lv. " + Integer.toString(pokemon2.getLevel()));
            TextView primaryType2 = (TextView)findViewById(R.id.Type1Pokemon2);
            primaryType2.setText(pokemon2.getPrimaryType()+" / ");
            TextView secondaryType2 = (TextView)findViewById(R.id.Type2Pokemon2);
            secondaryType2.setText(pokemon2.getSecondaryType());
            TextView move5 = (TextView)findViewById(R.id.Move5);
            move5.setText(pokemon2.getMove1());
            TextView move6 = (TextView)findViewById(R.id.Move6);
            move6.setText(pokemon2.getMove2());
            TextView move7 = (TextView)findViewById(R.id.Move7);
            move7.setText(pokemon2.getMove3());
            TextView move8 = (TextView)findViewById(R.id.Move8);
            move8.setText(pokemon2.getMove4());
        }
    }
}


