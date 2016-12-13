package com.algonquincollege.arms0333.doors_open_final;


import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    private Geocoder mGeocoder;

    private TextView textIntent1;
    private TextView textIntent2;
    private TextView textIntent3;
    private TextView textIntent4;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mGeocoder = new Geocoder( this, Locale.CANADA );
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        textIntent1 = (TextView) findViewById(R.id.textViewTitle);
        textIntent2 = (TextView) findViewById(R.id.textViewAddress);
        textIntent3 = (TextView) findViewById(R.id.textViewDescription);
        textIntent4 = (TextView) findViewById(R.id.textViewDate);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String pointofinterestnam = bundle.getString("pointofinterestnam");
            String pointofinterestaddr = bundle.getString("pointofinterestaddr");
            String pointofinterestdesc = bundle.getString("pointofinterestdesc");
            ArrayList<String> dates=bundle.getStringArrayList("date");
            Log.e("TAG",dates.size()+"");
            textIntent1.setText(pointofinterestnam);
            textIntent2.setText(pointofinterestaddr);
            textIntent3.setText(pointofinterestdesc);
            String stringDate = "";
            for(int i=0;i<dates.size(); i++){
                stringDate += dates.get(i) + "\n";
            }
            textIntent4.setText(stringDate);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pin( String locationName )
    {
        try {
            Address address = mGeocoder.getFromLocationName(locationName, 1).get(0);
            LatLng ll = new LatLng( address.getLatitude(), address.getLongitude() );
            mMap.addMarker( new MarkerOptions().position(ll).title(locationName) );
            // i got reference from here http://stackoverflow.com/questions/16998169/google-maps-v2-set-zoom-level
            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(ll, 15));
            Toast.makeText(this, "Pinned: " + locationName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Not found: " + locationName, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String ad = bundle.getString("pointofinterestaddr");
            DetailsActivity.this.pin(ad);
        }


    }
}
