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
import sharingiscaring.sharingiscaring.DataStructure.Offer;

/**
 * @author hoye
 * @version 0.1
 * @since 21/03/16
 *
 * This object is used to validate user input when they are trying to associate a bid between
 * two users in the application.
 *
 * Based on Lab07 ElasticSearchController.
 * Original created by esports on 2/17/16.
 * https://github.com/earthiverse/lonelyTwitter
 *
 */
public class EsOfferControl extends ElasticSearchController {
    private static final String searchType = "offer";

    public static class AddOfferTask extends AsyncTask<Offer, Void, Void> {
        @Override
        protected Void doInBackground(Offer... offers) {
            verifyClient();

            for (Offer offer : offers) {
                Index index = new Index.Builder(offer).index(searchIndex).type(searchType).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {

                    } else {
                        Log.i("ElasticSearch", "We failed to add a bid.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static class GetOfferTask extends AsyncTask<String, Void, ArrayList<Offer>> {
        @Override
        protected ArrayList<Offer> doInBackground(String... UUID) {
            verifyClient();
            String query = generateBidQuery(UUID[0]);
            Search search = new Search.Builder(query).addIndex(searchIndex).addType(searchType).build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    return separateObjects(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class GetNotificationsTask extends AsyncTask<String, Void, ArrayList<Offer>> {
        @Override
        protected ArrayList<Offer> doInBackground(String... UserUUID) {
            verifyClient();

            String query = generateNotificationQuery(UserUUID[0]);
            Search search = new Search.Builder(query).addIndex(searchIndex).addType(searchType).build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    return separateObjects(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static String generateBidQuery(String UUID) {
        String string = "{\"query\":\n" +
                        "    {\"match\": {\"ItemUUID\": \"" + UUID + "\"}}" +
                        "}";
        return string;
    }

    public static String generateNotificationQuery(String UUID) {
        String string = "{\"query\":\n" +
                "    {\"match\": {\"owner\": \"" + UUID + "\"}}" +
                "}";
        return string;
    }

    private static ArrayList<Offer> separateObjects(SearchResult result) {
        ArrayList<Offer> offers = new ArrayList<Offer>();
        if (result.isSucceeded()) {
            List<SearchResult.Hit<Offer, Void>> returned_bids = result.getHits(Offer.class);
            for (int i = 0; i < returned_bids.size(); i++) {
                offers.add(returned_bids.get(i).source);
            }
        } else {
            Log.i("TODO", "Separate Objects Properly");
        }
        return offers;
    }
}
