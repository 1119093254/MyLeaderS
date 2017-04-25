package com.multshows.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Beans.UserAPI;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.ChangeBirthdayDialog;
import com.multshows.Views.MyPicPopupWindow;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;

/**
 * 我的——完善个人信息
 */
public class My_Information_Activity extends AppCompatActivity {
    public static final int CREAM_CODE = 110;//照相机返回结果
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    TextView mMyInviteCode;
    //TextView mSave;//保存
    SimpleDraweeView mHeaderPic;//头像图片
    TextView mNameText;//昵称文本
    TextView mBirthdayText;//生日文本
    TextView mSexText;//性别文本
    TextView mPhone;//账号
    RelativeLayout mHeader;//头像
    RelativeLayout mNickName;//昵称
    RelativeLayout mBirthday;//生日
    RelativeLayout mSex;//性别
    //RelativeLayout mRelationShip;//和宝宝关系
    RelativeLayout mRealName;//实名认证
    RelativeLayout mAddress;//收货地址
    RelativeLayout My_invite_Code;//我的邀请码
    MyPicPopupWindow mPopWindow;
    SaveSharedPreferences mSharedPreferences = new SaveSharedPreferences();
    String type = "";
    Uri photoUri;
    Gson mGson = new Gson();
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    getBirthday();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inforation);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_Information_Activity.this, R.color.app_topColor);
        type = mSharedPreferences.Get_PREFS(My_Information_Activity.this, "Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.My_Inforation_return);
        mReturnLayout = (RelativeLayout) findViewById(R.id.My_Inforation_returnLayout);
        mParentLayout = (LinearLayout) findViewById(R.id.My_Inforation_parentLayout);
        mTopTitle = (TextView) findViewById(R.id.My_Inforation_TopTitle);
        mMyInviteCode = (TextView) findViewById(R.id.MyInviteCode);
        //mSave= (TextView) findViewById(R.id.My_Inforation_save);
        mHeaderPic = (SimpleDraweeView) findViewById(R.id.My_Inforation_header_pic);
        mNameText = (TextView) findViewById(R.id.My_Inforation_name_text);
        mPhone = (TextView) findViewById(R.id.My_Inforation_Phone_text);
        mHeader = (RelativeLayout) findViewById(R.id.My_Inforation_header);
        mNickName = (RelativeLayout) findViewById(R.id.My_Inforation_nickName);
        mBirthday = (RelativeLayout) findViewById(R.id.My_Inforation_birthday);
        mSexText = (TextView) findViewById(R.id.My_Inforation_sex_text);
        mSex = (RelativeLayout) findViewById(R.id.My_Inforation_sex);
        //mRelationShip= (RelativeLayout) findViewById(R.id.My_Inforation_relationship);
        mRealName = (RelativeLayout) findViewById(R.id.My_Inforation_realName);
        mAddress = (RelativeLayout) findViewById(R.id.My_Inforation_address);
        My_invite_Code = (RelativeLayout) findViewById(R.id.My_invite_Code);
        mBirthdayText = (TextView) findViewById(R.id.My_Inforation_birthday_text);
        if ("mom".equals(type)) {
            //妈妈端
            mRealName.setVisibility(View.VISIBLE);
        } else if ("baby".equals(type)) {
            //宝宝端
            mRealName.setVisibility(View.GONE);
        }

        MomORBaby_Utils momORBabyUtils = new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_Information_Activity.this, mReturnLayout, mParentLayout, mReturn, mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        if (Login_Static.mAccount != null) {
            mMyInviteCode.setText(Login_Static.mAccount.getAccountId());
            if (Login_Static.mAccount.getPortrait() != null && !"null".equals(Login_Static.mAccount.getPortrait())) {
                mHeaderPic.setImageURI(Uri.parse(SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait())));
            } else {
                mHeaderPic.setImageURI(Uri.parse(""));
            }
            mPhone.setText(Login_Static.mAccount.getLoginName());
            mNameText.setText(Login_Static.mAccount.getNickName());
            mBirthdayText.setText(Time_Now.getYearTime(Login_Static.mAccount.getBirthday()));
            if (Login_Static.mAccount.getSex() == 1) {
                mSexText.setText("男");
            } else if (Login_Static.mAccount.getSex() == 2) {
                mSexText.setText("女");
            } else {
                mSexText.setText("保密");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新加载一遍个人信息数据
        if (Login_Static.mAccount != null) {
            if (Login_Static.mAccount.getPortrait() != null && !Login_Static.mAccount.getPortrait().equals("null")) {
                mHeaderPic.setImageURI(Uri.parse(SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait())));
            } else {
                mHeaderPic.setImageURI(Uri.parse(""));
            }
            mNameText.setText(Login_Static.mAccount.getNickName());
            mBirthdayText.setText(Time_Now.getYearTime(Login_Static.mAccount.getBirthday()));
            if (Login_Static.mAccount.getSex() == 1) {
                mSexText.setText("男");
            } else if (Login_Static.mAccount.getSex() == 2) {
                mSexText.setText("女");
            } else {
                mSexText.setText("保密");
            }
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
                finish();
            }
        });
        //更换头像
        mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //底部弹出框
                //实例化MyPicPopupWindow
                mPopWindow = new MyPicPopupWindow(My_Information_Activity.this, itemsOnClick,
                        "拍照", "手机相册", "", "");
                //设置弹出动画效果
                mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPopWindow.showAtLocation(findViewById(R.id.My_Inforation_parentLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        //更换昵称
        mNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Information_Activity.this, My_Information_Change_Activity.class);
                intent.putExtra("Information_type", "NickName");
                intent.putExtra("name", mNameText.getText().toString());
                startActivity(intent);
            }
        });
        //更换生日
        mBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAbortTimeDialog(mBirthdayText.getText().toString());
            }
        });
        //更换性别
        mSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Information_Activity.this, My_Information_Change_Activity.class);
                intent.putExtra("Information_type", "SexChange");
                startActivity(intent);
            }
        });
        /*//和宝宝关系
        mRelationShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(My_Information_Activity.this,My_Information_Change_Activity.class);
                intent.putExtra("Information_type","BabyShip");
                startActivity(intent);
            }
        });*/
        //实名认证
        mRealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Information_Activity.this, My_Information_RealName_Activity.class);
                startActivity(intent);
            }
        });
        //更换收货地址
        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Information_Activity.this, My_Information_ShipAddress_Activity.class);
                intent.putExtra("chioce", "no");
                startActivity(intent);
            }
        });
        //我的邀请码
        My_invite_Code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Information_Activity.this, InvitationActivity.class);
                if (Login_Static.mAccount != null) {
                    intent.putExtra("inviteCode", Login_Static.mAccount.getAccountId());
                }
                startActivity(intent);
            }
        });
    }

    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lookPhoto_dialog_Top:
                    //打开照相机
                   /* Intent intent_cream = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent_cream, CREAM_CODE);*/
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                            "yyyy_MM_dd_HH_mm_ss");
                    String filename = timeStampFormat.format(new Date());
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, filename);

                    photoUri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, CREAM_CODE);
                    break;
                case R.id.lookPhoto_dialog_Center:
                    intent = new Intent(My_Information_Activity.this, Photo_Album_Activity.class);
                    intent.putExtra("album_type", "replace_photo");
                    intent.putExtra("userId", Login_Static.myUserID);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };

    private void ShowAbortTimeDialog(String data) {
        ChangeBirthdayDialog mChangeBirthDialog = new ChangeBirthdayDialog(
                My_Information_Activity.this, 1);
        if (!"".equals(data)) {
            String[] times = data.split("-");
            mChangeBirthDialog.setDate(Integer.parseInt(times[0]), Integer.parseInt(times[1]), Integer.parseInt(times[2]));
        } else {
            mChangeBirthDialog.setDate(2000, 1, 1);
        }
        mChangeBirthDialog.show();
        mChangeBirthDialog.setClosingdateListener(new ChangeBirthdayDialog.OnClosingdateListener() {
            @Override
            public void onClick(String year, String month, String day) {
                // TODO Auto-generated method stub
                mBirthdayText.setText(year + "-" + month + "-" + day);
                mHandler.sendEmptyMessage(1);
            }
        });
    }

    private void getBirthday() {
        UserAPI userAPI = new UserAPI();
        userAPI.setBirthday(Time_Now.getIntYearTime(mBirthdayText.getText().toString()));
        userAPI.setUserId(Login_Static.myUserID);
        String data = mGson.toJson(userAPI);
        CLog.e("testing", "修改个人生日失败:" + data);
        OKHttp.OkHttpPost("/User/UpdateUserInfo", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "修改个人生日失败:" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "修改个人生日:" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        Login_Static.mAccount.setBirthday(Time_Now.getIntYearTime(mBirthdayText.getText().toString()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CREAM_CODE:
                if (resultCode == RESULT_OK) {
                    //这里开始的第二部分，获取图片的存储路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(photoUri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    if (resultCode == -1) {
                        Intent intent_look = new Intent(My_Information_Activity.this, ReplacPhoto_Look_Activity.class);
                        intent_look.putExtra("ReplacType", 0);
                        intent_look.putExtra("ReplacPhoto_Look", path);
                        intent_look.putExtra("userId", Login_Static.myUserID);
                        startActivityForResult(intent_look, 666);
                    }
                }
                break;
        }
    }
}
