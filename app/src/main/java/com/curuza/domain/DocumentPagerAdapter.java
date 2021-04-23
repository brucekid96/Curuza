package com.curuza.domain;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class DocumentPagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public DocumentPagerAdapter(FragmentManager fm , int NumberOfTabs) {

        super(fm);
        this.mNoOfTabs= NumberOfTabs;

    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllProductsFragment allProductsFragment = new AllProductsFragment();
                return allProductsFragment;
            case 1:
                EnterProductsFragment enterProductsFragment = new EnterProductsFragment();
                return enterProductsFragment;
            case 2:
                ExitProductsFragment exitProductsFragment = new ExitProductsFragment();
                return exitProductsFragment;

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
