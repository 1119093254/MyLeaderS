package com.multshows.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.multshows.Activity.Login_FindPassword_Activity;
import com.multshows.R;
import com.multshows.Utils.HideInputManager_Utils;


/**
 * 描述：切换密码输入框
 * 作者：李萧萧
 * 时间：2016.12/9
 */
public class ChangeUser_Pop extends PopupWindow {
    private View mMenuView;
    Activity mContext;
    ImageView mReturn;//取消
    EditText mPassWord;//密码输入框
    TextView mForget;//忘记密码
    TextView mOK;//确定

    LinearLayout mDelete;
    String text = "";
    Num num;



    int length = 19;//长度为20
    Dialog dialog;

    public interface Num {
        void num(String text);
    }

    public void setNum(Num num) {
        this.num = num;
    }

    public ChangeUser_Pop(Activity context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.change_user_show_dialog, null);
        ChangeUser_Pop.this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mReturn = (ImageView) mMenuView.findViewById(R.id.Rechange_PassWord_Return);
        mPassWord = (EditText) mMenuView.findViewById(R.id.Rechange_PassWord_EditText);
        mForget = (TextView) mMenuView.findViewById(R.id.Rechange_PassWord_Forget);
        mOK = (TextView) mMenuView.findViewById(R.id.Rechange_PassWord_OK);
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
        mDelete = (LinearLayout) mMenuView.findViewById(R.id.number_bottom_delete);
        text = "";

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text = "";
                Log.e("关闭", "123");
                mPassWord.setText(text);
                //隐藏软键盘
                dismiss();
                HideInputManager_Utils.hideInputManager(mContext);

            }
        });
        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = mPassWord.getText().toString();
                if (text.length() != 4) {
                    dialog = new HintText_Dialog(mContext, R.style.MyDialog);
                    dialog.show();
                    HintText_Dialog mTextDialog = new HintText_Dialog(mContext,
                            "密码为4位数", "fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 2000);
                } else {
                    num.num(text);
                }
            }
        });
        //忘记密码
        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Login_FindPassword_Activity.class);
                intent.putExtra("PassWordType", 1);
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

    public void setNumText() {
        text = "";
        mPassWord.setText(text);
    }

}
