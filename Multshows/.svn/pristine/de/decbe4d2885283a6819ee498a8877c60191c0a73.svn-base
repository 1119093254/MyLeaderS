package com.multshows.Views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.R;


/**
 * 描述：自定义dialog实现添加相册的弹出框效果
 * 作者：贾强胜
 * 时间：2016.7.14
 */
public class Dialog_Editext extends Dialog{
    //控件
    EditText mEditText;//输入框
    ImageView mDelete;//清空
    ImageView chinaNumImage;//
    TextView chinaNum;//中国区号
    TextView mCancel;//取消
    TextView mSave;//保存
    TextView Dialog_title;
    //上下文
    Context mContext;
    EditTextContent mText;
    String hitText;//提示文字
    String hitErrorText;//提示文字
    String title;//标题名称
    //回调函数
    public interface EditTextContent{
        void getEditText(String text);
    }


    public Dialog_Editext(Context context) {
        super(context);
        mContext=context;
    }

    public Dialog_Editext(Context context, int themeResId, String hitText,String hitErrorText,String title,EditTextContent editText) {
        super(context, themeResId);
        mContext=context;
        mText=editText;
        this.hitErrorText=hitErrorText;
        this.hitText=hitText;
        this.title=title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定自定义布局
        this.setContentView(R.layout.dialog_editext);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        initView();
        if(hitText.equals("请输入手机号")){
            chinaNum.setVisibility(View.VISIBLE);
            chinaNumImage.setVisibility(View.VISIBLE);
        }
        Dialog_title.setText(title);
        mEditText.setHint(hitText);
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mEditText= (EditText) findViewById(R.id.interest_dialog_editText);
        mDelete= (ImageView) findViewById(R.id.interest_dialog_delete);
        chinaNumImage= (ImageView) findViewById(R.id.chinaNumImage);
        mCancel= (TextView) findViewById(R.id.interest_dialog_cancel);
        chinaNum= (TextView) findViewById(R.id.chinaNum);
        mSave= (TextView) findViewById(R.id.interest_dialog_ok);
        Dialog_title= (TextView) findViewById(R.id.Dialog_title);
    }

    /**
     * 事件监听
     */
    private void initListen() {
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
                String text=mEditText.getText().toString();
                if(text.equals("")){
                    mEditText.setHint(hitErrorText);
                    mEditText.setHintTextColor(Color.RED);
                }else {
                    mText.getEditText(text);
                    dismiss();
                }
            }
        });
        //监听输入框
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2>0){
                    mDelete.setVisibility(View.VISIBLE);
                }else {
                    mDelete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                mEditText.setHint(hitText);
                mEditText.setHintTextColor(getContext().getResources().getColor(R.color.text_white_c));
                mDelete.setVisibility(View.INVISIBLE);
            }
        });
    }
}
