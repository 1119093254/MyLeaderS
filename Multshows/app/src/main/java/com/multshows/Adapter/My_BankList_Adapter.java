package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.SystemBankCard;
import com.multshows.Beans.UserBank;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;

/**
 * 我的银行卡适配器
 */
public class My_BankList_Adapter extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    List<UserBank> mList;
    MyApplication myApplicatio;
    int type;
    public My_BankList_Adapter(Context context, List<UserBank> list,int type) {
        mContext = context;
        mList = list;
        this.type=type;
        mInflater=LayoutInflater.from(mContext);
        myApplicatio= (MyApplication) mContext.getApplicationContext();
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

    class BankViewHolder{
        SimpleDraweeView mIcon;//银行卡图标
        TextView mBankName;//银行卡名称
        TextView mBankType;//银行卡类型
        TextView mBankNumber;//银行卡尾号
        Button mDelete;//删除
        RelativeLayout mDeleteLayout;//删除布局
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BankViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.my_banklist_item,null);
            mViewHolder=new BankViewHolder();
            mViewHolder.mIcon= (SimpleDraweeView) convertView.findViewById(R.id.My_BankList_BankIcon);
            mViewHolder.mBankName= (TextView) convertView.findViewById(R.id.My_BankList_BankName);
            mViewHolder.mBankType= (TextView) convertView.findViewById(R.id.My_BankList_BankType);
            mViewHolder.mBankNumber= (TextView) convertView.findViewById(R.id.My_BankList_BankNumber);
            mViewHolder.mDelete= (Button) convertView.findViewById(R.id.My_BankList_DeleteBank);
            mViewHolder.mDeleteLayout= (RelativeLayout) convertView.findViewById(R.id.My_BankList_DeleteLayout);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (BankViewHolder) convertView.getTag();
        }
        if(type==0){
            mViewHolder.mDeleteLayout.setVisibility(View.GONE);
        }else {
            mViewHolder.mDeleteLayout.setVisibility(View.VISIBLE);
        }
        final UserBank bank=mList.get(position);
        SystemBankCard bankCard=bank.getBank();
        if(bankCard.getIcon()!=null && !"null".equals(bankCard.getIcon())) {
            mViewHolder.mIcon.setImageURI(Uri.parse(SubString_Utils.getImageUrl(bankCard.getIcon())));
        }else {
            mViewHolder.mIcon.setImageURI(Uri.parse(""));
        }
        mViewHolder.mBankName.setText(bankCard.getName());
        if(bank.getIsCredit()==0) {
            mViewHolder.mBankType.setText("储蓄卡");
        }else {
            mViewHolder.mBankType.setText("信用卡");
        }
        String card="";
        if(bank.getCard()!=null && !"null".equals(bank.getCard()) && !"".equals(bank.getCard()) && bank.getCard().length()>=4){
            card=bank.getCard().substring(bank.getCard().length()-4,bank.getCard().length());
        }
        mViewHolder.mBankNumber.setText("尾号"+card);
        //删除银行卡
        mViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBankList(bank.getId(),position);
            }
        });
        return convertView;
    }
    /**
     * 删除银行卡
     */
    private void deleteBankList(String bankID, final int position){

        OkHttpUtils.get().url(myApplicatio.getUrl()+"/User/DelUserBank")
                .addParams("bankId",bankID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","删除银行卡失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","删除银行卡"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
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
