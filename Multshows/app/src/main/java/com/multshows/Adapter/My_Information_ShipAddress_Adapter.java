package com.multshows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.Add_NewAddress_Activity;
import com.multshows.Activity.BuyProduce_Activity;
import com.multshows.Beans.My_ShipAddress_Beans;
import com.multshows.Beans.UserAddress;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;

/**
 * 我的收货地址   适配器
 */
public class My_Information_ShipAddress_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    //绑定布局类
    LayoutInflater mInflater;
    //数据集合
    List<UserAddress> mList;
    public String chioce="";//是否从订单页跳进来
    Dialog_Hint mDialogHint;
    Gson mGson=new Gson();
    MyApplication myApplication;
    public My_Information_ShipAddress_Adapter(Context context, List<UserAddress> list) {
        mContext = context;
        mList = list;
        mInflater=LayoutInflater.from(mContext);
    }
    public My_Information_ShipAddress_Adapter(Context context, List<UserAddress> list,String mChioce) {
        mContext = context;
        mList = list;
        this.chioce=mChioce;
        mInflater=LayoutInflater.from(mContext);
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
    class My_ShipAddress_ViewHolder{
        TextView mUserName;//姓名
        TextView mUserType;//用户类型
        TextView mUserPhone;//手机号
        TextView mUserAddress;//地址
        TextView mUserEdit;//编辑
        TextView mUserDelete;//删除
        CheckBox mCheckBox;//默认地址选择
        RelativeLayout chioceAddressLayout;//选择收货地址返回上一页
    }
    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        My_ShipAddress_ViewHolder mViewHolder;
        if(view==null){
            view=mInflater.inflate(R.layout.my_shipaddress_list_item,null);
            mViewHolder=new My_ShipAddress_ViewHolder();
            mViewHolder.mUserName= (TextView) view.findViewById(R.id.my_ShipAddress_userName);
            mViewHolder.mUserType= (TextView) view.findViewById(R.id.my_ShipAddress_userType);
            mViewHolder.mUserPhone= (TextView) view.findViewById(R.id.my_ShipAddress_userPhone);
            mViewHolder.mUserAddress= (TextView) view.findViewById(R.id.my_ShipAddress_userAddress);
            mViewHolder.mUserEdit= (TextView) view.findViewById(R.id.my_ShipAddress_editing);
            mViewHolder.mUserDelete= (TextView) view.findViewById(R.id.my_ShipAddress_delete);
            mViewHolder.mCheckBox= (CheckBox) view.findViewById(R.id.my_ShipAddress_checkBox);
            mViewHolder.chioceAddressLayout= (RelativeLayout) view.findViewById(R.id.chioceAddressLayout);
            view.setTag(mViewHolder);
        }else {
            mViewHolder= (My_ShipAddress_ViewHolder) view.getTag();
        }
        final UserAddress beans=mList.get(position);
        mViewHolder.mUserName.setText(beans.getReceiverName());
        mViewHolder.mUserType.setText("");
        mViewHolder.mUserPhone.setText(beans.getMobile());
        //省市区
        String mPCD=beans.getProvinceName()+" "+beans.getCityname()+" "+beans.getDistrictName();
        mViewHolder.mUserAddress.setText(mPCD+"  "+beans.getDetailAddress());
        //编辑
        mViewHolder.mUserEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,Add_NewAddress_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Address",beans);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        mViewHolder.chioceAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chioce.equals("yes")){
                    UserAddress mUserAddress=mList.get(position);
                    CLog.e("mUserAddress",mUserAddress.getDetailAddress());
                    Intent intent = new Intent();
                    intent.putExtra("mUserAddress", mUserAddress);
                    Activity activity= (Activity) mContext;
                    activity.setResult(5, intent);
                    activity.finish();
                }

            }
        });
        //删除
        mViewHolder.mUserDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogHint = new Dialog_Hint(mContext, 0, "确定删除改地址?", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.hint_dialog_ok:

                                OkHttpUtils.get().url(myApplication.getUrl()+"/User/DelAddress")
                                        .addParams("Id",beans.getId())
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                CLog.e("testing","删除收货地址失败:"+e.toString());
                                                mDialogHint.dismiss();
                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                CLog.e("testing","删除收货地址:"+response);
                                                try {
                                                    if(Json_Utils.getCode(response)==0){
                                                        if("1".equals(mList.get(position).getIsDefault())){
                                                            //先判断是否是默认选中 是
                                                            if(position!=mList.size()-1){
                                                                //在判断是不是最后一行数据 不是
                                                                mList.remove(position);
                                                                if(mList.size()!=0) {
                                                                    //判断是否还有数据
                                                                    mList.get(position).setIsDefault("1");
                                                                }
                                                            }else {
                                                                //是最后一行
                                                                mList.remove(position);
                                                                if(mList.size()!=0) {
                                                                    //判断是否还有数据
                                                                    mList.get(0).setIsDefault("1");
                                                                }
                                                            }
                                                        }else {
                                                            mList.remove(position);
                                                        }
                                                        mDialogHint.dismiss();
                                                        notifyDataSetChanged();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                break;
                        }

                    }
                });
                mDialogHint.show();


            }
        });
        mViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLog.e("getLOL","进这里了");
                UserAddress userAddress = new UserAddress();
                userAddress.setId(beans.getId());
                userAddress.setUserId(beans.getUserId());
                userAddress.setIsDefault("1");
                String data = mGson.toJson(userAddress);
                OKHttp.OkHttpPost("/User/UpdateAddress", "", data, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "修改默认收货地址失败：" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "修改默认收货地址：" + response);
                        for(int i=0;i<mList.size();i++){
                            mList.get(i).setIsDefault("0");
                        }
                        mList.get(position).setIsDefault("1");
                        notifyDataSetChanged();
                    }
                });

            }
        });
       /* mViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.e("getLOL","进这里了");
                if(isChecked){
                    for(int i=0;i<mList.size();i++){
                        mList.get(i).setIsDefault("0");
                    }
                    mList.get(position).setIsDefault("1");
                }else {
                    mList.get(position).setIsDefault("1");
                }
                notifyDataSetChanged();
            }
        });*/
        //重置每一行checkbox状态，必须放到监听事件后面
        boolean isChecked;
        isChecked = "1".equals(beans.getIsDefault());
        mViewHolder.mCheckBox.setChecked(isChecked);
        if(mViewHolder.mCheckBox.isChecked()){
            //设置不可点击
            mViewHolder.mCheckBox.setClickable(false);
        }else {
            mViewHolder.mCheckBox.setClickable(true);
        }
        return view;
    }
}
