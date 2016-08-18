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
import sharingiscaring.sharingiscaring.DataStructure.User;

/**
 * @author hoye
 * @version 0.1
 * @since 12/03/16
 *
 * This object is used to generate elasticsearch queries when a user is trying to register with the
 * application.
 *
 * Based on Lab07 ElasticSearchController.
 * Original created by esports on 2/17/16.
 * https://github.com/earthiverse/lonelyTwitter
 *
 */

public class EsUserControl extends ElasticSearchController{
    private static final String searchType = "user";

    public static class AddUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();

            for (User user : users) {
                Index index = new Index.Builder(user).index(searchIndex).type(searchType).id(user.getName()).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.i(user.getName(), user.getUUID());
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

    public static class UpdateUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifyClient();

            for (User user : users){
                Log.i("UUID", user.getUUID());
                // String UUID = user.getUUID();
                Index index = new Index.Builder(user).index(searchIndex).type(searchType).id(user.getName()).build();
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

    public static class GetUsersTask extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... user_params){
            verifyClient();
            String query = generateUserQuery(user_params);
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

    private static String generateUserQuery(String... text){
        String result;
        String username = text[0];
        result = "{\"query\": " +
                "    {\"match\": {\"name\" : \"" + username + "\"}}" +
                "}";
        return result;
    }

    private static ArrayList<User> separateObjects(SearchResult result){
        ArrayList<User> users = new ArrayList<User>();
        if (result.isSucceeded()){
            List<SearchResult.Hit<User, Void>> returned_users = result.getHits(User.class);
            for (int i = 0; i < returned_users.size(); i++){
                users.add(returned_users.get(i).source);
            }
        } else {
            Log.i("TODO", "We failed our search.");
        }
        return users;
    }
}
