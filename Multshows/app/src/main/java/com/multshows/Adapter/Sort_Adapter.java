package com.multshows.Adapter;

/**
 * 首字母显示适配器
 */
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Activity.AddFriends_Activity;
import com.multshows.Activity.Chat_Activity;
import com.multshows.Activity.GroupList_Activity;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Activity.Search_Activity;
import com.multshows.Beans.Friends_friend_Beans;
import com.multshows.Beans.GroupMember;
import com.multshows.Beans.UserFriendRemark;
import com.multshows.Beans.User_;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.System_Utils;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.Dialog_Interest;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.SwipeLayout_Views;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

public class Sort_Adapter extends BaseAdapter implements SectionIndexer{
    private List<Friends_friend_Beans> list = null;
    private Context mContext;
    Dialog_Hint mDialogHint;
    MyApplication myApplication;
    Gson mGson=new Gson();
    Dialog_Interest mDialog;
    Dialog mDialog_;
    int flags=0;
    String otherId="";
    SaveSharedPreferences saveSharedPreferences=new SaveSharedPreferences();
    List<GroupMember> mUserList=new ArrayList<>();//成员
    public Sort_Adapter(Context mContext, List<Friends_friend_Beans> list) {
        this.mContext = mContext;
        this.list = list;
        myApplication= (MyApplication) mContext.getApplicationContext();
    }
    public Sort_Adapter(Context mContext, List<Friends_friend_Beans> list,int flag,String id,List<GroupMember> userList) {
        this.mContext = mContext;
        this.list = list;
        myApplication= (MyApplication) mContext.getApplicationContext();
        flags=flag;
        otherId=id;
        mUserList=userList;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<Friends_friend_Beans> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getFlag();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        if(flags!=1) {
            if (getItemViewType(position) == 1) {
                ViewHolder viewHolder = null;
                if (view == null) {
                    viewHolder = new ViewHolder();
                    view = LayoutInflater.from(mContext).inflate(R.layout.sort_item, null);
                    viewHolder.mImageView = (SimpleDraweeView) view.findViewById(R.id.sort_headImage);
                    viewHolder.mName = (TextView) view.findViewById(R.id.sort_userName);
                    viewHolder.mFirstCharacter = (TextView) view.findViewById(R.id.sort_firstCharacter);
                    viewHolder.mLayout = (RelativeLayout) view.findViewById(R.id.sort_layout);
                    viewHolder.mItemLayout = (RelativeLayout) view.findViewById(R.id.sort_Item_Layout);
                    viewHolder.mSwipeLayoutViews = (SwipeLayout_Views) view.findViewById(R.id.sort_swipeLayout);
                    viewHolder.mDelete = (TextView) view.findViewById(R.id.sort_deleteFriend);
                    viewHolder.mBei = (TextView) view.findViewById(R.id.sort_NameFriend);
                    viewHolder.mTag_1 = (TextView) view.findViewById(R.id.sort_userTag1);
                    viewHolder.mTag_2 = (TextView) view.findViewById(R.id.sort_userTag2);
                    viewHolder.mTag_3 = (TextView) view.findViewById(R.id.sort_userTag3);
                    SwipeLayout_Views.addSwipeView(viewHolder.mSwipeLayoutViews);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                //初始化
                viewHolder.mTag_1.setVisibility(View.INVISIBLE);
                viewHolder.mTag_2.setVisibility(View.INVISIBLE);
                viewHolder.mTag_3.setVisibility(View.INVISIBLE);


                final Friends_friend_Beans mContent = list.get(position);
                //根据position获取分类的首字母的char ascii值
                int section = getSectionForPosition(position);
                //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
                if (position == getPositionForSection(section)) {
                    viewHolder.mLayout.setVisibility(View.VISIBLE);
                    viewHolder.mFirstCharacter.setText(mContent.getSortLetters());
                } else {
                    viewHolder.mLayout.setVisibility(View.GONE);
                }
                //显示标签部分
                if (mContent.getUserTagList() != null) {
                    if (mContent.getUserTagList().size() == 0) {
                        viewHolder.mTag_1.setVisibility(View.GONE);
                        viewHolder.mTag_2.setVisibility(View.GONE);
                        viewHolder.mTag_3.setVisibility(View.GONE);
                    } else if (mContent.getUserTagList().size() == 1) {
                        viewHolder.mTag_1.setVisibility(View.VISIBLE);
                        viewHolder.mTag_2.setVisibility(View.GONE);
                        viewHolder.mTag_3.setVisibility(View.GONE);
                        viewHolder.mTag_1.setText(mContent.getUserTagList().get(0).getTagName());
                    } else if (mContent.getUserTagList().size() == 2) {
                        viewHolder.mTag_2.setVisibility(View.VISIBLE);
                        viewHolder.mTag_1.setVisibility(View.VISIBLE);
                        viewHolder.mTag_3.setVisibility(View.GONE);
                        viewHolder.mTag_1.setText(mContent.getUserTagList().get(0).getTagName());
                        viewHolder.mTag_2.setText(mContent.getUserTagList().get(1).getTagName());
                    } else {
                        viewHolder.mTag_3.setVisibility(View.VISIBLE);
                        viewHolder.mTag_2.setVisibility(View.VISIBLE);
                        viewHolder.mTag_1.setVisibility(View.VISIBLE);
                        viewHolder.mTag_1.setText(mContent.getUserTagList().get(0).getTagName());
                        viewHolder.mTag_2.setText(mContent.getUserTagList().get(1).getTagName());
                        viewHolder.mTag_3.setText(mContent.getUserTagList().get(2).getTagName());
                    }
                }
                viewHolder.mName.setText(mContent.getNickName());
                //备注
                final ViewHolder finalViewHolder1 = viewHolder;
                viewHolder.mBei.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog = new Dialog_Interest(mContext, 0, "备注", new Dialog_Interest.EditTextContent() {
                            @Override
                            public void getEditText(String text) {
                                mDialog_ = new HintText_Dialog(mContext, R.style.MyDialog);
                                mDialog_.show();
                                HintText_Dialog hintTextDialog = new HintText_Dialog(mContext, "修改中...", "");
                                finalViewHolder1.mSwipeLayoutViews.hintSwipeView(finalViewHolder1.mSwipeLayoutViews);
                                setFriendRemark(mContent.getId(), text, mContent.getUserId(), mContent.getTargetUserId()
                                        , finalViewHolder1.mName,position);
                            }
                        });
                        mDialog.show();
                    }
                });

                //删除好友操作
//            final ViewHolder finalViewHolder = viewHolder;
                final ViewHolder finalViewHolder = viewHolder;
                viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialogHint = new Dialog_Hint(mContext, 0, "确定删除好友?", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.hint_dialog_ok:
                                        OkHttpUtils.get().url(myApplication.getUrl() + "/User/DelFriend")
                                                .addParams("id", mContent.getId())  //加密好友Id
                                                .build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        CLog.e("testing", "删除好友失败：" + e.toString());
                                                        mDialogHint.dismiss();
                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        CLog.e("testing", "删除好友：" + response);
                                                        SwipeLayout_Views.removeSwipeView(finalViewHolder.mSwipeLayoutViews);
                                                        list.remove(position);
                                                        mDialogHint.dismiss();
                                                        notifyDataSetChanged();
                                                    }
                                                });
                                        break;
                                }

                            }
                        });
                        mDialogHint.show();
                    }
                });

                if (!"null".equals(mContent.getUserPic()) && mContent.getUserPic() != null) {
                    Uri uri = Uri.parse(SubString_Utils.getImageUrl(mContent.getUserPic()));
                    viewHolder.mImageView.setImageURI(uri);
                } else {
                    Uri uri = Uri.parse("");
                    viewHolder.mImageView.setImageURI(uri);
                }

                viewHolder.mItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, Chat_Activity.class);
                        intent.putExtra("userId", mContent.getTargetUserId());
                        mContext.startActivity(intent);
                    }
                });
                viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                        saveSharedPreferences.Save_PREFS(mContext, "otherId", mContent.getTargetUserId());
                        mContext.startActivity(intent);
                    }
                });


            } else {
                if(saveSharedPreferences.Get_PREFS(mContext, "Type").equals("baby")){
                    view = LayoutInflater.from(mContext).inflate(R.layout.sort_top_item5, null);
                }else
                view = LayoutInflater.from(mContext).inflate(R.layout.sort_top_item, null);
                RelativeLayout searchLayout = (RelativeLayout) view.findViewById(R.id.friends_Friend_SearchLayout);
                RelativeLayout searchAdd = (RelativeLayout) view.findViewById(R.id.search_AddFriend);
                RelativeLayout searchMore = (RelativeLayout) view.findViewById(R.id.search_MoreFriend);
                searchLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, Search_Activity.class);
                        intent.putExtra("SearchType", "friend");
                        mContext.startActivity(intent);
                    }
                });
                //添加好友
                searchAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AddFriends_Activity.class);
                        mContext.startActivity(intent);
                    }
                });
                //群聊
                searchMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(mContext, GroupList_Activity.class);
                        mContext.startActivity(intent);
                    }
                });
            }
            return view;
        }else {
            //创建群 —— 添加好友
                ViewHolder_Add mHolderAdd;
                if (view == null) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.sort_item_add, null);
                    mHolderAdd = new ViewHolder_Add();
                    mHolderAdd.mImageView = (SimpleDraweeView) view.findViewById(R.id.sort_Add_headImage);
                    mHolderAdd.mName = (TextView) view.findViewById(R.id.sort_userName);
                    mHolderAdd.mFirstCharacter = (TextView) view.findViewById(R.id.sort_firstCharacter);
                    mHolderAdd.mLayout = (RelativeLayout) view.findViewById(R.id.sort_Add_layout);
                    mHolderAdd.mItemLayout = (RelativeLayout) view.findViewById(R.id.sort_Item_Layout);
                    mHolderAdd.mCheckBox = (CheckBox) view.findViewById(R.id.sort_Add_checkBox);
                    mHolderAdd.mTag_1 = (TextView) view.findViewById(R.id.sort_userTag1);
                    mHolderAdd.mTag_2 = (TextView) view.findViewById(R.id.sort_userTag2);
                    mHolderAdd.mTag_3 = (TextView) view.findViewById(R.id.sort_userTag3);
                    view.setTag(mHolderAdd);
                } else {
                    mHolderAdd = (ViewHolder_Add) view.getTag();
                }
                final Friends_friend_Beans mContent = list.get(position);
                //根据position获取分类的首字母的char ascii值
                int section = getSectionForPosition(position);
                //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
                if (position == getPositionForSection_add(section)) {
                    mHolderAdd.mLayout.setVisibility(View.VISIBLE);
                    mHolderAdd.mFirstCharacter.setText(mContent.getSortLetters());
                } else {
                    mHolderAdd.mLayout.setVisibility(View.GONE);
                }
                //显示标签部分
                if (mContent.getUserTagList() != null) {
                    if (mContent.getUserTagList().size() == 0) {
                        mHolderAdd.mTag_1.setVisibility(View.GONE);
                        mHolderAdd.mTag_2.setVisibility(View.GONE);
                        mHolderAdd.mTag_3.setVisibility(View.GONE);
                    } else if (mContent.getUserTagList().size() == 1) {
                        mHolderAdd.mTag_1.setVisibility(View.VISIBLE);
                        mHolderAdd.mTag_2.setVisibility(View.GONE);
                        mHolderAdd.mTag_3.setVisibility(View.GONE);
                        mHolderAdd.mTag_1.setText(mContent.getUserTagList().get(0).getTagName());
                    } else if (mContent.getUserTagList().size() == 2) {
                        mHolderAdd.mTag_2.setVisibility(View.VISIBLE);
                        mHolderAdd.mTag_1.setVisibility(View.VISIBLE);
                        mHolderAdd.mTag_3.setVisibility(View.GONE);
                        mHolderAdd.mTag_1.setText(mContent.getUserTagList().get(0).getTagName());
                        mHolderAdd.mTag_2.setText(mContent.getUserTagList().get(1).getTagName());
                    } else {
                        mHolderAdd.mTag_3.setVisibility(View.VISIBLE);
                        mHolderAdd.mTag_2.setVisibility(View.VISIBLE);
                        mHolderAdd.mTag_1.setVisibility(View.VISIBLE);
                        mHolderAdd.mTag_1.setText(mContent.getUserTagList().get(0).getTagName());
                        mHolderAdd.mTag_2.setText(mContent.getUserTagList().get(1).getTagName());
                        mHolderAdd.mTag_3.setText(mContent.getUserTagList().get(2).getTagName());
                    }
                }
                //默认选中
                if(otherId!=null && otherId.equals(mContent.getTargetUserId())){
                    mContent.setCheck(true);
                    mHolderAdd.mCheckBox.setChecked(true);
                }else if(mUserList!=null){
                    for (int i = 0; i < mUserList.size(); i++) {
                        if(mContent.getTargetUserId().equals(mUserList.get(i).getUserId())){
                            mContent.setCheck(true);
                            mHolderAdd.mCheckBox.setChecked(true);
                        }
                    }
                }
                mHolderAdd.mName.setText(mContent.getNickName());
                if (!"null".equals(mContent.getUserPic()) && mContent.getUserPic() != null) {
                    Uri uri = Uri.parse(SubString_Utils.getImageUrl(mContent.getUserPic()));
                    mHolderAdd.mImageView.setImageURI(uri);
                } else {
                    Uri uri = Uri.parse("");
                    mHolderAdd.mImageView.setImageURI(uri);
                }
            return view;

        }
    }



    public class ViewHolder {
        SimpleDraweeView mImageView;
        TextView mName;
        TextView mFirstCharacter;
        RelativeLayout mLayout;
        SwipeLayout_Views mSwipeLayoutViews;
        TextView mDelete;//删除
        TextView mBei;//备注
        RelativeLayout mItemLayout;//点击跳转聊天界面
        TextView mTag_1;//标签1
        TextView mTag_2;//标签2
        TextView mTag_3;//标签3
    }
    public class ViewHolder_Add {
        SimpleDraweeView mImageView;
        TextView mName;
        TextView mFirstCharacter;
        RelativeLayout mLayout;
        CheckBox mCheckBox;
        RelativeLayout mItemLayout;//点击跳转聊天界面
        TextView mTag_1;//标签1
        TextView mTag_2;//标签2
        TextView mTag_3;//标签3
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 1; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }
    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection_add(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }


    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 添加备注
     * @param userId
     * @param name
     */
    private void setFriendRemark(String Id, final String name, String userId, String targetId, final TextView mName, final int position){
        UserFriendRemark userFriendRemark=new UserFriendRemark();
        userFriendRemark.setUfId(Id);
        userFriendRemark.setName(name);
        userFriendRemark.setUserid(userId);
        userFriendRemark.setTargetuserid(targetId);
        String data=mGson.toJson(userFriendRemark);
        CLog.e("testing","添加备注data"+data);
        OKHttp.OkHttpPost("/User/AddFriendRemark", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","添加备注失败"+e.toString());
                HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"网络异常","fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      mDialog_.dismiss();

                    }
                }, Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","添加备注"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        list.get(position).setNickName(name);
                        mName.setText(name);
                        notifyDataSetChanged();
                        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"修改备注成功","success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog_.dismiss();
                            }
                        },Login_Static.hintTime);
                    }else {
                        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,
                                Json_Utils.getMessage(response),"fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog_.dismiss();
                            }
                        },Login_Static.hintTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
