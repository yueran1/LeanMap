package sharingiscaring.sharingiscaring.TrainerCard;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import sharingiscaring.sharingiscaring.ElasticSearch.EsUserControl;
import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * Created by hoye on 3/21/16.
 *
 * Setting references to Widgets from other classes
 * http://stackoverflow.com/questions/10996479/how-to-update-a-textview-of-an-activity-from-another-class
 * User: AVD
 * Date: 21/03/16
 *
 * Populates the username and EditText's for the current trainer's profile from the UserLocal singleton.
 */
public class TrainerCardStateOwn extends TrainerCardState {
    private final Context context;
    private final UserLocal user;
    private final TextView currentTrainer;
    private final EditText email;
    private final EditText phone;
    private final EditText streetAddress;
    private final EditText city;
    private final EditText province;
    private final EditText postalCode;

    public TrainerCardStateOwn(Context context) {
        this.context = context;
        user = UserLocal.getInstance();

        currentTrainer = (TextView) ((Activity) context).findViewById(R.id.textNameUserOwn);
        email = (EditText) ((Activity) context).findViewById(R.id.textUserEmailII);
        phone = (EditText) ((Activity) context).findViewById(R.id.textUserPhoneII);
        streetAddress = (EditText) ((Activity) context).findViewById(R.id.textUserAddressII);
        city = (EditText) ((Activity) context).findViewById(R.id.textUserCityII);
        province = (EditText) ((Activity) context).findViewById(R.id.textUserProvinceII);
        postalCode = (EditText) ((Activity) context).findViewById(R.id.textUserPostalII);
    }

    public void populate() {
        currentTrainer.setText(user.getName());
        email.setText(user.getEmail(), TextView.BufferType.EDITABLE);
        phone.setText(user.getPhone(), TextView.BufferType.EDITABLE);
        streetAddress.setText(user.getStreetAddress(), TextView.BufferType.EDITABLE);
        city.setText(user.getCity(), TextView.BufferType.EDITABLE);
        province.setText(user.getProvince(), TextView.BufferType.EDITABLE);
        postalCode.setText(user.getPostalCode(), TextView.BufferType.EDITABLE);
    }

    public void save() {
        user.setEmail(email.getText().toString());
        user.setPhone(phone.getText().toString());
        user.setStreetAddress(streetAddress.getText().toString());
        user.setCity(city.getText().toString());
        user.setProvince(province.getText().toString());
        user.setPostalCode(postalCode.getText().toString());

        // Create a new user to insert into the Elasticsearch database.
        // We insert a User rather than a UserLocal so that the structure of object being inserted
        // is consistent with the other users in the Elasticsearch database.
        // TODO: Need to set owned items, still.
        User update_user = new User(user);
        update_user.setUUID(user.getUUID());

        EsUserControl.UpdateUserTask updateUserTask = new EsUserControl.UpdateUserTask();
        updateUserTask.execute(update_user);
    }
}
