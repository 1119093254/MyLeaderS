package com.multshows.Utils;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.R;

/**
 * 妈妈还是宝宝
 */
public class MomORBaby_Utils {
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    String type;
    public void isMomORBaby(Activity activity, RelativeLayout mLayout, View mView, ImageView mReturn, TextView title, TextView mOther){
        type=mSharedPreferences.Get_PREFS(activity,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        if("mom".equals(type)) {
            //妈妈端
            MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(activity,R.color.app_topColor);
            mLayout.setBackgroundColor(activity.getResources().getColor(R.color.app_topColor));
            mView.setBackgroundColor(activity.getResources().getColor(R.color.background_grey));
            mReturn.setImageResource(R.drawable.index_return_arrow);
            if(title!=null)
            title.setTextColor(activity.getResources().getColor(R.color.white));
            mOther.setTextColor(activity.getResources().getColor(R.color.white));
        }else if("baby".equals(type)){
            //宝宝端
            MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(activity,R.color.baby_topColor);
            mLayout.setBackgroundResource(R.drawable.head_bgimg);
            mView.setBackgroundColor(activity.getResources().getColor(R.color.background_alight));
            if(title!=null)
                title.setTextColor(activity.getResources().getColor(R.color.white));
            mOther.setTextColor(activity.getResources().getColor(R.color.white));//red_grey
         //   mLayout.setBackgroundResource(R.drawable.head_bgimg);
          //  mView.setBackgroundColor(activity.getResources().getColor(R.color.background_alight));
        }
    }
    public void isMomORBaby(Activity activity, RelativeLayout mLayout,View mView,ImageView mReturn,TextView title){
        type=mSharedPreferences.Get_PREFS(activity,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        if("mom".equals(type)) {
            //妈妈端
            MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(activity,R.color.app_topColor);
            mLayout.setBackgroundColor(activity.getResources().getColor(R.color.app_topColor));
            mView.setBackgroundColor(activity.getResources().getColor(R.color.background_grey));
            mReturn.setImageResource(R.drawable.index_return_arrow);
            if(title!=null)
            title.setTextColor(activity.getResources().getColor(R.color.white));
        }else if("baby".equals(type)){
            //宝宝端
            MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(activity,R.color.baby_topColor);
            mLayout.setBackgroundResource(R.drawable.head_bgimg);
            mView.setBackgroundColor(activity.getResources().getColor(R.color.background_alight));
            if(title!=null)
                title.setTextColor(activity.getResources().getColor(R.color.white));
        }
    }

}
