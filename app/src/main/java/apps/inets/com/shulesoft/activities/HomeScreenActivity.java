package apps.inets.com.shulesoft.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import apps.inets.com.shulesoft.R;


public class HomeScreenActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_root_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.the_Toolbar);


        //setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        if (navigationView != null) {
//            setupDrawerContent(navigationView);
//        }
//
//        Button navigationDrawerButton = (Button) findViewById(R.id.navigation_icon);
//        navigationDrawerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDrawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
//            case R.id.menu_night_mode_system:
//                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
//                break;
//            case R.id.menu_night_mode_day:
//                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                break;
//            case R.id.menu_night_mode_night:
//                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                break;
//            case R.id.menu_night_mode_auto:
//                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    /*
        Restarts the school search activity when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        Intent schoolNameIntent = getIntent();

        String schoolName = "";
        if(schoolNameIntent.getExtras() != null){
             schoolName = (String) schoolNameIntent.getExtras().get("School");
        }
        Intent backIntent = new Intent(this,LoginActivity.class);
        backIntent.putExtra("School",schoolName);
        startActivity(backIntent);
    }
}
