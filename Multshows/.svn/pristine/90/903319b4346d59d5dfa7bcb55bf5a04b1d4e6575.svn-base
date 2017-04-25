package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//可见人选择（暂时去掉）
public class Release_WhoSee_Activity extends AppCompatActivity {

    @Bind(R.id.WhoSee_finshActivity)
    TextView mWhoSeeFinshActivity;//完成选择
    @Bind(R.id.WhoSee_return)
    TextView mWhoSeeReturn;//返回
    @Bind(R.id.public_see)
    CheckBox mPublicSee;//多选按钮（实现选择效果），实际是单选，隐藏其它
    @Bind(R.id.publicLayout)
    RelativeLayout mPublicLayout;//所有可见区域点击
    @Bind(R.id.Private_see)
    CheckBox mPrivateSee;//多选按钮（实现选择效果），实际是单选，隐藏其它
    @Bind(R.id.PrivateLayout)
    RelativeLayout mPrivateLayout;//自己可见区域点击
    @Bind(R.id.Friends_see)
    CheckBox mFriendsSee;//多选按钮（实现选择效果），实际是单选，隐藏其它
    @Bind(R.id.FriendsLayout)
    RelativeLayout mFriendsLayout;//好友可见区域点击
    String chioceString="好友可见";
    String intentString="";//传过来的信息
    Intent mIntent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_who_see);
        ButterKnife.bind(this);
        initData();
    }
    //初始化数据
    private void initData() {
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Release_WhoSee_Activity.this, R.color.green);
        mIntent =getIntent();
        intentString=mIntent.getStringExtra("mWhoSeeText");
        if(intentString.equals("所有人可见")){
            chioceString="所有人可见";
            mPublicSee.setVisibility(View.VISIBLE);
            mFriendsSee.setVisibility(View.INVISIBLE);
            mPrivateSee.setVisibility(View.INVISIBLE);
        }
        if(intentString.equals("仅自己可见")){
            chioceString="仅自己可见";
            mPublicSee.setVisibility(View.INVISIBLE);
            mPrivateSee.setVisibility(View.VISIBLE);
            mFriendsSee.setVisibility(View.INVISIBLE);
        }
        if(intentString.equals("好友可见")){
            chioceString="好友可见";
            mPublicSee.setVisibility(View.INVISIBLE);
            mFriendsSee.setVisibility(View.VISIBLE);
            mPrivateSee.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.WhoSee_finshActivity, R.id.WhoSee_return, R.id.publicLayout, R.id.PrivateLayout, R.id.FriendsLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.WhoSee_finshActivity:
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("chioceResult",chioceString);
                setResult(4, intent);
                finish();
                break;
            case R.id.WhoSee_return:
                finish();
                break;
            case R.id.publicLayout:
                mPublicSee.setVisibility(View.VISIBLE);
                mFriendsSee.setVisibility(View.INVISIBLE);
                mPrivateSee.setVisibility(View.INVISIBLE);
                chioceString="所有人可见";
                break;
            case R.id.PrivateLayout:
                mPublicSee.setVisibility(View.INVISIBLE);
                mPrivateSee.setVisibility(View.VISIBLE);
                mFriendsSee.setVisibility(View.INVISIBLE);
                chioceString="仅自己可见";
                break;
            case R.id.FriendsLayout:
                mPublicSee.setVisibility(View.INVISIBLE);
                mFriendsSee.setVisibility(View.VISIBLE);
                mPrivateSee.setVisibility(View.INVISIBLE);
                chioceString="好友可见";
                break;
        }
    }
}
