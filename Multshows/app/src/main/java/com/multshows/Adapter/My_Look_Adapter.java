package com.multshows.Adapter;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Beans.My_Look_Beans;
import com.multshows.Beans.UserFootprint;
import com.multshows.Beans.UserFriend;
import com.multshows.Beans.User_;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.SwipeLayout_Views;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;

/**
 * 列表适配器
 */
public class My_Look_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    //数据集合
    List<UserFootprint> mList;
    LayoutInflater mInflater;
    Dialog mDialog;
    MyApplication myApplication;
    SaveSharedPreferences saveSharedPreferences=new SaveSharedPreferences();
    int type;
    Gson mGson=new Gson();

    public My_Look_Adapter(Context context, List<UserFootprint> list,int type) {
        mContext = context;
        mList = list;
        this.type=type;
        mInflater=LayoutInflater.from(mContext);
        myApplication= (MyApplication) mContext.getApplicationContext();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class MyLookViewHolder{
        SimpleDraweeView mHeader;
        TextView mName;
        TextView mTag_1;
        TextView mTag_2;
        TextView mTag_3;
        Button mButton;
        SwipeLayout_Views mSwipeLayoutViews;
        LinearLayout mDelete;
        RelativeLayout mAllParents;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final MyLookViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.my_look_items,null);
            mViewHolder=new MyLookViewHolder();
            mViewHolder.mHeader= (SimpleDraweeView) convertView.findViewById(R.id.My_Look_items_userHeader);
            mViewHolder.mName= (TextView) convertView.findViewById(R.id.My_Look_items_userName);
            mViewHolder.mButton= (Button) convertView.findViewById(R.id.My_Look_items_Button);
            mViewHolder.mTag_1= (TextView) convertView.findViewById(R.id.look_userTag1);
            mViewHolder.mTag_2= (TextView) convertView.findViewById(R.id.look_userTag2);
            mViewHolder.mTag_3= (TextView) convertView.findViewById(R.id.look_userTag3);
            mViewHolder.mSwipeLayoutViews= (SwipeLayout_Views) convertView.findViewById(R.id.My_Look_items_swipeLayout);
            mViewHolder.mDelete= (LinearLayout) convertView.findViewById(R.id.My_Look_items_delete);
            mViewHolder.mAllParents= (RelativeLayout) convertView.findViewById(R.id.My_Look_items_AllParents);
            SwipeLayout_Views.addSwipeView(mViewHolder.mSwipeLayoutViews);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (MyLookViewHolder) convertView.getTag();
        }
        final UserFootprint beans=mList.get(position);
        final User_ look=beans.getUser();//查看人
        final User_ tolook=beans.getVisitedUser();//被查看人
        if(type==0) {
            if (tolook.getPortrait() == null || "null".equals(tolook.getPortrait())) {
                mViewHolder.mHeader.setImageURI(Uri.parse(""));
            } else {
                mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(tolook.getPortrait())));
            }
            mViewHolder.mName.setText(tolook.getNickName());
        }else {
            if (look.getPortrait() == null || "null".equals(look.getPortrait())) {
                mViewHolder.mHeader.setImageURI(Uri.parse(""));
            } else {
                mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(look.getPortrait())));
            }
            mViewHolder.mName.setText(look.getNickName());
        }
        //删除足迹
        mViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFoot(beans.getId(),position,mViewHolder.mSwipeLayoutViews);
            }
        });
        //显示标签部分
        if(type==0) {
                //显示标签部分
                if(tolook.getUserTagList()!=null){
                    if(tolook.getUserTagList().size()==0){
                        mViewHolder.mTag_1.setVisibility(View.GONE);
                        mViewHolder.mTag_2.setVisibility(View.GONE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                    }else if(tolook.getUserTagList().size()==1){
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_2.setVisibility(View.GONE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                        mViewHolder.mTag_1.setText(tolook.getUserTagList().get(0).getTagName());
                    }else if(tolook.getUserTagList().size()==2){
                        mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                        mViewHolder.mTag_1.setText(tolook.getUserTagList().get(0).getTagName());
                        mViewHolder.mTag_2.setText(tolook.getUserTagList().get(1).getTagName());
                    }else {
                        mViewHolder.mTag_3.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setText(tolook.getUserTagList().get(0).getTagName());
                        mViewHolder.mTag_2.setText(tolook.getUserTagList().get(1).getTagName());
                        mViewHolder.mTag_3.setText(tolook.getUserTagList().get(2).getTagName());
                    }
            }

        }else {
                //显示标签部分
                if(look.getUserTagList()!=null){
                    if(look.getUserTagList().size()==0){
                        mViewHolder.mTag_1.setVisibility(View.GONE);
                        mViewHolder.mTag_2.setVisibility(View.GONE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                    }else if(look.getUserTagList().size()==1){
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_2.setVisibility(View.GONE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                        mViewHolder.mTag_1.setText(look.getUserTagList().get(0).getTagName());
                    }else if(look.getUserTagList().size()==2){
                        mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                        mViewHolder.mTag_1.setText(look.getUserTagList().get(0).getTagName());
                        mViewHolder.mTag_2.setText(look.getUserTagList().get(1).getTagName());
                    }else {
                        mViewHolder.mTag_3.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setText(look.getUserTagList().get(0).getTagName());
                        mViewHolder.mTag_2.setText(look.getUserTagList().get(1).getTagName());
                        mViewHolder.mTag_3.setText(look.getUserTagList().get(2).getTagName());
                    }

                }
        }

        if(beans.getIsFriend()==1){
            mViewHolder.mButton.setText("已为好友");
            mViewHolder.mButton.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
            mViewHolder.mButton.setBackgroundResource(R.drawable.shape_grey_4);
            mViewHolder.mButton.setEnabled(false);
        }else if(beans.getIsFriend()==0){
            mViewHolder.mButton.setText("请求已发送");
            mViewHolder.mButton.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
            mViewHolder.mButton.setBackgroundResource(R.drawable.shape_grey_4);
            mViewHolder.mButton.setEnabled(false);
        }else {
            mViewHolder.mButton.setText("添加好友");
            mViewHolder.mButton.setTextColor(mContext.getResources().getColor(R.color.green));
            mViewHolder.mButton.setBackgroundResource(R.drawable.shape_alight_green_4);
            mViewHolder.mButton.setEnabled(true);
        }
        mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==0) {
                    addFriends(tolook.getUserId(),mViewHolder.mButton,position);
                }else {
                    addFriends(look.getUserId(),mViewHolder.mButton,position);
                }

            }
        });
        mViewHolder.mAllParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                if(type==0) {
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", tolook.getUserId());
                }else {
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", look.getUserId());
                }
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    /**
     * 添加好友
     */
    private void addFriends(String otherId, final Button v, final int position){
        mDialog = new HintText_Dialog(mContext,R.style.MyDialog);
        mDialog.show();
        HintText_Dialog mText_dialog=new HintText_Dialog(mContext,"发送中...","");
        UserFriend userF=new UserFriend();
        userF.setUserId(Login_Static.myUserID);
        userF.setTargetUserId(otherId);
        userF.setState(0);
        String data=mGson.toJson(userF);
        CLog.e("testing",data);
        OKHttp.OkHttpPost("/User/AddFriend", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","添加好友失败："+e.toString());
                HintText_Dialog mText_dialog=new HintText_Dialog(mContext,"网络异常","fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                },Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","添加好友："+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        HintText_Dialog mText_dialog=new HintText_Dialog(mContext,"请求已发送","success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                v.setText("请求已发送");
                                v.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                                v.setBackgroundResource(R.drawable.shape_grey_4);
                                v.setEnabled(false);
                                mList.get(position).setIsFriend(3);
                            }
                        },Login_Static.hintTime);
                    }else {
                        HintText_Dialog mText_dialog=new HintText_Dialog(mContext,Json_Utils.getMessage(response),"fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //删除足迹
    private void deleteFoot(String id, final int position, final SwipeLayout_Views swipeLayoutViews){

        OkHttpUtils.get().url(myApplication.getUrl()+"/User/DelFoot")
                .addParams("Id",id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","删除足迹失败"+e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","删除足迹"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                SwipeLayout_Views.removeSwipeView(swipeLayoutViews);
                                mList.remove(position);
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
