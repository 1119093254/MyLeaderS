package com.multshows.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.multshows.Utils.CLog;

import java.util.List;

/**
 * 循环viewpager适配器
 */
public class HomeFrament_c_Adapter extends FragmentPagerAdapter {
    List<Fragment> mList;

    public HomeFrament_c_Adapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mList=list;
    }



    @Override
    public Fragment getItem(int position)
    {
        if(mList.size()!=0) {
            position %= mList.size();
            if (position < 0) {
                position = mList.size() + position;
            }
        }
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        //Warning：不要在这里调用removeView
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        CLog.e("testing",position+"到这里了");
        Fragment view=null;
        if(mList.size()!=0) {
            position %= mList.size();
            if (position < 0) {
                position = mList.size() + position;
            }
            view = mList.get(position);
        }


        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
       /* ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }*/
        //container.addView(view);
        //add listeners here if necessary
        return view;
    }
}
