package apps.inets.com.shulesoft.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


import apps.inets.com.shulesoft.R;


/**
 * Models an activity with a searchable spinner displaying a list of schools
 */

public class SchoolSearchActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> mSchools;



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schools);
        mSchools = new ArrayList<String>();

        Bundle bundle = getIntent().getExtras();

        mSchools = (ArrayList<String>) bundle.get("Schools");

        //If there is no internet, display message
        TextView noInternet = findViewById(R.id.noInternet_text_view);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_search);
        LinearLayout activityLayout = findViewById(R.id.search_layout);
        if(!isNetworkAvailable()){
            noInternet.setVisibility(View.VISIBLE);
            activityLayout.setVisibility(View.GONE);
        }else{
            noInternet.setVisibility(View.GONE);
            activityLayout.setVisibility(View.VISIBLE);

        }




        adapter = new ArrayAdapter<String>(this, R.layout.schools_list_item, mSchools);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSchool = parent.getItemAtPosition(position).toString();
                Intent webIntent = new Intent
                        (SchoolSearchActivity.this, WebViewActivity.class);
                webIntent.putExtra("School", selectedSchool);
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
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*post-closes the application down the back button is pressed
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        this.finishAffinity();
        this.finish();
    }

}






