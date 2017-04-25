package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.SystemBankCard;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;

import java.util.List;

/**
 * 选择银行卡列表 适配器
 */
public class BankType_Adapter extends BaseAdapter{
    Context mContext;//上下文
    List<SystemBankCard> mList;//数据集合
    LayoutInflater mInflater;

    public BankType_Adapter(Context context, List<SystemBankCard> list) {
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

    class IViewHolder{
        SimpleDraweeView mIcon;//银行图标
        TextView mName;//银行名称
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.bank_type_item,null);
            mViewHolder=new IViewHolder();
            mViewHolder.mIcon= (SimpleDraweeView) convertView.findViewById(R.id.Bank_Type_item_Icon);
            mViewHolder.mName= (TextView) convertView.findViewById(R.id.Bank_Type_item_Name);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (IViewHolder) convertView.getTag();
        }
        SystemBankCard bean=mList.get(position);
        //绑定数据
        if(bean.getIcon()!=null && !"null".equals(bean.getIcon())){
            mViewHolder.mIcon.setImageURI(Uri.parse(SubString_Utils.getImageUrl(bean.getIcon())));
        }else {
            mViewHolder.mIcon.setImageURI(Uri.parse(""));
        }
        mViewHolder.mName.setText(bean.getName());
        return convertView;
    }
}
