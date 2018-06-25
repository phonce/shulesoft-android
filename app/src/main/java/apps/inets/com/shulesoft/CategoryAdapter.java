package apps.inets.com.shulesoft;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by katherinekuan on 4/14/16.
 *
 * not really used now
 */
public class CategoryAdapter extends FragmentPagerAdapter {


    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FirstScreen();
        } else if (position == 1) {
            return new SecondScreen();
        } else if (position == 2) {
            return new ThirdScreen();
        } else {
            return new FourthScreen();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }


    /*@Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Numbers";
        } else if (position == 1) {
            return "Family Members";
        } else if (position == 2) {
            return "Colors";
        } else {
            return "Phrases";
        }
    }
    */
}