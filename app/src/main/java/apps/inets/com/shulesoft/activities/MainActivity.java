package apps.inets.com.shulesoft.activities;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.activities.SchoolSearchActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }

    public void searchSchools(View view){
        Intent intent = new Intent
                (this,SchoolSearchActivity.class);
        startActivity(intent);
    }

}
