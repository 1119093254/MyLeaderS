package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Beans.Setting_LoginUser_Beans;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserTerm;
import com.multshows.Fragment.Setting_Change_Fragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.HintText_Dialog;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 设置——切换账号
 */
public class Setting_Change_Activity extends BaseActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    RelativeLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    TextView mForgetPassword;
    ViewPager mViewPager;
    RadioGroup mGroup;
    //适配器
    HomeFrament_Adapter mAdapter;
    //数据集合
    List<Fragment> mFramentList = new ArrayList<>();
    //工具
    FragmentManager mFragmentManager;
    Bundle mBundle;
    //Fragment
    String type;//登录类型
    Setting_Change_Fragment mChangeFragment;
    //数据
    List<Setting_LoginUser_Beans> mList=new ArrayList<>();
    SaveSharedPreferences mSave=new SaveSharedPreferences();
    Gson mGson=new Gson();
    Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_change);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Setting_Change_Activity.this, R.color.app_topColor);
        initView();
        initData();
        initListen();
    }

    @Override
    public void findViews() {

    }

    @Override
    public void addListeners() {

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Setting_Change_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Setting_Change_topLayout);
        mParentLayout= (RelativeLayout) findViewById(R.id.Setting_Change_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Setting_Change_TopTitle);
        mForgetPassword = (TextView) findViewById(R.id.ForgetPassword);
        mGroup = (RadioGroup) findViewById(R.id.Setting_Change_Group);
        mViewPager = (ViewPager) findViewById(R.id.Setting_Change_viewPager);
    }

    /**
     * 数据处理
     */
    private void initData() {
         type=mSave.Get_PREFS(Setting_Change_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        //动态添加
        dataDeal();
        //初始化适配器
        mFragmentManager =getSupportFragmentManager();
        mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
        mViewPager.setAdapter(mAdapter);
        if("mom".equals(type)) {
            //妈妈端
            getBabyList(Login_Static.myUserID);
        }else if("baby".equals(type)){
            //宝宝端(首先要通过家长id获取宝宝列表)
            if(Login_Static.mAccount!=null)
            getBabyList(Login_Static.mAccount.getParentId());

        }else if("unLogin".equals(type)){
            Setting_LoginUser_Beans beans_ = new Setting_LoginUser_Beans(mSave.Get_PREFS(Setting_Change_Activity.this, "momUserId"),
                    mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPic")
                    , mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPhone"),
                    false,"mom");
            mList.add(beans_);
            dataDeal();
            mAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 动态添加
     *  根据宝宝列表创建界面布局
     */
    private void dataDeal(){
        //动态添加RadioButton
        for (int i = 0; i < mList.size(); i++) {
            RadioButton tempButton = new RadioButton(this);
            if (i == 0) {
                tempButton.setChecked(true);
            }
            tempButton.setButtonDrawable(null);           // 设置按钮的样式
            tempButton.setPadding(5, 0, 5, 0);                 // 设置文字距离按钮四周的距离
            tempButton.setButtonDrawable(R.drawable.radio_button_check);
            tempButton.setId(i);
            mGroup.addView(tempButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        //动态添加ViewPager
        for (int i = 0; i <mList.size(); i++) {
            mChangeFragment=new Setting_Change_Fragment();
            mBundle=new Bundle();
            mBundle.putSerializable("Login_Beans",mList.get(i));
            mChangeFragment.setArguments(mBundle);
            mFramentList.add(mChangeFragment);
        }
    }
    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSave.Get_PREFS(getApplicationContext(),"Type").equals("unLogin")){
                    mSave.Save_PREFS(getApplicationContext(),"Type","baby");
                }
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
                RadioButton tempButton = (RadioButton)findViewById(position);
                tempButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态改变时（调用三次）
            }
        });
        //忘记切换密码
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting_Change_Activity.this, Login_FindPassword_Activity.class);
                intent.putExtra("PassWordType",1);
                startActivity(intent);
            }
        });
    }
    /**
     * 功能：ViewPager监听事件
     * 参数：无
     * 返回：无
     */
    private void resetViewPager(int checkId) {
                mViewPager.setCurrentItem(checkId,true);
        }

    /**
     * 通过userid获取宝宝列表
     * @param userId_
     */
    private void getBabyList(String userId_){
        UserTerm userTerm=new UserTerm();
        userTerm.setUserId(userId_);
        userTerm.setPageIndex(0);
        userTerm.setPageSize(0);
        String data=mGson.toJson(userTerm);
        CLog.e("testing","获取宝贝列表data："+data);
        OKHttp.OkHttpPost("/User/GetUserList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","获取宝贝列表失败："+e.toString());
                //固定数据，妈妈
                if("mom".equals(mSave.Get_PREFS(Setting_Change_Activity.this,"Type"))) {
                    Setting_LoginUser_Beans beans = new Setting_LoginUser_Beans(mSave.Get_PREFS(Setting_Change_Activity.this, "momUserId"),
                            mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPic")
                            , mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPhone"),
                            true,"mom");
                    mList.add(beans);
                }else {
                    Setting_LoginUser_Beans beans = new Setting_LoginUser_Beans(mSave.Get_PREFS(Setting_Change_Activity.this, "momUserId"),
                            mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPic")
                            , mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPhone"),
                            false,"mom");
                    mList.add(beans);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取宝贝列表："+response);
                mSave.Save_PREFS(Setting_Change_Activity.this,"babyList",response);
                //固定数据，妈妈
                if("mom".equals(mSave.Get_PREFS(Setting_Change_Activity.this,"Type"))) {
                    Setting_LoginUser_Beans beans = new Setting_LoginUser_Beans(mSave.Get_PREFS(Setting_Change_Activity.this, "momUserId"),
                            mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPic")
                            , mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPhone"),
                            true,"mom");
                    mList.add(beans);
                }else {
                    Setting_LoginUser_Beans beans = new Setting_LoginUser_Beans(mSave.Get_PREFS(Setting_Change_Activity.this, "momUserId"),
                            mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPic")
                            , mSave.Get_PREFS(Setting_Change_Activity.this, "momNickName"), mSave.Get_PREFS(Setting_Change_Activity.this, "momPhone"),
                            false,"mom");
                    mList.add(beans);
                }
                try {
                    if(Json_Utils.getCode(response)==0) {
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        if(jsonArray.length()!=0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject array = new JSONObject(jsonArray.getString(i));
                                //CLog.e("baby",array.toString());
                                UserBase baby = mGson.fromJson(array.toString(), UserBase.class);
                                boolean flag;
                                //if(baby.getState()==1){
                                //启用（判断该宝宝是不是当前正在登录的用户：是true 否false）
                                flag = Login_Static.myUserID.equals(baby.getUserId());
                                Setting_LoginUser_Beans beans = new Setting_LoginUser_Beans(baby.getUserId(),
                                        baby.getNickName(), baby.getPortrait(), baby.getNickName(), baby.getMobile(),
                                        flag, "baby");
                                mList.add(beans);
                            }
                            dataDeal();
                            mAdapter.notifyDataSetChanged();
                        }else {
                            //如果没有宝宝提示添加宝宝
                            mDialog=new HintText_Dialog(Setting_Change_Activity.this,R.style.MyDialog);
                            mDialog.show();
                            HintText_Dialog mText_dialog=new HintText_Dialog(Setting_Change_Activity.this,
                                    "请添加宝宝","fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //得到返回值后，2秒后加载框消失
                                    mDialog.dismiss();
                                }
                            }, Login_Static.hintTime);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    /**
     * 监听Back键按下事件
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(mSave.Get_PREFS(getApplicationContext(),"Type").equals("unLogin")){
                mSave.Save_PREFS(getApplicationContext(),"Type","baby");
            }

            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
