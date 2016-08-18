package sharingiscaring.sharingiscaring.DataStructure;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * @author hoye
 * @version 0.1
 * @since 08/03/16
 *
 * This extension of User will be a singleton reference to the user who logs into the app.
 * Making this a singleton will allow us to reference the user's info easily throughout the app.
 *
 */

public class UserLocal extends User{
    private static UserLocal thisUser = null;
    private Pokemon viewedPokemon;
    private ArrayList<AsyncTask> tasks;

    // Initiates the singleton if it doesn't already exist. Otherwise, returns the singleton.
    // UserLocal methods can be called as such:   UserLocal.getInstance().methodName()
    public static UserLocal getInstance() {
        if(thisUser == null) {
            thisUser = new UserLocal();
        }
        return thisUser;
    }

    public static void removeUser() {
        thisUser = null;
    }

    public void returnItem(Item item){
        item.setStatus(Item.RETURNED);
        this.borrowedItems.remove(item);
    }
    public void validateReturn(Item item){
        item.setStatus(Item.AVAILABLE);
    }


    public ArrayList<AsyncTask> getTasks(){
        return this.tasks;
    }
    public void setTasks(ArrayList<AsyncTask> tasks){
        this.tasks = tasks;
    }
    public void addTask(AsyncTask task){
        this.tasks.add(task);
    }

    public Pokemon getViewedPokemon() {
        return viewedPokemon;
    }

    public void setViewedPokemon(Pokemon viewedPokemon) {
        this.viewedPokemon = viewedPokemon;
    }
}
