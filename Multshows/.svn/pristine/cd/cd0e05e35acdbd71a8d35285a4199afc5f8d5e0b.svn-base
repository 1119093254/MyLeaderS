package com.multshows.Activity;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Adapter.ActivityDetails_ImageText_Adapter;
import com.multshows.Adapter.ActivityDetails_User_Adapter;
import com.multshows.Beans.Activity;
import com.multshows.Beans.ActivityDetailsUser_Beans;
import com.multshows.Beans.ActivityDetails_Beans;
import com.multshows.Beans.UserAuthentication;
import com.multshows.Beans.UserInfoTag;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Verification_Utils;
import com.multshows.Views.Dialog_Editext;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 描述：活动详情界面
 * 作者：李萧萧
 * 时间：2016.10/8
 */
public class Activity_Details_Activity extends BaseActivity {
    @Bind(R.id.Activity_return)
    ImageView mActivityReturn;//返回
    @Bind(R.id.Activity_edit)
    TextView mActivityEdit;//编辑文字（已隐藏）
    @Bind(R.id.ActivityDetails_image)
    SimpleDraweeView mActivityDetailsImage;//发布人的头像
    @Bind(R.id.ActivityDetails_username)
    TextView mActivityDetailsUsername;//发布人的昵称
    @Bind(R.id.TitleText)
    TextView mTitleText;//活动的标题
    @Bind(R.id.TitleDetail)
    TextView mTitleDetail;//活动的详情
    @Bind(R.id.OverTimeText)
    TextView mOverTimeText;//报名截止时间
    @Bind(R.id.TimeText)
    TextView mTimeText;//活动进行时间
    @Bind(R.id.looknum)
    TextView looknum;//看过该活动的人的数量
    @Bind(R.id.AddressText)
    TextView mAddressText;//活动地址显示
    @Bind(R.id.Activity_details_listview)
    MyListView mActivityDetailsListview;//活动图片显示ListView
    @Bind(R.id.personNum)
    TextView mPersonNum;//报名人数
    @Bind(R.id.Activity_persondetails_listview)
    MyListView mActivityPersondetailsListview;//报名人列表ListView
    @Bind(R.id.Mysignup)
    Button mMysignup;//报名活动/取消报名/取消活动的按钮
    @Bind(R.id.bottomLayout)
    RelativeLayout bottomLayout;//报名活动按钮所在的布局，用来设置背景
    @Bind(R.id.BabyisLook)
    ImageView mBabyisLook;//宝宝是否可见按钮（已隐藏）
    @Bind(R.id.activityLayout)
    LinearLayout mActivityLayout;//活动布局
    @Bind(R.id.BabyisLookLayout)
    RelativeLayout mBabyisLookLayout;//活动布局
    int isBabyLook = 0;//是否可见 0不可见，1可见
    List<ActivityDetails_Beans> mList;//图文详情bean集合
    List<ActivityDetailsUser_Beans> mListuser;//已报名用户列表bean
    @Bind(R.id.ActivityDetailsLayout)
    RelativeLayout mActivityDetailsLayout;//顶部导航布局， 用于设置背景
    String activityId;//活动id
    Gson mGson = new Gson();
    Dialog_Hint hintText_dialog;//报名提示框
    Dialog dialog;//消息提示框
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    MyApplication mApplication;
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    Activity activity;//活动信息
    String types="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitydetails);
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Activity_Details_Activity.this, R.color.green);//设置沉浸式颜色
        ButterKnife.bind(this);//绑定id
        mApplication = (MyApplication) this.getApplicationContext();
        types=mSave.Get_PREFS(Activity_Details_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        if("mom".equals(types)) {
            mMysignup.setEnabled(true);//妈妈端 报名活动设置可点击
        }else {
            mMysignup.setEnabled(false);//宝宝端 报名活动设置不可点击
            mBabyisLookLayout.setVisibility(View.GONE);//宝宝端 隐藏报名按钮
        }
        dialog = new HintText_Dialog(Activity_Details_Activity.this, R.style.MyDialog);//初始化提示框
        Intent intent = getIntent();
        activityId = intent.getStringExtra("activityId");//接收上一页传过来的活动id ，用此id请求活动详情
        mList = new ArrayList<>();//初始化 list集合
        //初始化数据
        inData();
        //监听

    }
    /**
     * 功能：实现BaseActivity 绑定id的抽象方法
     *
     * 无参
     *
     * 返回值：空
     */
    @Override
    public void findViews() {

    }
    /**
     * 实现BaseActivity 监听事件的抽象方法
     *
     * 无参
     *
     * 返回值：空
     */
    @Override
    public void addListeners() {

    }


    /**
     * 功能： 设置宝宝对互动的可见性
     *
     * 无参
     *
     * 返回值：空
     */
    public void ChangeActivityVisible() {
        CLog.e("ChangeActivityVisible", activityId + "  " + Login_Static.mAccount.getAccountId() + "  " + isBabyLook);
        OkHttpUtils.get().url(mApplication.getUrl() + "/Activity/ChangeActivityVisible")//接口地址
                .addParams("activityId", activityId)// 所传参数 活动id
                .addParams("accountId", Login_Static.mAccount.getAccountId())//所传参数 账户id
                .addParams("isShow", isBabyLook + "")//设置可见性参数 0不可见，1可见
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //访问错误回调（断网 或者服务器异常）
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //成功返回
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {//code=0 代表成功返回 设置成功
                                dialog.show();
                                showError("设置成功", 1);

                            } else {
                                dialog.show();//code=0 代表失败返回 设置失败
                                showError("设置失败", 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    /**
     * 功能： 初始化数据
     *
     * 无参
     *
     * 返回值：空
     */
    private void inData() {
        CLog.e("activityId", activityId);
        CLog.e("currentUserId", Login_Static.myUserID);
        //调用请求活动详情接口
        OkHttpUtils.get().url(mApplication.getUrl() + "/Activity/GetActivityInfo")//请求活动详情接口
                .addParams("activityId", activityId)//传参 活动id
                .addParams("currentUserId", Login_Static.myUserID)//传参 当前用户id
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //访问错误回调（断网 或者服务器异常）
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //访问接口返回JSONO字符串
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {//成功活动到活动详情数据
                                JSONObject jsonArray = new JSONObject(Json_Utils.getTemplate(response));
                                CLog.e("baby", jsonArray.length() + "");
                                //json解析，Activity类型接收
                                activity = mGson.fromJson(jsonArray.toString(), Activity.class);//解析json字符串
                                Uri mUri = Uri.parse(SubString_Utils.getImageUrl(activity.getSponsor().getPortrait()));//设置头像完整的Uri
                                mActivityDetailsImage.setImageURI(mUri);//活动发起者头像显示
                                mActivityDetailsUsername.setText(activity.getSponsor().getNickName());//活动发起者昵称
                                mTitleText.setText(Emoji_Map.Emojichange(Activity_Details_Activity.this, mEmoji_change.ChangeEmoji(activity.getTitle())));//活动标题显示，含有表情字符格式的转化成表情图片
                                mTitleDetail.setText(Emoji_Map.Emojichange(Activity_Details_Activity.this, mEmoji_change.ChangeEmoji(activity.getInfos())));//活动详情显示，含有表情字符格式的转化成表情图片
                                mTimeText.setText(activity.getBeginTimeStr().substring(0, 16) + "至" + activity.getEndTimeStr().substring(0, 16));//活动举办时间
                                mOverTimeText.setText(activity.getSignDeadlineStr().substring(0, 16)+" 截止报名");//截止报名时间
                                mAddressText.setText(Emoji_Map.Emojichange(Activity_Details_Activity.this, mEmoji_change.ChangeEmoji(activity.getLocation())));//活动地址显示，含有表情字符格式的转化成表情图片
                                mPersonNum.setText("已报名：" + activity.getParticipantCount());//已报名人数显示
                                if (activity.isIsShow()) {//判断当前宝宝可见性 true表示宝宝可见，false表示宝宝不可见
                                    mBabyisLook.setImageResource(R.drawable.switch_on);//设置可见时的图片显示
                                    isBabyLook = 1;//是否可见 0不可见，1可见
                                } else {
                                    mBabyisLook.setImageResource(R.drawable.switch_off);//设置不可见时的图片显示
                                    isBabyLook = 0;//是否可见 0不可见，1可见
                                }
                                looknum.setText(activity.getViews() + "");//浏览量显示
                                List<String> imageStrinig3 = new ArrayList<String>();//存储图片名称
                                //分割图片字符串，获得图片名称
                                if (activity.getImgs() != null) {
                                    String[] imag = activity.getImgs().split(",");//分割图片字符串
                                    CLog.e("", "" + imag.length);
                                    for (int m = 0; m < imag.length; m++) {
                                        imageStrinig3.add(SubString_Utils.getImageUrl(imag[m]));//循环增加图片名称
                                    }
                                }
                                for (int j = 0; j < imageStrinig3.size(); j++) {//根据图片个数，循环增加ActivityDetails_Beans，最初布局设计使用，后来需求修改，标题，详情只有position为0 的时候才显示
                                    ActivityDetails_Beans bean2 = new ActivityDetails_Beans(imageStrinig3.get(j), activity.getInfos());
                                    mList.add(bean2);
                                }
                                //查看报名列表中是否存在本人，存在的话按钮显示“取消报名”
                                for (int i = 0; i < activity.getParticipantCount(); i++) {
                                    UserInfoTag mUser = activity.getParticipant().get(i).getUserInfo();
                                    if (mUser.getUserId().equals(Login_Static.myUserID)) {
                                        mMysignup.setText("取消报名");
                                    }
                                    //循环增加已报名人信息，用来显示报名人列表
                                    ActivityDetailsUser_Beans mBean = new ActivityDetailsUser_Beans(activity.getSponsor().getUserId(), SubString_Utils.getImageUrl(mUser.getPortrait()), mUser.getNickName(), null, activity.getParticipant().get(i).getSignMobile());
                                    mListuser.add(mBean);
                                }
                                //活动详情图文详情适配器
                                ActivityDetails_ImageText_Adapter mAdapter = new ActivityDetails_ImageText_Adapter(Activity_Details_Activity.this, mList);
                                mActivityDetailsListview.setAdapter(mAdapter);
                                //活动报名列表适配器
                                ActivityDetails_User_Adapter mUserAdapter = new ActivityDetails_User_Adapter(Activity_Details_Activity.this, mListuser);
                                mActivityPersondetailsListview.setAdapter(mUserAdapter);
                                //查看发布活动是否存在本人，存在的话按钮显示“取消活动”
                                if (Login_Static.myUserID.equals(activity.getSponsor().getUserId())) {
                                    mMysignup.setText("取消活动");
                                    mMysignup.setVisibility(View.VISIBLE);
                                } else {
                                    bottomLayout.setVisibility(View.VISIBLE);
                                    mMysignup.setVisibility(View.VISIBLE);
                                }
                                mActivityLayout.setVisibility(View.VISIBLE);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        CLog.e("activityId", "" + activityId);
        if (mSave.Get_PREFS(Activity_Details_Activity.this, "Type").equals("mom")) {
            //家长端设置背景为白色
            mActivityDetailsLayout.setBackgroundResource(R.color.white);
            bottomLayout.setBackgroundResource(R.color.white);
        }
        mListuser = new ArrayList<>();
    }

    @OnClick({R.id.Activity_return, R.id.Activity_edit, R.id.ActivityDetails_image, R.id.Mysignup, R.id.BabyisLook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Activity_return:
                //返回按钮
                finish();
                break;
            case R.id.Activity_edit:
                //编辑按钮（功能已取消，已隐藏）
                if (activity != null) {
                    //跳转到活动编辑界面
                    Intent mIntent = new Intent(Activity_Details_Activity.this, Release_Activity_Activity.class);
                    mIntent.putExtra("activity", activity);
                    startActivity(mIntent);
                }
                break;
            case R.id.ActivityDetails_image:
                break;
            case R.id.BabyisLook:
                //宝宝可见性
                if (isBabyLook == 1) {
                    //设置可见时的图片显示
                    mBabyisLook.setImageResource(R.drawable.switch_off);
                    isBabyLook = 0;
                    ChangeActivityVisible();
                } else {
                    //设置可见时的图片显示
                    isBabyLook = 1;
                    mBabyisLook.setImageResource(R.drawable.switch_on);
                    ChangeActivityVisible();
                }
                break;
            case R.id.Mysignup:
                //根据按钮的文字，判断点击按钮后的操作
                if (mMysignup.getText().equals("我要报名")) {
                    getState();//报名
                } else if (mMysignup.getText().equals("取消报名")) {
                    cancleSignUp(activityId, Login_Static.mAccount.getAccountId());//取消报名
                } else if (mMysignup.getText().equals("取消活动")) {
                    CloseActivity(activityId);//关闭活动
                }
                break;
        }
    }
    /**
     * 功能：调用取消活动接口
     *
     *
     *
     * 返回值：空
     */
    private void CloseActivity(String activityId) {
        dialog.show();
        HintText_Dialog hint = new HintText_Dialog(Activity_Details_Activity.this, "正在取消活动...", "");//取消活动中提示框
        OkHttpUtils.get().url(mApplication.getUrl() + "/Activity/CancelActivity")
                .addParams("activityId", activityId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //访问错误回调（断网 或者服务器异常）
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {//code=0 代表成功返回 关闭成功
                                showError("关闭活动成功", 2);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 功能：发布结果提示
     *
     * @param errorString      错误显示文字
     * @param is                用来区分显示对应的图片  1 对勾  2错误
     * 返回值：空
     */
    public void showError(String errorString, final int is) {
        String isString = null;
        if (is == 1) {
            isString = "success";
        }
        if (is == 2) {
            isString = "success";
            mMysignup.setText("取消报名");
        }
        if (is == 0) {
            isString = "fail";
        }
        HintText_Dialog hint = new HintText_Dialog(Activity_Details_Activity.this, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，1秒后加载框消失
                if (is == 2) {
                    finish();
                }
                dialog.dismiss();
            }
        }, 1000);
    }

    /**
     * 功能：调用取消 报名接口
     *
     * @param mActivityId      活动id
     * @param accountId         账户id
     *
     * 返回值：空
     */
    private void cancleSignUp(String mActivityId, String accountId) {
        dialog.show();
        HintText_Dialog hint = new HintText_Dialog(Activity_Details_Activity.this, "正在取消报名...", "");
        OkHttpUtils.get().url(mApplication.getUrl() + "/Activity/UndoParticipateActivity")
                .addParams("activityId", mActivityId)//传参 活动id
                .addParams("accountId", accountId) //传参 账户id
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //访问错误回调（断网 或者服务器异常）
                        CLog.e("onError", e.getMessage());
                        //错误回调
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mMysignup.setText("我要报名");
                                mList.clear();
                                inData();
                                showError("取消报名成功", 1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 功能：判断实名制状态
     *
     * 无参
     *
     * 返回值：空
     */
    private void getState() {
        OkHttpUtils.get().url(mApplication.getUrl() + "/User/GetAuthen")
                .addParams("userId", Login_Static.myUserID)//获取本人实名制情况
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //访问错误回调（断网 或者服务器异常）
                        CLog.e("testing", "获取用户实名认证失败" + e.toString());
                        dialog.show();
                        showError("获取实名认证信息失败", 0);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "获取用户实名认证" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {//成功取到数据
                                UserAuthentication bean = mGson.fromJson(Json_Utils.getTemplate(response), UserAuthentication.class);
                                if (bean.getUserId() != null && !"null".equals(bean.getUserId()) && !"".equals(bean.getUserId())) {
                                    if (bean.getIsAuthenticated() == 0) {//实名认证未通过
                                        //0未认证通过
                                        dialog.show();
                                        showError("实名认证未通过", 0);
                                    } else if (bean.getIsAuthenticated() == 1) { //1认证通过
                                        //输入手机号对话框显示
                                        final Dialog_Editext mDialog = new Dialog_Editext(Activity_Details_Activity.this, 0, "请输入手机号", "手机号不可为空", "输入手机号", new Dialog_Editext.EditTextContent() {
                                            @Override
                                            public void getEditText(String text) {
                                                //点击确定后执行此部分代码
                                                if (Verification_Utils.checkMobileNumber(text)) {//验证手机格式
                                                    dialog.show();
                                                    HintText_Dialog hint = new HintText_Dialog(Activity_Details_Activity.this, "正在报名...", "");
                                                    CLog.e("url", "" + mApplication.getUrl() + "/Activity/ParticipateActivity");
                                                    CLog.e("userId", Login_Static.myUserID);
                                                    CLog.e("activityId", activityId);
                                                    CLog.e("mobile", text);
                                                    OkHttpUtils.get().url(mApplication.getUrl() + "/Activity/ParticipateActivity")
                                                            .addParams("activityId", activityId)    //活动id
                                                            .addParams("userId", Login_Static.myUserID) //用户id
                                                            .addParams("mobile", text)      //用户报名手机号
                                                            .build()
                                                            .execute(new StringCallback() {
                                                                @Override
                                                                public void onError(Call call, Exception e, int id) {
                                                                    CLog.e("testing", e.getMessage());
                                                                    //访问错误回调（断网 或者服务器异常）
                                                                }

                                                                @Override
                                                                public void onResponse(String response, int id) {
                                                                    CLog.e("testing", response);
                                                                    JSONObject jsonObject = null;
                                                                    try {
                                                                        if (Json_Utils.getCode(response) == 0) {
                                                                            mList.clear();
                                                                            inData();
                                                                            showError("报名成功", 2);
                                                                        } else {
                                                                            //后期判断错误原因
                                                                            showError("报名失败", 0);
                                                                        }
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                } else {
                                                    dialog.show();
                                                    showError("手机号格式不正确", 0);
                                                }


                                            }
                                        });
                                        mDialog.show();
                                    } else if (bean.getIsAuthenticated() == 2) {
                                        //2认证中
                                        dialog.show();
                                        showError("实名认证审核中无法报名活动,请等待...", 0);
                                    }
                                } else {
                                    //还没实名认证，弹出提示框
                                    hintText_dialog = new Dialog_Hint(Activity_Details_Activity.this, 0,
                                            "还未进行实名认证，是否前往实名认证?", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //前去实名制界面·
                                            hintText_dialog.dismiss();
                                            Intent intent = new Intent(Activity_Details_Activity.this, My_Information_RealName_Activity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    hintText_dialog.show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
