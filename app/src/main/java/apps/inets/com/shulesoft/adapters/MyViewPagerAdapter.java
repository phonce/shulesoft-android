package apps.inets.com.shulesoft.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.activities.FeatureActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * View pager adapter to change the pages as one slides across
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private int[] mImages, mTexts, mHeaders;


    public MyViewPagerAdapter(Context context, int[] images, int[] texts, int[] headers) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImages = images;
        mTexts = texts;
        mHeaders = headers;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = mLayoutInflater.inflate(R.layout.screen_one, container, false);
        ImageView imageView = view.findViewById(R.id.image_feature);
        imageView.setImageResource(mImages[position]);
        TextView featuresText = view.findViewById(R.id.features_text);
        featuresText.setText(mTexts[position]);
        TextView headersText = view.findViewById(R.id.features_header);
        headersText.setText(mHeaders[position]);



        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}