package lwtech.itad230.location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    private TextView mLatitude;
    private TextView mLongitude;
    private TextView mLastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*
    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient);

        if( mLastLocation == null ) {
            return;
        }

        mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitude.setText( String.valueOf(mLastLocation.getLongitude()));
        String dat = DateFormat.getTimeInstance().format(new Date()).toString();
        mLastUpdate.setText( dat );

        createLocationRequest();
        if( mLocationRequest != null){
            startLocationUpdates();
        }
    }
    */

    @Override
    protected void onStart() {
        //mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        //mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void startButtonClick(View view) {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    public void stopButtonClick(View view) {
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
    }

    public void locsButtonClick(View view) {
        String str = "";
        for (String s : LocationService.locations) {
            //tv.setText(s);
            str += (s + "\n");
        }
        TextView tv = (TextView) findViewById(R.id.locations);
        tv.setText(str);
    }
}
