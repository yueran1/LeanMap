package sharingiscaring.sharingiscaring;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;

import sharingiscaring.sharingiscaring.Activities.MyPCActivity;
import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.DataStructure.User;

/**
 * Created by klark on 2/12/16.
 */
public class testMyPCActivity extends ActivityInstrumentationTestCase2 {
    public testMyPCActivity(Class activityClass) { super(MyPCActivity.class); }

    public void testViewMyThings(){
        Intent intent = new Intent();
        User user = new User();
        Pokemon item1 = new Pokemon();

        user.addItem(item1);
        // intent.putExtra(MyPCActivity.MODE_TO_TRANSFER_KEY, user);
        setActivityIntent(intent);  //onStart will populate the ListView


        MyPCActivity viewMe = (MyPCActivity) getActivity();
        ListView thingsList = (ListView) viewMe.findViewById(R.id.viewMyBox);

        View origin = viewMe.findViewById(R.id.myPC);
        ViewAsserts.assertOnScreen(origin, thingsList);
    }

    public void testViewOwnedBorrowedThings(){
        Intent intent = new Intent();
        User user = new User();
        Pokemon item1 = new Pokemon();

        item1.setStatus(Item.BORROWED);
        user.addItem(item1);
        // intent.putExtra(MyPCActivity.MODE_TO_TRANSFER_KEY, user);
        setActivityIntent(intent);  //onStart will populate the ListView


        MyPCActivity viewMe = (MyPCActivity) getActivity();
        ListView thingsList = (ListView) viewMe.findViewById(R.id.viewMyBox);

        View origin = (View) viewMe.findViewById(R.id.myPC);
        ViewAsserts.assertOnScreen(origin, thingsList);
    }

    public void testViewOthersBorrowedThings(){
        Intent intent = new Intent();
        User user = new User();
        Pokemon item1 = new Pokemon();

        item1.setStatus(Item.BORROWED);
        user.addItem(item1);
        // intent.putExtra(MyPCActivity.MODE_TO_TRANSFER_KEY, user);
        setActivityIntent(intent);  //onStart will populate the ListView


        MyPCActivity viewMe = (MyPCActivity) getActivity();
        ListView thingsList = (ListView) viewMe.findViewById(R.id.viewBorrowedBox);

        View origin = (View) viewMe.findViewById(R.id.myPC);
        ViewAsserts.assertOnScreen(origin, thingsList);
    }
}
