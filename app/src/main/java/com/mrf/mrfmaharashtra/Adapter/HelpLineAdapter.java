package com.mrf.mrfmaharashtra.Adapter;

import android.content.Context;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mrf.mrfmaharashtra.Fragment.AllIndiaHelpline;
import com.mrf.mrfmaharashtra.Fragment.DistrictOfficeFragment;
import com.mrf.mrfmaharashtra.Fragment.WomenHelpline;

public class HelpLineAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public HelpLineAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllIndiaHelpline allIndiaHelpline = new AllIndiaHelpline();
                return allIndiaHelpline;
            case 1:
                WomenHelpline womenHelpline = new WomenHelpline();
                return womenHelpline;
            case 2:
                DistrictOfficeFragment districtOfficeFragment = new DistrictOfficeFragment();
                return districtOfficeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
