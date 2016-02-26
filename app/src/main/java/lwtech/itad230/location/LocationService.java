package lwtech.itad230.location;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    private GoogleApiClient mGoogleApiClient;  //our google play services API
    private Location mLastLocation;   //the location
    private LocationRequest mLocationRequest;  //grabs locations
    static protected ArrayList<String> locations = new ArrayList<String>();   //stores the location data in this structure.

    // leave alone for now
    public LocationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;  //we don't do binding
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( mGoogleApiClient == null ) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)  //starts up an instance of the API client
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)  //adds location service API
                    .build();
        }

        mGoogleApiClient.connect();  //connect to the API client
        return super.onStartCommand(intent,flags,startId);  //equivalent to return START.STICKY, if we get killed, will return and restart
    }

    @Override
    public void onConnected(Bundle bundle) {  //upon connecting to the API client
        int permissionCheck;  //checks that we have the permissions for location services in the android manifest.
        permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if( permissionCheck == PackageManager.PERMISSION_DENIED) {
            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient); //gets location from the API client
        if( mLastLocation == null ) {
            return;
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(4000);  //sets a pause between location updates
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);  //gives us accuracy.

        if( mLocationRequest != null){  //if our location is null, retries.
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, (LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;  //updates the current location to the new location
        String dat = DateFormat.getDateTimeInstance().format(new Date());  //these lines grab date, longitude and latitude into strings
        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        locations.add(dat);  //stores the strings with that information in our listarray structure.
        locations.add(lon);
        locations.add(lat);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
    }  //kills the API client whene the service is being destroyed.
}
