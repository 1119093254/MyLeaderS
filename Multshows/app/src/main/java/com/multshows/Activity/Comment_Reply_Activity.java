package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Beans.ShowsCommentAdd;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.HintText_Dialog;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 评论回复  界面activity
 */
public class Comment_Reply_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    TextView mSend;//发送
    TextView replyCommentTitle;//回复评论顶部标题
    EmojiconEditText mEditText;//输入框
    RelativeLayout ReplyCommentLayout;//顶部标题
    String otherID = "";//评论人id
    String authorID = "";//作者id
    String commentID = "";//被评论id
    String showID = "";//作品id
    SaveSharedPreferences mSharedPreferences = new SaveSharedPreferences();
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    Dialog mDialog;
    Gson mGson = new Gson();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_reply);
        initView();
        initData();
        initListen();
    }

    /**
     * 功能：初始化控件
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/13 9:08
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.comment_Reply_return);
        mSend = (TextView) findViewById(R.id.comment_Reply_send);
        replyCommentTitle = (TextView) findViewById(R.id.replyCommentTitle);
        ReplyCommentLayout = (RelativeLayout) findViewById(R.id.ReplyCommentLayout);
        mEditText = (EmojiconEditText) findViewById(R.id.comment_Reply_EditText);
    }
    /**
     * 功能：数据处理
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/13 9:09
     */
    private void initData() {
        //家长端，宝宝端界面显示区分设置
        if (mSharedPreferences.Get_PREFS(Comment_Reply_Activity.this, "Type").equals("baby")) {
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            //tintManageUtils.setSystemBarTintManage(MainActivity.this, R.color.app_babyColor);
            tintManageUtils.setSystemBarTintManage_noColor(Comment_Reply_Activity.this);
            mReturn.setImageResource(R.drawable.return_arrow);//返回图标设置
            replyCommentTitle.setTextColor(getResources().getColor(R.color.red_grey));//标题颜色设置
            mSend.setTextColor(getResources().getColor(R.color.red_grey));
            ReplyCommentLayout.setBackgroundResource(R.drawable.classify_topbg);
        } else if (mSharedPreferences.Get_PREFS(Comment_Reply_Activity.this, "Type").equals("mom")) {
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(Comment_Reply_Activity.this, R.color.green);
        }
        intent = getIntent();
        otherID = intent.getStringExtra("otherID");
        authorID = intent.getStringExtra("AuthorId");
        commentID = intent.getStringExtra("CommentID");
        showID = intent.getStringExtra("WorkID");
    }

    /**
     * 功能：事件监听
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/13 9:13
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideInputManager_Utils.hideInputManager(Comment_Reply_Activity.this);
                finish();
            }
        });
        //发送
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();
                mDialog = new HintText_Dialog(Comment_Reply_Activity.this, R.style.MyDialog);
                mDialog.show();
                if ("".equals(text)) {
                    HintText_Dialog hint = new HintText_Dialog(Comment_Reply_Activity.this,
                            "请输入回复内容", "fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    }, 2000);
                } else {
                    releaseComment(mEmoji_change.EmojiChange(text));
                }
            }
        });
    }

    /**
     * 功能：  评论接口调用
     * @param text      评论文本内容
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/13 9:13
     */
    private void releaseComment(String text) {
        HintText_Dialog hint = new HintText_Dialog(Comment_Reply_Activity.this, "正在回复...", "");//正在回复提示框
        ShowsCommentAdd mShowsCommentAdd = new ShowsCommentAdd();//添加评论数据赋值
        mShowsCommentAdd.setForeignld(showID);
        mShowsCommentAdd.setUserId(Login_Static.myUserID);
        mShowsCommentAdd.setTargetUserId(otherID);
        mShowsCommentAdd.setAuthorId(authorID);
        mShowsCommentAdd.setType(1);
        mShowsCommentAdd.setReplyId(commentID);
        mShowsCommentAdd.setContent(text);
        String data = mGson.toJson(mShowsCommentAdd);//转换为json格式
        CLog.e("testing", data);
        //评论接口调用 post请求
        OKHttp.OkHttpPost("/Comment/AddComment", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //访问错误回调（断网 或者服务器异常）
                HintText_Dialog hint = new HintText_Dialog(Comment_Reply_Activity.this, "回复失败", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                }, 2000);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    CLog.e("testing", "成功：" + response);
                    if (Json_Utils.getCode(response) == 0) {
                        //成功获取到数据并解析
                        HintText_Dialog hint = new HintText_Dialog(Comment_Reply_Activity.this, "回复成功", "success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                intent.putExtra("result", 1);
                                setResult(6, intent);
                                finish();
                            }
                        }, 1000);
                    } else {
                        //评论失败，返回提示失败原因
                        HintText_Dialog hint = new HintText_Dialog(Comment_Reply_Activity.this,
                                Json_Utils.getMessage(response), "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
