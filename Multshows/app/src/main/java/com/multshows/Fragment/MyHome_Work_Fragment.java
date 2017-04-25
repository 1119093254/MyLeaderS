package com.multshows.Fragment;

import android.animation.LayoutTransition;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.google.gson.Gson;
import com.multshows.Adapter.WorkAll_Adapter;
import com.multshows.Beans.Shows;
import com.multshows.Beans.ShowsCommentAdd;
import com.multshows.Beans.ShowsTerm;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.Fragment.base.ScrollAbleFragment;
import com.multshows.Interfaces.MyPageCommentInterface;
import com.multshows.Login_Static;
import com.multshows.R;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 描述：我的主页  作品页fragment
 * 作者：李萧萧
 * 时间：2016.9/27
 */
public class MyHome_Work_Fragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
    RelativeLayout Comment_Layout;//评论布局
    MyApplication mMyApplication;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    RelativeLayout mRelativeLayout;//activity底部布局
    //表情框高度
    private ListView mAllworkListview;
    String ReplyId;//被回复者id
    ImageView CommentfaceImage;//表情按钮
    EmojiconEditText comment_EditText;//评论文本框
    private boolean init;//记录界面是否为第一次加载
    WorkAll_Adapter mAdapter;
    List<WorkAll_Beans> mList;
    String workId;//作品id
    String userid;//用户id
    private PullToRefresh mPullToRefresh;//刷新控件
    String TargetUserId;//用户id
    ShowsCommentAdd mShowsCommentAdd;//添加评论
    Dialog mDialog;//消息提示框
    int pageIndexs = 1;//页码
    int emotionHeight;
    TextView MyCommentSent;
    Gson mGson = new Gson();
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    //表情框显示标志
    boolean face_Flag = false;
    private final LayoutTransition transitioner = new LayoutTransition();//键盘和表情切换

    public static MyHome_Work_Fragment newInstance() {
        MyHome_Work_Fragment myHomeWorkFragment = new MyHome_Work_Fragment();
        return myHomeWorkFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        mMyApplication= (MyApplication) getActivity().getApplication();
        mAllworkListview = (ListView) view.findViewById(R.id.AllworkListview);
        mPullToRefresh = (PullToRefresh) view.findViewById(R.id.Fragment_Work_Refresh);
        CommentfaceImage = (ImageView) getActivity().findViewById(R.id.MyCommentfaceImage);
        MyCommentSent = (TextView) getActivity().findViewById(R.id.MyCommentSent);
        comment_EditText = (EmojiconEditText) getActivity().findViewById(R.id.MyComment_EditText);
        Comment_Layout = (RelativeLayout) getActivity().findViewById(R.id.MyPageComment_Layout);
        mRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.Mycomment_bottom_bottom);
        mNoView = view.findViewById(R.id.Fragment_Work_No_View);
        mNoImage = (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText = (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton = (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);
        mAllworkListview.setEmptyView(mNoView);
        mDialog = new HintText_Dialog(getActivity(), R.style.MyDialog);
        init = true;//第一次加载
        //下拉刷新
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                pageIndexs = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndexs = 1;
                        GetListShows();
                    }
                }, 2000);
            }
        });
        //上拉加载
        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                CLog.e("testing", pageIndexs + "页码");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GetListShows();
                    }
                }, 2000);

            }
        });
        return view;
    }

    private void GetListShows() {
        ShowsTerm mShowsTerm = new ShowsTerm();

        if (saveSharedPreferences!=null&&saveSharedPreferences.Get_PREFS(getActivity(), "ismy").equals("yes")){
            mShowsTerm.setUserid(saveSharedPreferences.Get_PREFS(getActivity(), "otherId"));
            mShowsTerm.setIsSelf(1);
        }

        CLog.e("saveSharedPreferences", "" + saveSharedPreferences.Get_PREFS(getActivity(), "otherId"));
        //   saveSharedPreferences.Save_PREFS(getActivity(),"ismy","no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现页 获取所有人的
        mShowsTerm.setPageIndex(pageIndexs);
        mShowsTerm.setPageSize(20);
        mShowsTerm.setSoType(-1);
        mShowsTerm.setType(1);
        mShowsTerm.setIsOver(-1);
        mShowsTerm.setIsShow(-1);
        mShowsTerm.setLocalId(Login_Static.myUserID);
        String data = mGson.toJson(mShowsTerm);
        CLog.e("data", data);
        OKHttp.OkHttpPost("/Shows/GetListShows", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testinggg", "changeNickName:" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        if (pageIndexs == 1) {
                            mList.clear();
                        }
                        pageIndexs++;
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        CLog.e("baby", jsonArray.length() + "");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            Shows Shows = mGson.fromJson(array.toString(), Shows.class);
                            List<String> imageStrinig3 = new ArrayList<>();
                            //对获取到的图片进行处理包装
                            if (Shows.getShowsPhoto() != null && !Shows.getShowsPhoto().equals("")) {
                                String[] imag = Shows.getShowsPhoto().split(",");
                                CLog.e("", "" + imag.length);
                                for (int m = 0; m < imag.length; m++) {
                                    imageStrinig3.add(imag[m]);
                                }
                            }
                            WorkAll_Beans beans = new WorkAll_Beans(Shows.getId(), Shows.getUser().getNickName(),Shows.getUser().getUserType(),  Shows.getPrice() + "", Shows.getUser().getUserId(), SubString_Utils.getImageUrl(Shows.getUser().getPortrait()),Shows.getViews(), Shows.getPublishTime() + "", Shows
                                    .getSerialNumber(), Shows.getTitle(), Shows.getInfos(), imageStrinig3, Shows.getRewardUse(), Shows.getLikeUser(), Shows.getFlowerUser(), Shows.getCommentUser(), Shows.getFavUser(),1);
                            beans.setIsTrade(Shows.getIsTrade());
                            if (Shows.getIsVideo() == 1) {
                                beans.setIsVideo(1);
                                beans.setVideoName(Shows.getVideoName());
                            }
                            mList.add(beans);
                        }
                        if (mAdapter == null) {
                            mAdapter = new WorkAll_Adapter(getActivity(), getActivity(), mList);
                            mAllworkListview.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                            mPullToRefresh.onHeaderRefreshComplete();
                            mPullToRefresh.onFooterRefreshComplete();
                        }


                        mAdapter.getMyPageInterface(new MyPageCommentInterface() {
                            @Override
                            public void execute(String mworkId, String mTargetUserId, String muserid, String mReplyId) {
                                CLog.e("workId", "" + mworkId + " " + muserid + " " + mTargetUserId + " " + mReplyId);
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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public View getScrollableView() {
        return mAllworkListview;
    }

    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {
        mList = new ArrayList<>();
        MyCommentSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseComment();
            }
        });
        GetListShows();
        /*CommentfaceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmojiconFragment(false);
                Log.e( "face_Flag",""+face_Flag);
                if (face_Flag) {
                    //隐藏
                    //点击输入框，打开软键盘，隐藏表情
                    face_Flag = false;
                    hideEmotionView(true);
                    MyHomePager_Activity.isShow=false;
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    comment_EditText.setSelection(comment_EditText.getText().length());
                    comment_EditText.requestFocus();
                } else {
                    //显示
                    //点击表情图标,如果表情显示，隐藏表情，打开软键盘。反之，显示表情，隐藏键盘
                    face_Flag = true;
                    MyHomePager_Activity.isShow=true;
                    showEmotionView(System_Utils.isKeyBoardShow(getActivity()));
                }

            }
        });*/



   /*     List<String> imageStrinig=new ArrayList<>();
        List<String> imageStrinig2=new ArrayList<>();
        List<String> rewardimageUrl=new ArrayList<>();
        List<String> likeUsername=new ArrayList<>();
        List<String> likeUsername2=new ArrayList<>();
        List<String> flowerUsername=new ArrayList<>();
        List<String> flowerUsername2=new ArrayList<>();
        flowerUsername2.add("李萧萧");
        List<WorkComment_Beans> commentBeansList=new ArrayList<>();
        List<WorkComment_Beans> commentBeansList2=new ArrayList<>();
        WorkComment_Beans comment_beans1=new WorkComment_Beans("我爱曾菲菲","","苏州不多见的，漂亮，漂亮,我看也不错");
        WorkComment_Beans comment_beans2=new WorkComment_Beans("贾强胜","李萧萧","我看也不错");
        commentBeansList.add(comment_beans1);
        commentBeansList.add(comment_beans2);
        imageStrinig.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        imageStrinig2.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        WorkAll_Beans beans=new WorkAll_Beans("贾强胜",(long)2,"http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg","2016-8-31 14:03","000000001","来吧晒晒我家这两朵美丽的小花","最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。",
                imageStrinig2,rewardimageUrl,likeUsername2,flowerUsername,commentBeansList);
        mList.add(beans);
        mList.add(beans);
        likeUsername.add("贾强胜");
        imageStrinig.add("http://pic3.bbzhi.com/fengjingbizhi/gaoqingkuanpingfengguangsheyingps/show_fengjingta_281299_11.jpg");
        WorkAll_Beans bean=new WorkAll_Beans("贾强胜",(long)2,"http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg","2016-8-31 14:03","000000001","来吧晒晒我家这两朵美丽的小花","最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。",
                imageStrinig,rewardimageUrl,likeUsername,flowerUsername,commentBeansList2);
        mList.add(bean);
        imageStrinig.add("http://paper.taizhou.com.cn/tzwb/res/1/2/2015-01/20/12/res03_attpic_brief.jpg");
        WorkAll_Beans bean2=new WorkAll_Beans("贾强胜",(long)2,"http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg","2016-8-31 14:03","000000002","来吧晒晒我家这两朵美丽的小花","最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。最近很少出去，一直待在家。",
                imageStrinig,rewardimageUrl,likeUsername,flowerUsername2,commentBeansList);
        mList.add(bean2);
        mAdapter=new WorkAll_Adapter(getActivity(),mList);
        mAllworkListview.setAdapter(mAdapter);*/
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
            /*if (saveSharedPreferences.Get_PREFS(getActivity(), "isfriend").equals("1")||saveSharedPreferences.Get_PREFS(getActivity(), "otherId").equals(Login_Static.myUserID)) {
                getservciesData();
            }*/
            getservciesData();
        }else {
            if(mList!=null&&!saveSharedPreferences.Get_PREFS(getActivity(),"WorkIdNum").equals("")){
                CLog.e("WorkId", saveSharedPreferences.Get_PREFS(getActivity(),"WorkIdNum"));
                UpdataViewNum(saveSharedPreferences.Get_PREFS(getActivity(),"WorkIdNum"));
            }
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

    private void releaseComment() {
        mShowsCommentAdd = new ShowsCommentAdd();
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
                        for (int j = 0; j < mList.size(); j++) {
                            if (mList.get(j).getId().equals(workId)) {
                                //重新获取该条作品/圈子 信息，替换掉旧数据
                                mList.remove(j);
                                GetWorkData(workId, j);
                                break;
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
                .replace(R.id.mycomment_bottom_fragment, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    //获取该作品评论后的新数据
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
                                WorkAll_Beans beans = new WorkAll_Beans(mShows.getId(), mShows.getUser().getNickName(),mShows.getUser().getUserType(),  mShows.getPrice() + "", mShows.getUser().getUserId(),  SubString_Utils.getImageUrl(mShows.getUser().getPortrait()), mShows.getViews(), mShows.getPublishTime() + "", mShows
                                        .getSerialNumber(), mShows.getTitle(), mShows.getInfos(), imageStrinig3, mShows.getRewardUse(), mShows.getLikeUser(), mShows.getFlowerUser(), mShows.getCommentUser(), mShows.getFavUser(),1);
                                if (saveSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                                    beans.setType(1);
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
    public void UpdataViewNum(String mWorkid){
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).getId().equals(mWorkid)){
                mList.get(i).setViews(mList.get(i).getViews()+1);
                mAdapter.notifyDataSetChanged();
                saveSharedPreferences.Save_PREFS(getActivity(),"WorkIdNum","");
                break;
            }
        }
    }
}
