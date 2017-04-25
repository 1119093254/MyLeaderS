package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.Friends_RequestList_Beans;
import com.multshows.Beans.UserFriend_Beans;
import com.multshows.Beans.User_;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.MyApplication;
import com.multshows.Views.SwipeLayout_Views;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;

/**
 * 好友申请列表适配器
 */
public class Friends_RequestList_Adapter extends BaseAdapter {
    //上下文
    Context mContext;
    LayoutInflater mInflater;
    //数据集合
    List<UserFriend_Beans> mList;
    Dialog_Hint mDialogHint;
    MyApplication myApplication;
    public Friends_RequestList_Adapter(Context context, List<UserFriend_Beans> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
        myApplication= (MyApplication) mContext.getApplicationContext();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class RequestListViewHolder {
        SimpleDraweeView mHeader;
        TextView mName;
        TextView mTag_1,mTag_2,mTag_3;
        SwipeLayout_Views mSwipeViews;
        Button mOkAdd;//同意添加
        LinearLayout mDelete;//删除申请
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final RequestListViewHolder mViewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.friends_requestlist_item, null);
            mViewHolder = new RequestListViewHolder();
            mViewHolder.mHeader = (SimpleDraweeView) view.findViewById(R.id.requestList_headImage);
            mViewHolder.mName = (TextView) view.findViewById(R.id.requestList_userName);
            mViewHolder.mOkAdd = (Button) view.findViewById(R.id.requestList_okAdd);
            mViewHolder.mSwipeViews = (SwipeLayout_Views) view.findViewById(R.id.requestList_swipeLayout);
            mViewHolder.mDelete = (LinearLayout) view.findViewById(R.id.requestList_deleteRequest);
            mViewHolder.mTag_1= (TextView) view.findViewById(R.id.requestList_userTag1);
            mViewHolder.mTag_2= (TextView) view.findViewById(R.id.requestList_userTag2);
            mViewHolder.mTag_3= (TextView) view.findViewById(R.id.requestList_userTag3);
            SwipeLayout_Views.addSwipeView(mViewHolder.mSwipeViews);
            view.setTag(mViewHolder);
        } else {
            mViewHolder = (RequestListViewHolder) view.getTag();
        }
        final UserFriend_Beans beans = mList.get(i);
        User_ tager=beans.getTargetUser();
        User_ targetUser=beans.getTargetUser();//对方信息


        if (!"null".equals(targetUser.getPortrait()) && targetUser.getPortrait() != null) {
            Uri uri = Uri.parse(SubString_Utils.getImageUrl(targetUser.getPortrait()));
            mViewHolder.mHeader.setImageURI(uri);
        }else {
            Uri uri = Uri.parse("");
            mViewHolder.mHeader.setImageURI(uri);
        }
        mViewHolder.mName.setText(targetUser.getNickName());
        //显示标签部分
        if(tager.getUserTagList()!=null){
            if(tager.getUserTagList().size()==0){
                mViewHolder.mTag_1.setVisibility(View.GONE);
                mViewHolder.mTag_2.setVisibility(View.GONE);
                mViewHolder.mTag_3.setVisibility(View.GONE);
            }else if(tager.getUserTagList().size()==1){
                mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                mViewHolder.mTag_2.setVisibility(View.GONE);
                mViewHolder.mTag_3.setVisibility(View.GONE);
                mViewHolder.mTag_1.setText(tager.getUserTagList().get(0).getTagName());
            }else if(tager.getUserTagList().size()==2){
                mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                mViewHolder.mTag_3.setVisibility(View.GONE);
                mViewHolder.mTag_1.setText(tager.getUserTagList().get(0).getTagName());
                mViewHolder.mTag_2.setText(tager.getUserTagList().get(1).getTagName());
            }else {
                mViewHolder.mTag_3.setVisibility(View.VISIBLE);
                mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                mViewHolder.mTag_1.setText(tager.getUserTagList().get(0).getTagName());
                mViewHolder.mTag_2.setText(tager.getUserTagList().get(1).getTagName());
                mViewHolder.mTag_3.setText(tager.getUserTagList().get(2).getTagName());
            }
        }
        if (beans.getState() == 0) {
            mViewHolder.mOkAdd.setText("同意添加");
            mViewHolder.mOkAdd.setTextColor(mContext.getResources().getColor(R.color.white));
            mViewHolder.mOkAdd.setBackgroundResource(R.drawable.shape_green_3);
            mViewHolder.mOkAdd.setEnabled(true);
        } else if (beans.getState() == 1) {
            mViewHolder.mOkAdd.setText("已同意");
            mViewHolder.mOkAdd.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
            mViewHolder.mOkAdd.setBackgroundResource(R.drawable.shape_grey_3);
            mViewHolder.mOkAdd.setEnabled(false);
        }
        //同意添加
        mViewHolder.mOkAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 同意添加好友
                 */
                CLog.e("testing","对方id"+beans.getId());
                OkHttpUtils.get().url(myApplication.getUrl() + "/User/UpdateFriend")
                        .addParams("id", beans.getId())
                        .addParams("state", "1")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                CLog.e("testing", "同意添加好友失败：" + e.toString());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                CLog.e("testing", "同意添加好友：" + response);
                                try {
                                    if (Json_Utils.getCode(response) == 0) {
                                        mViewHolder.mOkAdd.setText("已同意");
                                        mViewHolder.mOkAdd.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                                        mViewHolder.mOkAdd.setBackgroundResource(R.drawable.shape_grey_3);
                                        mViewHolder.mOkAdd.setEnabled(false);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }
        });
        //删除申请
        mViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogHint = new Dialog_Hint(mContext, 0, "确定删除好友申请?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.hint_dialog_ok:
                                CLog.e("testing","删除好友ID"+beans.getId());
                                OkHttpUtils.get().url(myApplication.getUrl() + "/User/DelFriend")
                                        .addParams("id",beans.getId())  //加密好友Id
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                CLog.e("testing", "删除好友申请失败：" + e.toString());
                                                mDialogHint.dismiss();
                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                CLog.e("testing", "删除好友申请：" + response);
                                                SwipeLayout_Views.removeSwipeView(mViewHolder.mSwipeViews);
                                                mList.remove(i);
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
        return view;
    }
}
