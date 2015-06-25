package com.sammekl.openspeedmap.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sammekl.openspeedmap.R;
import com.sammekl.openspeedmap.model.Highway;
import com.sammekl.openspeedmap.utils.Constants;
import com.sammekl.openspeedmap.utils.TempStorage;

public class WaysActivity extends ActionBarActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ways);

        Log.i(getClass().getSimpleName(), TempStorage.getAllHighways().toString());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

        initVariables();
        setArrayAdapter();
        setOnClickListener();

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
    // Private methods
    // ====================================
    private void initVariables() {
        lv = (ListView) findViewById(R.id.listView);
    }

    private void setArrayAdapter() {
        ArrayAdapter<Highway> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, TempStorage.getAllHighways());
        lv.setAdapter(arrayAdapter);
    }

    private void setOnClickListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                Highway selected = (Highway) (lv.getItemAtPosition(position));

                getActivity().startActivity(new Intent(getActivity(), WayViewActivity.class).putExtra(Constants.PREF_EXTRA_HIGHWAY, selected));
                getActivity().overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

            }
        });
    }
    private Activity getActivity() {
        return this;
    }
}
