package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.multshows.Beans.Anim_Model;
import com.multshows.Beans.Task;
import com.multshows.Beans.UserBase;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.DrawHookView;
import com.multshows.Views.MyApplication;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.preview.ImagePreviewActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;

/**
 * 宝宝任务列表展示
 */
public class MyBaby_Task_Item_Fragment extends Fragment{
    View mView;
    Context mContext;
    //控件
    TextView mTitle;//任务标题
    SimpleDraweeView mParents;//任务图片背景
    SimpleDraweeView mHeader1;
    SimpleDraweeView mHeader2;
    TextView mContent;//内容
    SimpleDraweeView mInfo;//任务图片
    RelativeLayout mContentLayout;
    RelativeLayout mImageLayout;
    ImageView mStar1;//不动的星星
    TextView mMoney_1;
    TextView mMoney_2;
    ImageView mVideo;
    LinearLayout mReadLayout;//阅读

    RelativeLayout mTextLayout;//文字显示
    RelativeLayout mTask01;//第一步
    RelativeLayout mTask02;//第二步
    RelativeLayout mTask03;//第三步01
    RelativeLayout mTask04;//第三步02

    TextView mHint_old;//一开始显示的
    TextView mHint_new;//完成后显示的

    ImageView mAnim_1;////动画的星星
    ImageView mAnim_2;//手指
    ImageView mAnim_3;//
    ImageView mAnim_4;//礼品盒
    DrawHookView mHookView;//对勾
    boolean isOk=false;
    boolean isGo=false;
    Animation animation;
    Animation animation_2;
    Animation animation_3;
    Animation animation_4;

