package apps.inets.com.shulesoft.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import apps.inets.com.shulesoft.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    //revert back to FeatureActivity .. parameter----View view
    public void openFeatureActivity(View view) {
        Intent intent = new Intent
                (this, FeatureActivity.class);
        startActivity(intent);
    }

    /**
     * Exits the application when the back button is pressed
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }


}
