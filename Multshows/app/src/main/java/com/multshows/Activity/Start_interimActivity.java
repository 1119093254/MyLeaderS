package com.multshows.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserLogin;
import com.multshows.Beans.UserPrimaryKey;
import com.multshows.Beans.UserTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.BaseAppManager;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;

/**
 * 描述：进入页
 * 作者：李萧萧
 * 时间：2016/11/11
 */
public class Start_interimActivity extends Activity {
    MyCountDownTimer cTimer;
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    Gson mGson = new Gson();
    boolean flag = false;//是否登录成功
    List<UserBase> mList = new ArrayList<>();//宝宝集合
    MyApplication mMyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hg_start_interim);
        mSave.Save_PREFS(Start_interimActivity.this, "intent", "no");
        sendCrashLog2PM("/mnt/sdcard/Multshows/Multshows.log");
        mMyApplication = (MyApplication) getApplication();
        Login_Static.myUserID="1";
        mSave.Save_PREFS(Start_interimActivity.this, Login_Static.myUserID+"token", "b72AZVb/1PCmk52Yrujl1bhs+daH9t4tClcJJtypFrpuFQFNUqXqohae5QFJFS+ZirrTTxT0xhUpNMja5cf/vg==");
        //设置充满状态栏
        //StatusBarUtils.StatusBar3(Start_interimActivity.this);
       /* if (LoginUsers.userid != -1) {
            Intent intent = new Intent(Start_interimActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {*/
        // VerificationLoginUtils.VerificationLogin(Start_interimActivity.this);
        cTimer = new MyCountDownTimer(5000, 1000);
        cTimer.start();
        Login();
        //闪屏,5秒后自动关闭
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!flag) {
                    if (mSave.Get_PREFS(Start_interimActivity.this, "first").equals("yes")) {
                        Intent intents = new Intent();
                        mSave.Save_PREFS(Start_interimActivity.this, "Type", "baby");
                        intents.setClass(Start_interimActivity.this, MainActivity.class);
                        startActivity(intents);
                        finish();
                    } else {
                        Intent intent = new Intent(Start_interimActivity.this, GuideActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {

                }
            }
        }, 4000);


    }


    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p/>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p/>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

        }
    }

    //对返回按钮监听，修改记录点击目标返回至主界面借贷界面
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
    //自动调用登录按钮
    public void Login() {

        //登录
        UserLogin userLogin = new UserLogin();
        userLogin.setAccount(mSave.Get_PREFS(Start_interimActivity.this, "userAccount"));
        userLogin.setPwd(mSave.Get_PREFS(Start_interimActivity.this, "userpassword"));
        userLogin.setType(0);
        String data = mGson.toJson(userLogin);
        CLog.e("testing", data);
        CLog.e("response", data);
        OKHttp.OkHttpPost("?type=2", "data", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("response", "" + e.getMessage());
                mSave.Save_PREFS(Start_interimActivity.this, "Unlogin", "yes");
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("response", response);
                CLog.e("testing", "==" + response);
                JSONObject jsonObject = null;
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        flag=true;
                        UserPrimaryKey beans = mGson.fromJson(Json_Utils.getTemplate(response), UserPrimaryKey.class);
                        Login_Static.myUserAccount = beans.getAccountId();
                        Login_Static.myUserID = beans.getUserId();
                        JPushInterface.setAlias(Start_interimActivity.this, "领秀S" + beans.getUserId(), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                CLog.i("JPush", "Jpush status: " + i + s);//状态
                            }
                        });//极光推送设置设备名称
                        mSave.Save_PREFS(Start_interimActivity.this, beans.getUserId()+"token", beans.getToken());
                        mSave.Save_PREFS(Start_interimActivity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                        mSave.Save_PREFS(Start_interimActivity.this, "Type", "mom");
                        mSave.Save_PREFS(Start_interimActivity.this, "Unlogin", "no");
                        if (beans.getType() == 1) {
                            mSave.Save_PREFS(Start_interimActivity.this, "Vip", "yes");
                            mSave.Save_PREFS(Start_interimActivity.this, "Type", "mom");
                            Intent mIntent = new Intent(Start_interimActivity.this, MainActivity.class);
                            BaseAppManager.getInstance().clear();
                            startActivity(mIntent);
                            finish();
                        } else {
                            mSave.Save_PREFS(Start_interimActivity.this, "Vip", "no");
                            getBabyList();
                        }
                    } else {
                        //后期判断错误原因
                        mSave.Save_PREFS(Start_interimActivity.this, "Unlogin", "yes");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //发送错误日志
    public void SendLogError(String errStr) {
        String data = "{\"Msg\":\"" + errStr + "\"}";
        CLog.e("data", data);
        OKHttp.OkHttpPost("/Log/AddErrorLog", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("error", e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    CLog.e("error", response);
                    if (Json_Utils.getCode(response) == 0) {
                        CLog.e("error", "上传错误日志成功");
                        File f = new File("/mnt/sdcard/Multshows");
                        File[] fl = f.listFiles();
                        for (int i = 0; i < fl.length; i++) {
                            if (fl[i].toString().endsWith(".log")) {
                                if (fl[i].delete()) {
                                    CLog.e("error", "删除成功");
                                } else {
                                    CLog.e("error", "删除失败");
                                }
                            }
                        }
                    } else if (Json_Utils.getCode(response) == 1) {
                        CLog.e("error", "上传错误日志失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 将捕获的导致崩溃的错误信息发送给开发人员
     * <p/>
     * 目前只将log日志保存在sdcard 和输出到LogCat中，并未发送给后台。
     */
    private void sendCrashLog2PM(String fileName) {
        if (!new File(fileName).exists()) {
            CLog.e("Start_interimActivity", "日志文件不存在");
            return;
        }
        FileInputStream fis = null;
        BufferedReader reader = null;
        String s = null;
        String errorString = "";
        try {
            fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis, "GBK"));
            while (true) {
                s = reader.readLine();
                if (s == null) break;
                //由于目前尚未确定以何种方式发送，所以先打出log日志。
                errorString = errorString + s.toString();
            }
            Log.i("errorString", errorString);
            SendLogError("android端错误日志" + errorString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {   // 关闭流
            try {
                reader.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //获取宝宝列表,判断默认宝宝 ，不存在默认宝宝进入第一个宝宝
    private void getBabyList() {
        UserTerm userTerm = new UserTerm();
        userTerm.setUserId(Login_Static.myUserID);
        userTerm.setPageIndex(1);
        userTerm.setPageSize(10);
        String data = mGson.toJson(userTerm);
        CLog.e("testing", "获取宝贝列表data：" + data);
        OKHttp.OkHttpPost("?type=4","data", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "获取宝贝列表失败：" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取宝贝列表：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {

                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject array = new JSONObject(jsonArray.getString(i));
                                //Log.e("baby",array.toString());
                                UserBase baby = mGson.fromJson(array.toString(), UserBase.class);
                                mList.add(baby);
                                if (baby.getIsDefault() == 1) {
                                    changeUser(mList.get(i).getUserId());
                                } else if (i == jsonArray.length() - 1) {
                                    changeUser(mList.get(0).getUserId());
                                }
                            }


                        }/*else {
                            mSave.Save_PREFS(getApplicationContext(),"Type","mom");
                            mSave.Save_PREFS(ChioceIdentity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                            Login_Static.flag = 0;
                            Intent mIntent = new Intent(ChioceIdentity.this, My_MyBaby_Activity.class);
                            startActivity(mIntent);
                        }*/

                    }
                    if (Json_Utils.getCode(response) == 1007) {
                        mSave.Save_PREFS(getApplicationContext(), "Type", "mom");
                        Intent mIntent = new Intent(Start_interimActivity.this, My_BabyInformation_Activity.class);
                        mIntent.putExtra("Type", 8);
                        finish();
                        startActivity(mIntent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //切换密码
    private void changeUser(String userId) {
        CLog.e("testing", "切换用户:" + userId);
        OkHttpUtils.get().url(mMyApplication.getmUrl()+"?type=6")
                .addParams("userid", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "切换用户登录失败" + e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "切换用户登录" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                final String error = Json_Utils.getTemplate(response);
                                UserPrimaryKey beans = mGson.fromJson(error, UserPrimaryKey.class);
                                Login_Static.myUserAccount = beans.getAccountId();
                                Login_Static.myUserID = beans.getUserId();
                                mSave.Save_PREFS(Start_interimActivity.this, beans.getUserId()+"token", beans.getToken());
                                mSave.Save_PREFS(Start_interimActivity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                mSave.Save_PREFS(Start_interimActivity.this, "Type", "baby");
                                mSave.Save_PREFS(Start_interimActivity.this, "Vip", "no");

                                Intent mIntent = new Intent(Start_interimActivity.this, MainActivity.class);
                                BaseAppManager.getInstance().clear();
                                startActivity(mIntent);
                                finish();
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}