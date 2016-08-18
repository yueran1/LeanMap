package sharingiscaring.sharingiscaring.ViewPokemon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.Activities.MyPCActivity;
import sharingiscaring.sharingiscaring.Activities.ViewPokemonActivity;
import sharingiscaring.sharingiscaring.DataStructure.ListItem;
import sharingiscaring.sharingiscaring.DataStructure.ListItemAdapter;
import sharingiscaring.sharingiscaring.DataStructure.ListOffer;
import sharingiscaring.sharingiscaring.DataStructure.ListOfferAdapter;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.ElasticSearch.EsOfferControl;
import sharingiscaring.sharingiscaring.DataStructure.Offer;
import sharingiscaring.sharingiscaring.ElasticSearch.EsPokemonControl;
import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;
import sharingiscaring.sharingiscaring.R;

/**
 * Created by Ryan on 2016-03-21.
 */
public class ViewPokemonStateOwnedAvailable extends ViewPokemonState {
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
    private ListView offerList;
    private ListOfferAdapter offerAdapter;
    private ArrayList<ListOffer> listOffers;
    private ArrayList<Offer> offers;



    public ViewPokemonStateOwnedAvailable(Context context, Pokemon pokemon) {
        Activity thisActivity = (Activity) context;
        this.context = context;
        item = pokemon;

        pokeView = (TextView) thisActivity.findViewById(R.id.editNameOA);
        currentTrainer = (TextView) thisActivity.findViewById(R.id.editOwnerOA);
        description = (TextView) thisActivity.findViewById(R.id.editDescriptionOA);
        level = (TextView) thisActivity.findViewById(R.id.editLevelOA);
        type1 = (TextView) thisActivity.findViewById(R.id.editType1OA);
        type2 = (TextView) thisActivity.findViewById(R.id.editType2OA);
        nature = (TextView) thisActivity.findViewById(R.id.editNatureOA);
        ability = (TextView) thisActivity.findViewById(R.id.editAbilityOA);
        pokeItem = (TextView) thisActivity.findViewById(R.id.editItemOA);
        hp = (TextView) thisActivity.findViewById(R.id.editHPOA);
        attack = (TextView) thisActivity.findViewById(R.id.editAttackOA);
        defense = (TextView) thisActivity.findViewById(R.id.editDefenseOA);
        spAtk = (TextView) thisActivity.findViewById(R.id.editSpAtkOA);
        spDef = (TextView) thisActivity.findViewById(R.id.editSpDefOA);
        speed = (TextView) thisActivity.findViewById(R.id.editSpeedOA);
        move1 = (TextView) thisActivity.findViewById(R.id.editMove1OA);
        move2 = (TextView) thisActivity.findViewById(R.id.editMove2OA);
        move3 = (TextView) thisActivity.findViewById(R.id.editMove3OA);
        move4 = (TextView) thisActivity.findViewById(R.id.editMove4OA);

        EsOfferControl.GetOfferTask getOfferTask = new EsOfferControl.GetOfferTask();
        getOfferTask.execute(pokemon.getIdentifier());
        try {
            item.setOffers(getOfferTask.get());
        } catch (Exception e){
            // TODO: Make this catch more specific.
        }

        offers = item.getOffers();
        listOffers = new ArrayList<ListOffer>();

        for(int i = 0; i < offers.size(); i++) {
            ListOffer listOffer = new ListOffer(offers.get(i).getBidder(),
                    "$" + Double.toString(offers.get(i).getRate()) + "/hr");
            listOffers.add(listOffer);
        }

        offerList = (ListView) ((Activity) context).findViewById(R.id.listOffersOA);
        offerList.setOnItemClickListener(AcceptListener);
        offerAdapter = new ListOfferAdapter(thisActivity, listOffers);
        offerList.setAdapter(offerAdapter);
    }

    private AdapterView.OnItemClickListener AcceptListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
            Pokemon pokemon;
            User owner;
            User bidder;
            Offer offer = offers.get(position);

            EsPokemonControl.GetPokemonTask getPokemonTask = new EsPokemonControl.GetPokemonTask();
            getPokemonTask.execute(offer.getItemUUID());
            EsUserControl.GetUsersTask getOwner = new EsUserControl.GetUsersTask();
            getOwner.execute(offer.getOwner());
            EsUserControl.GetUsersTask getBidder = new EsUserControl.GetUsersTask();
            getBidder.execute(offer.getBidder());

            try {

                pokemon = getPokemonTask.get().get(0);
                owner = getOwner.get().get(0);
                bidder = getBidder.get().get(0);

                pokemon.setStatus(Pokemon.BORROWED);
                owner.updatePokemon(pokemon);
                bidder.borrow(pokemon);

                EsPokemonControl.UpdatePokemonTask updatePokemonTask = new EsPokemonControl.UpdatePokemonTask();
                EsUserControl.UpdateUserTask updateOwner = new EsUserControl.UpdateUserTask();
                EsUserControl.UpdateUserTask updateBidder = new EsUserControl.UpdateUserTask();

                updatePokemonTask.execute(pokemon);
                updateOwner.execute(owner);
                updateBidder.execute(bidder);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    };

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
        offerAdapter.notifyDataSetChanged();
    }

    public Pokemon getItem(){
        return this.item;
    }
}
