package sharingiscaring.sharingiscaring;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;

import sharingiscaring.sharingiscaring.Activities.ViewPokemonActivity;
import sharingiscaring.sharingiscaring.DataStructure.Item;
import sharingiscaring.sharingiscaring.DataStructure.Pokemon;
import sharingiscaring.sharingiscaring.DataStructure.User;

/**
 * Created by klark on 2/12/16.
 */
public class testViewOnePokemon extends ActivityInstrumentationTestCase2 {
    public testViewOnePokemon(Class activityClass) { super(ViewPokemonActivity.class); }

    public void testViewOneThing(){
        Intent intent = new Intent();
        User user = new User();
        Pokemon item1 = new Pokemon();
        int index = 0;

        user.addItem(item1);
        //intent.putExtra(ViewPokemonActivity.POKEMON_NAME, user.getOwnedItems().
        //        get(index).getName());
        //intent.putExtra(ViewOneThingActivity.POKEMON_DESCRIPTION, user.getOwnedItems().
        //        get(index).getDescription());
        setActivityIntent(intent);  //onStart will populate the ListView


        ViewPokemonActivity viewMe = (ViewPokemonActivity) getActivity();

        View origin = (View) viewMe.findViewById(R.id.editNameOU);
        View name = (View) viewMe.findViewById(R.id.editOwnerOU);
        View description = (View) viewMe.findViewById(R.id.editDescriptionAdd);

        ViewAsserts.assertOnScreen(origin, name);
        ViewAsserts.assertOnScreen(origin, description);
    }
    //public void testViewOneThing(){ // 01.03.01
    //   Intent intent = new Intent();
    //    intent.putExtra(ViewOneThingActivity.POKEMON_NAME, "test name");
    //    intent.putExtra(ViewOneThingActivity.POKEMON_DESCRIPTION, "test description");
    //
    //    setActivityIntent(intent);
    //    ViewOneThingActivity viewMe = (ViewOneThingActivity) getActivity();

    //    assertEquals("test name", viewMe.getName());
    //    assertEquals("test description", viewMe.getDescription());
    //}
}
