package apps.inets.com.shulesoft.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.extras.School;
import apps.inets.com.shulesoft.adapters.SchoolAdapter;


/**
 * Created by admin on 19 Jun 2018.
 */

public class SchoolSearchActivity extends AppCompatActivity {

    private ArrayAdapter<School> adapter;

    private EditText editText;

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);

        ArrayList<School> schools = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            schools.add(new
                    School("Makongo", "https://makongo.shulesoft.com"));
            schools.add(new
                    School("Canossa", "https://canossa.shulesoft.com"));
        }
        editText = (EditText) findViewById(R.id.search_edit_text);
        listView = (ListView) findViewById(R.id.list);

        adapter = new SchoolAdapter(this, schools);



        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                School currentSchool = adapter.getItem(i);

                String currentSchoolUrl = currentSchool.getUrl();

                Intent loginIntent = new Intent
                        (SchoolSearchActivity.this, LoginActivity.class);
                loginIntent.putExtra("URL", currentSchoolUrl);
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
}
