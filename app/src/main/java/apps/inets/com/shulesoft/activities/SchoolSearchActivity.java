package apps.inets.com.shulesoft.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

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
import apps.inets.com.shulesoft.extras.School;
import apps.inets.com.shulesoft.adapters.SchoolAdapter;


/**
 * Created by admin on 19 Jun 2018.
 */

public class SchoolSearchActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    private ArrayList<String > mSchools;

    private EditText editText;

    private ListView listView;

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);

        mRequestQueue = Volley.newRequestQueue(this);

        mSchools = new ArrayList<String>();
        makeHttpRequest();

        /*for (int i = 0; i < 10; i++) {
            mSchools.add("makongo");
            mSchools.add("canossa");
        }*/

        editText = (EditText) findViewById(R.id.search_edit_text);
        editText.setHint("\uD83D\uDD0D     Search Schools");

        listView = (ListView) findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(this,R.layout.schools_list_item,mSchools);

        listView.setAdapter(adapter);

        /**
         * Starts the login activity and passes the clicked school name to it
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentSchool =  adapter.getItem(i);

                Intent loginIntent = new Intent
                        (SchoolSearchActivity.this, LoginActivity.class);
                loginIntent.putExtra("School", currentSchool);
                startActivity(loginIntent);
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               SchoolSearchActivity.this.adapter.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * Makes a network request to return the list of schools
     */
    public void makeHttpRequest(){
        String getSchoolsUrl = "http://158.69.112.216:8081/api/getSchools";

       /* JSONObject params = new JSONObject();

        //String shulesoftChecksum = md5.
        try {
            params= params.put("tag", "getSchools");
            params = params.put("checksum","md5('shulesoft')");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getSchoolsUrl,
                 new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i <response.length(); i++){
                            try {
                                JSONObject school = response.getJSONObject(i);
                                String name = school.getString("table_schema");
                                mSchools.add(name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.v("Response","There is a response");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        Log.v("HAHAHAH",error.toString());
            }
        });
        mRequestQueue.add(jsonArrayRequest);
    }

    /**
     *
     * @return the request queue
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
