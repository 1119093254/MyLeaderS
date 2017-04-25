package com.multshows.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Beans.BabyAPI;
import com.multshows.Beans.User;
import com.multshows.Beans.UserFootprint;
import com.multshows.Beans.UserFriend;
import com.multshows.Beans.User_;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
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
public class My_AddFriends_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    //数据集合
    List<User> mList;
    LayoutInflater mInflater;
    Dialog mDialog;
    Gson mGson=new Gson();
    MyApplication myApplication ;
    public My_AddFriends_Adapter(Context context, List<User> list) {
        mContext = context;
        mList = list;
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
        TextView mPhone;
        TextView mType;
        TextView mName;
        TextView mTag_1;
        TextView mTag_2;
        TextView mTag_3;
        Button mButton;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final MyLookViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.my_look_items_2,null);
            mViewHolder=new MyLookViewHolder();
            mViewHolder.mHeader= (SimpleDraweeView) convertView.findViewById(R.id.AddFriends_item_userHeader);
            mViewHolder.mType= (TextView) convertView.findViewById(R.id.AddFriends_item_Text);
            mViewHolder.mPhone= (TextView) convertView.findViewById(R.id.AddFriends_item_userPhone);
            mViewHolder.mName= (TextView) convertView.findViewById(R.id.AddFriends_item_userName);
            mViewHolder.mButton= (Button) convertView.findViewById(R.id.AddFriends_item_Button);
            mViewHolder.mTag_1= (TextView) convertView.findViewById(R.id.AddFriends_item_userTag1);
            mViewHolder.mTag_2= (TextView) convertView.findViewById(R.id.AddFriends_item_userTag2);
            mViewHolder.mTag_3= (TextView) convertView.findViewById(R.id.AddFriends_item_userTag3);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (MyLookViewHolder) convertView.getTag();
        }
        final User beans=mList.get(position);
        isFriends(beans.getUserId(),mViewHolder.mButton);
            if (beans.getPortrait() == null || "null".equals(beans.getPortrait())) {
                mViewHolder.mHeader.setImageURI(Uri.parse(""));
            } else {
                mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(beans.getPortrait())));
            }
        mViewHolder.mType.setVisibility(View.GONE);
        if(beans.getUserType()==0){
            mViewHolder.mType.setVisibility(View.VISIBLE);
            mViewHolder.mType.setText("家长");
        }else {
            for (int i = 0; i < mList.size(); i++) {
                if(beans.getUserType()==1){
                    mViewHolder.mType.setVisibility(View.VISIBLE);
                    mViewHolder.mType.setText("宝宝");
                    break;
                }
            }
        }
        if(beans.getMobile()!=null && !"".equals(beans.getMobile()) && !"null".equals(beans.getMobile())) {
            mViewHolder.mPhone.setVisibility(View.VISIBLE);
            mViewHolder.mPhone.setText("(" +beans.getMobile()+ ")");
        }else {
            mViewHolder.mPhone.setVisibility(View.GONE);
        }
            mViewHolder.mName.setText(beans.getNickName());
        if(beans.getUserId().equals(Login_Static.myUserID)){
            mViewHolder.mButton.setVisibility(View.GONE);
        }else {
            mViewHolder.mButton.setVisibility(View.VISIBLE);
        }
                //显示标签部分
                if(beans.getListUserTag()!=null) {
                    if (beans.getListUserTag().size() == 0) {
                        mViewHolder.mTag_1.setVisibility(View.GONE);
                        mViewHolder.mTag_2.setVisibility(View.GONE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                    } else if (beans.getListUserTag().size() == 1) {
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_2.setVisibility(View.GONE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                        mViewHolder.mTag_1.setText(beans.getListUserTag().get(0).getTagName());
                    } else if (beans.getListUserTag().size() == 2) {
                        mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_3.setVisibility(View.GONE);
                        mViewHolder.mTag_1.setText(beans.getListUserTag().get(0).getTagName());
                        mViewHolder.mTag_2.setText(beans.getListUserTag().get(1).getTagName());
                    } else {
                        mViewHolder.mTag_3.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                        mViewHolder.mTag_1.setText(beans.getListUserTag().get(0).getTagName());
                        mViewHolder.mTag_2.setText(beans.getListUserTag().get(1).getTagName());
                        mViewHolder.mTag_3.setText(beans.getListUserTag().get(2).getTagName());
                    }
                }



        mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriends(beans.getUserId(),mViewHolder.mButton,position);
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
                Log.e("testing","添加好友失败："+e.toString());
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
                                //mList.get(position).setIsFriend(3);
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
    //
    private void isFriends(String otherID, final Button mButton){

        OkHttpUtils.get().url(myApplication.getUrl() + "/User/GetUser")
                .addParams("userid",otherID)
                .addParams("targetuserid", Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "判断是否是好友失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "判断是否是好友 " + response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                User mUser = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                if(mUser.getIsFriend()==0){
                                    //等待通过
                                    mButton.setText("请求已发送");
                                    mButton.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                                    mButton.setBackgroundResource(R.drawable.shape_grey_4);
                                    mButton.setEnabled(false);
                                }else if(mUser.getIsFriend()==1){
                                    //已为好友
                                    mButton.setText("已为好友");
                                    mButton.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                                    mButton.setBackgroundResource(R.drawable.shape_grey_4);
                                    mButton.setEnabled(false);
                                }else{
                                    //不是好友
                                    mButton.setText("添加好友");
                                    mButton.setTextColor(mContext.getResources().getColor(R.color.green));
                                   mButton.setBackgroundResource(R.drawable.shape_alight_green_4);
                                    mButton.setEnabled(true);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}