package com.aafilogic.fc6;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aafilogic.fc6.fragment.challengeFragment;
import com.aafilogic.fc6.fragment.menuFragment;
import com.aafilogic.fc6.fragment.notificationFragment;
import com.aafilogic.fc6.fragment.profileFragment;
import com.aafilogic.fc6.fragment.timelineFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                timelineFragment tab1 = new timelineFragment();
                return tab1;
            case 1:
                challengeFragment tab2 = new challengeFragment();
                return tab2;
            case 2:
                profileFragment tab3 = new profileFragment();
                return tab3;
            case 3:
                notificationFragment tab4 = new notificationFragment();
                return tab4;
            case 4:
                menuFragment tab5 = new menuFragment();
                return tab5;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}