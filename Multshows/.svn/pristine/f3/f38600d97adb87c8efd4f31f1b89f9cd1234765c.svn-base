package com.multshows.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.multshows.R;


/**
 * 描述：自定义数字键盘
 * 作者：贾强胜
 * 时间：2016.7.15
 */
public class MyNumber_Pop extends PopupWindow {
    private View mMenuView;
    Context mContext;
    TextView m0;
    TextView m1;
    TextView m2;
    TextView m3;
    TextView m4;
    TextView m5;
    TextView m6;
    TextView m7;
    TextView m8;
    TextView m9;
    LinearLayout mDelete;
    String text="";
    Num num;
    int length=3;//长度为4

    public interface Num{
        void num(String text);
    }
    public void setNum(Num num){
        this.num=num;
    }

    public MyNumber_Pop(Activity context) {
        super(context);
        mContext=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.number_show_dialog, null);
        m0= (TextView) mMenuView.findViewById(R.id.number_bottom_0);
        m1= (TextView) mMenuView.findViewById(R.id.number_bottom_1);
        m2= (TextView) mMenuView.findViewById(R.id.number_bottom_2);
        m3= (TextView) mMenuView.findViewById(R.id.number_bottom_3);
        m4= (TextView) mMenuView.findViewById(R.id.number_bottom_4);
        m5= (TextView) mMenuView.findViewById(R.id.number_bottom_5);
        m6= (TextView) mMenuView.findViewById(R.id.number_bottom_6);
        m7= (TextView) mMenuView.findViewById(R.id.number_bottom_7);
        m8= (TextView) mMenuView.findViewById(R.id.number_bottom_8);
        m9= (TextView) mMenuView.findViewById(R.id.number_bottom_9);
        mDelete= (LinearLayout) mMenuView.findViewById(R.id.number_bottom_delete);
        text="";



        //1
        m1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 1;
                    num.num(text);
                }
            }
        });
        //2
        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 2;
                    num.num(text);
                }
            }
        });
        //3
        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 3;
                    num.num(text);
                }
            }
        });
        //4
        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 4;
                    num.num(text);
                }
            }
        });
        //5
        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 5;
                    num.num(text);
                }
            }
        });
        //6
        m6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 6;
                    num.num(text);
                }
            }
        });
        //7
        m7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 7;
                    num.num(text);
                }
            }
        });
        //8
        m8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 8;
                    num.num(text);
                }
            }
        });
        //9
        m9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 9;
                    num.num(text);
                }
            }
        });
        //0
        m0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.length()<=length) {
                    text = text + 0;
                    num.num(text);
                }
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(text)) {
                    text = text.substring(0, text.length() - 1);
                    num.num(text);
                }
            }
        });


        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.number_bottom_Parents).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
    public void setNumText(){
        text="";
    }

}
