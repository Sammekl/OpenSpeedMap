package com.sammekl.openspeedmap.activities;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sammekl.openspeedmap.R;
import com.sammekl.openspeedmap.model.Highway;
import com.sammekl.openspeedmap.utils.TempStorage;

public class WaysActivity extends ActionBarActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ways);

        Log.i(getClass().getSimpleName(), TempStorage.getAllHighways().toString());

        initVariables();
        setArrayAdapter();

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
}
