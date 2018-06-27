package apps.inets.com.shulesoft;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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


/**
 * Created by admin on 19 Jun 2018.
 */

public class SchoolSearchActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    private ArrayList<String> mSchools;


    private RequestQueue mRequestQueue;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(this);
        mSchools = new ArrayList<String>();
        makeHttpRequest();
        setContentView(R.layout.activity_schools);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_search);
        

        adapter = new ArrayAdapter<String>(this, R.layout.schools_list_item, mSchools);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSchool = parent.getItemAtPosition(position).toString();
                Intent loginIntent = new Intent
                        (SchoolSearchActivity.this, LoginActivity.class);
                loginIntent.putExtra("School", selectedSchool);
                startActivity(loginIntent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /**
     * Makes a network request to return the list of schools
     */
    public void makeHttpRequest() {
        String getSchoolsUrl = "http://158.69.112.216:8081/api/getSchools";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getSchoolsUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject school = response.getJSONObject(i);
                                String name = school.getString("table_schema");
                                mSchools.add(name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.v("Response", "There is a response");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("SERVER ERROR", error.toString());
            }
        });
        mRequestQueue.add(jsonArrayRequest);
    }

    /*    *//**
         * Checks if network connection is available
         *//*

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }*/


}

