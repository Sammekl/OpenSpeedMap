package com.sammekl.openspeedmap.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sammekl.openspeedmap.R;
import com.sammekl.openspeedmap.model.Highway;
import com.sammekl.openspeedmap.utils.Constants;
import com.sammekl.openspeedmap.utils.OpenSpeedMapService;

public class WayViewActivity extends ActionBarActivity {

    private WebView webView;
    private Highway receivedHighway;
    private int maxspeed;
    int spinnerSelection;
    OpenSpeedMapService openSpeedMapService = new OpenSpeedMapService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_view);

        getHighwayFromIntent();

        initVariables();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
    }

    // ====================================
    // Public methods
    // ====================================

    public void setMaxSpeed(View view) {
        showAlertDialog();
    }

    public void updateSpeed(String xml) {
        openSpeedMapService.updateSpeed(getActivity(), receivedHighway, xml);
    }

    public void setPositiveDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Update succesful");
        alert.setMessage("Way with name '" + receivedHighway.getRoadName() + "' has been updated. It can take up to one hour for the changes to take effect.");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
            }
        });
        alert.show();

    }
    // ====================================
    // Private methods
    // ====================================

    private void getHighwayFromIntent() {
        receivedHighway = (Highway) getIntent().getSerializableExtra(Constants.PREF_EXTRA_HIGHWAY);
        setTitle(getTitle() + " " + receivedHighway.getId());
    }

    private void initVariables() {
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Constants.PREF_OSM_WAY_DISPLAY_URL + receivedHighway.getId());
        final ProgressDialog pd = ProgressDialog.show(this, "", "Loading " + receivedHighway.getRoadName() + "...", true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (pd.isShowing() && pd != null) {
                    pd.dismiss();
                }
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);


        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.my_dialog_layout, null);
        alert.setView(promptsView);

        // Set message
        alert.setTitle("Set maxspeed");
        alert.setMessage("Set maxspeed for " + receivedHighway.getRoadName());
        final AlertDialog alertDialog = alert.create();

        // Set a Spinner view to get user input
        final Spinner input = (Spinner) promptsView.findViewById(R.id.spinner_id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.speed_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input.setAdapter(adapter);
        input.setSelection(0);
        input.setOnItemSelectedListener(new OnSpinnerItemClicked());

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                maxspeed = spinnerSelection;
                Log.e(getClass().getSimpleName(), "Maxspeed: " + maxspeed);
                if (maxspeed < 15 || maxspeed > 130) {
                    Toast.makeText(getActivity(), "Maxspeed must be between 15 and 130", Toast.LENGTH_SHORT).show();
                } else {
                    openSpeedMapService.getHighwayXml(getActivity(), receivedHighway, maxspeed);
                }
            }
        });
        alert.show();
    }

    private Activity getActivity() {
        return this;
    }


    private class OnSpinnerItemClicked implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            spinnerSelection = Integer.parseInt((String) parent.getItemAtPosition(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
