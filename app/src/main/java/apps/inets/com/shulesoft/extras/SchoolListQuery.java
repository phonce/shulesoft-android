package apps.inets.com.shulesoft.extras;

import android.util.Log;

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
 * This class contains a http request for the list of schools
 */

public final class SchoolListQuery {

    private static ArrayList<String> mSchools = new ArrayList<String>();

    private RequestQueue mRequestQueue;

    public static final String SCHOOL_LIST_URL = "http://158.69.112.216:8081/api/getSchools";

    private SchoolListQuery(){
        //mRequestQueue = Volley.newRequestQueue(this);
    }

    /**
     * Makes a network request to return the list of schools
     */
    public static void makeHttpRequest() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, SCHOOL_LIST_URL, new Response.Listener<JSONArray>() {
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
        //mRequestQueue.add(jsonArrayRequest);

    }

    public static ArrayList<String> getSchoolsList(){
        return mSchools;
    }
}
