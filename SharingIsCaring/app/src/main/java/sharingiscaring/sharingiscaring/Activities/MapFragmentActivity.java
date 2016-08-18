package sharingiscaring.sharingiscaring.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;

import sharingiscaring.sharingiscaring.R;

/**
 * Created by tonysunyueran on 03/04/2016.
 */
public class MapFragmentActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        //return super.onCreateView(inflater,container,savedInstanceState);
        View v=inflater.inflate(R.layout.mapfragment,container,false);



        return v;
    }
}
