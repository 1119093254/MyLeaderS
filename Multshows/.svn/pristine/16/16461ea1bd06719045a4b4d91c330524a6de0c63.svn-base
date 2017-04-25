package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.SystemBankCard;
import com.multshows.Beans.UserBank;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;

import java.util.List;

/**
 * 绑定银行卡
 */
public class BoundBankList_Adapter extends BaseAdapter{
    Context mContext;//上下文
    LayoutInflater mInflater;
    List<UserBank> mList;//数据集合

    public BoundBankList_Adapter(Context context, List<UserBank> list) {
        mContext = context;
        mList = list;
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

    class KittingViewHolder{
        SimpleDraweeView mIcon;//图标
        TextView mName;//银行卡名称
        TextView mInformation;//银行卡信息
        ImageView mChecked;//选择银行卡标志
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KittingViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.bound_banklist_item,null);
            mViewHolder=new KittingViewHolder();
            mViewHolder.mIcon= (SimpleDraweeView) convertView.findViewById(R.id.Bound_BankList_Item_icon);
            mViewHolder.mName= (TextView) convertView.findViewById(R.id.Bound_BankList_Item_Name);
            mViewHolder.mInformation= (TextView) convertView.findViewById(R.id.Bound_BankList_Item_Information);
            mViewHolder.mChecked= (ImageView) convertView.findViewById(R.id.Bound_BankList_Item_Choose);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (KittingViewHolder) convertView.getTag();
        }
        UserBank bank=mList.get(position);
        SystemBankCard bankCard=bank.getBank();
        if(bankCard.getIcon()!=null && !"null".equals(bankCard.getIcon())) {
            mViewHolder.mIcon.setImageURI(Uri.parse(SubString_Utils.getImageUrl(bankCard.getIcon())));
        }else {
            mViewHolder.mIcon.setImageURI(Uri.parse(""));
        }
        mViewHolder.mName.setText(bankCard.getName());
        String bankType="";
        //根据IsCredit属性值判断银行卡类型
        if(bank.getIsCredit()==0){
            bankType="储蓄卡";
        }else {
            bankType="信用卡";
        }
        if(bank.getIsDefault()==1){
            //默认
            mViewHolder.mChecked.setVisibility(View.VISIBLE);
        }else {
            //不默认
            mViewHolder.mChecked.setVisibility(View.GONE);
        }
        String card="";
        //银行卡号取后四位 数据处理
        if(bank.getCard()!=null && !"null".equals(bank.getCard()) && !"".equals(bank.getCard()) && bank.getCard().length()>=4){
            card=bank.getCard().substring(bank.getCard().length()-4,bank.getCard().length());
        }
        mViewHolder.mInformation.setText("尾号"+card+"  "+bankType);

        return convertView;
    }
}
