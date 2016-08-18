package sharingiscaring.sharingiscaring.ViewPokemon;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.Offer;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * Created by Ryan on 2016-03-21.
 */
public class ViewPokemonStateOtherBorrowed extends ViewPokemonState {
    private final Context context;
    private Pokemon item;
    private Float currentBid;
    private final TextView pokeView;
    private final TextView otherTrainer;
    private final TextView bid;
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

    public ViewPokemonStateOtherBorrowed(Context context, Pokemon pokemon) {
        Activity thisActivity = (Activity) context;
        this.context = context;
        item = pokemon;

        pokeView = (TextView) ((Activity) context).findViewById(R.id.editNameTB);
        otherTrainer = (TextView) ((Activity) context).findViewById(R.id.editOwnerTB);
        bid = (TextView) ((Activity) context).findViewById(R.id.editOfferTB);
        description = (TextView) ((Activity) context).findViewById(R.id.editDescriptionTB);
        level = (TextView) thisActivity.findViewById(R.id.editLevelTB);
        type1 = (TextView) thisActivity.findViewById(R.id.editType1TB);
        type2 = (TextView) thisActivity.findViewById(R.id.editType2TB);
        nature = (TextView) thisActivity.findViewById(R.id.editNatureTB);
        ability = (TextView) thisActivity.findViewById(R.id.editAbilityTB);
        pokeItem = (TextView) thisActivity.findViewById(R.id.editItemTB);
        hp = (TextView) thisActivity.findViewById(R.id.editHPTB);
        attack = (TextView) thisActivity.findViewById(R.id.editAttackTB);
        defense = (TextView) thisActivity.findViewById(R.id.editDefenseTB);
        spAtk = (TextView) thisActivity.findViewById(R.id.editSpAtkTB);
        spDef = (TextView) thisActivity.findViewById(R.id.editSpDefTB);
        speed = (TextView) thisActivity.findViewById(R.id.editSpeedTB);
        move1 = (TextView) thisActivity.findViewById(R.id.editMove1TB);
        move2 = (TextView) thisActivity.findViewById(R.id.editMove2TB);
        move3 = (TextView) thisActivity.findViewById(R.id.editMove3TB);
        move4 = (TextView) thisActivity.findViewById(R.id.editMove4TB);

    }

    public void populate() {
        pokeView.setText(item.getTitle());
        otherTrainer.setText(item.getOwner());

        ArrayList<Offer> offers = item.getOffers();
        for(int i = 0; i < offers.size(); i++) {
            if(offers.get(i).getOwner() == UserLocal.getInstance().getName()) {
                bid.setText(Double.toString(offers.get(i).getRate()));
            }
        }

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
