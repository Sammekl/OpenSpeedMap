package com.sammekl.openspeedmap.tasks;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import com.sammekl.openspeedmap.activities.WayViewActivity;
import com.sammekl.openspeedmap.helpers.XMLHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Samme on 21-6-2015.
 */
public class GetHighwayTask extends BackgroundTask {

    private String url;
    private String userName;
    private String password;

    private int maxspeed;
    private WayViewActivity wayViewActivity;

    /**
     * @param activity The activity which invoked this task
     */
    public GetHighwayTask(Activity activity) {
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
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Basic " + Base64.encodeToString("user:password".getBytes(), Base64.NO_WRAP));
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error in request");
        }
        Long timeTaken = System.currentTimeMillis() - startTime;
        Log.d("GetHighwayTask.doTask", "time taken: " + timeTaken);
        return stringBuilder.toString();
    }

    /**
     * @param result The result from doTask()
     */
    @Override
    public void doProcessResult(String result) {
        Log.e(getClass().getSimpleName(), "Result: " + result);
        try {
            String parsed = XMLHelper.parseDom(result, maxspeed);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
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


    public int getMaxspeed() {
        return maxspeed;
    }

    public void setMaxspeed(int maxspeed) {
        this.maxspeed = maxspeed;
    }
}
