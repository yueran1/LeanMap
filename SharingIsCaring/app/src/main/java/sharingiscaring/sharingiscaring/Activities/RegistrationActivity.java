package sharingiscaring.sharingiscaring.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.ElasticSearch.EsLoginControl;
import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;
import sharingiscaring.sharingiscaring.DataStructure.Login;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.Serializer;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * @author rbrewer
 * @version 0.1
 * @since 04/03/16
 *
 * This adds a User to our ElasticSearch server. Gathers information and creates a User type object.
 *
 */

public class RegistrationActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private EditText phoneInput;
    private EditText addressInput;
    private EditText cityInput;
    private EditText provinceInput;
    private EditText postalInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameInput = (EditText) findViewById(R.id.registerUsername);
        passwordInput = (EditText) findViewById(R.id.registerPassword);
        emailInput = (EditText) findViewById(R.id.registerEmail);
        phoneInput = (EditText) findViewById(R.id.registerPhone);
        addressInput = (EditText) findViewById(R.id.registerAddress);
        cityInput = (EditText) findViewById(R.id.registerCity);
        provinceInput = (EditText) findViewById(R.id.registerProvince);
        postalInput = (EditText) findViewById(R.id.registerPostal);
    }

    public void finishRegistration(View view) throws ExecutionException, InterruptedException {
        ArrayList<User> holder;
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String email = emailInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String address = addressInput.getText().toString();
        String city = cityInput.getText().toString();
        String province = provinceInput.getText().toString();
        String postal = postalInput.getText().toString();

        UserLocal userLocal = UserLocal.getInstance();
        userLocal.setUUID(username);
        userLocal.setPassword(password);
        userLocal.setEmail(email);
        userLocal.setPhone(phone);
        userLocal.setStreetAddress(address);
        userLocal.setCity(city);
        userLocal.setProvince(province);
        userLocal.setPostalCode(postal);

        User user = new User(username, password, email, phone, address, city, province, postal);
        Login login = new Login(username, password);
        EsLoginControl.ValidateTask validator = new EsLoginControl.ValidateTask();

        validator.execute(login);

        Intent returnIntent = new Intent();
        // TODO: handle this more elegantly within the validator class.  Gave me trouble on March 18.
        ArrayList<Login> temp = validator.get();
        if (temp.size() == 0){
            // Username is still unique.  Add the user.  Add the login. finish.
            EsUserControl.AddUserTask addUser = new EsUserControl.AddUserTask();
            EsLoginControl.AddLoginTask addLogin = new EsLoginControl.AddLoginTask();

            addUser.execute(user);
            addLogin.execute(new Login(user.getName(), user.getPassword()));
            Log.i("Registration", "We WILL register this user.");

            // Returns username to login screen, will automatically populate the login field
            returnIntent.putExtra("username", user.getName());
            setResult(RESULT_OK, returnIntent);

            Serializer serializer = new Serializer(getApplicationContext());
            serializer.save();
            finish();
        } else {
            Log.i("Registration", "We will NOT register this user.");
        }
    }
}
