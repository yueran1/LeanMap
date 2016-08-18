package sharingiscaring.sharingiscaring.TrainerCard;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import sharingiscaring.sharingiscaring.R;
import sharingiscaring.sharingiscaring.DataStructure.User;
import sharingiscaring.sharingiscaring.DataStructure.UserLocal;

/**
 * Created by hoye on 3/21/16.
 *
 * Populates the TextViews for another trainer's profile with data from the UserLocal singleton.
 */
public class TrainerCardStateOther extends TrainerCardState {
    private final Context context;
    private User user;
    private final TextView otherTrainer;
    private final TextView email;
    private final TextView phone;
    private final TextView streetAddress;
    private final TextView city;
    private final TextView province;
    private final TextView postalCode;

    public TrainerCardStateOther(Context context, User user) {
        this.context = context;
        this.user = user;

        otherTrainer = (TextView) ((Activity) context).findViewById(R.id.textNameUserOther);
        email = (TextView) ((Activity) context).findViewById(R.id.textOtherEmailII);
        phone = (TextView) ((Activity) context).findViewById(R.id.textOtherPhoneII);
        streetAddress = (TextView) ((Activity) context).findViewById(R.id.textOtherAddressII);
        city = (TextView) ((Activity) context).findViewById(R.id.textOtherCityII);
        province = (TextView) ((Activity) context).findViewById(R.id.textOtherProvinceII);
        postalCode = (TextView) ((Activity) context).findViewById(R.id.textOtherPostalII);
    }

    public void populate() {
        otherTrainer.setText(UserLocal.getInstance().getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        streetAddress.setText(user.getStreetAddress());
        city.setText(user.getCity());
        province.setText(user.getProvince());
        postalCode.setText(user.getPostalCode());
    }

    public void save() { }
}
