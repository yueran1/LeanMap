package sharingiscaring.sharingiscaring;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;

import sharingiscaring.sharingiscaring.Activities.MarketplaceActivity;
import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.DataStructure.User;

/**
 * Created by klark on 2/12/16.
 *
 */
public class testMarketplaceActivity extends ActivityInstrumentationTestCase2 {
    public testMarketplaceActivity(Class activityClass) { super(MarketplaceActivity.class); }

    public void testSearchThings(String input){
        Intent intent = new Intent();
        User borrower = new User();
        String search = "default";//user would input this search term
        Pokemon item1 = new Pokemon();
        boolean found = false;
        borrower.addItem(item1);

        for(int i=0; i<borrower.getOwnedItems().size(); i++) {
            if(borrower.getOwnedItems().get(i).getTitle().equals(search)){
                found = true;
            }
        }
        assertEquals(found, true);//assert that the search was successful in finding the item

        // intent.putExtra(MarketplaceActivity.MODE_TO_TRANSFER_KEY, borrower);
        setActivityIntent(intent);  //onStart will populate the ListView


        MarketplaceActivity viewMe = (MarketplaceActivity) getActivity();
        ListView searchList = (ListView) viewMe.findViewById(R.id.searchResultsListView);

        View origin = (View) viewMe.findViewById(R.id.market);
        ViewAsserts.assertOnScreen(origin, searchList);
    }

}
