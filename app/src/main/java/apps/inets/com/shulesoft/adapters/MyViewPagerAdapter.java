package apps.inets.com.shulesoft.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import apps.inets.com.shulesoft.R;
import apps.inets.com.shulesoft.activities.FeatureActivity;

/**
 * View pager adapter to change the pages as one slides across
 */
public class MyViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private int[] mImages, mTexts;


    public MyViewPagerAdapter(Context context, int[] images, int[] texts) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImages = images;
        mTexts = texts;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = mLayoutInflater.inflate(R.layout.screen_one, container, false);
        ImageView imageView = view.findViewById(R.id.image_feature);
        //imageView.setImageResource(mImages[position]);//mResources[position]
        imageView.setImageBitmap(getBitmap(mContext,mImages[position]));
        TextView featuresText = view.findViewById(R.id.features_text);
        featuresText.setText(mTexts[position]);

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
        View view = (View) object;
        container.removeView(view);
    }

    public Bitmap getBitmap(Context context, int drawableId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
       return  Bitmap.createScaledBitmap(bitmap, width, height, true);
        //BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        ///view.setBackground(bitmapDrawable);
    }
}