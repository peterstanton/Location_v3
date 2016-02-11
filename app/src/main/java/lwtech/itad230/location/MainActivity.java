package lwtech.itad230.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private TextView mLatitude;
    private TextView mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitude = (TextView) findViewById( R.id.latitude );
        mLongitude = (TextView) findViewById( R.id.longitude );

        if( mGoogleApiClient == null ) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        //int fineLoc = checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION );
        //int coarseLoc = checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION );

        //if(( fineLoc != PackageManager.PERMISSION_GRANTED ) && ( coarseLoc != PackageManager.PERMISSION_GRANTED )) {
        //    return;
        //}

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient);

        if( mLastLocation != null ) {
            mLatitude.setText( String.valueOf(mLastLocation.getLatitude()));
            mLongitude.setText( String.valueOf( mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
