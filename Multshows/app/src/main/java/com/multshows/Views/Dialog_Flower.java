package com.multshows.Views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Activity.My_FlowerMarket_Activity;
import com.multshows.Beans.User;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.Json_Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;


/**
 * 描述：自定义dialog实现添加相册的弹出框效果
 * 作者：贾强胜
 * 时间：2016.7.14
 */
public class Dialog_Flower extends Dialog {
    //控件
    EditText mEditText;//输入框
    ImageView mCancel;//取消
    TextView mSave;//保存
    Dialog_Hint hintText_dialog;
    private FlakeView flakeView;
    RelativeLayout Flower1;
    RelativeLayout Flower10;
    RelativeLayout Flower20;
    RelativeLayout Flower5;
    RelativeLayout Flower100;
    RelativeLayout Flower200;
    RelativeLayout FlowerLayout;
    RelativeLayout FlowerLayout2;
    ImageView FlowerImage1;//鲜花图片，用来替换选中鲜花
    ImageView FlowerImage2;
    ImageView FlowerImage3;
    ImageView FlowerImage4;
    ImageView FlowerImage5;
    ImageView FlowerImage6;

    //上下文
    //回调函数
    Handler handlerRain = new Handler();
    Runnable runnableRain = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            flakeView.addFlakes(15);
            //handlerRain.postDelayed(runnableRain, 500);
            if(flakeView.getNumFlakes() > 70)
            {
                handlerRain.removeCallbacks(runnableRain);
            }
        }
    };
    Context mContext;
    EditTextContent mText;
    SimpleDraweeView mUserPic;//用户头像
    TextView mUserText;//用户昵称
    WorkAll_Beans beans;
    String texts="";
    String text;
    Gson mGson=new Gson();
    //回调函数
    public interface EditTextContent {
        void getEditText(String text);
    }


    public Dialog_Flower(Context context) {
        super(context);
        mContext = context;
    }

    public Dialog_Flower(Context context, int themeResId, WorkAll_Beans bean, EditTextContent editText) {
        super(context, themeResId);
        mContext = context;
        mText = editText;
        beans=bean;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定自定义布局
        this.setContentView(R.layout.dialog_flower);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失

        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        initView();
        initData();
        initListen();

    }

    private void initData() {
        flakeView = new FlakeView(mContext);
        FlowerLayout.addView(flakeView);
        handlerRain.postDelayed(runnableRain, 0);
        handlerRain.postDelayed(runnableRain, 0);
        handlerRain.postDelayed(runnableRain, 0);

        mUserPic.setImageURI(Uri.parse(beans.getUserPic()));
        mUserText.setText(beans.getUsername());
    }
    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= RelativeLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height= RelativeLayout.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

    }
    /**
     * 初始化
     */
    private void initView() {
        Flower1= (RelativeLayout) findViewById(R.id.Flower1);
        Flower10= (RelativeLayout) findViewById(R.id.Flower10);
        Flower20= (RelativeLayout) findViewById(R.id.Flower20);
        Flower5= (RelativeLayout) findViewById(R.id.Flower5);
        Flower100= (RelativeLayout) findViewById(R.id.Flower100);
        Flower200= (RelativeLayout) findViewById(R.id.Flower200);
        mEditText = (EditText) findViewById(R.id.TextNumFlower);
        mUserPic = (SimpleDraweeView) findViewById(R.id.flower_userPic);
        FlowerLayout = (RelativeLayout) findViewById(R.id.FlowerLayout);
        FlowerLayout2 = (RelativeLayout) findViewById(R.id.flowerLayout2);
        FlowerImage1 = (ImageView) findViewById(R.id.FlowerImage1);
        FlowerImage2 = (ImageView) findViewById(R.id.FlowerImage2);
        FlowerImage3 = (ImageView) findViewById(R.id.FlowerImage3);
        FlowerImage4 = (ImageView) findViewById(R.id.FlowerImage4);
        FlowerImage5 = (ImageView) findViewById(R.id.FlowerImage5);
        FlowerImage6 = (ImageView) findViewById(R.id.FlowerImage6);
        mCancel = (ImageView) findViewById(R.id.Flowerclose);
        mSave = (TextView) findViewById(R.id.SureSend);
        mUserText = (TextView) findViewById(R.id.flower_userName);
        mSave = (TextView) findViewById(R.id.SureSend);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //限制只能两位小数，不能大于999
        mEditText.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable edt)
            {
                String temp = edt.toString();
                if(!temp.equals(""))
                if(Integer.parseInt(temp)>999){
                    mEditText.setText("999");
                    mEditText.setSelection(mEditText.getText().length());
                }

            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });
        FlowerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        FlowerLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Flower1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("1");
                mEditText.setSelection(mEditText.getText().length());
                FlowerImage1.setImageResource(R.drawable.slelected_flower);
                FlowerImage2.setImageResource(R.drawable.money_flower_hua);
                FlowerImage3.setImageResource(R.drawable.money_flower_hua);
                FlowerImage4.setImageResource(R.drawable.money_flower_hua);
                FlowerImage5.setImageResource(R.drawable.money_flower_hua);
                FlowerImage6.setImageResource(R.drawable.money_flower_hua);

            }
        });
        Flower10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("10");
                mEditText.setSelection(mEditText.getText().length());
                FlowerImage1.setImageResource(R.drawable.money_flower_hua);
                FlowerImage2.setImageResource(R.drawable.slelected_flower);
                FlowerImage3.setImageResource(R.drawable.money_flower_hua);
                FlowerImage4.setImageResource(R.drawable.money_flower_hua);
                FlowerImage5.setImageResource(R.drawable.money_flower_hua);
                FlowerImage6.setImageResource(R.drawable.money_flower_hua);
            }
        });
        Flower20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("20");
                mEditText.setSelection(mEditText.getText().length());
                FlowerImage1.setImageResource(R.drawable.money_flower_hua);
                FlowerImage2.setImageResource(R.drawable.money_flower_hua);
                FlowerImage3.setImageResource(R.drawable.slelected_flower);
                FlowerImage4.setImageResource(R.drawable.money_flower_hua);
                FlowerImage5.setImageResource(R.drawable.money_flower_hua);
                FlowerImage6.setImageResource(R.drawable.money_flower_hua);
            }
        });
        Flower5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("5");
                mEditText.setSelection(mEditText.getText().length());
                FlowerImage1.setImageResource(R.drawable.money_flower_hua);
                FlowerImage2.setImageResource(R.drawable.money_flower_hua);
                FlowerImage3.setImageResource(R.drawable.money_flower_hua);
                FlowerImage4.setImageResource(R.drawable.slelected_flower);
                FlowerImage5.setImageResource(R.drawable.money_flower_hua);
                FlowerImage6.setImageResource(R.drawable.money_flower_hua);
            }
        });
        Flower100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("100");
                mEditText.setSelection(mEditText.getText().length());
                FlowerImage1.setImageResource(R.drawable.money_flower_hua);
                FlowerImage2.setImageResource(R.drawable.money_flower_hua);
                FlowerImage3.setImageResource(R.drawable.money_flower_hua);
                FlowerImage4.setImageResource(R.drawable.money_flower_hua);
                FlowerImage5.setImageResource(R.drawable.slelected_flower);
                FlowerImage6.setImageResource(R.drawable.money_flower_hua);
            }
        });
        Flower200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("200");
                mEditText.setSelection(mEditText.getText().length());
                FlowerImage1.setImageResource(R.drawable.money_flower_hua);
                FlowerImage2.setImageResource(R.drawable.money_flower_hua);
                FlowerImage3.setImageResource(R.drawable.money_flower_hua);
                FlowerImage4.setImageResource(R.drawable.money_flower_hua);
                FlowerImage5.setImageResource(R.drawable.money_flower_hua);
                FlowerImage6.setImageResource(R.drawable.slelected_flower);
            }
        });
        //取消
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //保存
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               text = mEditText.getText().toString();
                if (text.equals("")) {
                    mEditText.setHint("输入送花数量，最多（999朵）");
                    mEditText.setHintTextColor(Color.RED);
                } else {
                    getUser();

                }
            }
        });
    }
    /**
     * 获取个人信息数据
     */
    private void getUser() {
        MyApplication myApplication = new MyApplication();
        OkHttpUtils.get().url(myApplication.getUrl() + "/User/GetUser")
                .addParams("userid", Login_Static.myUserID)
                .addParams("targetuserid", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("testing", "个人信息 失败" + e.toString());
                        mSave.setEnabled(true);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("testing", "个人信息：" + response);
                        mSave.setEnabled(true);
                        try {
                            Log.e("testing", "个人信息：" + Json_Utils.getTemplate(response));
                            if (Json_Utils.getCode(response) == 0) {
                                User mUser = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                if(mUser.getFlowers()<Integer.parseInt(text)){
                                    //没设置
                                    hintText_dialog = new Dialog_Hint(mContext, 0,
                                            "您的鲜花不足,是否前往购买?", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            hintText_dialog.dismiss();
                                            Intent intent = new Intent(mContext, My_FlowerMarket_Activity.class);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                    hintText_dialog.show();
                                }else {
                                    mText.getEditText(text);
                                    dismiss();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
