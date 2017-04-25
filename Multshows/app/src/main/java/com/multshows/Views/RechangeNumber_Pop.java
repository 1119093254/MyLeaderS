package com.multshows.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.multshows.Activity.Login_FindPassword_Activity;
import com.multshows.Activity.MainActivity;
import com.multshows.R;
import com.multshows.Utils.HideInputManager_Utils;


/**
 * 描述：支付密码 自定义数字键盘
 * 作者：贾强胜
 * 时间：2016.7.15
 */
public class RechangeNumber_Pop extends PopupWindow {
    private View mMenuView;
    Context mContext;
    ImageView mReturn;//取消
    EditText mPassWord;//密码输入框
    TextView mForget;//忘记密码
    TextView mOK;//确定

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
    int length=19;//长度为20
    Dialog dialog;
    String money="";
    TextView mMoney;
    public interface Num{
        void num(String text);
    }
    public void setNum(Num num){
        this.num=num;
    }

    public RechangeNumber_Pop(Activity context,String money) {
        super(context);
        mContext=context;
        this.money = money;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.rechange_number_show_dialog, null);
        RechangeNumber_Pop.this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mReturn= (ImageView) mMenuView.findViewById(R.id.Rechange_PassWord_Return);
        mPassWord= (EditText) mMenuView.findViewById(R.id.Rechange_PassWord_EditText);
        mForget= (TextView) mMenuView.findViewById(R.id.Rechange_PassWord_Forget);
        mOK= (TextView) mMenuView.findViewById(R.id.Rechange_PassWord_OK);
        mMoney= (TextView) mMenuView.findViewById(R.id.Rechange_PassWord_Money);
        /*m0= (TextView) mMenuView.findViewById(R.id.number_bottom_0);
        m1= (TextView) mMenuView.findViewById(R.id.number_bottom_1);
        m2= (TextView) mMenuView.findViewById(R.id.number_bottom_2);
        m3= (TextView) mMenuView.findViewById(R.id.number_bottom_3);
        m4= (TextView) mMenuView.findViewById(R.id.number_bottom_4);
        m5= (TextView) mMenuView.findViewById(R.id.number_bottom_5);
        m6= (TextView) mMenuView.findViewById(R.id.number_bottom_6);
        m7= (TextView) mMenuView.findViewById(R.id.number_bottom_7);
        m8= (TextView) mMenuView.findViewById(R.id.number_bottom_8);
        m9= (TextView) mMenuView.findViewById(R.id.number_bottom_9);*/
        mDelete= (LinearLayout) mMenuView.findViewById(R.id.number_bottom_delete);
        text="";

        if(!"".equals(money)){
            mMoney.setVisibility(View.VISIBLE);
            mMoney.setText("¥"+money);
        }else {
            mMoney.setVisibility(View.GONE);
        }

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text="";
                mPassWord.setText(text);
                //隐藏软键盘
                HideInputManager_Utils.hideInputManager(mContext);
                MainActivity.isShow2=true;
                dismiss();

            }
        });
        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text=mPassWord.getText().toString();
                if(text.length()<6){
                    dialog=new HintText_Dialog(mContext,R.style.MyDialog);
                    dialog.show();
                    HintText_Dialog mTextDialog=new HintText_Dialog(mContext,
                            "密码至少为6位数","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            MainActivity.isShow2=true;
                        }
                    },2000);
                }else {
                    num.num(text);
                }
            }
        });
        //忘记密码
        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Login_FindPassword_Activity.class);
                intent.putExtra("PassWordType",2);
                mContext.startActivity(intent);
            }
        });


        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
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
       /* mMenuView.setOnTouchListener(new View.OnTouchListener() {
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
        });*/
    }
    public void setNumText(){
        text="";
        mPassWord.setText(text);
    }

}
