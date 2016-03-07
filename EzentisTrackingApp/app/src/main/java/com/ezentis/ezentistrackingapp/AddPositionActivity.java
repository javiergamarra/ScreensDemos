package com.ezentis.ezentistrackingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezentis.ezentistrackingapp.views.DDLFormView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddPositionActivity extends AppCompatActivity implements DDLFormListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String REQUESTING_LOCATION_UPDATES_KEY = "REQUESTING_LOCATION_UPDATES_KEY";
    private static final String LOCATION_KEY = "LOCATION_KEY";
    private static final String LAST_UPDATED_TIME_STRING_KEY = "LAST_UPDATED_TIME_STRING_KEY";
    private GoogleApiClient googleApiClient;
    private boolean requestingLocationUpdates;
    private Location currentLocation;
    private String lastUpdateTime;
    private Button submit;
    private DDLFormScreenlet ddlFormScreenlet;
    private View view;

    private State nextState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_position);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ddlFormScreenlet = (DDLFormScreenlet) findViewById(R.id.ddlform_screenlet);
        ddlFormScreenlet.setListener(this);

        submit = (Button) findViewById(R.id.liferay_form_submit);
        submit.setEnabled(false);
        submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        view = findViewById(android.R.id.content);

        nextState = (State) getIntent().getSerializableExtra("nextState");
        setTitle(nextState.getValue());

        detectIfGPSEnabled();
    }

    private void detectIfGPSEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDDLFormLoaded(Record record) {
        fillUserValues(SessionContext.getCurrentUser());

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

        View view = findViewById(R.id.ddlform_loading_screen_progress_bar);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDDLFormRecordLoaded(Record record) {

    }

    @Override
    public void onDDLFormRecordAdded(Record record) {
        Snackbar.make(view, "Work sent!", Snackbar.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onDDLFormRecordUpdated(Record record) {

    }

    @Override
    public void onDDLFormLoadFailed(Exception e) {

    }

    @Override
    public void onDDLFormRecordLoadFailed(Exception e) {

    }

    @Override
    public void onDDLFormRecordAddFailed(Exception e) {
        Snackbar.make(view, "Send work failed :(", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDDLFormUpdateRecordFailed(Exception e) {

    }

    @Override
    public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

    }

    @Override
    public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

    }

    @Override
    public void loadingFromCache(boolean success) {

    }

    @Override
    public void retrievingOnline(boolean triedInCache, Exception e) {

    }

    @Override
    public void storingToCache(Object object) {

    }

    @Override
    public void onConnected(Bundle bundle) {

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            currentLocation = lastLocation;
            lastUpdateTime = DateFormat.getTimeInstance().format(lastLocation.getTime());
            submit.setEnabled(true);
            submit.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            fillLocationValues(lastLocation);

            TextView gpsQuality = (TextView) findViewById(R.id.gps_quality);
            gpsQuality.setText("GPS Quality: poor");

            View view = findViewById(R.id.ddlform_loading_screen_progress_bar);
            view.setVisibility(View.INVISIBLE);
        }

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);

        requestingLocationUpdates = true;
    }

    private void fillLocationValues(Location location) {
        Record record = ddlFormScreenlet.getRecord();
        double latitude = location.getLatitude();
        record.getFieldByName("Latitude").setCurrentValue(latitude);
        double longitude = location.getLongitude();
        record.getFieldByName("Longitude").setCurrentValue(longitude);

        DDLFormView ddlFormView = (DDLFormView) findViewById(R.id.ddlformview);
        ddlFormView.showRecordValues();
    }

    private void fillUserValues(User user) {
        Record record = ddlFormScreenlet.getRecord();
        record.getFieldByName("Name").setCurrentValue(user.getFirstName());
        record.getFieldByName("Surname").setCurrentValue(user.getLastName());
        String hour = new SimpleDateFormat("HH:mm").format(new Date());
        record.getFieldByName("Time").setCurrentValue(hour);
        record.getFieldByName("Date").setCurrentValue(new Date());
        Field activity = record.getFieldByName("Activity");
        activity.setCurrentStringValue(nextState.getValue());

        DDLFormView ddlFormView = (DDLFormView) findViewById(R.id.ddlformview);
        ddlFormView.showRecordValues();

        TextView label = (TextView) findViewById(R.id.label_position);
        label.setText("Logging " + nextState.getValue() + " at " + hour);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            ddlFormScreenlet.startUploadByPosition(requestCode);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        submit.setEnabled(true);
        submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        currentLocation = location;
        lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        fillLocationValues(location);

        TextView gpsQuality = (TextView) findViewById(R.id.gps_quality);
        gpsQuality.setText("GPS Quality: excellent");

        View view = findViewById(R.id.ddlform_loading_screen_progress_bar);
        view.setVisibility(View.INVISIBLE);

        stopLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, currentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, lastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(REQUESTING_LOCATION_UPDATES_KEY)) {
            requestingLocationUpdates = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY);
        }

        if (savedInstanceState.containsKey(LOCATION_KEY)) {
            currentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
        }

        if (savedInstanceState.containsKey(LAST_UPDATED_TIME_STRING_KEY)) {
            lastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
        }
    }
}
