package apps.inets.com.shulesoft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import com.android.volley.RequestQueue;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void searchSchools(View view) {
        Intent intent = new Intent
                (this, SchoolSearchActivity.class);
        startActivity(intent);
    }



}
