package sharingiscaring.sharingiscaring;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.DataStructure.Login;
import sharingiscaring.sharingiscaring.ElasticSearch.EsLoginControl;

/**
 * Created by hoye on 3/14/16.
 */
public class testElasticSearchLogin extends ActivityInstrumentationTestCase2 {
    public testElasticSearchLogin(){
        super(EsLoginControl.class);
    }

    public void testUsernameValidation() throws ExecutionException, InterruptedException {
        EsLoginControl.AddLoginTask addLogin = new EsLoginControl.AddLoginTask();
        EsLoginControl.GetLoginsTask getLogin = new EsLoginControl.GetLoginsTask();
        Login testLogin = new Login("Test", "T3ST");
        Login testLogin2 = new Login("Non-existingName", "Non-existingPassword");
        ArrayList<Login> result;

        addLogin.execute(testLogin);

        result = getLogin.execute(testLogin).get();
        assertEquals(1, result.size());

        getLogin = new EsLoginControl.GetLoginsTask();
        result = getLogin.execute(testLogin2).get();
        assertEquals(0, result.size());

        getLogin = new EsLoginControl.GetLoginsTask();
        testLogin2.setPassword(testLogin.getPassword());
        result = getLogin.execute(testLogin2).get();
        assertEquals(0, result.size());

        getLogin = new EsLoginControl.GetLoginsTask();

        testLogin2.setUsername(testLogin.getUsername());
        testLogin2.setPassword("InvalidPassword");
        result = getLogin.execute(testLogin2).get();
        assertEquals(0, result.size());
    }

}
