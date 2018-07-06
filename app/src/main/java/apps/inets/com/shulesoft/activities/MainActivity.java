package apps.inets.com.shulesoft.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);
        mSchools = new ArrayList<String>();


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

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * Opens FeatureActivity
     */
    public void openFeatureActivity() {
        Intent intent = new Intent
                (this, FeatureActivity.class);
        intent.putExtra("Schools",mSchools);
        startActivity(intent);
    }

    /**
     * Exits the application when the back button is pressed
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        this.finishAffinity();
        System.exit(0);
    }


}
