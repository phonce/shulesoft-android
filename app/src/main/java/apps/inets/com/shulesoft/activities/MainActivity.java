package apps.inets.com.shulesoft.activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.extras.ConnectionService;


public class MainActivity extends AppCompatActivity  {
    private HashMap<String, String> schoolMaps;
    private ArrayList<String> mSchools;
    private RequestQueue mRequestQueue;
    private Boolean firstTime = null;

    private static final String SCHOOLS = "Schools";
    private static final String FIRST_TIME_PREF_KEY = "firstTime";
    private static final String IS_FIRST_TIME_PREF = "first_time";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRequestQueue = Volley.newRequestQueue(this);
        mSchools = new ArrayList<>();
        schoolMaps = new HashMap<String, String>();


        makeHttpRequest();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                TextView textView = findViewById(R.id.initializing_text_view);
                textView.setText(getResources().getString(R.string.slow_interent));
            }
        }, 4000L);
        
    }

    /**
     * Makes a network request to return the list of schools
     */
    public void makeHttpRequest() {
        String getSchoolsUrl = "http://158.69.112.216:8081/api/getSchools";
        String deviceModel = android.os.Build.MODEL;
        String deviceManufacture = android.os.Build.MANUFACTURER;

        //sending device info
        JSONObject params = new JSONObject();
        try {
            params.put("Device Manufacture", deviceManufacture);
            params.put("Device Model", deviceModel);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getSchoolsUrl, params, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject school = response.getJSONObject(i);
                        String name = school.getString("sname");
                        String urlString = school.getString("table_schema");
                        schoolMaps.put(name,urlString);
                        openFeatureActivity();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mRequestQueue.add(jsonArrayRequest);
    }

   /* public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }*/

    /**
     * Opens FeatureActivity
     */
    public void openFeatureActivity() {
        stopService(new Intent(this, ConnectionService.class));
        if (isFirstTime()) {
            // Checking for first time launch - before calling setContentView()
            Intent intent = new Intent
                    (this, FeatureActivity.class);
            intent.putExtra(SCHOOLS, schoolMaps);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent(this, SchoolSearchActivity.class);
        intent.putExtra("Schools", schoolMaps);
        startActivity(intent);
    }


    /**
     * Checks if the user is opening the app for the first time.
     */
    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences(IS_FIRST_TIME_PREF, Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean(FIRST_TIME_PREF_KEY, true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean(FIRST_TIME_PREF_KEY, false);
                editor.apply();
            }
        }
        return firstTime;
    }


   /* *//**
     * Starts the connection service
     *//*
    public void startConnectionService() {
        Intent intent = new Intent(this, ConnectionService.class);
        // Interval in seconds
        intent.putExtra(ConnectionService.TAG_INTERVAL, 20);
        // URL to ping
        intent.putExtra(ConnectionService.TAG_URL_PING, "http://158.69.112.216:8081/api/getSchools");
        // Name of the class that is calling this service
        intent.putExtra(ConnectionService.TAG_ACTIVITY_NAME, this.getClass().getName());
        // Starts the service
        startService(intent);
    }

    *//**
     * When there is internet connection
     *//*
    @Override
    public void hasInternetConnection() {
        //makeHttpRequest();
        Log.v("HAS_INTERNET", "jahha");
    }

    *//**
     * When there is no Internet connection
     *//*
    @Override
    public void hasNoInternetConnection() {
        TextView textView = findViewById(R.id.initializing_text_view);
        textView.setText(getResources().getString(R.string.no_Internet_Connection));
    }
    */
}

