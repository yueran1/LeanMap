package sharingiscaring.sharingiscaring.ElasticSearch;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import sharingiscaring.sharingiscaring.DataStructure.Move;

/**
 * @author Klark
 * @since 4/3/2016.
 */
public class EsMoveControl extends ElasticSearchController{
    private static final String searchType = "move";

    public static class AddMoveTask extends AsyncTask<Move, Void, Void> {
        @Override
        protected Void doInBackground(Move... moves) {
            verifyClient();

            for (Move move : moves) {
                Index index = new Index.Builder(move).index(searchIndex).type(searchType).id(move.getMoveName()).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {

                    } else {
                        Log.i("TODO", "We failed to add a user.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static class GetMoveTask extends AsyncTask<String, Void, ArrayList<Move>> {
        @Override
        protected ArrayList<Move> doInBackground(String... move_params){
            verifyClient();
            String query = generateMoveQuery(move_params);
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

    private static String generateMoveQuery(String... text){
        String result;
        String movename = text[0];
        result = "{\"query\": " +
                "    {\"match\": {\"moveName\" : \"" + movename + "\"}}" +
                "}";
        return result;
    }

    private static ArrayList<Move> separateObjects(SearchResult result){
        ArrayList<Move> users = new ArrayList<Move>();
        if (result.isSucceeded()){
            List<SearchResult.Hit<Move, Void>> returned_users = result.getHits(Move.class);
            for (int i = 0; i < returned_users.size(); i++){
                users.add(returned_users.get(i).source);
            }
        } else {
            Log.i("TODO", "We failed our search.");
        }
        return users;
    }
}
