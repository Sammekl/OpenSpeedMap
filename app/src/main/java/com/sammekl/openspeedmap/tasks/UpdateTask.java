package com.sammekl.openspeedmap.tasks;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import com.sammekl.openspeedmap.activities.WayViewActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Samme on 21-6-2015.
 */
public class UpdateTask  extends BackgroundTask {

    private String url;
    private String userName;
    private String password;
    private String xml;
    private WayViewActivity wayViewActivity;

    /**
     * @param activity The activity which invoked this task
     */
    public UpdateTask(Activity activity) {
        super(activity);
        this.wayViewActivity = (WayViewActivity) activity;
    }

    /**
     * The task to execute
     */
    @Override
    public String doTask() {
        Long startTime = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
//        httpPost.addHeader("Authorization", "Basic " + Base64.encodeToString((userName + ":" + password).getBytes(), Base64.NO_WRAP));

        try {
            // Set XML
            httpPut.setHeader("Content-Type", "application/xml");
            StringEntity xmlEntity = new StringEntity(xml);
            httpPut.setEntity(xmlEntity);
            HttpResponse response = httpClient.execute(httpPut);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                return "true";
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error in request");
        }
        Long timeTaken = System.currentTimeMillis() - startTime;
        Log.d("UpdateTask.doTask", "time taken: " + timeTaken);
        return "false";
    }

    /**
     * @param result The result from doTask()
     */
    @Override
    public void doProcessResult(String result) {
        if(Boolean.parseBoolean(result)) {
            // Result is true, OSM has been informed to update.
            wayViewActivity.setPositiveDialog();
        }
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setXml(String xml) {
        this.xml = xml;
    }
}
