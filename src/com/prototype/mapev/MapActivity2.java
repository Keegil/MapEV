package com.prototype.mapev;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity2 extends FragmentActivity implements
		OnMapClickListener, LocationListener {

	private static final String LOG_TAG = "mapev";
	private GoogleMap googleMap;
	private Location lastLocation;
	private TextView tvMap;
	private float totalDistance = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity2_map);
		tvMap = (TextView) findViewById(R.id.tv_map);
		
		GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		googleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		googleMap.setMyLocationEnabled(true);
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.setOnMapClickListener(this);
		
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		lastLocation = locationManager.getLastKnownLocation(provider);
		locationManager.requestLocationUpdates(provider, 500, 0, (LocationListener) this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_activity2, menu);
		return true;
	}

	@Override
	public void onMapClick(LatLng point) {
		googleMap
				.addMarker(new MarkerOptions().position(point).title("Marker"));
	}

	@Override
	public void onLocationChanged(Location currentLocation) {
		totalDistance += currentLocation.distanceTo(lastLocation);
		tvMap.setText(Double.toString(totalDistance));
		Log.d(LOG_TAG,
				currentLocation + " " + lastLocation + " " + totalDistance
						+ " " + currentLocation.distanceTo(lastLocation));
		lastLocation = currentLocation;
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
