package com.multshows.Fragment;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cpoopc.scrollablelayoutlib.ScrollableLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Activity.Chat_Activity;
import com.multshows.Activity.LoginActivity;
import com.multshows.Activity.My_Information_Activity;
import com.multshows.Beans.User;
import com.multshows.Beans.UserFootprint_Add;
import com.multshows.Beans.UserFriend;
import com.multshows.Beans.UserTag;
import com.multshows.Fragment.base.ScrollAbleFragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.CustomViewPager;
import com.multshows.Views.MyApplication;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.preview.ImagePreviewActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 描述：作者详情页 其它人详情fragment
 * 作者：李萧萧
 * 时间：2016.9/25
 */
public class MyHome_other_Fragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();
    @Bind(R.id.otherPageReturn)
    ImageView mOtherPageReturn;
    @Bind(R.id.My_title)
    TextView mMyTitle;//姓名
    @Bind(R.id.My_Userpic)
    SimpleDraweeView mMyUserpic;//头像
    @Bind(R.id.My_BacgroundImage)
    SimpleDraweeView mMy_BacgroundImage;//头像
    @Bind(R.id.MyLookText)
    TextView mMyLookText;//我看过的/他看过的
    @Bind(R.id.myLookNum)
    TextView mMyLookNum;
    @Bind(R.id.LookMyText)
    TextView mLookMyText;//看过我的/看过他的
    @Bind(R.id.LookMynum)
    TextView mLookMynum;//看过我的/看过他的 数目
    @Bind(R.id.My_userTag1)//3个标签
    TextView mMyUserTag1;
    @Bind(R.id.My_userTag2)
    TextView mMyUserTag2;
    @Bind(R.id.My_userTag3)
    TextView mMyUserTag3;
    @Bind(R.id.chat_her)//私聊
            Button mChatHer;
    @Bind(R.id.My_addFriends)
    Button mMyAddFriends;//添加好友
    @Bind(R.id.OtherTopLayout)
    LinearLayout OtherTopLayout;//
    private ScrollableLayout mScrollLayout;
    SaveSharedPreferences mSharedPreferences = new SaveSharedPreferences();
    TextView CommentSent;//评论
    TextView CommentSent2;//评论
    int UserType;//用户类型  0 妈妈 1宝宝
    private final List<ScrollAbleFragment> fragmentList = new ArrayList<>();
    CustomViewPager viewPager;
    RadioGroup mMyHomeGroup;//单选按钮组
    RadioButton mRadioButton1;
    RadioButton mRadioButton2;
    RadioButton mRadioButton3;
    RadioButton mRadioButton4;
    Gson mGson = new Gson();
    MyApplication myApplication ;
    ArrayList<ImageInfo> imageInfo = new ArrayList<>();

    int flags_my=0;//我的传过来的
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_simple, container, false);
        initView(layout);
        ButterKnife.bind(this, layout);
        myApplication= (MyApplication) getActivity().getApplication();
        Intent intent=getActivity().getIntent();
        flags_my=intent.getIntExtra("myfragment",0);
        CLog.e("testing","我的传来的:"+flags_my);
        initData();
        viewPager.setOffscreenPageLimit(4);
        initLister();
        return layout;
    }

    private void initData() {
        if (mSharedPreferences.Get_PREFS(getActivity(), "Type").equals("baby")) {
            OtherTopLayout.setBackgroundResource(R.drawable.head_bgimg);
            mRadioButton1.setTextColor(getResources().getColor(R.color.red_grey));
            mRadioButton2.setTextColor(getResources().getColor(R.color.red_grey));
            mRadioButton3.setTextColor(getResources().getColor(R.color.red_grey));
            mRadioButton4.setTextColor(getResources().getColor(R.color.red_grey));
        } else {
            mRadioButton1.setBackground(null);
            mRadioButton2.setBackground(null);
            mRadioButton3.setBackground(null);
            mRadioButton4.setBackground(null);
            mRadioButton1.setTextColor(getResources().getColor(R.color.white));
            mRadioButton2.setTextColor(getResources().getColor(R.color.white2));
            mRadioButton3.setTextColor(getResources().getColor(R.color.white2));
            mRadioButton4.setTextColor(getResources().getColor(R.color.white2));
            viewPager.setBackgroundColor(getResources().getColor(R.color.white));
        }
        if (saveSharedPreferences.Get_PREFS(getActivity(), "otherId").equals(Login_Static.myUserID)) {
            mMyAddFriends.setVisibility(View.GONE);
            mChatHer.setVisibility(View.VISIBLE);
            mChatHer.setText("编辑资料");
        } else {
            addLook(saveSharedPreferences.Get_PREFS(getActivity(), "otherId"));
        }


        OkHttpUtils.get().url(myApplication.getUrl() + "/User/GetUser")
                .addParams("userid", saveSharedPreferences.Get_PREFS(getActivity(), "otherId"))
                .addParams("targetuserid", Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "个人信息 失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            CLog.e("testing", "个人信息：" + Json_Utils.getTemplate(response));
                            if (Json_Utils.getCode(response) == 0) {
                                User mUser = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                UserType = mUser.getUserType();
                                if (UserType != 1) {
                                    mRadioButton2.setVisibility(View.VISIBLE);
                                    mRadioButton3.setText("圈子");
                                } else {
                                    mRadioButton2.setVisibility(View.GONE);
                                }
                                saveSharedPreferences.Save_PREFS(getActivity(), "isfriend", mUser.getIsFriend() + "");
                                initFragmentPager(viewPager, mScrollLayout);
                                mMyTitle.setText(mUser.getNickName());
                                if (mUser.getPortrait() != null&&!mUser.getPortrait().equals("")) {
                                    mMyUserpic.setImageURI(Uri.parse(SubString_Utils.getImageUrl(mUser.getPortrait())));
                                    mMy_BacgroundImage.setImageURI(Uri.parse(SubString_Utils.getImageUrl(mUser.getPortrait())));
                                    //头像点击放大需要传的图片URL
                                    ImageInfo info = new ImageInfo();
                                    info.setThumbnailUrl(SubString_Utils.getImageUrl3(mUser.getPortrait()));
                                    info.setBigImageUrl(SubString_Utils.getImageUrlclearly(mUser.getPortrait()));
                                    imageInfo.add(info);
                                }

                                mMyLookNum.setText(mUser.getSeenMeCount() + "");
                                mLookMynum.setText(mUser.getReadMyCount() + "");
                                List<UserTag> mUserTags = new ArrayList<UserTag>();

                                mUserTags = mUser.getListUserTag();
                                if (mUserTags != null) {
                                    if (mUserTags.size() > 0) {
                                        if (mUserTags.size() >= 3) {
                                            mMyUserTag1.setText(mUser.getListUserTag().get(0).getTagName());
                                            mMyUserTag2.setText(mUser.getListUserTag().get(1).getTagName());
                                            mMyUserTag3.setText(mUser.getListUserTag().get(2).getTagName());
                                        } else if (mUserTags.size() == 2) {
                                            mMyUserTag1.setText(mUser.getListUserTag().get(0).getTagName());
                                            mMyUserTag2.setVisibility(View.GONE);
                                            mMyUserTag3.setText(mUser.getListUserTag().get(1).getTagName());
                                        } else if (mUserTags.size() == 1) {
                                            mMyUserTag1.setVisibility(View.GONE);
                                            mMyUserTag2.setText(mUser.getListUserTag().get(0).getTagName());
                                            mMyUserTag3.setVisibility(View.GONE);
                                        }
                                    } else {
                                        mMyUserTag1.setVisibility(View.GONE);
                                        mMyUserTag2.setVisibility(View.GONE);
                                        mMyUserTag3.setVisibility(View.GONE);
                                    }
                                }
                                if (mUser.getIsFriend() == 0) {
                                    mMyAddFriends.setText("等待通过");
                                    mMyAddFriends.setTextColor(getActivity().getResources().getColor(R.color.text_white_c));
                                    mMyAddFriends.setBackgroundResource(R.drawable.shape_grey_4);
                                    mMyAddFriends.setEnabled(false);
                                } else if (mUser.getIsFriend() == 1) {
                                    mMyAddFriends.setText("已添加");
                                    mMyAddFriends.setTextColor(getActivity().getResources().getColor(R.color.text_white_c));
                                    mMyAddFriends.setBackgroundResource(R.drawable.shape_grey_4);
                                    mMyAddFriends.setEnabled(false);
                                } else if (mUser.getIsFriend() == 3) {
                                    mMyAddFriends.setText("添加好友");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    private void initLister() {
            mMyUserpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageInfo.size()>0) {
                        Intent intent = new Intent(getActivity(), ImagePreviewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, imageInfo);
                        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                        getActivity().overridePendingTransition(0, 0);
                    }
                }
            });
        //RadioGroup事件监听，根据选中的不同进行viewpager界面切换
        mMyHomeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });


        //viewPager监听事件
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动中
            }

            @Override
            public void onPageSelected(int position) {
                //滑动结束时
                switch (position) {
                    case 0:
                        mRadioButton1.setChecked(true);
                        CommentSent.setVisibility(View.VISIBLE);
                        CommentSent2.setVisibility(View.GONE);
                        if (mSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                            mRadioButton1.setTextColor(getResources().getColor(R.color.white));
                            mRadioButton2.setTextColor(getResources().getColor(R.color.white2));
                            mRadioButton3.setTextColor(getResources().getColor(R.color.white2));
                            mRadioButton4.setTextColor(getResources().getColor(R.color.white2));

                        }
                        break;
                    case 1:
                     /*   if (mSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                            mRadioButton1.setTextColor(getResources().getColor(R.color.white2));
                            mRadioButton2.setTextColor(getResources().getColor(R.color.white));
                            mRadioButton3.setTextColor(getResources().getColor(R.color.white2));
                        }else     mRadioButton2.setChecked(true);*/
                        break;
                    case 2:
                        CommentSent.setVisibility(View.GONE);
                        CommentSent2.setVisibility(View.VISIBLE);
                        if (mSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                            mRadioButton1.setTextColor(getResources().getColor(R.color.white2));
                            mRadioButton2.setTextColor(getResources().getColor(R.color.white2));
                            mRadioButton3.setTextColor(getResources().getColor(R.color.white));
                        }
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态改变时（调用三次）
            }
        });
    }

    private void initView(View layout) {
        viewPager = (CustomViewPager) layout.findViewById(R.id.simpelviewpager);
        mScrollLayout = (ScrollableLayout) layout.findViewById(R.id.scrollableLayout);
        CommentSent = (TextView) getActivity().findViewById(R.id.MyCommentSent);
        CommentSent2 = (TextView) getActivity().findViewById(R.id.MyCommentSent2);


        mMyHomeGroup = (RadioGroup) layout.findViewById(R.id.myhome_Group);
        mRadioButton1 = (RadioButton) layout.findViewById(R.id.myhome_RadioBtn1);
        mRadioButton2 = (RadioButton) layout.findViewById(R.id.myhome_RadioBtn2);
        mRadioButton3 = (RadioButton) layout.findViewById(R.id.myhome_RadioBtn3);
        mRadioButton4 = (RadioButton) layout.findViewById(R.id.myhome_RadioBtn4);
    }

    public void initFragmentPager(ViewPager viewPager, final ScrollableLayout mScrollLayout) {

        FragmentManager mFragmentManager = getActivity().getSupportFragmentManager();
        CommonFragementPagerAdapter commonFragementPagerAdapter = new CommonFragementPagerAdapter(mFragmentManager);
        fragmentList.add(MyHome_Work_Fragment.newInstance());
        fragmentList.add(MyHome_Idle_Fragment.newInstance());

        if (UserType == 0) {
            fragmentList.add(Cricle_Fragment.newInstance());
            fragmentList.add(MyHome_Photo_Fragment.newInstance());
        }
        if (UserType == 1) {
            fragmentList.add(MyHome_Task_Fragment.newInstance());
            fragmentList.add(MyHome_Photo_Fragment.newInstance());
        }

        viewPager.setAdapter(commonFragementPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
        if(flags_my==0){
            viewPager.setCurrentItem(0);
            mRadioButton1.setChecked(true);
        }else if(flags_my==1){
            viewPager.setCurrentItem(0);
            mRadioButton1.setChecked(true);
        }else if(flags_my==2){
            viewPager.setCurrentItem(2);
            mRadioButton3.setChecked(true);
        }else if(flags_my==3){
            viewPager.setCurrentItem(1);
            mRadioButton2.setChecked(true);
        }else if(flags_my==4){
            viewPager.setCurrentItem(3);
            mRadioButton4.setChecked(true);
        }else if(flags_my==5){
            viewPager.setCurrentItem(2);
            mRadioButton3.setChecked(true);
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.otherPageReturn, R.id.chat_her, R.id.My_addFriends})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.otherPageReturn:
                saveSharedPreferences.Save_PREFS(getActivity(), "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                getActivity().finish();
                break;
            case R.id.chat_her:
                if (mChatHer.getText().toString().equals("私聊")) {
                    if(!saveSharedPreferences.Get_PREFS(getActivity(),"Unlogin").equals("yes")){
                        Intent intent = new Intent(getActivity(), Chat_Activity.class);
                        intent.putExtra("userId", saveSharedPreferences.Get_PREFS(getActivity(), "otherId"));
                        startActivity(intent);
                    }else {
                        Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(mIntent);
                    }

                } else if (mChatHer.getText().toString().equals("编辑资料")) {
                    Intent intent = new Intent(getActivity(), My_Information_Activity.class);
                    startActivity(intent);
                }

                break;
            case R.id.My_addFriends:
                if(!saveSharedPreferences.Get_PREFS(getActivity(),"Unlogin").equals("yes")){
                    addFriends(saveSharedPreferences.Get_PREFS(getActivity(), "otherId"), mMyAddFriends);
                }else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(mIntent);
                }

                break;
        }
    }

    public class CommonFragementPagerAdapter extends FragmentPagerAdapter {

        public CommonFragementPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getCount() > position ? fragmentList.get(position) : null;
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }
    }

    /**
     * 功能：ViewPager监听事件
     * 参数：无
     * 返回：无
     */
    private void resetViewPager(int checkId) {
        switch (checkId) {
            case R.id.myhome_RadioBtn1:
                CommentSent.setVisibility(View.VISIBLE);
                CommentSent2.setVisibility(View.GONE);
                viewPager.setCurrentItem(0, false);
                mRadioButton1.setChecked(true);
                if (mSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                    mRadioButton1.setTextColor(getResources().getColor(R.color.white));
                    mRadioButton2.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton3.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton4.setTextColor(getResources().getColor(R.color.white2));
                }
                break;
            case R.id.myhome_RadioBtn2:

                if (mSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                    viewPager.setCurrentItem(1, false);
                    mRadioButton1.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton2.setTextColor(getResources().getColor(R.color.white));
                    mRadioButton3.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton4.setTextColor(getResources().getColor(R.color.white2));

                } else {
                    viewPager.setCurrentItem(1, false);

                }
                break;
            case R.id.myhome_RadioBtn3:
                CommentSent2.setVisibility(View.VISIBLE);
                CommentSent.setVisibility(View.GONE);
                viewPager.setCurrentItem(2, false);
                if (mSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                    mRadioButton1.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton2.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton3.setTextColor(getResources().getColor(R.color.white));
                    mRadioButton4.setTextColor(getResources().getColor(R.color.white2));
                }
                break;
            case R.id.myhome_RadioBtn4:
                CommentSent2.setVisibility(View.VISIBLE);
                CommentSent.setVisibility(View.GONE);
                viewPager.setCurrentItem(3, false);
                if (mSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                    mRadioButton1.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton2.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton3.setTextColor(getResources().getColor(R.color.white2));
                    mRadioButton4.setTextColor(getResources().getColor(R.color.white));
                }
                break;

        }
    }

    //添加足迹
    private void addLook(String otherId) {
        UserFootprint_Add userFootprint = new UserFootprint_Add();
        userFootprint.setUserId(Login_Static.myUserID);
        userFootprint.setVisitedUserId(otherId);
        String data = mGson.toJson(userFootprint);
        CLog.e("testing", "添加足迹data" + data);
        OKHttp.OkHttpPost("/User/AddFoot", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "添加足迹失败" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "添加足迹" + response);

            }
        });
    }

    /**
     * 添加好友
     */
    private void addFriends(String otherId, final Button v) {
        UserFriend userF = new UserFriend();
        userF.setUserId(Login_Static.myUserID);
        userF.setTargetUserId(otherId);
        userF.setState(0);
        String data = mGson.toJson(userF);
        CLog.e("testing", data);
        OKHttp.OkHttpPost("/User/AddFriend", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "添加好友失败：" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "添加好友：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        v.setText("请求已发送");
                        v.setTextColor(getActivity().getResources().getColor(R.color.text_white_c));
                        v.setBackgroundResource(R.drawable.shape_grey_4);
                        v.setEnabled(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
