package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.multshows.R;
import com.multshows.Views.TextSpaceView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

//分享邀请界面
public class InvitationActivity extends BaseActivity {

    @Bind(R.id.Idle_ProduceDetails_return)
    ImageView mIdleProduceDetailsReturn;//返回图标
    @Bind(R.id.shareimage)
    ImageView mShareimage;//分享图标
    @Bind(R.id.ShareMoney)
    ImageView ShareMoney;
    @Bind(R.id.InviteCode)
    TextSpaceView InviteCode;//自定义邀请码显示TextView

    String inviteCode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        inviteCode=intent.getStringExtra("inviteCode");
        InviteCode.setSpacing(9);//设置字间距
        InviteCode.setText(inviteCode);
    }

    @Override
    public void findViews() {

    }

    @Override
    public void addListeners() {

    }

    @OnClick({R.id.Idle_ProduceDetails_return, R.id.shareimage,R.id.ShareMoney})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Idle_ProduceDetails_return:
                finish();//返回前一页
                break;
            case R.id.shareimage:
                showShare();//调分享界面
                break;
            case R.id.ShareMoney:
                showShare();//调分享界面
                break;
        }
    }
    //第三方分享界面
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("领秀S APP-开启奇妙的育儿之旅！");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.leadershows.com/home/invitetion?code="+inviteCode);
        // text是分享文本，所有平台都需要这个字段

        oks.setText("来自领秀S APP，体验专属领秀S的独特育儿方式");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博

        oks.setImageUrl("http://www.leadershows.com/icon1.png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.leadershows.com/home/invitetion?code="+inviteCode);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(" ");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("领秀s");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.leadershows.com/home/invitetion?code="+inviteCode);

        // 启动分享GUI
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            //自定义分享的回调想要函数
            @Override
            public void onShare(Platform platform,
                                cn.sharesdk.framework.Platform.ShareParams paramsToShare) {

                //点击新浪微博
                if("SinaWeibo".equals(platform.getName())){
                    //限制微博分享的文字不能超过20
                    paramsToShare.setText("来自领秀S APP，体验专属领秀S的独特育儿方式"+" "+"http://www.leadershows.com/home/download");
                    paramsToShare.setShareType(Platform.SHARE_APPS);
                }
                //点击除了QQ以外的平台
                if(!"QQ".equals(platform.getName())){
                    Log.i("", "点击了QQ以外的平台");
                }
            }
        });
// 启动分享GUI
        oks.show(this);
    }
}
