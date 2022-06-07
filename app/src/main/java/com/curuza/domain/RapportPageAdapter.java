package com.curuza.domain;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class RapportPageAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;
    String mDate;

    public RapportPageAdapter(FragmentManager fm , int NumberOfTabs,String date) {

        super(fm);
        this.mNoOfTabs= NumberOfTabs;
        this.mDate = date;

    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SelledFragment selledFragment = new SelledFragment(mDate);
                return selledFragment;
            case 1:
                CreditFragment creditFragment = new CreditFragment(mDate);
                return creditFragment;
            case 2:
                DepenseFragment depenseFragment = new DepenseFragment(mDate);
                return depenseFragment;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
