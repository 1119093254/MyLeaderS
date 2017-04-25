package com.multshows.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Beans.UpdatePwdAPI;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 设置——修改密码
 */
public class Setting_ChangePassword_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    EditText mEditText;//旧密码输入框
    Button mButton;//下一步
    TextView mTextView;//找回密码
    ImageView mDelete;//删除

    Gson mGson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_change_password);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Setting_ChangePassword_Activity.this, R.color.app_topColor);
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Setting_ChangePassword_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Setting_ChangePassword_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Setting_ChangePassword_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Setting_ChangePassword_TopTitle);
        mEditText = (EditText) findViewById(R.id.Setting_ChangePassword_editText);
        mButton = (Button) findViewById(R.id.Setting_ChangePassword_button);
        mTextView = (TextView) findViewById(R.id.Setting_ChangePassword_found);
        mDelete = (ImageView) findViewById(R.id.Setting_ChangePassword_delete);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Setting_ChangePassword_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {


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
        //监听EditText内容变化
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!mEditText.getText().toString().equals("")) {
                    mButton.setBackgroundResource(R.drawable.shape_green_4);
                    mButton.setTextColor(getResources().getColor(R.color.white));
                    mButton.setEnabled(true);
                    mDelete.setVisibility(View.VISIBLE);
                } else {
                    mButton.setBackgroundResource(R.drawable.shape_grey_4);
                    mButton.setTextColor(getResources().getColor(R.color.text_white_c));
                    mButton.setEnabled(false);
                    mDelete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //找回密码
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting_ChangePassword_Activity.this, Login_FindPassword_Activity.class);
                intent.putExtra("PassWordType",3);
                startActivity(intent);
            }
        });
        //下一步
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePwdAPI updatePwdAPI=new UpdatePwdAPI();
                updatePwdAPI.setOldPwd(mEditText.getText().toString());
                if(Login_Static.mAccount!=null)
                updatePwdAPI.setAccountId(Login_Static.mAccount.getAccountId());
                updatePwdAPI.setNewPwd(mEditText.getText().toString());
                updatePwdAPI.setType(1);
                String data=mGson.toJson(updatePwdAPI);
                CLog.e("testing",data);
                OKHttp.OkHttpPost("/User/UpdatePwd","",data, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(Setting_ChangePassword_Activity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing",response+"+++"+mEditText.getText().toString());
                        try {
                            if(Json_Utils.getCode(response)==1006){
                                Toast.makeText(Setting_ChangePassword_Activity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            }else if(Json_Utils.getCode(response)==0){
                                Intent intent = new Intent(Setting_ChangePassword_Activity.this, Setting_ChangePassword_new_Activity.class);
                                intent.putExtra("oldPwd",mEditText.getText().toString());
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        //删除
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                mDelete.setVisibility(View.INVISIBLE);
            }
        });

    }
}
