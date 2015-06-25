package com.sammekl.openspeedmap.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sammekl.openspeedmap.R;
import com.sammekl.openspeedmap.model.Highway;
import com.sammekl.openspeedmap.model.Node;
import com.sammekl.openspeedmap.utils.OpenSpeedMapService;
import com.sammekl.openspeedmap.utils.TempStorage;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Button searchBtn;
    private SeekBar seekBar;
    private TextView progressDecimal;
    private int progress;

    private List<Node> allNodes;
    private List<Highway> allHighways;

    private OpenSpeedMapService openSpeedMapService;

    LocationManager locationManager;
    LocationListener locationListener;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        initVariables();
        setSeekBarListener();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            this.startActivity(new Intent(this, SettingsActivity.class));
            this.overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
        }
        return super.onOptionsItemSelected(item);
    }


    // ====================================
    // Public methods
    // ====================================

    public void searchWays(View view) {
        startLocationListener();

    }

    /**
     * Set from RoadTask
     *
     * @param nodes all the nodes found within the radius
     */

    public void setAllNodes(List<Node> nodes) {
        if (allNodes != null) {
            this.allNodes.clear();
        }
        this.allNodes = nodes;
    }

    /**
     * Set from RoadTask
     *
     * @param highways all the highways found within the radius
     */
    public void setAllHighways(List<Highway> highways) {
        if (allHighways != null) {
            this.allHighways.clear();
        }
        this.allHighways = highways;
    }


    /**
     * Invoked from RoadTask, start the List Activity.
     */
    public void startDisplayActivity() {
        if(TempStorage.getAllHighways() != null && TempStorage.getAllHighways().size() > 0 && TempStorage.getAllNodes() != null && TempStorage.getAllNodes().size() > 0) {
            this.startActivity(new Intent(this, WaysActivity.class));
            this.overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
        } else {
            Toast.makeText(this, "No ways found within " + progress + " meters of your location", Toast.LENGTH_SHORT).show();
        }

    }

    // ====================================
    // Private methods
    // ====================================

    private void initVariables() {
        searchBtn = (Button) findViewById(R.id.search_button);
        seekBar = (SeekBar) findViewById(R.id.seekRange);
        progressDecimal = (TextView) findViewById(R.id.progressDecimal);
        progressDecimal.setText(seekBar.getProgress() + " meters");
        progress = seekBar.getProgress();
    }

    private void setSeekBarListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int prog, boolean b) {
                progressDecimal.setText(prog + " meters");
                progress = prog;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do something if on start of touch event.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do something if event stops.
            }
        });
    }

    private void startLocationListener() {
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                dismissProgressDialog();

                openSpeedMapService = new OpenSpeedMapService();
                openSpeedMapService.getHighwayData(getActivity(), progress, location.getLatitude(), location.getLongitude());

                if (locationListener != null) {
                    locationManager.removeUpdates(locationListener);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status changed", "Provider: " + provider);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider", "enable");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider", "disable");
            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        showLoadingProgressDialog();
    }

    private void showLoadingProgressDialog() {
        this.showProgressDialog(this.getString(R.string.dialog_get_location));
    }

    private void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private Activity getActivity() {
        return this;
    }
}
