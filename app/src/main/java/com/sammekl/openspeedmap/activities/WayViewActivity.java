package com.sammekl.openspeedmap.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sammekl.openspeedmap.R;
import com.sammekl.openspeedmap.model.Highway;
import com.sammekl.openspeedmap.utils.Constants;
import com.sammekl.openspeedmap.utils.OpenSpeedMapService;

public class WayViewActivity extends ActionBarActivity {

    WebView webView;
    Highway receivedHighway;
    int maxspeed;
    OpenSpeedMapService openSpeedMapService = new OpenSpeedMapService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_view);

        getHighwayFromIntent();

        initVariables();

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

    public void setMaxSpeed(View view) {
        showAlertDialog();
    }

    public void updateSpeed(String xml) {
        openSpeedMapService.updateSpeed(getActivity(), receivedHighway, xml);
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
    }

    private void showAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Set maxspeed");
        alert.setMessage("Set maxspeed for " + receivedHighway.getRoadName());

        // Create new layout to change width of input
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        layout.addView(input, params);

        alert.setView(layout);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                maxspeed = Integer.parseInt(input.getText().toString());
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

}
