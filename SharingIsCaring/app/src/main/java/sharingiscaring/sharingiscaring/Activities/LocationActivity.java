package sharingiscaring.sharingiscaring.Activities;

// import com.google.android.gms.maps.GoogleMap;
// import com.google.android.gms.maps.MapFragment;

//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;


// import sharingiscaring.sharingiscaring.R;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import sharingiscaring.sharingiscaring.R;

/**
 * Created by Stu on 2016-04-02.
 *
 * Process of adding map to the app described in tutorial:
 * http://www.tutorialspoint.com/android/android_google_maps.htm
 * Date: April 2, 2016
 *
 */

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Button SHOWMAP;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmap);
        SHOWMAP =(Button) findViewById(R.id.showMap);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapid);
        //mapFragment.getMapAsync(this);
        SHOWMAP.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                FragmentManager FM=getFragmentManager();
                FragmentTransaction FT= FM.beginTransaction();
                MapFragmentActivity MF=new MapFragmentActivity();
                FT.add(R.id.maplayout,MF);
                FT.commit();

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(53.5272, -113.529), 16));

        // You can customize the marker image using images bundled with
        // your app, or dynamically generated bitmaps.
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(53.5272, -113.529)).title("i am here"));
    }



}