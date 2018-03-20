package hackathon.rajasthan.rajasthantourism;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import hackathon.rajasthan.rajasthantourism.database.Database;
import hackathon.rajasthan.rajasthantourism.model.Constants;
import hackathon.rajasthan.rajasthantourism.model.Seller;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static hackathon.rajasthan.rajasthantourism.R.id.map;

public class NearMe extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {

    private MapFragment mapFragment;
    GoogleMap mMap;
    private ProgressDialog progress;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_CHECK_SETTINGS = 404;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private boolean locationServiceConnected;
    private int count = 0;
    private LatLng currentLoc;
    private ProgressDialog p;
    List<Seller> sellerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_me);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)                     //TODO: Change these values to 1000 or more if multiple intents take place in onLocationChanged
                .setFastestInterval(1000);             //TODO: Change these values to 1000 or more if multiple intents take place in onLocationChanged

        initiateChecking();



        /*progress = new ProgressDialog(this);
        progress.setTitle("Fetching The Location");
        progress.setMessage("Please Wait");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);*/

        sellerList = new Database(NearMe.this).getAllSellers();
        for (int i =0; i<sellerList.size();i++){
            Log.d("sellerlist",sellerList.get(i).getName() + sellerList.get(i).getDesc() + sellerList.get(i).getContact() +
                    sellerList.get(i).getAddress() + sellerList.get(i).getSells() + sellerList.get(i).getLat() + sellerList.get(i).getLat());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Toast.makeText(this, "onMapReady", Toast.LENGTH_SHORT).show();

        mMap = googleMap;
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        displaySellers();

        /*LatLng delhi = new LatLng(28.6139, 77.209);
        //marker = mMap.addMarker(new MarkerOptions().position(delhi));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(delhi)
                .zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(point)
                        .zoom(15)
                        .build();

            }
        });*/
    }

    private void initiateChecking() {            //METHOD TO INITIATE PERMISSION AND GPS CHECKING
        if (checkPermission()) {            //Requesting for location permission
            enableGPS();
        } else {
            requestPermission();
        }
    }


    private boolean checkPermission() {                 //PERMISSION RELATED METHOD
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {              //PERMISSION RELATED METHOD
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {         //PERMISSION RELATED METHOD
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted)
                        enableGPS();
                    else {
                        showErrorDialog("Permissions Denied, Please Enable Permissions in the Settings!");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to all the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                            else{

                                AlertDialog permissionSettings = new AlertDialog.Builder(NearMe.this)
                                        .setMessage("It looks like you have turned off the permission required for this feature. Enable it under the app settings.")
                                        .setPositiveButton("OPEN SETTINGS", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                                        .setData(Uri.fromParts("package", getPackageName(), null)));
                                            }
                                        })
                                        .create();
                                permissionSettings.show();
                            }
                        }

                    }
                }
                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {              //PERMISSION RELATED METHOD
        AlertDialog dialog = new AlertDialog.Builder(NearMe.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void enableGPS() {                                      //GPS ENABLE RELATED METHOD
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result
                        .getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:                           //GPS ALREDY TURNED ON
                        if (locationServiceConnected) {
                            if (ActivityCompat.checkSelfPermission(NearMe.this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                                p = new ProgressDialog(NearMe.this);
                                p.setMessage("Please Wait. Fetching Your Current Location");
                                p.show();
                                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, NearMe.this);
                            }
                        }
                        else {
                            showErrorDialog("ERROR: Not able to connect to Location Service");
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:               //GPS NOT ALREDY TURNED ON, SHOW TURN ON DIALOG
                        try {
                            status.startResolutionForResult(NearMe.this, REQUEST_CHECK_SETTINGS); //CREATING DIALOG FOR GPS TURN ON
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:       //GPS CANNOT BE TURNED ON
                        showErrorDialog("Permission Available, not able to enable GPS");
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {             //GPS ENABLE RELATED METHOD, DIALOG FOR GPS TURN ON
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {                 // GPS TURN ON ACCEPTED
                if (locationServiceConnected) {
                    if (ActivityCompat.checkSelfPermission(NearMe.this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                        p = new ProgressDialog(NearMe.this);
                        p.setMessage("Please Wait. Fetching Your Current Location");
                        p.show();
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, NearMe.this);
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {               //GPS TURN ON REJECTED
                showErrorDialog("Permission Available, GPS Off");
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {              //LOCATION FETCH METHOD
        locationServiceConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {                      //LOCATION FETCH METHOD
        locationServiceConnected = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {            //LOCATION FETCH METHOD
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 505);
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            locationServiceConnected = false;
            Log.i("TAG", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }



    @Override
    public void onLocationChanged(Location location) {// Called in equal intervals of time after requestLocationUpdates is called
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        if(p!=null){
            p.hide();
        }currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
        displaySellers();
        Toast.makeText(this, "Your Location has been fetched. The map will be loaded shortly.", Toast.LENGTH_SHORT).show();
    }

    public void showErrorDialog(String error){
        AlertDialog errorDialog = new AlertDialog.Builder(NearMe.this)
                .setMessage(error)
                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        initiateChecking();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create();
        errorDialog.show();
    }

    public void displaySellers(){
        count++;
        if(count==2){
            Marker markerCurrentLoc = mMap.addMarker(new MarkerOptions().position(currentLoc).title("My Location"));
            markerCurrentLoc.showInfoWindow();
            Log.d("currentloc",currentLoc.latitude +"and "+currentLoc.longitude +"");
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentLoc)
                    .zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            for (int i=0;i<sellerList.size();i++){
                LatLng markloc = new LatLng(sellerList.get(i).getLon(), sellerList.get(i).getLat());
                Marker markerLoc = mMap.addMarker(new MarkerOptions().position(markloc).title(sellerList.get(i).getName()).snippet(sellerList.get(i).getDesc()).icon(BitmapDescriptorFactory.fromResource(Constants.getIconId(sellerList.get(i).getSells()))));

            }
        }
    }
}
