package com.users.qwikhomeservices.activities.welcome;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.users.qwikhomeservices.R;
import com.users.qwikhomeservices.activities.ItemViewClickEvents;
import com.users.qwikhomeservices.adapters.SlidePagerAdapter;
import com.users.qwikhomeservices.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    private static int MAX_LAYOUT = 3;
    ActivityWelcomeBinding activityWelcomeBinding;
    ItemViewClickEvents itemViewClickEvents;
    private int counterPage;
    private boolean isLastPageSwiped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityWelcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        itemViewClickEvents = new ItemViewClickEvents(this);
        activityWelcomeBinding.setOnItemClick(itemViewClickEvents);

        initViews();

    }


    private void initViews() {
        SlidePagerAdapter slidePagerAdapter = new SlidePagerAdapter(this);
        activityWelcomeBinding.Viewpager.setAdapter(slidePagerAdapter);
        activityWelcomeBinding.Viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == MAX_LAYOUT - 1 && !isLastPageSwiped) {
                    if (counterPage != 0) {
                        isLastPageSwiped = true;
                        activityWelcomeBinding.showTerms.setVisibility(View.VISIBLE);
                    }
                    counterPage++;
                } else {
                    counterPage = 0;

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        activityWelcomeBinding.slideDots.setViewPager(activityWelcomeBinding.Viewpager);
        activityWelcomeBinding.slideDots.setBackgroundColor(getResources().getColor(R.color.purple));
        activityWelcomeBinding.termsAndConditions.setText(Html.fromHtml(getResources().getString(R.string.termAndConditions)));


    }


}
