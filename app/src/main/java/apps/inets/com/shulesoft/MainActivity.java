package apps.inets.com.shulesoft;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


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
