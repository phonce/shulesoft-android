package apps.inets.com.shulesoft.activities;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import apps.inets.com.shulesoft.R;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openFeatureActivity();
    }

    //revert back to FeatureActivity .. parameter----View view
    public void openFeatureActivity(){
        Intent intent = new Intent
                (this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
