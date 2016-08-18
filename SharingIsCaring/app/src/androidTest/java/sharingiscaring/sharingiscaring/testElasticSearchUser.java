package sharingiscaring.sharingiscaring;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;

/**
 * Created by hoye on 3/14/16.
 */
public class testElasticSearchUser extends ActivityInstrumentationTestCase2 {
    public testElasticSearchUser() { super(EsUserControl.class); }

    public void testUnique(){
        EsUserControl.AddUserTask addUser = new EsUserControl.AddUserTask();
        EsUserControl.GetUsersTask getUsers = new EsUserControl.GetUsersTask();
        ArrayList<User> results = new ArrayList<User>();
        String name = "Identical";
        User user1 = new User();
        User user2 = new User();
        user1.setName(name);
        user2.setName(name);

        addUser.execute(user1);
        addUser = new EsUserControl.AddUserTask();

        addUser.execute(user2);

        try {
            results = getUsers.execute(name).get();
            assertEquals(1, results.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
