package com.multshows.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.Fragment.My_BabyChangeName_Fragment;
import com.multshows.Fragment.My_BabySexChange_Fragment;
import com.multshows.Fragment.My_Information_SexChange_Fragment;
import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;

/**
 * 我的宝贝信息修改界面
 */
public class My_BabyInformationChange_Activity extends AppCompatActivity implements My_BabyChangeName_Fragment.SendBabyMessage,
My_BabySexChange_Fragment.SendBabyMessage{
    //控件
    //fragment
    My_BabyChangeName_Fragment mChangeNameFragment;//更换昵称
    My_BabySexChange_Fragment mSexChangeFragment;//更换性别
    //工具
    FragmentManager mFragmentManager;
    //进入方式
    String type;
    Intent intent;
    int sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_baby_information_change);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_BabyInformationChange_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mFragmentManager=getSupportFragmentManager();
    }

    /**
     * 数据处理
     */
    private void initData() {
        intent=getIntent();
        Bundle bundle=new Bundle();
        type=intent.getStringExtra("changeType");
        if("name".equals(type)){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            //更换昵称
            String name=intent.getStringExtra("name");
            mChangeNameFragment=new My_BabyChangeName_Fragment();
            bundle.putString("nickName",name);
            mChangeNameFragment.setArguments(bundle);
            mFragmentManager.beginTransaction().replace(R.id.My_BabyChange_FrameLayout,mChangeNameFragment).commit();
        }else if("sex".equals(type)){
            sex=intent.getIntExtra("sex",0);
            mSexChangeFragment=new My_BabySexChange_Fragment();
            bundle.putInt("sex",sex);
            mSexChangeFragment.setArguments(bundle);
            mFragmentManager.beginTransaction().replace(R.id.My_BabyChange_FrameLayout,mSexChangeFragment).commit();
        }
    }

    /**
     * 事件监听
     */
    private void initListen() {


    }
    //昵称修改返回值
    @Override
    public void sendBabyMessageName(String name) {
        intent.putExtra("name",name);
        setResult(3,intent);
    }
    //性别修改返回值
    @Override
    public void sendBabyMessageSex(int sex) {
        intent.putExtra("sex",sex);
        setResult(2,intent);
    }
}
