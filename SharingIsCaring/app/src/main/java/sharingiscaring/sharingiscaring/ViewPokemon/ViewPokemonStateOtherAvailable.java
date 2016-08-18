package sharingiscaring.sharingiscaring.ViewPokemon;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sharingiscaring.sharingiscaring.ElasticSearch.EsOfferControl;
import sharingiscaring.sharingiscaring.DataStructure.Offer;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * Created by Ryan on 2016-03-21.
 *
 * String to double:
 * http://stackoverflow.com/questions/5769669/convert-string-to-double-in-java
 * User; WhiteFang34
 * Date: March 29, 2016
 *
 */
public class ViewPokemonStateOtherAvailable extends ViewPokemonState {
    private final Context context;
    private Pokemon item;
    private final TextView pokeView;
    private final TextView otherTrainer;
    private final TextView status;
    private final EditText bid;
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


    ImageView attachPhotoView;



    public ViewPokemonStateOtherAvailable(Context context, Pokemon pokemon) {
        Activity thisActivity = (Activity) context;
        this.context = context;
        this.item = pokemon;

        pokeView = (TextView) ((Activity) context).findViewById(R.id.editNameTA);
        otherTrainer = (TextView) ((Activity) context).findViewById(R.id.editOwnerTA);
        status = (TextView) ((Activity) context).findViewById(R.id.editStatusTA);
        bid = (EditText) ((Activity) context).findViewById(R.id.editOfferTA);
        description = (TextView) ((Activity) context).findViewById(R.id.editDescriptionTA);
        level = (TextView) thisActivity.findViewById(R.id.editLevelTA);
        type1 = (TextView) thisActivity.findViewById(R.id.editType1TA);
        type2 = (TextView) thisActivity.findViewById(R.id.editType2TA);
        nature = (TextView) thisActivity.findViewById(R.id.editNatureTA);
        ability = (TextView) thisActivity.findViewById(R.id.editAbilityTA);
        pokeItem = (TextView) thisActivity.findViewById(R.id.editItemTA);
        hp = (TextView) thisActivity.findViewById(R.id.editHPTA);
        attack = (TextView) thisActivity.findViewById(R.id.editAttackTA);
        defense = (TextView) thisActivity.findViewById(R.id.editDefenseTA);
        spAtk = (TextView) thisActivity.findViewById(R.id.editSpAtkTA);
        spDef = (TextView) thisActivity.findViewById(R.id.editSpDefTA);
        speed = (TextView) thisActivity.findViewById(R.id.editSpeedTA);
        move1 = (TextView) thisActivity.findViewById(R.id.editMove1TA);
        move2 = (TextView) thisActivity.findViewById(R.id.editMove2TA);
        move3 = (TextView) thisActivity.findViewById(R.id.editMove3TA);
        move4 = (TextView) thisActivity.findViewById(R.id.editMove4TA);
    }

    public void populate() {
        pokeView.setText(item.getTitle());
        otherTrainer.setText(item.getOwner());

        ArrayList<Offer> offers = item.getOffers();
        for(int i = 0; i < offers.size(); i++) {
            if(offers.get(i).getOwner() == UserLocal.getInstance().getName()) {
                bid.setText(Double.toString(offers.get(i).getRate()), TextView.BufferType.EDITABLE);
                status.setText(R.string.status_pending);
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
    public Offer getOffer() {
        return new Offer(UserLocal.getInstance().getName(), item.getOwner(), item.getIdentifier(), 0);
    }
}
