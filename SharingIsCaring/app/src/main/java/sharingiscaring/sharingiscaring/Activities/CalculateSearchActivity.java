package sharingiscaring.sharingiscaring.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.ListItem;
import sharingiscaring.sharingiscaring.DataStructure.ListItemAdapter;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;
import sharingiscaring.sharingiscaring.ElasticSearch.EsPokemonControl;
import sharingiscaring.sharingiscaring.R;


/**
 * @author klark
 * @since 4/1/16.
 * based off of our market place search this search is slightly modified to return a pokemon
 * instead of viewing the selected pokemon
 */
public class CalculateSearchActivity extends AppCompatActivity {
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private EditText searchTerms;

    private ListView pokemonSearchResultsList;
    private ArrayList<ListItem> searchedPokemonListItems = new ArrayList<ListItem>();
    private ListItemAdapter searchedAdapter;
    private ArrayList<Pokemon> results = new ArrayList<Pokemon>();
    private UserLocal user;
    private int pokemonTag;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.calculate_search);

        user = UserLocal.getInstance();
        searchTerms = (EditText) findViewById(R.id.pokemonSearchTerms);

        pokemonSearchResultsList = (ListView) findViewById(R.id.pokemonSearchResultsListView);
        searchedAdapter = new ListItemAdapter(this, searchedPokemonListItems);
        pokemonSearchResultsList .setOnItemClickListener(viewSearchedPokemon);
        pokemonSearchResultsList.setAdapter(searchedAdapter);
        Bundle bundle = getIntent().getExtras();
        pokemonTag = bundle.getInt("Tag");
    }
    private AdapterView.OnItemClickListener viewSearchedPokemon = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
            UserLocal user = UserLocal.getInstance();
            user.setViewedPokemon(results.get(position));
            int status = results.get(position).getStatus();
            Intent intent = new Intent(CalculateSearchActivity.this, CalculateDisplayActivity.class);
            intent.putExtra("SelectedPokemon",results.get(position));
            if(pokemonTag==1){
                setResult(1,intent);
            }
            if(pokemonTag==2) {
                setResult(2, intent);
            }
            finish();

        }
    };
    private void updateBoxLists() {
        searchedPokemonListItems.clear();
        for(Pokemon pokemon : results) {
            searchedPokemonListItems.add(fillBoxSlot(pokemon));
        }
        searchedAdapter.notifyDataSetChanged();
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

    public void search(View view){

        String searchText = searchTerms.getText().toString();

        switch (searchText){
            case "": Log.i("Marketplace", "Empty");
                EsPokemonControl.GetAllPokemonTask getAll = new EsPokemonControl.GetAllPokemonTask();
                getAll.execute("");
                try {
                    results = getAll.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            default:
                Log.i("Marketplace", searchText);
                EsPokemonControl.GetPokemonTask searchTask = new EsPokemonControl.GetPokemonTask();
                searchTask.execute(searchText);
                try {
                    results = searchTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
        }
        updateBoxLists();
    }

}
