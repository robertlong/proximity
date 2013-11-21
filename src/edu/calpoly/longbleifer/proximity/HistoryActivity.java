package edu.calpoly.longbleifer.proximity;

import java.util.HashMap;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.calpoly.longbleifer.proximity.beacon.BeaconService;
import edu.calpoly.longbleifer.proximity.models.Trigger;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class HistoryActivity extends FragmentActivity implements LocationListener, OnMarkerClickListener, OnMapClickListener, OnClickListener {
	
	private GoogleMap googleMap;
	private List<Trigger> triggers;
	private HashMap<Marker, Trigger> markerTriggers;
	private Trigger selectedTrigger;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.history_activity);
		
		Intent beaconIntent = new Intent(this, BeaconService.class);
        startService(beaconIntent);
        
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
 
        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
 
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
 
        } else {
 
        	SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
 
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
            
            googleMap.setOnMarkerClickListener(this);
            
            googleMap.setOnMapClickListener(this);
 
            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
 
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
 
            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
 
            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);
 
            if(location!=null){
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
            
            this.triggers = Trigger.all();
            this.markerTriggers = new HashMap<Marker, Trigger> ();
            
            for (Trigger trigger : triggers) {
            	LatLng position = new LatLng(trigger.latitude, trigger.longitude);
            	Marker marker = this.googleMap.addMarker(new MarkerOptions().position(position).title(trigger.name));
            	this.markerTriggers.put(marker, trigger);
            }
            
            ImageButton cardButton = (ImageButton) this.findViewById(R.id.card_button);
            cardButton.setOnClickListener(this);
        }
    }
    @Override
    public void onLocationChanged(Location location) {
 
        // Getting latitude of the current location
        double latitude = location.getLatitude();
 
        // Getting longitude of the current location
        double longitude = location.getLongitude();
 
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
 
        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
 
        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
 
    }
 
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}
	@Override
	public boolean onMarkerClick(Marker marker) {
		Trigger trigger = this.markerTriggers.get(marker);
		
		RelativeLayout cardLayout = (RelativeLayout)this.findViewById(R.id.card_layout);
		cardLayout.setVisibility(RelativeLayout.VISIBLE);
		
		TextView cardText = (TextView) this.findViewById(R.id.card_text);
		cardText.setText(trigger.name);
		
		this.selectedTrigger = trigger;
		
		return true;
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.card_button && this.selectedTrigger != null) {
			Intent intent = new Intent(this, ProximityActivity.class);
			intent.putExtra("trigger-id", this.selectedTrigger.getId());
			this.startActivity(intent);
		}
	}
	@Override
	public void onMapClick(LatLng latLng) {
		this.selectedTrigger = null;
		RelativeLayout cardLayout = (RelativeLayout)this.findViewById(R.id.card_layout);
		cardLayout.setVisibility(RelativeLayout.INVISIBLE);
	}

}
