package com.multshows.Views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 描述：自定义dialog实现添加相册的弹出框效果
 * 作者：贾强胜
 * 时间：2016.7.14
 */
public class Dialog_Interest extends Dialog{
    //控件
    EditText mEditText;//输入框
    ImageView mDelete;//清空
    TextView mCancel;//取消
    TextView mSave;//保存
    TextView mTitle;//标题
    //上下文
    Context mContext;
    EditTextContent mText;
    //传来的参数
    String Title="";//标题
    //回调函数
    public interface EditTextContent{
        void getEditText(String text);
    }


    public Dialog_Interest(Context context) {
        super(context);
        mContext=context;
    }
    public Dialog_Interest(Context context, int themeResId,String title,EditTextContent editText) {
        super(context, themeResId);
        mContext=context;
        mText=editText;
        Title=title;
    }
    public Dialog_Interest(Context context, int themeResId, EditTextContent editText) {
        super(context, themeResId);
        mContext=context;
        mText=editText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定自定义布局
        this.setContentView(R.layout.interest_dialog);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        initView();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mEditText= (EditText) findViewById(R.id.interest_dialog_editText);
        mDelete= (ImageView) findViewById(R.id.interest_dialog_delete);
        mCancel= (TextView) findViewById(R.id.interest_dialog_cancel);
        mSave= (TextView) findViewById(R.id.interest_dialog_ok);
        mTitle= (TextView) findViewById(R.id.Dialog_title);
        if(!"".equals(Title)){
            mTitle.setText(Title);
            mEditText.setHint("请输入备注信息...");
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        }

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
                String con=text.replace(" ","");
                if(text.equals("") || "".equals(con)){
                    mEditText.setHint("名称不能为空");
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
                String editable = mEditText.getText().toString();
                String str = stringFilter(editable.toString());
                if(!editable.equals(str)){
                    mEditText.setText(str);
                    //设置新的光标所在位置
                    mEditText.setSelection(str.length());
                }
                if(str.length()>0){
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
                if("".equals(Title)) {
                    mEditText.setText("");
                    mEditText.setHint("输入你喜欢的兴趣词");
                    mEditText.setHintTextColor(getContext().getResources().getColor(R.color.text_white_c));
                    mDelete.setVisibility(View.INVISIBLE);
                }else {
                    mEditText.setText("");
                    mEditText.setHint("请输入备注信息...");
                    mEditText.setHintTextColor(getContext().getResources().getColor(R.color.text_white_c));
                    mDelete.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    /**
     * 匹配正则表达式
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str)throws PatternSyntaxException {
        // 只允许字母、数字和汉字-
        //String   regEx  =  "[^a-zA-Z0-9-\u4E00-\u9FA5]";
        String   regEx  =  "[^a-zA-Z0-9\\-\u4E00-\u9FA5]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }
}
