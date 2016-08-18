package sharingiscaring.sharingiscaring.ViewPokemon;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.R;

/**
 * Created by Ryan on 2016-03-21.
 */
public class ViewPokemonStateOwnedBorrowed extends ViewPokemonState {
    private final Context context;
    private Pokemon item;
    private final TextView pokeView;
    private final TextView currentTrainer;
    private final TextView description;
    private final TextView level;
    private final TextView type1;
    private final TextView type2;
    private final TextView nature;
    private final TextView ability;
    private final TextView pokeItem;
    private final TextView hp;
    private final TextView attack;
    private final TextView defense;
    private final TextView spAtk;
    private final TextView spDef;
    private final TextView speed;
    private final TextView move1;
    private final TextView move2;;
    private final TextView move3;
    private final TextView move4;

    public ViewPokemonStateOwnedBorrowed(Context context, Pokemon pokemon) {
        Activity thisActivity = (Activity) context;
        this.context = context;
        item = pokemon;

        pokeView = (TextView) ((Activity) context).findViewById(R.id.editNameOB);
        currentTrainer = (TextView) ((Activity) context).findViewById(R.id.editOwnerOB);
        description = (TextView) ((Activity) context).findViewById(R.id.editDescriptionOB);
        level = (TextView) thisActivity.findViewById(R.id.editLevelOB);
        type1 = (TextView) thisActivity.findViewById(R.id.editType1OB);
        type2 = (TextView) thisActivity.findViewById(R.id.editType2OB);
        nature = (TextView) thisActivity.findViewById(R.id.editNatureOB);
        ability = (TextView) thisActivity.findViewById(R.id.editAbilityOB);
        pokeItem = (TextView) thisActivity.findViewById(R.id.editItemOB);
        hp = (TextView) thisActivity.findViewById(R.id.editHPOB);
        attack = (TextView) thisActivity.findViewById(R.id.editAttackOB);
        defense = (TextView) thisActivity.findViewById(R.id.editDefenseOB);
        spAtk = (TextView) thisActivity.findViewById(R.id.editSpAtkOB);
        spDef = (TextView) thisActivity.findViewById(R.id.editSpDefOB);
        speed = (TextView) thisActivity.findViewById(R.id.editSpeedOB);
        move1 = (TextView) thisActivity.findViewById(R.id.editMove1OB);
        move2 = (TextView) thisActivity.findViewById(R.id.editMove2OB);
        move3 = (TextView) thisActivity.findViewById(R.id.editMove3OB);
        move4 = (TextView) thisActivity.findViewById(R.id.editMove4OB);
    }

    public void populate() {
        pokeView.setText(item.getTitle());
        currentTrainer.setText(item.getOwner());
        description.setText(item.getDescription());
        level.setText(Integer.toString(item.getLevel()));
        type1.setText(item.getPrimaryType());
        type2.setText(item.getSecondaryType());
        nature.setText(item.getNature());
        ability.setText(item.getAbility());
        pokeItem.setText(item.getPokemonItem());
        hp.setText(Integer.toString(item.getHealthPoints()));
        attack.setText(Integer.toString(item.getAttack()));
        defense.setText(Integer.toString(item.getDefense()));
        spAtk.setText(Integer.toString(item.getSpecialAttack()));
        spDef.setText(Integer.toString(item.getSpecialDefense()));
        speed.setText(Integer.toString(item.getSpeed()));
        move1.setText(item.getMove1());
        move2.setText(item.getMove2());
        move3.setText(item.getMove3());
        move4.setText(item.getMove4());
    }
    public Pokemon getItem(){
        return this.item;
    }
}
