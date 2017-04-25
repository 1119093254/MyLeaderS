package com.multshows.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.multshows.Activity.MainActivity;
import com.multshows.Fragment.Home_Fragment;
import com.multshows.Fragment.Find_Fragment;
import com.multshows.Fragment.Friends_Fragment;
import com.multshows.Fragment.MyBaby_Task_Fragment;
import com.multshows.Fragment.My_Fragment;
import com.multshows.Fragment.base.ScrollAbleFragment;
import com.multshows.Login_Static;
import com.multshows.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：主页viewPage适配器
 * 作者：李萧萧
 * 时间：2016.8/30
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    public Home_Fragment mHomefragment = null;
    public MyBaby_Task_Fragment mMyBaby_Task_Fragment = null;
    public Find_Fragment mFindfragment = null;
    private Friends_Fragment mNewsFragment = null;
    private My_Fragment mMyFragment = null;
    Context mContext;
    SaveSharedPreferences mSave=new SaveSharedPreferences();

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mHomefragment = new Home_Fragment();
        mMyBaby_Task_Fragment = new MyBaby_Task_Fragment();
        mFindfragment = new Find_Fragment();
        mNewsFragment = new Friends_Fragment();
        mMyFragment = new My_Fragment();
        mContext=context;
    }




    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                if(mSave.Get_PREFS(mContext, "Type").equals("baby")){
                    fragment = mHomefragment;
                }else if(mSave.Get_PREFS(mContext, "Type").equals("mom")){
                    fragment = mHomefragment;
                }

                break;
            case MainActivity.PAGE_TWO:
                if(mSave.Get_PREFS(mContext, "Type").equals("baby")){
                    fragment = mMyBaby_Task_Fragment;
                }else if(mSave.Get_PREFS(mContext, "Type").equals("mom")){
                    fragment = mFindfragment;
                }
                break;
            case MainActivity.PAGE_THREE:
                fragment = mNewsFragment;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = mMyFragment;
                break;
        }
        return fragment;
    }


}

