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
import apps.inets.com.shulesoft.R;



public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mSchools;
    private RequestQueue mRequestQueue;
    private Boolean firstTime = null;

    private static final  String SCHOOLS = "Schools";
    private static final String FIRST_TIME_PREF_KEY = "firstTime";
    private static final String IS_FIRST_TIME_PREF = "first_time";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);
        mSchools = new ArrayList<>();


        makeHttpRequest();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                TextView textView = findViewById(R.id.initializing_text_view);
                textView.setText(getResources().getString(R.string.slow_interent));
            }

        }, 60000L);
    }

    /**
     * Makes a network request to return the list of schools
     */
    public void makeHttpRequest() {
        String getSchoolsUrl = "http://158.69.112.216:8081/api/getSchools";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getSchoolsUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject school = response.getJSONObject(i);
                        String name = school.getString("table_schema");
                        mSchools.add(name);
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
        //Opens FeatureActivity if its the first run since Installation
            //if(isFirstTime()){
                // Checking for first time launch - before calling setContentView()
                Intent intent = new Intent
                        (this, FeatureActivity.class);
                intent.putExtra(SCHOOLS,mSchools);
                startActivity(intent);
//            }
//        //Opens SchoolSearchActivity if its not the first run
//            else{
//                Intent intent = new Intent
//                        (this, SchoolSearchActivity.class);
//                intent.putExtra(SCHOOLS,mSchools);
//                startActivity(intent);
//            }
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
}

