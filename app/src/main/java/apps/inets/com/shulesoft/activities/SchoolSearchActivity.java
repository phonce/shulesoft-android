package apps.inets.com.shulesoft.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import apps.inets.com.shulesoft.R;


/**
 * Models an activity with a searchable spinner to display the list of schools
 */

public class SchoolSearchActivity extends AppCompatActivity {

    private static final String SCHOOLS_LIST = "Schools";
    private ArrayList<String> schoolNames;
    private HashMap<String, String> schoolMaps;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schools);

        Intent intent = getIntent();
        schoolMaps = (HashMap<String, String>) intent.getSerializableExtra("Schools");

        schoolNames = new ArrayList<String>();
        Iterator it = schoolMaps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            schoolNames.add((String) pair.getKey());
        }

        //If there is no internet, display message
        TextView noInternet = findViewById(R.id.noInternet_text_view);
        Spinner spinner = findViewById(R.id.spinner_search);
        LinearLayout activityLayout = findViewById(R.id.search_layout);
        if (!isNetworkAvailable()) {
            noInternet.setVisibility(View.VISIBLE);
            activityLayout.setVisibility(View.GONE);
        } else {
            noInternet.setVisibility(View.GONE);
            activityLayout.setVisibility(View.VISIBLE);

        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.schools_list_item, schoolNames);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSchool = parent.getItemAtPosition(position).toString();
                Intent webIntent = new Intent
                        (SchoolSearchActivity.this, WebViewActivity.class);
                webIntent.putExtra("School", selectedSchool);
                webIntent.putExtra("SchoolMaps", schoolMaps);
                startActivity(webIntent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



  /*  public void onBackPressed() {
        startActivity(new Intent(this, FeatureActivity.class));
    }*/


    /**
     * Checks if network connection is available
     */

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Leaves the application when back button is pressed
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        this.finishAffinity();
        this.finish();
    }

}






