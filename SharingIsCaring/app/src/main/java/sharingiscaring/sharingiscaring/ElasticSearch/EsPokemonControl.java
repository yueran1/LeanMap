package sharingiscaring.sharingiscaring.ElasticSearch;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;

/**
 * @author hoye
 * @version 0.1
 * @since 12/03/16
 *
 * This object is used to generate queries for elasticsearch when a user is trying to add a Pokemon
 * to the application.
 *
 * Based on Lab07 ElasticSearchController.
 * Original created by esports on 2/17/16.
 * https://github.com/earthiverse/lonelyTwitter
 *
 */

public class EsPokemonControl extends ElasticSearchController{
    private static final String searchType = "pokemon";

    public static class AddPokemonTask extends AsyncTask<Pokemon, Void, String> {
        @Override
        protected String doInBackground(Pokemon... pokemon) {
            verifyClient();
            String UUID = "";

            for (Pokemon poke : pokemon) {
                Index index = new Index.Builder(poke).index(searchIndex).type(searchType).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        UUID = result.getId();
                        poke.setIdentifier(UUID);
                        Delete delete = new Delete.Builder("1")
                                                  .index(searchIndex)
                                                  .type(searchType)
                                                  .id(UUID)
                                                  .build();
                        Index update = new Index.Builder(poke)
                                                .index(searchIndex)
                                                .type(searchType)
                                                .id(poke.getIdentifier())
                                                .build();
                        client.execute(delete);
                        client.execute(update);
                    } else {
                        Log.i("TODO", "We failed to add a pokemon.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return UUID;
        }
    }

    public static class UpdatePokemonTask extends AsyncTask<Pokemon, Void, Void> {
        @Override
        protected Void doInBackground(Pokemon... pokemon) {
            verifyClient();

            for (Pokemon poke : pokemon){
                String UUID = poke.getIdentifier();
                Index index = new Index.Builder(poke).index(searchIndex).type(searchType).id(UUID).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                    } else {
                        Log.i("TODO", "We failed to update the pokemon.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static class GetPokemonTask extends AsyncTask<String, Void, ArrayList<Pokemon>> {
        @Override
        protected ArrayList<Pokemon> doInBackground(String... pokemon_params){
            verifyClient();
            String query = generatePokemonQuery(pokemon_params[0]);
            Search search = new Search.Builder(query).addIndex(searchIndex).addType(searchType).build();
            Log.i("Query", query);
            try {
                SearchResult execute = client.execute(search);
                return separateObjects(execute);
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class GetAllPokemonTask extends AsyncTask<String, Void, ArrayList<Pokemon>> {
        @Override
        protected ArrayList<Pokemon> doInBackground(String... pokemon_params){
            verifyClient();
            String query = generateAllPokemonQuery(pokemon_params[0]);
            Search search = new Search.Builder(query).addIndex(searchIndex).addType(searchType).build();

            try {
                SearchResult execute = client.execute(search);
                return separateObjects(execute);
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class DeletePokemonTask extends AsyncTask<Pokemon, Void, Void>{

        @Override
        protected Void doInBackground(Pokemon... params) {
            verifyClient();

            Pokemon pokemon = params[0];
            Delete delete = new Delete.Builder("1")
                                       .type(searchType)
                                       .index(searchIndex)
                                       .id(pokemon.getIdentifier())
                                       .build();
            try {
                client.execute(delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //inline the variable searchText to eliminate redundancy
    protected static String generatePokemonQuery(String text){
        String result = "";
        result = "{\"query\":" +
                 "    {\"multi_match\": " +
                 "        {\"query\": \"" + text + "\"," +
                 "        \"fields\": [\"title\", \"ability\", \"primaryType\", \"secondaryType\"," +
                 "        \"move1\", \"move2\", \"move3\", \"move4\", \"owner\", \"description\"," +
                 "        \"pokemonItem\", \"nature\"]}" +
                 "    }"+
                 "}";
        Log.i("Query:", result);
        return result;
    }

    protected static String generateAllPokemonQuery(String text){
        String result;
        result = "{\"query\": "+
                 "{\"match_all\": {}}," +
                 "\"from\":0, \"size\":10000" +
                 "}";
        return result;
    }

    private static ArrayList<Pokemon> separateObjects(SearchResult result){
        ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
        if (result.isSucceeded()){
            List<SearchResult.Hit<Pokemon, Void>> returned_pokemon = result.getHits(Pokemon.class);
            for (int i = 0; i < returned_pokemon.size(); i++){
                pokemon.add(returned_pokemon.get(i).source);
            }
        } else {
            Log.i("TODO", "We failed our search.");
        }
        return pokemon;
    }
}
