package com.ainosi.iman.bankmandiri.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ainosi.iman.bankmandiri.view.fragment.MPlan10Fragment;
import com.ainosi.iman.bankmandiri.view.fragment.MPlan20Fragment;
import com.ainosi.iman.bankmandiri.view.fragment.MPlan70Fragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    int fragmentCount;

    public PageAdapter(FragmentManager fm, int count) {
        super(fm);
        this.fragmentCount = count;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new MPlan70Fragment();
            case 1:
                return new MPlan20Fragment();
            case 2:
                return new MPlan10Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragmentCount;
    }
}