    Task task;//传来的数据
    String tsakID="";
    UserBase mom;
    boolean isShow=false;
    MyApplication mMyApplication;
    List<ImageInfo> mImageInfoList=new ArrayList<>();
    ImageInfo info_image;//
    String timeInt=(Time_Now.getNowTime()+3600)+"";//过期时间戳
    String mUrl="";//视频名称
    String  token;//七牛云token
    String path;//视频播放地址
    //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
    SpeechSynthesizer mTts;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_baby_task_item,null);
        mMyApplication= (MyApplication) getActivity().getApplication();
        mTts = SpeechSynthesizer.createSynthesizer(mContext, null);
        initView();
        initData();
        initListen();
        isOk=true;
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mTitle= (TextView) mView.findViewById(R.id.fragment_BabyTask_Item_Title);
        mMoney_1= (TextView) mView.findViewById(R.id.fragment_BabyTask_Item_money1);
        mMoney_2= (TextView) mView.findViewById(R.id.fragment_BabyTask_Item_money2);
        mVideo= (ImageView) mView.findViewById(R.id.fragment_BabyTask_Item_VodioHeader);
        mTextLayout= (RelativeLayout) mView.findViewById(R.id.fragment_BabyTask_Item_TextLayout);
        mParents= (SimpleDraweeView) mView.findViewById(R.id.fragment_BabyTask_Item_Parents);
        mHint_old= (TextView) mView.findViewById(R.id.fragment_BabyTask_Item_hint_old);
        mHint_new= (TextView) mView.findViewById(R.id.fragment_BabyTask_Item_hint_new);
        mStar1= (ImageView) mView.findViewById(R.id.fragment_BabyTask_Item_TaskImage01);
        mAnim_1= (ImageView) mView.findViewById(R.id.fragment_BabyTask_Item_Anim01);
        mAnim_2= (ImageView) mView.findViewById(R.id.fragment_BabyTask_Item_Anim02);
        mAnim_3= (ImageView) mView.findViewById(R.id.fragment_BabyTask_Item_Anim04);
        mAnim_4= (ImageView) mView.findViewById(R.id.fragment_BabyTask_Item_Anim05);
        mHeader1= (SimpleDraweeView) mView.findViewById(R.id.fragment_BabyTask_Item_Header);
        mHeader2= (SimpleDraweeView) mView.findViewById(R.id.fragment_BabyTask_Item_Header2);
        mInfo= (SimpleDraweeView) mView.findViewById(R.id.fragment_BabyTask_Item_Vodio);
        mContent= (TextView) mView.findViewById(R.id.fragment_BabyTask_Item_content);
        mContentLayout= (RelativeLayout) mView.findViewById(R.id.fragment_BabyTask_Item_ContentLayout);
        mImageLayout= (RelativeLayout) mView.findViewById(R.id.fragment_BabyTask_Item_ImageLayout);
        mTask01= (RelativeLayout) mView.findViewById(R.id.fragment_BabyTask_Item_TASK01);
        mTask02= (RelativeLayout) mView.findViewById(R.id.fragment_BabyTask_Item_TASK02);
        mTask03= (RelativeLayout) mView.findViewById(R.id.fragment_BabyTask_Item_Anim04Layout);
        mTask04= (RelativeLayout) mView.findViewById(R.id.fragment_BabyTask_Item_Anim05Layout);
        mHookView= (DrawHookView) mView.findViewById(R.id.fragment_BabyTask_Item_Anim03);
        mReadLayout= (LinearLayout) mView.findViewById(R.id.fragment_BabyTask_Item_contentLayout);
        info_image = new ImageInfo();
    }

    /**
     * 数据处理
     */
    private void initData() {
        Bundle bundle=getArguments();
        if(bundle!=null){
            task= (Task) bundle.getSerializable("TaskBean");
            if(task!=null) {
                tsakID=task.getId();
                mom=task.getPublisher();
                if (task.getIcon() != null && !"null".equals(task.getIcon())) {
                    mParents.setImageURI(Uri.parse(SubString_Utils.getImageUrlclearly(task.getIcon())));
                } else {
                    mParents.setImageURI(Uri.parse(""));
                }
                mTitle.setText(task.getTitle());
                if(mom.getPortrait()!=null && !"null".equals(mom.getPortrait())) {
                    mHeader1.setImageURI(Uri.parse(SubString_Utils.getImageUrl(mom.getPortrait())));
                    mHeader2.setImageURI(Uri.parse(SubString_Utils.getImageUrl(mom.getPortrait())));
                }else {
                    mHeader1.setImageURI(Uri.parse(""));
                    mHeader2.setImageURI(Uri.parse(""));
                }
                mContent.setText(task.getInfos());
                if (task.getImgs() != null && !"null".equals(task.getImgs()) && !"".equals(task.getImgs())) {
                    mImageLayout.setVisibility(View.VISIBLE);
                    mInfo.setImageURI(Uri.parse(SubString_Utils.getImageUrl(task.getImgs())));
                } else {
                    mImageLayout.setVisibility(View.GONE);
                    mInfo.setImageURI(Uri.parse(""));
                }
                if(task.getAtchType()==1) {
                    mVideo.setVisibility(View.GONE);
                }else {
                    mVideo.setVisibility(View.VISIBLE);
                    mUrl=task.getVideoName();
                }
                String[] sMoney=(task.getReward()+"").split("\\.");
                if(sMoney.length==2) {
                    mMoney_1.setText(sMoney[0]);
                    mMoney_2.setText("."+sMoney[1]);
                }else if(sMoney.length!=0){
                    mMoney_1.setText(sMoney[0]);
                }

                if(task.getState()==1){
                    //未完成
                    mHint_old.setText("已有"+task.getCompleteCount()+"宝宝完成");
                    // 使用AnimationUtils装载动画配置文件
                    animation = AnimationUtils.loadAnimation(getActivity(), R.anim.task_anim1);
                    animation.setAnimationListener(new ReStartAnimationListener());
                    animation_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.task_anim2);
                    animation_2.setAnimationListener(new ReStartAnimationListener());
                    // 启动动画
                    //if(!isGo) {
                        mAnim_1.startAnimation(animation);
                        mAnim_2.startAnimation(animation_2);
                        isGo = true;
                   // }
                }else if(task.getState()==2){
                    //待完成
                    mTask01.setVisibility(View.GONE);
                    mTask02.setVisibility(View.GONE);
                    mStar1.setVisibility(View.GONE);
                    mAnim_1.setVisibility(View.GONE);
                    mTextLayout.setVisibility(View.GONE);
                    mTask03.setVisibility(View.VISIBLE);
                    mTask04.setVisibility(View.VISIBLE);
                    mHint_new.setText("已有"+task.getCompleteCount()+"宝宝完成");
                }


            }
        }

    }

    /**
     * 事件监听
     */
    private void initListen() {
        //点击第一步 点击完成——待确认
        mTask01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChangeState(tsakID);

            }
        });
        //查看大图或视频
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task.getAtchType()==1) {
                    mImageInfoList.clear();
                    if (task.getImgs() != null && !"null".equals(task.getImgs())) {
                        info_image.setThumbnailUrl(SubString_Utils.getImageUrl(task.getImgs()));
                        info_image.setBigImageUrl(SubString_Utils.getImageUrlclearly(task.getImgs()));
                        mImageInfoList.add(info_image);
                    }else {
                        info_image.setThumbnailUrl("");
                        info_image.setBigImageUrl("");
                        mImageInfoList.add(info_image);
                    }
                    //查看图片
                    Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) mImageInfoList);
                    bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(0, 0);
                }else {
                    //查看视频
                    GetQiniuDownToken();

                }
            }
        });
        //阅读
        mReadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTts!=null &&!mTts.isSpeaking()) {
                    //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
                    mTts.setParameter(SpeechConstant.VOICE_NAME, "vixy");//设置发音人
                    mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
                    mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
                    mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
                    //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
                    //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
                    //如果不需要保存合成音频，注释该行代码
                    //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
                    //3.开始合成
                    mTts.startSpeaking(mContent.getText().toString(), mSynListener);
                }
                }
        });
    }
    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
    //动画绘制完成
    @Subscribe
    public void onEvent(Anim_Model model) {
        if("OK".equals(model.getFlags())){
            if(isShow) {
                mTask02.setVisibility(View.GONE);
                //运行第三个动画
                mStar1.setVisibility(View.GONE);
                mAnim_1.setVisibility(View.GONE);
                mTextLayout.setVisibility(View.GONE);
                mTask03.setVisibility(View.VISIBLE);
                mTask04.setVisibility(View.VISIBLE);
                animation_3 = AnimationUtils.loadAnimation(getActivity(), R.anim.task_anim3);
                mAnim_3.startAnimation(animation_3);
                animation_4 = AnimationUtils.loadAnimation(getActivity(), R.anim.task_anim4);
                mAnim_4.startAnimation(animation_4);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        mTts.stopSpeaking();
    }

    /**
     * 重复启动动画
     */
    private class ReStartAnimationListener implements Animation.AnimationListener {

        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            animation.reset();
            animation.setAnimationListener(new ReStartAnimationListener());
            animation.start();
        }

        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * 功能：该界面是否为显示状态
     * 参数：isVisibleToUser 是否正在显示
     * 返回：无
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        //界面显示的时候加载数据
        if (isVisibleToUser) {
            EventBus.getDefault().register(this);
            isShow=true;
            if (isOk) {
                /*if(isGo) {
                    // 启动动画
                    mAnim_1.startAnimation(animation);
                    mAnim_2.startAnimation(animation_2);
                }*/
            }
        }else {
            EventBus.getDefault().unregister(this);
            if (isOk) {
               /* mAnim_1.clearAnimation();
                mAnim_2.clearAnimation();*/

            }
        }
    }

    /**
     * 未完成——已完成（待确认）
     * @param taskId
     */
    private void setChangeState(String taskId){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/Task/ChangeTaskState")
                .addParams("taskId",taskId)
                .addParams("state","2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","完成——待确认失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","完成——待确认"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                task.setState(2);
                                mAnim_1.clearAnimation();
                                mAnim_2.clearAnimation();
                                mTask01.setVisibility(View.GONE);
                                mTask02.setVisibility(View.VISIBLE);
                            }else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void GetQiniuDownToken(){
        CLog.e("","http://ofqiajplg.bkt.clouddn.com/"+mUrl+"?e="+timeInt);
        OkHttpUtils.get().url(mMyApplication.getUrl() + "/service/GetQiniuDownToken")
                .addParams("rawUrl","http://ofqiajplg.bkt.clouddn.com/"+mUrl+"?e="+timeInt)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("token",""+e.getMessage() );
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONObject jsonArray = new JSONObject(response);
                                token=jsonArray.getString("token");

                                CLog.e("token",""+token );
                                path="http://ofqiajplg.bkt.clouddn.com/"+mUrl+"?e="+timeInt+"&token="+token;
                                CLog.e("path",""+path );
                                Uri uri = Uri.parse(path);
                                // 调用系统自带的播放器来播放流媒体视频
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                Log.v("URI:::::::::", uri.toString());
                                intent.setDataAndType(uri, "video/mp4");
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


}
