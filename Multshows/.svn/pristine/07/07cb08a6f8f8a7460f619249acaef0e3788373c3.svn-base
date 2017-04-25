package com.multshows.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.UserHead;
import com.multshows.Interfaces.CircleTransform;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.RecyclerImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalListViewAdapter extends BaseAdapter{
    private List<UserHead> mList;
    private Context mContext;
    private LayoutInflater mInflater;


    public HorizontalListViewAdapter(Context context,  List<UserHead> list){
        this.mContext = context;
        mList=list;
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_horizontal_list_item, null);
            holder.mImage= (ImageView) convertView.findViewById(R.id.user_image);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        CLog.e("convertView",SubString_Utils.getImageUrl(mList.get(position).getPortrait()));
         CLog.e("convertView",SubString_Utils.getImageUrl(mList.get(position).getPortrait()));

       if(!"".equals(mList.get(position).getPortrait()))
        Picasso.with(mContext).load(SubString_Utils.getImageUrl2(mList.get(position).getPortrait())).placeholder(R.drawable.defult).error(R.drawable.defult).config(Bitmap.Config.ALPHA_8).tag(convertView).transform(new CircleTransform()).into(holder.mImage);
        //Uri mUri=Uri.parse(SubString_Utils.getImageUrl(mList.get(position).getPortrait()));
        //holder.mImage.setImageURI(mUri);
        return convertView;
    }

    private static class ViewHolder {
        private ImageView mImage;
    }

}