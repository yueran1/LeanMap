package sharingiscaring.sharingiscaring.DataStructure;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;

/**
 * Created by Stu on 2016-03-31.
 *
 * This class is written based on code from:
 * http://www.tutorialspoint.com/java/java_serialization.htm
 * Date: March 31, 2016.
 *
 * Info on accessing local files:
 * http://developer.android.com/training/basics/data-storage/files.html
 * Date: March 31, 2016.
 *
 * Info on specific to Android platforms:
 * http://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android
 * User: Ralkie
 * Date: March 31, 2016
 *
 */
public class Serializer {
    Context context;
    String filename;
    UserLocal user;
    File file;

    // Set references to everything that would need to be saved.
    public Serializer(Context context){
        this.context = context;
        this.user = UserLocal.getInstance();
        this.filename = user.getName() + ".txt";
        file = new File(filename);
    }

    // Write info about the user to a local savefile.
    // The savefile is <Username>.txt - no worry of conflicts: usernames are unique.
    public void save() {
        try {
            FileOutputStream output = context.openFileOutput(filename, context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(output);
            out.writeObject((User) user);
            out.close();
            output.close();
            Log.i("Serializer:", "Serialization complete.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Clears the local savefile.
    public void clear() {
        try {
            FileOutputStream output = context.openFileOutput(filename, context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(output);
            out.writeObject("");
            out.close();
            output.close();
            Log.i("Serializer:", "Clearing savefile complete.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Try to read the local save file that would be associated with the username.
    // If no such file exists, then try to retrieve the user from the database.
    public void read() {
        User inUser;
        UserLocal user = UserLocal.getInstance();

        if (!file.exists()) {
            EsUserControl.GetUsersTask getUsersTask = new EsUserControl.GetUsersTask();
            ArrayList<User> users = new ArrayList<User>();

            try {
                getUsersTask.execute(user.getName());
                users = getUsersTask.get();
            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (ExecutionException e){
                e.printStackTrace();
            }

            if (users.size() > 0) {
                User result = users.get(0);
                user.setName(result.getName());
                //UserLocal.getInstance().setPassword(users.get(0).getPassword());
                user.setEmail(result.getEmail());
                user.setPhone(result.getPhone());
                user.setStreetAddress(result.getStreetAddress());
                user.setCity(result.getCity());
                user.setProvince(result.getProvince());
                user.setPostalCode(result.getPostalCode());
                user.setUUID(result.getUUID());
                user.setOwnedItems(result.getOwnedItems());
                user.setNotifications(result.getNotifications());
            }
            this.save();
        } else {
            try {
                FileInputStream input = context.openFileInput(filename);
                ObjectInputStream in = new ObjectInputStream(input);
                inUser = (User) in.readObject();
                in.close();
                input.close();

                user.setUUID(inUser.getUUID());
                user.setName(inUser.getName());
                user.setPassword(inUser.getPassword());
                user.setOwnedItems(inUser.getOwnedItems());
                user.setContact(inUser.getContact());
                user.setPhone(inUser.getPhone());
                user.setEmail(inUser.getEmail());
                user.setStreetAddress(inUser.getStreetAddress());
                user.setCity(inUser.getCity());
                user.setProvince(inUser.getProvince());
                user.setPostalCode(inUser.getPostalCode());

                Log.i("Deserializer: ", user.getUUID());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
