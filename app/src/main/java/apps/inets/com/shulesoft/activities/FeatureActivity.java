package apps.inets.com.shulesoft.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.adapters.MyViewPagerAdapter;


public class FeatureActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] images, feature_texts, layouts, feature_header;
    private Button btnSkip, btnGotIt;
    private ArrayList<String> mSchools;
    private HashMap<String, String> schoolMaps;

    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        schoolMaps = (HashMap<String, String>) intent.getSerializableExtra("Schools");


        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.swipe_screen);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnGotIt = (Button) findViewById(R.id.btn_got_it);
        btnGotIt.setVisibility(View.GONE);


        images = new int[]{
                R.drawable.exam_reports, R.drawable.accounting, R.drawable.mobile_payment, R.drawable.free_sms};
        feature_texts = new int[]{R.string.exam_reports, R.string.accounting, R.string.mobile_payments, R.string.free_sms};
        feature_header = new int[]{R.string.exam_reports_header, R.string.accounting_header, R.string.mobile_payments_header, R.string.free_sms_header};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter(this, images, feature_texts, feature_header);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        //Log.v("prefManager","first3  " + prefs.getBoolean("firstrun", true));
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginScreen();
            }
        });

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginScreen();
            }
        });

    }


    /**
     * Adds bottom dots to the bottom of a swipe page
     * corresponding to the current page selected
     */
    private void addBottomDots(int currentPage) {
        dots = new TextView[images.length];

        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.black));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.white));
    }

    //gets the current viewPager item
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //starts the LogInActivity
    private void launchLoginScreen() {
        Intent intent = new Intent(FeatureActivity.this, LoginActivity.class);
        intent.putExtra("Schools", schoolMaps);
        startActivity(intent);
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == images.length - 1) {
                // last page. make button text to GOT IT
                btnGotIt.setVisibility(View.VISIBLE);
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnGotIt.setVisibility(View.GONE);
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        this.finishAffinity();
        this.finish();
    }
}



