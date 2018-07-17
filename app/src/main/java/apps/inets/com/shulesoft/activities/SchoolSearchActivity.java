package apps.inets.com.shulesoft.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.adapters.SchoolAdapter;


/**
 * Models an activity with a searchable spinner to display the list of schools
 */

public class SchoolSearchActivity extends AppCompatActivity {

    private static final String SCHOOLS_LIST = "Schools";
    private ArrayList<String> schoolNames;
    private HashMap<String, String> schoolMaps;
    //private RippleDrawable rippleDrawable;

    private EditText editText;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schools);

        Intent intent = getIntent();
        schoolMaps = (HashMap<String, String>) intent.getSerializableExtra("Schools");

        schoolNames = new ArrayList<>();
        Iterator it = schoolMaps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            schoolNames.add((String) pair.getKey());
        }

        //If there is no internet, display message
        TextView noInternet = findViewById(R.id.noInternet_text_view);

        editText = (EditText) findViewById(R.id.search_edit_text);
        editText.setHint("\uD83D\uDD0D     Search Schools");
        listView = (ListView) findViewById(R.id.list);
        listView.setDivider(new ColorDrawable(Color.parseColor("#1abc9c")));
        listView.setDividerHeight(2);


        adapter = new SchoolAdapter(this, schoolNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 String selectedSchool = adapter.getItem(i).toString();
                 Intent webIntent = new Intent
                         (SchoolSearchActivity.this, WebViewActivity.class);
                 webIntent.putExtra("School", selectedSchool);
                 webIntent.putExtra("SchoolMaps", schoolMaps);
                 startActivity(webIntent);
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






