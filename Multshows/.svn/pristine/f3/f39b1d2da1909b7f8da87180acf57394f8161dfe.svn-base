package com.multshows.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 李萧萧 on 2016/8/30.
 * viewpager适配器
 */
public class HomeFrament_Adapter extends FragmentPagerAdapter {
    List<Fragment> mList;

    public HomeFrament_Adapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mList=list;
    }



    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
