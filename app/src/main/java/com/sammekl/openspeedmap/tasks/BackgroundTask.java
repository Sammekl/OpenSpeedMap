package com.sammekl.openspeedmap.tasks;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by Samme on 20-6-2015.
 */
public abstract class BackgroundTask extends AsyncTask<Void, Void, String> {

    ///////////////////////////////////
    // Properties
    ///////////////////////////////////

    protected Activity activity;
    public BackgroundTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.doTask();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        doProcessResult(result);
    }
    ///////////////////////////////////
    // Public Methods
    ///////////////////////////////////

    public abstract void doProcessResult(String result);

    public abstract String doTask();
}
