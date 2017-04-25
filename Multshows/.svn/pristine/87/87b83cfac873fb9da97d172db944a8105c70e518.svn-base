package com.multshows.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Beans.InterMessageData;
import com.multshows.Fragment.Friends_Interaction_Fragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 互动消息 界面
 */
public class Friends_Interaction_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;
    RelativeLayout mReturnLayout;//顶部布局
    RelativeLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    RadioGroup mGroup;
    RadioButton mComment;//评论
    RadioButton mZan;//点赞
    RadioButton mReWord;//打赏
    RadioButton mFlower;//鲜花
    //RadioButton mHead;//掌声

    TextView mPLMessage;//评论小点点
    TextView mDZMessage;//点赞小点点
    TextView mDSMessage;//打赏小点点
    TextView mXHMessage;//鲜花小点点

    ViewPager mViewPager;

    //Fragment
    Friends_Interaction_Fragment mFragment;
    //适配器
    HomeFrament_Adapter mAdapter;
    //数据集合
    List<Fragment> mList=new ArrayList<>();
    //工具
    FragmentManager mFragmentManager;
    Bundle mBundle;
    MyApplication mMyApplication;
    Gson mGson=new Gson();
    List<InterMessageData> mMessageList=new ArrayList();
    int messageType=1;//消息标志
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_interaction);
        MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Friends_Interaction_Activity.this,R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.friends_Interaction_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.friends_Interaction_returnLayout);
        mParentLayout= (RelativeLayout) findViewById(R.id.friends_Interaction_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.friends_Interaction_TopTitle);
        mGroup= (RadioGroup) findViewById(R.id.friends_Interaction_Group);
        mViewPager= (ViewPager) findViewById(R.id.friends_Interaction_ViewPager);
        mComment= (RadioButton) findViewById(R.id.friends_Interaction_RadioBtn_comm);
        mZan= (RadioButton) findViewById(R.id.friends_Interaction_RadioBtn_Zan);
        mReWord= (RadioButton) findViewById(R.id.friends_Interaction_RadioBtn_ReWord);
        mFlower= (RadioButton) findViewById(R.id.friends_Interaction_RadioBtn_Flower);
        //mHead= (RadioButton) findViewById(R.id.friends_Interaction_RadioBtn_head);
        mPLMessage= (TextView) findViewById(R.id.friends_news_PL_NoReader);
        mDZMessage= (TextView) findViewById(R.id.friends_news_DZ_NoReader);
        mDSMessage= (TextView) findViewById(R.id.friends_news_DS_NoReader);
        mXHMessage= (TextView) findViewById(R.id.friends_news_XH_NoReader);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Friends_Interaction_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        getMessage();
        Intent intent=getIntent();
        //互动消息类型：1评论 2点赞3打赏4鲜花
        messageType=intent.getIntExtra("messageType",1);

        for (int i=0;i<4;i++) {
            mFragment = new Friends_Interaction_Fragment();
            mBundle = new Bundle();
            mBundle.putInt("position", i);
            mFragment.setArguments(mBundle);
            mList.add(mFragment);
        }
        //初始化适配器
        mFragmentManager = getSupportFragmentManager();
        mAdapter = new HomeFrament_Adapter(mFragmentManager, mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(messageType-1,false);
        mViewPager.setOffscreenPageLimit(3);
        if(messageType==1){
            mComment.setChecked(true);
        }else if(messageType==2){
            mZan.setChecked(true);
        }else if(messageType==3){
            mReWord.setChecked(true);
        }else if(messageType==4){
            mFlower.setChecked(true);
        }
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //RadioGroup事件监听，根据选中的不同进行viewpager界面切换
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //viewPager监听事件
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动中
            }

            @Override
            public void onPageSelected(int position) {
                //滑动结束时
                switch (position) {
                    case 0:
                        mComment.setChecked(true);
                        break;
                    case 1:
                        mZan.setChecked(true);
                        break;
                    case 2:
                        mReWord.setChecked(true);
                        break;
                    case 3:
                        mFlower.setChecked(true);
                        break;
                    /*case 4:
                        mHead.setChecked(true);
                        break;*/
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态改变时（调用三次）
            }
        });
    }
    /**
     * 功能：ViewPager监听事件
     * 参数：无
     * 返回：无
     */
    private void resetViewPager(int checkId) {
        switch (checkId) {
            case R.id.friends_Interaction_RadioBtn_comm:
                mPLMessage.setVisibility(View.INVISIBLE);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.friends_Interaction_RadioBtn_Zan:
                mDZMessage.setVisibility(View.INVISIBLE);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.friends_Interaction_RadioBtn_ReWord:
                mDSMessage.setVisibility(View.INVISIBLE);
                CLog.e("==========","222223311111");
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.friends_Interaction_RadioBtn_Flower:
                mXHMessage.setVisibility(View.INVISIBLE);
                mViewPager.setCurrentItem(3, false);
                break;
           /* case R.id.friends_Interaction_RadioBtn_head:
                mViewPager.setCurrentItem(4, false);
                break;*/
        }
    }

    /**
     * 获取用户互动消息列表未读消息
     */
    private void getMessage(){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/ListUserInterMessage")
                .addParams("userId",Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取用户互动消息列表未读消息失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取用户互动消息列表未读消息"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    InterMessageData bean=mGson.fromJson(jsonArray.getString(i),InterMessageData.class);
                                    if(bean.getNumber()!=0){
                                        if(bean.getType()==1){
                                            mPLMessage.setVisibility(View.VISIBLE);
                                        }else if(bean.getType()==2){
                                            mDZMessage.setVisibility(View.VISIBLE);
                                        }else if(bean.getType()==3){
                                            mDSMessage.setVisibility(View.VISIBLE);
                                        }else if(bean.getType()==4){
                                            mXHMessage.setVisibility(View.VISIBLE);
                                        }
                                    }else {
                                        if(bean.getType()==1){
                                            mPLMessage.setVisibility(View.INVISIBLE);
                                        }else if(bean.getType()==2){
                                            mDZMessage.setVisibility(View.INVISIBLE);
                                        }else if(bean.getType()==3){
                                            mDSMessage.setVisibility(View.INVISIBLE);
                                        }else if(bean.getType()==4){
                                            mXHMessage.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }
                                CLog.e("=============","messageType="+messageType);
                                if(messageType==1){
                                    mPLMessage.setVisibility(View.INVISIBLE);
                                }else if(messageType==2){
                                    mDZMessage.setVisibility(View.INVISIBLE);
                                }else if(messageType==3){
                                    mDSMessage.setVisibility(View.INVISIBLE);
                                }else if(messageType==4){
                                    mXHMessage.setVisibility(View.INVISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}
