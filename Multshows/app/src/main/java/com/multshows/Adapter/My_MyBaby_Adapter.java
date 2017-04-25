package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.BabyAPI;
import com.multshows.Beans.UserBase;
import com.multshows.R;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.MyApplication;
import com.multshows.Views.SwipeLayout_Views;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;

/**
 * 我的宝宝  适配器
 */
public class My_MyBaby_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    LayoutInflater mInflater;
    //数据集合
    List<UserBase> mList;
    RelativeLayout mAddBaby;//添加宝贝


    public My_MyBaby_Adapter(Context context, List<UserBase> list,RelativeLayout addBaby) {
        mContext = context;
        mList = list;
        mAddBaby=addBaby;
        mInflater=LayoutInflater.from(mContext);
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
    class MyBabyViewHolder{
        SimpleDraweeView mHeader;//头像
        TextView mName;//昵称
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final MyBabyViewHolder mViewHolder;
        if(view==null){
            view=mInflater.inflate(R.layout.my_mybaby_item,null);
            mViewHolder=new MyBabyViewHolder();
            mViewHolder.mHeader= (SimpleDraweeView) view.findViewById(R.id.My_MyBaby_item_userHeader);
            mViewHolder.mName= (TextView) view.findViewById(R.id.My_MyBaby_item_userName);
            view.setTag(mViewHolder);
        }else {
            mViewHolder= (MyBabyViewHolder) view.getTag();
        }
        final UserBase bean=mList.get(position);
        if(bean.getPortrait()!=null) {
            mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(bean.getPortrait())));
        }else {
            mViewHolder.mHeader.setImageURI(Uri.parse(""));
        }
        mViewHolder.mName.setText(bean.getNickName());
        /*//删除宝宝
        mViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBaby(bean.getUserId(),position,mViewHolder.mSwipeLayoutViews);
            }
        });*/

        return view;
    }

    /*private void deleteBaby(String babyId, final int position, final SwipeLayout_Views swipeLayoutViews){
        MyApplication myApplication=new MyApplication();
        OkHttpUtils.get().url(myApplication.getUrl()+"/User/DelUser")
                .addParams("userId",babyId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("testing","删除宝宝失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("testing","删除宝宝"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                SwipeLayout_Views.removeSwipeView(swipeLayoutViews);
                                mList.remove(position);
                                mAddBaby.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }*/
}
