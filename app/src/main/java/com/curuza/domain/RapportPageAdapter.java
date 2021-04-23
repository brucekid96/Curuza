package com.curuza.domain;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class RapportPageAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public RapportPageAdapter(FragmentManager fm , int NumberOfTabs) {

        super(fm);
        this.mNoOfTabs= NumberOfTabs;

    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SelledFragment selledFragment = new SelledFragment();
                return selledFragment;
            case 1:
                CreditFragment creditFragment = new CreditFragment();
                return creditFragment;
            case 2:
                DepenseFragment depenseFragment = new DepenseFragment();
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
