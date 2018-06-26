package apps.inets.com.shulesoft.slideshow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apps.inets.com.shulesoft.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * not really used now
 */
public class ThirdScreen extends Fragment {


    public ThirdScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.screen_three,container,false);


        return rootView;
    }

}
