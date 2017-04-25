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
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Beans.My_ReWordList_Beans;
import com.multshows.Beans.ShowsBase;
import com.multshows.Beans.ShowsReward;
import com.multshows.Beans.UserBase;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.DateDeal;
import com.multshows.Utils.DateDeal_2;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.Call;

/**
 * 打赏记录适配器
 */
public class My_ReWordList_Adapter extends BaseAdapter {
    //上下文
    Context mContext;
    //数据集合
    List<ShowsReward> mList;
    LayoutInflater mInflater;
    MyApplication myApplication;
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    int flag;
    Dialog mDialog;
    public My_ReWordList_Adapter(Context context, List<ShowsReward> list, int flag) {
        mContext = context;
        mList = list;
        this.flag = flag;
        mInflater = LayoutInflater.from(mContext);
        myApplication= (MyApplication) mContext.getApplicationContext();
        mDialog=new HintText_Dialog(mContext,R.style.MyDialog);
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

    class ReWordListViewHolder {
        TextView mName;//昵称、
        TextView mTime1;//时间1
        TextView mTime2;//时间2
        SimpleDraweeView mHeader;//头像
        TextView mMoney;//金额
        //Button mButton;//撤回
    }
    class ReWordListViewHolder_me {//打赏我的
        TextView mName;//昵称、
        TextView mTime1;//时间1
        TextView mTime2;//时间2
        SimpleDraweeView mHeader;//头像
        TextView mMoney;//金额
        TextView mFei;//手续费
        TextView mRealMoney1;//实际收到金额
        TextView mRealMoney2;//实际收到金额
        Button mGetMoney;//领赏
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(flag==0) {
            final ReWordListViewHolder_me mViewHolder;
            if (view == null) {
                //打赏我的
                view = mInflater.inflate(R.layout.my_reword_list_item_2, null);
                mViewHolder = new ReWordListViewHolder_me();
                mViewHolder.mHeader = (SimpleDraweeView) view.findViewById(R.id.my_ReWordList_item_userHeader);
                mViewHolder.mName = (TextView) view.findViewById(R.id.my_ReWordList_item_userName);
                mViewHolder.mTime1 = (TextView) view.findViewById(R.id.my_ReWordList_item_time1);
                mViewHolder.mTime2 = (TextView) view.findViewById(R.id.my_ReWordList_item_time2);
                mViewHolder.mFei = (TextView) view.findViewById(R.id.my_ReWordList_item_Money2_Fei);
                mViewHolder.mMoney = (TextView) view.findViewById(R.id.my_ReWordList_item_Money2_Money);
                mViewHolder.mRealMoney1 = (TextView) view.findViewById(R.id.my_ReWordList_item_Money2_TrueMoney1);
                mViewHolder.mRealMoney2 = (TextView) view.findViewById(R.id.my_ReWordList_item_Money2_TrueMoney2);
                mViewHolder.mGetMoney= (Button) view.findViewById(R.id.my_ReWordList_item_GetMoney);
                view.setTag(mViewHolder);
            } else {
                mViewHolder = (ReWordListViewHolder_me) view.getTag();
            }
            final ShowsReward beans = mList.get(i);
            final UserBase user=beans.getUser();//打赏者
            ShowsBase show=beans.getShows();//作品编号
            if (!"null".equals(user.getPortrait()) && user.getPortrait()!=null) {
                mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(user.getPortrait())));
            }
            mViewHolder.mName.setText(user.getNickName());
            //0未支付 1 已打赏 2 已撤回 3 已完成
            if(beans.getState()==1){
                mViewHolder.mGetMoney.setText("领取");
                mViewHolder.mGetMoney.setEnabled(true);
                mViewHolder.mGetMoney.setTextColor(mContext.getResources().getColor(R.color.white));
                mViewHolder.mGetMoney.setBackgroundResource(R.drawable.shape_green_4);
            }else if(beans.getState()==2){
                mViewHolder.mGetMoney.setText("已过期");
                mViewHolder.mGetMoney.setEnabled(false);
                mViewHolder.mGetMoney.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                mViewHolder.mGetMoney.setBackgroundResource(R.drawable.shape_green_4);
            }else{
                mViewHolder.mGetMoney.setText("已领取");
                mViewHolder.mGetMoney.setEnabled(false);
                mViewHolder.mGetMoney.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                mViewHolder.mGetMoney.setBackgroundResource(R.drawable.shape_grey_4);
            }
            String str= DateDeal_2.format(Time_Now.getStringTime(beans.getRewardTime()));
            if(SubString_Utils.getTimeUrl(str)!=null) {
                if (SubString_Utils.getTimeUrl(str).length != 0) {
                    mViewHolder.mTime1.setText(SubString_Utils.getTimeUrl(str)[0]);
                } else {
                    mViewHolder.mTime1.setText("");
                }
                if (SubString_Utils.getTimeUrl(str).length >= 2) {
                    mViewHolder.mTime2.setText(SubString_Utils.getTimeUrl(str)[1]);
                } else {
                    mViewHolder.mTime2.setText("");
                }
            }
            mViewHolder.mMoney.setText("¥"+beans.getAmount());
            mViewHolder.mFei.setText("-¥"+beans.getTransFee());
            double real=beans.getAmount()-beans.getTransFee();
            DecimalFormat df=new DecimalFormat("0.##");
            String[] sMoney = df.format(real).split("\\.");
            if(sMoney.length==2) {
                mViewHolder.mRealMoney1.setText("¥"+sMoney[0]);
                mViewHolder.mRealMoney2.setText("."+sMoney[1]);
            }else if(sMoney.length!=0){
                mViewHolder.mRealMoney1.setText("¥"+sMoney[0]);
            }
            //点击进入主页
            mViewHolder.mHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                    if(user!=null)
                        mSave.Save_PREFS(mContext, "otherId",user.getUserId());
                    mContext.startActivity(intent);
                }
            });

            /**
             * 领取打赏
             */
            mViewHolder.mGetMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewHolder.mGetMoney.setEnabled(false);
                    mDialog.show();
                    HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"领取中...","");
                     OkHttpUtils.get().url(myApplication.getUrl()+"/Shows/ReceiveShowsReward")
                    .addParams("showsrewardId",beans.getId())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mViewHolder.mGetMoney.setEnabled(true);
                            HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"网络异常","fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                }
                            }, Login_Static.hintTime);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                if(Json_Utils.getCode(response)==0){
                                    //领取打赏成功
                                    mDialog.dismiss();
                                    mViewHolder.mGetMoney.setText("已领取");
                                    mViewHolder.mGetMoney.setEnabled(false);
                                    mViewHolder.mGetMoney.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                                    mViewHolder.mGetMoney.setBackgroundResource(R.drawable.shape_grey_4);
                                }else {
                                    //领取打赏失败
                                    mViewHolder.mGetMoney.setEnabled(true);
                                    HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,Json_Utils.getMessage(response),"fail");
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
            });



        }else if(flag==1){
            //我打赏的
            final ReWordListViewHolder mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.my_reword_list_item, null);
                mViewHolder = new ReWordListViewHolder();
                mViewHolder.mHeader = (SimpleDraweeView) view.findViewById(R.id.my_ReWordList_item_userHeader);
                mViewHolder.mName = (TextView) view.findViewById(R.id.my_ReWordList_item_userName);
                mViewHolder.mTime1 = (TextView) view.findViewById(R.id.my_ReWordList_item_time1);
                mViewHolder.mTime2 = (TextView) view.findViewById(R.id.my_ReWordList_item_time2);

                mViewHolder.mMoney = (TextView) view.findViewById(R.id.my_ReWordList_item_userMoney);
                //mViewHolder.mButton= (Button) view.findViewById(R.id.my_ReWordList_item_Button);
                view.setTag(mViewHolder);
            } else {
                mViewHolder = (ReWordListViewHolder) view.getTag();
            }
            final ShowsReward beans = mList.get(i);
            UserBase user=beans.getAuthor();//被打赏者
            if (!"null".equals(user.getPortrait()) && user.getPortrait()!=null) {
                mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(user.getPortrait())));
            }
            /*mViewHolder.mButton.setEnabled(true);
            if(beans.getState()==1){
                if(beans.getIsRecall()==1) {
                    //可撤回
                    mViewHolder.mButton.setVisibility(View.VISIBLE);
                    mViewHolder.mButton.setText("撤回");
                    mViewHolder.mButton.setBackgroundResource(R.drawable.shape_alight_green_4);
                    mViewHolder.mButton.setTextColor(mContext.getResources().getColor(R.color.text_green));
                }else {
                    //不可撤回
                    mViewHolder.mButton.setVisibility(View.GONE);
                }
            }else if(beans.getState()==2){
                mViewHolder.mButton.setVisibility(View.VISIBLE);
                mViewHolder.mButton.setBackgroundResource(R.drawable.shape_grey_4);
                mViewHolder.mButton.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                mViewHolder.mButton.setText("已撤回");
                mViewHolder.mButton.setEnabled(false);
            }else {
                mViewHolder.mButton.setVisibility(View.GONE);
            }*/
            mViewHolder.mName.setText(user.getNickName());
            String str=DateDeal_2.format(Time_Now.getStringTime(beans.getRewardTime()));
            if(SubString_Utils.getTimeUrl(str)!=null) {
                if (SubString_Utils.getTimeUrl(str).length != 0) {
                    mViewHolder.mTime1.setText(SubString_Utils.getTimeUrl(str)[0]);
                } else {
                    mViewHolder.mTime1.setText("");
                }
                if (SubString_Utils.getTimeUrl(str).length >= 2) {
                    mViewHolder.mTime2.setText(SubString_Utils.getTimeUrl(str)[1]);
                } else {
                    mViewHolder.mTime2.setText("");
                }
            }
            mViewHolder.mMoney.setText(beans.getAmount()+"");
            //撤回
           /* mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("testing","撤销登录data"+beans.getId());
                    OkHttpUtils.get().url(myApplication.getUrl()+"/Shows/DelShowsReward")
                            .addParams("showsrewardId",beans.getId())
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e("testing","撤销打赏失败"+e.toString());
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("testing","撤销打赏"+response);
                                    try {
                                        if(Json_Utils.getCode(response)==0){
                                            mViewHolder.mButton.setVisibility(View.VISIBLE);
                                            mViewHolder.mButton.setBackgroundResource(R.drawable.shape_grey_4);
                                            mViewHolder.mButton.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                                            mViewHolder.mButton.setText("已撤回");
                                            mViewHolder.mButton.setEnabled(false);
                                            mSave.Save_PREFS(mContext,"WorkId",beans.getShows().getId());
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            });
*/
        }
        return view;
    }
}
