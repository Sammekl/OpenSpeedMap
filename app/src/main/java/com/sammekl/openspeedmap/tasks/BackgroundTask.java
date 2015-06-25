package com.sammekl.openspeedmap.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.sammekl.openspeedmap.R;

/**
 * Created by Samme on 20-6-2015.
 */
public abstract class BackgroundTask extends AsyncTask<Void, Void, String> {

    ///////////////////////////////////
    // Properties
    ///////////////////////////////////

    private ProgressDialog progressDialog;
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
        dismissProgressDialog();
    }
    ///////////////////////////////////
    // Public Methods
    ///////////////////////////////////

    public abstract void doProcessResult(String result);

    public abstract String doTask();

    public void showLoadingProgressDialog(String msg) {
        this.showProgressDialog(msg);
    }

    private void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
