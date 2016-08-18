package sharingiscaring.sharingiscaring.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.ListItem;
import sharingiscaring.sharingiscaring.DataStructure.ListItemAdapter;
import sharingiscaring.sharingiscaring.ElasticSearch.EsPokemonControl;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * @author rbrewer
 * @version 0.1
 * @since 04/03/16
 *
 * This will allow a user to search for a Pokemon, and display results based on their query.
 * Currently does nothing.
 *
 * Resource for using SearchView Widget:
 * http://developer.android.com/guide/topics/search/search-dialog.html
 */

public class MarketplaceActivity extends AppCompatActivity {
    private ListView searchResultsView;
    private ListAdapter resultsAdapter;
    private EditText searchTerms;


    private ListView searchedPokemonList;
    private ArrayList<ListItem> searchedPokemonListItems = new ArrayList<ListItem>();
    private ListItemAdapter searchedAdapter;
    private ArrayList<Pokemon> results = new ArrayList<Pokemon>();
    private ArrayAdapter<Pokemon> pokemonArrayAdapter;
    private UserLocal user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = UserLocal.getInstance();
        searchTerms = (EditText) findViewById(R.id.searchTerms);

        searchedPokemonList = (ListView) findViewById(R.id.searchResultsListView);
        searchedAdapter = new ListItemAdapter(this, searchedPokemonListItems);
        searchedPokemonList.setOnItemClickListener(viewSearchedPokemon);
        searchedPokemonList.setAdapter(searchedAdapter);

    }

    // Called from listitem_pokemon.xml, starts the ViewPokemonActivity. Sends the availability status
    // of the current Pokemon to that activity, which will then determine which view to display based
    // on it. See ViewPokemonActivity for details on which status is represented by which integer.
    private AdapterView.OnItemClickListener viewSearchedPokemon = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long rowID) {
            UserLocal user = UserLocal.getInstance();
            user.setViewedPokemon(results.get(position));
            int status = results.get(position).getStatus();
            Intent intent = new Intent(MarketplaceActivity.this, ViewPokemonActivity.class);
            intent.putExtra("status", status);
            intent.putExtra("UUID", results.get(position).getIdentifier());
            intent.putExtra("position", position);
            startActivityForResult(intent, 1);
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


    // Called from listitem_pokemon.xml, starts the ViewPokemonActivity. Sends the availability status
    // of the current Pokemon to that activity, which will then determine which view to display based
    // on it. See ViewPokemonActivity for details on which status is represented by which integer.
    public void viewPokemon(View view) {
        Intent intent = new Intent(this, ViewPokemonActivity.class);
        int status = 0;   // Gather this from the availability status of the Pokemon being selected
        intent.putExtra("status", status);
        startActivity(intent);
    }
}
