package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Activity.Chat_Activity;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Adapter.Sort_Adapter;
import com.multshows.Beans.Friends_friend_Beans;
import com.multshows.Beans.UserFriend;
import com.multshows.Beans.UserFriendTerm;
import com.multshows.Beans.UserFriend_Beans;
import com.multshows.Beans.User_;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.CharacterParser;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.PinyinComparator;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.multshows.Views.SearchEditText_Views;
import com.multshows.Views.SideBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

/**
 * 好友模块的消息界面
 */
public class Friends_friend_Fragment extends Fragment{
    View mView;
    //上下文
    Context mContext;
    //加载数据
    List<Friends_friend_Beans> mList;
    //控件
    public TextView dialog;
    SideBar sideBar;
    private ListView sortListView;
    Gson mGson=new Gson();
    /**
     * 显示字母的TextView
     */

    private Sort_Adapter adapter;
    // private SearchEditText_Views mEditText;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    //新数据
    private List<Friends_friend_Beans> SourceDateList;
    Friends_friend_Beans top;//搜索布局

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.friends_friend,null);
        initViews();
        initData();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initViews() {
        LayoutInflater factory = LayoutInflater.from(mContext);
        View layout = factory.inflate(R.layout.sort_text_item, null);
        dialog= (TextView) layout.findViewById(R.id.sort_text_item_textView);
        mNoView=mView.findViewById(R.id.friends_Friend_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无好友!");
        mNoButton.setVisibility(View.GONE);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar= (SideBar) mView.findViewById(R.id.friends_Friend_sideBar);
        sideBar.setTextView(dialog);

        sortListView = (ListView)mView.findViewById(R.id.friends_Friend_ListView);
        //mEditText = (SearchEditText_Views) mView.findViewById(R.id.friends_Friend_editText);
        sortListView.setEmptyView(mNoView);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mList=new ArrayList<>();
        top=new Friends_friend_Beans("","","","","",null,0,"",0);
        mList.add(top);

        //提取用户名首字母(重新插入数据)
        SourceDateList = filledData(mList);

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new Sort_Adapter(mContext,SourceDateList);
        sortListView.setAdapter(adapter);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }

            }
        });
    }
    /**
     * 为ListView填充数据
     * @param list
     * @return
     */
    private List<Friends_friend_Beans> filledData(List<Friends_friend_Beans> list){
        if(list.size()<=1){
            sideBar.setVisibility(View.INVISIBLE);
        }else {
            sideBar.setVisibility(View.VISIBLE);
        }
        for(int i=1; i<list.size(); i++){
            //汉字转换成拼音
            if("".equals(list.get(i).getNickName()) || list.get(i).getNickName()==null) {
                list.get(i).setSortLetters("#");
            }else {
                String pinyin = characterParser.getSelling(list.get(i).getNickName());
                String sortString = pinyin.substring(0, 1).toUpperCase();


                // 正则表达式，判断首字母是否是英文字母
                if(sortString.matches("[A-Z]")){
                    list.get(i).setSortLetters(sortString.toUpperCase());
                }else{
                    list.get(i).setSortLetters("#");
                }
            }
        }
        return list;

    }
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<Friends_friend_Beans> filterDateList = new ArrayList<Friends_friend_Beans>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (Friends_friend_Beans sortModel : SourceDateList) {
                String name = sortModel.getNickName();
                if (name.toUpperCase().indexOf(
                        filterStr.toString().toUpperCase()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
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
    /**
     * 功能：加载网络数据
     * 参数：无
     * 返回：无
     */

    private void getdata() {
        getFriendsList(1);
    }
    private void getFriendsList(final int pageIndex){
        if(Login_Static.myUserID!=null && !"".equals(Login_Static.myUserID)) {
            UserFriendTerm userFriendTerm = new UserFriendTerm();
            userFriendTerm.setUserId(Login_Static.myUserID);
            userFriendTerm.setState(1);
            userFriendTerm.setPageSize(20);
            userFriendTerm.setPageIndex(pageIndex);
            String data = mGson.toJson(userFriendTerm);
            CLog.e("testing", data);
            OKHttp.OkHttpPost("/User/GetFriendList", "", data, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    CLog.e("testing", "获取好友列表失败：" + e.toString());
                }

                @Override
                public void onResponse(String response, int id) {
                    CLog.e("testing", "获取好友列表：" + response);
                    try {
                        if (Json_Utils.getCode(response) == 0) {
                            if (pageIndex == 1) {
                                mList.clear();
                                mList.add(top);
                            }
                            JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                UserFriend_Beans b = mGson.fromJson(jsonArray.getString(i), UserFriend_Beans.class);
                                User_ target = b.getTargetUser();
                                Friends_friend_Beans bean = new Friends_friend_Beans(b.getId(), Login_Static.myUserID,
                                        target.getUserId(), target.getNickName(), target.getPortrait(),
                                        target.getUserTagList(), b.getState(), "", 1);
                                mList.add(bean);
                            }
                            //提取用户名首字母(重新插入数据)
                            SourceDateList = filledData(mList);
                            // 根据a-z进行排序源数据
                            Collections.sort(SourceDateList, pinyinComparator);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }
}
