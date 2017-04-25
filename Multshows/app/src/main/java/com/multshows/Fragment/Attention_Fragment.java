package com.multshows.Fragment;

import android.animation.LayoutTransition;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.LoginActivity;
import com.multshows.Activity.LoginRegisterActivity;
import com.multshows.Activity.MainActivity;
import com.multshows.Adapter.WorkAll_Adapter;
import com.multshows.Beans.EventBus_Model;
import com.multshows.Beans.Shows;
import com.multshows.Beans.ShowsCommentAdd;
import com.multshows.Beans.ShowsTerm;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.Interfaces.CommentInterface;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.ACache;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.SystemBarUtils;
import com.multshows.Utils.System_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.PullToRefresh;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by li on 2016/8/30
 */
public class Attention_Fragment extends Fragment {
    ACache mACache;//加入缓存数据
    @Bind(R.id.friendworkListview)
    ListView mFriendworkListview;
    private boolean init;//记录界面是否为第一次加载
    ImageView CommentfaceImage;
    View mUnlogin;//未登录界面视图
    TextView mUnLoginHint;
    Button mLogin;//登录
    Button mZC;//注册
    LinearLayout Unlogin_view;
    RelativeLayout Comment_Layout;//评论布局
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    //自定义表情框
    RelativeLayout mRelativeLayout;
    TextView CommentSent;//发表评论
    WorkAll_Beans top;
    //表情框高度
    int emotionHeight;
    int firstHeight;
    //表情框显示标志
    boolean face_Flag = false;
    Gson mGson = new Gson();
    String workId;//作品id
    String userid;//用户id
    String ReplyId;//被回复者id
    String TargetUserId;//用户id
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    EmojiconEditText comment_EditText;
    ShowsCommentAdd mShowsCommentAdd;//添加评论
    private PullToRefresh mPullToRefresh;//刷新控件
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    MyApplication mMyApplication;
    int pageIndexs = 1;//页码
    Dialog mDialog;//消息提示框
    private final LayoutTransition transitioner = new LayoutTransition();//键盘和表情切换

    public Attention_Fragment() {
    }

