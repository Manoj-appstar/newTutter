package com.appstar.tutionportal.institute.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appstar.tutionportal.institute.fragments.FragmentClasses;
import com.appstar.tutionportal.institute.fragments.FragmentInstitute;

public class PagerHomeAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PagerHomeAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragmentInstitute();
        } else
            return new FragmentClasses();

    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Institute / Branch";
            case 1:
                return "Classes";


            default:
                return null;
        }
    }

}