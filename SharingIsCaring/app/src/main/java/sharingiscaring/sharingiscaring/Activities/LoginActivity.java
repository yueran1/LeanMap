package sharingiscaring.sharingiscaring.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.DataStructure.LoginController;
import sharingiscaring.sharingiscaring.ElasticSearch.EsLoginControl;
import sharingiscaring.sharingiscaring.DataStructure.Login;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.Serializer;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * @author rbrewer
 * @version 0.1
 * @since 04/03/16
 *
 * This class is what the user first sees when they enter our app, and handles the login.
 * The user can either try to log in, or register as a new user.
 * Either action will take the user to a different Activity.
 *
 */

public class LoginActivity extends AppCompatActivity {
    private UserLocal user;

    private LoginController loginController;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usernameInput = (EditText) findViewById(R.id.loginUsername);
        passwordInput = (EditText) findViewById(R.id.loginPassword);

        user = UserLocal.getInstance();
    }

    // Attempts to log the user in, checking the ElasticSearch server for a valid username/password
    // combination. If successful, on return removes the current user and clears the editable
    // username and password fields.
    public void login(View view) {
        Login login = new Login(usernameInput.getText().toString(), passwordInput.getText().toString());
        this.loginController = new LoginController(getApplicationContext(), login);
        if (loginController.validate()){
            loginController.login();
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    // Takes the user to the registration screen. On a successful registration returns the user's
    // new username, and automatically populates the Username EditText field with it.
    public void register(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivityForResult(intent, 2);
    }

    // Deals with events on returning from other activities. Which activities, and specifics, below:
    // requestCode = 1: Returning from the Main Menu / Regular Application (MainMenuActivity)
    // requestCode = 2: Returning from the Registration screen (RegistrationActivity)
    protected void onActivityResult(int requestCode, int resultCode, Intent extras) {

        // Clears the LocalUser singleton in preparation for a new user to be logged in.
        // Also clears the editable Username and Password fields containing the previous user's information.
        if (requestCode == 1) {
            UserLocal.removeUser();
            user = UserLocal.getInstance();
            usernameInput.setText("", TextView.BufferType.EDITABLE);
            passwordInput.setText("", TextView.BufferType.EDITABLE);
        }

        // Gets username from RegistrationActivity and populates the Username EditText field with it.
        // Also empties the editable Password field of any text which may have previously been in it.
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                usernameInput.setText(extras.getStringExtra("username"), TextView.BufferType.EDITABLE);
                passwordInput.setText("", TextView.BufferType.EDITABLE);
                Serializer serializer = new Serializer(getApplicationContext());
                serializer.save();
            }
        }

        // Still clears the text boxes on return from an another activity.
        else {
            usernameInput.setText("", TextView.BufferType.EDITABLE);
            passwordInput.setText("", TextView.BufferType.EDITABLE);
        }
    }


}
