package apps.inets.com.shulesoft.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import apps.inets.com.shulesoft.R;


public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        //getting the card views from the home screen layout
        CardView reportCard =  findViewById(R.id.card_view_reports);
        CardView paymentCard = findViewById(R.id.card_view_payments);
        CardView schoolCalendarCard = findViewById(R.id.card_school_calendar);
        CardView smsAnnouncements = findViewById(R.id.card_sms_announcements);

    }
}
