package apps.inets.com.shulesoft.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.adapters.MyViewPagerAdapter;

public class FeatureActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] images, texts;
    private Button btnSkip, btnGotIt;
    private ArrayList<String> mSchools;
    private RequestQueue mRequestQueue;

    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // // Checking for first time launch - before calling setContentView()
        // prefManager = new PrefManager(this);
        // if (!prefManager.isFirstTimeLaunch()) {
        //     launchHomeScreen();
        //     finish();
        // }

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


        // layouts of all welcome sliders
        // add few more layouts if you want
                images = new int[]{
                        R.drawable.exam_reports,R.drawable.accounting,R.drawable.mobile_payment,R.drawable.free_sms};
                texts = new int[] {R.string.exam_reports,R.string.accounting,R.string.mobile_payments,R.string.free_sms};


        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter(this,images,texts);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

         btnSkip.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(FeatureActivity.this,SchoolSearchActivity.class);
                 intent.putExtra("Schools",mSchools);
                 startActivity(intent);
             }
         });

         btnGotIt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(FeatureActivity.this,SchoolSearchActivity.class);
                 intent.putExtra("Schools",mSchools);
                 startActivity(intent);
             }
         });


        mRequestQueue = Volley.newRequestQueue(this);
        mSchools = new ArrayList<String>();
        makeHttpRequest();
    }


    /*post - adding bottom dots to the bottom of a swipe page
    * corresponding to the current page selected*/
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

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    // private void launchHomeScreen() {
    //     prefManager.setFirstTimeLaunch(false);
    //     startActivity(new Intent(FeatureActivity.this, MainActivity.class));
    //     finish();
    // }

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


    /**
     * Makes a network request to return the list of schools
     */
    public void makeHttpRequest() {
        String getSchoolsUrl = "http://158.69.112.216:8081/api/getSchools";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, getSchoolsUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject school = response.getJSONObject(i);
                        String name = school.getString("table_schema");
                        mSchools.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.v("Response", "There is a response");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("SERVER ERROR", error.toString());
            }
        });
        mRequestQueue.add(jsonArrayRequest);

    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * Ensures that pressing the back button opens splash screnn not SchoolSearchActivity
     */

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}

