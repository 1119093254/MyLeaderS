package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Beans.User;
import com.multshows.Beans.UserAuthentication;
import com.multshows.Beans.YMDTerm;
import com.multshows.Fragment.MyInformation_RealName_Fragment;
import com.multshows.Fragment.MyInformation_RealName_No_Fragment;
import com.multshows.Fragment.MyInformation_RealName_Yes_Fragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 绑定一麻袋接口
 */
public class BoundYMD_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    EditText mNumber;//账号
    EditText mName;//真实姓名
    EditText mIDCord;//身份证号
    Button mOk;//绑定按钮
    RelativeLayout mYMD_OK;//已经绑定
    LinearLayout mYMD_NO;//未绑定
    TextView mYMD;//一麻袋账户

    Gson mGson=new Gson();
    Dialog mDialog;
    Intent mIntent;
    int flags_=0;
    String type="";
    MyApplication myApplication ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_ymd);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BoundYMD_Activity.this, R.color.app_topColor);
        myApplication = (MyApplication) getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Bound_YMD_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_BabyInformation_TopLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Bound_YMD_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Bound_YMD_Title);
        mNumber= (EditText) findViewById(R.id.Bound_YMD_Number);
        mName= (EditText) findViewById(R.id.Bound_YMD_Password);
        mIDCord= (EditText) findViewById(R.id.Bound_YMD_IDCord);
        mYMD_OK= (RelativeLayout) findViewById(R.id.Bound_YMD_YMD_OK);
        mYMD_NO= (LinearLayout) findViewById(R.id.Bound_YMD_YMD_NO);
        mOk= (Button) findViewById(R.id.Bound_YMD_OK);
        mYMD= (TextView) findViewById(R.id.Bound_YMD_YMD_YMD);
        mDialog=new HintText_Dialog(BoundYMD_Activity.this,R.style.MyDialog);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(BoundYMD_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent=getIntent();
        flags_=mIntent.getIntExtra("flags",0);
        getYMD();
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideInputManager_Utils.hideInputManager(BoundYMD_Activity.this);
                finish();
            }
        });
        //绑定点击事件
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mName.getText().toString();
                String con=name.replace(" ","");
                if("no".equals(type)){
                    mDialog.show();
                    if(!"".equals(mNumber.getText().toString())&&
                            !"".equals(name) && !"".equals(con) &&
                            !"".equals(mIDCord.getText().toString())) {
                        HintText_Dialog mTextDialog=new HintText_Dialog(BoundYMD_Activity.this,"正在绑定...","");
                        boundYMD(mNumber.getText().toString(),mName.getText().toString(),mIDCord.getText().toString());
                    }else {
                        HintText_Dialog mTextDialog=new HintText_Dialog(BoundYMD_Activity.this,"请完善信息","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }
                }else if("yes".equals(type)){
                    mYMD_OK.setVisibility(View.GONE);
                    mYMD_NO.setVisibility(View.VISIBLE);
                    mOk.setText("立即绑定");
                    type="no";
                }

            }
        });
    }

    /**
     * 绑定一麻袋
     */
    private void boundYMD(String number,String name,String IDCord){

        CLog.e("testing",myApplication.getUrl()+"/Service/BindYmdAccount?"+"accountName="+number+"&userId="+Login_Static.myUserID+"&cardNo="+
                IDCord+"&realName="+name);
        OkHttpUtils.post().url(myApplication.getUrl()+"/Service/BindYmdAccount?"+"accountName="+number+"&userId="+Login_Static.myUserID+"&cardNo="+
                IDCord+"&realName="+name)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","绑定一麻袋失败"+e.toString());
                        HintText_Dialog mTextDialog=new HintText_Dialog(BoundYMD_Activity.this,"绑定失败","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","绑定一麻袋"+response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(Json_Utils.getCode(response)==0){
                                final String message=jsonObject.getString("Data");
                                HintText_Dialog mTextDialog=new HintText_Dialog(BoundYMD_Activity.this,
                                        "绑定成功","success");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                        if(flags_!=0){
                                            mIntent.putExtra("YMD",message);
                                            setResult(2,mIntent);
                                        }
                                        finish();
                                    }
                                },Login_Static.hintTime);
                            }else {
                                HintText_Dialog mTextDialog=new HintText_Dialog(BoundYMD_Activity.this,
                                        "绑定失败","fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                },Login_Static.hintTime);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 获取一麻袋信息
     */
    private void getYMD(){

        OkHttpUtils.get().url(myApplication.getUrl() + "/User/GetUser")
                .addParams("userid", Login_Static.myUserID)
                .addParams("targetuserid", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "个人信息 失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, final int id) {
                        try {
                            CLog.e("testing", "个人信息：" + Json_Utils.getTemplate(response));
                            if (Json_Utils.getCode(response) == 0) {
                                User mUser = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                if("".equals(mUser.getYmdAccount())){
                                    //未绑定一麻袋
                                    mYMD_OK.setVisibility(View.GONE);
                                    mYMD_NO.setVisibility(View.VISIBLE);
                                    mOk.setText("立即绑定");
                                    type="no";
                                }else {
                                    mYMD.setText(mUser.getYmdAccount());
                                    mYMD_OK.setVisibility(View.VISIBLE);
                                    mYMD_NO.setVisibility(View.GONE);
                                    mOk.setText("修改绑定");
                                    type="yes";
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


}
