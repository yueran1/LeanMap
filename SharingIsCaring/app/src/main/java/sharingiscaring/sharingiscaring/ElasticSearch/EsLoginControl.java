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
import sharingiscaring.sharingiscaring.DataStructure.Login;

/**
 * @author hoye
 * @version 0.1
 * @since 12/03/16
 *
 * This object is used to validate user input when they are trying to log into the application.
 *
 * Based on Lab07 ElasticSearchController.
 * Original created by esports on 2/17/16.
 * https://github.com/earthiverse/lonelyTwitter
 *
 */

public class EsLoginControl extends ElasticSearchController{
    private static final String searchType = "login";

    public static class AddLoginTask extends AsyncTask<Login, Void, Void> {
        private Boolean isAvailable;
        private Boolean isValid;

        @Override
        protected Void doInBackground(Login... logins) {
            verifyClient();

            Login login = logins[0];
            String query = generateLoginQuery(login);
            Search search = new Search.Builder(query).addIndex(searchIndex).addType(searchType).build();
            SearchResult results;

            try {
                results = client.execute(search);
                if (separateObjects(results).size() == 0){
                    isAvailable = Boolean.TRUE;
                    isValid = Boolean.FALSE;
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            if (isAvailable) {
                Index index = new Index.Builder(login).index(searchIndex).type(searchType).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {

                    } else {
                        Log.i("TODO", "We failed to add a login.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        public Boolean valid(){
            return isValid;
        }
    }

    public static class GetLoginsTask extends AsyncTask<Login, Void, ArrayList<Login>> {
        private Boolean isAvailable;
        private Boolean isValid;

        @Override
        protected ArrayList<Login> doInBackground(Login... login_params){
            verifyClient();
            ArrayList<Login> logins = new ArrayList<Login>();
            String query = generateLoginQuery(login_params[0]);
            Search search = new Search.Builder(query).addIndex(searchIndex).addType(searchType).build();

            SearchResult results;
            try {
                results = client.execute(search);
                logins = separateObjects(results);
                if (logins.size() == 0){
                    isAvailable = Boolean.TRUE;
                    isValid = Boolean.FALSE;
                }
                return logins;
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        public Boolean valid(){
            return isValid;
        }
    }

    public static class ValidateTask extends AsyncTask<Login, Void, ArrayList<Login>> {

        @Override
        protected ArrayList<Login> doInBackground(Login... login_params){
            verifyClient();
            ArrayList<Login> logins = new ArrayList<Login>();
            String query = generateValidateQuery(login_params[0]);
            Search search = new Search.Builder(query).addIndex(searchIndex).addType(searchType).build();

            SearchResult results;
            try {
                results = client.execute(search);
                logins = separateObjects(results);
                return logins;
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

    }

    private static String generateLoginQuery(Login login){
        return "{\"query\": " +
                "    {\"bool\": " +
                "        {\"must\": " +
                "        [" +
                "            {\"match\": {\"uname\" : \"" + login.getUsername() + "\"}}," +
                "            {\"match\": {\"pword\" : \"" + login.getPassword() + "\"}}" +
                "        ]" +
                "        }" +
                "    }" +
                "}";
    }

    private static String generateValidateQuery(Login login){
        return "{\"query\": " +
                "    {\"match\": {\"uname\" : \"" + login.getUsername() + "\"}}" +
                "}";
    }

    private static ArrayList<Login> separateObjects(SearchResult result){
        ArrayList<Login> logins = new ArrayList<Login>();
        if (result.isSucceeded()){
            List<SearchResult.Hit<Login, Void>> returned_logins = result.getHits(Login.class);
            for (int i = 0; i < returned_logins.size(); i++){
                logins.add(returned_logins.get(i).source);
            }
        } else {
            Log.i("TODO", "We failed our search.");
        }
        return logins;
    }
}