    WorkAll_Adapter mAdapter;
    List<WorkAll_Beans> mList;
    int IsAll = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attention, container, false);
        mMyApplication = (MyApplication) getActivity().getApplication();
        init = true;//第一次加载
        mList = new ArrayList<>();
        EventBus.getDefault().register(this);
        top = new WorkAll_Beans(null, null, 0, null, null, null, 0, null, null, null, null, null, null, null, null, null, null, 0);
        mACache = ACache.get(getActivity());
        comment_EditText = (EmojiconEditText) getActivity().findViewById(R.id.comment_EditText);
        mRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.comment_bottom_bottom);
        CommentSent = (TextView) getActivity().findViewById(R.id.CommentSent2);
        mNoView = view.findViewById(R.id.Fragment_Work_No_View);
        mNoImage = (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText = (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton = (Button) mNoView.findViewById(R.id.no_view_Button);
        Unlogin_view = (LinearLayout) view.findViewById(R.id.Unlogin_view);
        mUnlogin = view.findViewById(R.id.unlogin_views);
        mUnLoginHint = (TextView) mUnlogin.findViewById(R.id.UnLogin_text);
        mLogin = (Button) mUnlogin.findViewById(R.id.UnLogin_login);
        mZC = (Button) mUnlogin.findViewById(R.id.UnLogin_ZC);
        mUnLoginHint.setText("登陆后，在这里可以对好友作品点评、打赏、送花");
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);
        Comment_Layout = (RelativeLayout) getActivity().findViewById(R.id.Comment_Layout);
        ButterKnife.bind(this, view);
        mPullToRefresh = (PullToRefresh) view.findViewById(R.id.attentionfragment_PullToRefresh);
        mDialog = new HintText_Dialog(getActivity(), R.style.MyDialog);
        CommentfaceImage = (ImageView) getActivity().findViewById(R.id.CommentfaceImage);
        mShowsCommentAdd = new ShowsCommentAdd();
        mFriendworkListview.setEmptyView(mNoView);
        if (mSave.Get_PREFS(getActivity(), "Vip").equals("yes")) {
            IsAll = 2;
        }
        if (mSave.Get_PREFS(getActivity(), "Unlogin").equals("yes")) {
            Unlogin_view.setVisibility(View.VISIBLE);
            mPullToRefresh.setVisibility(View.GONE);
        } else {
            Unlogin_view.setVisibility(View.GONE);
            mPullToRefresh.setVisibility(View.VISIBLE);
        }
        CommentfaceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmojiconFragment(false);
                CLog.e("face_Flag", "face_Flag" + face_Flag);

                if (face_Flag && MainActivity.isShow) {
                    //隐藏
                    //点击输入框，打开软键盘，隐藏表情
                    face_Flag = false;
                    hideEmotionView(true);
                    MainActivity.isShow = false;
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //显示
                    //点击表情图标,如果表情显示，隐藏表情，打开软键盘。反之，显示表情，隐藏键盘
                    face_Flag = true;
                    showEmotionView(System_Utils.isKeyBoardShow(getActivity()));
                    MainActivity.isShow = true;
                }
            }


        });
        CommentSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!comment_EditText.getText().toString().equals("")){
                    boolean flag=true;
                    String[] res = comment_EditText.getText().toString().split("\n");
                    String[] res2 = comment_EditText.getText().toString().split(" ");
                    String[] res1 = comment_EditText.getText().toString().split("\n");
                    String[] res3 = comment_EditText.getText().toString().split(" ");
                    CLog.e("res", res.length + "");
                    CLog.e("res1", res1.length + "");
                    CLog.e("res2", res2.length + "");
                    CLog.e("res3", res3.length + "");
                    if ((res.length > 3 && res2.length >= 5) || (res1.length > 4 && res3.length > 5)) {
                        flag = false;
                    }
                    if(flag){
                        releaseComment();
                    }else {
                        mDialog.show();
                        showError("输入文字内容不符合要求", 0);
                        comment_EditText.setText("");
                    }

                }
                else{
                    mDialog.show();
                    showError("内容不可为空", 0);
                }

            }
        });
        //下拉加载
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                pageIndexs = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getListShows();
                    }
                }, 2000);
            }
        });

        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                CLog.e("testing", pageIndexs + "页码");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getListShows();
                    }
                }, 2000);

            }
        });
        //点击相应item 进入相应的宝贝详情页
    /*    mFriendworkListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                *//*if(!mList.get(position).getUserid().equals(Login_Static.myUserID)&&saveSharedPreferences.Get_PREFS(getActivity(), "Type").equals("baby")&&!mList.get(position).getPrice().equals("0.0"))*//*

                    Intent mIntent = new Intent(getActivity(), Idle_ProduceDetails_Activity.class);
                    mIntent.putExtra("IdleID", mList.get(position).getId());
                    getActivity().startActivity(mIntent);


            }
        });*/
        //登录
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        //注册
        mZC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginRegisterActivity.class);
                intent.putExtra("IsThree", "no");//传值，yes代表是第三方登录
                intent.putExtra("Logintoken", "no");
                startActivity(intent);

            }
        });
        return view;
    }

    // 获取作品数据集合
    private void getListShows() {
        ShowsTerm mShowsTerm = new ShowsTerm();
        mSave = new SaveSharedPreferences();
        if (mSave.Get_PREFS(getActivity(), "ismy").equals("yes"))
            mShowsTerm.setIsSelf(1);
        mShowsTerm.setPageIndex(pageIndexs);
        mShowsTerm.setPageSize(20);
        mShowsTerm.setLocalId(Login_Static.myUserID);
        if (mSave.Get_PREFS(getActivity(), "Type").equals("mom")) {
            mShowsTerm.setSoType(-1);
        }
        if (mSave.Get_PREFS(getActivity(), "Type").equals("baby")) {

            mShowsTerm.setSoType(-1);
            mShowsTerm.setIsFriend(1);
        }
        mShowsTerm.setUserid(Login_Static.myUserID);
        mShowsTerm.setShowsClassId(Login_Static.ShowsClassId);
        mShowsTerm.setIsOver(-1);
        mShowsTerm.setType(1);
        mShowsTerm.setIsShow(-1);
        if (IsAll != 0) {
            mShowsTerm.setIsAll(IsAll);
        }
        String data = mGson.toJson(mShowsTerm);
        CLog.e("data", data);
        OKHttp.OkHttpPost("?type=10", "data", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testinggg", "changeNickName:" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        if (pageIndexs == 1) {

                            if (mSave.Get_PREFS(getActivity(), "Type").equals("mom")) {
                                mACache.put("MomTwo" + pageIndexs, response);
                            } else if (mSave.Get_PREFS(getActivity(), "Type").equals("baby")) {
                                mACache.put("BabyTwo" + pageIndexs, response);
                            }
                        }
                        if (pageIndexs == 1) {
                            mList.clear();
                            mList.add(top);
                        }
                        pageIndexs++;
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            Shows Shows = mGson.fromJson(array.toString(), Shows.class);
                            List<String> imageStrinig3 = new ArrayList<>();
                            List<String> likeUsername = new ArrayList<>();
                            if (Shows.getLikeUser() != null) {
                                for (int m = 0; m < Shows.getLikeUser().size(); m++) {
                                    likeUsername.add(Shows.getLikeUser().get(m).getNickName());
                                }
                            }

                            //对获取到的图片进行处理包装
                            if (Shows.getShowsPhoto() != null) {
                                String[] imag = Shows.getShowsPhoto().split(",");
                                for (int m = 0; m < imag.length; m++) {
                                    imageStrinig3.add(imag[m]);
                                }
                            }

                         /*   imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
                            imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
                            imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");*/
                   /*         List<String> rewardimageUrl = new ArrayList<>();
                            if (Shows.getRewardUse() != null)
                                for (int m = 0; i < Shows.getRewardUse().size(); m++) {
                                    rewardimageUrl.add(SubString_Utils.getImageUrl(Shows.getRewardUse().get(m).getPortrait()));
                                }
                            List<String> flowerUsername = new ArrayList<>();*/

                      /*      WorkComment_Beans comment_beans1=new WorkComment_Beans("我爱曾菲菲","","苏州不多见的，漂亮，漂亮,我看也不错");
                            WorkComment_Beans comment_beans2=new WorkComment_Beans("贾强胜","李萧萧","我看也不错");
                            commentBeansList.add(comment_beans1);
                            commentBeansList.add(comment_beans2);*/
                            WorkAll_Beans beans = new WorkAll_Beans(Shows.getId(), Shows.getUser().getNickName(), Shows.getUser().getUserType(), Shows.getPrice() + "", Shows.getUser().getUserId(), SubString_Utils.getImageUrl2(Shows.getUser().getPortrait()), Shows.getViews(), Shows.getPublishTime() + "", Shows
                                    .getSerialNumber(), Shows.getTitle(), Shows.getInfos(), imageStrinig3, Shows.getRewardUse(), Shows.getLikeUser(), Shows.getFlowerUser(), Shows.getCommentUser(), Shows.getFavUser(), 1);
                            if (Shows.getIsVideo() == 1) {
                                beans.setIsVideo(Shows.getIsVideo());
                                beans.setVideoName(Shows.getVideoName());
                            }
                            if (Shows.getIsTrade() == 0) {
                                beans.setIsTrade(0);
                            }
                            mList.add(beans);
                        }
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                        if (mAdapter == null) {
                            mAdapter = new WorkAll_Adapter(getActivity(), getActivity(), mList);
                            mFriendworkListview.setAdapter(mAdapter);
                            mAdapter.getInterface(new CommentInterface() {
                                @Override
                                public void execute(String mworkId, String mTargetUserId, String muserid, String mReplyId) {
                                    CLog.e("mworkId", "" + mworkId + " " + muserid + " " + mTargetUserId + " " + mReplyId);
                                    workId = mworkId;
                                    TargetUserId = mTargetUserId;
                                    userid = muserid;
                                    ReplyId = mReplyId;
                                    comment_EditText.setSelection(comment_EditText.getText().length());
                                    comment_EditText.requestFocus();
                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            });
                        } else mAdapter.notifyDataSetChanged();


                    } else {
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void releaseComment() {
        mDialog.show();
        HintText_Dialog hint = new HintText_Dialog(getActivity(), "正在评论...", "");
        mShowsCommentAdd.setForeignld(workId);
        mShowsCommentAdd.setUserId(Login_Static.myUserID);
        mShowsCommentAdd.setTargetUserId(TargetUserId);
        mShowsCommentAdd.setAuthorId(userid);
        mShowsCommentAdd.setType(1);
        mShowsCommentAdd.setReplyId(ReplyId);
        mShowsCommentAdd.setContent(mEmoji_change.EmojiChange(comment_EditText.getText().toString()));
        String data = mGson.toJson(mShowsCommentAdd);
        CLog.e("testing", data);
        OKHttp.OkHttpPost("/Comment/AddComment", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                showError("评论失败", 0);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    CLog.e("testing", "成功：" + response);
                    if (Json_Utils.getCode(response) == 0) {
                        hideEmotionView(true);
                        face_Flag = false;
                        MainActivity.isShow = false;

                        comment_EditText.setText("");
                        for (int j = 1; j < mList.size(); j++) {
                            CLog.e("mList", "" + mList.get(j).getId());
                            if (mList.get(j).getId().equals(workId)) {
                                //重新获取该条作品/圈子 信息，替换掉旧数据
                                mList.remove(j);
                                GetWorkData(workId, j);
                            }
                        }
                        showError("评论成功", 1);
                    } else showError("评论失败", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {
        getListShows();



      /*  List<String> imageStrinig = new ArrayList<>();
        List<String> imageStrinig2 = new ArrayList<>();
        List<String> rewardimageUrl = new ArrayList<>();
        List<String> likeUsername = new ArrayList<>();
        List<String> likeUsername2 = new ArrayList<>();
        List<String> flowerUsername = new ArrayList<>();
        rewardimageUrl.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        rewardimageUrl.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=d3110bc93887e9505d17f46c2038531b/f11f3a292df5e0fefc348d0b5e6034a85edf728f.jpg");
        rewardimageUrl.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        rewardimageUrl.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=d3110bc93887e9505d17f46c2038531b/f11f3a292df5e0fefc348d0b5e6034a85edf728f.jpg");
        rewardimageUrl.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");

        List<String> flowerUsername2 = new ArrayList<>();
        flowerUsername2.add("李萧萧");
        List<WorkComment_Beans> commentBeansList = new ArrayList<>();
        List<WorkComment_Beans> commentBeansList2=new ArrayList<>();
        WorkComment_Beans comment_beans1=new WorkComment_Beans("我爱曾菲菲","","苏州不多见的，漂亮，漂亮,我看也不错");
        WorkComment_Beans comment_beans2=new WorkComment_Beans("贾强胜","李萧萧","我看也不错");
        commentBeansList.add(comment_beans1);
        commentBeansList.add(comment_beans2);
        imageStrinig.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        imageStrinig2.add("http://pic.4j4j.cn/upload/pic/20130815/31e652fe2d.jpg");
        WorkAll_Beans beans = new WorkAll_Beans("贾强胜", (long) 2, "http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg", "2016-8-31 14:03", "000000001", "来吧晒晒我家这两朵美丽的小花", "最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。",
                imageStrinig2, rewardimageUrl, likeUsername2, flowerUsername, commentBeansList);
        mList.add(beans);
        mList.add(beans);
        likeUsername.add("贾强胜");
        imageStrinig.add("http://pic3.bbzhi.com/fengjingbizhi/gaoqingkuanpingfengguangsheyingps/show_fengjingta_281299_11.jpg");
        WorkAll_Beans bean = new WorkAll_Beans("贾强胜", (long) 2, "http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg", "2016-8-31 14:03", "000000001", "来吧晒晒我家这两朵美丽的小花", "最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。",
                imageStrinig, rewardimageUrl, likeUsername, flowerUsername, commentBeansList);
        mList.add(bean);
        imageStrinig.add("http://paper.taizhou.com.cn/tzwb/res/1/2/2015-01/20/12/res03_attpic_brief.jpg");
        WorkAll_Beans bean2 = new WorkAll_Beans("贾强胜", (long) 2, "http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg", "2016-8-31 14:03", "000000001", "来吧晒晒我家这两朵美丽的小花", "最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。",
                imageStrinig, rewardimageUrl, likeUsername, flowerUsername2, commentBeansList2);
        mList.add(bean2);
        mAdapter = new WorkAll_Adapter(getActivity(), mList);
        mFriendworkListview.setAdapter(mAdapter);*/
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (getUserVisibleHint()) {
            getdata();
        }
    }


    /**
     * 功能：加载网络数据
     * 参数：无
     * 返回：无
     */
    private void getdata() {
        if (init) {
            init = false;//第一次加载数据后，设置为false,不在加载数据
            if (mSave.Get_PREFS(getActivity(), "Type").equals("mom")) {
                if (mACache.getAsString("MomTwo" + 1) != null) {
                    String response = mACache.getAsString("MomTwo" + 1).toString();
                    getLocalData(response);
                }
            } else if (mSave.Get_PREFS(getActivity(), "Type").equals("baby")) {
                if (mACache.getAsString("BabyTwo" + 1) != null) {
                    String response = mACache.getAsString("BabyTwo" + 1).toString();
                    getLocalData(response);
                }
            }
            getservciesData();//取网络数据
        } else {
            if(mList!=null&&!mSave.Get_PREFS(getActivity(),"WorkIdNum").equals("")){
                CLog.e("WorkId", mSave.Get_PREFS(getActivity(),"WorkIdNum"));
                UpdataViewNum(mSave.Get_PREFS(getActivity(),"WorkIdNum"));
            }
            if (!mSave.Get_PREFS(getActivity(), "WorkId").equals("")) {

                for (int j = 1; j < mList.size(); j++) {
                    CLog.e("WorkId", mList.get(j).getId());
                    CLog.e("WorkId", mSave.Get_PREFS(getActivity(), "WorkId"));
                    if (mList.get(j).getId().equals(mSave.Get_PREFS(getActivity(), "WorkId"))) {
                        //重新获取该条作品/圈子 信息，替换掉旧数据
                        mList.remove(j);
                        GetWorkData(mSave.Get_PREFS(getActivity(), "WorkId"), j);
                        mSave.Save_PREFS(getActivity(), "WorkId", "");
                        break;
                    }
                }
            }
            if (mSave.Get_PREFS(getActivity(), "intent").equals("work") || mSave.Get_PREFS(getActivity(), "Refresh1").equals("yes") || mSave.Get_PREFS(getActivity(), "Refresh3").equals("yes")) {
                pageIndexs = 1;
                if (mList == null) mList = new ArrayList<>();
                mList.clear();
                getservciesData();
                mSave.Save_PREFS(getActivity(), "intent", "no");
                mSave.Save_PREFS(getActivity(), "Refresh1", "no");
                mSave.Save_PREFS(getActivity(), "Refresh3", "no");
            }
        }
        mSave.Save_PREFS(getActivity(), "Refresh1", "no");//刷新妈妈端作品列表


    }

    //获取本地作品/圈子list集合
    private void getLocalData(String response) {

        try {
            if (Json_Utils.getCode(response) == 0) {
                if (pageIndexs == 1) {
                    mList.clear();
                    mList.add(top);
                }
                JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject array = new JSONObject(jsonArray.getString(i));
                    Shows Shows = mGson.fromJson(array.toString(), Shows.class);
                    List<String> imageStrinig3 = new ArrayList<>();
                    List<String> likeUsername = new ArrayList<>();

                    if (Shows.getLikeUser() != null) {
                        for (int m = 0; m < Shows.getLikeUser().size(); m++) {
                            likeUsername.add(Shows.getLikeUser().get(m).getNickName());
                        }
                    }

                    //对获取到的图片进行处理包装
                    if (Shows.getShowsPhoto() != null) {
                        String[] imag = Shows.getShowsPhoto().split(",");
                        for (int m = 0; m < imag.length; m++) {
                            imageStrinig3.add(imag[m]);
                        }
                    }

                         /*   imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
                            imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
                            imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");*/
                   /*         List<String> rewardimageUrl = new ArrayList<>();
                            if (Shows.getRewardUse() != null)
                                for (int m = 0; i < Shows.getRewardUse().size(); m++) {
                                    rewardimageUrl.add(SubString_Utils.getImageUrl(Shows.getRewardUse().get(m).getPortrait()));
                                }
                            List<String> flowerUsername = new ArrayList<>();*/

                      /*      WorkComment_Beans comment_beans1=new WorkComment_Beans("我爱曾菲菲","","苏州不多见的，漂亮，漂亮,我看也不错");
                            WorkComment_Beans comment_beans2=new WorkComment_Beans("贾强胜","李萧萧","我看也不错");
                            commentBeansList.add(comment_beans1);
                            commentBeansList.add(comment_beans2);*/
                    WorkAll_Beans beans = new WorkAll_Beans(Shows.getId(), Shows.getUser().getNickName(), Shows.getUser().getUserType(), Shows.getPrice() + "", Shows.getUser().getUserId(), SubString_Utils.getImageUrl2(Shows.getUser().getPortrait()), Shows.getViews(), Shows.getPublishTime() + "", Shows
                            .getSerialNumber(), Shows.getTitle(), Shows.getInfos(), imageStrinig3, Shows.getRewardUse(), Shows.getLikeUser(), Shows.getFlowerUser(), Shows.getCommentUser(), Shows.getFavUser(), 1);
                    if (Shows.getIsVideo() == 1) {
                        beans.setIsVideo(Shows.getIsVideo());
                        beans.setVideoName(Shows.getVideoName());
                    }
                    if (Shows.getIsTrade() == 0) {
                        beans.setIsTrade(0);
                    }
                    mList.add(beans);
                }
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                if (mAdapter == null) {
                    mAdapter = new WorkAll_Adapter(getActivity(), getActivity(), mList);
                    mFriendworkListview.setAdapter(mAdapter);
                    mAdapter.getInterface(new CommentInterface() {
                        @Override
                        public void execute(String mworkId, String mTargetUserId, String muserid, String mReplyId) {

                            CLog.e("mworkId", "" + mworkId + " " + muserid + " " + mTargetUserId + " " + mReplyId);
                            workId = mworkId;
                            TargetUserId = mTargetUserId;
                            userid = muserid;
                            ReplyId = mReplyId;
                            comment_EditText.setSelection(comment_EditText.getText().length());
                            comment_EditText.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    });
                } else mAdapter.notifyDataSetChanged();


            } else {
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
            getdata();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //发布结果提示
    public void showError(final String errorString, final int is) {
        String isString = null;
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        HintText_Dialog hint = new HintText_Dialog(getActivity(), errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                mDialog.dismiss();
            }
        }, 1000);
    }

    /**
     * 隐藏emoji
     **/
    private void hideEmotionView(boolean showKeyBoard) {
        if (mRelativeLayout.isShown()) {
            if (showKeyBoard) {
                RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) Comment_Layout.getLayoutParams();
                localLayoutParams.height = mRelativeLayout.getTop();
                localLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                mRelativeLayout.setVisibility(View.GONE);
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                System_Utils.showKeyBoard(comment_EditText);
                comment_EditText.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        unlockContainerHeightDelayed();
                    }

                }, 200L);
            } else {
                mRelativeLayout.setVisibility(View.GONE);
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                unlockContainerHeightDelayed();
            }
        }
    }

    private void lockContainerHeight(int paramInt) {
        //RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) mLayout.getLayoutParams();
        // localLayoutParams.height = emotionHeight;
        // localLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
    }

    public void unlockContainerHeightDelayed() {
        // ((RelativeLayout.LayoutParams) mLayout.getLayoutParams()).height =emotionHeight;
    }

    private void showEmotionView(boolean showAnimation) {
        if (showAnimation) {
            transitioner.setDuration(200);
        } else {
            transitioner.setDuration(0);
        }

        int statusBarHeight = SystemBarUtils.getStatusBarHeight(getActivity());
        emotionHeight = System_Utils.getKeyboardHeight(getActivity());

        System_Utils.hideSoftInput(comment_EditText);
        mRelativeLayout.getLayoutParams().height = emotionHeight;
        mRelativeLayout.setVisibility(View.VISIBLE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //在5.0有navigationbar的手机，高度高了一个statusBar
        int lockHeight = System_Utils.getAppContentHeight(getActivity());
//            lockHeight = lockHeight - statusBarHeight;
        lockContainerHeight(lockHeight);
    }


    private void setEmojiconFragment(boolean useSystemDefault) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.comment_bottom_fragment, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    private void GetWorkData(String showsId, final int position) {

        OkHttpUtils.get().url(mMyApplication.getUrl() + "/Shows/GetShows")
                .addParams("showsId", showsId)
                .addParams("userId", Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("error", e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONObject jsonject = new JSONObject(Json_Utils.getTemplate(response));
                                Shows mShows = mGson.fromJson(jsonject.toString(), Shows.class);
                                List<String> imageStrinig3 = new ArrayList<>();
                                List<String> likeUsername = new ArrayList<>();

                                if (mShows.getLikeUser() != null) {
                                    for (int m = 0; m < mShows.getLikeUser().size(); m++) {
                                        likeUsername.add(mShows.getLikeUser().get(m).getNickName());
                                    }
                                }
                                //对获取到的图片进行处理包装
                                if (mShows.getShowsPhoto() != null && !mShows.getShowsPhoto().equals("")) {
                                    String[] imag = mShows.getShowsPhoto().split(",");
                                    for (int m = 0; m < imag.length; m++) {
                                        imageStrinig3.add(imag[m]);
                                    }
                                }

                         /*   imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
                            imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
                            imageStrinig3.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");*/
                         /*   List<String> rewardimageUrl = new ArrayList<>();
                            if (Shows.getRewardUse() != null)
                                for (int m = 0; i < Shows.getRewardUse().size(); m++) {
                                    rewardimageUrl.add(SubString_Utils.getImageUrl(Shows.getRewardUse().get(m).getPortrait()));
                                }
                            List<String> flowerUsername = new ArrayList<>();*/

                      /*      WorkComment_Beans comment_beans1=new WorkComment_Beans("我爱曾菲菲","","苏州不多见的，漂亮，漂亮,我看也不错");
                            WorkComment_Beans comment_beans2=new WorkComment_Beans("贾强胜","李萧萧","我看也不错");
                            commentBeansList.add(comment_beans1);
                            commentBeansList.add(comment_beans2);*/
                                WorkAll_Beans beans = new WorkAll_Beans(mShows.getId(), mShows.getUser().getNickName(), mShows.getUser().getUserType(), mShows.getPrice() + "", mShows.getUser().getUserId(), SubString_Utils.getImageUrl2(mShows.getUser().getPortrait()), mShows.getViews(), mShows.getPublishTime() + "", mShows
                                        .getSerialNumber(), mShows.getTitle(), mShows.getInfos(), imageStrinig3, mShows.getRewardUse(), mShows.getLikeUser(), mShows.getFlowerUser(), mShows.getCommentUser(), mShows.getFavUser(), 1);
                                if (mShows.getIsTrade() == 0) {
                                    beans.setIsTrade(0);
                                }
                                if (mSave.Get_PREFS(getActivity(), "Type").equals("mom")) {
                                    //   beans.setType(1);
                                }
                                if (mShows.getIsVideo() == 1) {
                                    beans.setIsVideo(1);
                                    beans.setVideoName(mShows.getVideoName());
                                }
                                mList.add(position, beans);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                //后期判断错误原因

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //分类拉取数据
    @Subscribe
    public void onEvent(EventBus_Model model) {
        if (model.getFlag().equals("宝宝作品")) {
            IsAll = 2;
            pageIndexs = 1;
            getListShows();
        } else if (model.getFlag().equals("妈妈作品")) {
            IsAll = 1;
            pageIndexs = 1;
            getListShows();
        }else if (model.getFlag().equals("update")){
            if(mSave.Get_PREFS(getActivity(), "Type").equals("mom")){
                IsAll = 0;
                pageIndexs = 1;
                getListShows();
                mFriendworkListview.setSelection(0);
            }
        } else{
            IsAll = 0;
            pageIndexs = 1;
            getListShows();
            mFriendworkListview.setSelection(0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void UpdataViewNum(String mWorkid){
        for (int i = 1; i < mList.size(); i++) {
            if(mList.get(i).getId().equals(mWorkid)){
                mList.get(i).setViews(mList.get(i).getViews()+1);
                mAdapter.notifyDataSetChanged();
                mSave.Save_PREFS(getActivity(),"WorkIdNum","");
                break;
            }
        }
    }
}