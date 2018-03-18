package hackathon.rajasthan.rajasthantourism;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hackathon.rajasthan.rajasthantourism.model.EmergencyObject;
import hackathon.rajasthan.rajasthantourism.utils.InternetChecker;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;

public class EmergencyActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    ConstraintLayout constraintUnsuccessful;
    ConstraintLayout constraintSuccessful;
    TextView txtErrorMessage;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_CHECK_SETTINGS = 404;
    AlertDialog dialog;
    private boolean locationServiceConnected;
    DatabaseReference emergencyDB;
    String IMEI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        emergencyDB = FirebaseDatabase.getInstance().getReference("Emergency");

        txtErrorMessage = findViewById(R.id.textView);
        constraintSuccessful = findViewById(R.id.constraintSuccessful);
        constraintUnsuccessful = findViewById(R.id.constraintUnsuccessful);
        constraintUnsuccessful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintSuccessful.setVisibility(View.GONE);
                constraintUnsuccessful.setVisibility(View.GONE);
                initiateChecking();
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(2000)                     //TODO: Change these values to 1000 or more if multiple intents take place in onLocationChanged
                .setFastestInterval(2000);             //TODO: Change these values to 1000 or more if multiple intents take place in onLocationChanged

        initiateChecking();
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
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {         //PERMISSION RELATED METHOD
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED));

                    if (locationAccepted) {
                        enableGPS();
                    } else {
                        txtErrorMessage.setText("Permissions Denied, Please Allow Permissions.");
                        constraintUnsuccessful.setVisibility(View.VISIBLE);
                        constraintSuccessful.setVisibility(View.GONE);
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
                            } else {

                                AlertDialog permissionSettings = new AlertDialog.Builder(EmergencyActivity.this)
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
        dialog = new AlertDialog.Builder(EmergencyActivity.this)
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
                            if (ActivityCompat.checkSelfPermission(EmergencyActivity.this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, EmergencyActivity.this);
                            }
                        }
                        else {
                            txtErrorMessage.setText("ERROR: Not able to connect to Location Service");
                            constraintUnsuccessful.setVisibility(View.VISIBLE);
                            constraintSuccessful.setVisibility(View.GONE);
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:               //GPS NOT ALREDY TURNED ON, SHOW TURN ON DIALOG
                        try {
                            status.startResolutionForResult(EmergencyActivity.this, REQUEST_CHECK_SETTINGS); //CREATING DIALOG FOR GPS TURN ON
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:       //GPS CANNOT BE TURNED ON
                        txtErrorMessage.setText("Permission Available, not able to turn on GPS");
                        constraintUnsuccessful.setVisibility(View.VISIBLE);
                        constraintSuccessful.setVisibility(View.GONE);
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
                    if (ActivityCompat.checkSelfPermission(EmergencyActivity.this, ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) EmergencyActivity.this);
                    }
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {               //GPS TURN ON REJECTED
                txtErrorMessage.setText("Please enable the GPS!");
                constraintUnsuccessful.setVisibility(View.VISIBLE);
                constraintSuccessful.setVisibility(View.GONE);
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



    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(Location location) {// Called in equal intervals of time after requestLocationUpdates is called
        if (InternetChecker.isOnline(EmergencyActivity.this)){
            IMEI = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            constraintUnsuccessful.setVisibility(View.GONE);
            constraintSuccessful.setVisibility(View.VISIBLE);
            emergencyDB.child(IMEI).setValue(new EmergencyObject(IMEI, location.getLatitude(), location.getLongitude()));
        }
        else{
            constraintUnsuccessful.setVisibility(View.VISIBLE);
            constraintSuccessful.setVisibility(View.GONE);
            txtErrorMessage.setText("PLEASE ENABLE YOUR INTERNET!!");
        }
    }
}
