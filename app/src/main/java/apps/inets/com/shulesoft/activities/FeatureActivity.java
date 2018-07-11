package apps.inets.com.shulesoft.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSchools = (ArrayList<String>) getIntent().getExtras().get("Schools");

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.swipe_screen);
      //ImageView backGround = findViewById(R.id.image_holder_one);
       // backGround.setImageBitmap(getBitmap(this, R.drawable.backdrop));

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnGotIt = (Button) findViewById(R.id.btn_got_it);
        btnGotIt.setVisibility(View.GONE);

        //images and texts of the sliding pages
        images = new int[]{
                R.drawable.exam_reports, R.drawable.accounting, R.drawable.mobile_payment, R.drawable.free_sms};
        texts = new int[]{R.string.exam_reports, R.string.accounting, R.string.mobile_payments, R.string.free_sms};


        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter(this, images, texts);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        //Log.v("prefManager","first3  " + prefs.getBoolean("firstrun", true));
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSearchScreen();
            }
        });

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSearchScreen();
            }
        });
        //prefs.edit().putBoolean("firstrun", false).commit();

    }

    /**
     * Returns a scalable bitmap of the imageId passed in
     *
     * @param context
     * @param drawableId
     * @return
     */

    @SuppressLint("NewApi")
    public Bitmap getBitmap(Context context, int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        return Bitmap.createScaledBitmap(bitmap, width, height, true);

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

    //starts the SchoolSearchActivity
    private void launchSearchScreen() {
        Intent intent = new Intent(FeatureActivity.this, SchoolSearchActivity.class);
        intent.putExtra("Schools", mSchools);
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



