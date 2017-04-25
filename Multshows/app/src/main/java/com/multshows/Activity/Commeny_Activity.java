package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Beans.Shows;
import com.multshows.Beans.ShowsCommentAdd;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.HintText_Dialog;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
/**
 *
 */

/**
 * 描述：(作品详情->)评论界面
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class Commeny_Activity extends AppCompatActivity {

    @Bind(R.id.comment_return)
    ImageView mCommentReturn;//返回图标
    @Bind(R.id.Release_commentText)
    TextView mReleaseCommentText;//发表 文字
    @Bind(R.id.CommentContentEditext)
    EditText mCommentContentEditext;//回复框
    Shows mShows;//作品列表
    Gson mGson=new Gson();
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    ShowsCommentAdd mShowsCommentAdd;//添加评论
    Dialog mDialog;//消息提示框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commeny);
        ButterKnife.bind(this);//绑定id
        initData();
    }
    /**
     * 功能：初始化数据
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/13 9:39
     */
    private void initData() {
        //家长端，宝宝端界面显示区分设置
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Commeny_Activity.this, R.color.green);
        mDialog = new HintText_Dialog(Commeny_Activity.this, R.style.MyDialog);
        mShowsCommentAdd=new ShowsCommentAdd();
        Intent intent = getIntent();
        mShows= (Shows) intent.getSerializableExtra("Shows");
        mCommentContentEditext.setHint("给"+mShows.getUser().getNickName()+"评论.....");
    }

    //返回图标，发表 点击事件
    @OnClick({R.id.comment_return, R.id.Release_commentText})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_return:
                finish();
                break;
            case R.id.Release_commentText:
                if(!mCommentContentEditext.getText().toString().equals(""))
                {
                  releaseComment();
                }else {
                    mDialog.show();
                    showError("评论内容不能为空",0);
                }
                break;
        }
    }
    //调用评论接口进行评论
    private void releaseComment() {
        mDialog.show();
        HintText_Dialog hint = new HintText_Dialog(Commeny_Activity.this, "正在评论...", "");
        mShowsCommentAdd.setForeignld(mShows.getId());
        mShowsCommentAdd.setUserId(Login_Static.myUserID);
        mShowsCommentAdd.setTargetUserId(mShows.getUser().getUserId());
        mShowsCommentAdd.setAuthorId(mShows.getUser().getUserId());
        mShowsCommentAdd.setType(1);
        mShowsCommentAdd.setContent(mEmoji_change.EmojiChange((mCommentContentEditext.getText().toString())));
        String data=mGson.toJson(mShowsCommentAdd);
        CLog.e("testing",data);
        OKHttp.OkHttpPost("/Comment/AddComment", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","发布失败："+e.toString());
                showError("评论失败", 0);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    CLog.e("testing","成功："+response);
                    if(Json_Utils.getCode(response)==0){
                        showError("评论成功", 1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //评论结果提示
    public void showError(final String errorString, final int is) {
        String isString = null;
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        HintText_Dialog hint = new HintText_Dialog(Commeny_Activity.this, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                mDialog.dismiss();
                if(is == 1){
                    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
                    if(mSharedPreferences.Get_PREFS(Commeny_Activity.this,"idleFresh").equals("yesno")) {
                        mSharedPreferences.Save_PREFS(Commeny_Activity.this,"idleFresh","yes");
                    }
                    finish();
                }
            }
        }, 1000);
    }

}
