package com.sammekl.openspeedmap.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.sammekl.openspeedmap.R;
import com.sammekl.openspeedmap.model.Highway;
import com.sammekl.openspeedmap.utils.Constants;

public class WayViewActivity extends ActionBarActivity {

    WebView webView;
    Highway receivedHighway;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_view);

        getHighwayFromIntent();

        initVariables();
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
}
