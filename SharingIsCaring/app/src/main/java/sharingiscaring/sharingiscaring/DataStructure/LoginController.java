package sharingiscaring.sharingiscaring.DataStructure;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.Activities.MainMenuActivity;
import sharingiscaring.sharingiscaring.ElasticSearch.EsLoginControl;
import sharingiscaring.sharingiscaring.ElasticSearch.EsOfferControl;
import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;

/**
 * Created by Stu on 2016-04-04.
 */
public class LoginController {
    private Context context;
    private Login login;

    public LoginController(Context context, Login login) {
        this.context = context;
        this.login = login;
    }

    public boolean validate(){
        ArrayList<Login> logins = new ArrayList<Login>();
        EsLoginControl.GetLoginsTask getLoginsTask = new EsLoginControl.GetLoginsTask();
        try {
            getLoginsTask.execute(login);
            logins = getLoginsTask.get();
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        // If login is successful, return true.
        if (logins.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void login(){
        UserLocal user = UserLocal.getInstance();
        user.setName(login.getUsername());

        Serializer serializer = new Serializer(context);
        serializer.read();

        EsOfferControl.GetNotificationsTask getNotificationsTask = new EsOfferControl.GetNotificationsTask();
        getNotificationsTask.execute(user.getName());
        try {
            user.setNotifications(getNotificationsTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        serializer.save();
    }
}
